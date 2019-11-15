package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class subjects {

    @SerializedName("Subject")
    private String subject;
    @SerializedName("Key")
    private String subject_key;

    public subjects(String subject , String subject_key) {

        this.subject_key = subject_key;
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public String getSubject_key() {
        return subject_key;
    }
}
