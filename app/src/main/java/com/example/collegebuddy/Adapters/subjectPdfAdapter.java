package com.example.collegebuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.subjectPdfListResponse;

import java.util.ArrayList;

public class subjectPdfAdapter extends RecyclerView.Adapter<subjectPdfAdapter.pdfHolder> {

    private ArrayList<subjectPdfListResponse> pdfList;
    OnPdfClickListener listener;

    public subjectPdfAdapter(ArrayList<subjectPdfListResponse> pdfList){
        this.pdfList = pdfList;
    }

    public interface OnPdfClickListener{
        void onPdfClick(int position);
    }

    public void setOnPdfClickListener(OnPdfClickListener listener){
        this.listener = listener;
    }

    @NonNull
    @Override
    public subjectPdfAdapter.pdfHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notes_layout,
                parent, false);
        return new pdfHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull subjectPdfAdapter.pdfHolder holder, int position) {
        subjectPdfListResponse pdf  = pdfList.get(position);
        holder.setPdf_name_text_view(pdf.getPdf_name());

    }

    @Override
    public int getItemCount() {
        return pdfList.size();
    }

    public class pdfHolder extends RecyclerView.ViewHolder {

        TextView pdf_name_text_view;

        public pdfHolder(@NonNull View itemView) {
            super(itemView);
            pdf_name_text_view = itemView.findViewById(R.id.note_name_text_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onPdfClick(position);
                    }
                }
            });

        }

        public void setPdf_name_text_view(String pdf_name) {
            pdf_name_text_view.setText(pdf_name);
        }
    }

}
