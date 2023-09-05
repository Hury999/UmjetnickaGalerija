package com.example.galerija;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UtilsForm {

    public static void openSelectForm(Class<?> clazz, Object parentForm) {
        try {
            Stage newStage = new Stage();
            FXMLLoader newFxmlLoader = new FXMLLoader(clazz.getResource("selectForm.fxml"));
            Parent root = newFxmlLoader.load();

            SelectFormController selectFormController = newFxmlLoader.getController();

            DBConnection dbConnection = new DBConnection();

            selectFormController.initialize(dbConnection.getAllDataBaseDataForObject(clazz),parentForm);

            Scene newScene = new Scene(root, 600, 400); // Set the height dynamically
            newStage.setMinWidth(250);
            newStage.setMinHeight(150);

            String className = clazz.getSimpleName(); // Get the class name
            newStage.setTitle("Select " + className);

            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Handle or log the exception as needed
        }
    }
}
