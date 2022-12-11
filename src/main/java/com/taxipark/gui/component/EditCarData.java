package com.taxipark.gui.component;

import java.sql.*;

public class EditCarData {

    private Connection connection = ConnectToDataBase.getConnection();

    private PreparedStatement preparedStatement;

    private int getGeneralInfoID(Car car){
        String getID = "select \"GeneralInfoID\"\n" +
                "from \"CarTable\"\n" +
                "where \"CarID\" = " + car.getCarID();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(getID);
            while (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int getTechnicInfoID(Car car){
        String getID = "select \"TechnicInfoID\"\n" +
                "from \"CarTable\"\n" +
                "where \"CarID\" = " + car.getCarID();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(getID);
            while (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int getFuelInfoID(Car car){
        String getID = "select \"FuelInfoID\"\n" +
                "from \"CarTable\"\n" +
                "where \"CarID\" = " + car.getCarID();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(getID);
            while (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    private int getMoreInformationID(Car car){
        String getID = "select \"MoreInformationID\"\n" +
                "from \"CarTable\"\n" +
                "where \"CarID\" = " + car.getCarID();

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(getID);
            while (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    public void updateDataOfCar(Car car, String newData){
        int id = car.getCarID();
        String sql = "update \"CarTable\"\n" +
                "set \"CarVIN\" = ?\n"+
                "where \"CarID\" = ?";

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newData);
            preparedStatement.setInt(2, car.getCarID());

            preparedStatement.execute();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDataOfGeneralInfo(Car car , String nameCharacteristic, String newData){
        int id = getGeneralInfoID(car);

        String sql = "update \"GeneralInfoTable\"\n" +
                "set \"" + nameCharacteristic + "\"= ?\n"+
                "where \"GeneralInfoID\" = ?";

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newData);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDataOfGeneralInfo(Car car , String nameCharacteristic, int newData){
        int id = getGeneralInfoID(car);

        String sql = "update \"GeneralInfoTable\"\n" +
                "set \"" + nameCharacteristic + "\"= ?\n"+
                "where \"GeneralInfoID\" = ?";

        try{
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, newData);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDataOfGeneralInfo(Car car , String nameCharacteristic, double newData) {
        int id = getGeneralInfoID(car);

        String sql = "update \"GeneralInfoTable\"\n" +
                "set \"" + nameCharacteristic + "\"= ?\n" +
                "where \"GeneralInfoID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newData);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void updateDataOfFuelInfo(Car car , String nameCharacteristic, String newData){
        int id = getFuelInfoID(car);

        String sql = "update \"FuelInfoTable\"\n" +
                "set \"" + nameCharacteristic + "\"= ?\n" +
                "where \"FuelInfoID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newData);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDataOfFuelInfo(Car car , String nameCharacteristic, double newData){
        int id = getFuelInfoID(car);

        String sql = "update \"FuelInfoTable\"\n" +
                "set \"" + nameCharacteristic + "\"= ?\n" +
                "where \"FuelInfoID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setDouble(1, newData);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDataOfTechnicInfo(Car car , String nameCharacteristic, String newData){
        int id = getTechnicInfoID(car);

        String sql = "update \"TechnicInfoTable\"\n" +
                "set \"" + nameCharacteristic + "\"= ?\n" +
                "where \"TechnicInfoID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newData);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateDataOfMoreInformation(Car car, String nameCharacteristic, String newData){
        int id = getMoreInformationID(car);

        String sql = "update \"MoreInformationTable\"\n" +
                "set \"" + nameCharacteristic + "\"= ?\n" +
                "where \"MoreInformationID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, newData);
            preparedStatement.setInt(2, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
