package com.example.collegebuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.questions;

import java.util.ArrayList;

public class userQuestionAdapter extends  RecyclerView.Adapter<userQuestionAdapter.userQuestionHolder>{

    private ArrayList<questions> questionsArrayList;
    private OnUserQuestionClickListener listener;

    public userQuestionAdapter(ArrayList<questions> data){
        questionsArrayList = data;
    }

    public interface OnUserQuestionClickListener {
        void onUserQuestionClick(int position);
    }

    public void setOnUserQuestionClickListener(OnUserQuestionClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public userQuestionAdapter.userQuestionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_layout,
                parent, false);
        return new userQuestionHolder(view , listener);
    }

    @Override
    public void onBindViewHolder(@NonNull userQuestionAdapter.userQuestionHolder holder, int position) {
        questions ques = questionsArrayList.get(position);

        holder.setUserQuestion(ques.getQuestion());
        holder.setAsked_by_name(ques.getName());
        holder.setDate_text_view(ques.getDate());
    }

    @Override
    public int getItemCount() {
        return questionsArrayList.size();
    }

    public class userQuestionHolder extends RecyclerView.ViewHolder{

        TextView question_text_view;
        TextView asked_by_name;
        TextView date_text_view;

        public userQuestionHolder(@NonNull View itemView , final OnUserQuestionClickListener listener) {
            super(itemView);

            question_text_view = itemView.findViewById(R.id.asked_question_task_view);
            asked_by_name = itemView.findViewById(R.id.asked_by_name_text_view);
            date_text_view = itemView.findViewById(R.id.asked_on_date_question_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onUserQuestionClick(position);
                        }
                    }
                }
            });

        }

        void setUserQuestion(String ques){
            question_text_view.setText(ques);
        }

        void setAsked_by_name(String name){
            asked_by_name.setText(name);
        }

        void setDate_text_view(String date){
            date_text_view.setText(date);
        }
    }
}
