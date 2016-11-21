package com.company.View;

import com.company.Controller.SarcinaController;
import com.company.Domain.Post;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AlexandruD on 11/20/2016.
 */
public class SarcinaView implements Initializable, View<SarcinaController> {

    private SarcinaController controller;

    @FXML
    public TableView<Post> main_table;

    @FXML
    public TableColumn<Post, Integer> main_table_column_id;

    @FXML
    public TableColumn<Post, String> main_table_column_name;

    @FXML
    public TableColumn<Post, Post.Type> main_table_column_type;

    @FXML
    public TextField textfield_main_id,
            textfield_main_name,
            textfield_add_id,
            textfield_add_name,
            textfield_update_id,
            textfield_update_name;

    @FXML
    public ComboBox<Post.Type> combobox_main_type,
            combobox_add_type,
            combobox_update_type;

    @FXML
    public Button btn_add_ok,
            btn_add_cancel,
            btn_main_add,
            btn_main_update,
            btn_main_remove,
            btn_update_ok,
            btn_update_cancel;

    @FXML
    public StackPane root;

    @FXML
    public AnchorPane anchor_add,
            anchor_update;


    @Override
    public void initialize(URL location, ResourceBundle resourceBundle) {
        prepTransitions();
    }

    @Override
    public void setController(SarcinaController controller) {
        this.controller = controller;
    }

    private void prepTransitions() {

        final int X_OFFSET = 510;

        TranslateTransition openAdd = new TranslateTransition(new Duration(350), anchor_add);
        openAdd.setByX(-1 * X_OFFSET);

        TranslateTransition closeAdd = new TranslateTransition(new Duration(350), anchor_add);
        closeAdd.setByX(X_OFFSET);

        btn_main_add.setOnMouseClicked((event) -> {clearFields("add"); openAdd.play();});
        btn_add_cancel.setOnMouseClicked((event) -> {closeAdd.play(); clearFields("add");});
        btn_add_ok.setOnMouseClicked((event) -> {closeAdd.play(); clearFields("add");});


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
        btn_update_ok.setOnMouseClicked((event) -> {updClose.play(); clearFields("update");});
    }

    private void clearFields(String context) {
        String  textfieldId = "textfield_"+context+"_id",
                textfieldName = "textfield_"+context+"_name",
                comboboxType = "combobox_"+context+"_type";

        try {
            TextField idField = (TextField) this.getClass().getField(textfieldId).get(this),
                    nameField = (TextField) this.getClass().getField(textfieldName).get(this);

            ComboBox<Post.Type> comboBox = (ComboBox) this.getClass().getField(comboboxType).get(this);

            idField.setText("");
            nameField.setText("");
            comboBox.getSelectionModel().selectFirst();

        }catch(NoSuchFieldException e) {
            e.printStackTrace();
        }catch(IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String message, Alert.AlertType type) {
        Alert alert = new Alert(type, message, ButtonType.OK);
        alert.show();
    }

    private void showError(String message) {
        showAlert(message, Alert.AlertType.ERROR);
    }


}
