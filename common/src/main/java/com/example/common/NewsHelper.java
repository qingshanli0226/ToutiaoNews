package com.example.common;

import android.content.Context;

import com.example.common.bean.NewsRoomBean;
import com.example.common.dao.NewsDao;
import com.example.common.dao.NewsDatabeans;

import java.util.List;

public class NewsHelper {
    private static NewsHelper newsHelper;
    private NewsDao newsDao;
    public static NewsHelper getInstance(){
        if(newsHelper == null){
            newsHelper = new NewsHelper();
        }
        return newsHelper;
    }

    public void init(Context context) {
        newsDao = NewsDatabeans.getInstance(context).getNewsDao();
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
