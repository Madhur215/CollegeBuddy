package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class members {

    @SerializedName("Name")
    private String member_name;
    @SerializedName("Branch")
    private String member_branch;
    @SerializedName("Year")
    private String member_year;

    public members(String member_name, String member_branch, String member_year) {
        this.member_name = member_name;
        this.member_branch = member_branch;
        this.member_year = member_year;
    }

    public String getMember_name() {
        return member_name;
    }

    public String getMember_branch() {
        return member_branch;
    }

    public String getMember_year() {
        return member_year;
    }
}
