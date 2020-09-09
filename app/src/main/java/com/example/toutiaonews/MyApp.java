package com.example.toutiaonews;

import android.app.Application;

import com.example.common.manager.CacheManager;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        /*--------------注册-------------*/
        CacheManager.getCacheManager().init(this);//数据缓存注册

    }
}
