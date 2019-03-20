package com.studentapp;

import android.app.Application;

import com.google.firebase.firestore.FirebaseFirestore;
import com.studentapp.utils.Utils;

import androidx.multidex.MultiDexApplication;


public class App extends MultiDexApplication {
    private static final String TAG = "App";
    public static FirebaseFirestore mFirestore;
    private static App mInstance = null;
    private Utils utils;

    // Getter to access Singleton instance
    public static App getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        utils = new Utils(this);
        initFirestore();
    }

    private void initFirestore() {
        mFirestore = FirebaseFirestore.getInstance();
    }
}
