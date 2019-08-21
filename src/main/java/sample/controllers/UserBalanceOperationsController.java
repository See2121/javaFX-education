package sample.controllers;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;
import sample.MainApp;
import sample.SceneSwitcher;
import sample.animations.Shake;
import sample.api.UserService;
import sample.service.InMemoryArrayListUserServiceImpl;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserBalanceOperationsController {


    private double userBalance;


    UserService userService = InMemoryArrayListUserServiceImpl.getInstance();

    private final DoubleProperty heigth = new SimpleDoubleProperty();

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button refillButton;

    @FXML
    private Button withdrawButton;

    @FXML
    private Button comeBackButton;

    @FXML
    private Label welcomeUserName;

    @FXML
    protected Label welcomeUserBalance;

    @FXML
    private TextField amountToRefill;

    @FXML
    private Label selectAnActions;


    public void atmProcess(String login, double balance, ResultSet resultSet) {

        Shake amountToRefillAnim = new Shake(amountToRefill);


        amountToRefill.textProperty().bindBidirectional(heigth, new NumberStringConverter());


        userBalance = balance;
        System.out.println(login);
        welcomeUserName.setText("Welcome " + "\n" + login + " !!!");
        welcomeUserBalance.setText("Your balance: " + "\n" + balance + "$");


        refillButton.setOnAction(event -> {

            userBalance = refillCount(heigth.get(), userBalance);

            if (heigth.get() <= 0) {
                amountToRefillAnim.playAnim();
                amountToRefill.clear();

            } else {

                try {
                    try {

                        PreparedStatement statement = userService.getDbConnection().prepareStatement("UPDATE users SET userbalance =? WHERE username =?");

                        int i = 1;
                        statement.setDouble(i++, userBalance);
                        statement.setString(i++, resultSet.getString("username"));
                        statement.executeUpdate();

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                amountToRefill.clear();
            }

        });


        withdrawButton.setOnAction(event -> {

            userBalance = withdrawCount(heigth.get(), userBalance);
            if (heigth.get() <= 0 || heigth.get() > userBalance) {

                amountToRefillAnim.playAnim();
                amountToRefill.clear();

            } else {
                try {
                    try {

                        PreparedStatement statement = userService.getDbConnection().prepareStatement("UPDATE users SET userbalance =? WHERE username =?");


                        int i = 1;
                        statement.setDouble(i++, userBalance);
                        statement.setString(i++, resultSet.getString("username"));
                        statement.executeUpdate();

                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                amountToRefill.clear();
            }

        });

        comeBackButton.setOnAction(actionEvent -> {

            comeBackButton.getScene().getWindow().hide();

            MainApp mainApp = new MainApp();
            try {
                mainApp.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    double refillCount(double money, double userBalance) {
        if (money <= 0) {
            try {
                Parent parent = FXMLLoader.load(SceneSwitcher.class.getResource("/fxml/errorWindow.fxml"));
                Scene scene = new Scene(parent);
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(scene);
                window.setResizable(false);
                window.show();
            } catch (IOException e) {
            }
        } else {
            userBalance += money;
            welcomeUserBalance.setText("Your balance: " + "\n" + userBalance + "$");
        }

        return userBalance;
    }


    double withdrawCount(double money, double userBalance) {
        if (money > userBalance || money <= 0) {
            try {
                Parent parent = FXMLLoader.load(SceneSwitcher.class.getResource("/fxml/errorWindow.fxml"));
                Scene scene = new Scene(parent);
                Stage window = new Stage();
                window.initModality(Modality.APPLICATION_MODAL);
                window.setScene(scene);
                window.setResizable(false);
                window.show();
            } catch (IOException e) {
            }
        } else {

            userBalance -= money;
            welcomeUserBalance.setText("Your balance: " + "\n" + userBalance + "$");

        }
        return userBalance;
    }

    @FXML
    public void initialize() {
        amountToRefill.clear();


    }


}




