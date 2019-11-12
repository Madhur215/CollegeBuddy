package com.example.collegebuddy.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class userData {

    private static SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private Context ctx;

    private static final String PREF_NAME = "user_data";
    public static final String USERNAME = "username";
    public static final String COLLEGE = "college";
    public static final String YEAR = "year";
    public static final String BRANCH = "branch";

    public userData(Context context){

        ctx = context;
        sp = ctx.getSharedPreferences(PREF_NAME , Context.MODE_PRIVATE);
        editor = sp.edit();


    }

    public void setUserData(String name , String year , String branch , String college){
        editor.putString(USERNAME , name);
        editor.putString(COLLEGE , college);
        editor.putString(YEAR , year);
        editor.putString(BRANCH , branch);
    }


    public HashMap<String, String> getUserData(){

        HashMap<String, String> user = new HashMap<>();
        user.put(USERNAME, sp.getString(USERNAME, null));
        user.put(COLLEGE, sp.getString(COLLEGE, null));
        user.put(BRANCH, sp.getString(BRANCH, null));
        user.put(YEAR, sp.getString(YEAR, null));
        return user;
    }

}
