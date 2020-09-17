package com.example.toutiaonews;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;

import com.example.common.CacheManager;
import com.example.framework2.manager.UserManager;

public class MyApp extends Application {

    //以下属性应用于整个应用程序，合理利用资源，减少资源浪费
    private static Context mContext;//上下文
    private static Thread mMainThread;//主线程
    private static long mMainThreadId;//主线程id
    private static Looper mMainLooper;//循环队列
    private static Handler mHandler;//主线程Handler

    private static Resources sRes;

    @Override
    public void onCreate() {
        super.onCreate();
        //对全局属性赋值
        mContext = getApplicationContext();
        mMainThread = Thread.currentThread();
        mMainThreadId = android.os.Process.myTid();
        mHandler = new Handler();

        /*--------------注册-------------*/
        CacheManager.getCacheManager().init(this);//数据缓存注册
        UserManager.getInstance().init(this);

    }

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context mContext) {
        MyApp.mContext = mContext;
    }

    public static Thread getMainThread() {
        return mMainThread;
    }

    public static void setMainThread(Thread mMainThread) {
        MyApp.mMainThread = mMainThread;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        MyApp.mMainThreadId = mMainThreadId;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }

    public static void getMainHandler(Handler mHandler) {
        MyApp.mHandler = mHandler;
    }
}
