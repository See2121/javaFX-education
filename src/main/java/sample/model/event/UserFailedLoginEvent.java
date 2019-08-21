package sample.model.event;


import java.sql.Date;

public class UserFailedLoginEvent extends AbstractEvent {
    @Override
    public String getFailedUsername() {
        return failedUsername;
    }

    @Override
    public Date getTrackeddAt() {
        return trackeddAt;
    }

    private String failedUsername;

    private Date trackeddAt;


    public UserFailedLoginEvent(String failedUsername, Date trackeddAt) {


        this.trackeddAt = trackeddAt;
        this.failedUsername = failedUsername;
    }

}