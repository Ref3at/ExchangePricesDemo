package com.refaat.exchangepricesdemobyrefaat;

import android.app.Application;

import com.refaat.exchangepricesdemobyrefaat.utils.PreferencesManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        PreferencesManager.initializeInstance(getApplicationContext());

    }
}
