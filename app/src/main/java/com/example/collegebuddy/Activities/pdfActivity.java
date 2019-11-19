package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.example.collegebuddy.Adapters.subjectPdfAdapter;
import com.example.collegebuddy.Fragments.homeFragment;
import com.example.collegebuddy.Fragments.pdfListFragment;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.subjectPdfListResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class pdfActivity extends AppCompatActivity {


    public static String pdf_key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Intent i = getIntent();
        pdf_key = i.getStringExtra(homeFragment.SUBJECT_KEY);
        Toolbar tb = findViewById(R.id.toolbar_pdf_activity);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_pdf ,
                new pdfListFragment())
                .commit();

    }

}
