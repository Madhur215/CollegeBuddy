package com.example.collegebuddy.utils;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.collegebuddy.Fragments.profile_answers_fragment;
import com.example.collegebuddy.Fragments.profile_questions_fragment;

public class pageAdapter extends FragmentPagerAdapter {

    private int numOfTabs;

    public pageAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new profile_questions_fragment();
            case 1:
                return new profile_answers_fragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
