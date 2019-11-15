package com.example.collegebuddy.Fragments;

import android.os.Bundle;
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

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.retrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otpFragment extends Fragment {

    private JsonApiHolder jsonApiHolder;
    private EditText otp_edit_text;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.otp_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        Button verify_phone_button = getView().findViewById(R.id.verify_otp_button);
        otp_edit_text = getView().findViewById(R.id.enter_otp_edit_text);
        TextView  user_name_text_view = getView().findViewById(R.id.user_name_otp);
        user_name_text_view.setText(signUpFragment.full_name);
        verify_phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String otp = otp_edit_text.getText().toString().trim();
                verifyPhone(otp);
            }
        });

    }

    private void verifyPhone(String otp) {
        Call<String> call = jsonApiHolder.verifyPhone(signUpDetailsFragment.id , otp);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

                if (response.isSuccessful()) {
                    String message = response.body();
                    if(message.equals("Verified")) {
                        getFragmentManager().beginTransaction().replace(R.id.fragment_container_login,
                                new loginFragment()).commit();
                    }
                    else{
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
