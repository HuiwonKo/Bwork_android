package com.perples.recosample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haein on 2017-07-13.
 */
public class SignInClass {

    @SerializedName("username")
    private String Username;

    @SerializedName("password")
    private String Password;

    /*public SignInClass(String id, String pw){
        this.id = id;
        this.pw = pw;
    }*/

    public SignInClass(String username, String password) {
        this.Username = username;
        this.Password = password;
    }

}
