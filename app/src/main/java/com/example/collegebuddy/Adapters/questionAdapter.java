package com.example.collegebuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.questions;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class questionAdapter extends RecyclerView.Adapter<questionAdapter.questionHolder> {

    private ArrayList<questions> mData;
    private OnQuestionClickListener mListener;
    private Context context;

    public questionAdapter(ArrayList<questions> data , Context context){
        mData = data;
        this.context = context;
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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.question_layout,
                parent, false);
        return new questionHolder(view , mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull questionAdapter.questionHolder holder, int position) {
        questions ques = mData.get(position);

        holder.setQuestion(ques.getQuestion());
        holder.setAsked_by_name(ques.getName());
        holder.setDate_text_view(ques.getDate());
        holder.setAsked_by_image(ques.getImage());


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class questionHolder extends RecyclerView.ViewHolder {

        TextView question_text_view;
        TextView asked_by_name;
        TextView date_text_view;
        ImageView asked_by_image;

        public questionHolder(@NonNull View itemView, final OnQuestionClickListener listener) {
            super(itemView);

            question_text_view = itemView.findViewById(R.id.asked_question_task_view);
            asked_by_name = itemView.findViewById(R.id.asked_by_name_text_view);
            date_text_view = itemView.findViewById(R.id.asked_on_date_question_layout);
            asked_by_image = itemView.findViewById(R.id.asked_by_user_image_question_layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onQuestionClick(position);
                        }
                    }
                }
            });

        }

        void setQuestion(String ques) {
            question_text_view.setText(ques);
        }

        void setAsked_by_name(String name) {
            asked_by_name.setText(name);
        }

        void setDate_text_view(String date) {
            date_text_view.setText(date);
        }

        void setAsked_by_image(String image){
            if(image != null) {
                String imgUrl = "https://51a7e9bd.ngrok.io" + image;
                Picasso.with(context).load(imgUrl).into(asked_by_image);
//            img.setImageURI(Uri.parse(imgUrl));
            }
    }
    }
}
