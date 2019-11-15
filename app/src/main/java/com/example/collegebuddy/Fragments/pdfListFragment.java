package com.example.collegebuddy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Activities.pdfActivity;
import com.example.collegebuddy.Adapters.subjectPdfAdapter;
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

public class pdfListFragment extends Fragment {

    RecyclerView pdf_recycler_view;
    private ArrayList<subjectPdfListResponse> pdfArrayList;
    private JsonApiHolder jsonApiHolder;
    private String token;
    private int p;
    private WebView pdf_webview;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.pdf_list_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        prefUtils pr = new prefUtils(getContext());
        pdf_webview = getView().findViewById(R.id.pdf_web_view);
        HashMap<String , String> sendToken =  pr.getUserDetails();
        token = sendToken.get(prefUtils.KEY_TOKEN);
        RecyclerView pdf_recycler_view = getView().findViewById(R.id.subject_pdf_recycler_view);
        getPdfs();
    }

    private void getPdfs() {


        Call<List<subjectPdfListResponse>> call = jsonApiHolder.getPdfs(token , pdfActivity.pdf_key);

        call.enqueue(new Callback<List<subjectPdfListResponse>>() {
            @Override
            public void onResponse(Call<List<subjectPdfListResponse>> call, Response<List<subjectPdfListResponse>> response) {
                if(response.isSuccessful()){
                    setAdapter();
                    List<subjectPdfListResponse> pdf = response.body();

                    for(subjectPdfListResponse p : pdf){

                        String pdf_name = p.getPdf_name();
                        String pdf_key = p.getPdf_key();

                        subjectPdfListResponse showPdf = new subjectPdfListResponse(pdf_name , pdf_key);
                        pdfArrayList.add(showPdf);

                    }

                }
                else{
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<subjectPdfListResponse>> call, Throwable t) {
                Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setAdapter() {
        RecyclerView pdfRecyclerView = getView().findViewById(R.id.subject_pdf_recycler_view);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pdfRecyclerView.setHasFixedSize(true);
        pdfArrayList = new ArrayList<>();
        subjectPdfAdapter mAdapter = new subjectPdfAdapter(pdfArrayList);
        pdfRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnPdfClickListener(new subjectPdfAdapter.OnPdfClickListener() {
            @Override
            public void onPdfClick(int position) {
                subjectPdfListResponse clickedPdf = pdfArrayList.get(position);
                String p_key = clickedPdf.getPdf_key();
                p = Integer.parseInt(p_key);


                String url = "https://869592ac.ngrok.io/api/PDFController/ViewPDF/" + p + "?token=" + token;
                String finalUrl = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
                pdf_webview.setVisibility(View.VISIBLE);
                pdf_webview.getSettings().setBuiltInZoomControls(true);
//                pdf_webview.getSettings().setJavaScriptEnabled(true);

                pdf_webview.loadUrl(finalUrl);
//                getFragmentManager().beginTransaction().replace(R.id.fragment_container_pdf ,
//                        new viewPdfFragment()).commit();
            }
        });
    }

    private void showPdf(int p) {

        Call<ResponseBody> call = jsonApiHolder.viewPdfs(p , token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });


    }
}
