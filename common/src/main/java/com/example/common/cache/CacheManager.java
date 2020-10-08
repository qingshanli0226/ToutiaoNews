package com.example.common.cache;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.common.dao.NewsDao;
import com.example.common.dao.NewsDatabeans;
import com.example.common.dao.NewsRoomBean;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CacheManager {
    private static CacheManager cacheManager;
    private SharedPreferences twoGroup;
    private NewsDao newsDao;
    private SharedPreferences.Editor edit;
    private Context mContext;
    private ExecutorService executorService = Executors.newCachedThreadPool();

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
        mContext = context;
        twoGroup = context.getSharedPreferences("TwoGroup", Context.MODE_PRIVATE);
        edit = twoGroup.edit();
        newsDao = NewsDatabeans.getInstance(context).getNewsDao();
    }

    public synchronized boolean getisVisit(String key, boolean isVisit) {
        boolean IsVisit = twoGroup.getBoolean(key, isVisit);
        return IsVisit;
    }

    public synchronized void putisVisit(String key, boolean isVisit) {
        edit.putBoolean(key, isVisit);
        edit.commit();
    }

    public synchronized long getVisitTime(String key, long VisitTime) {
        long visitTime = twoGroup.getLong(key, VisitTime);
        return visitTime;
    }

    public synchronized void putVisitTime(String key, long VisitTime) {
        edit.putLong(key, VisitTime);
        edit.commit();
    }

    public synchronized void putFirstTime(String key, long firstTime) {
        edit.putLong(key, firstTime);
        edit.commit();
    }

    public synchronized long getFirstTime(String key, long time) {
        long firstTime = twoGroup.getLong(key, time);
        return firstTime;
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

    public void deletTime(long newsTime) {
        newsDao.deleteNewsBean(newsTime);
    }

    public boolean isConnect() {
        //普通网络判断
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null) { //有网返回true
            return true;
        } else {   //没有网返回false
            return false;
        }
    }

    public ExecutorService getExecutor(){
        return executorService;
    }

}
