package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.google.android.material.textfield.TextInputLayout;

public class signUpFragment extends Fragment {

    private String branch;
    private String year;
    private String college;
    public static String full_name;
    public static String password;
    public static String mobile_number;
    private JsonApiHolder jsonApiHolder;
    public static String id;
    private Spinner sp_branch;
    private Spinner sp_year;
    private Spinner sp_college;
    private EditText full_name_edit_text;
    private EditText password_edit_text;
    private EditText mobile_number_edit_text;
    TextInputLayout full_name_text_input;
    String toastMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.sign_up_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        full_name_edit_text = getView().findViewById(R.id.full_name_register_edit_text);
        password_edit_text = getView().findViewById(R.id.password_register_edit_text);
        mobile_number_edit_text = getView().findViewById(R.id.phone_number_Register_edit_text);
        full_name_text_input = getView().findViewById(R.id.input_layout_full_name);
        TextView login_text_view = getView().findViewById(R.id.login_text_view);
        Button next_button = getView().findViewById(R.id.sign_up_next_button);

        login_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                        new loginFragment()).commit();
            }
        });

        next_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkFullName() && checkMobileNumber() && checkPassword()) {
                    full_name = full_name_edit_text.getText().toString().trim();
                    password = password_edit_text.getText().toString().trim();
                    mobile_number = mobile_number_edit_text.getText().toString().trim();
                    signUpDetails();
                }
                else{
                    Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signUpDetails() {
        getFragmentManager().beginTransaction().replace(R.id.fragment_container_login ,
                new signUpDetailsFragment()).commit();
    }

    private boolean checkFullName(){
        String name = full_name_edit_text.getText().toString().trim();
        if(name.length() == 0){
            toastMessage = "Invalid Name";
            return false;
        }
        else return true;
    }

    private boolean checkPassword(){
        String p = password_edit_text.getText().toString().trim();
        if(p.length() == 0){
            toastMessage = "Invalid Password";
            return false;
        }
        else if(p.length() < 6){
            toastMessage = "Password Should Have At Least 6 Characters!";
            return false;
        }
        else
            return true;
    }

    private boolean checkMobileNumber(){
        String phone = mobile_number_edit_text.getText().toString().trim();
        if(phone.length() == 0 || phone.length() < 10){
            toastMessage = "Invalid Phone!";
            return false;
        }
        else return true;
    }
}
