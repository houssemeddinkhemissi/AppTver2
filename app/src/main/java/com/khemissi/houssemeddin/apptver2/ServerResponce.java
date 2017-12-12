package com.khemissi.houssemeddin.apptver2;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Houssem on 20/11/2017.
 */

public class ServerResponce {
    @SerializedName("code")
    private String code ;
    @SerializedName("message")
    private String message;
    @SerializedName("user")
    private User user;

    public ServerResponce() {
    }

    public ServerResponce(String code, String message, User user) {
        this.code = code;
        this.message = message;
        this.user = user;
    }

    public ServerResponce(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
