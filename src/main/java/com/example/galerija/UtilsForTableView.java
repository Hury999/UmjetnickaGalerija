package com.example.galerija;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.lang.reflect.Field;
import java.util.Date;

public class UtilsForTableView {

    public static void GenerateColumnsForType(Class<?> objectType, TableView tableView) {
        Field[] fields = objectType.getDeclaredFields();

        // Clear existing columns
        tableView.getColumns().clear();

        for (Field field : fields) {
            field.setAccessible(true); // Set accessibility to true for private fields

            if (field.getType() == IntegerProperty.class) {
                TableColumn<Object, Integer> column = new TableColumn<>(field.getName());
                column.setEditable(true);
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            } else if (field.getType() == StringProperty.class) {
                TableColumn<Object, String> column = new TableColumn<>(field.getName());
                column.setEditable(true);
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            } else if (field.getType() == ObjectProperty.class && field.getGenericType().getTypeName().equals("javafx.beans.property.ObjectProperty<java.util.Date>")) {
                TableColumn<Object, Date> column = new TableColumn<>(field.getName());
                column.setEditable(true);
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            }
            // Add more else if blocks for other types as needed
        }


    }

}
