package sample.controllers.adminControllers;

import sample.animations.Shake;
import sample.api.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.SceneSwitcher;
import sample.service.InMemoryArrayListUserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminOperationsController {
    UserService userService = InMemoryArrayListUserServiceImpl.getInstance();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Button comeBackButton;

    @FXML
    private Button printUsersButton;


    @FXML
    private Button viewAuthorizationLogButton;


    @FXML
    public void switchToMainScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/sample.fxml");

    }

    @FXML
    public void switchToEventsScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/showEventsToTable.fxml");

    }

    @FXML
    public void switchToAdminShowScene() throws IOException {
        new SceneSwitcher().switchSceneTo("/fxml/adminShow.fxml");
    }

    @FXML
    void initialize() {
        printUsersButton.setOnAction(event -> {
            printUsersButton.getScene().getWindow().hide();

            try {
                printUsersButton.getScene().getWindow().hide();
                switchToAdminShowScene();

            } catch (IOException e) {
            }

        });

        viewAuthorizationLogButton.setOnAction(event -> {

                try {
                    viewAuthorizationLogButton.getScene().getWindow().hide();
                    Stage st = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/showEventsToTable.fxml"));
                    Parent sceneBank = loader.load();
                    Scene scene = new Scene(sceneBank);
                    st.setScene(scene);
                    st.setResizable(false);
                    st.show();
                } catch (IOException e) {
                }

        });

        comeBackButton.setOnAction(actionEvent -> {

            Shake comeBackButtonAnim = new Shake(comeBackButton);
            Shake welcomeLabelAnim = new Shake(welcomeLabel);
            comeBackButtonAnim.playAnim();
            welcomeLabelAnim.playAnim();

            Platform.runLater(() -> {
                try {
                    switchToMainScene();
                } catch (IOException e) {
                }
            });
        });
    }
}
