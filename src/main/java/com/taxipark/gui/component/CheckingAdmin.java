package com.taxipark.gui.component;

import java.sql.*;
import java.time.LocalDate;

public class CheckingAdmin {

    Connection connection = ConnectToDataBase.getConnection();

    public boolean checkSizeAdminLoginAndPassword(String loginOrPassword) {
        return loginOrPassword.length() <= 30 && loginOrPassword.length() >= 8;
    }

    public boolean checkSizeAdminNameAndPosition(String nameOrPosition) {
        return nameOrPosition.length() <= 30 && nameOrPosition.length() >= 1;
    }

    public boolean checkCorrectDate(LocalDate dateBirth, LocalDate dateEmployment) {
        return dateEmployment.isAfter(dateBirth);
    }

    public boolean isAdminInDB(String admin, String password) {

        String checkIsLogin = "select count(*)\n" +
                "from \"Admins\"\n" +
                "where \"Admins\".\"AdminLogin\" = '" + admin +
                "' and \"Admins\".\"AdminPassword\" = '" + password + "'";

        try {
            Statement statement = connection.createStatement();
            ResultSet resultQuery = statement.executeQuery(checkIsLogin);

            if (resultQuery.next()) {
                return resultQuery.getInt(1) == 1;
            }

        } catch (
                SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public boolean isNotLoginInDB(String login) {

        String checkIsLogin = "select count(*)\n" +
                "from \"Admins\"\n" +
                "where \"Admins\".\"AdminLogin\" = '" + login +"'";

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
}
