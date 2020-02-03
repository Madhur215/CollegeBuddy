package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.os.CountDownTimer;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class otpFragment extends Fragment {

    private JsonApiHolder jsonApiHolder;
    private EditText otp_edit_text;
    private TextView timer_text_view;
    private CountDownTimer countDownTimer;
    private long timeLeft = 120000;
    private boolean timerRunning = false;
    private TextView resend_otp_text;
    Button verify_phone_button;
    Button resend_otp_button;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.otp_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        verify_phone_button = getView().findViewById(R.id.verify_otp_button);
        otp_edit_text = getView().findViewById(R.id.enter_otp_edit_text);
        TextView  user_name_text_view = getView().findViewById(R.id.user_name_otp);
        timer_text_view = getView().findViewById(R.id.timer_text_view);
        resend_otp_text = getView().findViewById(R.id.resend_otp_text);
        resend_otp_button = getView().findViewById(R.id.resend_otp_button);
        resend_otp_button.setVisibility(View.INVISIBLE);
//        checkTimer();
        startTimer();
        user_name_text_view.setText(signUpFragment.full_name);
        verify_phone_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkOtp()) {
                    String otp = otp_edit_text.getText().toString().trim();
                    stopTimer();
                    timer_text_view.setVisibility(View.INVISIBLE);
                    resend_otp_text.setVisibility(View.INVISIBLE);
//                    checkTimer();

                    verifyPhone(otp);

                }
                else{
                    Toast.makeText(getContext(), "Enter OTP First!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resend_otp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "Resend OTP clicked!", Toast.LENGTH_SHORT).show();
                    resendOtp();
            }

        });

    }

    private void resendOtp() {

        Call<ResponseBody> call = jsonApiHolder.resendOTP(signUpDetailsFragment.id );

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    resend_otp_button.setVisibility(View.INVISIBLE);
                    resend_otp_text.setVisibility(View.VISIBLE);
                    timer_text_view.setVisibility(View.VISIBLE);
                    timeLeft = 120000;
                    startTimer();
                }
                else{
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

//    private void checkTimer() {
//        if(timerRunning){
//            stopTimer();
//        }
//        else{
//            startTimer();
//
//        }
//    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft , 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeft = millisUntilFinished;
                updateTimer();

            }

            @Override
            public void onFinish() {

            }
        }.start();
        timerRunning = true;

    }

    private void updateTimer() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;
        String timeLeftText  = "";

        timeLeftText += "" + minutes;
        timeLeftText += ":";
        if(seconds < 10 ){
            timeLeftText += "0";
        }
        timeLeftText += seconds;
        timer_text_view.setText(timeLeftText);
        if(timeLeftText.equals("0:00")){
            timer_text_view.setVisibility(View.INVISIBLE);
            resend_otp_text.setVisibility(View.INVISIBLE);
            resend_otp_button.setVisibility(View.VISIBLE);
        }

    }

    private void stopTimer() {
        countDownTimer.cancel();
        timerRunning = false;
    }

    private boolean checkOtp(){
        String otp = otp_edit_text.getText().toString().trim();
        return otp.length() != 0;
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
//                        verify_phone_button.setText(R.string.resend_otp);
                    }
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}
