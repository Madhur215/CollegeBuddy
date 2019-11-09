package com.example.collegebuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.members;

import java.util.ArrayList;

public class membersListAdapter extends RecyclerView.Adapter<membersListAdapter.memberHolder> {

    private ArrayList<members> membersArrayList;

    public membersListAdapter(ArrayList<members> membersList) {
        membersArrayList = membersList;
    }

    @NonNull
    @Override
    public membersListAdapter.memberHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.members_layout,
                parent, false);
        return new memberHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull membersListAdapter.memberHolder holder, int position) {
            members student = membersArrayList.get(position);

            holder.setMember_name_text_view(student.getMember_name());
            holder.setMember_branch_text_view(student.getMember_branch());
            holder.setMember_year_text_view(student.getMember_year());

    }

    @Override
    public int getItemCount() {
        return membersArrayList.size();
    }

    public class memberHolder extends RecyclerView.ViewHolder{

        TextView member_name_text_view;
        TextView member_year_text_view;
        TextView member_branch_text_view;


        public memberHolder(@NonNull View itemView) {
            super(itemView);

            member_name_text_view = itemView.findViewById(R.id.user_name_member_layout);
            member_branch_text_view = itemView.findViewById(R.id.branch_text_view_members_list);
            member_year_text_view = itemView.findViewById(R.id.year_text_view_members_list);

        }

        void setMember_name_text_view(String member_name){
            member_name_text_view.setText(member_name);
        }

        void setMember_branch_text_view(String member_branch){
            member_branch_text_view.setText(member_branch);
        }

        void setMember_year_text_view(String member_year){
            member_year_text_view.setText(member_year);
        }



    }



}
