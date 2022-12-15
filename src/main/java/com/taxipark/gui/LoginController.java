package com.taxipark.gui;

import com.taxipark.component.CheckingAdmin;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginPerson;

    @FXML
    private PasswordField passwordPerson;

    @FXML
    private Label messageInfo;

    public static String admin;

    @FXML
    protected void onLoginButtonClick(ActionEvent event) {

        admin = loginPerson.getText();

        CheckingAdmin checkingAdmin = new CheckingAdmin();

        try {

            if (admin.isEmpty() || passwordPerson.getText().isEmpty()) {
                throw new Exception();
            }

            if (checkingAdmin.isAdminInDB(loginPerson.getText(), passwordPerson.getText())) {
                openMenu(event);
            } else {
                throw new Exception();
            }

        } catch (Exception e) {
            messageInfo.setVisible(true);
        }
    }

    public void openMenu(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainMenu.fxml"));
        try {
            Parent root = loader.load();

            MainMenuController mainMenuController = loader.getController();
            mainMenuController.displayLoginAndPosition(admin);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}