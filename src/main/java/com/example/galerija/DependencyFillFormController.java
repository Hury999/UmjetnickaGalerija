package com.example.galerija;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import java.io.IOException;

public class DependencyFillFormController {

    @FXML
    public MFXButton btnSave;
    @FXML
    private VBox containerVBox;
    @FXML
    private Text objectTypeText;

    private Object mainObject;
    private void onSaveButtonClick(ActionEvent actionEvent) {

            String queryToInsertToDB = generateInsertQuery(mainObject);

            
    }
    @FXML
    public void initialize(Object object) {

        mainObject = object;

        btnSave.setOnAction(this::onSaveButtonClick);

        objectTypeText.setText(object.getClass().getSimpleName());

        Class<?> objectClass = object.getClass();

        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            try {
                Method getterMethod = objectClass.getMethod("get" + capitalizedFieldName);
                Object value = getterMethod.invoke(object);

                createAndBindHBox(fieldName + ":", value.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createAndBindHBox(String label, String value) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TemplateHBox.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            HBox hbox = (HBox) anchorPane.getChildren().get(0);

            // Set label and value in the HBox
            MFXTextField textField = (MFXTextField) hbox.lookup("#textField");
            textField.setFloatingText(label);
            textField.setText(value);

            // Check if the property contains "id" and make the text field read-only
            if (label.toLowerCase().contains("id")) {
                textField.setEditable(false);
            }

            containerVBox.getChildren().add(hbox);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HBox createClonedHBox() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TemplateHBox.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            return (HBox) anchorPane.getChildren().get(0);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String generateInsertQuery(Object object) {
        Class<?> objectClass = object.getClass();
        String tableName = objectClass.getSimpleName();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO ").append(tableName).append(" VALUES (");

        Field[] fields = objectClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            try {
                Object value = field.get(object);

                // Append the value to the query string
                if (value instanceof String) {
                    queryBuilder.append("'").append(value).append("'");
                } else {
                    queryBuilder.append(value);
                }

                // Add a comma separator between values except for the last value
                if (i < fields.length - 1) {
                    queryBuilder.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        queryBuilder.append(")");

        return queryBuilder.toString();
    }

}
