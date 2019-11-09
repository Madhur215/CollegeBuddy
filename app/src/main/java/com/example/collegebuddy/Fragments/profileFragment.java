package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profileFragment extends Fragment {

    private TextView user_name_text_view;
    TextView year_text_view;
    TextView branch_text_view;
    ImageView user_image_view;
    TextView upload_image_text_view;
    JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    private TabLayout profile_tab_layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        user_name_text_view = getView().findViewById(R.id.user_name_profile);
        year_text_view = getView().findViewById(R.id.year_profile);
        branch_text_view = getView().findViewById(R.id.branch_profile);
        upload_image_text_view = getView().findViewById(R.id.upload_image_text_view);
        user_image_view = getView().findViewById(R.id.user_image_profile);

        // CHECK HERE
        profile_tab_layout = getView().findViewById(R.id.profile_tab_layout);

        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        getProfile();

    }

//    @Override
//    public void onResume() {
//        super.onResume();
//    }

    public class sectionPagerAdaper extends FragmentPagerAdapter{

        public sectionPagerAdaper(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            Fragment selectedFragment = null;
            switch (position){
                case 0:
                    selectedFragment = new profile_questions_fragment();
                    break;
                case 1:
                    selectedFragment = new profile_answers_fragment();
                    break;
                case 2:
                    selectedFragment = new profile_uploads_fragment();
                    break;
            }
            return selectedFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }


    private void getProfile() {

        HashMap<String , String> sendToken =  pr.getUserDetails();
        String token = sendToken.get(prefUtils.KEY_TOKEN);

        Call<List<profileResponse>> call = jsonApiHolder.getProfile(token);

        call.enqueue(new Callback<List<profileResponse>>() {
            @Override
            public void onResponse(Call<List<profileResponse>> call, Response<List<profileResponse>> response) {
                if (response.isSuccessful()) {

                    List<profileResponse> profile = response.body();

                    for (profileResponse pr : profile) {

                        user_name_text_view.setText(pr.getUser_name());
                        branch_text_view.setText(pr.getBranch());
                        year_text_view.setText(pr.getYear());

                    }
                }
                else{
                    Toast.makeText(getContext(), "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<profileResponse>> call, Throwable t) {
                    Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
