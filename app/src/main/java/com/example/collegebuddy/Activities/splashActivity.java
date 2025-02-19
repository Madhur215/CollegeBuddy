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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        pref = new prefUtils(this);
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
        },1500);
    }

}
