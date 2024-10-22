package com.example.collegebuddy.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Activities.AnswerActivity;
import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.Activities.askQuestionActivity;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.questions;
import com.example.collegebuddy.models.questionsResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.Adapters.questionAdapter;
import com.example.collegebuddy.utils.retrofitInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class questionFragment extends Fragment {


    public final static String QUESTION_ID ="qid";
    public final static String QUESTION = "question";
    public final static String ASKED_BY_NAME = "name";
    public final static String IMAGE_URI = "image";

    private ArrayList<questions> questionsArrayList;
    private JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private ImageView progress_image;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        if(!isConnected(getActivity())){
            buildDialog(getActivity()).show();
        }

        else {

            FloatingActionButton ask_question = getView().findViewById(R.id.ask_question_floating_button);
            jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
            pr = new prefUtils(getContext());
            TextView college_name_question_fragment = view.findViewById(R.id.college_name_question_fragment);
            college_name_question_fragment.setText(MainActivity.pres.getCollege());
            progress_image = getView().findViewById(R.id.progressBar);
            progress_image.setImageResource(R.drawable.loader);
            progress_image.setVisibility(View.VISIBLE);
            getData();

            ask_question.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getContext(), askQuestionActivity.class);
                    startActivity(i);
                }
            });
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    private void getData() {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);
        Log.d(token, "getData: TOKEN");
        Call<List<questionsResponse>> call = jsonApiHolder.getQuestions(token);


        call.enqueue(new Callback<List<questionsResponse>>() {
            @Override
            public void onResponse(Call<List<questionsResponse>> call,
                                   Response<List<questionsResponse>> response) {
                progress_image.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    try {
                        setAdapter();
                        List<questionsResponse> questions = response.body();

                        for (questionsResponse question : questions) {

                            String q = question.getQuestion();
                            String qId = question.getQuestion_id();
                            String name = question.getAsked_by_name();
                            String date = question.getAsked_on_date();
                            String image = question.getImage();
                            com.example.collegebuddy.models.questions ques = new questions(q, qId,
                                    name, date , image );

                            questionsArrayList.add(ques);
                        }
                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
                    }

                }


                else{
                    try {
                        Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();

                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e) , "onResponse: TOAST");
                    }
                }
            }

            @Override
            public void onFailure(Call<List<questionsResponse>> call, Throwable t) {
                try {
                    Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Log.d(String.valueOf(e), "onFailure: GET CONTEXT");
                }
            }
        });
    }

    private void setAdapter() {

        try {
            RecyclerView questionRecyclerView = getView().findViewById(R.id.questions_recycler_view);
            questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            questionRecyclerView.setHasFixedSize(true);
            questionsArrayList = new ArrayList<>();
            questionAdapter mAdapter = new questionAdapter(questionsArrayList , getContext());
            questionRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnQuestionClickListener(new questionAdapter.OnQuestionClickListener() {
                @Override
                public void onQuestionClick(int position) {
                    questions clickedQuestion = questionsArrayList.get(position);
                    Intent i = new Intent(getContext(), AnswerActivity.class);
                    i.putExtra(QUESTION_ID, clickedQuestion.getQid());
                    i.putExtra(QUESTION, clickedQuestion.getQuestion());
                    i.putExtra(ASKED_BY_NAME, clickedQuestion.getName());
                    i.putExtra(IMAGE_URI , clickedQuestion.getImage());
                    startActivity(i);
                }
            });
        }
        catch (NullPointerException e){
            Log.d(String.valueOf(e), "setAdapter: ADAPTER GONE");
        }

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
