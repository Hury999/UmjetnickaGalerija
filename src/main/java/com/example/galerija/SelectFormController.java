package com.example.galerija;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;

public class SelectFormController {

    public SelectFormController()
    {

    }

    @FXML
    private TableView<Object> tableView;

    private ObservableList<Object> items = FXCollections.observableArrayList();
    @FXML
    public void initialize(ObservableList<? extends Object> listOfObjectToShow) {



        // Get the type of the first object in the list
        Object firstObject = listOfObjectToShow.get(0);

        Class<?> objectType =  firstObject.getClass();

        generateColumnsForType(objectType);

        if(objectType == Djelo.class)
        {
            //TODO:LOAD DATA FOR DJELO FROM DB
            DBConnection dbConnection = new DBConnection();
            ObservableList<Djelo> djela =  dbConnection.GetAllDjelo();

            items.setAll(djela);
            tableView.setItems(items);



        } else if (objectType == Dvorana.class) {
            DBConnection dbConnection = new DBConnection();
            ObservableList<Djelo> djela =  dbConnection.GetAllDjelo();
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
