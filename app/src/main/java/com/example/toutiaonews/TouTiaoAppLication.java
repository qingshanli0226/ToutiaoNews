package com.example.toutiaonews;

import android.app.Application;
import android.content.SharedPreferences;

public class TouTiaoAppLication extends Application {
   public SharedPreferences sp ;
   public boolean tofLogin;
    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("lo", 0);
        tofLogin=false;
    }
}
