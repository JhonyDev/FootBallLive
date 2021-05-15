package com.bakrin.fblive.model.Pojo;

import com.google.gson.annotations.SerializedName;

public class CreatedUser {
    @SerializedName("id")
    public String id;
    @SerializedName("user_token")
    public String user_token;

    public CreatedUser(String user_token) {
        this.user_token = user_token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
