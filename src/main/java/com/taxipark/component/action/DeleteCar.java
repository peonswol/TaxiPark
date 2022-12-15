package com.taxipark.component.action;

import com.taxipark.component.ConnectToDataBase;
import com.taxipark.component.car.Car;
import com.taxipark.component.car.CarInDataBase;

import java.sql.*;

public class DeleteCar {
    private final Connection connection = ConnectToDataBase.getConnection();

    private PreparedStatement preparedStatement;


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

        int idGeneralInfoID = CarInDataBase.getGeneralInfoID(car);
        int idTechnicInfoID = CarInDataBase.getTechnicInfoID(car);
        int idFuelInfoID = CarInDataBase.getFuelInfoID(car);
        int idMoreInformationID = CarInDataBase.getMoreInformationID(car);

        deleteCar(car);
        deleteGeneralInfo(idGeneralInfoID);
        deleteTechnicInfo(idTechnicInfoID);
        deleteFuelInfo(idFuelInfoID);
        deleteMoreInformation(idMoreInformationID);

    }
}
