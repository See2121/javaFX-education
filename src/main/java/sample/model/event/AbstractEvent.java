package sample.model.event;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;


public class AbstractEvent implements Serializable {

    private String username;
    private Timestamp trackeddAt;
    private String failedUsername;
    private String successLogin;
    private String userRegisteredName;
    private String statusCorrectLogin;
    private Boolean statusChange;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String time;




    public String getStatusCorrectLogin() {
        return statusCorrectLogin;
    }


    public String getUserRegisteredName() {
        return userRegisteredName;
    }


    public AbstractEvent(String successLogin, Timestamp trackeddAt, String failedUsername, String userRegisteredName, String statusCorrectLogin, Boolean statusChange) {
        this.trackeddAt = trackeddAt;
        this.successLogin = successLogin;
        this.failedUsername = failedUsername;
        this.userRegisteredName = userRegisteredName;
        this.statusCorrectLogin = statusCorrectLogin;
        this.statusChange = statusChange;
    }

    public AbstractEvent(String successLogin, String time, String failedUsername, String userRegisteredName, String statusCorrectLogin, Boolean statusChange) {
        this.time=time;
        this.successLogin = successLogin;
        this.failedUsername = failedUsername;
        this.userRegisteredName = userRegisteredName;
        this.statusCorrectLogin = statusCorrectLogin;
        this.statusChange = statusChange;
    }


    public String getSuccessLogin() {
        return successLogin;
    }

    public Boolean isStatusChange() {
        return statusChange;
    }

    public String getFailedUsername() {
        return failedUsername;
    }


    public Date getTrackeddAt() {
        return trackeddAt;
    }

    public String getUsername() {
        return username;
    }


    public AbstractEvent() {

    }


}
