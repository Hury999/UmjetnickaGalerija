package com.example.galerija;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;

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


    public <T> ObservableList<T> getAllDataBaseDataForObject(Class<T> clazz) {
        ObservableList<T> dbObjects = FXCollections.observableArrayList();

        // Getting the class type of the object
        String tableName = clazz.getSimpleName(); // Get the class name for the table name

        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String connectQuery = "SELECT * FROM " + tableName;

            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            // Loop through the result set and append each value to dbObjects list
            while (queryOutput.next()) {
                // Populate the object based on the result set
                T item = createInstanceFromResultSet(clazz, queryOutput);
                dbObjects.add(item);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            return dbObjects;
        }
    }


    private <T> T createInstanceFromResultSet(Class<T> clazz, ResultSet resultSet) throws SQLException, IllegalAccessException, InstantiationException {
        T instance = null;

        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            instance = constructor.newInstance();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Loop through all fields in the class
        for (Field field : clazz.getDeclaredFields()) {
            String fieldName = field.getName();
            Object value = resultSet.getObject(fieldName);

            // Convert the value to SimpleIntegerProperty if it's an Integer
            if (value instanceof Integer && field.getType().equals(IntegerProperty.class)) {
                value = new SimpleIntegerProperty((Integer) value);
            } else if (value instanceof String && field.getType().equals(StringProperty.class)) {
                value = new SimpleStringProperty((String) value);
            } else if (value instanceof java.sql.Date && field.getType().equals(ObjectProperty.class)) {
                value = new SimpleObjectProperty<>(new Date(((java.sql.Date) value).getTime()));
            }

            // Set the value of the field in the instance using reflection
            field.setAccessible(true);
            field.set(instance, value);
        }

        return instance;
    }

    // Query the database and map the result set to a list of Djelo objects
    public ObservableList<Djelo> GetAllDjelo() {

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
        } finally {
            return djela;
        }

    }

    public void InsertIntoDjelo(Djelo djelo) {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String insertQuery = "INSERT INTO djelo (ID_Djelo, Naslov, GodinaNastanka, ID_Umjetnik, ID_Pravac, ID_Tehnika, ID_Izlozba, ID_Lokacija) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, djelo.getID_Djelo());
            statement.setString(2, djelo.getNaslov());
            statement.setInt(3, djelo.getGodinaNastanka());
            statement.setInt(4, djelo.getID_Umjetnik());
            statement.setInt(5, djelo.getID_Pravac());
            statement.setInt(6, djelo.getID_Tehnika());
            statement.setInt(7, djelo.getID_Izlozba());
            statement.setInt(8, djelo.getID_Lokacija());

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
            statement.setInt(3, djelo.getID_Umjetnik());
            statement.setInt(4, djelo.getID_Pravac());
            statement.setInt(5, djelo.getID_Tehnika());
            statement.setInt(6, djelo.getID_Izlozba());
            statement.setInt(7, djelo.getID_Lokacija());
            statement.setInt(8, djelo.getID_Djelo());

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

    public void deleteDjelo(Djelo djelo) throws Exception {

        // Connect to the database
        Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

        String deleteQuery = "DELETE FROM djelo WHERE ID_Djelo = ?";

        PreparedStatement statement = conn.prepareStatement(deleteQuery);

        statement.setInt(1, djelo.getID_Djelo());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Djelo deleted successfully.");
        } else {
            System.out.println("No rows deleted. Djelo not found.");
        }

    }


    public ObservableList<Dvorana> GetAllDvorana() {
        ObservableList<Dvorana> dvorane = FXCollections.observableArrayList();

        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String selectQuery = "SELECT ID_Dvorana, NazivDvorana FROM dvorana";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int idDvorana = resultSet.getInt("ID_Dvorana");
                String nazivDvorana = resultSet.getString("NazivDvorana");

                Dvorana dvorana = new Dvorana(idDvorana, nazivDvorana);
                dvorane.add(dvorana);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return dvorane;
    }

    public void InsertIntoDvorana(Dvorana dvorana) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String insertQuery = "INSERT INTO dvorana (ID_Dvorana, NazivDvorana) VALUES (?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, dvorana.getID_Dvorana());
            statement.setString(2, dvorana.getNazivDvorana());

            statement.executeUpdate();

            System.out.println("Dvorana inserted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void UpdateDvorana(Dvorana dvorana) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String updateQuery = "UPDATE dvorana SET NazivDvorana = ? WHERE ID_Dvorana = ?";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, dvorana.getNazivDvorana());
            statement.setInt(2, dvorana.getID_Dvorana());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Dvorana updated successfully.");
            } else {
                System.out.println("No rows updated. Dvorana not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void DeleteDvorana(Dvorana dvorana) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/mojaBaza", "root", "hare");

        String deleteQuery = "DELETE FROM dvorana WHERE ID_Dvorana = ?";

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, dvorana.getID_Dvorana());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Dvorana deleted successfully.");
        } else {
            System.out.println("No rows deleted. Dvorana not found.");
        }

        statement.close();
        conn.close();
    }

    public ObservableList<Izlozba> GetAllIzlozba() {
        ObservableList<Izlozba> izlozbe = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare")) {
            String selectQuery = "SELECT ID_Izlozba, PocetakDatum, ZavrsetakDatum, Tip, ID_Dvorana FROM izlozba";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int idIzlozba = resultSet.getInt("ID_Izlozba");
                Date pocetakDatum = resultSet.getDate("PocetakDatum");
                Date zavrsetakDatum = resultSet.getDate("ZavrsetakDatum");
                String tip = resultSet.getString("Tip");
                int idDvorana = resultSet.getInt("ID_Dvorana");

                Izlozba izlozba = new Izlozba(idIzlozba, pocetakDatum, zavrsetakDatum, tip, idDvorana);
                izlozbe.add(izlozba);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return izlozbe;
    }

    public void InsertIntoIzlozba(Izlozba izlozba) {
        try (Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare")) {
            String insertQuery = "INSERT INTO izlozba (ID_Izlozba, PocetakDatum, ZavrsetakDatum, Tip, ID_Dvorana) " +
                    "VALUES (?, ?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, izlozba.getID_Izlozba());
            statement.setDate(2, new java.sql.Date(izlozba.getPocetakDatum().getTime()));
            statement.setDate(3, new java.sql.Date(izlozba.getZavrsetakDatum().getTime()));
            statement.setString(4, izlozba.getTip());
            statement.setInt(5, izlozba.getID_Dvorana());

            statement.executeUpdate();

            System.out.println("Izlozba inserted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void UpdateIzlozba(Izlozba izlozba) {
        try (Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare")) {
            String updateQuery = "UPDATE izlozba SET PocetakDatum = ?, ZavrsetakDatum = ?, Tip = ?, ID_Dvorana = ? " +
                    "WHERE ID_Izlozba = ?";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setDate(1, new java.sql.Date(izlozba.getPocetakDatum().getTime()));
            statement.setDate(2, new java.sql.Date(izlozba.getZavrsetakDatum().getTime()));
            statement.setString(3, izlozba.getTip());
            statement.setInt(4, izlozba.getID_Dvorana());
            statement.setInt(5, izlozba.getID_Izlozba());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Izlozba updated successfully.");
            } else {
                System.out.println("No rows updated. Izlozba not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void DeleteIzlozba(Izlozba izlozba) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");
        String deleteQuery = "DELETE FROM izlozba WHERE ID_Izlozba = ?";

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, izlozba.getID_Izlozba());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Izlozba deleted successfully.");
        } else {
            System.out.println("No rows deleted. Izlozba not found.");
        }
    }


    public ObservableList<Lokacija> GetAllLokacija() {
        ObservableList<Lokacija> lokacije = FXCollections.observableArrayList();

        try (Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare")) {
            String selectQuery = "SELECT ID_Lokacija, NazivLokacija, BrojProstorije, ID_Dvorana FROM lokacija";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int idLokacija = resultSet.getInt("ID_Lokacija");
                String nazivLokacija = resultSet.getString("NazivLokacija");
                int brojProstorije = resultSet.getInt("BrojProstorije");
                int idDvorana = resultSet.getInt("ID_Dvorana");

                Lokacija lokacija = new Lokacija(idLokacija, nazivLokacija, brojProstorije, idDvorana);
                lokacije.add(lokacija);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return lokacije;
    }

    public void InsertIntoLokacija(Lokacija lokacija) {
        try (Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare")) {
            String insertQuery = "INSERT INTO lokacija (ID_Lokacija, NazivLokacija, BrojProstorije, ID_Dvorana) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, lokacija.getID_Lokacija());
            statement.setString(2, lokacija.getNazivLokacija());
            statement.setInt(3, lokacija.getBrojProstorije());
            statement.setInt(4, lokacija.getID_Dvorana());

            statement.executeUpdate();

            System.out.println("Lokacija inserted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void UpdateLokacija(Lokacija lokacija) {
        try (Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare")) {
            String updateQuery = "UPDATE lokacija SET NazivLokacija = ?, BrojProstorije = ?, ID_Dvorana = ? WHERE ID_Lokacija = ?";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, lokacija.getNazivLokacija());
            statement.setInt(2, lokacija.getBrojProstorije());
            statement.setInt(3, lokacija.getID_Dvorana());
            statement.setInt(4, lokacija.getID_Lokacija());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Lokacija updated successfully.");
            } else {
                System.out.println("No rows updated. Lokacija not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void DeleteLokacija(Lokacija lokacija) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");
        String deleteQuery = "DELETE FROM lokacija WHERE ID_Lokacija = ?";

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, lokacija.getID_Lokacija());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Lokacija deleted successfully.");
        } else {
            System.out.println("No rows deleted. Lokacija not found.");
        }
    }


    public ObservableList<Pravac> GetAllPravac() {
        ObservableList<Pravac> pravaci = FXCollections.observableArrayList();

        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String selectQuery = "SELECT ID_Pravac, NazivPravac FROM pravac";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int idPravac = resultSet.getInt("ID_Pravac");
                String nazivPravac = resultSet.getString("NazivPravac");

                Pravac pravac = new Pravac(idPravac, nazivPravac);
                pravaci.add(pravac);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return pravaci;
    }

    public void InsertIntoPravac(Pravac pravac) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String insertQuery = "INSERT INTO pravac (ID_Pravac, NazivPravac) VALUES (?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, pravac.getID_Pravac());
            statement.setString(2, pravac.getNazivPravac());

            statement.executeUpdate();

            System.out.println("Pravac inserted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void UpdatePravac(Pravac pravac) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String updateQuery = "UPDATE pravac SET NazivPravac = ? WHERE ID_Pravac = ?";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, pravac.getNazivPravac());
            statement.setInt(2, pravac.getID_Pravac());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Pravac updated successfully.");
            } else {
                System.out.println("No rows updated. Pravac not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void DeletePravac(Pravac pravac) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

        String deleteQuery = "DELETE FROM pravac WHERE ID_Pravac = ?";

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, pravac.getID_Pravac());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Pravac deleted successfully.");
        } else {
            System.out.println("No rows deleted. Pravac not found.");
        }
    }


    public ObservableList<Tehnika> GetAllTehnika() {
        ObservableList<Tehnika> tehnike = FXCollections.observableArrayList();

        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String selectQuery = "SELECT ID_Tehnika, NazivTehnika FROM tehnika";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int idTehnika = resultSet.getInt("ID_Tehnika");
                String nazivTehnika = resultSet.getString("NazivTehnika");

                Tehnika tehnika = new Tehnika(idTehnika, nazivTehnika);
                tehnike.add(tehnika);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return tehnike;
    }

    public void InsertIntoTehnika(Tehnika tehnika) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String insertQuery = "INSERT INTO tehnika (ID_Tehnika, NazivTehnika) VALUES (?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, tehnika.getID_Tehnika());
            statement.setString(2, tehnika.getNazivTehnika());

            statement.executeUpdate();

            System.out.println("Tehnika inserted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void UpdateTehnika(Tehnika tehnika) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String updateQuery = "UPDATE tehnika SET NazivTehnika = ? WHERE ID_Tehnika = ?";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, tehnika.getNazivTehnika());
            statement.setInt(2, tehnika.getID_Tehnika());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tehnika updated successfully.");
            } else {
                System.out.println("No rows updated. Tehnika not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void DeleteTehnika(Tehnika tehnika) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

        String deleteQuery = "DELETE FROM tehnika WHERE ID_Tehnika = ?";

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, tehnika.getID_Tehnika());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Tehnika deleted successfully.");
        } else {
            System.out.println("No rows deleted. Tehnika not found.");
        }
    }


    public ObservableList<Umjetnik> GetAllUmjetnik() {
        ObservableList<Umjetnik> umjetnici = FXCollections.observableArrayList();

        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String selectQuery = "SELECT ID_Umjetnik, Ime, Prezime, Biografija FROM umjetnik";

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(selectQuery);

            while (resultSet.next()) {
                int idUmjetnik = resultSet.getInt("ID_Umjetnik");
                String ime = resultSet.getString("Ime");
                String prezime = resultSet.getString("Prezime");
                String biografija = resultSet.getString("Biografija");

                Umjetnik umjetnik = new Umjetnik(idUmjetnik, ime, prezime, biografija);
                umjetnici.add(umjetnik);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return umjetnici;
    }

    public void InsertIntoUmjetnik(Umjetnik umjetnik) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String insertQuery = "INSERT INTO umjetnik (ID_Umjetnik, Ime, Prezime, Biografija) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(insertQuery);
            statement.setInt(1, umjetnik.getID_Umjetnik());
            statement.setString(2, umjetnik.getIme());
            statement.setString(3, umjetnik.getPrezime());
            statement.setString(4, umjetnik.getBiografija());

            statement.executeUpdate();

            System.out.println("Umjetnik inserted successfully.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void UpdateUmjetnik(Umjetnik umjetnik) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String updateQuery = "UPDATE umjetnik SET Ime = ?, Prezime = ?, Biografija = ? WHERE ID_Umjetnik = ?";

            PreparedStatement statement = conn.prepareStatement(updateQuery);
            statement.setString(1, umjetnik.getIme());
            statement.setString(2, umjetnik.getPrezime());
            statement.setString(3, umjetnik.getBiografija());
            statement.setInt(4, umjetnik.getID_Umjetnik());

            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Umjetnik updated successfully.");
            } else {
                System.out.println("No rows updated. Umjetnik not found.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void DeleteUmjetnik(Umjetnik umjetnik) throws Exception {

        Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

        String deleteQuery = "DELETE FROM umjetnik WHERE ID_Umjetnik = ?";

        PreparedStatement statement = conn.prepareStatement(deleteQuery);
        statement.setInt(1, umjetnik.getID_Umjetnik());

        int rowsAffected = statement.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Umjetnik deleted successfully.");
        } else {
            System.out.println("No rows deleted. Umjetnik not found.");
        }
    }
}




