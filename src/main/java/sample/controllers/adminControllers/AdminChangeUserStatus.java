package sample.controllers.adminControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableRow;
import javafx.stage.Stage;
import sample.api.MonitoringService;
import sample.api.UserService;
import sample.model.event.UserStatusChangedEvent;
import sample.service.InMemoryArrayListMonitoringServiceImpl;
import sample.service.InMemoryArrayListUserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ResourceBundle;

public class AdminChangeUserStatus {

    MonitoringService monitoringService = InMemoryArrayListMonitoringServiceImpl.getInstance();
    UserService userService = InMemoryArrayListUserServiceImpl.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    public Label userNameLabel;

    @FXML
    public Button buttonYes;

    @FXML
    public Button buttonNo;


    @FXML
    void initialize() {

    }

    public void showTable(String login, ResultSet resultSet, TableRow row) {
        userNameLabel.setText("Change status for " + login + " ?");
        buttonYes.setOnAction(event -> {

            try {
                try {

                    if (resultSet.getBoolean("userstatus")) {

                        PreparedStatement statement = userService.getDbConnection().prepareStatement("UPDATE users SET userstatus = false WHERE username =?");

                        int i = 1;
                        statement.setString(i++, resultSet.getString("username"));
                        statement.executeUpdate();
                    } else {
                        PreparedStatement statement = userService.getDbConnection().prepareStatement("UPDATE users SET userstatus = true WHERE username =?");

                        int i = 1;
                        statement.setString(i++, resultSet.getString("username"));
                        statement.executeUpdate();

                    }

                    monitoringService.logEvent(new UserStatusChangedEvent(login, new Date(((new java.util.Date()).getTime())), resultSet.getBoolean("userstatus")));
                    buttonYes.getScene().getWindow().hide();
                    row.getScene().getWindow().hide();
                    try {
                        Stage st = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/adminShow.fxml"));
                        Parent sceneBank = loader.load();
                        Scene scene = new Scene(sceneBank);
                        st.setScene(scene);
                        st.setResizable(false);
                        st.show();
                    } catch (IOException e) {
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                }

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        buttonNo.setOnAction(event -> {
            buttonNo.getScene().getWindow().hide();
        });
    }
}
