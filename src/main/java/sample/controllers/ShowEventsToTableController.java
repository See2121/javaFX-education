package sample.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.SceneSwitcher;
import sample.api.MonitoringService;
import sample.model.event.*;
import sample.service.InMemoryArrayListMonitoringServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ShowEventsToTableController {

    public ObservableList<AbstractEvent> usersData = FXCollections.observableArrayList();
    MonitoringService monitoringService = InMemoryArrayListMonitoringServiceImpl.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button comeBackButton;

    @FXML
    private TableView<AbstractEvent> usersEventsTable;

    @FXML
    private TableColumn<UserRegisteredEvent, String> registeredNameColumn;

    @FXML
    private TableColumn<UserSuccessLoginEvent, String> successfulColumn;

    @FXML
    private TableColumn<UserFailedLoginEvent, String> failedColumn;

    @FXML
    private TableColumn<UserStatusChangedEvent, String> changeStatusColumn;

    @FXML
    private TableColumn<AbstractEvent, java.sql.Date> timeColumn;

    @FXML
    private TableColumn<UserStatusChangedEvent, Boolean> statusChangeColumn;

    @FXML
    public void switchToAdminOperationsScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/adminOperations.fxml");
    }


    @FXML
    void initialize() {
        registeredNameColumn.setCellValueFactory(new PropertyValueFactory<UserRegisteredEvent, String>("userRegisteredName"));
        successfulColumn.setCellValueFactory(new PropertyValueFactory<UserSuccessLoginEvent, String>("successLogin"));
        failedColumn.setCellValueFactory(new PropertyValueFactory<UserFailedLoginEvent, String>("failedUsername"));
        changeStatusColumn.setCellValueFactory(new PropertyValueFactory<UserStatusChangedEvent, String>("statusCorrectLogin"));
        statusChangeColumn.setCellValueFactory(new PropertyValueFactory<UserStatusChangedEvent,Boolean>("statusChange"));
        timeColumn.setCellValueFactory(new PropertyValueFactory<AbstractEvent, Date>("time"));
        monitoringService.getEvents(usersData, usersEventsTable);


        comeBackButton.setOnAction(event -> {
            comeBackButton.getScene().getWindow().hide();
            Platform.runLater(() -> {
                try {
                    switchToAdminOperationsScene();
                } catch (IOException e) {
                }
            });
        });
    }
}
