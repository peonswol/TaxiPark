package com.taxipark.gui.component;

import javafx.collections.ObservableArray;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Car {

    private int carID;

    private String carVIN;

    private String markAndModel;
    private int yearManufacture;
    private double cost;
    private String color;
    private double maxSpeed;

    private String transmission;
    private String driveType;

    private String fuelType;
    private String engineType;
    private double engineCapacity;
    private double fuelConsumptionFor100km;

    private String state;
    private String securityTypes;
    private String comfortTypes;

    public int getCarID() {
        return carID;
    }

    public void setCarID(int carID) {
        this.carID = carID;
    }

    public String getMarkAndModel() {
        return markAndModel;
    }

    public void setMarkAndModel(String markAndModel) {
        this.markAndModel = markAndModel;
    }

    public int getYearManufacture() {
        return yearManufacture;
    }

    public void setYearManufacture(int yearManufacture) {
        this.yearManufacture = yearManufacture;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public double getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    public String getDriveType() {
        return driveType;
    }

    public void setDriveType(String driveType) {
        this.driveType = driveType;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public String getEngineType() {
        return engineType;
    }

    public void setEngineType(String engineType) {
        this.engineType = engineType;
    }

    public double getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(double engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public double getFuelConsumptionFor100km() {
        return fuelConsumptionFor100km;
    }

    public void setFuelConsumptionFor100km(double fuelConsumptionFor100km) {
        this.fuelConsumptionFor100km = fuelConsumptionFor100km;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSecurityTypes() {
        return securityTypes;
    }

    public void setSecurityTypes(String securityTypes) {
        this.securityTypes = securityTypes;
    }

    public String getComfortTypes() {
        return comfortTypes;
    }

    public void setComfortTypes(String comfortTypes) {
        this.comfortTypes = comfortTypes;
    }

    public String getCarVIN() {
        return carVIN;
    }

    public void setCarVIN(String carVIN) {
        this.carVIN = carVIN;
    }

    public static List<Car> getCarsFromDB(){

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

}


