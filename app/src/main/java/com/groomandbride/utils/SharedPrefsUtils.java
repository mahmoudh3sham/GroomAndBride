package com.groomandbride.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.groomandbride.data.models.UserModel;

public class SharedPrefsUtils {

    private static String PREFS_NAME = "groom_pride";
    private static SharedPrefsUtils sharedPrefsUtils;
    private SharedPreferences sharedPreferences;
    private static String PREF_USER = "pref_groom_pride";
    private static String ACCESS_TOKEN = "access_token";
    private static String USER_NAME = "username";
    private static String DEVICE_TOKEN = "device_token";
    private static String USER_ID = "user_id";
    private static String USER_OBJECT = "user_object";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String IS_FIRST_TIME_HOME = "IsFirstTimeHome";


    private SharedPrefsUtils(Context context){
        PREFS_NAME = PREFS_NAME + context.getPackageName();
        this.sharedPreferences = context.getSharedPreferences(PREFS_NAME , Context.MODE_PRIVATE);
    }

    //MAIN METHOD
    public static SharedPrefsUtils getInstance(){
        if (sharedPrefsUtils == null){
            sharedPrefsUtils = new SharedPrefsUtils(MyApp.getContext());
        }
        return sharedPrefsUtils;
    }

    public void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_NAME, username);
        editor.apply();
    }
    public String getUsername(){
        return sharedPreferences.getString(USER_NAME, "");
    }

    public void saveAccessToken(String accessToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ACCESS_TOKEN, accessToken);
        editor.apply();
    }
    public void removeAccessToken() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(ACCESS_TOKEN);
        editor.apply();
    }
    public String getAccessToken(){
        return sharedPreferences.getString(ACCESS_TOKEN, "");
    }

    public void saveDeviceToken(String deviceToken) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DEVICE_TOKEN, deviceToken);
        editor.apply();
    }
    public String getDeviceToken(){
        return sharedPreferences.getString(DEVICE_TOKEN, "");
    }

    public void saveUserId (int userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(USER_ID, userId);
        editor.apply();
    }

    public int getUserId(){
        return sharedPreferences.getInt(USER_ID, -1);
    }

    public void saveUserObject(UserModel userModel){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_OBJECT, new Gson().toJson(userModel));
        editor.apply();
    }

    public UserModel getUserObject(){
        String objStr = sharedPreferences.getString(USER_OBJECT, "");
        if(!objStr.matches("")){
            return new Gson().fromJson(objStr, UserModel.class);
        }
        else return null;
    }

    public Boolean isUserLogged(){
        if (!sharedPreferences.getString(ACCESS_TOKEN, "").equals(""))
            return true;
        return false;
    }

    public void clearUser() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(USER_ID);
        editor.remove(ACCESS_TOKEN);
        editor.remove(USER_OBJECT);
        //editor.clear();
        editor.apply();
    }


    public void saveMyString(String key , String val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key , val);
        editor.apply();
    }

    public String getMyString(String key, String defVal){
        return sharedPreferences.getString(key, defVal);
    }

    public String getMyString(String key){
        return sharedPreferences.getString(key, "");
    }


    public void removeMyString(String key){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public void saveMyInt(String key , int val){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key , val);
        editor.apply();
    }

    public int getMyInt(String key, int defVal){
        return sharedPreferences.getInt(key, defVal);
    }


    public void removeAllSharedValues(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public void saveObject(String key, Object mObject){
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(mObject);
        prefsEditor.putString("MyObject", json);
        prefsEditor.apply();
    }

    public Object getMyObject(String key){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(key, "");
        return gson.fromJson(json, Object.class);
    }



    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }





    public void setFirstTimeHome(boolean isFirstTime) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(IS_FIRST_TIME_HOME, isFirstTime);
        editor.apply();
    }

    public boolean isFirstTimeHome() {
        return sharedPreferences.getBoolean(IS_FIRST_TIME_HOME, true);
    }

}
