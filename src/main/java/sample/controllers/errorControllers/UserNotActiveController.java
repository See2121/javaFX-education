

package sample.controllers.errorControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import sample.SceneSwitcher;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserNotActiveController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button comeBackButton;
    @FXML
    void buttonActons(MouseEvent event) {


    }

    @FXML
    public void switchToMainScene() throws IOException {

        new SceneSwitcher().switchSceneTo("/fxml/sample.fxml");
    }

    @FXML
    void initialize() {

        comeBackButton.setOnAction(actionEvent -> {

              comeBackButton.getScene().getWindow().hide();

            Platform.runLater(() -> {
                try {
                    switchToMainScene();
                } catch (IOException e) {
                }
            });
        });

    }
}
