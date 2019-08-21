package sample.controllers.adminControllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.SceneSwitcher;
import sample.api.MonitoringService;
import sample.api.UserService;
import sample.model.User;
import sample.service.InMemoryArrayListMonitoringServiceImpl;
import sample.service.InMemoryArrayListUserServiceImpl;

import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class AdminOperationsShowing {

    public ObservableList<User> usersData = FXCollections.observableArrayList();


    UserService userService = InMemoryArrayListUserServiceImpl.getInstance();
    MonitoringService monitoringService = InMemoryArrayListMonitoringServiceImpl.getInstance();


    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button comeBackButton;

    @FXML
    public TableView<User> usersTable;

    @FXML
    private TableColumn<User, String> userNameColumn;

    @FXML
    private TableColumn<User, Double> balanceColumn;

    @FXML
    private TableColumn<User, String> typeColumn;

    @FXML
    private TableColumn<User, ImageView> statusColumn;

    @FXML
    private TextField userNameField;


    @FXML
    public void switchToAdminShowScene() throws IOException {
        new SceneSwitcher().switchSceneTo("/fxml/adminShow.fxml");
    }


    @FXML
    public void switchToAdminOperationsScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/adminOperations.fxml");

    }

    @FXML
    public void switchToErrorWindow() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/errorWindow.fxml");

    }


    @FXML
    void initialize() {

        userNameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
        balanceColumn.setCellValueFactory(new PropertyValueFactory<User, Double>("balance"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<User, String>("userType"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<User, ImageView>("imageView"));
        userService.printUsersToTable(usersData, usersTable);

        usersTable.setRowFactory(tv -> {
            TableRow<User> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    User rowData = row.getItem();

                    ResultSet resultSet;
                    resultSet = userService.getResultSet(rowData.getLogin());

                    try {
                        Stage st = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/changeUserStatus.fxml"));
                        Parent sceneBank = loader.load();
                        AdminChangeUserStatus adminChangeUserStatus = loader.<AdminChangeUserStatus>getController();
                        Scene scene = new Scene(sceneBank);
                        st.setScene(scene);
                        st.initModality(Modality.APPLICATION_MODAL);

                        st.setResizable(false);
                        st.show();
                        try {
                            adminChangeUserStatus.showTable(resultSet.getString("username"), resultSet, row);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                    } catch (IOException e) {
                    }
                }
            });
            return row;
        });


        comeBackButton.setOnAction(actionEvent -> {


            comeBackButton.getScene().getWindow().hide();
            Platform.runLater(() -> {
                try {
                    switchToAdminOperationsScene();
                } catch (IOException e) {

                }
            });
        });

        FilteredList<User> filteredList = new FilteredList<>(usersData, e -> true);
        userNameField.setOnKeyReleased(e -> {
            userNameField.textProperty().addListener((observableValue, oldValue, newValue) -> {
                filteredList.setPredicate((Predicate<? super User>) user -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (user.getLogin().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });
            SortedList<User> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(usersTable.comparatorProperty());
            usersTable.setItems(sortedList);
        });


    }

}
