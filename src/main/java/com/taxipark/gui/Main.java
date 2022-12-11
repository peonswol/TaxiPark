package com.taxipark.gui;

import com.taxipark.gui.component.ConnectToDataBase;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        try {
            stage.setResizable(false);
            stage.getIcons().add(new Image("C:\\Users\\bilda\\TaxiPark\\TaxiPark\\src\\main\\resources\\com\\taxipark\\content\\taxi.png"));

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Welcome!");
            stage.setScene(scene);
            stage.show();

        } catch (Exception exception){
            exception.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }

}