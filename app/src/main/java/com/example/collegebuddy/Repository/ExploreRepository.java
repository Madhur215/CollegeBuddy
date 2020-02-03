package com.example.collegebuddy.Repository;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;

import com.example.collegebuddy.Inteface.JsonApiHolder;
import com.example.collegebuddy.Local.DAO.ExploreDAO;
import com.example.collegebuddy.Local.Database.CollegeDatabase;
import com.example.collegebuddy.Local.Entity.ExploreEntity;
import com.example.collegebuddy.models.members;
import com.example.collegebuddy.utils.prefUtils;
import com.example.collegebuddy.utils.retrofitInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreRepository {

    private ExploreDAO exploreDAO;
    private LiveData<List<ExploreEntity>> allMembers;
    private JsonApiHolder jsonApiHolder;
    private prefUtils sp;
    private Application application;

    public ExploreRepository(Application application){
        CollegeDatabase collegeDatabase =  CollegeDatabase.getInstance(application);
        exploreDAO = collegeDatabase.exploreDAO();
        allMembers = exploreDAO.getAllMembers();
        jsonApiHolder = retrofitInstance.getRetrofitInstance().create(JsonApiHolder.class);
        sp = new prefUtils(application);
        this.application = application;
        getMembers();
    }

    private void getMembers() {

        Call<List<members>> call = jsonApiHolder.getMembers(sp.getToken());

        call.enqueue(new Callback<List<members>>() {
            @Override
            public void onResponse(Call<List<members>> call, Response<List<members>> response) {
//                members_progress_bar.setVisibility(View.GONE);
                if(response.isSuccessful()){
                    try {

                        List<members> studentsList = response.body();
                        List<ExploreEntity> exploreEntityList = new ArrayList<>();
                        if (studentsList != null) {
                            for (members student : studentsList) {

                                String name = student.getMember_name();
                                String branch = student.getMember_branch();
                                String year = student.getMember_year();

                                ExploreEntity exploreEntity = new ExploreEntity(1 ,name, branch, year);
                                exploreEntityList.add(exploreEntity);

                            }
                            exploreDAO.insert(exploreEntityList);
                        }
                    }
                    catch (NullPointerException e){
                        Log.d(String.valueOf(e), "onResponse: EMPTY ARRAY");
                    }
                }
                else{
                    Toast.makeText(application, "An Error Occurred!", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<members>> call, Throwable t) {
                Toast.makeText(application, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public LiveData<List<ExploreEntity>> getAllMembers() {
        return allMembers;
    }

}
