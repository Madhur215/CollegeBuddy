package com.example.collegebuddy.models;

public class questions {
    private String question;
    private String qid;
    private String name;

    public questions(String question, String qid, String name) {
        this.question = question;
        this.qid = qid;
        this.name = name;
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

    public void setQid(String qid) {
        this.qid = qid;
    }

    public void setName(String name) {
        this.name = name;
    }
}
