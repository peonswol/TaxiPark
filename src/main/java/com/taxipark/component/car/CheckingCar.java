package com.taxipark.component.car;

import com.taxipark.component.ConnectToDataBase;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CheckingCar {

    private final Connection connection = ConnectToDataBase.getConnection();

    public boolean checkSizeMark(String mark){
        return mark.length() <= 50 &&  mark.length() > 0;
    }

    public boolean checkSizeVIN(String carVIN){
        return carVIN.length() == 10;
    }

    public boolean checkSizeColor(String color){
        return color.length() <= 20 && color.length() > 0;
    }

    public boolean isNotVINInDB(String vin) {

        String checkIsLogin = "select count(*)\n" +
                "from \"CarTable\"\n" +
                "where \"CarTable\".\"CarVIN\" = '" + vin +"'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);

            if (resultQuery.next()) {
                return resultQuery.getInt(1) == 0;
            }

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean stringIsDouble(String string){
        try {
            Double.parseDouble(string);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    }
}

