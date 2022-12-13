package com.taxipark.gui.component;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EditCarData {

    private final Connection connection = ConnectToDataBase.getConnection();

    private PreparedStatement preparedStatement;

    private Map<String, String> parameterToTable = new HashMap<>();

    private Map<String, String> parameterMap = new HashMap<>();

    private String nameTable;

    private String nameParameter;

    private int id;

    private String sql = """
                update \"?\"
                set \"?\" = ?
                where ? = ?""";

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

    public void updateData(Car car, String parameter, String newData){

        initParamAndTable(car, parameter);

        try{
            preparedStatement = setParamAndTable();
            preparedStatement.setString(1, newData);
            preparedStatement.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(Car car, String parameter, double newData){

        initParamAndTable(car, parameter);

        try{
            preparedStatement = setParamAndTable();
            preparedStatement.setDouble(1, newData);
            preparedStatement.execute();
        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateData(Car car, String parameter, int newData){

        initParamAndTable(car, parameter);

        try{
            preparedStatement = setParamAndTable();
            preparedStatement.setInt(1, newData);
            preparedStatement.execute();
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
                    "update \"%s\"\n" +
                    "set \"%s\" = ?\n" +
                    "where \"%s\" = ?", nameTable + "Table", nameParameter, nameTable + "ID"));

            preparedStatement.setInt(2, id);
            return preparedStatement;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
