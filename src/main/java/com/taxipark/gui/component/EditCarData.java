package com.taxipark.gui.component;

import javafx.scene.control.Alert;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.taxipark.gui.MainMenuController.setAlert;

public class EditCarData {

    private final Connection connection = ConnectToDataBase.getConnection();

    private PreparedStatement preparedStatement;

    private final Map<String, String> parameterToTable = new HashMap<>();

    private final Map<String, String> parameterMap = new HashMap<>();

    private String nameTable;

    private String nameParameter;

    private int id;

    CheckingCar checkingCar = new CheckingCar();

    public EditCarData(){
        setMapParameter();
        setMapParameterToTable();
    }

    private void setMapParameter() {
        try {
            parameterMap.put("ID", "CarID");
            parameterMap.put("VIN", "CarVin");
            parameterMap.put("Марка і модель", "MarkAndModel");
            parameterMap.put("Рік збірки", "YearManufacture");
            parameterMap.put("Вартість", "Cost");
            parameterMap.put("Колір", "Color");
            parameterMap.put("Максимальна швидкість", "MaxSpeed");
            parameterMap.put("Коробка передач", "Transmission");
            parameterMap.put("Тип приводу", "DriveType");
            parameterMap.put("Тип пального", "FuelType");
            parameterMap.put("Тип двигуна", "EngineType");
            parameterMap.put("Об'єм двигуна", "EngineCapacity");
            parameterMap.put("Витрати пального за 100 км", "FuelConsumptionFor100km");
            parameterMap.put("Стан", "State");
            parameterMap.put("Типи безпеки", "SecurityTypes");
            parameterMap.put("Типи комфорту", "ComfortTypes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMapParameterToTable() {
        try {
            parameterToTable.put("CarVIN", "Car");
            parameterToTable.put("MarkAndModel", "GeneralInfo");
            parameterToTable.put("YearManufacture", "GeneralInfo");
            parameterToTable.put("Cost", "GeneralInfo");
            parameterToTable.put("Color", "GeneralInfo");
            parameterToTable.put("MaxSpeed", "GeneralInfo");
            parameterToTable.put("Transmission", "TechnicInfo");
            parameterToTable.put("DriveType", "TechnicInfo");
            parameterToTable.put("FuelType", "FuelInfo");
            parameterToTable.put("EngineType", "FuelInfo");
            parameterToTable.put("EngineCapacity", "FuelInfo");
            parameterToTable.put("FuelConsumptionFor100km", "FuelInfo");
            parameterToTable.put("State", "MoreInformation");
            parameterToTable.put("SecurityTypes", "MoreInformation");
            parameterToTable.put("ComfortTypes", "MoreInformation");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean updateData(Car car, String parameter, String newData){

        if (!allDataIsCorrect(parameter, String.valueOf(newData))){
            return false;
        }

        initParamAndTable(car, parameter);

        try{
            preparedStatement = setParamAndTable();
            preparedStatement.setString(1, newData);
            preparedStatement.execute();
            return true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateData(Car car, String parameter, double newData){

        if (!allDataIsCorrect(parameter, String.valueOf(newData))){
            return false;
        }

        initParamAndTable(car, parameter);

        try{
            preparedStatement = setParamAndTable();
            preparedStatement.setDouble(1, newData);
            preparedStatement.execute();
            return true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateData(Car car, String parameter, int newData){

        if (!allDataIsCorrect(parameter, String.valueOf(newData))){
            return false;
        }

        initParamAndTable(car, parameter);

        try{
            preparedStatement = setParamAndTable();
            preparedStatement.setInt(1, newData);
            preparedStatement.execute();
            return true;
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void initParamAndTable(Car car, String parameter){

        nameParameter = parameterMap.get(parameter);
        nameTable = parameterToTable.get(nameParameter);

        switch (nameTable) {
            case "Car" -> id = car.getCarID();
            case "GeneralInfo" -> id = CarInDataBase.getGeneralInfoID(car);
            case "TechnicInfo" -> id = CarInDataBase.getTechnicInfoID(car);
            case "FuelInfo" -> id = CarInDataBase.getFuelInfoID(car);
            case "MoreInformation" -> id = CarInDataBase.getMoreInformationID(car);
        }
    }

    private PreparedStatement setParamAndTable(){
        try {

            PreparedStatement preparedStatement = connection.prepareStatement(String.format(
                    """
                            update "%s"
                            set "%s" = ?
                            where "%s" = ?""", nameTable + "Table", nameParameter, nameTable + "ID"));

            preparedStatement.setInt(2, id);
            return preparedStatement;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean allDataIsCorrectSize(String parameter, String newData) {
        try {

            switch (parameter) {
                case "VIN" -> {
                    if (checkingCar.checkSizeVIN(newData)) {
                        return true;
                    }
                }
                case "Марка і модель" -> {
                    if (checkingCar.checkSizeMark(newData)) {
                        return true;
                    }
                }
                case "Вартість", "Максимальна швидкість" -> {
                    if (checkingCar.stringIsDouble(newData)) {
                        return true;
                    }
                }
                case "Колір" -> {
                    if (checkingCar.checkSizeColor(newData)) {
                        return true;
                    }
                }
                default -> {
                    if (!newData.isEmpty()) {
                        return true;
                    }
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    private boolean allDataIsCorrect(String parameter, String newData) {
        try {
            if (!allDataIsCorrectSize(parameter, newData)) {
                setAlert(Alert.AlertType.ERROR, "Введіть ВСІ дані і коректного розміру!", "Спробуйте ще раз, перевіривши розмір вводу або заповнивши пусті поля.\n*****\nVIN - 10\nмарка - <= 50\nколір - <= 20");
                return false;
            }
            if (parameter.equals("VIN")) {
                if (!checkingCar.isNotVINInDB(newData)) {
                    setAlert(Alert.AlertType.ERROR, "Такий VIN вже існує!", "Авто з введеним номером уже записаний");
                    return false;
                }/*
            if (!checkingAdmin.checkCorrectDate(dateBirth.getValue(), dateEmployment.getValue())){
                setMessage("Введіть коректні дати!");
                return false;
            }*/
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }


}
