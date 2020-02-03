package com.example.collegebuddy.ViewModel;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.collegebuddy.Local.Entity.ExploreEntity;
import com.example.collegebuddy.Repository.ExploreRepository;

import java.util.List;

public class ExploreViewModel extends AndroidViewModel {

    private ExploreRepository exploreRepository;
    private LiveData<List<ExploreEntity>> membersList;

    public ExploreViewModel(@NonNull Application application) {
        super(application);
        exploreRepository = new ExploreRepository(application);
        membersList = exploreRepository.getAllMembers();
    }

    public LiveData<List<ExploreEntity>> getMembersList() {
        return membersList;
    }
}
