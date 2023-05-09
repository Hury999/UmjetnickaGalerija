package com.example.galerija;



import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    public Connection DataBaseLink;

    public Connection GetConnection() {
        String databaseName = "mojaBaza";
        String databaseUser = "root";
        String databasePassword = "hare";
        String url = "jdbc:mysql://localhost" + databaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DataBaseLink = DriverManager.getConnection(url, databaseUser, databasePassword);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return DataBaseLink;
    }
}




