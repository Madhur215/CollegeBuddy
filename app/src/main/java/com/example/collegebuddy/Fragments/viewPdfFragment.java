package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.prefUtils;

import java.util.HashMap;

public class viewPdfFragment extends Fragment {

    WebView pdf_webview;
    String url;
    prefUtils pr;
    int p_key;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.view_pdf_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        pdf_webview = getView().findViewById(R.id.pdf_webview);
//        pr = new prefUtils(getContext());
//        p_key = pdfListFragment.p;
//        HashMap<String , String> sendToken =  pr.getUserDetails();
//        String token = sendToken.get(prefUtils.KEY_TOKEN);
//        url = "https://869592ac.ngrok.io/api/PDFController/ViewPDF/" + p_key + "?token=" + token;
//        String finalUrl = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
//        pdf_webview.getSettings().setBuiltInZoomControls(true);
//        pdf_webview.getSettings().setJavaScriptEnabled(true);
//
//        pdf_webview.loadUrl(finalUrl);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getFragmentManager().beginTransaction().replace(R.id.fragment_container_pdf , new pdfListFragment())
//                .commit();
//    }
}
