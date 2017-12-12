package com.khemissi.houssemeddin.apptver2;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Houssem on 20/11/2017.
 */

public class ListServerResponce {
    @SerializedName("code")
    private String code ;
    @SerializedName("message")
    private List message;
    @SerializedName("user")
    private User user;

    public ListServerResponce() {
    }

    public ListServerResponce(String code, List message, User user) {
        this.code = code;
        this.message = message;
        this.user = user;
    }

    public ListServerResponce(String code, List message) {
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

    public List getMessage() {
        return message;
    }

    public void setMessage(List message) {
        this.message = message;
    }
}
