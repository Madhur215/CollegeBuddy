package com.example.collegebuddy.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.questions;

import java.util.ArrayList;

public class questionAdapter extends RecyclerView.Adapter<questionAdapter.questionHolder> {

    private ArrayList<questions> mData;
    private OnQuestionClickListener mListener;

    public questionAdapter(ArrayList<questions> data){
        mData = data;
    }

    public interface OnQuestionClickListener {
        void onQuestionClick(int position);
    }

    public void setOnQuestionClickListener(OnQuestionClickListener listener){
        mListener = listener;
    }

    @NonNull
    @Override
    public questionAdapter.questionHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_layout, parent, false);
        return new questionHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull questionAdapter.questionHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class questionHolder extends RecyclerView.ViewHolder{


        public questionHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
