package sample.service;


import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.ConstForUsers;
import sample.api.MonitoringService;
import sample.model.event.AbstractEvent;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class InMemoryArrayListMonitoringServiceImpl implements MonitoringService {
    private static InMemoryArrayListMonitoringServiceImpl inMemoryArrayListMonitoringService;

    public static InMemoryArrayListMonitoringServiceImpl getInstance() {
        if (inMemoryArrayListMonitoringService == null) {
            inMemoryArrayListMonitoringService = new InMemoryArrayListMonitoringServiceImpl();
        }
        return inMemoryArrayListMonitoringService;
    }


    private String dbHost = "localhost";
    private String dbPort = "127.0.0.1:3306";
    private String dbUser = "root";
    private String dbPass = "12345";
    private String dbName = "seemonitoring";

    private ResultSet resultSet;

    private Connection dbConnect;


    public void getEvents(ObservableList userData, TableView usersEventsTable) {

        String select = "SELECT * FROM " + ConstForUsers.MONITORING_TABLE;
        try {

            PreparedStatement preparedStatement = getDbConnect().prepareStatement(select);

            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {

                userData.add(new AbstractEvent(resultSet.getString("successLogin"), resultSet.getString("time"), resultSet.getString("failedLogin"), resultSet.getString("regLogin"), resultSet.getString("changedStatusLogin"), resultSet.getBoolean("status")));
                usersEventsTable.setItems(userData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Connection getDbConnect() throws ClassNotFoundException, SQLException {
        String connectString = "jdbc:mysql://localhost/seemonitoring?serverTimezone= Europe/Moscow# "+ dbHost + ":" + dbPort + "/" + dbName;

        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnect = DriverManager.getConnection(connectString, dbUser, dbPass);

        return dbConnect;
    }

    @Override
    public void logEvent(AbstractEvent event) {


        try {

            String ins = "INSERT INTO " + ConstForUsers.MONITORING_TABLE + "("
                    + ConstForUsers.REG_LOGIN + "," + ConstForUsers.SUCCESS_LOGIN + ","
                    + ConstForUsers.FAILED_LOGIN + "," + ConstForUsers.CHANGED_STATUS + ","
                    + ConstForUsers.STATUS_STATUS + "," + ConstForUsers.TIME_TIME + ")" +
                    "VALUES(?,?,?,?,?,?)";

            boolean d;
            if (event.isStatusChange() == null || event.isStatusChange()) {
                d = true;
            } else {
                d = false;
            }


            PreparedStatement preparedSt = getDbConnect().prepareStatement(ins);
            System.out.println("LONG" + event.getTrackeddAt().getTime());
            System.out.println("DATE" + new Date(event.getTrackeddAt().getTime()));
            System.out.println("SQL_DATE" + new java.util.Date(event.getTrackeddAt().getTime()));
            System.out.println("TIMESTAMP" + new Timestamp(event.getTrackeddAt().getTime()));
            System.out.println("LOCALDATE" + LocalDateTime.ofInstant((new java.util.Date(event.getTrackeddAt().getTime()).toInstant()), ZoneId.systemDefault()));

            preparedSt.setString(1, event.getUserRegisteredName());
            preparedSt.setString(2, event.getUsername());
            preparedSt.setString(3, event.getFailedUsername());
            preparedSt.setString(4, event.getStatusCorrectLogin());
            preparedSt.setBoolean(5, d);
            preparedSt.setTimestamp(6, new Timestamp(event.getTrackeddAt().getTime()));
            preparedSt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}
