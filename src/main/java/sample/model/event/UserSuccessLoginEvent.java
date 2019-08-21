package sample.model.event;

import java.sql.Date;

public class UserSuccessLoginEvent extends AbstractEvent {
    private Date trackeddAt;
    private String successLogin;

    @Override
    public Date getTrackeddAt() {
        return trackeddAt;
    }
@Override
    public String getUsername() {
        return successLogin;
    }




    public UserSuccessLoginEvent(String successLogin, Date trackeddAt){
        this.successLogin=successLogin;
        this.trackeddAt=trackeddAt;
    }




}
