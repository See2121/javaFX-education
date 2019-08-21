package sample.api;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public interface UserService {

    void printUsersToTable(ObservableList userData, TableView<User> userTable);


    ResultSet getUser(String login);

    ResultSet getResultSet(String login);

    Connection getDbConnection() throws ClassNotFoundException, SQLException;

    void signUppUsers(User user);

}
