package com.cubes.komentar.pavlovic.ui;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefs {

    private final int VOTE = 1;

    private static SharedPrefs instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private SharedPrefs(Activity activity) {
        preferences = activity.getPreferences(Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SharedPrefs getInstance(Activity activity) {

        if (instance == null) {
            instance = new SharedPrefs(activity);
        }

        return instance;
    }

    public void saveLike(int vote) {
        editor.putInt(String.valueOf(VOTE), vote);
        editor.apply();
    }

    public int vote() {
        return preferences.getInt(String.valueOf(VOTE), 1);
    }

}
