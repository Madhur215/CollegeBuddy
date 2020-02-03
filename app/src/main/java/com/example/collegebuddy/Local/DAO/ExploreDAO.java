package com.example.collegebuddy.Local.DAO;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.example.collegebuddy.Local.Entity.ExploreEntity;

import java.util.List;

@Dao
public interface ExploreDAO {

    @Insert
    void insert(List<ExploreEntity> exploreEntities);

    @Query("select * from explore_table")
    LiveData<List<ExploreEntity>> getAllMembers();
}
