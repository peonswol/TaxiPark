package com.taxipark.gui;

import com.taxipark.gui.component.Car;
import com.taxipark.gui.component.ConnectToDataBase;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SearchCarByIDController implements Initializable {

    @FXML
    TableView<StringTable> table;

    @FXML
    TableColumn<StringTable, String> nameParameterColumn;

    @FXML
    TableColumn<StringTable, String> characteristicColumn;

    @FXML
    ChoiceBox<Integer> selectListCar;

    private List<Car> cars;

    private Car carSelectedByID;

    private static class StringTable{
        String name;
        String characteristic;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public StringTable(String name, String characteristic) {
            this.name = name;
            this.characteristic = characteristic;
        }

        public String getCharacteristic() {
            return characteristic;
        }

        public void setCharacteristic(String characteristic) {
            this.characteristic = characteristic;
        }
    }

    private List<Car> getCarsFromDB(){

        Connection connection = ConnectToDataBase.getConnection();

        String getCars =
                "select car.\"CarID\", car.\"CarVIN\", \"general\".\"MarkAndModel\"," +
                        "\"general\".\"YearManufacture\", \"general\".\"Cost\", " +
                        "\"general\".\"Color\", \"general\".\"MaxSpeed\", " +
                        "\"technic\".\"Transmission\", \"technic\".\"DriveType\", " +
                        "\"fuel\".\"FuelType\", \"fuel\".\"EngineType\", " +
                        "\"fuel\".\"EngineCapacity\", \"fuel\".\"FuelConsumptionFor100km\", " +
                        "\"moreInfo\".\"State\", \"moreInfo\".\"SecurityTypes\", " +
                        "\"moreInfo\".\"ComfortTypes\"\n" +
                        "from \"CarTable\" as \"car\"\n" +
                        "inner join \"GeneralInfoTable\" as \"general\"\n" +
                        "on \"car\".\"GeneralInfoID\" = \"general\".\"GeneralInfoID\"\n" +
                        "inner join \"FuelInfoTable\" as \"fuel\"\n" +
                        "on \"car\".\"FuelInfoID\" = \"fuel\".\"FuelInfoID\"\n" +
                        "inner join \"TechnicInfoTable\" as \"technic\"\n" +
                        "on \"car\".\"TechnicInfoID\" = \"technic\".\"TechnicInfoID\"\n" +
                        "inner join \"MoreInformationTable\" as \"moreInfo\"\n" +
                        "on \"car\".\"MoreInformationID\" = \"moreInfo\".\"MoreInformationID\"";

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
        cars = getCarsFromDB();

        for (Car car:cars) {
            selectListCar.getItems().add(car.getCarID());
        }
    }

    private List<StringTable> createListToWrite(){

        List<StringTable> stringTableList = new ArrayList<>();

        try {

            stringTableList.add(new StringTable("ID", String.valueOf(carSelectedByID.getCarID())));
            stringTableList.add(new StringTable("VIN", carSelectedByID.getCarVIN()));
            stringTableList.add(new StringTable("Марка і модель", carSelectedByID.getMarkAndModel()));
            stringTableList.add(new StringTable("Рік збірки", String.valueOf(carSelectedByID.getYearManufacture())));
            stringTableList.add(new StringTable("Вартість", String.valueOf(carSelectedByID.getCost())));
            stringTableList.add(new StringTable("Колір", carSelectedByID.getColor()));
            stringTableList.add(new StringTable("Максимальна швидкість", String.valueOf(carSelectedByID.getMaxSpeed())));
            stringTableList.add(new StringTable("Коробка передач", carSelectedByID.getTransmission()));
            stringTableList.add(new StringTable("Тип приводу", carSelectedByID.getDriveType()));
            stringTableList.add(new StringTable("Тип пального", carSelectedByID.getFuelType()));
            stringTableList.add(new StringTable("Тип двигуна", carSelectedByID.getEngineType()));
            stringTableList.add(new StringTable("Об'єм двигуна", String.valueOf(carSelectedByID.getEngineCapacity())));
            stringTableList.add(new StringTable("Витрати пального за 100 км", String.valueOf(carSelectedByID.getFuelConsumptionFor100km())));
            stringTableList.add(new StringTable("Стан", carSelectedByID.getState()));
            stringTableList.add(new StringTable("Типи безпеки", carSelectedByID.getSecurityTypes()));
            stringTableList.add(new StringTable("Типи комфорту", carSelectedByID.getComfortTypes()));

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
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void setDataTable(){
        try {
            List<StringTable> stringTableList = createListToWrite();

            nameParameterColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            characteristicColumn.setCellValueFactory(new PropertyValueFactory<>("characteristic"));

            table.setItems(FXCollections.observableArrayList(stringTableList));
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onEditCarDataButtonClick(ActionEvent event){
        try {

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void onDeleteCarButtonClick(ActionEvent event){
        try {

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
}
