package com.example.collegebuddy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.Activities.askQuestionActivity;
import com.example.collegebuddy.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class homeFragment extends Fragment {

    FloatingActionButton ask_question;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ask_question = getView().findViewById(R.id.ask_question_button);
        ask_question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , askQuestionActivity.class);
                startActivity(i);
            }
        });
    }
}
