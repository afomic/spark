package com.afomic.spark.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by afomic on 18-Oct-16.
 * this is a preference class that is  use to hold the detail of a user
 */
public class PreferenceManager {
    private SharedPreferences preferences;
    private static boolean loggedIn = false;
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String PREF_DEPARTMENT_NAME = "department";
    private static final String PREF_USER_ID = "id";
    private String emptyString = "djkljcamcxk";
    private static final String PREF_ASSOCIATION_NAME="association_name";

    public PreferenceManager(Context context) {
        preferences = context.getSharedPreferences(Constants.PREF_NAME, 0);
    }

    public static void setLoggedIn() {
        loggedIn = true;
    }

    public static void setLoggedOut() {
        loggedIn = false;
    }


    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public String getPassword() {
        return preferences.getString(Constants.PASSWORD, "none");
    }

    public void setPassword(String password) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.PASSWORD, password);
        editor.apply();
    }

    public void setUserId(String userId) {
        SharedPreferences.Editor mEditor = preferences.edit();
        mEditor.putString(PREF_USER_ID, userId);
        mEditor.apply();
    }

    public String getUserId() {
        return preferences.getString(PREF_USER_ID, emptyString);
    }

    public int getSemester() {
        return preferences.getInt(Constants.SEMESTER, 1);
    }

    public void setSemester(int semester) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.SEMESTER, semester);
        editor.apply();
    }
    public String getAssociationName() {
        return preferences.getString(PREF_ASSOCIATION_NAME, null);
    }

    public void setAssociationName(String associationName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_ASSOCIATION_NAME, associationName);
        editor.apply();
    }

    public int getTotalUnit() {
        return preferences.getInt(Constants.TOTAL_UNIT, 1);
    }

    public void setTotalUnit(int totalUnit) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.TOTAL_UNIT, totalUnit);
        editor.apply();
    }

    public int getTotalPoint() {
        return preferences.getInt(Constants.TOTAL_POINT, 1);
    }

    public void setTotalPoint(int totalPoint) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.TOTAL_POINT, totalPoint);
        editor.apply();
    }

    public int getLevel() {
        return preferences.getInt(Constants.LEVEL, 1);
    }

    public void setLevel(int level) {
        Log.e(Constants.TAG, "" + level);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(Constants.LEVEL, level);
        editor.apply();
    }

    public String getOption() {
        return preferences.getString(Constants.OPTION, null);
    }

    public void setOption(String option) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(Constants.OPTION, option);
        editor.apply();
    }

    public boolean isThereASavedData() {
        return !(getPassword().equals("none"));
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public void setDepartmentName(String departmentName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_DEPARTMENT_NAME, departmentName);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public String getDepartmentName() {
        return preferences.getString(PREF_DEPARTMENT_NAME, "");
    }

}
