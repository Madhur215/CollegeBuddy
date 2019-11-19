package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Adapters.userQuestionAdapter;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.questions;
import com.example.collegebuddy.models.questionsResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userQuestionFragment extends Fragment {

    public static String USER_QUESTION_ID ;
    public static String USER_QUESTION ;
    public static String USER_ASKED_BY_NAME;
    public static String USER_DATE;

    private ArrayList<questions> questionsList;
    private JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private ImageView progress_image;
    private ProgressBar user_question_progress_bar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_question_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        user_question_progress_bar = getView().findViewById(R.id.user_question_progress_bar);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        getUserQuestions();
    }

    private void getUserQuestions() {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);
        Call<List<questionsResponse>> call = jsonApiHolder.getUserQuestions(token);

        call.enqueue(new Callback<List<questionsResponse>>() {
            @Override
            public void onResponse(Call<List<questionsResponse>> call, Response<List<questionsResponse>> response) {
                user_question_progress_bar.setVisibility(View.INVISIBLE);
                if(response.isSuccessful()){
                    try {
                        setAdapter();
                        List<questionsResponse> questions = response.body();

                        for (questionsResponse question : questions) {

                            String q = question.getQuestion();
                            String qId = question.getQuestion_id();
                            String name = question.getAsked_by_name();
                            String date = question.getAsked_on_date();
                            com.example.collegebuddy.models.questions ques = new questions(q, qId,
                                    name, date);

                            questionsList.add(ques);
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
                user_question_progress_bar.setVisibility(View.INVISIBLE);
                try{
                    Toast.makeText(getContext(), "No response from the server!",
                            Toast.LENGTH_SHORT).show();
                }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
            }
        });

    }

    private void setAdapter() {

        try{
            RecyclerView questionRecyclerView = getView().findViewById(R.id.user_questions_recycler_view);
            questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            questionRecyclerView.setHasFixedSize(true);
            questionsList = new ArrayList<>();
            userQuestionAdapter mAdapter = new userQuestionAdapter(questionsList);
            questionRecyclerView.setAdapter(mAdapter);

            mAdapter.setOnUserQuestionClickListener(new userQuestionAdapter.OnUserQuestionClickListener() {
                @Override
                public void onUserQuestionClick(int position) {
                    questions clickedQuestion = questionsList.get(position);
                    USER_QUESTION = clickedQuestion.getQuestion();
                    USER_ASKED_BY_NAME = clickedQuestion.getName();
                    USER_QUESTION_ID = clickedQuestion.getQid();
                    USER_DATE = clickedQuestion.getDate();

                    getFragmentManager().beginTransaction().replace(R.id.fragment_container_user_question ,
                            new viewUserAnswer()).commit();
                }
            });

        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }
}
