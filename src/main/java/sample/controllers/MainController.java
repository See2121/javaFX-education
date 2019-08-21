package sample.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.SceneSwitcher;
import sample.animations.Shake;
import sample.api.MonitoringService;
import sample.api.UserService;
import sample.model.UserType;
import sample.model.event.UserFailedLoginEvent;
import sample.model.event.UserSuccessLoginEvent;
import sample.service.InMemoryArrayListMonitoringServiceImpl;
import sample.service.InMemoryArrayListUserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ResourceBundle;

public class MainController {
    private UserService userService = InMemoryArrayListUserServiceImpl.getInstance();
    private MonitoringService monitoringService = InMemoryArrayListMonitoringServiceImpl.getInstance();


    byte x = 0;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane mainWindow;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authSignButton;

    @FXML
    private Button loginSignAppButton;

    @FXML
    private Label attemptShow;


    @FXML
    public void switchToNotActiveScene() throws IOException {
        new SceneSwitcher().switchSceneTo("/fxml/notActive.fxml");
    }

    @FXML
    public void switchToSignAppScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/signUpp.fxml");

    }

    @FXML
    public void switchToBalanceOperationsScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/balanceOperations.fxml");

    }

    @FXML
    public void switchToAdminOperationsScene() throws IOException {
        new SceneSwitcher().switchSceneTo("/fxml/adminOperations.fxml");
    }

    @FXML
    public void switchToIncorrectLoginWindow() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/errorAuthorizations.fxml");
    }

    @FXML
    public void initialize() {

        authSignButton.setOnAction(event -> {

            Shake userLoginAnim = new Shake(loginField);
            Shake userPassAnim = new Shake(passwordField);
            userLoginAnim.playAnim();
            userPassAnim.playAnim();

            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();
            UserType userType = UserType.CUSTOMER;

            ResultSet resultSet;
            resultSet = userService.getResultSet(loginText);

            String newPassText = CryptWithMD5.cryptWithMD5(passwordText);

            if (!loginText.equals("") && !passwordText.equals("")) {

                if (resultSet == null) {
                    try {
                        Stage st = new Stage();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/loginDoesntFind.fxml"));
                        Parent sceneBank = loader.load();
                        Scene scene = new Scene(sceneBank);
                        st.setScene(scene);
                        st.initModality(Modality.APPLICATION_MODAL);

                        st.setResizable(false);
                        st.show();

                    } catch (IOException e) {
                    }
                }

                try {
                    try {
                        assert resultSet != null;
                        if (resultSet.getString("usertype").equals(userType.toString()) && resultSet.getString("userpassword").equals(newPassText) && resultSet.getBoolean("userstatus")) {
                            monitoringService.logEvent(new UserSuccessLoginEvent(loginText, new Date((new java.util.Date()).getTime())));
                            try {
                                authSignButton.getScene().getWindow().hide();
                                Stage st = new Stage();
                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/balanceOperations.fxml"));
                                Parent sceneBank = loader.load();
                                UserBalanceOperationsController balanceOperationsController = loader.<UserBalanceOperationsController>getController();
                                Scene scene = new Scene(sceneBank);
                                st.setScene(scene);
                                balanceOperationsController.atmProcess(resultSet.getString("username"), resultSet.getDouble("userbalance"), resultSet);
                                st.setResizable(false);
                                st.show();

                            } catch (IOException e) {
                            }
                        } else if (!resultSet.getString("usertype").equals(userType.toString()) && resultSet.getString("userpassword").equals(newPassText)) {
                            monitoringService.logEvent(new UserSuccessLoginEvent(loginText, new Date((new java.util.Date()).getTime())));
                            Platform.runLater(() -> {
                                try {
                                    switchToAdminOperationsScene();
                                } catch (IOException e) {
                                }
                            });
                        } else if (!resultSet.getBoolean("userstatus")) {
                            monitoringService.logEvent(new UserFailedLoginEvent(loginText, new Date((new java.util.Date()).getTime())));
                            userLoginAnim.playAnim();
                            userPassAnim.playAnim();

                            Platform.runLater(() -> {
                                try {
                                    switchToNotActiveScene();
                                } catch (IOException e) {
                                }
                            });
                        } else if (loginText.equals(resultSet.getString("username")) && !newPassText.equals(resultSet.getString("userpassword")) && !loginText.equals("admin")) {
                            x++;
                            monitoringService.logEvent(new UserFailedLoginEvent(loginText, new Date((new java.util.Date()).getTime())));


                            attemptShow.setText("Incorrect login or password!" + "\n" + "Attempt: " + x + " /3");
                            if (x == 3) {

                                try {

                                    PreparedStatement statement = userService.getDbConnection().prepareStatement("UPDATE users SET userstatus = false WHERE username =?");

                                    int i = 1;
                                    statement.setString(i++, resultSet.getString("username"));
                                    statement.executeUpdate();

                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }

                                userPassAnim.playAnim();
                                authSignButton.getScene().getWindow().hide();

                                try {
                                    switchToNotActiveScene();
                                } catch (IOException e) {
                                }
                            }
                        } else {
                            monitoringService.logEvent(new UserFailedLoginEvent(loginText, new Date((new java.util.Date()).getTime())));
                            try {
                                if (!loginText.equals("admin")) {
                                    Parent parent = FXMLLoader.load(SceneSwitcher.class.getResource("/fxml/errorAuthorizations.fxml"));
                                    Scene scene = new Scene(parent);
                                    Stage window = new Stage();
                                    window.initModality(Modality.APPLICATION_MODAL);
                                    window.setScene(scene);
                                    window.setResizable(false);
                                    window.show();
                                }

                            } catch (IOException e) {
                            }
                            attemptShow.setText("                Incorrect login" + "\n" + "                or empty fields!");
                        }
                    } catch (NullPointerException m) {
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        });

        loginSignAppButton.setOnAction(actionEvent -> {
            Shake userLoginAnim = new Shake(loginField);
            Shake userPassAnim = new Shake(passwordField);

            userLoginAnim.playAnim();
            userPassAnim.playAnim();

            Platform.runLater(() -> {
                try {
                    switchToSignAppScene();
                } catch (IOException e) {
                }
            });
        });
    }
}
