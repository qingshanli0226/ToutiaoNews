package com.example.framework2.manager;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.text.TextUtils;
import android.widget.ImageView;

import com.example.common.NetCommon;
import com.example.framework2.MyService;
import com.example.net.activity_bean.NewsListBean;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CacheManager {
    private CacheManager() {
    }


    private NewsListBean newsListBean;
    private SharedPreferences sharedPreferences;
    private List<Activity> activityList=new ArrayList<>();
    private static CacheManager instance;
    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }

        return instance;
    }

    public NewsListBean getNewsListBean() {
        return newsListBean;
    }

    public void setNewsListBean(NewsListBean newsListBean) {
        this.newsListBean = newsListBean;
    }

    public void init(Context context){
        sharedPreferences = context.getSharedPreferences(NetCommon.SP_NAME, Context.MODE_PRIVATE);
        Intent intent = new Intent(context, MyService.class);
        ServiceConnection serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder myBinder=(MyService.MyBinder)service;
//                if (!TextUtils.isEmpty(getToken())){
//                    myBinder.getService().autoLogin(getToken());
//                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
    }


    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public List<Activity> getActivityList() {
        return activityList;
    }



}
