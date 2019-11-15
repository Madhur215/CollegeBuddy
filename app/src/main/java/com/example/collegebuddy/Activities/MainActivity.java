package com.example.collegebuddy.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegebuddy.Fragments.downloadsFragment;
import com.example.collegebuddy.Fragments.exploreFragment;
import com.example.collegebuddy.Fragments.homeFragment;
import com.example.collegebuddy.Fragments.profileFragment;
import com.example.collegebuddy.Fragments.questionFragment;
import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.R;
import com.example.collegebuddy.models.profileResponse;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigation;
    private Fragment selectedFragment;
    JsonApiHolder jsonApiHolder;
    private prefUtils pr;
    public static profileResponse pres = new profileResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigation = findViewById(R.id.bottom_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        (getSupportActionBar()).setDisplayShowTitleEnabled(false);
        bottomNavigation.setOnNavigationItemSelectedListener(navListener);
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        pr = new prefUtils(this);
        getProfile();

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

                        String username = pr.getUser_name();
                        String branch = pr.getBranch();
                        String college = pr.getCollege();
                        String year = pr.getYear();
//                        pres = new profileResponse();

                        pres.setUser_name(username);
                        pres.setCollege(college);
                        pres.setYear(year);
                        pres.setBranch(branch);


                    }
                }
                else{
                    Toast.makeText(MainActivity.this, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<profileResponse>> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

}
