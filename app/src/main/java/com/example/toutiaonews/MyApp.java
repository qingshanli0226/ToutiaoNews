package com.example.toutiaonews;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;

import androidx.appcompat.app.AppCompatDelegate;

import com.example.common.manager.CacheManager;

public class MyApp extends Application {


    private static Resources sRes;

    @Override
    public void onCreate() {
        super.onCreate();
        /*--------------注册-------------*/
        CacheManager.getCacheManager().init(this);//数据缓存注册

    }

}
