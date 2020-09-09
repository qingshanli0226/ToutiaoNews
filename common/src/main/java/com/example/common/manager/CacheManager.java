package com.example.common.manager;

import android.content.Context;
import android.content.SharedPreferences;

public class CacheManager {
    private SharedPreferences sharedPreferences;//向SP取数据
    private SharedPreferences.Editor editor;//向SP存数据

    private static CacheManager cacheManager;

    private CacheManager() {
    }

    public static CacheManager getCacheManager() {
        if (cacheManager == null) {
            cacheManager = new CacheManager();
        }
        return cacheManager;
    }

    public void init(Context context) {
        sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    //提供字符串存储
    public synchronized void setSPOfString(String paramString, String messageString) {
        editor.putString(paramString, messageString);
        editor.commit();
    }

    //获取存储字符串值
    public synchronized String getSPOfString(String paramString) {
        return sharedPreferences.getString(paramString, "");
    }

    //获取存储布尔值
    public synchronized boolean getSPOfBoolean(String paramString) {
        return sharedPreferences.getBoolean(paramString, false);
    }

    //提供布尔存储
    public synchronized void setSPOfBoolean(String paramString, boolean messageBoolean) {
        editor.putBoolean(paramString, messageBoolean);
        editor.commit();
    }
}
