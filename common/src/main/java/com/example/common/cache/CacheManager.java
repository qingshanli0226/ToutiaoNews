package com.example.common.cache;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheManager {
    private static CacheManager cacheManager;
    private SharedPreferences twoGroup;
    private SharedPreferences.Editor edit;
    public static CacheManager getInstance(){
        if(cacheManager == null){
            synchronized (CacheManager.class){
                if(cacheManager == null){
                    cacheManager = new CacheManager();
                }
            }
        }
        return cacheManager;
    }

    public void init(Context context){
        twoGroup = context.getSharedPreferences("TwoGroup", Context.MODE_PRIVATE);
        edit = twoGroup.edit();
    }

    public void onFristTime(String firstTime){
        edit.putString("firstTime", firstTime);
        edit.commit();
    }
    public String getFristTime(){
        String firstTime = twoGroup.getString("firstTime", "");
        return firstTime;
    }
}
