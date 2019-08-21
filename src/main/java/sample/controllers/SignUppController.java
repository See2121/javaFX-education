package sample.controllers;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import sample.SceneSwitcher;
import sample.animations.Shake;
import sample.api.MonitoringService;
import sample.api.UserService;
import sample.model.User;
import sample.model.UserType;
import sample.model.event.UserRegisteredEvent;
import sample.service.InMemoryArrayListMonitoringServiceImpl;
import sample.service.InMemoryArrayListUserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.Date;
import java.util.ResourceBundle;


public class SignUppController {

    private UserService userService = InMemoryArrayListUserServiceImpl.getInstance();
    MonitoringService monitoringService = InMemoryArrayListMonitoringServiceImpl.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private Label showMessage;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button authSignButton;

    @FXML
    private Button comeBackButton;

    @FXML
    private Label loginExistsShow;

    @FXML
    private PasswordField passwordFieldСonfirmation;

    @FXML
    public void switchToMainScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/sample.fxml");

    }

    @FXML
    public void initialize() {
        authSignButton.setOnAction(event -> {
            loginExistsShow.setText(" ");
            Shake loginFieldAnim = new Shake(loginField);
            Shake passwordFieldAnim = new Shake(passwordField);
            Shake passwordFieldConfirmationAnim = new Shake(passwordFieldСonfirmation);
            Shake loginExistsShowAnim = new Shake(loginExistsShow);

            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();
            String passwordConfirmation = passwordFieldСonfirmation.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {

                ResultSet resultSet = userService.getUser(loginText);


                if (resultSet == null) {

                    loginExistsShow.setText("Login exists!");

                    loginField.clear();
                    passwordField.clear();
                    passwordFieldСonfirmation.clear();

                    loginFieldAnim.playAnim();
                    passwordFieldAnim.playAnim();
                    passwordFieldConfirmationAnim.playAnim();
                    loginExistsShowAnim.playAnim();

                } else {
                    if (passwordText.equals(passwordConfirmation)) {
                        monitoringService.logEvent(new UserRegisteredEvent(loginText, new Date(((new java.util.Date()).getTime()))));
                        loginExistsShow.setText("Successfully!");
                        
                        String encryptedPassword;

                        encryptedPassword = CryptWithMD5.cryptWithMD5(passwordText);

                        User user = new User();
                        user.setLogin(loginText);
                        user.setPassword(encryptedPassword);
                        user.setBalance(0);
                        user.setActive(true);
                        user.setType(UserType.CUSTOMER);

                        System.out.println(encryptedPassword);

                        userService.signUppUsers(user);

                        loginField.clear();
                        passwordField.clear();
                        passwordFieldСonfirmation.clear();

                        Platform.runLater(() -> {

                            PauseTransition delay = new PauseTransition(Duration.seconds(0.5));
                            delay.setOnFinished(event1 -> {
                                authSignButton.getScene().getWindow().hide();
                                try {
                                    switchToMainScene();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                            delay.play();

                        });

                    }
                    if (!passwordText.equals(passwordConfirmation)) {
                        loginFieldAnim.playAnim();
                        passwordFieldAnim.playAnim();
                        passwordFieldConfirmationAnim.playAnim();
                        try {
                            Parent parent = FXMLLoader.load(SceneSwitcher.class.getResource("/fxml/errorPasswordInput.fxml"));
                            Scene scene = new Scene(parent);
                            Stage window = new Stage();
                            window.initModality(Modality.APPLICATION_MODAL);
                            window.setScene(scene);
                            window.setResizable(false);
                            window.showAndWait();
                        } catch (IOException e) {
                        }
                    }
                }
            } else {
                try {
                    Parent parent = FXMLLoader.load(SceneSwitcher.class.getResource("/fxml/errorAuthorizations.fxml"));
                    Scene scene = new Scene(parent);
                    Stage window = new Stage();
                    window.initModality(Modality.APPLICATION_MODAL);
                    window.setScene(scene);
                    window.setResizable(false);
                    window.show();
                } catch (IOException e) {
                }
            }
        });
    }


    @FXML
    private void comeBackAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                switchToMainScene();
            } catch (IOException e) {
            }
        });
    }
}
