package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static Stage thisStage;


    @Override
    public void start(Stage primaryStage) throws Exception {

        Parent root;
        root = FXMLLoader.load(getClass().getResource("/fxml/sample.fxml"));
        primaryStage.setScene(new Scene(root));
        thisStage = primaryStage;
        primaryStage.show();
        primaryStage.setResizable(false);


    }

    public static void main(String[] args) throws Exception {
        launch(args);


    }

}
