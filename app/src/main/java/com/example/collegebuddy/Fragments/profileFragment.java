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
import androidx.viewpager.widget.ViewPager;

import com.example.collegebuddy.Activities.MainActivity;
import com.example.collegebuddy.R;
import com.example.collegebuddy.utils.pageAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;

public class profileFragment extends Fragment {


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
        // CHECK HERE
        TabLayout profile_tab_layout = getView().findViewById(R.id.profile_tab_layout);
        TabItem question_tab = getView().findViewById(R.id.question_tab);
        TabItem answers_tab = getView().findViewById(R.id.answers_tab);
        final ViewPager viewPager = getView().findViewById(R.id.tab_layout_view_pager);
        pageAdapter pg = new pageAdapter(getFragmentManager(), profile_tab_layout.getTabCount());
        viewPager.setAdapter(pg);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(profile_tab_layout));

        year_text_view.setText(MainActivity.pres.getYear());
        branch_text_view.setText(MainActivity.pres.getBranch());
        user_name_text_view.setText(MainActivity.pres.getUser_name());
        college_text_view.setText(MainActivity.pres.getCollege());


        profile_tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(profile_tab_layout));


    }



}
