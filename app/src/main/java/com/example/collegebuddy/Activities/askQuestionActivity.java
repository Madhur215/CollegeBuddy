package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.collegebuddy.Fragments.loginFragment;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class askQuestionActivity extends AppCompatActivity {


    private String question;
    private JsonApiHolder jsonApiHolder;
    prefUtils pr;
    private String ask_anonymous = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_question);
        final EditText ask_question_edit_text = findViewById(R.id.enter_question_edit_text);
        Button ask_button = findViewById(R.id.ask_question_button);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(this);

        ask_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                question = ask_question_edit_text.getText().toString().trim();
                askQuestion(question);
            }
        });

    }

    public void askQuestion(String question){

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);
        Log.d(token, "ASK_QUESTION: TOKEN");
        Log.d(question, "askQuestion: question");
        Call<ResponseBody> call = jsonApiHolder.askQuestion(token , question);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

//                ResponseBody body = response.body();
//                Toast.makeText(askQuestionActivity.this, String.valueOf(body), Toast.LENGTH_SHORT).show();
                Toast.makeText(askQuestionActivity.this, "Question Added!", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(askQuestionActivity.this, MainActivity.class);
                startActivity(i);

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(askQuestionActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void askAnonymously(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        if(checked){
            ask_anonymous = "true";
        }
    }


}
