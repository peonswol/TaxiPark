package com.taxipark.gui;

import com.taxipark.gui.component.Car;
import com.taxipark.gui.component.ConnectToDataBase;
import com.taxipark.gui.component.DeleteCar;
import com.taxipark.gui.component.EditCarData;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchCarByIDController implements Initializable {

    @FXML
    private TableView<StringTable> table;

    @FXML
    private TableColumn<StringTable, String> nameParameterColumn;

    @FXML
    private TableColumn<StringTable, String> characteristicColumn;

    @FXML
    private ChoiceBox<Integer> selectListCar;

    @FXML
    private ChoiceBox<String> selectListCharacteristic;

    @FXML
    private Button searchCarButton;

    @FXML
    private Button editCarDataButton;

    @FXML
    private Button deleteCarButton;

    @FXML
    private Button saveChangeButton;

    @FXML
    private Button goBackButton;

    @FXML
    private Button goBackwitoutSavingButton;

    @FXML
    private Button resetEditThisCharacteristicButton;

    @FXML
    private Button chooseCharacteristicButton;

    @FXML
    private Label labelInfo;

    @FXML
    private Label messageLabelMain;

    @FXML
    private TextField enteringData;

    @FXML
    Spinner<Integer> enteringYearManufactureCar;

    @FXML
    Spinner<Double> enteringEngineCapacityCar;

    @FXML
    Spinner<Double> enteringFuelConsumptionFor100kmCar;

    @FXML
    ChoiceBox<String> enteringFuelTypeCar;

    @FXML
    Label messageLabel;

    private List<Car> cars;

    private List<String> listCharacteristic;

    private Car carSelectedByID;

    public class StringTable{
        String nameParameterColumn;
        String characteristicColumn;

        public StringTable(String nameParameterColumn, String characteristicColumn) {
            this.nameParameterColumn = nameParameterColumn;
            this.characteristicColumn = characteristicColumn;
        }
    //todo get

    }

    private List<Car> getCarsFromDB(){

        Connection connection = ConnectToDataBase.getConnection();

        String getCars =
                """
                        select car."CarID", car."CarVIN", "general"."MarkAndModel","general"."YearManufacture", "general"."Cost", "general"."Color", "general"."MaxSpeed", "technic"."Transmission", "technic"."DriveType", "fuel"."FuelType", "fuel"."EngineType", "fuel"."EngineCapacity", "fuel"."FuelConsumptionFor100km", "moreInfo"."State", "moreInfo"."SecurityTypes", "moreInfo"."ComfortTypes"
                        from "CarTable" as "car"
                        inner join "GeneralInfoTable" as "general"
                        on "car"."GeneralInfoID" = "general"."GeneralInfoID"
                        inner join "FuelInfoTable" as "fuel"
                        on "car"."FuelInfoID" = "fuel"."FuelInfoID"
                        inner join "TechnicInfoTable" as "technic"
                        on "car"."TechnicInfoID" = "technic"."TechnicInfoID"
                        inner join "MoreInformationTable" as "moreInfo"
                        on "car"."MoreInformationID" = "moreInfo"."MoreInformationID\"""";

        try {

            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(getCars);
            Car tempCar;
            List<Car> cars = new ArrayList<>();

            while (result.next()) {
                tempCar = new Car();
                tempCar.setCarID(result.getInt("CarID"));
                tempCar.setCarVIN(result.getString("CarVIN"));

                tempCar.setMarkAndModel(result.getString("MarkAndModel"));
                tempCar.setYearManufacture(result.getInt("YearManufacture"));
                tempCar.setCost(result.getDouble("Cost"));
                tempCar.setColor(result.getString("Color"));
                tempCar.setMaxSpeed(result.getDouble("MaxSpeed"));

                tempCar.setFuelType(result.getString("FuelType"));
                tempCar.setEngineType(result.getString("EngineType"));
                tempCar.setEngineCapacity(result.getDouble("EngineCapacity"));
                tempCar.setFuelConsumptionFor100km(result.getDouble("FuelConsumptionFor100km"));

                tempCar.setTransmission(result.getString("Transmission"));
                tempCar.setDriveType(result.getString("DriveType"));

                tempCar.setState(result.getString("State"));
                tempCar.setSecurityTypes(result.getString("SecurityTypes"));
                tempCar.setComfortTypes(result.getString("ComfortTypes"));

                cars.add(tempCar);
            }

            return cars;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            cars = getCarsFromDB();

            if (cars.size() == 0) {
                caseNoCar();
            } else {

                setListCharacteristic();

                for (Car car : cars) {
                    selectListCar.getItems().add(car.getCarID());
                }

                for (int i = 1; i < listCharacteristic.size(); i++) {
                    selectListCharacteristic.getItems().add(listCharacteristic.get(i));
                }

                enteringFuelTypeCar.getItems().addAll("А-100", "А-98", "А-95 Energy", "А-95", "А-92 Energy", "ДТ Energy", "ГАЗ");
                enteringYearManufactureCar.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(1970, 2022));
                enteringEngineCapacityCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 3, 1, 0.1));
                enteringFuelConsumptionFor100kmCar.setValueFactory(new SpinnerValueFactory.DoubleSpinnerValueFactory(1, 20, 1, 0.1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void caseNoCar() {
        try {
            deleteCarButton.setDisable(true);
            editCarDataButton.setDisable(true);
            saveChangeButton.setDisable(true);
            selectListCar.setDisable(true);
            setMainMessage("Авто немає!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setListCharacteristic() {
        try {
            listCharacteristic = new ArrayList<>();

            listCharacteristic.add("ID");
            listCharacteristic.add("VIN");
            listCharacteristic.add("Марка і модель");
            listCharacteristic.add("Рік збірки");
            listCharacteristic.add("Вартість");
            listCharacteristic.add("Колір");
            listCharacteristic.add("Максимальна швидкість");
            listCharacteristic.add("Коробка передач");
            listCharacteristic.add("Тип приводу");
            listCharacteristic.add("Тип пального");
            listCharacteristic.add("Тип двигуна");
            listCharacteristic.add("Об'єм двигуна");
            listCharacteristic.add("Витрати пального за 100 км");
            listCharacteristic.add("Стан");
            listCharacteristic.add("Типи безпеки");
            listCharacteristic.add("Типи комфорту");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<StringTable> createListToWrite(){

        List<StringTable> stringTableList = new ArrayList<>();

        try {

            stringTableList.add(new StringTable(listCharacteristic.get(0), String.valueOf(carSelectedByID.getCarID())));
            stringTableList.add(new StringTable(listCharacteristic.get(1), carSelectedByID.getCarVIN()));
            stringTableList.add(new StringTable(listCharacteristic.get(2), carSelectedByID.getMarkAndModel()));
            stringTableList.add(new StringTable(listCharacteristic.get(3), String.valueOf(carSelectedByID.getYearManufacture())));
            stringTableList.add(new StringTable(listCharacteristic.get(4), String.valueOf(carSelectedByID.getCost())));
            stringTableList.add(new StringTable(listCharacteristic.get(5), carSelectedByID.getColor()));
            stringTableList.add(new StringTable(listCharacteristic.get(6), String.valueOf(carSelectedByID.getMaxSpeed())));
            stringTableList.add(new StringTable(listCharacteristic.get(7), carSelectedByID.getTransmission()));
            stringTableList.add(new StringTable(listCharacteristic.get(8), carSelectedByID.getDriveType()));
            stringTableList.add(new StringTable(listCharacteristic.get(9), carSelectedByID.getFuelType()));
            stringTableList.add(new StringTable(listCharacteristic.get(10), carSelectedByID.getEngineType()));
            stringTableList.add(new StringTable(listCharacteristic.get(11), String.valueOf(carSelectedByID.getEngineCapacity())));
            stringTableList.add(new StringTable(listCharacteristic.get(12), String.valueOf(carSelectedByID.getFuelConsumptionFor100km())));
            stringTableList.add(new StringTable(listCharacteristic.get(13), carSelectedByID.getState()));
            stringTableList.add(new StringTable(listCharacteristic.get(14), carSelectedByID.getSecurityTypes()));
            stringTableList.add(new StringTable(listCharacteristic.get(15), carSelectedByID.getComfortTypes()));

        }catch (Exception e){
            e.printStackTrace();
        }
        return stringTableList;
    }

    public void onSearchCarButtonClick(ActionEvent event){

        try {
            if (selectListCar.getValue() != null){
                int id = selectListCar.getValue();
                carSelectedByID = searchCarByID(id);
                if (carSelectedByID != null){
                    setDataTable();
                    setMainMessage("Дані про вибране авто виведено)");
                }else {
                    setMainMessage("Авто не знайдено!");
                }
            }else {
                setMainMessage("Виберіть авто!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setDataTable(){
        try {
            nameParameterColumn.setCellValueFactory(new PropertyValueFactory<>("nameParameterColumn"));
            characteristicColumn.setCellValueFactory(new PropertyValueFactory<>("characteristicColumn"));

            List<StringTable> stringTableList = createListToWrite();
            table.setItems(FXCollections.observableArrayList(stringTableList));

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onEditCarDataButtonClick(ActionEvent event){

        try {
            if (selectListCar.getValue() != null) {
                int id = selectListCar.getValue();
                carSelectedByID = searchCarByID(id);

                if (carSelectedByID != null) {
                    labelInfo.setText("Виберіть параметр для оновлення інформації :");

                    turnOnVisibleButtonAndChoiceListOnMainScene(false);
                    onResetEditThisCharacteristicButtonClick(event);

                }else {
                    setMainMessage("Авто не знайдено!");
                }
            }else {
                setMainMessage("Виберіть авто!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onChooseCharacteristicButtonClick(ActionEvent event) {
        try {
            boolean isSelected = true;

            if (selectListCharacteristic.getValue() == null) {
                messageLabel.setText("Виберіть характеристику для редагування!!");
                messageLabel.setVisible(true);
                isSelected = false;
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(3))) {
                enteringYearManufactureCar.setVisible(true);
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(9))) {
                enteringFuelTypeCar.setVisible(true);
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(11))) {
                enteringEngineCapacityCar.setVisible(true);
            } else if (selectListCharacteristic.getValue().equals(listCharacteristic.get(12))) {
                enteringFuelConsumptionFor100kmCar.setVisible(true);
            } else {
                enteringData.setVisible(true);
            }

            if (isSelected) {
                messageLabel.setText("Введіть нові дані:");
                chooseCharacteristicButton.setDisable(true);
                turnOnVisibleButtonAndChoiceListOnChangingDataScene(true);
                selectListCharacteristic.setDisable(true);
                saveChangeButton.setDisable(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onResetEditThisCharacteristicButtonClick(ActionEvent event){
        try {
            turnOnVisibleButtonAndChoiceListOnChangingDataScene(false);
            selectListCharacteristic.setVisible(true);
            selectListCharacteristic.setDisable(false);
            chooseCharacteristicButton.setDisable(false);
            chooseCharacteristicButton.setVisible(true);
            resetEditThisCharacteristicButton.setVisible(true);
            turnOffAndClearAllEnteringField();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onDeleteCarButtonClick(ActionEvent event){
        try {
            if (selectListCar.getValue() != null) {
                int id = selectListCar.getValue();
                carSelectedByID = searchCarByID(id);

                if (carSelectedByID != null) {
                    DeleteCar deleteCar = new DeleteCar();
                    deleteCar.execute(carSelectedByID);

                    MainMenuController mainMenuController = new MainMenuController();
                    mainMenuController.openNewScene(event, "searchCarByID.fxml");

                    setMainMessage("Авто успішно видалено)");
                }else {
                    setMainMessage("Виникла помилка із видаленням!");
                }
            }else {
                setMainMessage("Виберіть авто!");
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMainMessage(String message){
        try {
            messageLabelMain.setText(message);
            messageLabelMain.setVisible(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onSaveChangeButtonClick(ActionEvent event){
        try {
            int id = carSelectedByID.getCarID();
            if (doActionWithSelectedCharacteristic()) {
                goBackButton.setDisable(false);
                cars = getCarsFromDB();
                carSelectedByID = searchCarByID(id);
                if (carSelectedByID != null){
                    setDataTable();
                    saveChangeButton.setDisable(true);
                    goBackButton.setDisable(true);
                    turnOffAndClearAllEnteringField();
                    messageLabel.setText("Зміни успішно збережено!");
                }
            }else {
                messageLabel.setText("Виникла помилка при збережені змін!");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean doActionWithSelectedCharacteristic(){
        try {

            EditCarData editCarData = new EditCarData();
            boolean isSaved = true;

            if(selectListCharacteristic.getValue() == null){
                messageLabel.setText("Введіть дані для збереження!!");
                isSaved = false;
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(1))){
                editCarData.updateDataOfCar(carSelectedByID, enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(2))){
                editCarData.updateDataOfGeneralInfo(carSelectedByID, "MarkAndModel", enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(3))){
                editCarData.updateDataOfGeneralInfo(carSelectedByID, "YearManufacture", enteringYearManufactureCar.getValue());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(4))){
                editCarData.updateDataOfGeneralInfo(carSelectedByID, "Cost", Double.parseDouble(enteringData.getText()));
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(5))){
                editCarData.updateDataOfGeneralInfo(carSelectedByID, "Color", enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(6))){
                editCarData.updateDataOfGeneralInfo(carSelectedByID, "MaxSpeed", Double.parseDouble(enteringData.getText()));
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(7))){
                editCarData.updateDataOfTechnicInfo(carSelectedByID, "Transmission", enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(8))){
                editCarData.updateDataOfTechnicInfo(carSelectedByID, "DriveType", enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(9))){
                editCarData.updateDataOfFuelInfo(carSelectedByID, "FuelType", enteringFuelTypeCar.getValue());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(10))){
                editCarData.updateDataOfFuelInfo(carSelectedByID, "EngineType", enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(11))){
                editCarData.updateDataOfFuelInfo(carSelectedByID, "EngineCapacity", enteringEngineCapacityCar.getValue());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(12))){
                editCarData.updateDataOfFuelInfo(carSelectedByID, "FuelConsumptionFor100km", enteringFuelConsumptionFor100kmCar.getValue());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(13))){
                editCarData.updateDataOfMoreInformation(carSelectedByID, "State", enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(14))){
                editCarData.updateDataOfMoreInformation(carSelectedByID, "SecurityTypes", enteringData.getText());
            }else if(selectListCharacteristic.getValue().equals(listCharacteristic.get(15))){
                editCarData.updateDataOfMoreInformation(carSelectedByID, "ComfortTypes", enteringData.getText());
            }

            return isSaved;

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    public void onGoBackButtonClick(ActionEvent event){
        try {
            turnOffAndClearAllEnteringField();
            turnOnVisibleButtonAndChoiceListOnChangingDataScene(false);
            turnOnVisibleButtonAndChoiceListOnMainScene(true);
            selectListCharacteristic.setVisible(false);
            chooseCharacteristicButton.setVisible(false);
            resetEditThisCharacteristicButton.setVisible(false);
        }catch (Exception e){
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

    private Car searchCarByID(int id){
        try {
            for (Car car:cars){
                if (car.getCarID() == id){
                    return car;
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private void turnOnVisibleButtonAndChoiceListOnMainScene(boolean bool){
        try {
            selectListCar.setVisible(bool);
            searchCarButton.setVisible(bool);
            editCarDataButton.setVisible(bool);
            deleteCarButton.setVisible(bool);
            goBackwitoutSavingButton.setVisible(!bool);
            messageLabelMain.setVisible(bool);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void turnOnVisibleButtonAndChoiceListOnChangingDataScene(boolean bool) {
        try {
            goBackButton.setVisible(bool);
            saveChangeButton.setVisible(bool);
            messageLabel.setVisible(bool);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void turnOffAndClearAllEnteringField() {
        try {
            enteringData.setVisible(false);
            enteringData.clear();
            enteringYearManufactureCar.setVisible(false);
            enteringEngineCapacityCar.setVisible(false);
            enteringFuelTypeCar.setVisible(false);
            enteringFuelConsumptionFor100kmCar.setVisible(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
