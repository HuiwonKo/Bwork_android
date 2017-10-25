package com.perples.recosample;

import com.google.gson.annotations.SerializedName;

/**
 * Created by haein on 2017-07-06.
 */
public class SignUpClass {

    @SerializedName("nick")
    private String Nick;

    @SerializedName("password")
    private String Password;

    @SerializedName("username")
    private String Username;

    @SerializedName("email")
    private String Email;

    @SerializedName("phone")
    private String Phone;

    @SerializedName("is_flextime")
    private String Is_flextime;

    @SerializedName("flextime")
    private String Flextime;

    @SerializedName("response")
    private String Response;  //서버로부터 받은 응답

    @SerializedName("id")
    private String id; //****서버로부터 받은 토큰

    @SerializedName("pk")
    private String pk; //****서버로부터 받은 토큰

    @SerializedName("token")
    private String token;

    public String getResponse() {
        return Response;
    }

    //****


    public String getNick() {
        return Nick;
    }

    public void setNick(String nick) {
        Nick = nick;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getIs_flextime() {
        return Is_flextime;
    }

    public void setIs_flextime(String is_flextime) {
        Is_flextime = is_flextime;
    }

    public String getFlextime() {
        return Flextime;
    }

    public void setFlextime(String flextime) {
        Flextime = flextime;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPk() {
        return pk;
    }

    public void setPk(String pk) {
        this.pk = pk;
    }
}
