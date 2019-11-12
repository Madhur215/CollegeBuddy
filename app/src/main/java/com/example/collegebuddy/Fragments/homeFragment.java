package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.collegebuddy.R;
import com.example.collegebuddy.Adapters.subjectsAdapter;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.utils.userData;

import java.util.ArrayList;
import java.util.HashMap;

public class homeFragment extends Fragment {

    private  ArrayList<String> subjectsList;
    String[] students = {"RAJAT" , "RAHUL" , "KARAN" };


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.home_fragment, container, false);
//        setHasOptionsMenu(true);
        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        GridView gridView = getView().findViewById(R.id.subjects_grid_view);

        // THIS
        subjectsAdapter adapter = new subjectsAdapter(getContext() , subjectsList);
        gridView.setAdapter(adapter);
        userData user_data = new userData(getContext());
        TextView year_text_view = getView().findViewById(R.id.year_text_view_home);
        TextView branch_text_view = getView().findViewById(R.id.branch_text_view_home);
        profileResponse response = new profileResponse();
//        TextView joined_date_text_view = getView().findViewById(R.id.user_joined_date_home);

//        HashMap<String , String> sendToken =  user_data.getUserData();
//        String branch = sendToken.get(userData.BRANCH);
//        String year = sendToken.get(userData.YEAR);
//        year_text_view.setText(year);
//        branch_text_view.setText(branch);

        year_text_view.setText(response.getYear());
        branch_text_view.setText(response.getBranch());

    }

    private void getSubjects(){

    }


}
