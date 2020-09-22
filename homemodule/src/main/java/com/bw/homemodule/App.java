package com.bw.homemodule;

import android.app.Application;

import com.example.common.cache.CacheManager;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //sp储存时间戳
        CacheManager.getInstance().init(this);
    }
}
