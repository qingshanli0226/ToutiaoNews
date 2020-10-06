package com.example.common.cache;

import android.content.Context;
import android.content.SharedPreferences;
<<<<<<< HEAD
import android.util.Log;
=======
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;
>>>>>>> 网络判断

import com.example.common.dao.NewsDao;
import com.example.common.dao.NewsDatabeans;
import com.example.common.dao.NewsRoomBean;

import java.util.List;

public class CacheManager {
    private static CacheManager cacheManager;
    private SharedPreferences twoGroup;
    private NewsDao newsDao;
    private SharedPreferences.Editor edit;

    public static CacheManager getInstance() {
        if (cacheManager == null) {
            synchronized (CacheManager.class) {
                if (cacheManager == null) {
                    cacheManager = new CacheManager();
                }
            }
        }
        return cacheManager;
    }

    public void init(Context context) {
        twoGroup = context.getSharedPreferences("TwoGroup", Context.MODE_PRIVATE);
        edit = twoGroup.edit();
        newsDao = NewsDatabeans.getInstance(context).getNewsDao();
    }

    public synchronized void putFirstTime(String key, long firstTime) {
        edit.putLong(key, firstTime);
        edit.commit();
    }

    public synchronized long getFirstTime(String key, long time) {
        long firstTime = twoGroup.getLong(key, time);
        return firstTime;
    }

<<<<<<< HEAD
    public synchronized void putisVisit(String key,boolean isVisit){
        edit.putBoolean(key, isVisit);
        edit.commit();
    }
    public boolean getisVisit(String key,boolean isVisit){
        boolean visitTime = twoGroup.getBoolean(key, isVisit);
        return visitTime;
    }


    public synchronized void putVisitTime(String key,long visitTime){
=======
    public synchronized void putVisitTime(String key, long visitTime) {
>>>>>>> 网络判断
        edit.putLong(key, visitTime);
        edit.commit();
    }

    public synchronized long getVisitTime(String key, long time) {
        long visitTime = twoGroup.getLong(key, time);
        return visitTime;
    }


    public synchronized void insert(NewsRoomBean newsRoomBean) {
        newsDao.insertNews(newsRoomBean);
    }

    public synchronized List<NewsRoomBean> query() {
        List<NewsRoomBean> all = newsDao.getAll();
        return all;
    }

    public synchronized NewsRoomBean queryChannel(String channel) {
        return newsDao.getNewsBean(channel);
    }

    public void delet(NewsRoomBean newsRoomBean) {
        newsDao.delete(newsRoomBean);
    }

<<<<<<< HEAD
    public void deletTime(long newsTime){
        newsDao.deleteNewsBean(newsTime);
=======
    public boolean isConnect(Context context) {
        //普通网络判断
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) { //有网返回true
            return true;
        } else {   //没有网返回false
            return false;
        }
>>>>>>> 网络判断
    }
}
