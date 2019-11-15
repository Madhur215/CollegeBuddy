package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegebuddy.Fragments.questionFragment;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.answers;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.Adapters.previousAnswersAdapter;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    private ArrayList<answers> answersArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);
        pr = new prefUtils(this);
        Intent i = getIntent();
        question_id = i.getStringExtra(questionFragment.QUESTION_ID);
        question = i.getStringExtra(questionFragment.QUESTION);
        asked_by_name = i.getStringExtra(questionFragment.ASKED_BY_NAME);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        post_answer_button = findViewById(R.id.post_answer_button);
        question_text_view = findViewById(R.id.question_text_view_write_answer);
        asked_by_name_text_view = findViewById(R.id.asked_by_name_write_answer);
        question_text_view.setText(question);
        asked_by_name_text_view.setText(asked_by_name);
        answer_edit_text = findViewById(R.id.write_answer_edit_text);
        Toolbar answer_toolbar = findViewById(R.id.toolbar_answer_activity);
        setSupportActionBar(answer_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getAnswers();

        post_answer_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkAnswer()) {
                    String answer = answer_edit_text.getText().toString().trim();
                    postAnswer(answer);
                }
            }
        });


    }

    private void getAnswers() {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<List<answers>> call = jsonApiHolder.getAnswers(question_id , token);

        call.enqueue(new Callback<List<answers>>() {
            @Override
            public void onResponse(Call<List<answers>> call, Response<List<answers>> response) {
                if(response.isSuccessful()){
                        setAdapter();
                        List<answers> answersList  = response.body();

                        for(answers ans : answersList){

                            String name = ans.getAnswered_by_name();
                            String answer = ans.getAnswer();
                            String upvotes = ans.getUpvotes();

                            answers show_answers = new answers(name , answer , upvotes);
                            answersArrayList.add(show_answers);
                        }
                }
                else{
                    Toast.makeText(AnswerActivity.this, "An Error Occurred", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<answers>> call, Throwable t) {
                Toast.makeText(AnswerActivity.this, "No response from the server!", Toast.LENGTH_SHORT).show();
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

    private void setAdapter(){

        RecyclerView answerRecyclerView = findViewById(R.id.answers_recycler_view);
        answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        answerRecyclerView.setHasFixedSize(true);
        answersArrayList = new ArrayList<>();
        previousAnswersAdapter mAdapter = new previousAnswersAdapter(answersArrayList);
        answerRecyclerView.setAdapter(mAdapter);
    }

    private boolean checkAnswer(){
        String ans = answer_edit_text.getText().toString().trim();
        if(ans.isEmpty()){
            Toast.makeText(this, "Invalid Answer!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
