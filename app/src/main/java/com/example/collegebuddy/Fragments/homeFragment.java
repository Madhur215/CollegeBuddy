package com.example.collegebuddy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.collegebuddy.Activities.askQuestionActivity;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.questions;
import com.example.collegebuddy.utils.questionAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class homeFragment extends Fragment {

    private RecyclerView questionRecyclerView;
    private questionAdapter mAdapter;
    private ArrayList<questions> questionsArrayList;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        FloatingActionButton ask_question = getView().findViewById(R.id.ask_question_floating_action_button);
        questionRecyclerView = getView().findViewById(R.id.questions_recyclerView);
        questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        questionRecyclerView.setHasFixedSize(true);
        questionsArrayList = new ArrayList<>();
        mAdapter = new questionAdapter(questionsArrayList);
        questionRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnQuestionClickListener(new questionAdapter.OnQuestionClickListener() {
            @Override
            public void onQuestionClick(int position) {

            }
        });


        ask_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , askQuestionActivity.class);
                startActivity(i);
            }
        });
    }
}
