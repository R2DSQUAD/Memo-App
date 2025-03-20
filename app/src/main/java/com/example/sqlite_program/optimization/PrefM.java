package com.example.sqlite_program.optimization;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

public class PrefM {

    public static final String PREFERENCES_NAME="memo";

    public static SharedPreferences getPrivatePref(Context context){
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferences getDefaultPref(Context context){
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setString(SharedPreferences prefs, String key, String value) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static void setBoolean(SharedPreferences prefs, String key, boolean value) {

        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(key, value);
        editor.apply();

    }

    public static String getString(SharedPreferences prefs, String key, String defaultValue) {
       return prefs.getString(key, defaultValue);
    }

    public static boolean getBoolean(SharedPreferences prefs, String key, boolean defaultValue) {
        return prefs.getBoolean(key, defaultValue);
    }

}
