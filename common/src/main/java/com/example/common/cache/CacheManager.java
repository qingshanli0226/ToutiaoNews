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

    public synchronized void putFirstTime(String key,long firstTime){
        edit.putLong(key, firstTime);
        edit.commit();
    }
    public synchronized long getFirstTime(String key,long time){
        long firstTime = twoGroup.getLong(key, time);
        return firstTime;
    }
}
