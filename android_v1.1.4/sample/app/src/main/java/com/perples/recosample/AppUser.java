package com.perples.recosample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haein on 2017-08-31.
 */
public class AppUser {

    @SerializedName("username")
    private String username;

    @SerializedName("email")
    private String email;

    @SerializedName("isSelected") //****************유저목록에서 checkbox 선택됐는지 여부
    private boolean isSelected;

    public AppUser(){

    }

    public AppUser(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public AppUser(String username, String email, boolean isSelected) {
        this.username = username;
        this.email = email;
        this.isSelected = isSelected;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
