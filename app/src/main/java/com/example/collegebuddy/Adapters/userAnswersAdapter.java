package com.example.collegebuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.answers;

import java.util.ArrayList;

public class userAnswersAdapter extends RecyclerView.Adapter<userAnswersAdapter.userAnswersHolder> {


    private ArrayList<answers> answersList;
    public userAnswersAdapter(ArrayList<answers> data){
        answersList = data;
    }

    @NonNull
    @Override
    public userAnswersAdapter.userAnswersHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answers_layout,
                parent, false);
        return new userAnswersHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull userAnswersAdapter.userAnswersHolder holder, int position) {

        answers ans = answersList.get(position);

        holder.setAnswer_text_view(ans.getAnswer());
        holder.setAnsweredByName(ans.getAnswered_by_name());
        holder.setUpvotes_text_view(ans.getUpvotes());

    }

    @Override
    public int getItemCount() {
        return answersList.size();
    }

    public class userAnswersHolder extends RecyclerView.ViewHolder{

        TextView user_answered_by_name_text_view;
        TextView user_date_text_view;
        TextView user_answer_text_view;
        TextView user_upvotes_text_view;


        public userAnswersHolder(@NonNull View itemView) {
            super(itemView);
            user_answered_by_name_text_view = itemView.findViewById(R.id.answered_by_user_name_answer_layout);
            user_date_text_view = itemView.findViewById(R.id.date_answers_layout);
            user_answer_text_view = itemView.findViewById(R.id.answer_text_view_answers_layout);
            user_upvotes_text_view = itemView.findViewById(R.id.number_of_likes_answers_layout);


        }

        void setAnsweredByName(String username){
            user_answered_by_name_text_view.setText(username);
        }

        void setUpvotes_text_view(String upvotes){
            user_upvotes_text_view.setText(upvotes);

        }

        void setAnswer_text_view(String ans){
            user_answer_text_view.setText(ans);
        }

        public void setDate_text_view(TextView date_text_view) {
            this.user_date_text_view = date_text_view;
        }

    }
}
