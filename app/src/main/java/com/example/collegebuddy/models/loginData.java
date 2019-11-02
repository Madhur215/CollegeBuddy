package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class loginData {

    @SerializedName("MobileNo")
    private String mobile_number;
    @SerializedName("Password")
    private String password;

    public loginData(String mobile_number, String password) {
        this.mobile_number = mobile_number;
        this.password = password;
    }

    public String getMobile_number() {
        return mobile_number;
    }

    public String getPassword() {
        return password;
    }

}
