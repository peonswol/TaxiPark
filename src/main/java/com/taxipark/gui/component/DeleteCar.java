package com.taxipark.gui.component;

import java.sql.*;

public class DeleteCar {
    private final Connection connection = ConnectToDataBase.getConnection();

    private PreparedStatement preparedStatement;

    private int getGeneralInfoID(Car car){

        try {

            String getID = "select \"GeneralInfoID\"\n" +
                    "from \"CarTable\"\n" +
                    "where \"CarID\" = " + car.getCarID();

            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(getID);
            if (resultQuery.next()) {
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
            if (resultQuery.next()) {
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
            if (resultQuery.next()) {
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
            if (resultQuery.next()) {
                return resultQuery.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }


    public void deleteCar(Car car){

        String sql = "delete from \"CarTable\"\n" +
                "where \"CarID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, car.getCarID());

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteGeneralInfo(int id){

        String sql = "delete from \"GeneralInfoTable\"\n" +
                "where \"GeneralInfoID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteFuelInfo(int id){

        String sql = "delete from \"FuelInfoTable\"\n" +
                "where \"FuelInfoID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteTechnicInfo(int id){

        String sql = "delete from \"TechnicInfoTable\"\n" +
                "where \"TechnicInfoID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteMoreInformation(int id){

        String sql = "delete from \"MoreInformationTable\"\n" +
                "where \"MoreInformationID\" = ?";

        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void execute(Car car){

        int idGeneralInfoID = getGeneralInfoID(car);
        int idTechnicInfoID = getTechnicInfoID(car);
        int idFuelInfoID = getFuelInfoID(car);
        int idMoreInformationID = getMoreInformationID(car);

        deleteCar(car);
        deleteGeneralInfo(idGeneralInfoID);
        deleteTechnicInfo(idTechnicInfoID);
        deleteFuelInfo(idFuelInfoID);
        deleteMoreInformation(idMoreInformationID);

    }
}
