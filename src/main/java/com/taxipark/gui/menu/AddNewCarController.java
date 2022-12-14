package com.taxipark.gui.menu;

import com.taxipark.component.car.CheckingCar;
import com.taxipark.component.ConnectToDataBase;
import com.taxipark.gui.LoginController;
import com.taxipark.gui.MainMenuController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

import static com.taxipark.gui.MainMenuController.setAlert;

public class AddNewCarController implements Initializable {

    @FXML
    private Button saveDataButton;

    @FXML
    private Button addAnotherCarButton;

    @FXML
    private TextField carVIN;

    @FXML
    private TextField markAndModelCar;

    @FXML
    private TextField costCar;

    @FXML
    private TextField colorCar;

    @FXML
    private TextField maxSpeedCar;

    @FXML
    private TextField transmissionCar;

    @FXML
    private TextField driveTypeCar;

    @FXML
    private TextField engineTypeCar;

    @FXML
    private TextField stateCar;

    @FXML
    private TextArea securityTypesCar;

    @FXML
    private TextArea comfortTypesCar;

    @FXML
    private Spinner<Integer> yearManufactureCar;

    @FXML
    private Spinner<Double> engineCapacityCar;

    @FXML
    private Spinner<Double> fuelConsumptionFor100kmCar;

    @FXML
    private ChoiceBox<String> fuelTypeCar;

    private final Connection connection = ConnectToDataBase.getConnection();

    private final CheckingCar checkingCar = new CheckingCar();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            fuelTypeCar.getItems().addAll("А-100", "А-98", "А-95 Energy", "А-95", "А-92 Energy", "ДТ Energy", "ГАЗ");
            yearManufactureCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2022));
            engineCapacityCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 6, 1, 0.1));
            fuelConsumptionFor100kmCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 20, 1, 0.1));

            yearManufactureCar.getValueFactory().setValue(2000);
            engineCapacityCar.getValueFactory().setValue(3.0);
            fuelConsumptionFor100kmCar.getValueFactory().setValue(10.0);
        }catch (Exception exception) {
            exception.printStackTrace();
        }

    }

    public void onCallMenuButtonClick(ActionEvent event) {
        try {
            LoginController loginController = new LoginController();
            loginController.openMenu(event);
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }


    private boolean allDataIsCorrectSize() {
        try {
            return checkingCar.checkSizeVIN(carVIN.getText())
                    && checkingCar.checkSizeMark(markAndModelCar.getText())
                    && yearManufactureCar != null
                    && checkingCar.stringIsDouble(costCar.getText())
                    && checkingCar.checkSizeColor(colorCar.getText())
                    && checkingCar.stringIsDouble(maxSpeedCar.getText())
                    && !transmissionCar.getText().isEmpty()
                    && !driveTypeCar.getText().isEmpty()
                    && fuelTypeCar.getValue() != null
                    && !engineTypeCar.getText().isEmpty()
                    && engineCapacityCar.getValue() != null
                    && fuelConsumptionFor100kmCar.getValue() != null
                    && !stateCar.getText().isEmpty()
                    && !securityTypesCar.getText().isEmpty()
                    && !comfortTypesCar.getText().isEmpty();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private boolean allDataIsCorrect() {
        try {
            if (!allDataIsCorrectSize()){
                setAlert(Alert.AlertType.ERROR, "Введіть ВСІ дані і коректного розміру!", "Спробуйте ще раз, перевіривши розмір вводу або заповнивши пусті поля.\n*****\nVIN - 10\nмарка - <= 50\nколір - <= 20");
                return false;
            }
            if (!checkingCar.isNotVINInDB(carVIN.getText())){
                setAlert(Alert.AlertType.ERROR, "Такий VIN вже існує!", "Авто з введеним номером уже записаний");
                return false;
            }/*
            if (!checkingAdmin.checkCorrectDate(dateBirth.getValue(), dateEmployment.getValue())){
                setMessage("Введіть коректні дати!");
                return false;
            }*/
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    public void onSaveDataButtonClick(ActionEvent event) {

        try {
            if (allDataIsCorrect()){
                if(addDataInDataBase()) {
                    setAlert(Alert.AlertType.INFORMATION, "Авто успішно додано)", "");
                    saveDataButton.setVisible(false);
                    addAnotherCarButton.setVisible(true);
                    clearAndDisableAllEntering();
                }else {
                    setAlert(Alert.AlertType.ERROR, "Виникла помилка при записі!", "Спробуйте ще раз, перевіривши коректність запитів.");
                }
            }
        }catch (Exception exception){
            exception.printStackTrace();
            setAlert(Alert.AlertType.ERROR, "Виникла помилка при записі!", "Спробуйте ще раз, перевіривши коректність запитів.");
        }
    }

    public void onAddAnotherCarButtonClick(ActionEvent event){
        try {
            MainMenuController mainMenuController = new MainMenuController();
            mainMenuController.openNewScene(event, "addNewCar.fxml");
        }catch (Exception exception){
            exception.printStackTrace();
        }

    }

    private boolean addDataInDataBase() {

        try {
            return insertGeneral() &&
                    insertTechnic() &&
                    insertFuel() &&
                    insertMoreInfo() &&
                    insertCar();
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return false;
    }

    private boolean insertGeneral(){

        String insertGeneral = "insert into \"GeneralInfoTable\" (" +
                "                \"MarkAndModel\", " +
                "                \"YearManufacture\", " +
                "                \"Cost\", " +
                "                \"Color\", " +
                "                \"MaxSpeed\" " +
                "        ) values(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertGeneral);
            preparedStatement.setString(1, markAndModelCar.getText());
            preparedStatement.setInt(2, yearManufactureCar.getValue());
            preparedStatement.setDouble(3, Double.parseDouble(costCar.getText()));
            preparedStatement.setString(4, colorCar.getText());
            preparedStatement.setDouble(5, Double.parseDouble(maxSpeedCar.getText()));
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
            preparedStatement.setDouble(3, engineCapacityCar.getValue());
            preparedStatement.setDouble(4, fuelConsumptionFor100kmCar.getValue());
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


    private int getGeneralInfoID() {

        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"GeneralInfoID\")\n" +
                "from \"GeneralInfoTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            if (resultQuery.next()) {
                return resultQuery.getInt(1);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return 0;
    }

    private int getTechnicInfoID(){
        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"TechnicInfoID\")\n" +
                "from \"TechnicInfoTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            if (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int getFuelInfoID(){
        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"FuelInfoID\")\n" +
                "from \"FuelInfoTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            if (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int getMoreInformationID(){
        Connection connection = ConnectToDataBase.getConnection();

        String checkIsLogin = "select max(\"MoreInformationID\")\n" +
                "from \"MoreInformationTable\"";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);
            if (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private boolean insertCar(){

        String insertCar = "insert into \"CarTable\" (" +
                "                \"GeneralInfoID\", " +
                "                \"TechnicInfoID\", " +
                "                \"FuelInfoID\", " +
                "                \"MoreInformationID\", " +
                "                \"CarVIN\" " +
                "        ) values(?,?,?,?,?)";

        int generalInfoID = getGeneralInfoID();
        int technicInfoID = getTechnicInfoID();
        int fuelInfoID = getFuelInfoID();
        int moreInformationID = getMoreInformationID();

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(insertCar);
            preparedStatement.setInt(1, generalInfoID);
            preparedStatement.setInt(2, technicInfoID);
            preparedStatement.setInt(3, fuelInfoID);
            preparedStatement.setInt(4, moreInformationID);
            preparedStatement.setString(5, carVIN.getText());
            preparedStatement.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void clearAndDisableAllEntering(){
        carVIN.setDisable(true);
        carVIN.clear();

        markAndModelCar.setDisable(true);
        markAndModelCar.clear();

        yearManufactureCar.getValueFactory().setValue(2000);
        yearManufactureCar.setDisable(true);

        costCar.setDisable(true);
        costCar.clear();

        colorCar.setDisable(true);
        colorCar.clear();

        maxSpeedCar.setDisable(true);
        maxSpeedCar.clear();

        transmissionCar.setDisable(true);
        transmissionCar.clear();

        driveTypeCar.setDisable(true);
        driveTypeCar.clear();

        fuelTypeCar.setValue(null);
        fuelTypeCar.setDisable(true);

        engineTypeCar.setDisable(true);
        engineTypeCar.clear();

        engineCapacityCar.getValueFactory().setValue(3.0);
        engineCapacityCar.setDisable(true);

        fuelConsumptionFor100kmCar.getValueFactory().setValue(10.0);
        fuelConsumptionFor100kmCar.setDisable(true);

        stateCar.setDisable(true);
        stateCar.clear();

        securityTypesCar.setDisable(true);
        securityTypesCar.clear();

        comfortTypesCar.setDisable(true);
        comfortTypesCar.clear();
    }
}
