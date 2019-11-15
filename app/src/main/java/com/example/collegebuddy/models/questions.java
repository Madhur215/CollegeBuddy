package com.example.collegebuddy.models;



public class questions {

    private String question;
    private String qid;
    private String name;
    private String date;


    public questions(String question, String qid, String name , String date) {
        this.question = question;
        this.qid = qid;
        this.name = name;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getQuestion() {
        return question;
    }

    public String getQid() {
        return qid;
    }

    public String getName() {
        return name;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
