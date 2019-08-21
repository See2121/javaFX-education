package sample.model.event;


import java.sql.Date;


public class UserRegisteredEvent extends AbstractEvent {
    private String userRegisteredName;

    @Override
    public String getUserRegisteredName() {
        return userRegisteredName;
    }

    @Override
    public Date getTrackeddAt() {
        return trackeddAt;
    }

    private Date trackeddAt;

    public UserRegisteredEvent(String userRegisteredName, Date trackeddAt) {
        this.userRegisteredName = userRegisteredName;
        this.trackeddAt = trackeddAt;
    }

    public UserRegisteredEvent(){

    }



}
