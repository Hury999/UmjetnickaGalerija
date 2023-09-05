package com.example.galerija;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Parent;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Date;


public class MainController {

    public MainController() {

    }

    DBConnection dbConnection = new DBConnection();

    CurrentMode currentMode = new CurrentMode();
    private ObservableList<Object> items = FXCollections.observableArrayList(); // Initial type is Object

    @FXML
    private MFXTextField textFieldSearch;

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
    private MFXButton btnSearch;

    @FXML
    private void handleBtnSearchClick(ActionEvent event) {
        String searchText = textFieldSearch.getText();
        ActiveDatabaseTable activeTable = ActiveDatabaseTableSingleton.getInstance().getActiveTable();

        ObservableList<?> objects = getObjectsForActiveTable(activeTable);
        items.setAll(objects);
        tableView.setItems(items);

        FilteredList<Object> filteredList = new FilteredList<>(tableView.getItems());
        filteredList.setPredicate(object -> isObjectMatchingSearchText(object, activeTable, searchText));
        tableView.setItems(filteredList);
    }

    private ObservableList<?> getObjectsForActiveTable(ActiveDatabaseTable activeTable) {
        switch (activeTable) {
            case Djelo:
                return dbConnection.getAllDataBaseDataForObject(Djelo.class);
            case Dvorana:
                return dbConnection.getAllDataBaseDataForObject(Dvorana.class);
            case Izlozba:
                return dbConnection.getAllDataBaseDataForObject(Izlozba.class);
            case Lokacija:
                return dbConnection.getAllDataBaseDataForObject(Lokacija.class);
            case Pravac:
                return dbConnection.getAllDataBaseDataForObject(Pravac.class);
            case Tehnika:
                return dbConnection.getAllDataBaseDataForObject(Tehnika.class);
            case Umjetnik:
                return dbConnection.getAllDataBaseDataForObject(Umjetnik.class);
            default:
                return FXCollections.emptyObservableList();
        }
    }

    private boolean isObjectMatchingSearchText(Object object, ActiveDatabaseTable activeTable, String searchText) {
        if (searchText.isEmpty()) {
            return true; // Display all elements if search text is empty
        }

        switch (activeTable) {
            case Djelo:
                Djelo djelo = (Djelo) object;
                return isDjeloMatchingSearchText(djelo, searchText);
            case Dvorana:
                Dvorana dvorana = (Dvorana) object;
                return isDvoranaMatchingSearchText(dvorana, searchText);
            case Izlozba:
                Izlozba izlozba = (Izlozba) object;
                return isIzlozbaMatchingSearchText(izlozba, searchText);
            case Lokacija:
                Lokacija lokacija = (Lokacija) object;
                return isLokacijaMatchingSearchText(lokacija, searchText);
            case Pravac:
                Pravac pravac = (Pravac) object;
                return isPravacMatchingSearchText(pravac, searchText);
            case Tehnika:
                Tehnika tehnika = (Tehnika) object;
                return isTehnikaMatchingSearchText(tehnika, searchText);
            case Umjetnik:
                Umjetnik umjetnik = (Umjetnik) object;
                return isUmjetnikMatchingSearchText(umjetnik, searchText);
            default:
                return false;
        }
    }

    private boolean isDjeloMatchingSearchText(Djelo djelo, String searchText) {
        return (Integer.toString(djelo.getGodinaNastanka()).toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(djelo.getID_Djelo()).toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(djelo.getID_Izlozba()).toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(djelo.getID_Lokacija()).toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(djelo.getID_Pravac()).toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(djelo.getID_Umjetnik()).toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(djelo.getID_Tehnika()).toLowerCase().contains(searchText.toLowerCase()) ||
                djelo.getNaslov().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean isDvoranaMatchingSearchText(Dvorana dvorana, String searchText) {
        return (Integer.toString(dvorana.getID_Dvorana()).toLowerCase().contains(searchText.toLowerCase()) ||
                dvorana.getNazivDvorana().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean isIzlozbaMatchingSearchText(Izlozba izlozba, String searchText) {
        return (Integer.toString(izlozba.getID_Izlozba()).toLowerCase().contains(searchText.toLowerCase()) ||
                izlozba.getPocetakDatum().toString().toLowerCase().contains(searchText.toLowerCase()) ||
                izlozba.getZavrsetakDatum().toString().toLowerCase().contains(searchText.toLowerCase()) ||
                izlozba.getTip().toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(izlozba.getID_Dvorana()).toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean isLokacijaMatchingSearchText(Lokacija lokacija, String searchText) {
        return (Integer.toString(lokacija.getID_Lokacija()).toLowerCase().contains(searchText.toLowerCase()) ||
                lokacija.getNazivLokacija().toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(lokacija.getBrojProstorije()).toLowerCase().contains(searchText.toLowerCase()) ||
                Integer.toString(lokacija.getID_Dvorana()).toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean isPravacMatchingSearchText(Pravac pravac, String searchText) {
        return (Integer.toString(pravac.getID_Pravac()).toLowerCase().contains(searchText.toLowerCase()) ||
                pravac.getNazivPravac().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean isTehnikaMatchingSearchText(Tehnika tehnika, String searchText) {
        return (Integer.toString(tehnika.getID_Tehnika()).toLowerCase().contains(searchText.toLowerCase()) ||
                tehnika.getNazivTehnika().toLowerCase().contains(searchText.toLowerCase()));
    }

    private boolean isUmjetnikMatchingSearchText(Umjetnik umjetnik, String searchText) {
        return (Integer.toString(umjetnik.getID_Umjetnik()).toLowerCase().contains(searchText.toLowerCase()) ||
                umjetnik.getIme().toLowerCase().contains(searchText.toLowerCase()) ||
                umjetnik.getPrezime().toLowerCase().contains(searchText.toLowerCase()) ||
                umjetnik.getBiografija().toLowerCase().contains(searchText.toLowerCase()));
    }

    @FXML
    private void handleBtnDjeloClick(ActionEvent event) {
        // Method logic for btnDjelo click
        System.out.println("Button Djelo clicked");
        currentTableText.setText("Currently opened: Djelo");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Djelo);

        Class<?> objectType = Djelo.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);

    }

    @FXML
    private void handleBtnDvoranaClick(ActionEvent event) {
        // Method logic for btnDvorana click
        System.out.println("Button Dvorana clicked");
        currentTableText.setText("Currently opened: Dvorana");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Dvorana);

        Class<?> objectType = Dvorana.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);

    }

    @FXML
    private void handleBtnIzlozbaClick(ActionEvent event) {
        // Method logic for btnIzlozba click
        currentTableText.setText("Currently opened: Izlozba");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Izlozba);

        Class<?> objectType = Izlozba.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);

    }

    @FXML
    private void handleBtnLokacijaClick(ActionEvent event) {
        // Method logic for btnLokacija click
        currentTableText.setText("Currently opened: Lokacija");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Lokacija);

        Class<?> objectType = Lokacija.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);
    }

    @FXML
    private void handleBtnPravacClick(ActionEvent event) {
        // Method logic for btnPravac click
        currentTableText.setText("Currently opened: Pravac");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Pravac);

        Class<?> objectType = Pravac.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);
    }

    @FXML
    private void handleBtnTehnikaClick(ActionEvent event) {
        // Method logic for btnTehnika click
        currentTableText.setText("Currently opened: Tehnika");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Tehnika);

        Class<?> objectType = Tehnika.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);
    }

    @FXML
    private void handleBtnUmjetnikClick(ActionEvent event) {
        // Method logic for btnUmjetnik click
        currentTableText.setText("Currently opened: Umjetnik");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Umjetnik);

        Class<?> objectType = Umjetnik.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);
    }


    @FXML
    private TableView<Object> tableView;

    @FXML
    private Text currentTableText;

    @FXML
    private Text errorText;
    public StringProperty tekstt = new SimpleStringProperty("haree");

    @FXML
    private Label welcomeText;

    @FXML
    public void initialize() {

        currentTableText.setText("Currently opened: Djelo");

        ActiveDatabaseTableSingleton.getInstance().setActiveTable(ActiveDatabaseTable.Djelo);

        hookUpButtonEvents();

        setUpTableView(tableView);
        setUpEventsForTableView(tableView);

        tableView.setEditable(true);

        Class<?> objectType = Djelo.class;

        UtilsForTableView.GenerateColumnsForType(objectType, tableView);

        DBConnection dbConnection = new DBConnection();
        ObservableList<?> objects = dbConnection.getAllDataBaseDataForObject(objectType);

        items.setAll(objects);
        tableView.setItems(items);

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

    private void setUpEventsForTableView(TableView<Object> tableView) {
        tableView.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.DELETE) {
                deleteSelectedRows();
            }
        });

        // Create a listener for double-click events to add new row
        tableView.setOnMouseClicked(event -> {
            if (event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                if (tableView.getItems().isEmpty()) {
                    // Table is empty, directly add a new row
                    addNewRow(tableView);
                } else {
                    int lastRowIndex = tableView.getItems().size() - 1;
                    if (tableView.getSelectionModel().isSelected(lastRowIndex)) {
                        // Double-click on the last row, add a new row
                        addNewRow(tableView);
                    }
                }
            }
        });
    }

    private void addNewRow(TableView<Object> tableView) {
        // TODO: ADD LOGIC FOR EVERY TYPE IN DB
        // Create a new empty Djelo object for the new row


        switch (ActiveDatabaseTableSingleton.getInstance().getActiveTable()) {
            case Djelo -> openInsertWindow("Unos novog Djela", Djelo.class);
            case Dvorana -> openInsertWindow("Unos nove Dvorane", Dvorana.class);
            case Izlozba -> openInsertWindow("Unos nove Izlozbe", Izlozba.class);
            case Lokacija -> openInsertWindow("Unos nove Lokacije", Lokacija.class);
            case Pravac -> openInsertWindow("Unos novog Pravca", Pravac.class);
            case Tehnika -> openInsertWindow("Unos nove Tehnike", Tehnika.class);
            case Umjetnik -> openInsertWindow("Unos novog Umjetnika", Umjetnik.class);
        }

        // Focus on the newly added row for editing (if desired)
        // tableView.scrollTo(newDjelo);
    }


    private void loadDataForDjelo() {
        ObservableList<Djelo> djela = dbConnection.getAllDataBaseDataForObject(Djelo.class);
        items.setAll(djela);
        tableView.setItems(items);

    }

    private void loadDataForDvorana() {
        ObservableList<Dvorana> dvorane = dbConnection.getAllDataBaseDataForObject(Dvorana.class);
        items.setAll(dvorane);
        tableView.setItems(items);
    }

    private void loadDataForIzlozba() {
        ObservableList<Izlozba> izlozbe = dbConnection.getAllDataBaseDataForObject(Izlozba.class);
        items.setAll(izlozbe);
        tableView.setItems(items);
    }

    private void loadDataForLokacija() {
        ObservableList<Lokacija> lokacije = dbConnection.getAllDataBaseDataForObject(Lokacija.class);
        items.setAll(lokacije);
        tableView.setItems(items);
    }

    private void loadDataForPravac() {
        ObservableList<Pravac> pravci = dbConnection.getAllDataBaseDataForObject(Pravac.class);
        items.setAll(pravci);
        tableView.setItems(items);
    }

    private void loadDataForTehnika() {
        ObservableList<Tehnika> tehnike = dbConnection.getAllDataBaseDataForObject(Tehnika.class);
        items.setAll(tehnike);
        tableView.setItems(items);
    }

    private void loadDataForUmjetnik() {
        ObservableList<Umjetnik> umjetnici = dbConnection.getAllDataBaseDataForObject(Umjetnik.class);
        items.setAll(umjetnici);
        tableView.setItems(items);
    }

    private void deleteSelectedRows() {

        errorText.setText("");

        ObservableList<Object> selectedRows = tableView.getSelectionModel().getSelectedItems();

        for (Object row : selectedRows) {

            try {
                if (row instanceof Djelo djelo) {
                    dbConnection.deleteDjelo(djelo);
                    loadDataForDjelo();
                } else if (row instanceof Dvorana dvorana) {
                    dbConnection.DeleteDvorana(dvorana);
                    loadDataForDvorana();
                } else if (row instanceof Izlozba izlozba) {
                    dbConnection.DeleteIzlozba(izlozba);
                    loadDataForIzlozba();
                } else if (row instanceof Lokacija lokacija) {
                    dbConnection.DeleteLokacija(lokacija);
                    loadDataForLokacija();
                } else if (row instanceof Pravac pravac) {
                    dbConnection.DeletePravac(pravac);
                    loadDataForPravac();
                } else if (row instanceof Tehnika tehnika) {
                    dbConnection.DeleteTehnika(tehnika);
                    loadDataForTehnika();
                } else if (row instanceof Umjetnik umjetnik) {
                    dbConnection.DeleteUmjetnik(umjetnik);
                    loadDataForUmjetnik();
                }
            }
            catch (Exception ex) {
                if (ex instanceof SQLIntegrityConstraintViolationException sqplIntegrityEx) {
                    String errorMessage = sqplIntegrityEx.getMessage();
                    String[] parts = errorMessage.split(":");
                    String importantInfo = parts[1].trim();
                    errorText.setText(importantInfo);
                }
            }
        }
    }

    private void openInsertWindow(String nameOfWindow, Class<?> objectToGenerateFor) {

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

            // Create a new instance using the parameterless constructor

            // Check if the class has a parameterless constructor

            try {
                Constructor<?> constructor = objectToGenerateFor.getDeclaredConstructor();
                constructor.setAccessible(true);
                Object instance = constructor.newInstance();

                controller.initialize(instance);

            } catch (Exception ex) {
                ex.printStackTrace();
            }

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
            controller.initialize(listOfObjectToShow, this);

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

    public <T> Constructor<T> getParameterlessConstructor(Class<T> clazz) throws NoSuchMethodException {
        return clazz.getDeclaredConstructor();
    }

}