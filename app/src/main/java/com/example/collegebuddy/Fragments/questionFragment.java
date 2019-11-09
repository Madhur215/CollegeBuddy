package com.example.collegebuddy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Activities.AnswerActivity;
import com.example.collegebuddy.Activities.askQuestionActivity;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.questions;
import com.example.collegebuddy.models.questionsResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.Adapters.questionAdapter;
import com.example.collegebuddy.utils.retrofitInstance;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    private ArrayList<questions> questionsArrayList;
    private JsonApiHolder jsonApiHolder;
    private prefUtils pr;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        FloatingActionButton ask_question = getView().findViewById(R.id.ask_question_floating_button);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        getData();

        ask_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , askQuestionActivity.class);
                startActivity(i);
            }
        });

    }

    private void getData() {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);
        Call<List<questionsResponse>> call = jsonApiHolder.getQuestions(token);


        call.enqueue(new Callback<List<questionsResponse>>() {
            @Override
            public void onResponse(Call<List<questionsResponse>> call,
                                   Response<List<questionsResponse>> response) {

                if(response.isSuccessful()){
                    setAdapter();
                    List<questionsResponse> questions = response.body();

                    for(questionsResponse question : questions){

                        String q = question.getQuestion();
                        String qId = question.getQuestion_id();
                        String name = question.getAsked_by_name();
                        com.example.collegebuddy.models.questions ques = new questions(q , qId , name);

                        questionsArrayList.add(ques);
                    }

                }
                else{
                    Toast.makeText(getContext(), response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<questionsResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setAdapter() {

        RecyclerView questionRecyclerView = getView().findViewById(R.id.questions_recycler_view);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionRecyclerView.setHasFixedSize(true);
        questionsArrayList = new ArrayList<>();
        questionAdapter mAdapter = new questionAdapter(questionsArrayList);
        questionRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnQuestionClickListener(new questionAdapter.OnQuestionClickListener() {
            @Override
            public void onQuestionClick(int position) {
                questions clickedQuestion = questionsArrayList.get(position);
                Intent i = new Intent(getContext() , AnswerActivity.class);
                i.putExtra(QUESTION_ID , clickedQuestion.getQid());
                i.putExtra(QUESTION , clickedQuestion.getQuestion());
                i.putExtra(ASKED_BY_NAME , clickedQuestion.getName());
                startActivity(i);
            }
        });

    }
}
