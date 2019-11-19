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
    @SerializedName("Image")
    private String imageUri;


    public profileResponse(String user_name, String college, String branch, String year , String imageUri) {
        this.user_name = user_name;
        this.college = college;
        this.branch = branch;
        this.year = year;
        this.imageUri = imageUri;
    }

    public profileResponse(){

    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getImageUri() {
        return imageUri;
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

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public void setYear(String year) {
        this.year = year;
    }

}
