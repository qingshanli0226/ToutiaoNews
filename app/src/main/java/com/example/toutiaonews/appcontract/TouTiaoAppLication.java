package com.example.toutiaonews.appcontract;

import android.app.Application;
import android.content.SharedPreferences;

import com.chad.library.adapter.base.util.TouchEventUtil;
import com.example.common.cache.CacheManager;

public class TouTiaoAppLication extends Application {
   public SharedPreferences sp ;
   public boolean tofLogin;
   private static TouchEventUtil touchEventUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance().init(this);

        sp = getSharedPreferences("lo", 0);
        tofLogin=false;
    }
}
