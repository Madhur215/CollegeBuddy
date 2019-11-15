package com.example.collegebuddy.Fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
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
    private int mrequestCode = 10;
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
//        Button download_button = getView().findViewById(R.id.download_pdf_button);
        RecyclerView pdf_recycler_view = getView().findViewById(R.id.subject_pdf_recycler_view);
        getPdfs();

//        download_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                downloadPdf();
//            }
//        });

    }

    private void downloadPdf() {

        if(ContextCompat.checkSelfPermission(getContext() , Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getContext() , "granted" , Toast.LENGTH_SHORT).show();


            Call<ResponseBody> call = jsonApiHolder.downloadPdf(1 , token);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });


        }
        else{
            if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity() ,
                    Manifest.permission.ACCESS_COARSE_LOCATION)){

                new AlertDialog.Builder(getContext())
                        .setTitle("Permission needed")
                        .setMessage("Allow to download pdf!")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(getActivity(),
                                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, mrequestCode);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();
            }else{
                ActivityCompat.requestPermissions(getActivity() ,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE} , mrequestCode);


            }
        }


    }

    private void getPdfs() {


        Call<List<subjectPdfListResponse>> call = jsonApiHolder.getPdfs(token , pdfActivity.pdf_key);

        call.enqueue(new Callback<List<subjectPdfListResponse>>() {
            @Override
            public void onResponse(Call<List<subjectPdfListResponse>> call, Response<List<subjectPdfListResponse>> response) {
                if(response.isSuccessful()){
                    try {
                        setAdapter();
                        List<subjectPdfListResponse> pdf = response.body();

                        for (subjectPdfListResponse p : pdf) {

                            String pdf_name = p.getPdf_name();
                            String pdf_key = p.getPdf_key();

                            subjectPdfListResponse showPdf = new subjectPdfListResponse(pdf_name, pdf_key);
                            pdfArrayList.add(showPdf);

                        }
                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
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
        try {
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
                    pdf_webview.setVisibility(View.INVISIBLE);
                }
            });
        }
        catch (NullPointerException e){
            Log.d(String.valueOf(e) , "setAdapter: ADAPTER");
        }
    }




}
