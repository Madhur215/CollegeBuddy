package com.example.collegebuddy.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.HashMap;

public class profileFragment extends Fragment {

    private TextView user_name_text_view;
    TextView year_text_view;
    TextView branch_text_view;
    ImageView user_image_view;
    TextView upload_image_text_view;
    JsonApiHolder jsonApiHolder;
    private prefUtils pr;

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
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(getContext());
        getProfile();

    }

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
    }
}
