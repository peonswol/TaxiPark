package com.taxipark;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage stage){
        try {
            stage.setResizable(false);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/com/taxipark/image/taxi.png")).toExternalForm()));

            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/com/taxipark/gui/login.fxml"));
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