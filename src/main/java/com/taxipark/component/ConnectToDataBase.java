package com.taxipark.component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectToDataBase {
    public static Connection connection;
    private static final String dataBaseUser = "postgres";
    private static final String dataBasePassword = "12345";
    private static final String url = "jdbc:postgresql://localhost:5432/TaxiPark";

    public static Connection getConnection(){

        try {
            Class.forName("org.postgresql.Driver");
             connection = DriverManager.getConnection(url, dataBaseUser, dataBasePassword);

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
