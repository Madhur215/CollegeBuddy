package com.example.collegebuddy.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.collegebuddy.R;
import com.example.collegebuddy.models.subjects;

import java.util.ArrayList;

public class subjectsAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<subjects> subjectsArrayList;
    private LayoutInflater inflater;

    public subjectsAdapter(Context ctx , ArrayList<subjects> subjectsArrayList) {
        this.ctx = ctx;
        this.subjectsArrayList = subjectsArrayList;
        inflater = LayoutInflater.from(ctx);
    }


    @Override
    public int getCount() {
        try{
            return subjectsArrayList.size();
        }
        catch(NullPointerException e){
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView names_tv;
        subjects sub = subjectsArrayList.get(position);
        convertView = inflater.inflate(R.layout.subjects_layout , null);
        names_tv = convertView.findViewById(R.id.names_text_view);
        names_tv.setText(sub.getSubject());

        // SET SUBJECT IMAGE !

        return convertView;

    }
}
