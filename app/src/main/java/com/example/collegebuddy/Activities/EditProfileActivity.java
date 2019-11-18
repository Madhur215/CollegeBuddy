package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.editDetails;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private editDetails changedDetails;
    EditText changed_branch_edit_text;
    EditText changed_year_edit_text;
    EditText changed_password_edit_text;
    EditText old_password_edit_text;
    private String p;
    JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private String toastMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(this);
        changed_branch_edit_text = findViewById(R.id.branch_change_edit_text);
        changed_year_edit_text = findViewById(R.id.year_change_edit_text);
        changed_password_edit_text = findViewById(R.id.new_password_edit_text);
        old_password_edit_text = findViewById(R.id.old_password_edit_text);
        Button save_details_button = findViewById(R.id.save_details_button);
        ImageView edit_profile_back_button = findViewById(R.id.edit_profile_back_button);
        edit_profile_back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        save_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkOldPassword() && checkNewPassword() && checkYear() && checkBranch()){
                    p = old_password_edit_text.getText().toString().trim();
                    String new_password = changed_password_edit_text.getText().toString().trim();
                    String y = changed_year_edit_text.getText().toString().trim();
                    String b = changed_branch_edit_text.getText().toString().trim();
                    changedDetails = new editDetails(b , y , new_password , p);
                    closeKeyboard();
                    editProfile();
                }
                else {
                    Toast.makeText(EditProfileActivity.this, toastMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void editProfile() {
        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);
        Call<String> call = jsonApiHolder.editProfile(token , changedDetails);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()){
//                    Toast.makeText(EditProfileActivity.this, "Details Saved!",
//                            Toast.LENGTH_SHORT).show();
                    String message = response.body();
                    Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    finish();
                }
                {
                    Toast.makeText(EditProfileActivity.this, "An Error Occurred!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkOldPassword() {
        String p = old_password_edit_text.getText().toString().trim();
        if(p.length() == 0){
            toastMessage = "Invalid Old Password!";
            return false;
        }
        return true;
    }

    private boolean checkNewPassword(){
        String p = changed_password_edit_text.getText().toString().trim();
        if(p.length() == 0){
            toastMessage = "Invalid New Password!";
            return false;
        }
        else if(p.length() < 6){
            toastMessage = "New Password Should Have At Least 6 Characters!";
        }
        return true;
    }

    private boolean checkYear(){
        String y = changed_year_edit_text.getText().toString().trim();
        if(y.length() == 0){
            toastMessage = "Invalid Year!";
            return false;
        }
        return true;
    }

    private boolean checkBranch(){
        String b = changed_branch_edit_text.getText().toString().trim();
        if(b.length() == 0){
            toastMessage = "Invalid Branch!";
            return false;
        }
        return true;
    }

    private void closeKeyboard() {

        View view = this.getCurrentFocus();
        if(view != null){
            InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken() , 0);
        }
    }

}
