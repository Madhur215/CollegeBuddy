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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class loginFragment extends Fragment {

    private EditText mobile_number_edit_text;
    private EditText password_edit_text;
    private String mobile_number;
    private String password;
    private JsonApiHolder jsonApiHolder;
    public static String token;
    prefUtils sp;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                                                        @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        mobile_number_edit_text = getView().findViewById(R.id.phone_number_login_edit_text);
        password_edit_text = getView().findViewById(R.id.password_login_edit_text);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        Button login_button = getView().findViewById(R.id.login_button);
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
                mobile_number = mobile_number_edit_text.getText().toString().trim();
                password = password_edit_text.getText().toString().trim();
                login();
            }
        });
    }

    private void login() {
        loginData login_data = new loginData(mobile_number , password);
        Call<loginResponse> call = jsonApiHolder.login(login_data);

        call.enqueue(new Callback<loginResponse>() {
            @Override
            public void onResponse(Call<loginResponse> call, Response<loginResponse> response) {
                loginResponse loginToken = response.body();
                token = loginToken.getAuth_token();
                sp.createLogin(token);
                Log.d(String.valueOf(token), "onResponse: token");
                Intent i = new Intent(getContext() , MainActivity.class);
                startActivity(i);
            }

            @Override
            public void onFailure(Call<loginResponse> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
