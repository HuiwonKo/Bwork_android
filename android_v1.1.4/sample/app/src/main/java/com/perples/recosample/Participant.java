package com.perples.recosample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haein on 2017-09-06.
 */
public class Participant {

    @SerializedName("pk")
    private String pk;

    @SerializedName("user")
    private String user;

    @SerializedName("user_name")
    private String user_name;

    @SerializedName("is_participate")
    private boolean is_participate;

    @SerializedName("participate_time")
    private String participate_time;

    @SerializedName("minutes")
    private String minutes;

    @SerializedName("user_pk")
    private String user_pk;

    @SerializedName("meeting_pk")
    private String meeting_pk;

    @SerializedName("meeting")
    private String meeting;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getMeeting() {
        return meeting;
    }

    public void setMeeting(String meeting) {
        this.meeting = meeting;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean getIs_participate() {
        return is_participate;
    }

    public void setIs_participate(boolean is_participate) {
        this.is_participate = is_participate;
    }

    public String getParticipate_time() {
        return participate_time;
    }

    public void setParticipate_time(String participate_time) {
        this.participate_time = participate_time;
    }

    public String getMinutes() {
        return minutes;
    }

    public void setMinutes(String minutes) {
        this.minutes = minutes;
    }

    public String getUser_pk() {
        return user_pk;
    }

    public void setUser_pk(String user_pk) {
        this.user_pk = user_pk;
    }

    public String getMeeting_pk() {
        return meeting_pk;
    }

    public void setMeeting_pk(String meeting_pk) {
        this.meeting_pk = meeting_pk;
    }
}
