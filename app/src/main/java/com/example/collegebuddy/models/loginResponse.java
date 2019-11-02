package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class loginResponse {

    @SerializedName("token")
    private String auth_token;

    public loginResponse(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getAuth_token() {
        return auth_token;
    }
}
