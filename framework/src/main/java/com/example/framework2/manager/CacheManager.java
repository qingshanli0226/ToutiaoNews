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


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class CacheManager {
    private CacheManager() {
    }

    private LoginBean loginBean;

    private SharedPreferences sharedPreferences;
    private boolean isGeted=false;
    private List<Activity> activityList=new ArrayList<>();
    private static CacheManager instance;
    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }

        return instance;
    }
    private IndexBean indexBean;
    private UpdateBean updateBean;
    private InvestBean investBean;

    public LoginBean getLoginBean() {
        return loginBean;
    }
    public void clearLogin(){
        this.loginBean=null;
        sharedPreferences.edit().putString(NetCommon.TOKEN,null);
    }
    public void setLoginBean(LoginBean loginBean) {
        this.loginBean = loginBean;
    }

    public void init(Context context){
        sharedPreferences = context.getSharedPreferences(NetCommon.SP, Context.MODE_PRIVATE);
        Intent intent = new Intent(context, MyService.class);
        ServiceConnection serviceConnection = new ServiceConnection() {

            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyService.MyBinder myBinder=(MyService.MyBinder)service;
                if (!TextUtils.isEmpty(getToken())){
                    myBinder.getService().autoLogin(getToken());
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    private String getToken() {
        return sharedPreferences.getString(NetCommon.SP,null);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public boolean isGeted() {
        return isGeted;
    }

    public void setGeted(boolean geted) {
        isGeted = geted;
    }

    public InvestBean getInvestBean() {
        return investBean;
    }

    public void setInvestBean(InvestBean investBean) {
        this.investBean = investBean;
    }

    public IndexBean getIndexBean() {
        return indexBean;
    }

    public void setIndexBean(IndexBean indexBean) {
        this.indexBean = indexBean;
    }
    public String getLoginName(){
        if (loginBean!=null){
            return loginBean.getResult().getName();
        }else {
            return null;
        }
    }
    public UpdateBean getUpdateBean() {
        return updateBean;
    }

    public void setUpdateBean(UpdateBean updateBean) {
        this.updateBean = updateBean;
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
    public void notifyLoginState(LoginBean loginBean,LoginINCallback loginINCallback){
        if (loginINCallback!=null){
            loginINCallback.OnLoginNotifyCallback(loginBean);
        }
    }
    public interface LoginINCallback{
        void OnLoginNotifyCallback(LoginBean loginBean);
    }
    //通过图片的二次采样的方法
    public Bitmap samplePic(ImageView imageView, Bitmap bitmap){

        int picWidth = bitmap.getWidth();
        int picHeight = bitmap.getHeight();
        //计算出缩放比例
        int sampleSize = 1;
        while (picHeight/sampleSize>imageView.getHeight() || picWidth/sampleSize > imageView.getWidth()) {
            sampleSize = sampleSize*2;
        }
        //第一次采样结束

        //第二次采样，就是按照这个比例采集像素
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;//不是采集边框，而是按比例采集像素
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        //将bitmap转换成byte[]
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        Bitmap samPlaceBitmap =  BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        return samPlaceBitmap;
    }
    //通过地址的二次采样
    public Bitmap samplePicPath(int width, int height, String filePath) {

        //第一次采样，主要采集图片边框，算出图片的尺寸
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;//通过该标志位，确定第一次采样只采集边框
        BitmapFactory.decodeFile(filePath,options);
        //计算出图片的宽度和高度
        int picWidth = options.outWidth;
        int picHeight = options.outHeight;
        //计算出缩放比例
        int sampleSize = 1;
        while (picHeight/sampleSize>height || picWidth/sampleSize > width) {
            sampleSize = sampleSize*2;
        }
        //第一次采样结束

        //第二次采样，就是按照这个比例采集像素
        options.inJustDecodeBounds = false;//不是采集边框，而是按比例采集像素
        options.inSampleSize = sampleSize;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;

        return BitmapFactory.decodeFile(filePath, options);

    }
}
