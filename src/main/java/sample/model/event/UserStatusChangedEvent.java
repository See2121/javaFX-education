package sample.model.event;


import java.sql.Date;

public class UserStatusChangedEvent extends AbstractEvent {
    private Boolean statusChange;
    private Date trackeddAt;
    private String statusCorrectLogin;


    @Override
    public Boolean isStatusChange() {
        return statusChange;
    }

    @Override
    public Date getTrackeddAt() {
        return trackeddAt;
    }

    @Override
    public String getStatusCorrectLogin() {
        return statusCorrectLogin;
    }


    public UserStatusChangedEvent(String statusCorrectLogin, Date trackeddAt, Boolean statusChange) {
        this.trackeddAt = trackeddAt;
        this.statusCorrectLogin = statusCorrectLogin;
        this.statusChange = statusChange;
    }


}
