package com.example.collegebuddy.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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
                    changedDetails = new editDetails(b , y , new_password);
                    editProfile();
                }
            }
        });

    }

    private void editProfile() {
        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);
        Call<ResponseBody> call = jsonApiHolder.editProfile(token , changedDetails , p);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(EditProfileActivity.this, "Details Saved!",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
                {
                    Toast.makeText(EditProfileActivity.this, "An Error Occurred!",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditProfileActivity.this, "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean checkOldPassword() {
        String p = old_password_edit_text.getText().toString().trim();
        return p.length() != 0;
    }

    private boolean checkNewPassword(){
        String p = changed_password_edit_text.getText().toString().trim();
        return p.length() != 0;
    }

    private boolean checkYear(){
        String y = changed_year_edit_text.getText().toString().trim();
        return y.length() != 0;
    }

    private boolean checkBranch(){
        String b = changed_branch_edit_text.getText().toString().trim();
        return b.length() != 0;
    }

}
