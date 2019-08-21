package sample.service;


import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.ConstForUsers;
import sample.api.UserService;
import sample.model.User;

import java.sql.*;

public class InMemoryArrayListUserServiceImpl implements UserService {
    private static InMemoryArrayListUserServiceImpl inMemoryArrayListUserService;

    public static InMemoryArrayListUserServiceImpl getInstance() {
        if (inMemoryArrayListUserService == null) {
            inMemoryArrayListUserService = new InMemoryArrayListUserServiceImpl();
        }
        return inMemoryArrayListUserService;
    }

    private Connection dbConnection;


    private ResultSet resultSet;

    private String dbHost = "localhost";
    private String dbPort = "127.0.0.1:3306";
    private String dbUser = "root";
    private String dbPass = "12345";
    private String dbName = "See";


    public InMemoryArrayListUserServiceImpl() {

    }


    @Override
    public ResultSet getUser(String login) {

        String select = "SELECT * FROM " + ConstForUsers.USER_TABLE;

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                if (login.equals(resultSet.getString("username"))) {
                    return null;

                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    @Override
    public ResultSet getResultSet(String login) {

        String select = "SELECT * FROM " + ConstForUsers.USER_TABLE;

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                if (login.equals(resultSet.getString("username"))) {

                    return resultSet;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Connection getDbConnection() throws ClassNotFoundException, SQLException {
        String connectionString = "jdbc:mysql://localhost/See?serverTimezone= Europe/Moscow#" + dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    @Override
    public void signUppUsers(User user) {
        String insert = "INSERT INTO " + ConstForUsers.USER_TABLE + "("
                + ConstForUsers.USERS_NAME + "," + ConstForUsers.USERS_PASSWORD + ","
                + ConstForUsers.USERS_BALANCE + "," + ConstForUsers.USERS_STATUS + ","
                + ConstForUsers.USERS_TYPE + ")" +
                "VALUES(?,?,?,?,?)";

        try {
            PreparedStatement preparedStatement = getDbConnection().prepareStatement(insert);
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setDouble(3, user.getBalance());
            preparedStatement.setBoolean(4, user.isActive());
            preparedStatement.setString(5, user.getType().toString());
            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public void printUsersToTable(ObservableList userData, TableView<User> userTable) {

        String select = "SELECT * FROM " + ConstForUsers.USER_TABLE;

        try {

            PreparedStatement preparedStatement = getDbConnection().prepareStatement(select);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                userData.add(new User(resultSet.getString("username"), resultSet.getDouble("userbalance"), resultSet.getBoolean("userstatus"), resultSet.getString("usertype")));
                userTable.setItems(userData);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
