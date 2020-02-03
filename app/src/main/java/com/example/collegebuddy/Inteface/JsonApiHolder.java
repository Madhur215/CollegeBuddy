package com.example.collegebuddy.Inteface;

import com.example.collegebuddy.models.answers;
import com.example.collegebuddy.models.editDetails;
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

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    Call<String> addToLibrary(@Path("PKEY") int key , @Query("token") String token);

    @GET("PDFController/Library")
    Call<List<subjectPdfListResponse>> getLibrary(@Query("token") String token);

    @GET("Contact/QuestionTab")
    Call<List<questionsResponse>> getUserQuestions(@Query("token") String token);

    @POST("Member/EditProfile")
    Call<String> editProfile(@Query("token") String token , @Body editDetails data );

    @Multipart
    @POST("Contacts/ImageUpload")
    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody ,
                                   @Query("token") String token);

//    @Multipart
//    @POST("feed/post")
//    Call<ResponseBody> uploadImage(@Part MultipartBody.Part file, @Part("name") RequestBody requestBody);


    @POST("Member/ResendOtp/{ID}")
    Call<ResponseBody> resendOTP(@Path("ID") String ID);

    @POST("PDFController/DeleteLibrary/{PKEY}")
    Call<ResponseBody> deleteFromLibrary(@Path("PKEY") String PKEY , @Query("token") String token);


}
