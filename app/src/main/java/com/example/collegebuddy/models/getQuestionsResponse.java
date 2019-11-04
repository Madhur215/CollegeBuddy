package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class getQuestionsResponse {

    @SerializedName("Question")
    private String question;
    @SerializedName("QID")
    private int question_id;

    public getQuestionsResponse(String question, int question_id) {
        this.question = question;
        this.question_id = question_id;
    }

    public String getQuestion() {
        return question;
    }

    public int getQuestion_id() {
        return question_id;
    }
}
