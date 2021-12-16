package com.fpoly.managebookings.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref_RememberUserAndPass {
    //Storage File
    private static final String SHARED_PREF_NAME = "RememberAccount";


    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";

    @SuppressLint("StaticFieldLeak")
    private static SharedPref_RememberUserAndPass mInstance;

    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    private SharedPref_RememberUserAndPass(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPref_RememberUserAndPass getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref_RememberUserAndPass(context);
        }
        return mInstance;
    }

    public void storeUsername(String avatar) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, avatar);
        editor.apply();
    }

    public String loggedInUsername() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME, null);
    }

    public void storePassword(String avatar) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(PASSWORD, avatar);
        editor.apply();
    }

    public String loggedInPassword() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(PASSWORD, null);
    }

    //Logout user
    public void clearSharedPreferences() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
