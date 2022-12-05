package com.taxipark.gui;

import com.taxipark.gui.component.ConnectToDataBase;
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
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginController {
    @FXML
    private TextField loginPerson;

    @FXML
    private PasswordField passwordPerson;

    @FXML
    private Label infoLogin;

    public static String admin;

    @FXML
    protected void onLoginClick(ActionEvent event) {

        admin = loginPerson.getText();

        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select count(*)\n" +
                "from \"admins\"\n" +
                "where \"admins\".\"adminLogin\" = '"+admin+
                "' and \"admins\".\"adminPassword\" = '"+passwordPerson.getText()+"'";


        try {

            if (admin.isEmpty() || passwordPerson.getText().isEmpty()){
                throw new Exception();
            }

            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);

            while (resultQuery.next()) {
                if (resultQuery.getInt(1) == 1){
                    openMenu(event);
                }
                else {
                    throw new Exception();
                }

            }
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }catch (Exception e){
            infoLogin.setText("Дані введено неправильно!\nСпробуйте ще раз)");
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