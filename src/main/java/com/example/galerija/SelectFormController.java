package com.example.galerija;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;

public class SelectFormController {

    public SelectFormController() {

    }

    @FXML
    private TableView<Object> tableView;

    @FXML
    private MFXButton selectButton;

    private ObservableList<Object> items = FXCollections.observableArrayList();

    private Object parentForm;

    @FXML
    public void initialize(ObservableList<? extends Object> listOfObjectToShow, Object parentForm) {

        this.parentForm = parentForm;

        if (listOfObjectToShow.size() > 0) {
            // Get the type of the first object in the list
            Object firstObject = listOfObjectToShow.get(0);

            Class<?> objectType = firstObject.getClass();

            UtilsForTableView.GenerateColumnsForType(objectType, tableView);

            //LOAD DATA FOR object FROM DB
            DBConnection dbConnection = new DBConnection();
            ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

            items.setAll(objects);
            tableView.setItems(items);
        }

        // Add a listener a Select button
        selectButton.setOnAction(event -> {

            handleSelectButtonClick();
        });

    }

    private void handleSelectButtonClick()
    {
        // Get the selected item from the TableView
        Object selectedObjectRowData = tableView.getSelectionModel().getSelectedItem();

        if (selectedObjectRowData != null) {
            // Now you can work with the selectedObject
            if(parentForm instanceof DependencyFillFormController dependencyFillFormController)
            {
                dependencyFillFormController.setSelectedObjectFromSelectFrom(selectedObjectRowData);
            }
        }
    }

    private void generateColumnsForType(Class<?> objectType) {
        Field[] fields = objectType.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Set accessibility to true for private fields

            if (field.getType() == IntegerProperty.class) {
                TableColumn<Object, Integer> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            } else if (field.getType() == StringProperty.class) {
                TableColumn<Object, String> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            }
            // Add more else if blocks for other types as needed
        }
    }


}
