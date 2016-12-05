package com.company.View;

import com.company.Controller.FisaPostController;
import com.company.Domain.Post;
import com.company.Domain.Sarcina;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import javafx.animation.TranslateTransition;
import javafx.collections.ListChangeListener;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.AbstractMap;
import java.util.ResourceBundle;
import java.util.function.Consumer;

/**
 * Created by AlexandruD on 12/3/2016.
 */
public class FisaPostView implements Initializable, View<FisaPostController> {

    private FisaPostController controller;

    @FXML
    private Button btn_add_ok,
            btn_add_cancel,
            btn_remove_ok,
            btn_remove_cancel,
            btn_top_tasks_ok,
            btn_tasks_ok;

    @FXML
    private TableView<AbstractMap.SimpleEntry<Sarcina, Long>> tableview_top_tasks;

    @FXML
    private TableColumn<AbstractMap.SimpleEntry<Sarcina, Long>, Integer> tablecolumn_top_tasks_task_id;

    @FXML
    private TableColumn<AbstractMap.SimpleEntry<Sarcina, Long>, String> tablecolumn_top_tasks_task_name;

    @FXML
    private TableColumn<AbstractMap.SimpleEntry<Sarcina, Long>, Long> tablecolumn_top_tasks_appearance_count;

    @FXML
    private TableView<Post> tableview_main;

    @FXML
    private TableColumn<Post, Integer> tablecolumn_main_id;

    @FXML
    private TableColumn<Post, String> tablecolumn_main_name;

    @FXML
    private TableView<Sarcina> tableview_tasks;

    @FXML
    private TableColumn<Sarcina, Integer> tablecolumn_tasks_task_id;

    @FXML
    private TableColumn<Sarcina, String> tablecolumn_tasks_task_description;

    @FXML
    private MenuItem menuitem_main_add_task,
            menuitem_main_remove_task,
            menuitem_main_top_tasks,
            menuitem_main_show_tasks;

    @FXML
    private ComboBox<Sarcina> combobox_add_tasks,
            combobox_remove_tasks;

    @FXML
    private AnchorPane anchor_add,
            anchor_remove,
            anchor_top_tasks,
            anchor_tasks;

    @FXML
    private TextField textfield_add_id,
            textfield_remove_id;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {

        final int DURATION = 350;
        final int X_OFFSET = 500;

        // Tasks transitions
        TranslateTransition tasks_close = getTranslation(anchor_tasks, DURATION, X_OFFSET);

        bindTranslationToMenuItem("menuitem_main_show_tasks", "anchor_tasks", (p) ->
            tableview_tasks.setItems(controller.getTasksOfPosition(p.getId())));

        btn_tasks_ok.setOnMouseClicked((event) -> tasks_close.play());

        // Add transitions
        TranslateTransition add_close = getTranslation(anchor_add, DURATION, X_OFFSET);

        bindTranslationToMenuItem("menuitem_main_add_task", "anchor_add", (p) -> {
            combobox_add_tasks.setItems(controller.getTasksModel());
            combobox_add_tasks.getSelectionModel().selectFirst();
            combobox_add_tasks.getItems().addListener((ListChangeListener.Change<? extends Sarcina> c) ->
                    combobox_add_tasks.getSelectionModel().selectFirst()
            );
            textfield_add_id.setText(Integer.toString(p.getId()));
        });

        btn_add_ok.setOnMouseClicked((event) -> {
            int postId = Integer.parseInt(textfield_add_id.getText());
            Sarcina s = combobox_add_tasks.getValue();
            try {
                controller.addFisaPostElem(postId, s.getId());
                add_close.play();
            }catch(ValidationException e) {
                // Impossibru
                showError("Cum pula mea vezi asta???");
            }catch(ElementExistsException e) {
                showError("Position - Task combination already added");
            }
        });

        btn_add_cancel.setOnMouseClicked((event) -> add_close.play());

        // Remove transitions
        TranslateTransition update_close = getTranslation(anchor_remove, DURATION, X_OFFSET);

        bindTranslationToMenuItem("menuitem_main_remove_task", "anchor_remove", (p) -> {
            combobox_remove_tasks.setItems(controller.getTasksModel());
            combobox_remove_tasks.getSelectionModel().selectFirst();
            combobox_remove_tasks.getItems().addListener((ListChangeListener.Change<? extends Sarcina> c) ->
                    combobox_remove_tasks.getSelectionModel().selectFirst()
            );
            textfield_remove_id.setText(Integer.toString(p.getId()));
        });

        btn_remove_ok.setOnMouseClicked((event) -> {
            int postId = Integer.parseInt(textfield_remove_id.getText());
            Sarcina s = combobox_remove_tasks.getValue();
            try {
                controller.removeFisaPostElem(postId, s.getId());
            }catch(ElementNotFoundException e) {
                showError("No Position - Task combination to update was found");
            }
        });

        btn_remove_cancel.setOnMouseClicked((event) -> update_close.play());


        // Top tasks transitions

        TranslateTransition top_tasks_open = getTranslation(anchor_top_tasks, DURATION, -1 * X_OFFSET);

        TranslateTransition top_tasks_close = getTranslation(anchor_top_tasks, DURATION, X_OFFSET);

        menuitem_main_top_tasks.setOnAction((event) -> {
            tableview_top_tasks.setItems(controller.topTasks());
            top_tasks_open.play();
        });

        btn_top_tasks_ok.setOnMouseClicked((event) -> top_tasks_close.play());

        setupMain();
        setupTasks();
        setupTopTasks();
    }

    private TranslateTransition getTranslation(Node node, int duration, int x_offset) {
        TranslateTransition translation = new TranslateTransition(new Duration(duration), node);
        translation.setByX(x_offset);
        return translation;
    }

    /**
     *
     * @param menuItemName
     * @param pane
     */
    private void bindTranslationToMenuItem(String menuItemName, String pane,
                                           Consumer<Post> func) {

        final int DURATION = 350;
        final int X_OFFSET = 500;

        try {
            AnchorPane rootPane = (AnchorPane) this.getClass().getDeclaredField(pane).get(this);
            TranslateTransition show = new TranslateTransition(new Duration(DURATION), rootPane);
            show.setByX(-1 * X_OFFSET);
            MenuItem menuItem = (MenuItem) this.getClass().getDeclaredField(menuItemName).get(this);
            menuItem.setOnAction((event) -> {
                Post p = tableview_main.getSelectionModel().getSelectedItem();
                if(p == null) {
                    showError("No position selected");
                    return;
                }
                func.accept(p);
                show.play();
            });

        }catch(IllegalAccessException |
                NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private void setupMain() {
        tablecolumn_main_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablecolumn_main_name.setCellValueFactory(new PropertyValueFactory<>("name"));
    }

    private void setupTasks() {
        tablecolumn_tasks_task_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        tablecolumn_tasks_task_description.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void setupTopTasks() {

        // In order to not remake the same PropertyValueFactory on every call
        PropertyValueFactory<Sarcina, Integer> idFactory = new PropertyValueFactory<>("id");
        PropertyValueFactory<Sarcina, String> descFactory = new PropertyValueFactory<>("description");

        tablecolumn_top_tasks_task_id.setCellValueFactory((param) ->
                idFactory.call(new TableColumn.CellDataFeatures<>(
                    null, null, param.getValue().getKey())));

        tablecolumn_top_tasks_task_name.setCellValueFactory((param) ->
                descFactory.call(new TableColumn.CellDataFeatures<>(
                    null, null, param.getValue().getKey())));

        // Here we have the same PropertyValueFactory every time we call the CellValueFactory
        tablecolumn_top_tasks_appearance_count.setCellValueFactory(new PropertyValueFactory<>("value"));
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.show();
    }

    private void showError(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }

    @Override
    public void setController(FisaPostController _controller) {
        controller = _controller;
        tableview_main.setItems(controller.getPositionsModel());
    }

}
