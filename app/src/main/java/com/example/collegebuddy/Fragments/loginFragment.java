package com.example.collegebuddy.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.loginData;
import com.example.collegebuddy.models.loginResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginFragment extends Fragment {

    private TextInputLayout phone_edit_text;
    private TextInputLayout password_edit_text_layout;
    private EditText mobile_number_edit_text;
    private EditText password_edit_text;
    private String mobile_number;
    private String password;
    private JsonApiHolder jsonApiHolder;
    public static String token;
    prefUtils sp;
    private String message;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                                        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mobile_number_edit_text = getView().findViewById(R.id.phone_number_login_edit_text);
        password_edit_text = getView().findViewById(R.id.password_login_edit_text);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        Button login_button = getView().findViewById(R.id.login_button);
        phone_edit_text = getView().findViewById(R.id.mobile_number_login_edit_text);
        password_edit_text_layout = getView().findViewById(R.id.password_text_input_login);
        TextView register_text_view = getView().findViewById(R.id.register_here_text_view);
        sp = new prefUtils(getContext());
        register_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                        new signUpFragment()).commit();
            }
        });

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!validatePhone() | !validatePassword()){
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    return;
                }
                mobile_number = mobile_number_edit_text.getText().toString().trim();
                password = password_edit_text.getText().toString().trim();
                if(mobile_number.length() == 13){
                    login();

                }
                else if(mobile_number.length() == 10){
                    mobile_number = "+91" + mobile_number;
                    login();
                }
            }
        });
    }


    private void login() {
//        pg.setVisibility(View.VISIBLE);
        loginData login_data = new loginData(mobile_number , password);
        Call<loginResponse> call = jsonApiHolder.login(login_data);

        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {

                if(response.isSuccessful()) {

                    loginResponse loginToken = response.body();
                    token = loginToken.getAuth_token();
                    Log.d(token, "onResponse: token");
                    sp.createLogin(token);
                    Log.d(String.valueOf(token), "onResponse: token");
                    Intent i = new Intent(getContext(), MainActivity.class);
//                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    getActivity().finish();
                }
                else if(response.code() == 400){
                    Toast.makeText(getContext(), "Verify Account First!", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 404){
                    Toast.makeText(getContext(), "Wrong phone number or password!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private boolean validatePhone(){

        String phone_number = phone_edit_text.getEditText().getText().toString().trim();

        if(phone_number.isEmpty()){
            phone_edit_text.setError("Field can't be empty!");
            message = "Field can't be empty!";
            return false;
        }
        else if(phone_number.length() < 10){
            phone_edit_text.setError("Invalid Phone Number");
            message = "Invalid Phone Number";
            return false;
        }
        else{
            phone_edit_text.setError(null);
            return true;
        }
    }

    private boolean validatePassword(){

        String password = password_edit_text_layout.getEditText().getText().toString().trim();

        if(password.isEmpty()){
            password_edit_text_layout.setError("Field can't be empty!");
            return false;
        }
        else{
            password_edit_text_layout.setError(null);
            return true;
        }
    }


}
