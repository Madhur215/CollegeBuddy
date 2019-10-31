package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class signUpData {

    @SerializedName("Name")
    private String name;
    @SerializedName("College")
    private String college;
    @SerializedName("Branch")
    private String branch;
    @SerializedName("Year")
    private String year;
    @SerializedName("Password")
    private String password;
    @SerializedName("MobileNo")
    private String mobile_number;

    public signUpData(String name, String college, String branch, String year, String password, String mobile_number) {
        this.name = name;
        this.college = college;
        this.branch = branch;
        this.year = year;
        this.password = password;
        this.mobile_number = mobile_number;
    }

    public String getName() {
        return name;
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

    public String getPassword() {
        return password;
    }

    public String getMobile_number() {
        return mobile_number;
    }
}
