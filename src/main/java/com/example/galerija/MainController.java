package com.example.galerija;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;

public class MainController {

    DBConnection dbConnection = new DBConnection();
    ObservableList<Djelo> djela;
    @FXML
    private MFXButton btnConnect;

    @FXML
    private TableView<Djelo> tableView;

    @FXML
    private TableColumn<Djelo, Integer> colIdDjelo;

    @FXML
    private TableColumn<Djelo, String> colNaslov;

    @FXML
    private TableColumn<Djelo, Integer> colGodinaNastanka;

    @FXML
    private TableColumn<Djelo, Integer> colIdUmjetnik;

    @FXML
    private TableColumn<Djelo, Integer> colIdPravac;

    @FXML
    private TableColumn<Djelo, Integer> colIdTehnika;

    @FXML
    private TableColumn<Djelo, Integer> colIdIzlozba;

    @FXML
    private TableColumn<Djelo, Integer> colIdLokacija;


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

    // Getters and Setters for the controls (optional)


    public MFXButton getBtnConnect() {
        return btnConnect;
    }

    public TableView<Djelo> getTableView() {
        return tableView;
    }

    public Label getWelcomeText() {
        return welcomeText;
    }


    @FXML
    public void initialize() {
        setUpTableView();
        setUpEventsForTableView();

        colIdDjelo.setCellValueFactory(new PropertyValueFactory<>("idDjelo"));
        colNaslov.setCellValueFactory(new PropertyValueFactory<>("naslov"));
        colGodinaNastanka.setCellValueFactory(new PropertyValueFactory<>("godinaNastanka"));

        colIdDjelo.setCellValueFactory(new PropertyValueFactory<>("idDjelo"));
        // Set cell value factories for other columns

        tableView.setEditable(true);
        colNaslov.setCellFactory(TextFieldTableCell.forTableColumn());

        loadDataForDjelo();
    }

    private void setUpTableView() {
        tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void setUpEventsForTableView() {
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

                    // Create a new empty Djelo object for the new row
                    Djelo newDjelo = new Djelo(4, "NoviNaslov", 3, 4, 5, 6, 7, 3);

                    // Add the new Djelo object to the data model
                    djela.add(newDjelo);

                    // Refresh the TableView to display the new row
                    tableView.refresh();

                    // Set focus on the newly added row for editing
                    tableView.scrollTo(newDjelo);

                    // Implement any additional logic for handling the new row
                    // For example, you can start editing the new row's cells here
                }
            }
        });

        //Events for triggering update of DataBase when user changed data in cells
        colNaslov.setOnEditCommit(event -> {
            Djelo djelo = event.getRowValue();
            djelo.setNaslov(event.getNewValue());
            dbConnection.updateDjelo(djelo);


        });

    }

    private void deleteSelectedRows() {
        ObservableList<Djelo> selectedRows = tableView.getSelectionModel().getSelectedItems();

        for (Djelo djelo : selectedRows) {
            dbConnection.deleteDjelo(djelo);
        }

        tableView.getItems().removeAll(selectedRows);
        loadDataForDjelo();
    }

    private void loadDataForDjelo() {

        djela = dbConnection.GetAllDjelo();
        tableView.setItems(djela);


    }
}