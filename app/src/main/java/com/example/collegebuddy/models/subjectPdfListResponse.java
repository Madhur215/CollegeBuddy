package com.example.collegebuddy.models;

import com.google.gson.annotations.SerializedName;

public class subjectPdfListResponse {

    @SerializedName("NameOfPDF")
    private String pdf_name;
    @SerializedName("PKey")
    private String pdf_key;

    public subjectPdfListResponse(String pdf_name, String pdf_key) {
        this.pdf_name = pdf_name;
        this.pdf_key = pdf_key;
    }

    public String getPdf_name() {
        return pdf_name;
    }

    public String getPdf_key() {
        return pdf_key;
    }
}
