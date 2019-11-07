package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegebuddy.Fragments.homeFragment;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.HashMap;
import java.util.ResourceBundle;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AnswerActivity extends AppCompatActivity {

    String question_id;
    String question;
    String asked_by_name;
    TextView question_text_view;
    TextView asked_by_name_text_view;
    Button post_answer_button;
    private JsonApiHolder jsonApiHolder;
    private String answer_anonymously;
    EditText answer_edit_text;
    prefUtils pr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        pr = new prefUtils(this);
        Intent i = getIntent();
        question_id = i.getStringExtra(homeFragment.QUESTION_ID);
        question = i.getStringExtra(homeFragment.QUESTION);
        asked_by_name = i.getStringExtra(homeFragment.ASKED_BY_NAME);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        post_answer_button = findViewById(R.id.post_answer_button);
        question_text_view = findViewById(R.id.question_text_view_write_answer);
        asked_by_name_text_view = findViewById(R.id.asked_by_name_write_answer);
        question_text_view.setText(question);
        asked_by_name_text_view.setText(asked_by_name);
        answer_edit_text = findViewById(R.id.write_answer_edit_text);

        post_answer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String answer = answer_edit_text.getText().toString().trim();
                postAnswer(answer);
            }
        });


    }

    private void postAnswer(String answer) {
        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<ResponseBody> call = jsonApiHolder.addAnswer(question_id , token , answer);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AnswerActivity.this, "Answer Added!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(AnswerActivity.this, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AnswerActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void answerAnonymously(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            answer_anonymously = "true";
        }
    }
}
