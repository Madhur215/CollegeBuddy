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

//    RecyclerView pdf_recycler_view;
//    private ArrayList<subjectPdfListResponse> pdfArrayList;
//    private JsonApiHolder jsonApiHolder;
//    prefUtils pr;
    public static String pdf_key;
//    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        Intent i = getIntent();
        pdf_key = i.getStringExtra(homeFragment.SUBJECT_KEY);
//        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
//        pr = new prefUtils(this);
//        HashMap<String , String> sendToken =  pr.getUserDetails();
//        token = sendToken.get(prefUtils.KEY_TOKEN);
        Toolbar tb = findViewById(R.id.toolbar_pdf_activity);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        pdf_recycler_view = findViewById(R.id.subject_pdf_recycler_view);
//        getPdfs();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_pdf ,
                new pdfListFragment())
                .commit();

    }

//    private void getPdfs() {
//
//
//        Call<List<subjectPdfListResponse>> call = jsonApiHolder.getPdfs(token , pdf_key);
//
//        call.enqueue(new Callback<List<subjectPdfListResponse>>() {
//            @Override
//            public void onResponse(Call<List<subjectPdfListResponse>> call, Response<List<subjectPdfListResponse>> response) {
//                if(response.isSuccessful()){
//                    setAdapter();
//                    List<subjectPdfListResponse> pdf = response.body();
//
//                    for(subjectPdfListResponse p : pdf){
//
//                        String pdf_name = p.getPdf_name();
//                        String pdf_key = p.getPdf_key();
//
//                        subjectPdfListResponse showPdf = new subjectPdfListResponse(pdf_name , pdf_key);
//                        pdfArrayList.add(showPdf);
//
//                    }
//
//                }
//                else{
//                    Toast.makeText(pdfActivity.this, "An Error Occurred!", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<List<subjectPdfListResponse>> call, Throwable t) {
//                Toast.makeText(pdfActivity.this, "No response from the server!", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//    }
//
//    private void setAdapter() {
//        RecyclerView pdfRecyclerView = findViewById(R.id.subject_pdf_recycler_view);
//        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        pdfRecyclerView.setHasFixedSize(true);
//        pdfArrayList = new ArrayList<>();
//        subjectPdfAdapter mAdapter = new subjectPdfAdapter(pdfArrayList);
//        pdfRecyclerView.setAdapter(mAdapter);
//        mAdapter.setOnPdfClickListener(new subjectPdfAdapter.OnPdfClickListener() {
//            @Override
//            public void onPdfClick(int position) {
//                subjectPdfListResponse clickedPdf = pdfArrayList.get(position);
//                String p_key = clickedPdf.getPdf_key();
//                int p = Integer.parseInt(p_key);
//                showPdf(p);
//            }
//        });
//    }
//
//    private void showPdf(int p) {
//
//        Call<ResponseBody> call = jsonApiHolder.viewPdfs(p , token);
//
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if(response.isSuccessful()){
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//
//
//    }

}
