package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class questionsResponse {

    @SerializedName("Question")
    private String question;
    @SerializedName("QID")
    private String question_id;
    @SerializedName("ID")
    private String asked_by_name;
    @SerializedName("Datetime")
    private String asked_on_date;
    @SerializedName("Image")
    private String image;

    public questionsResponse(String question, String question_id , String asked_by_name ,
                             String asked_on_date , String image) {
        this.question = question;
        this.question_id = question_id;
        this.asked_by_name = asked_by_name;
        this.asked_on_date = asked_on_date;
        this.image = image;

    }



    public String getImage() {
        return image;
    }

    public String getQuestion() {
        return question;
    }

    public String getAsked_on_date() {
        return asked_on_date;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public String getAsked_by_name(){
        return asked_by_name;
    }

}
