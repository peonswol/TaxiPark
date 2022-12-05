package com.taxipark.gui;

import com.taxipark.gui.component.ConnectToDataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class AddNewCarController implements Initializable {

    @FXML
    Label labelInfo;

    @FXML
    Button callMenu;

    @FXML
    Button saveData;

    @FXML
    TextField markAndModelCar;

    @FXML
    TextField costCar;

    @FXML
    TextField colorCar;

    @FXML
    TextField maxSpeedCar;

    @FXML
    TextField transmissionCar;

    @FXML
    TextField driveTypeCar;

    @FXML
    TextField engineTypeCar;


    @FXML
    TextField stateCar;


    @FXML
    TextArea securityTypesCar;


    @FXML
    TextArea comfortTypesCar;

    @FXML
    Spinner<Integer> yearManufactureCar;

    @FXML
    Spinner<Double> engineCapacityCar;

    @FXML
    Spinner<Double> fuelConsumptionFor100kmCar;

    @FXML
    ChoiceBox<String> fuelTypeCar;

    Connection connection = ConnectToDataBase.getConnection();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fuelTypeCar.getItems().addAll("А-98", "А-98", "А-95 Energy", "А-95", "А-92 Energy", "ДТ Energy", "ГАЗ");
        yearManufactureCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2022));
        engineCapacityCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 3));
        fuelConsumptionFor100kmCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1970, 2022));

    }

    private boolean allDataIsNotEmpty(){
        return !markAndModelCar.getText().isEmpty()
                && yearManufactureCar.getValue() != 0
                && !costCar.getText().isEmpty()
                && !colorCar.getText().isEmpty()
                && !maxSpeedCar.getText().isEmpty()
                && !transmissionCar.getText().isEmpty()
                && !driveTypeCar.getText().isEmpty()
                && fuelTypeCar.getValue() != null
                && !engineTypeCar.getText().isEmpty()
                && engineCapacityCar.getValue() != 0
                && fuelConsumptionFor100kmCar.getValue() != 0
                && !stateCar.getText().isEmpty()
                && !securityTypesCar.getText().isEmpty()
                && !comfortTypesCar.getText().isEmpty();
    }

    public void onCallMenuClick(ActionEvent event) {
        LoginController loginController = new LoginController();
        loginController.openMenu(event);
    }

    public void onSaveDataClick(ActionEvent event) {
        if (!allDataIsNotEmpty()){
            labelInfo.setText("Введіть ВСІ дані коректно");
        }
        else {
            //todo check writing
            addDataInDataBase();
            labelInfo.setText("Користувача успішно додано");
        }
    }

    private boolean addDataInDataBase() {

        return insertGeneral() &&
                insertTechnic() &&
                insertFuel() &&
                insertMoreInfo() &&
                insertCar();
    }


    private boolean insertGeneral(){

        String insertGeneral = "insert into \"GeneralInfoTable\"(" +
                "                \"MarkAndModel\", " +
                "                \"YearManufacture\", " +
                "                \"Cost\", " +
                "                \"Color\", " +
                "                \"MaxSpeed\" " +
                "        ) values(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertGeneral);
            preparedStatement.setString(1, markAndModelCar.getText());
            preparedStatement.setString(2, yearManufactureCar.getValue().toString());
            preparedStatement.setString(3, costCar.getText());
            preparedStatement.setString(4, colorCar.getText());
            preparedStatement.setString(5, maxSpeedCar.getText());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertTechnic(){

        String insertTechnic = "insert into \"TechnicInfoTable\"(" +
                "                \"Transmission\", " +
                "                \"DriveType\" " +
                "        ) values(?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertTechnic);
            preparedStatement.setString(1, transmissionCar.getText());
            preparedStatement.setString(2, driveTypeCar.getText());

            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertFuel(){

        String insertFuel = "insert into \"FuelInfoTable\"(" +
                "                \"FuelType\", " +
                "                \"EngineType\", " +
                "                \"EngineCapacity\", " +
                "                \"FuelConsumptionFor100km\" " +
                "        ) values(?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertFuel);
            preparedStatement.setString(1, fuelTypeCar.getValue());
            preparedStatement.setString(2, engineTypeCar.getText());
            preparedStatement.setString(3, engineCapacityCar.getValue().toString());
            preparedStatement.setString(4, fuelConsumptionFor100kmCar.getValue().toString());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean insertMoreInfo(){

        String insertMoreInfo = "insert into \"MoreInformationTable\"(" +
                "                \"State\", " +
                "                \"SecurityTypes\", " +
                "                \"ComfortTypes\" " +
                "        ) values(?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertMoreInfo);
            preparedStatement.setString(1, stateCar.getText());
            preparedStatement.setString(2, securityTypesCar.getText());
            preparedStatement.setString(3, comfortTypesCar.getText());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    private String getGeneralInfoID() {

        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"GeneralInfoID\")\n" +
                "from \"GeneralInfoTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            return resultQuery.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getTechnicInfoID(){
        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"TechnicInfoID\")\n" +
                "from \"TechnicInfoTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            return resultQuery.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getFuelInfoID(){
        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"FuelInfoID\")\n" +
                "from \"FuelInfoTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            return resultQuery.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String getMoreInformationID(){
        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"MoreInformationID\")\n" +
                "from \"MoreInformationTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            return resultQuery.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean insertCar(){

        String insertCar = "insert into \"CarTable\" (" +
                "                \"GeneralInfoID\", " +
                "                \"TechnicInfoID\", " +
                "                \"FuelInfoID\", " +
                "                \"MoreInformationID\" " +
                "        ) values(?,?,?,?)";

        String generalInfoID = getGeneralInfoID();
        String technicInfoID = getTechnicInfoID();
        String fuelInfoID = getFuelInfoID();
        String moreInformationID = getMoreInformationID();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertCar);
            preparedStatement.setString(1, generalInfoID);
            preparedStatement.setString(2, technicInfoID);
            preparedStatement.setString(3, fuelInfoID);
            preparedStatement.setString(4, moreInformationID);
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
