package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class questionsResponse {

    @SerializedName("Question")
    private String question;
    @SerializedName("QID")
    private String question_id;
    @SerializedName("ID")
    private String asked_by_name;

    public questionsResponse(String question, String question_id , String asked_by_name) {
        this.question = question;
        this.question_id = question_id;
        this.asked_by_name = asked_by_name;
    }

    public String getQuestion() {
        return question;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getAsked_by_name(){
        return asked_by_name;
    }
}
