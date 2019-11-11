package com.example.collegebuddy.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.collegebuddy.Fragments.downloadsFragment;
import com.example.collegebuddy.Fragments.exploreFragment;
import com.example.collegebuddy.Fragments.homeFragment;
import com.example.collegebuddy.Fragments.profileFragment;
import com.example.collegebuddy.Fragments.questionFragment;
import com.example.collegebuddy.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Fragment selectedFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar_ask_question_activity);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                    new homeFragment()).commit();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat, menu);
        return true;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.home:
                            selectedFragment = new homeFragment();
                            break;
                        case R.id.question:
                            selectedFragment = new questionFragment();
                            break;
                        case R.id.downloads:
                            selectedFragment = new downloadsFragment();
                            break;
                        case R.id.explore:
                            selectedFragment = new exploreFragment();
                            break;
                        case R.id.profile:
                            selectedFragment = new profileFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                            selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        MenuItem homeItem = bottomNavigation.getMenu().getItem(0);

        if(selectedFragment instanceof homeFragment) {
            super.onBackPressed();
        }
        else{

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_main,
                    new homeFragment()).commit();
            bottomNavigation.setSelectedItemId(homeItem.getItemId());
        }
    }
}
