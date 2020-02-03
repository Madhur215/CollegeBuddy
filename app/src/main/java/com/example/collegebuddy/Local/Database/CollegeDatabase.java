package com.example.collegebuddy.Local.Database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.collegebuddy.Local.DAO.ExploreDAO;
import com.example.collegebuddy.Local.Entity.ExploreEntity;

@Database(entities = {ExploreEntity.class} , version = 1)
public abstract class CollegeDatabase extends RoomDatabase {


    private static CollegeDatabase instance;

    public abstract ExploreDAO exploreDAO();

    public static synchronized CollegeDatabase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context, CollegeDatabase.class, "hospital_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
