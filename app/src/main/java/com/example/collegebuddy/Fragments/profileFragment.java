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
import androidx.viewpager.widget.ViewPager;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.utils.pageAdapter;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;
import com.example.collegebuddy.utils.userData;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profileFragment extends Fragment {

    userData user_data;

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
        ImageView user_image_view = getView().findViewById(R.id.user_image_profile);
        user_data = new userData(getContext());
        // CHECK HERE
        TabLayout profile_tab_layout = getView().findViewById(R.id.profile_tab_layout);
        TabItem question_tab = getView().findViewById(R.id.question_tab);
        TabItem answers_tab = getView().findViewById(R.id.answers_tab);
        TabItem uploads_tab = getView().findViewById(R.id.uploads_tab);
        TabItem update_tab = getView().findViewById(R.id.update_tab);
        ViewPager viewPager = getView().findViewById(R.id.tab_layout_view_pager);
        pageAdapter pg = new pageAdapter(getFragmentManager(), profile_tab_layout.getTabCount());
        viewPager.setAdapter(pg);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(profile_tab_layout));


        HashMap<String , String> sendToken =  user_data.getUserData();
        String branch = sendToken.get(userData.BRANCH);
        String year = sendToken.get(userData.YEAR);
        year_text_view.setText(year);
        branch_text_view.setText(branch);

      //  getProfile();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
