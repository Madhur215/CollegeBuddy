package com.example.collegebuddy.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
        void downloadPdf(int position);
        void addToLibrary(int position);
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
        ImageView download_pdf_image;
        ImageView add_to_library_image;

        public pdfHolder(@NonNull View itemView) {
            super(itemView);
            pdf_name_text_view = itemView.findViewById(R.id.note_name_text_view);
            download_pdf_image = itemView.findViewById(R.id.download_pdf_image_view);
            add_to_library_image = itemView.findViewById(R.id.add_to_library_image);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION){
                        listener.onPdfClick(position);
                    }
                }
            });

//            download_pdf_image.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int p = getAdapterPosition();
//                    if(p != RecyclerView.NO_POSITION){
//                        listener.downloadPdf(p);
//                    }
//                }
//            });

            add_to_library_image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int p = getAdapterPosition();
                    if(p != RecyclerView.NO_POSITION){
                        listener.addToLibrary(p);
                    }
                }
            });
        }

        public void setPdf_name_text_view(String pdf_name) {
            pdf_name_text_view.setText(pdf_name);
        }
    }

}
