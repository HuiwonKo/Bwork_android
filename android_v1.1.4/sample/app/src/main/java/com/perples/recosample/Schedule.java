package com.perples.recosample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haein on 2017-08-30.
 */
public class Schedule {

    @SerializedName("plan")
    private String Plan;

    @SerializedName("time")
    private String Time;

    @SerializedName("location")
    private String Location;

    @SerializedName("participant")
    private String Participant;

    @SerializedName("meeting")
    private int meeting;

    @SerializedName("pk")
    private String pk;

    public int getMeeting() {
        return meeting;
    }

    public void setMeeting(int meeting) {
        this.meeting = meeting;
    }

    public String getPlan() {
        return Plan;
    }

    public void setPlan(String plan) {
        Plan = plan;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getParticipant() {
        return Participant;
    }

    public void setParticipant(String participant) {
        Participant = participant;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }
}
