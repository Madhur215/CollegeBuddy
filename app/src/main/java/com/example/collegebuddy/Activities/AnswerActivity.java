package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegebuddy.Fragments.questionFragment;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.answers;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.Adapters.previousAnswersAdapter;
import com.example.collegebuddy.utils.retrofitInstance;
import com.squareup.picasso.Picasso;

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
    ImageView asked_by_image;
    Button post_answer_button;
    private JsonApiHolder jsonApiHolder;
    private String answer_anonymously;
    EditText answer_edit_text;
    prefUtils pr;
    private ArrayList<answers> answersArrayList;
    ProgressBar answers_progress_bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        if(!isConnected(this)){
            buildDialog(this).show();
        }
        else {
            pr = new prefUtils(this);
            Intent i = getIntent();
            question_id = i.getStringExtra(questionFragment.QUESTION_ID);
            question = i.getStringExtra(questionFragment.QUESTION);
            asked_by_name = i.getStringExtra(questionFragment.ASKED_BY_NAME);
            jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
            post_answer_button = findViewById(R.id.post_answer_button);
            question_text_view = findViewById(R.id.question_text_view_write_answer);
            asked_by_name_text_view = findViewById(R.id.asked_by_name_write_answer);
            asked_by_image = findViewById(R.id.asked_by_user_image_write_answer);
            question_text_view.setText(question);
            asked_by_name_text_view.setText(asked_by_name);
            setImage(i.getStringExtra(questionFragment.IMAGE_URI));
            answer_edit_text = findViewById(R.id.write_answer_edit_text);
            Toolbar answer_toolbar = findViewById(R.id.toolbar_answer_activity);
            answers_progress_bar = findViewById(R.id.previous_answers_progress_bar);
            setSupportActionBar(answer_toolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            answers_progress_bar.setVisibility(View.VISIBLE);
            getAnswers();
            ImageView back_image_view = findViewById(R.id.answer_activity_back_image);
            back_image_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            post_answer_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (checkAnswer()) {
                        closeKeyboard();
                        String answer = answer_edit_text.getText().toString().trim();
                        postAnswer(answer);
                    }
                }
            });


        }


    }

    private void setImage(String image_uri) {
        if(image_uri != null) {
            String imgUrl = retrofitInstance.URL + image_uri;
            Picasso.with(this).load(imgUrl).into(asked_by_image);
//            img.setImageURI(Uri.parse(imgUrl));
        }
    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

    private void getAnswers() {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<List<answers>> call = jsonApiHolder.getAnswers(question_id , token);

        call.enqueue(new Callback<List<answers>>() {
            @Override
            public void onResponse(Call<List<answers>> call, Response<List<answers>> response) {
                answers_progress_bar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    try {
                        setAdapter();
                        List<answers> answersList = response.body();

                        for (answers ans : answersList) {

                            String name = ans.getAnswered_by_name();
                            String answer = ans.getAnswer();
                            String upvotes = ans.getUpvotes();

                            answers show_answers = new answers(name, answer, upvotes);
                            answersArrayList.add(show_answers);
                        }
                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
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

    private void setAdapter() {
        try {
            RecyclerView answerRecyclerView = findViewById(R.id.answers_recycler_view);
            answerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            answerRecyclerView.setHasFixedSize(true);
            answersArrayList = new ArrayList<>();
            previousAnswersAdapter mAdapter = new previousAnswersAdapter(answersArrayList);
            answerRecyclerView.setAdapter(mAdapter);
        }
        catch (NullPointerException e){
            Log.d(String.valueOf(e), "setAdapter: ADAPTER");
        }
    }

    private boolean checkAnswer(){
        String ans = answer_edit_text.getText().toString().trim();
        if(ans.isEmpty()){
            Toast.makeText(this, "Invalid Answer!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean isConnected(Context context){
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    private AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Dismiss.");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
                dialog.cancel();
            }
        });

        return builder;
    }
}
