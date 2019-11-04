package com.example.collegebuddy.Inteface;

import com.example.collegebuddy.models.getProfileResponse;
import com.example.collegebuddy.models.getQuestionsResponse;
import com.example.collegebuddy.models.loginData;

import com.example.collegebuddy.models.loginResponse;
import com.example.collegebuddy.models.signUpData;
import com.example.collegebuddy.models.signUpResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonApiHolder {

    @POST("Member/Signup")
    Call<signUpResponse> signUp(@Body signUpData data);

    @POST("Member/Verify/{ID}")
    Call<ResponseBody> verifyPhone(@Path ("ID") String id , @Body String otp);

    @POST("Member/Login")
    Call<loginResponse> login(@Body loginData data);

    @POST("Dashboard/AskQuestion")
    Call<ResponseBody> askQuestion(@Query("token") String token , @Body String question) ;

    @GET("Member/Profile")
    Call<getProfileResponse> getProfileResponse(@Query("token") String token);

    @GET("Dashboard/Home")
    Call<getQuestionsResponse> getQuestions(@Query("token") String token);
}
