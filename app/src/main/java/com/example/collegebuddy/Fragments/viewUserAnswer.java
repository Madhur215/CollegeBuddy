package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Adapters.userAnswersAdapter;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.answers;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class viewUserAnswer extends Fragment {

    private String QID;
    private JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private ArrayList<answers> answersArrayList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_user_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        TextView user_question = view.findViewById(R.id.user_question_text_view);
        TextView asked_by_name = view.findViewById(R.id.user_asked_by_name_text_view);
        TextView date_text_view = view.findViewById(R.id.user_date_text_view);

        user_question.setText(userQuestionFragment.USER_QUESTION);
        asked_by_name.setText(userQuestionFragment.USER_ASKED_BY_NAME);
        date_text_view.setText(userQuestionFragment.USER_DATE);
        QID = userQuestionFragment.USER_QUESTION_ID;
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        getAnswers();

    }

    private void getAnswers() {
        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<List<answers>> call = jsonApiHolder.getAnswers(QID , token);

        call.enqueue(new Callback<List<answers>>() {
            @Override
            public void onResponse(Call<List<answers>> call, Response<List<answers>> response) {
                    if(response.isSuccessful()){
                        try{
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
                            e.printStackTrace();
                        }

                    }
                    else{
                        try{
                            Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                        }
                        catch (NullPointerException e){
                            e.printStackTrace();
                        }
                    }
            }

            @Override
            public void onFailure(Call<List<answers>> call, Throwable t) {
                 try{
                     Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
                 }
                 catch (NullPointerException e){
                     e.printStackTrace();
                 }
            }
        });

    }

    private void setAdapter() {

        try {
            RecyclerView answerRecyclerView = getView().findViewById(R.id.user_answers_recycler_view);
            answerRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            answerRecyclerView.setHasFixedSize(true);
            answersArrayList = new ArrayList<>();
            userAnswersAdapter mAdapter = new userAnswersAdapter(answersArrayList);
            answerRecyclerView.setAdapter(mAdapter);
        }
        catch (NullPointerException e){
            Log.d(String.valueOf(e), "setAdapter: ADAPTER");
        }
    }
}
