package com.example.galerija;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LocalDateStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.lang.System.err;
import static java.lang.System.in;


public class DependencyFillFormController {

    private ObjectProperty<ActiveDatabaseTable> activeDatabaseTable = new SimpleObjectProperty<ActiveDatabaseTable>(ActiveDatabaseTable.Djelo);

    List<MFXTextField> generatedTextFields = new ArrayList<MFXTextField>();

    @FXML
    public MFXButton btnSave;
    @FXML
    public MFXCheckbox checkboxUpdate;
    @FXML
    private VBox containerVBox;
    @FXML
    private Text objectTypeText;
    @FXML
    private Text errorText;


    private Object mainObject;


    private Object selectedObjectFromSelectFrom;

    public Object getSelectedObjectFromSelectFrom() {
        return selectedObjectFromSelectFrom;
    }

    public void setSelectedObjectFromSelectFrom(Object selectedObjectFromSelectFrom) {

        this.selectedObjectFromSelectFrom = selectedObjectFromSelectFrom;

        switch (selectedObjectFromSelectFrom) {
            case Djelo djelo:
                setTextForMatchingFields(generatedTextFields,djelo);
                break;

            case Dvorana dvorana:
                // Iterate over the list of text fields
                setTextForMatchingFields(generatedTextFields,dvorana);
                break;

            case Izlozba izlozba:
                // Iterate over the list of text fields
                setTextForMatchingFields(generatedTextFields,izlozba);
                break;

            case Lokacija lokacija:
                setTextForMatchingFields(generatedTextFields,lokacija);
                break;

            case Pravac pravac:
                setTextForMatchingFields(generatedTextFields,pravac);
                break;

            case Tehnika tehnika:
                setTextForMatchingFields(generatedTextFields,tehnika);
                break;

            case Umjetnik umjetnik:
                setTextForMatchingFields(generatedTextFields,umjetnik);
                break;

            default:
        }
    }


    public void setTextForMatchingFields(List<MFXTextField> generatedTextFields, Object object) {
        for (MFXTextField txtField : generatedTextFields) {
            String floatingText = txtField.getFloatingText();

            if (floatingText != null && floatingText.toLowerCase().startsWith("id_")) {
                String methodName = "getID_" + object.getClass().getSimpleName();

                try {
                    Method[] methods = object.getClass().getMethods();
                    Method matchingMethod = null;

                    for (Method method : methods) {
                        if (method.getName().equalsIgnoreCase(methodName) && method.getParameterCount() == 0) {
                            matchingMethod = method;
                            break;
                        }
                    }

                    if (matchingMethod != null) {
                        Object result = matchingMethod.invoke(object);
                        if (result != null) {
                            if(txtField.getFloatingText().contains("ID_" + object.getClass().getSimpleName()))
                            {
                                txtField.setText(result.toString());
                                break;
                            }
                        }
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                    // Handle any exceptions that may occur during reflection
                }
            }
        }
    }

    @FXML
    private void onSaveButtonClick(ActionEvent actionEvent) {

        String queryToInsertToDB;
        if(checkboxUpdate.isSelected())
            queryToInsertToDB = generateUpdateQuery(mainObject);
        else
            queryToInsertToDB = generateInsertQuery(mainObject);

        DBConnection dbConnectionw = new DBConnection();

        try {
            // Connect to the database
            Connection conn = DriverManager.getConnection("jdbc:MySql://127.0.0.1:3306/mojaBaza", "root", "hare");

            PreparedStatement statement = conn.prepareStatement(queryToInsertToDB);

            statement.executeUpdate();

            errorText.setFill(Color.GREEN);

            System.out.println("Object inserted successfully.");

            if(checkboxUpdate.isSelected())
                errorText.setText("Object updated successfully.");
            else
                errorText.setText("Object inserted successfully.");

            MFXTextField idTextFieldMainObject = findTextField(generatedTextFields,mainObject);

            String TextFieldIDText = idTextFieldMainObject.getText();

            if(!checkIfIDExistInDB(mainObject,Integer.parseInt(TextFieldIDText))) {
                errorText.setFill(Color.RED);
                errorText.setText("Object "+ mainObject.getClass().getSimpleName() + " with an ID:" + TextFieldIDText +" doesn't exist in DB!");
            }


        } catch (SQLIntegrityConstraintViolationException e) {
            errorText.setFill(Color.RED);
            // Handle duplicate key error
            if (e.getMessage().contains("Duplicate entry")) {
                System.out.println("Duplicate key error occurred.");

                // Extract the object name from the error message
                String errorMessage = e.getMessage();
                String valueID = errorMessage.substring(errorMessage.indexOf("'") + 1, errorMessage.lastIndexOf("'"));

                String errorTextMessage = "Duplicate key error occurred for value: " + valueID;
                errorText.setText(errorTextMessage);
            }
            else if (e.getMessage().contains("a foreign key constraint fails")) {
                System.out.println("Foreign key constraint fails.");

                // Extract the referenced table and key information from the error message
                String errorMessage = e.getMessage();
                String tableInfo = errorMessage.substring(errorMessage.indexOf("FOREIGN KEY (`") + 14, errorMessage.indexOf("`) REFERENCES"));
                String keyInfo = errorMessage.substring(errorMessage.indexOf("REFERENCES `") + 12, errorMessage.indexOf("` (`"));

                String errorTextMessage = "Foreign key constraint fails for table: " + tableInfo + ", key: " + keyInfo;
                errorText.setText(errorTextMessage);
            }
            else {
                // Handle other integrity constraint violation errors
                System.out.println("Integrity constraint violation error occurred.");
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            // Handle other SQL errors
            errorText.setFill(Color.RED);
            errorText.setText(e.getMessage());
            e.printStackTrace();
        }
    }


    public MFXTextField findTextField(List<MFXTextField> generatedTextFields, Object object) {
        return generatedTextFields.stream()
                .filter(textField -> textField.getFloatingText().contains("ID_" + object.getClass().getSimpleName()))
                .findFirst()
                .orElse(null);
    }

    private boolean checkIfIDExistInDB(Object object, int ID) {

        Class<?> clazz = object.getClass();

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> dbObjects = dbConnection.getAllDataBaseDataForObject(clazz);

        try {
            Method getterMethod = clazz.getMethod("getID_" + clazz.getSimpleName());
            for (Object obj : dbObjects) {
                int objID = (int) getterMethod.invoke(obj);
                if (objID == ID) {
                    return true;
                }
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        return false;
    }

    @FXML
    public void onSelectButtonClick(ActionEvent actionEvent) throws IOException {

        switch (activeDatabaseTable.get()) {
            case Djelo:
                UtilsForm.openSelectForm(Djelo.class, this);
                break;
            case Dvorana:
                UtilsForm.openSelectForm(Dvorana.class,this);
                break;
            case Izlozba:
                UtilsForm.openSelectForm(Izlozba.class,this);
                break;
            case Lokacija:
                UtilsForm.openSelectForm(Lokacija.class,this);
                break;
            case Pravac:
                UtilsForm.openSelectForm(Pravac.class,this);
                break;
            case Tehnika:
                UtilsForm.openSelectForm(Tehnika.class,this);
                break;
            case Umjetnik:
                UtilsForm.openSelectForm(Umjetnik.class,this);
                break;
            default:
                throw new IllegalArgumentException("Unsupported ActiveDatabaseTable: " + activeDatabaseTable.get());
        }
    }

    private void checkboxUpdateSelectedChanged(boolean oldValue, boolean newValue)
    {
        if (newValue) {
            // Checkbox is checked
            btnSave.setText("Update" + mainObject.getClass().getSimpleName());

        } else {
            // Checkbox is unchecked
            btnSave.setText("Insert into DB");
        }
    }

    @FXML
    public void initialize(Object object) {

        mainObject = object;

        btnSave.setOnAction(this::onSaveButtonClick);

        objectTypeText.setText("");

        checkboxUpdate.selectedProperty().addListener((observable, oldValue, newValue) -> {
            checkboxUpdateSelectedChanged(oldValue,newValue);
        });

        Class<?> objectClass = object.getClass();

        Field[] fields = objectClass.getDeclaredFields();
        for (Field field : fields) {
            String fieldName = field.getName();
            String capitalizedFieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

            try {
                Method getterMethod = objectClass.getMethod("get" + capitalizedFieldName);
                Object value = getterMethod.invoke(object);

                createAndBindHBox(fieldName + ":", object, field);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void createAndBindHBox(String label, Object object, Field field) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("TemplateHBox.fxml"));
        try {
            AnchorPane anchorPane = fxmlLoader.load();
            HBox hbox = (HBox) anchorPane.getChildren().get(0);

            // Set label and object in the HBox
            MFXTextField textField = (MFXTextField) hbox.lookup("#textField");
            textField.setFloatingText(label);
            textField.setText(object.toString());


            // Get the Class object
            Class<?> mainObjectClass = mainObject.getClass();

            String test = capitalizeFirstLetter(textField.getFloatingText()).trim() + "Property";

            Method getterMethod = mainObjectClass.getDeclaredMethod(test);
            getterMethod.setAccessible(true);

            Object propertyValue = getterMethod.invoke(mainObject);

            StringProperty propertyToBind = new SimpleStringProperty();

            // Using setOnMouseClicked method
            textField.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1) {
                    System.out.println("Text field clicked using setOnMouseClicked");

                    MFXTextField clickedTextField = (MFXTextField) event.getSource();

                    String floatingText = clickedTextField.getFloatingText().trim();

                    Pattern pattern = Pattern.compile("ID_(\\w+):?");

                    Matcher matcher = pattern.matcher(floatingText);

                    if (matcher.matches()) {
                        String extractedValue = matcher.group(1); // Extracts "Djelo"
                        try {
                            activeDatabaseTable.setValue(ActiveDatabaseTable.valueOf(extractedValue));

                            switch (extractedValue) {
                                case "Djelo":
                                    objectTypeText.setText("Djelo selected");
                                    break;
                                case "Dvorana":
                                    objectTypeText.setText("Dvorana selected");
                                    break;
                                case "Izlozba":
                                    objectTypeText.setText("Izlozba selected");
                                    break;
                                case "Lokacija":
                                    objectTypeText.setText("Lokacija selected");
                                    break;
                                case "Pravac":
                                    objectTypeText.setText("Pravac selected");
                                    break;
                                case "Tehnika":
                                    objectTypeText.setText("Tehnika selected");
                                    break;
                                case "Umjetnik":
                                    objectTypeText.setText("Umjetnik selected");
                                    break;
                            }

                        } catch (IllegalArgumentException e) {
                            // Handle if the value cannot be cast to the enum
                        }
                    }
                }
            });

            if (propertyValue instanceof StringProperty) {
                // The getter method returns a StringProperty
                propertyToBind = (StringProperty) propertyValue;

                // Invoke the getter method on the instance to get the StringProperty
                textField.textProperty().bindBidirectional(propertyToBind);

            } else if (propertyValue instanceof IntegerProperty) {
                // The getter method returns an IntegerProperty
                IntegerProperty integerProperty = (IntegerProperty) propertyValue;
                textField.textProperty().bindBidirectional(integerProperty, new NumberStringConverter());

            } else if (field.getType() == ObjectProperty.class && field.getGenericType().getTypeName().equals("javafx.beans.property.ObjectProperty<java.util.Date>")) {

                ObjectProperty<Date> dateProperty = (ObjectProperty<Date>) propertyValue;

                // Format the date as a string
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Choose your desired date format
                String dateString = dateFormat.format(dateProperty.get());

                // Set the textField text to the formatted date string
                textField.setText(dateString);

            } else {
                // The getter method returns a different property type
                // Handle the case accordingly
            }

            if(textField != null)
                generatedTextFields.add(textField);


            containerVBox.getChildren().add(hbox);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }


    public static String capitalizeFirstLetter(String str) {
        return str.isEmpty() ? str : Character.toUpperCase(str.charAt(0)) + str.substring(1, str.length() - 1);
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

                if (value instanceof IntegerProperty) {
                    // Get the actual integer value from the IntegerProperty
                    int intValue = ((IntegerProperty) value).get();
                    queryBuilder.append(intValue);
                } else if (value instanceof StringProperty) {
                    // Get the actual string value from the StringProperty
                    String strValue = ((StringProperty) value).get();
                    if (strValue != null) {
                        queryBuilder.append("'").append(strValue).append("'");
                    } else {
                        queryBuilder.append("''");
                    }
                } else if (value instanceof ObjectProperty && field.getType() == ObjectProperty.class && field.getGenericType().getTypeName().equals("javafx.beans.property.ObjectProperty<java.util.Date>")) {
                    // Get the actual Date value from the ObjectProperty<Date>
                    ObjectProperty<Date> dateProperty = (ObjectProperty<Date>) value;
                    Date dateValue = dateProperty.get();

                    if (dateValue != null) {
                        // Format the date as a string
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); // Choose your desired date format
                        String dateString = dateFormat.format(dateValue);

                        queryBuilder.append("'").append(dateString).append("'");
                    } else {
                        queryBuilder.append("''");
                    }
                } else {
                    // For other types, you need to handle them accordingly
                    // or throw an exception if unsupported
                    throw new UnsupportedOperationException("Unsupported property type: " + value.getClass().getSimpleName());
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

    public String generateUpdateQuery(Object object) {
        Class<?> objectClass = object.getClass();
        String tableName = objectClass.getSimpleName();

        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("UPDATE ").append(tableName).append(" SET ");

        Field[] fields = objectClass.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            field.setAccessible(true);

            try {
                Object value = field.get(object);

                if (value instanceof IntegerProperty) {
                    // Get the actual integer value from the IntegerProperty
                    int intValue = Integer.parseInt(returnTextValueOfTextFieldWhichContainsSpecificFloatingText(generatedTextFields,field.getName()));
                    queryBuilder.append(field.getName()).append(" = ").append(intValue);
                } else if (value instanceof StringProperty) {
                    // Get the actual string value from the StringProperty
                    String strValue = returnTextValueOfTextFieldWhichContainsSpecificFloatingText(generatedTextFields,field.getName());
                    if (strValue != null) {
                        queryBuilder.append(field.getName()).append(" = '").append(strValue).append("'");
                    } else {
                        queryBuilder.append(field.getName()).append(" = ''");
                    }
                } else if (value instanceof ObjectProperty && field.getType() == ObjectProperty.class && field.getGenericType().getTypeName().equals("javafx.beans.property.ObjectProperty<java.util.Date>")) {
                    // Get the actual Date value from the ObjectProperty<Date>
                    ObjectProperty<Date> dateProperty = (ObjectProperty<Date>) value;
                    Date dateValue = dateProperty.get();

                    queryBuilder.append(field.getName()).append(" = '").append(returnTextValueOfTextFieldWhichContainsSpecificFloatingText(generatedTextFields,field.getName())).append("'");

                } else {
                    // For other types, you need to handle them accordingly
                    // or throw an exception if unsupported
                    throw new UnsupportedOperationException("Unsupported property type: " + value.getClass().getSimpleName());
                }

                // Add a comma separator between values except for the last value
                if (i < fields.length - 1) {
                    queryBuilder.append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        String idFieldName = "ID_" + objectClass.getSimpleName();
        String valueOfID = retrieveIDValue(object, idFieldName);
        queryBuilder.append(" WHERE ").append(idFieldName).append(" = ").append(valueOfID);


        return queryBuilder.toString();
    }

    private String returnTextValueOfTextFieldWhichContainsSpecificFloatingText(List<MFXTextField> generatedTextFields, String textWhichMightContain) {

        MFXTextField foundTextField = generatedTextFields.stream()
                .filter(textField -> textField.getFloatingText().contains(textWhichMightContain))
                .findFirst()
                .orElse(null);

        if(foundTextField != null)
            return foundTextField.getText();

        return null;
    }

    private String retrieveIDValue(Object object, String idFieldName) {
        try {
            Method getterMethod = object.getClass().getMethod("get" + idFieldName);
            Object idValue = getterMethod.invoke(object);
            return idValue.toString();
        } catch (NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
            e.printStackTrace();
            return "";
        }
    }

}
