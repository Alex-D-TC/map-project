package com.company.View;

import com.company.Controller.SarcinaController;
import com.company.Domain.Sarcina;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.sun.javaws.exceptions.InvalidArgumentException;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.util.Duration;

import javax.xml.bind.ValidationException;
import java.util.Optional;

/**
 * Created by AlexandruD on 11/20/2016.
 */
public class SarcinaView implements View<SarcinaController> {

    public static final int WIDTH = 487;
    public static final int HEIGHT = 400;

    private SarcinaController controller;

    private TableView<Sarcina> main_table;

    private TableColumn<Sarcina, Integer> main_table_column_id;

    private TableColumn<Sarcina, String> main_table_column_desc;

    private TextField textfield_main_id,
            textfield_main_desc,
            textfield_add_id,
            textfield_add_desc,
            textfield_update_id,
            textfield_update_desc,
            textfield_main_filter_substring;

    private Button btn_add_ok,
            btn_add_cancel,
            btn_main_add,
            btn_main_update,
            btn_main_remove,
            btn_update_ok,
            btn_update_cancel,
            btn_main_clear_filter;

    private StackPane root;

    private AnchorPane anchor_add,
                      anchor_update;

    public SarcinaView(SarcinaController _controller) {
        controller = _controller;
        setup();
        buildView();
    }

    /**
     * Sets the controller
     * @param controller The controller
     */
    @Override
    public void setController(SarcinaController controller) {
        this.controller = controller;
    }

    /**
     * Clears the fields of the given context.<br><br>
     *
     *  For example, for the context 'add', the following controls will be cleared:<br><br>
     *      textfield_add_id<br>
     *      textfield_add_desc<br>
     * @param context
     */
    private void clearFields(String context) {
        String  textfieldId = "textfield_"+context+"_id",
                textfieldName = "textfield_"+context+"_desc";

        try {
            TextField idField = (TextField) this.getClass().getDeclaredField(textfieldId).get(this),
                    descField = (TextField) this.getClass().getDeclaredField(textfieldName).get(this);

            idField.setText("");
            descField.setText("");

        }catch(NoSuchFieldException e) {
            e.printStackTrace();
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Displays an alert
     * @param message The alert message
     * @param type The alert type
     */
    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.show();
    }

    /**
     * Displays an error alert
     * @param message The error message
     */
    private void showError(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }

    /**
     * Builds the interface
     */
    private void buildView() {

        final int OFFSET = 500;

        BorderPane main = buildMain();
        anchor_add = buildSubMenu("add").get();
        anchor_update = buildSubMenu("update").get();


        // Anchorpane translations

        TranslateTransition openAdd = new TranslateTransition(new Duration(350), anchor_add);
        openAdd.setByX(-1 * OFFSET);

        TranslateTransition closeAdd = new TranslateTransition(new Duration(350), anchor_add);
        closeAdd.setByX(OFFSET);

        TranslateTransition openUpdate = new TranslateTransition(new Duration(350), anchor_update);
        openUpdate.setByX(-1 * OFFSET);

        TranslateTransition closeUpdate = new TranslateTransition(new Duration(350), anchor_update);
        closeUpdate.setByX(OFFSET);


        btn_main_clear_filter.setOnMouseClicked((event) -> clearFilter());

        btn_main_add.setOnMouseClicked((event) -> {
            clearFields("add");
            openAdd.play();
        });

        btn_main_update.setOnMouseClicked((event) -> {
            if(fieldsClear("main")) {
                showError("Please select an element");
                return;
            }
            textfield_update_id.setText(textfield_main_id.getText());
            textfield_update_desc.setText(textfield_main_desc.getText());
            openUpdate.play();
        });

        btn_main_remove.setOnMouseClicked((event) -> {
            if(fieldsClear("main")) {
                showError("Please select an element");
                return;
            }

            try {
                controller.remove(Integer.parseInt(textfield_main_id.getText()));
            }catch(ElementNotFoundException e) {
                e.printStackTrace();
            }
        });

        btn_add_ok.setOnMouseClicked((event) -> {

            if(fieldsClear("add")) {
                showError("One or more fields are empty");
                return;
            }

            try {
                addTask();
                closeAdd.play();
            }catch(Exception e) {
                showError(e.getMessage());
            }

        });

        btn_add_cancel.setOnMouseClicked((event) -> closeAdd.play());

        btn_update_ok.setOnMouseClicked((event) -> {
            if(fieldsClear("update")) {
                showError("Description field is empty");
                return;
            }

            try {
                updateTask();
                closeUpdate.play();
            }catch(Exception e) {
                showError(e.getMessage());
            }

        });

        textfield_main_filter_substring.setOnKeyReleased((event) -> filter());

        btn_update_cancel.setOnMouseClicked((event) -> closeUpdate.play());

        root.setPrefWidth(WIDTH);
        root.setPrefHeight(HEIGHT);

        root.setMinWidth(WIDTH);
        root.setMinHeight(HEIGHT);

        anchor_add.setPrefWidth(WIDTH);
        anchor_add.setMinWidth(WIDTH);

        anchor_update.setPrefWidth(WIDTH);
        anchor_update.setMinWidth(WIDTH);

        root.getChildren().add(main);
        root.getChildren().addAll(anchor_add, anchor_update);

        root.setMargin(anchor_add, new Insets(0, 0, 0, 999));
        root.setMargin(anchor_update, new Insets(0, 0, 0, 999));
    }

    /**
     * Initializes the core controls
     */
    private void setup() {

        root = new StackPane();

        textfield_add_id = new TextField();

        textfield_main_filter_substring = new TextField();

        textfield_add_desc = new TextField();

        btn_add_ok = new Button("OK");
        btn_add_cancel = new Button("CANCEL");

        btn_main_clear_filter = new Button("Clear Filter");

        btn_update_ok = new Button("OK");
        btn_update_cancel = new Button("CANCEL");

        main_table = new TableView<>();

        main_table_column_id = new TableColumn<>("ID");
        main_table_column_desc = new TableColumn<>("Desc");

        main_table_column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        main_table_column_desc.setCellValueFactory(new PropertyValueFactory<>("description"));

        main_table.getColumns().addAll(main_table_column_id, main_table_column_desc);

        main_table.setItems(controller.getFilterModel());

        main_table.getSelectionModel().selectedItemProperty().addListener((observable, old, newv) -> {
            displayTask(newv);
        });

        textfield_main_id = new TextField();
        textfield_main_id.setEditable(false);

        textfield_main_desc = new TextField();
        textfield_main_desc.setEditable(false);

        textfield_update_id = new TextField();
        textfield_update_id.setEditable(false);

        textfield_update_desc = new TextField();

        btn_main_add = new Button("Add");
        btn_main_remove = new Button("Remove");
        btn_main_update = new Button("Update");

    }

    /**
     * Builds the main screen UI
     * @return The main screen pane
     */
    private BorderPane buildMain() {

        BorderPane pane = new BorderPane();

        // LEFT

        StackPane leftPane = new StackPane();

        leftPane.getChildren().add(main_table);

        pane.setLeft(leftPane);

        // RIGHT

        GridPane rightPane = new GridPane();

        rightPane.setPadding(new Insets(20, 10, 10, 0));

        rightPane.setAlignment(Pos.CENTER);

        rightPane.setHgap(5);
        rightPane.setVgap(20);

        rightPane.add(new Label("ID:"),0,0);
        rightPane.add(new Label("Description"),0,1);

        rightPane.add(textfield_main_desc, 1, 1);
        rightPane.add(textfield_main_id, 1, 0);

        HBox buttonBox = new HBox();

        buttonBox.setSpacing(20);

        buttonBox.setAlignment(Pos.CENTER);

        buttonBox.setPadding(new Insets(10,15,10,0));

        buttonBox.getChildren().addAll(
                btn_main_add,
                btn_main_update,
                btn_main_remove,
                btn_main_clear_filter
        );

        // Filter controls

        VBox filterBox = new VBox();
        HBox substringFilter = new HBox();

        substringFilter.setAlignment(Pos.CENTER);

        Label substringLabel = new Label("Filter substring: ");

        substringFilter.getChildren().add(substringLabel);
        substringFilter.getChildren().add(textfield_main_filter_substring);

        filterBox.getChildren().add(substringFilter);

        rightPane.add(filterBox, 0, 2, 2, 1);

        rightPane.add(buttonBox, 0, 3, 2, 1);

        pane.setRight(rightPane);

        return pane;
    }

    /**
     * Checks if the fields of the given context are clear<br><br>
     *  For example, for the context 'add', the following controls will be cleared:<br><br>
     *      textfield_add_id<br>
     *      textfield_add_desc<br>
     * @param context The context
     * @return
     */
    private boolean fieldsClear(String context) {
        try {
            TextField id_field = (TextField) getClass().getDeclaredField("textfield_" + context + "_id").get(this),
                    desc_field = (TextField) getClass().getDeclaredField("textfield_" + context + "_desc").get(this);

            return id_field.getText().equals("") || desc_field.getText().equals("");
        }catch(IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Displays a {@link Sarcina} on the GUI
     * @param sarcina The {@link Sarcina}
     */
    private void displayTask(Sarcina sarcina) {

        if(sarcina == null) {
            clearFields("main");
            return;
        }

        textfield_main_id.setText(Integer.toString(sarcina.getId()));
        textfield_main_desc.setText(sarcina.getDescription());

    }

    /**
     * Builds the submenu of a given context
     * @param context The context
     * @return The submenu
     */
    private Optional<AnchorPane> buildSubMenu(String context) {

        try {

            AnchorPane pane = new AnchorPane();

            Background b = new Background(new BackgroundFill(Paint.valueOf("#e8e8e8"), null, null));

            pane.setBackground(b);

            VBox box = new VBox();

            box.setAlignment(Pos.CENTER);

            pane.getChildren().add(box);

            Label lab = new Label( Character.toUpperCase(context.charAt(0)) + context.substring(1));

            lab.setMinWidth(WIDTH);
            lab.setPrefWidth(WIDTH);

            lab.setAlignment(Pos.CENTER);
            lab.setFont(Font.font("System", 34));

            GridPane grid = new GridPane();

            grid.setMinWidth(WIDTH);
            grid.setPrefWidth(WIDTH);

            grid.setAlignment(Pos.CENTER);

            grid.setPadding(new Insets(20, 0, 0, 0));

            grid.setHgap(20);
            grid.setVgap(25);

            Label idLab = new Label("ID:");

            TextField textfield_id = (TextField) getClass().getDeclaredField("textfield_"+context+"_id").get(this),
                      textfield_desc = (TextField) getClass().getDeclaredField("textfield_"+context+"_desc").get(this);

            grid.add(idLab, 0, 0);
            grid.add(textfield_id, 1, 0);

            Label descLab = new Label("Desription:");

            grid.add(descLab, 0, 1);
            grid.add(textfield_desc, 1, 1);

            HBox buttonBox = new HBox();

            buttonBox.setMinWidth(WIDTH);
            buttonBox.setPrefWidth(WIDTH);

            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(15,0,0,0));
            buttonBox.setSpacing(20);

            Button button_ok = (Button) this.getClass().getDeclaredField("btn_" + context + "_ok").get(this),
                    button_cancel = (Button) this.getClass().getDeclaredField("btn_"+ context +"_cancel").get(this);

            buttonBox.getChildren().addAll(button_ok, button_cancel);

            box.getChildren().addAll(lab, grid, buttonBox);

            return Optional.of(pane);

        }catch(NoSuchFieldException e) {
            e.printStackTrace();
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    /**
     * Adds a task
     * @throws InvalidArgumentException, ElementExistsException, ValidationException
     */
    private void addTask() throws InvalidArgumentException, ElementExistsException, ValidationException {

        int id;
        String desc;

        try {

            if(textfield_add_id.getText().equals(""))
                throw new InvalidArgumentException (new String[]{"Id field is empty"});

            if(textfield_add_desc.getText().equals(""))
                throw new InvalidArgumentException (new String[]{"Description field is empty"});

            id = Integer.parseInt(textfield_add_id.getText());
            desc = textfield_add_desc.getText();

            controller.add(id, desc);

        }catch(NumberFormatException e) {
            throw new NumberFormatException("Invalid number inserted in the id field");
        }

    }

    /**
     * Updates a task
     * @throws InvalidArgumentException, ElementNotFoundException, ValidationException
     */
    private void updateTask() throws InvalidArgumentException, ElementNotFoundException, ValidationException {

        int id;
        String desc;

        if(textfield_update_desc.getText().equals(""))
            throw new InvalidArgumentException(new String[]{"Description field is empty"});

        id = Integer.parseInt(textfield_update_id.getText());
        desc = textfield_update_desc.getText();

        controller.update(id, desc);
    }

    /**
     * Returns the root pane
     * @return The root pane
     */
    public StackPane getView() {
        return root;
    }

    private void clearFilter() {
        textfield_main_filter_substring.clear();
        controller.filter(Optional.empty());
    }

    private void filter() {
        String substring = textfield_main_filter_substring.getText();
        Optional<String> substr;

        if(substring.equals(""))
            substr = Optional.empty();
        else
            substr = Optional.of(substring);

        controller.filter(substr);
    }

}
