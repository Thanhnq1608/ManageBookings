package com.fpoly.managebookings.tool;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.fpoly.managebookings.views.login.LoginActivity;

public class SharedPref_InfoUser {
    //Storage File
    private static final String SHARED_PREF_NAME = "ManageBooking";

    //Username
    private static final String USER_EMAIL = "user_token";
    private static final String USER_TOKEN = "user_email";
    private static final String USER_FULLNAME = "full_name";
    private static final String USER_AVATAR = "user_avatar";

    @SuppressLint("StaticFieldLeak")
    private static SharedPref_InfoUser mInstance;

    @SuppressLint("StaticFieldLeak")
    private static Context mCtx;

    private SharedPref_InfoUser(Context context) {
        mCtx = context;
    }

    public static synchronized SharedPref_InfoUser getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPref_InfoUser(context);
        }
        return mInstance;
    }

    //method to store user data
    public void storeUserToKen(String token) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN, token);
        editor.apply();
    }

    public String LoggedInUserToken() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_TOKEN, null);
    }

    public void storeUserEmail(String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_EMAIL, email);
        editor.apply();
    }

    public String LoggedInEmail() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_EMAIL, null);
    }

    public void storeUserFullName(String email) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_FULLNAME, email);
        editor.apply();
    }

    public String LoggedInFullName() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_FULLNAME, null);
    }

    public void storeUserAvatar(String avatar) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_AVATAR, avatar);
        editor.apply();
    }

    public String LoggedInUserAvatar() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_AVATAR, null);
    }

    //Logout user
    public void clearSharedPreferences() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
