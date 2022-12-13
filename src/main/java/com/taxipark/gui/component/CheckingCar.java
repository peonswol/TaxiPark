package com.taxipark.gui.component;

public class CheckingCar {
    public boolean checkSizeMark(String mark){
        return mark.length() <= 50 &&  mark.length() > 0;
    }

    public boolean checkSizeColor(String color){
        return color.length() <= 20 && color.length() > 0;
    }

    public boolean checkSizeTransmissionAndDriveType(String transmissionOrDriveType){
        return transmissionOrDriveType.length() <= 100 && transmissionOrDriveType.length() > 0;
    }
}

