package com.example.collegebuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.notesList;

import java.util.ArrayList;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.notesHolder> {

    private ArrayList<notesList> notesArrayList;

    public notesAdapter(ArrayList<notesList> notesLists){
        this.notesArrayList = notesLists;
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
            notesList notes = notesArrayList.get(position);

            holder.setNote_name_text_view(notes.getNotes_name());
            holder.setNote_user_name_text_view(notes.getSent_by_name());
            holder.setNumber_of_pages_text_view(notes.getNumber_of_pages());
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    public class notesHolder extends RecyclerView.ViewHolder{
        TextView note_name_text_view;
        TextView note_user_name_text_view;
        TextView number_of_pages_text_view;
        ImageView download_pdf_image_view;


        public notesHolder(@NonNull View itemView) {
            super(itemView);

            note_name_text_view = itemView.findViewById(R.id.note_name_text_view);
            note_user_name_text_view = itemView.findViewById(R.id.notes_user_name_text_view);
            number_of_pages_text_view = itemView.findViewById(R.id.number_of_pages_text_view);
            download_pdf_image_view = itemView.findViewById(R.id.download_pdf_image_view);


        }

        public void setNote_name_text_view(String notes_name) {
            note_name_text_view.setText(notes_name);
        }

        public void setNote_user_name_text_view(String note_user_name) {
            note_user_name_text_view.setText(note_user_name);
        }

        public void setNumber_of_pages_text_view(String number) {
            number_of_pages_text_view.setText(number);
        }

    }
}
