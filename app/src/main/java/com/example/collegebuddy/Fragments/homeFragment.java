package com.example.collegebuddy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.Activities.pdfActivity;
import com.example.collegebuddy.Activities.splashActivity;
import com.example.collegebuddy.Adapters.notesAdapter;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.Adapters.subjectsAdapter;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.models.subjectPdfListResponse;
import com.example.collegebuddy.models.subjects;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
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
    private String token;
    private ArrayList<subjectPdfListResponse> notesArrayList;
    private WebView pdf_web_view_home;


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
        pdf_web_view_home = getView().findViewById(R.id.pdf_web_view_home);
        HashMap<String , String> sendToken =  pr.getUserDetails();
        token = sendToken.get(prefUtils.KEY_TOKEN);
        getSubjects();
        getLibrary();
        ImageView user_image = getView().findViewById(R.id.user_image_home);
        if(MainActivity.pres.getImageUri() != null) {
            String imgUrl = "https://85d52d03.ngrok.io" + MainActivity.pres.getImageUri();
            Picasso.with(getContext()).load(imgUrl).into(user_image);
//            img.setImageURI(Uri.parse(imgUrl));
        }
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

    private void getLibrary() {

        Call<List<subjectPdfListResponse>> call = jsonApiHolder.getLibrary(token);

        call.enqueue(new Callback<List<subjectPdfListResponse>>() {
            @Override
            public void onResponse(Call<List<subjectPdfListResponse>> call,
                                   Response<List<subjectPdfListResponse>> response) {
                if(response.isSuccessful()) {
                    try {
                        setAdapter();
                        List<subjectPdfListResponse> notes = response.body();

                        for(subjectPdfListResponse note : notes){

                            String name = note.getPdf_name();
                            String key = note.getPdf_key();

                            subjectPdfListResponse addNotes = new subjectPdfListResponse(name , key);
                            notesArrayList.add(addNotes);

                        }
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                    }
                    catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<subjectPdfListResponse>> call, Throwable t) {
                try{
                Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
            }
                catch (NullPointerException e){
                    e.printStackTrace();
                }
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
        Log.d(token, "getSubjects: TOKEN");
        Call<List<subjects>> call = jsonApiHolder.getSubjects(token);

        call.enqueue(new Callback<List<subjects>>() {
            @Override
            public void onResponse(Call<List<subjects>> call, Response<List<subjects>> response) {
                Log.e("....", String.valueOf(response));
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
                try{
                Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
            }
                catch (NullPointerException e){
                    Log.d(String.valueOf(e), "onFailure: GET CONTEXT");
                }
            }

        });

    }

    private void setAdapter(){
        try{
            RecyclerView questionRecyclerView = getView().findViewById(R.id.library_recycler_view);
            questionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            questionRecyclerView.setHasFixedSize(true);
            notesArrayList = new ArrayList<>();
            notesAdapter mAdapter = new notesAdapter(notesArrayList);
            questionRecyclerView.setAdapter(mAdapter);
            mAdapter.setOnNotesClickListener(new notesAdapter.OnNotesClickListener() {
                @Override
                public void onNotesClick(int position) {
                    subjectPdfListResponse clickedPdf = notesArrayList.get(position);
                    String p_key = clickedPdf.getPdf_key();
                    int p = Integer.parseInt(p_key);

                    String url = "https://d9efaef9.ngrok.io/api/PDFController/ViewPDF/" + p + "?token=" + token;
                    String finalUrl = "http://drive.google.com/viewerng/viewer?embedded=true&url=" + url;
                    pdf_web_view_home.setVisibility(View.VISIBLE);
                    pdf_web_view_home.getSettings().setBuiltInZoomControls(true);

                    pdf_web_view_home.loadUrl(finalUrl);
                    pdf_web_view_home.setVisibility(View.INVISIBLE);
                }
            });
        }
        catch (NullPointerException e){
            Log.d(String.valueOf(e), "setAdapter: ADAPTER GONE");
        }
    }


}
