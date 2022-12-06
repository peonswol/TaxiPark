package com.taxipark.gui;

import com.taxipark.gui.component.ConnectToDataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainMenuController {

    @FXML
    Button addNewCar;

    @FXML
    Button showListCars;

    @FXML
    Button searchCarByID;

    @FXML
    Button addNewAdmin;

    @FXML
    Button showCostCars;

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

    //todo try
    public void onAddNewCarClick(ActionEvent event){
        try {
            openNewScene(event, "addNewCar.fxml");
        }
        catch (IllegalStateException exception){
            exception.printStackTrace();
        }
    }

    public void onShowCostCarsClick(ActionEvent event){
        openNewScene(event, "showCostCars.fxml");
    }

    public void onSearchCarByIDClick(ActionEvent event){
        openNewScene(event, "searchCarByID.fxml");
    }

    public void onShowListCarsClick(ActionEvent event){
        openNewScene(event, "showCostCars.fxml");
    }

    public void onAddNewAdminClick(ActionEvent event){
        openNewScene(event, "addNewAdmin.fxml");
    }

    private void openNewScene(ActionEvent event, String newScene){
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
