package com.company.View;

import com.company.Controller.PostController;
import com.company.Domain.Post;
import com.company.Utils.Exceptions.ElementExistsException;
import com.company.Utils.Exceptions.ElementNotFoundException;
import com.sun.javafx.collections.ObservableListWrapper;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import javax.xml.bind.ValidationException;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by AlexandruD on 11/20/2016.
 */
public class PostView implements Initializable, View<PostController> {

    private PostController controller;

    @FXML
    private TableView<Post> main_table;

    @FXML
    private TableColumn<Post, Integer> main_table_column_id;

    @FXML
    private TableColumn<Post, String> main_table_column_name;

    @FXML
    private TableColumn<Post, Post.Type> main_table_column_type;

    @FXML
    private TextField textfield_main_id,
                     textfield_main_name,
                     textfield_add_id,
                     textfield_add_name,
                     textfield_update_id,
                     textfield_update_name,
                     textfield_main_filter_substring;

    @FXML
    private ComboBox<Post.Type> combobox_main_type,
                               combobox_add_type,
                               combobox_update_type,
                               combobox_main_filter_type;

    @FXML
    private Button btn_add_ok,
                  btn_add_cancel,
                  btn_main_add,
                  btn_main_update,
                  btn_main_remove,
                  btn_update_ok,
                  btn_update_cancel,
                  btn_main_clear_filter;

    @FXML
    private AnchorPane anchor_add,
                      anchor_update;

    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        setupTable();

        btn_main_remove.setOnMouseClicked((event) -> removePosition());
        btn_main_clear_filter.setOnMouseClicked((event) -> clearFilter());
        textfield_main_filter_substring.setOnKeyReleased((event) -> filter());

        prepTransitions();
        setupComboBoxes();
    }

    public PostView() {}

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.show();
    }

    private void showError(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }

    private void setupComboBoxes() {
        ObservableListWrapper<Post.Type> wrapper = new ObservableListWrapper<>(Arrays.asList(Post.Type.values()));
        combobox_add_type.setItems(wrapper);
        combobox_main_type.setItems(wrapper);
        combobox_update_type.setItems(wrapper);
        combobox_main_filter_type.setItems(wrapper);

        combobox_main_filter_type.getSelectionModel().selectedItemProperty().addListener((event) ->
                filter());
    }

    private void filter() {
        String substr = textfield_main_filter_substring.getText();
        Post.Type tp = combobox_main_filter_type.getSelectionModel().getSelectedItem();
        Optional<String> substring;
        Optional<Post.Type> type;

        if(substr.equals(""))
            substring = Optional.empty();
        else
            substring = Optional.of(substr);

        if(tp == null) {
            type = Optional.empty();
        }
        else
            type = Optional.of(tp);

        controller.filter(type, substring);
    }

    private void clearFilter() {
        textfield_main_filter_substring.clear();
        combobox_main_filter_type.getSelectionModel().select(-1);
        controller.filter(Optional.empty(), Optional.empty());
    }

    private void prepTransitions() {

        final int X_OFFSET = 510;

        TranslateTransition openAdd = new TranslateTransition(new Duration(350), anchor_add);
        openAdd.setByX(-1 * X_OFFSET);

        TranslateTransition closeAdd = new TranslateTransition(new Duration(350), anchor_add);
        closeAdd.setByX(X_OFFSET);

        btn_main_add.setOnMouseClicked((event) -> {clearFields("add"); openAdd.play();});
        btn_add_cancel.setOnMouseClicked((event) -> {closeAdd.play(); clearFields("add");});
        btn_add_ok.setOnMouseClicked((event) -> {addPositiom(); closeAdd.play(); clearFields("add");});

        TranslateTransition updOpen = new TranslateTransition(new Duration(350), anchor_update);
        updOpen.setByX(-1 * X_OFFSET);

        TranslateTransition updClose = new TranslateTransition(new Duration(350), anchor_update);
        updClose.setByX(X_OFFSET);

        btn_main_update.setOnMouseClicked((event) -> {

            if(textfield_main_id.getText().equals("")) {
                showError("Select an item before updating");
                return;
            }

            clearFields("update");
            textfield_update_id.setText(textfield_main_id.getText());
            textfield_update_name.setText(textfield_main_name.getText());
            combobox_update_type
                    .getSelectionModel().select(combobox_main_type.getSelectionModel().getSelectedItem());
            updOpen.play();
        });
        btn_update_cancel.setOnMouseClicked((event) -> {updClose.play(); clearFields("update");});
        btn_update_ok.setOnMouseClicked((event) -> {updatePosition(); updClose.play(); clearFields("update");});

    }

    public void setController(PostController controller) {
        this.controller = controller;
        main_table.setItems(controller.getFilterModel());
    }

    private void setupTable() {
        main_table_column_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        main_table_column_name.setCellValueFactory(new PropertyValueFactory<>("name"));
        main_table_column_type.setCellValueFactory(new PropertyValueFactory<>("type"));

        main_table.getSelectionModel().selectedItemProperty().addListener(
                (observable, old, newV) -> displayPosition(newV));
    }

    public void displayPosition(Post p) {

        if(p == null) {
            clearFields("main");
            return;
        }

        textfield_main_id.setText(Integer.toString(p.getId()));
        textfield_main_name.setText(p.getName());
        combobox_main_type.getSelectionModel().select(p.getType());
    }

    public void addPositiom() {

        if(textfield_add_id.getText().equals("") || textfield_add_name.getText().equals("")) {
            showError("Please provide data in the textfields");
            return;
        }

        try {

            Post p = retrievePost("add").get();
            controller.add(p.getId(), p.getName(), p.getType());

        }catch(ValidationException e) {
            showError(e.getMessage());
        }catch(ElementExistsException e) {
            showError("The element already exists");
        }catch(NullPointerException e) {
            e.printStackTrace();
        }

    }

    private void updatePosition() {

        try {

            Post p = retrievePost("update").get();
            controller.update(p.getId(), p.getName(), p.getType());

        }catch(ValidationException e) {
            showError(e.getMessage());
        }catch(ElementNotFoundException e) {
            showError("The element already exists");
        }

    }

    private void removePosition() {

        int id;
        if(textfield_main_id.getText().equals(""))
            showError("An item must be first selected");

        id = Integer.parseInt(textfield_main_id.getText());
        try {
            controller.remove(id);
        }catch(ElementNotFoundException e) {
            showError("Item not found");
        }

    }

    private void clearFields(String context) {
        String  textfieldId = "textfield_"+context+"_id",
                textfieldName = "textfield_"+context+"_name",
                comboboxType = "combobox_"+context+"_type";

        try {
            TextField idField = (TextField) this.getClass().getDeclaredField(textfieldId).get(this),
                    nameField = (TextField) this.getClass().getDeclaredField(textfieldName).get(this);

            ComboBox<Post.Type> comboBox = (ComboBox) this.getClass().getDeclaredField(comboboxType).get(this);

            idField.setText("");
            nameField.setText("");
            comboBox.getSelectionModel().selectFirst();

        }catch(NoSuchFieldException e) {
            e.printStackTrace();
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Optional<Post> retrievePost(String context) {
        int id;
        String name;
        Post.Type type;

        String  textfieldId = "textfield_"+context+"_id",
                textfieldName = "textfield_"+context+"_name",
                comboboxType = "combobox_"+context+"_type";

        try {
            TextField idField = (TextField) this.getClass().getDeclaredField(textfieldId).get(this),
                    nameField = (TextField) this.getClass().getDeclaredField(textfieldName).get(this);

            ComboBox<Post.Type> comboBox = (ComboBox) this.getClass().getDeclaredField(comboboxType).get(this);

            if(idField.getText().equals("") || nameField.getText().equals("")) {
                showError("Please provide data in the textfields");
                return Optional.empty();
            }

            id = Integer.parseInt(idField.getText());
            name = nameField.getText();
            type = comboBox.getSelectionModel().getSelectedItem();

            return Optional.of(new Post(id, name, type));

        }catch(NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }catch(NumberFormatException e) {
            showAlert("Invalid data passed into the id field", Alert.AlertType.ERROR);
            return Optional.empty();
        }

        return Optional.empty();
    }

}
