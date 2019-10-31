package com.example.collegebuddy.Inteface;

import com.example.collegebuddy.models.signUpData;
import com.example.collegebuddy.models.signUpResponse;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonApiHolder {

    @POST("Member/Signup")
    Call<signUpResponse> signUp(@Body signUpData data);

    @POST("Member/Verify/{ID}")
    Call<ResponseBody> verifyPhone(@Path ("ID") String id , @Body String otp);

//    @POST("Member/Login")
//    Call<>




}
