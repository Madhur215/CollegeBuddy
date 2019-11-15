package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class splashActivity extends AppCompatActivity {

    prefUtils pref;
    JsonApiHolder jsonApiHolder;
    public static profileResponse pres = new profileResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = new prefUtils(this);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
//        getProfile();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){

                if(pref.isLoggedIn()){
                    Intent intent = new Intent(splashActivity.this , MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Intent intent = new Intent(splashActivity.this , LoginSignUpActivity.class);
                    startActivity(intent);
                    finish();
                }

            }
        },2500);
    }


    private void getProfile() {

        HashMap<String , String> sendToken =  pref.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<List<profileResponse>> call = jsonApiHolder.getProfile(token);

        call.enqueue(new Callback<List<profileResponse>>() {
            @Override
            public void onResponse(Call<List<profileResponse>> call, Response<List<profileResponse>> response) {
                if (response.isSuccessful()) {

                    List<profileResponse> profile = response.body();

                    for (profileResponse pr : profile) {

                        String username = pr.getUser_name();
                        String branch = pr.getBranch();
                        String college = pr.getCollege();
                        String year = pr.getYear();
//                        pres = new profileResponse();

                        pres.setUser_name(username);
                        pres.setCollege(college);
                        pres.setYear(year);
                        pres.setBranch(branch);


                    }
                }
                else{
                    Toast.makeText(splashActivity.this, "An Error Occurred!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<profileResponse>> call, Throwable t) {
                Toast.makeText(splashActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
