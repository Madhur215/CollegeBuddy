package com.example.collegebuddy.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.notesList;

import java.util.ArrayList;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.notesHolder> {

    private ArrayList<notesList> notesLists;

    public notesAdapter(ArrayList<notesList> notesLists){
        this.notesLists = notesLists;
    }


    @NonNull
    @Override
    public notesAdapter.notesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,
                parent, false);
        return new notesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesAdapter.notesHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return notesLists.size();
    }

    public class notesHolder extends RecyclerView.ViewHolder{
        TextView note_name_text_view;
        TextView note_user_name_text_view;
        TextView number_of_pages_text_view;


        public notesHolder(@NonNull View itemView) {
            super(itemView);

            note_name_text_view = itemView.findViewById(R.id.note_name_text_view);
            note_user_name_text_view = itemView.findViewById(R.id.notes_user_name_text_view);
            number_of_pages_text_view = itemView.findViewById(R.id.number_of_pages_text_view);



        }
    }
}
