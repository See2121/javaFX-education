package sample.model;

import javafx.scene.control.CheckBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Objects;

public class User {

    private static User user;

    public static User getInstance() {
        if (user == null) {
            user = new User();
        }
        return user;
    }


    private String login;
    private String password;
    private double balance;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    private  String userType;
    private boolean active;
    private UserType type;
    private ImageView imageView;
    private FileInputStream imageStreamForActiveUsers;
    private FileInputStream imageStreamForNotActiveUsers;

    {
        try {
            imageStreamForActiveUsers = new FileInputStream("C:\\Java\\Workspace\\untitled\\src\\main\\java\\sample\\assets\\good.png");
            imageStreamForNotActiveUsers = new FileInputStream("C:\\Java\\Workspace\\untitled\\src\\main\\java\\sample\\assets\\bad.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    Image imageForActiveUsers = new Image(Objects.requireNonNull(imageStreamForActiveUsers));
    Image imageForNotActiveUsers = new Image(Objects.requireNonNull(imageStreamForNotActiveUsers));

    public ImageView getImageView() {

        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }


    private CheckBox status = new CheckBox();

    public User() {
    }


    public User(String login, double balance, boolean active, String userType) {
        this.login = login;
        this.balance = balance;
        this.active=active;
        this.userType=userType;
        status.setDisable(false);
        status.isResizable();
        this.status = getStatus();
        if (active==true) {
            this.imageView = new ImageView(imageForActiveUsers);
        } else {
            this.imageView = new ImageView(imageForNotActiveUsers);
        }
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public boolean isActive() {

        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }


    public CheckBox getStatus() {
        status.setDisable(false);
        return status;
    }


}


