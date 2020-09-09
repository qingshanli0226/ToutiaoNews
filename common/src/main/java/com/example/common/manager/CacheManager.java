package com.example.common.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.dao.NetWorkDataEntity;
import com.example.common.dao.NetWorkDatabase;

import java.util.List;

public class CacheManager {
    private SharedPreferences sharedPreferences;//向SP取数据
    private SharedPreferences.Editor editor;//向SP存数据
    private NetWorkDatabase netWorkDatabase;//Room数据库

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
        netWorkDatabase = NetWorkDatabase.getInstance(context);
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

    //查询所有
    public List<NetWorkDataEntity> getAllData() {
        return netWorkDatabase.getNetWorkDataDao().getAllData();
    }

    //查询图片地址---返回图片地址
    public NetWorkDataEntity getImgUrl(String imgUrl) {
        return netWorkDatabase.getNetWorkDataDao().getImgUrl(imgUrl);
    }

    //查询网页地址---返回网页地址
    public NetWorkDataEntity getWebUrl(String webUrl) {
        return netWorkDatabase.getNetWorkDataDao().getWebUrl(webUrl);
    }

    //查询视频地址---返回视频地址
    public NetWorkDataEntity getVideoUrl(String videoUrl) {
        return netWorkDatabase.getNetWorkDataDao().getVideoUrl(videoUrl);
    }

    //查询Json地址---返回Json地址
    public NetWorkDataEntity getJsonUrl(String jsonUrl) {
        return netWorkDatabase.getNetWorkDataDao().getJsonUrl(jsonUrl);
    }

    //添加数据
    public void insert(NetWorkDataEntity netWorkDataEntities) {
        netWorkDatabase.getNetWorkDataDao().inster(netWorkDataEntities);
    }

    //添加对象集合
    public void insertList(List<NetWorkDataEntity> netWorkDataEntities) {
        netWorkDatabase.getNetWorkDataDao().insertList(netWorkDataEntities);
    }

    //更新某一项
    public void update(NetWorkDataEntity netWorkDataEntities) {
        netWorkDatabase.getNetWorkDataDao().update(netWorkDataEntities);
    }

    //删除某一项
    public void delete(NetWorkDataEntity netWorkDataEntities) {
        netWorkDatabase.getNetWorkDataDao().delete(netWorkDataEntities);
    }

    //删除全部
    public void deleteAll() {
        netWorkDatabase.getNetWorkDataDao().deleteAll();
    }

}
