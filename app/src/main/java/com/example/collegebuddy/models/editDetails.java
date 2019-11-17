package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class editDetails {

    @SerializedName("Branch")
    private String branch;
    @SerializedName("Year")
    private String year;
    @SerializedName("Password")
    private String new_password;

    public editDetails(String branch, String year, String new_password) {
        this.branch = branch;
        this.year = year;
        this.new_password = new_password;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }

    public String getNew_password() {
        return new_password;
    }
}
