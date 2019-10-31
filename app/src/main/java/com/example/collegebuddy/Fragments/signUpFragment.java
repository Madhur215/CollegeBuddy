package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class signUpFragment extends Fragment {

    private String branch;
    private String year;
    private String college;
    private String full_name;
    private String password;
    private String mobile_number;
    private JsonApiHolder jsonApiHolder;
    public static String id;
    private Spinner sp_branch;
    private Spinner sp_year;
    private Spinner sp_college;
    private EditText full_name_edit_text;
    private EditText password_edit_text;
    private EditText mobile_number_edit_text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp_branch = getView().findViewById(R.id.select_branch_drop_down);
        sp_year = getView().findViewById(R.id.select_year_drop_down);
        sp_college = getView().findViewById(R.id.select_college_drop_down);
        full_name_edit_text = getView().findViewById(R.id.full_name_register_edit_text);
        password_edit_text = getView().findViewById(R.id.password_register_edit_text);
        mobile_number_edit_text = getView().findViewById(R.id.phone_number_Register_edit_text);
        Button register_button = getView().findViewById(R.id.sign_up_button);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                branch = sp_branch.getSelectedItem().toString();
                year = sp_year.getSelectedItem().toString();
                college = sp_college.getSelectedItem().toString();
                full_name =full_name_edit_text.getText().toString().trim();
                password = password_edit_text.getText().toString().trim();
                mobile_number = mobile_number_edit_text.getText().toString().trim();

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
                id = signUpResponse.getID();

                getFragmentManager().beginTransaction().replace(R.id.fragment_container_login , 
                        new otpFragment()).commit();

            }

            @Override
            public void onFailure(Call<signUpResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

}
