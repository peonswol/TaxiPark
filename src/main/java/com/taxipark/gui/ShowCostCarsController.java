package com.taxipark.gui;

import com.taxipark.gui.component.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ShowCostCarsController implements Initializable {

    @FXML
    private Label sum;

    @FXML
    private Label kst;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<Car> cars = Car.getCarsFromDB();

            kst.setText(String.valueOf(cars.size()));

            double sumAllCar = 0;

            for (Car car : cars) {
                sumAllCar += car.getCost();
            }

            sum.setText(String.valueOf(sumAllCar));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onCallMenuButtonClick(ActionEvent event){
        try {
            LoginController loginController = new LoginController();
            loginController.openMenu(event);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
