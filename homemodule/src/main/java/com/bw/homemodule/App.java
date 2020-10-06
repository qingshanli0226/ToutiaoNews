package com.bw.homemodule;

import android.app.Application;
import android.content.Context;

import com.example.common.cache.CacheManager;

public class App extends Application {

    public static Context app;

    @Override
    public void onCreate() {
        super.onCreate();

        app = this;
        //sp储存时间戳
        CacheManager.getInstance().init(this);
    }
}
