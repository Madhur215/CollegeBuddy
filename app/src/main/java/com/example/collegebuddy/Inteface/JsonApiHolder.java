package com.example.collegebuddy.Inteface;

import com.example.collegebuddy.models.answers;
import com.example.collegebuddy.models.members;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.models.questionsResponse;
import com.example.collegebuddy.models.loginData;

import com.example.collegebuddy.models.loginResponse;
import com.example.collegebuddy.models.signUpData;
import com.example.collegebuddy.models.signUpResponse;
import com.example.collegebuddy.models.subjectPdfListResponse;
import com.example.collegebuddy.models.subjects;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface JsonApiHolder {

    @POST("Member/Signup")
    Call<signUpResponse> signUp(@Body signUpData data);

    @POST("Member/Verify/{ID}")
    Call<String> verifyPhone(@Path ("ID") String id , @Body String otp);

    @POST("Member/Login")
    Call<loginResponse> login(@Body loginData data);

    @POST("Dashboard/AskQuestion")
    Call<ResponseBody> askQuestion(@Query("token") String token , @Body String question) ;

    @GET("Member/Profile")
    Call<List<profileResponse>> getProfile(@Query("token") String token);

    @GET("Dashboard/Home")
    Call<List<questionsResponse>> getQuestions(@Query("token") String token);

    @POST("Dashboard/AddAnswer/{QID}")
    Call<ResponseBody> addAnswer(@Path("QID") String id , @Query("token")  String token ,
                                 @Body String answer);

    @GET("Dashboard/QuestionDetails/{QID}")
    Call<List<answers>> getAnswers(@Path("QID") String id , @Query("token") String token);

    @GET("Contact/List")
    Call<List<members>> getMembers(@Query("token") String token);

    @GET("Contact/SubjectList")
    Call<List<subjects>> getSubjects(@Query("token") String token);

    @GET("PDFcontroller/SubjectPDF")
    Call<List<subjectPdfListResponse>> getPdfs(@Query("token") String token , @Query("key") String key);

    @GET
    Call<ResponseBody> downloadPdf(@Url String url);

    @POST("PDFController/AddToLibrary/{PKEY}")
    Call<ResponseBody> addToLibrary(@Path("PKEY") int key , @Query("token") String token);

    @GET("PDFController/Library")
    Call<List<subjectPdfListResponse>> getLibrary(@Query("token") String token);

    @GET("Contact/AnswerTab")
    Call<ResponseBody> getUserAnswers(@Query("token") String token);

    @GET("Contact/QuestionTab")
    Call<List<questionsResponse>> getUserQuestions(@Query("token") String token);




}
