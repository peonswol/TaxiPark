package com.taxipark.gui.component;

import javafx.collections.ObservableArray;

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

}


