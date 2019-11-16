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
import com.example.collegebuddy.models.subjectPdfListResponse;

import java.util.ArrayList;

public class notesAdapter extends RecyclerView.Adapter<notesAdapter.notesHolder> {

    private ArrayList<subjectPdfListResponse> notesArrayList;
    private OnNotesClickListener listener;

    public notesAdapter(ArrayList<subjectPdfListResponse> notesLists){
        this.notesArrayList = notesLists;

    }

    public interface OnNotesClickListener{
        void onNotesClick(int position);
    }

    public void setOnNotesClickListener(OnNotesClickListener listener){
        this.listener = listener;
    }


    @NonNull
    @Override
    public notesAdapter.notesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_notes_layout,
                parent, false);
        return new notesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull notesAdapter.notesHolder holder, int position) {
        subjectPdfListResponse notes = notesArrayList.get(position);

            holder.setNote_name_text_view(notes.getPdf_name());
//            holder.setNote_user_name_text_view(notes.getSent_by_name());
//            holder.setNumber_of_pages_text_view(notes.getNumber_of_pages());
    }

    @Override
    public int getItemCount() {
        try {
            return notesArrayList.size();
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }

    public class notesHolder extends RecyclerView.ViewHolder{
        TextView note_name_text_view;
        TextView note_user_name_text_view;
        TextView number_of_pages_text_view;
//        ImageView download_pdf_image_view;


        public notesHolder(@NonNull View itemView) {
            super(itemView);

            note_name_text_view = itemView.findViewById(R.id.note_name_text_view_home);
            note_user_name_text_view = itemView.findViewById(R.id.notes_user_name_text_view);
            number_of_pages_text_view = itemView.findViewById(R.id.number_of_pages_text_view);
//            download_pdf_image_view = itemView.findViewById(R.id.download_pdf_image_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onNotesClick(position);
                    }
                }
            });

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
