package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class profileResponse {

    @SerializedName("Name")
    private String user_name;

    @SerializedName("College")
    private String college;
    @SerializedName("Branch")
    private String branch;
    @SerializedName("Year")
    private String year;

    public profileResponse(String user_name, String college, String branch, String year) {
        this.user_name = user_name;
        this.college = college;
        this.branch = branch;
        this.year = year;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getCollege() {
        return college;
    }

    public String getBranch() {
        return branch;
    }

    public String getYear() {
        return year;
    }
}
