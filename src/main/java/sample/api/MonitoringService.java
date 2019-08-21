package sample.api;

import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import sample.model.event.AbstractEvent;

import java.sql.Connection;
import java.sql.SQLException;

public interface MonitoringService {


    void logEvent(AbstractEvent event);

    void getEvents(ObservableList userData, TableView usersEventsTable);

    public Connection getDbConnect() throws ClassNotFoundException, SQLException;
}
