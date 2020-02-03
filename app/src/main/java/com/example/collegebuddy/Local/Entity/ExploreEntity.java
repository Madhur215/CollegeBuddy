package com.example.collegebuddy.Local.Entity;


import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
@Entity(tableName = "explore_table")
public class ExploreEntity  {


    @Getter
    @PrimaryKey(autoGenerate = true)
    private int id;

    @SerializedName("Name")
    private String member_name;
    @SerializedName("Branch")
    private String member_branch;
    @SerializedName("Year")
    private String member_year;
}
