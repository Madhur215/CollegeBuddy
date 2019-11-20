package com.example.collegebuddy.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.collegebuddy.Activities.EditProfileActivity;
import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.Activities.UserQuestionsActivity;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static androidx.core.provider.FontsContractCompat.FontRequestCallback.RESULT_OK;

public class profileFragment extends Fragment {

    private prefUtils pr;
    private int GALLERY_REQUEST_CODE = 10;
    private int STORAGE_PERMISSION_CODE = 20;
    private JsonApiHolder jsonApiHolder;
    ImageView user_image_view;
    private Uri selectedImage;
    private ProgressBar user_image_progress_bar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        TextView user_name_text_view = getView().findViewById(R.id.user_name_profile);
        TextView year_text_view = getView().findViewById(R.id.year_profile);
        TextView branch_text_view = getView().findViewById(R.id.branch_profile);
        TextView college_text_view = getView().findViewById(R.id.college_name_text_view_profile);
        user_image_view = getView().findViewById(R.id.user_image_profile);
        CardView asked_question_card_view = getView().findViewById(R.id.asked_questions_card_view);
        CardView edit_profile_card_view = getView().findViewById(R.id.edit_profile_card_view);
        CardView logout_card_view = getView().findViewById(R.id.logout_card_view);
        pr = new prefUtils(getContext());
        user_image_progress_bar = getView().findViewById(R.id.user_image_progress_bar);
        user_image_progress_bar.setVisibility(View.INVISIBLE);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        ImageView upload_image = getView().findViewById(R.id.upload_image);
        requestStoragePermission();
        upload_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickFromGallery();
            }
        });

        logout_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        edit_profile_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , EditProfileActivity.class);
                startActivity(i);
            }
        });

        asked_question_card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext() , UserQuestionsActivity.class);
                startActivity(i);
            }
        });

        year_text_view.setText(MainActivity.pres.getYear());
        branch_text_view.setText(MainActivity.pres.getBranch());
        user_name_text_view.setText(MainActivity.pres.getUser_name());
        college_text_view.setText(MainActivity.pres.getCollege());
        if(MainActivity.pres.getImageUri() != null) {
            String imgUrl = "https://51a7e9bd.ngrok.io" + MainActivity.pres.getImageUri();
            Picasso.with(getContext()).load(imgUrl).into(user_image_view);
//            img.setImageURI(Uri.parse(imgUrl));
        }
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                        pr.logoutUser();
                        getActivity().finish();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
       builder.create().show();


    }

    private void pickFromGallery(){

        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
        startActivityForResult(intent,GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode,int resultCode,Intent data){

        if (resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
//            user_image_view.setImageURI(selectedImage);
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContext().getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);

            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String path = cursor.getString(columnIndex);
            Log.d(path, "onActivityResult: IMAGE PATH");
            cursor.close();
            user_image_progress_bar.setVisibility(View.VISIBLE);
            uploadToServer(path);

        }
    }

    private void uploadToServer(String filePath) {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);
        File file = new File(filePath);
        RequestBody fileReqBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("upload", file.getName(), fileReqBody);

        RequestBody name = RequestBody.create(MediaType.parse("text/plain"), "image-type");

        Call<ResponseBody> call = jsonApiHolder.uploadImage(part , name , token);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                user_image_progress_bar.setVisibility(View.INVISIBLE);
                if(response.isSuccessful()){
                    Toast.makeText(getContext(), "Image Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                    user_image_view.setImageURI(selectedImage);

                }
                else{
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                user_image_progress_bar.setVisibility(View.INVISIBLE);
                Toast.makeText(getContext(), "No response from the server!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {

        }

        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {


        if (requestCode == STORAGE_PERMISSION_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Toast.makeText(getContext(), "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {

                Toast.makeText(getContext(), "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

}
