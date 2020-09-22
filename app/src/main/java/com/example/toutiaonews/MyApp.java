package com.example.toutiaonews;

import android.app.Application;

import com.example.framework2.manager.CacheManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance().init(this);
    }
}
