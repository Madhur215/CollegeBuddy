package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.collegebuddy.Fragments.signUpFragment;
import com.example.collegebuddy.R;

public class LoginSignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sign_up);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                new signUpFragment())
                .commit();
    }
}
