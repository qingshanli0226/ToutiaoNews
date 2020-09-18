package com.example.video.app;

import android.app.Application;

import com.example.common.cache.CacheManager;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance().init(this);
    }
}
