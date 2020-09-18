package com.example.common.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.common.dao.NewsDao;
import com.example.common.dao.NewsDatabeans;
import com.example.common.dao.NewsRoomBean;

import java.util.List;

public class CacheManager {
    private static CacheManager cacheManager;
    private SharedPreferences twoGroup;
    private NewsDao newsDao;
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
        newsDao = NewsDatabeans.getInstance(context).getNewsDao();
    }

    public synchronized void putFirstTime(String key,long firstTime){
        edit.putLong(key, firstTime);
        edit.commit();
    }
    public synchronized long getFirstTime(String key,long time){
        long firstTime = twoGroup.getLong(key, time);
        return firstTime;
    }
    public synchronized void insert(NewsRoomBean newsRoomBean){
        newsDao.insertAll(newsRoomBean);
    }

    public synchronized List<NewsRoomBean> query(){
        List<NewsRoomBean> all = newsDao.getAll();
        return all;
    }

    public synchronized NewsRoomBean queryId(int id){
        return newsDao.getNewsBean(id);
    }

    public void delet(NewsRoomBean newsRoomBean){
        newsDao.delete(newsRoomBean);
    }
}
