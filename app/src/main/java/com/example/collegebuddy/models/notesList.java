package com.example.collegebuddy.models;

public class notesList {

    private String notes_name;
    private String sent_by_name;
    private String number_of_pages;

    public notesList(String notes_name, String sent_by_name, String number_of_pages) {
        this.notes_name = notes_name;
        this.sent_by_name = sent_by_name;
        this.number_of_pages = number_of_pages;
    }

    public String getNotes_name() {
        return notes_name;
    }

    public String getSent_by_name() {
        return sent_by_name;
    }

    public String getNumber_of_pages() {
        return number_of_pages;
    }
}
