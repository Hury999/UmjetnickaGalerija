package com.example.galerija;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            String connectQuery = "SELECT Naslov FROM djelo";

            Statement statement = conn.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            // Clear the previous text from welcomeText
            welcomeText.setText("");

            // Loop through the result set and append each value of Naslov to welcomeText
            while (queryOutput.next()) {
                String naslov = queryOutput.getString("Naslov");
                welcomeText.setText(welcomeText.getText() + naslov + "\n");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }
}