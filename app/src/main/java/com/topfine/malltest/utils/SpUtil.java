package com.topfine.malltest.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Lcs on 2016/8/16.
 */
public class SpUtil {
    public static final String SP_NAME = "top_fineshop";
    public static final String TOKEN = "token";
    public static final String MOBILE = "mobile";
    public static final String USERNAME = "USERNAME";


    public static SharedPreferences sharedPreferences;



    public static SharedPreferences getSP(Context context) {
        if (sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences(SP_NAME, context.MODE_PRIVATE);
        }
        return sharedPreferences;
    }


    public static void setData(Context context, String key, Boolean value) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putBoolean(key, value).apply();
    }

    public static void setToken(Context context, String token) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putString(TOKEN, token).apply();
    }

    public static void setMobile(Context context, String mobile) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putString(MOBILE, mobile).apply();
    }

    public static void setUserName(Context context, String name) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putString(USERNAME, name).apply();
    }

    public static String getToken(Context context) {
        return getString(context, TOKEN, "");
    }

    public static String getMobile(Context context) {
        return getString(context, MOBILE, "");
    }

    public static String getUserName(Context context) {
        return getString(context, USERNAME, "");

    }

    public static void removeToken(Context context) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().remove(TOKEN).commit();
    }

    public static void removeMobile(Context context) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().remove(MOBILE).commit();
    }

    public static void setString(Context context, String key, String value) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String key, String defaultvalue) {
        sharedPreferences = getSP(context);
        return sharedPreferences.getString(key, defaultvalue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultvalue) {
        sharedPreferences = getSP(context);
        return sharedPreferences.getBoolean(key, defaultvalue);
    }

    /**
     * @return long
     * @Description
     */
    public static long getLong(Context context, String key, long defValue) {
        sharedPreferences = getSP(context);
        return sharedPreferences.getLong(key, defValue);
    }

    /**
     * @return void
     * @Description
     */
    public static void setLong(Context context, String key, long value) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putLong(key, value).commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        sharedPreferences = getSP(context);
        return sharedPreferences.getInt(key, defValue);
    }

    /**
     * @return void
     * @Description
     */
    public static void setInt(Context context, String key, int value) {
        sharedPreferences = getSP(context);
        sharedPreferences.edit().putInt(key, value).commit();
    }



}

