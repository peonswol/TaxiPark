package com.taxipark.gui;

import com.taxipark.gui.component.ConnectToDataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainMenuController {

    @FXML
    Label loginPerson;

    @FXML
    Label positionPerson;

    Connection connection = ConnectToDataBase.getConnection();

    public void displayLoginAndPosition(String login){

        String returnPosition = "select \"Admins\".\"AdminPosition\"\n" +
                "from \"Admins\"\n" +
                "where \"Admins\".\"AdminLogin\" = '"+ login +"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(returnPosition);

            while (resultQuery.next()) {
                loginPerson.setText("Ваш логін - " + login);
                positionPerson.setText("Ваша посада - " + resultQuery.getString(1));
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void setAlert(Alert.AlertType alertType, String headerText, String contentText){

        try {
            Alert alert = new Alert(alertType);
            alert.setTitle("Інформація про запис авто");
            alert.setHeaderText(headerText);
            alert.setContentText(contentText);
            alert.show();

        }catch (Exception exception){
            exception.printStackTrace();
        }
    }

    //todo try
    public void onAddNewCarButtonClick(ActionEvent event){
        try {
            openNewScene(event, "addNewCar.fxml");
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    public void onShowCostCarsButtonClick(ActionEvent event) {
        try {
            openNewScene(event, "showCostCars.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onSearchCarByIDButtonClick(ActionEvent event) {
        try {
            openNewScene(event, "searchCarByID.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onShowListCarsButtonClick(ActionEvent event) {
        try {
            openNewScene(event, "showListCars.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAddNewAdminButtonClick(ActionEvent event) {
        try {
            openNewScene(event, "addNewAdmin.fxml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openNewScene(ActionEvent event, String newScene){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(newScene));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
