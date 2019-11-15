package com.example.collegebuddy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.Activities.pdfActivity;
import com.example.collegebuddy.Activities.splashActivity;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.Adapters.subjectsAdapter;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.models.subjects;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class homeFragment extends Fragment {

    private  ArrayList<subjects> subjectsList;
    private prefUtils pr;
    private JsonApiHolder jsonApiHolder;
    private GridView gridView;
    public final static String SUBJECT_KEY ="skey";
    ProgressBar subjects_progress_bar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridView = getView().findViewById(R.id.subjects_grid_view);

        // THIS
        pr = new prefUtils(getContext());
        subjects_progress_bar = getView().findViewById(R.id.subjects_progress_bar);
        subjects_progress_bar.setVisibility(View.VISIBLE);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        getSubjects();
        TextView year_text_view = getView().findViewById(R.id.year_text_view_home);
        TextView branch_text_view = getView().findViewById(R.id.branch_text_view_home);

        year_text_view.setText(MainActivity.pres.getYear());
        branch_text_view.setText(MainActivity.pres.getBranch());
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                subjectDetails(position);
            }
        });
    }

    private void subjectDetails(int position) {
        subjects s = subjectsList.get(position);
        Intent i = new Intent(getContext() , pdfActivity.class);
        i.putExtra(SUBJECT_KEY  , s.getSubject_key());
        startActivity(i);

    }

    private void getSubjects(){
        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<List<subjects>> call = jsonApiHolder.getSubjects(token);

        call.enqueue(new Callback<List<subjects>>() {
            @Override
            public void onResponse(Call<List<subjects>> call, Response<List<subjects>> response) {

                subjects_progress_bar.setVisibility(View.GONE);
                if(response.isSuccessful()) {
                    try {
                        subjectsList = new ArrayList<>();
                        subjectsAdapter adapter = new subjectsAdapter(getContext(), subjectsList);
                        gridView.setAdapter(adapter);
                        List<subjects> subjects = response.body();

                        for (com.example.collegebuddy.models.subjects sub : subjects) {

                            String SUBJECT = sub.getSubject();
                            String SUBJECT_KEY = sub.getSubject_key();
                            com.example.collegebuddy.models.subjects s = new subjects(SUBJECT, SUBJECT_KEY);
                            subjectsList.add(s);

                        }
                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e), "onResponse: ARRAY EMPTY");
                    }
                }

                else{
                    try {
                        Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();

                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e) , "onResponse: TOAST");
                    }
                    }
            }

            @Override
            public void onFailure(Call<List<subjects>> call, Throwable t) {
                Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });

    }


}
