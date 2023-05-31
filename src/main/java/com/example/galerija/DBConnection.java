package com.example.galerija;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {

    public Connection DataBaseLink;

    public Connection GetConnection(String dataBaseName, String dataBaseUser, String dataBasePassword) {

        String url = "jdbc:mysql://localhost" + dataBaseName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DataBaseLink = DriverManager.getConnection(url, dataBaseUser, dataBasePassword);


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return DataBaseLink;
    }

    // Query the database and map the result set to a list of Djelo objects
    public ObservableList<Djelo> GetAllDjelo () {

        ObservableList<Djelo> djela = FXCollections.observableArrayList();

        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String connectQuery = "SELECT ID_Djelo, Naslov, GodinaNastanka, ID_Umjetnik, ID_Pravac, ID_Tehnika, ID_Izlozba, ID_Lokacija FROM djelo";

            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);


            // Loop through the result set and append each value of Naslov to welcomeText
            while (queryOutput.next()) {
                int idDjelo = queryOutput.getInt("ID_Djelo");
                String naslov = queryOutput.getString("Naslov");
                int godinaNastanka = queryOutput.getInt("GodinaNastanka");
                int idUmjetnik = queryOutput.getInt("ID_Umjetnik");
                int idPravac = queryOutput.getInt("ID_Pravac");
                int idTehnika = queryOutput.getInt("ID_Tehnika");
                int idIzlozba = queryOutput.getInt("ID_Izlozba");
                int idLokacija = queryOutput.getInt("id_Lokacija");
                Djelo djelo = new Djelo(idDjelo, naslov, godinaNastanka, idUmjetnik, idPravac, idTehnika, idIzlozba, idLokacija);
                djela.add(djelo);
            }



        } catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            return djela;
        }

    }


    public void insertIntoDjelo(Djelo djelo) {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String insertQuery = "INSERT INTO djelo (ID_Djelo, Naslov, GodinaNastanka, ID_Umjetnik, ID_Pravac, ID_Tehnika, ID_Izlozba, ID_Lokacija) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, djelo.getIdDjelo());
            statement.setString(2, djelo.getNaslov());
            statement.setInt(3, djelo.getGodinaNastanka());
            statement.setInt(4, djelo.getIdUmjetnik());
            statement.setInt(5, djelo.getIdPravac());
            statement.setInt(6, djelo.getIdTehnika());
            statement.setInt(7, djelo.getIdIzlozba());
            statement.setInt(8, djelo.getIdLokacija());

            statement.executeUpdate();

            System.out.println("Djelo inserted successfully.");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void updateDjelo(Djelo djelo) {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String updateQuery = "UPDATE djelo SET Naslov = ?, GodinaNastanka = ?, ID_Umjetnik = ?, ID_Pravac = ?, ID_Tehnika = ?, ID_Izlozba = ?, ID_Lokacija = ? WHERE ID_Djelo = ?";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, djelo.getNaslov());
            statement.setInt(2, djelo.getGodinaNastanka());
            statement.setInt(3, djelo.getIdUmjetnik());
            statement.setInt(4, djelo.getIdPravac());
            statement.setInt(5, djelo.getIdTehnika());
            statement.setInt(6, djelo.getIdIzlozba());
            statement.setInt(7, djelo.getIdLokacija());
            statement.setInt(8, djelo.getIdDjelo());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Djelo updated successfully.");
            } else {
                System.out.println("No rows updated. Djelo not found.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void deleteDjelo(Djelo djelo) {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String deleteQuery = "DELETE FROM djelo WHERE ID_Djelo = ?";

            PreparedStatement statement = conn.prepareStatement(deleteQuery);

            statement.setInt(1, djelo.getIdDjelo());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Djelo deleted successfully.");
            } else {
                System.out.println("No rows deleted. Djelo not found.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}




