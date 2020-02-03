package com.example.collegebuddy.utils;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class retrofitInstance {

    private static Retrofit retrofit;
    private static final String BASE_URL = "https://9923297b.ngrok.io/api/";
    public  static String URL = "https://9923297b.ngrok.io";

    public String getURL() {
        return URL;
    }

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
