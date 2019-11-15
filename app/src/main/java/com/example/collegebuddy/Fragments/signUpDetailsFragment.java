package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.signUpData;
import com.example.collegebuddy.models.signUpResponse;
import com.example.collegebuddy.utils.retrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signUpDetailsFragment extends Fragment {

    private String branch;
    private String year;
    private String college;
    private String full_name;
    private String password;
    private String mobile_number;
    private Spinner sp_branch;
    private Spinner sp_year;
    private Spinner sp_college;
    private JsonApiHolder jsonApiHolder;
    public static String id;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_details_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp_branch = getView().findViewById(R.id.select_branch_drop_down);
        sp_year = getView().findViewById(R.id.select_year_drop_down);
        sp_college = getView().findViewById(R.id.select_college_drop_down);
        Button sign_up_button = getView().findViewById(R.id.sign_up_button_details_layout);
        full_name = signUpFragment.full_name;
        password = signUpFragment.password;
        mobile_number = signUpFragment.mobile_number;

        sign_up_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branch = sp_branch.getSelectedItem().toString();
                year = sp_year.getSelectedItem().toString();
                college = sp_college.getSelectedItem().toString();
                signUp();
            }
        });
    }

    private void signUp() {
        signUpData sendData = new signUpData(full_name , college , branch , year , password ,
                mobile_number);

        Call<signUpResponse> call = jsonApiHolder.signUp(sendData);

        call.enqueue(new Callback<signUpResponse>() {
            @Override
            public void onResponse(Call<signUpResponse> call, Response<signUpResponse> response) {
                    signUpResponse signUpResponse = response.body();
                    try {
                        id = signUpResponse.getID();
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                                new otpFragment()).commit();
                    }
                    catch (NullPointerException e){
                        Toast.makeText(getContext(), "Error in getting response from the server!",
                            Toast.LENGTH_SHORT).show();
                    }
            }
            @Override
            public void onFailure(Call<signUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
