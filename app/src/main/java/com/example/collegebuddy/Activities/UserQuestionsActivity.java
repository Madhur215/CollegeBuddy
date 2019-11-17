package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.collegebuddy.Fragments.userQuestionFragment;
import com.example.collegebuddy.R;

public class UserQuestionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_questions);
        Toolbar user_question_toolbar = findViewById(R.id.toolbar_user_question_activity);
        setSupportActionBar(user_question_toolbar);
        ImageView back_button = findViewById(R.id.user_question_back_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_user_question ,
                new userQuestionFragment()).commit();
    }
}
