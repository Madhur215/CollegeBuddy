package com.example.collegebuddy.utils;

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

    //CHANGE ARRAY TYPE

    private ArrayList<String> subjectsArrayList;
    private LayoutInflater inflater;
    String[] names;

    public subjectsAdapter(Context ctx , String[] names) {
        this.ctx = ctx;
//        this.subjectsArrayList = subjectsArrayList;
        this.names =names;
        inflater = LayoutInflater.from(ctx);
    }



    @Override
    public int getCount() {
        return names.length;
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

        convertView = inflater.inflate(R.layout.subjects_layout , null);
        names_tv = convertView.findViewById(R.id.names_text_view);
        names_tv.setText(names[position]);

        // SET SUBJECT IMAGE !

        return convertView;

    }
}
