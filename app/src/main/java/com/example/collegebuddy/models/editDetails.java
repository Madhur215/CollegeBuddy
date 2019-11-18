package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class editDetails {

    @SerializedName("Branch")
    private String branch;
    @SerializedName("Year")
    private String year;
    @SerializedName("Password")
    private String new_password;
    @SerializedName("OldPassword")
    private String old_password;

    public editDetails(String branch, String year, String new_password , String old_password) {
        this.branch = branch;
        this.year = year;
        this.new_password = new_password;
        this.old_password = old_password;
    }

}
