package com.taxipark.gui.menu;

import com.taxipark.component.car.Car;
import com.taxipark.component.car.CarInDataBase;
import com.taxipark.gui.LoginController;
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

    @FXML
    private Label carsImg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            List<Car> cars = CarInDataBase.getCarsFromDB();

            StringBuilder showCars = new StringBuilder();

            double sumAllCar = 0;

            for (Car car : cars) {
                sumAllCar += car.getCost();
                showCars.append("🚗 ");
            }

            sum.setText(String.valueOf(sumAllCar));
            kst.setText(String.valueOf(cars.size()));
            carsImg.setText(showCars.toString());

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
