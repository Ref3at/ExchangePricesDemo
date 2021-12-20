package com.refaat.exchangepricesdemobyrefaat.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.refaat.exchangepricesdemobyrefaat.BuildConfig;

public class PreferencesManager {

    private static PreferencesManager sInstance;
    private final SharedPreferences mPref;
    private static final String PREF_NAME = "PREFS_OF_" + BuildConfig.APPLICATION_ID;
    private static final String SELECTED_INTERVAL = "selected_interval";


    private PreferencesManager(Context context) {
        mPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (sInstance == null) {
            sInstance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (sInstance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return sInstance;
    }


    public void saveTheSelectedInterval(int selectedInterval) {
        mPref.edit()
                .putInt(SELECTED_INTERVAL, selectedInterval)
                .apply();

    }

    public int getTheSelectedInterval() {
        return mPref.getInt(SELECTED_INTERVAL, 30);
    }
//    public int getTheSelectedIntervalInMilliSecond() {
//        return mPref.getInt(SELECTED_INTERVAL, 30) *1000;
//    }


    public boolean clearPreferences() {
        return mPref.edit()
                .clear()
                .commit();
    }

}
