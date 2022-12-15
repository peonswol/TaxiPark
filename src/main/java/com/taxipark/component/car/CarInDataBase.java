package com.taxipark.component.car;

import com.taxipark.component.ConnectToDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CarInDataBase {

    private static final Connection connection = ConnectToDataBase.getConnection();

    public static List<Car> getCarsFromDB(){

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

    public static int getGeneralInfoID(Car car){
        String getID = "select \"GeneralInfoID\"\n" +
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

    public static int getFuelInfoID(Car car){
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

    public static int getMoreInformationID(Car car){
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

    public static int getTechnicInfoID(Car car){
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
}
