package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.subjectsAdapter;

import java.util.ArrayList;

public class homeFragment extends Fragment {

    GridView gridView;
    ArrayList<String> names = new ArrayList<>();
    String[] students = {"RAJAT" , "RAHUL" , "KARAN" };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.home_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridView = getView().findViewById(R.id.subjects_grid_view);
        subjectsAdapter adapter = new subjectsAdapter(getContext() , students);
        gridView.setAdapter(adapter);

    }


}
