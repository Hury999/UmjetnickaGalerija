package com.example.galerija;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;


public class MainController {

    public MainController() {
        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Djelo;
    }

    DBConnection dbConnection = new DBConnection();

    CurrentMode currentMode = new CurrentMode();
    private ObservableList<Object> items = FXCollections.observableArrayList(); // Initial type is Object
    @FXML
    private MFXButton btnDjelo;

    @FXML
    private MFXButton btnDvorana;

    @FXML
    private MFXButton btnIzlozba;

    @FXML
    private MFXButton btnLokacija;

    @FXML
    private MFXButton btnPravac;

    @FXML
    private MFXButton btnTehnika;

    @FXML
    private MFXButton btnUmjetnik;


    @FXML
    private void handleBtnDjeloClick(ActionEvent event) {
        // Method logic for btnDjelo click
        System.out.println("Button Djelo clicked");

        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Djelo;

        Class<?> objectType = Djelo.class;

        generateColumnsForType(objectType);

        loadDataForDjelo();

    }

    @FXML
    private void handleBtnDvoranaClick(ActionEvent event) {
        // Method logic for btnDvorana click
        System.out.println("Button Dvorana clicked");

        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Dvorana;

        Class<?> objectType = Dvorana.class;

        generateColumnsForType(objectType);

        loadDataForDvorana();
    }

    @FXML
    private void handleBtnIzlozbaClick(ActionEvent event) {
        // Method logic for btnIzlozba click

        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Izlozba;

        Class<?> objectType = Izlozba.class;

        generateColumnsForType(objectType);

        loadDataForIzlozba();

    }

    @FXML
    private void handleBtnLokacijaClick(ActionEvent event) {
        // Method logic for btnLokacija click
        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Lokacija;
    }

    @FXML
    private void handleBtnPravacClick(ActionEvent event) {
        // Method logic for btnPravac click
        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Pravac;
    }

    @FXML
    private void handleBtnTehnikaClick(ActionEvent event) {
        // Method logic for btnTehnika click
        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Tehnika;
    }

    @FXML
    private void handleBtnUmjetnikClick(ActionEvent event) {
        // Method logic for btnUmjetnik click
        currentMode.CurrentMode = CurrentMode.CurrentModeEnum.Umjetnik;
    }


    @FXML
    private TableView<Object> tableView;

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        // TODO: Implement button click action
    }

    @FXML
    protected void onBtnConnectClick(ActionEvent actionEvent) {
        // TODO: Implement button click action
    }


    @FXML
    public void initialize() {



        hookUpButtonEvents();

        setUpTableView(tableView);
        setUpEventsForTableView(tableView);

        // ...

        tableView.setEditable(true);

        Class<?> objectType = Djelo.class;

        generateColumnsForType(objectType);

        loadDataForDjelo();

        openDependencyWindow("New Win", Umjetnik.class);

        openSelectWindow("Select Djelo", dbConnection.GetAllDjelo());
    }

    private void hookUpButtonEvents() {
        // Hook up btnDjelo click event
        btnDjelo.setOnAction(this::handleBtnDjeloClick);

        // Hook up btnDvorana click event
        btnDvorana.setOnAction(this::handleBtnDvoranaClick);

        // Hook up btnIzlozba click event
        btnIzlozba.setOnAction(this::handleBtnIzlozbaClick);

        // Hook up btnLokacija click event
        btnLokacija.setOnAction(this::handleBtnLokacijaClick);

        // Hook up btnPravac click event
        btnPravac.setOnAction(this::handleBtnPravacClick);

        // Hook up btnTehnika click event
        btnTehnika.setOnAction(this::handleBtnTehnikaClick);

        // Hook up btnUmjetnik click event
        btnUmjetnik.setOnAction(this::handleBtnUmjetnikClick);
    }

    private void generateColumnsForType(Class<?> objectType) {
        Field[] fields = objectType.getDeclaredFields();

        // Clear existing columns
        tableView.getColumns().clear();

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
            } else if (field.getType() == ObjectProperty.class && field.getGenericType().getTypeName().equals("javafx.beans.property.ObjectProperty<java.util.Date>")) {
                TableColumn<Object, Date> column = new TableColumn<>(field.getName());
                column.setCellValueFactory(new PropertyValueFactory<>(field.getName()));
                tableView.getColumns().add(column);
            }
            // Add more else if blocks for other types as needed
        }
    }


    private <T> void setUpTableView(TableView<T> tableView) {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private <T> void setUpEventsForTableView(TableView<T> tableView) {
        tableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DELETE) {
                deleteSelectedRows();
            }
        });

        // Create a listener for double-click events to add new row
        tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                int lastRowIndex = tableView.getItems().size() - 1;
                if (tableView.getSelectionModel().isSelected(lastRowIndex)) {
                    // Double-click on the last row

                    //TODO:DO ADD LOGIC FOR EVERY TYPE IN DB
                    // Create a new empty Djelo object for the new row
                    Djelo newDjelo = new Djelo(4, "NoviNaslov", 3,
                            4, 5, 6, 7, 3);


                    // Refresh the TableView to display the new row
                    tableView.refresh();

                    // Set focus on the newly added row for editing
                    /* tableView.scrollTo(newDjelo);*/

                    // Implement any additional logic for handling the new row
                    // For example, you can start editing the new row's cells here
                }
            }
        });


    }

    private void loadDataForDjelo() {
        ObservableList<Djelo> djela = dbConnection.GetAllDjelo();
        items.setAll(djela);
        tableView.setItems(items);
    }

    private void loadDataForDvorana() {
        ObservableList<Dvorana> dvorane = dbConnection.GetAllDvorana();
        items.setAll(dvorane);
        tableView.setItems(items);
    }

    private void loadDataForIzlozba() {
        ObservableList<Izlozba> izlozbe = dbConnection.GetAllIzlozba();
        items.setAll(izlozbe);
        tableView.setItems(items);
    }


    private void deleteSelectedRows() {
        ObservableList<Object> selectedRows = tableView.getSelectionModel().getSelectedItems();

       /* for (Djelo djelo : selectedRows) {
            dbConnection.deleteDjelo(djelo);
        }*/

        tableView.getItems().removeAll(selectedRows);
        loadDataForDjelo();
    }


    public static Object createInstance(CurrentMode.CurrentModeEnum mode) {
        Object newItem = new Object();

        //TODO:FIND NEXT ID IN SEQUENCE IN DATABASE,ALSO
        //OPEN ALL SUBSEQUENT FORMS AND ASK USER IF ITS
        //RELATED TO CURRENTLY PRESENT RECORD IN DB, BY ID

        switch (mode) {
            case Djelo:

                break;
            case Dvorana:

                break;
            case Izlozba:

                break;
            case Lokacija:

                break;
            case Pravac:

                break;
            case Tehnika:

                break;
            case Umjetnik:

                break;
            default:
                throw new IllegalArgumentException("Invalid mode value");
        }

        return newItem;
    }


    private void openDependencyWindow(String nameOfWindow, Object objectToGenerateFor) {

        try {
            // Open new window
            Stage newStage = new Stage();
            FXMLLoader newFxmlLoader = new FXMLLoader(MainApplication.class.getResource("DependencyFillForm.fxml"));
            Parent root = newFxmlLoader.load();
            DependencyFillFormController controller = newFxmlLoader.getController();

            // Get the number of fields of type int and String using reflection
            Field[] fields = objectToGenerateFor.getClass().getDeclaredFields();
            int numberOfFields = 0;

            for (Field field : fields) {
                if (field.getType() == int.class || field.getType() == String.class) {
                    numberOfFields++;
                }
            }

            int numHBoxes = numberOfFields - 2;

            // Call the initialize method of the controller and pass the number of HBoxes
            controller.initialize(new Umjetnik(1, "hare", "Muharemovic", "dasd"));

            // Calculate the height of the window based on the number of HBoxes
            int windowHeight = numHBoxes * 70 + 230; // Adjust the values as per requirements

            Scene newScene = new Scene(root, 600, windowHeight); // Set the height dynamically
            newStage.setMinWidth(250);
            newStage.setMinHeight(windowHeight);
            newStage.setTitle(nameOfWindow);
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openSelectWindow(String nameOfWindow, ObservableList<? extends Object> listOfObjectToShow) {

        try {
            // Open new window
            Stage newStage = new Stage();
            FXMLLoader newFxmlLoader = new FXMLLoader(MainApplication.class.getResource("selectForm.fxml"));
            Parent root = newFxmlLoader.load();
            SelectFormController controller = newFxmlLoader.getController();

            // Call the initialize method of the controller and pass the number of HBoxes
            controller.initialize(listOfObjectToShow);

            Scene newScene = new Scene(root, 600, 600); // Set the height dynamically
            newStage.setMinWidth(250);
            newStage.setMinHeight(600);
            newStage.setTitle(nameOfWindow);
            newStage.setScene(newScene);
            newStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}