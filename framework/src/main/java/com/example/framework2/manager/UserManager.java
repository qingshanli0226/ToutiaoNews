package com.example.framework2.manager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;

import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.mode.LoginBean;
import com.example.framework2.TouTiaoService;

import java.util.LinkedList;
import java.util.List;

public class UserManager {

    private SharedPreferences sharedPreferences;//向SP取数据
    private SharedPreferences.Editor editor;//向SP存数据

    private LoginBean loginBean;
    //管理页面监听接口登录状态的链表
    private List<ILoginStatusChangeListener> loginStatusChangeListeners = new LinkedList<>();

    private UserManager() {
    }

    private static UserManager userManager;

    public static UserManager getInstance(){
        if(userManager == null){
            synchronized (UserManager.class){
                if(userManager == null){
                    userManager = new UserManager();
                }
            }
        }
        return userManager;
    }

    public void init(Context context){
        sharedPreferences = context.getSharedPreferences("token", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
            //登录过 可以实现自动登录
            //实现自动登录
            Intent intent = new Intent();
            intent.setClass(context, TouTiaoService.class);
            //绑定
            context.bindService(intent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {
                    TouTiaoService.TouTiaoBinder binder = (TouTiaoService.TouTiaoBinder) service;
                    if(getToken() != null){
                        binder.getService().autoLogin("http://49.233.93.155:8080/autoLogin?token="+getToken());
                    }
                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            },Context.BIND_AUTO_CREATE);
    }

    //返回储存的token值
    public String getToken(){
        return getSPOfString(TouTiaoNewsConstant.SP_TOKEN);
    }

    //判断当前用户是否登录
    public boolean isUserLogin(){
        //如果loginBean不为空 则代表登录了
        return loginBean != null;
    }

    //将未登录状态改为已登录状态
    public void setLoginBean(LoginBean loginBean){
        this.loginBean = loginBean;
        //通知用户登录成功
        if(loginStatusChangeListeners.size() > 0){
            for (ILoginStatusChangeListener listener : loginStatusChangeListeners){
                listener.onLoginSuccess(loginBean);
            }
        }

        //将token储存在sp文件中
        setSPOfString(TouTiaoNewsConstant.SP_TOKEN,loginBean.getResult().getToken());
    }

    //退出登录
    public void loginOutSuccess(){
        //内存登录状态变为未登录
        this.loginBean = null;

        //通知页面退出登录
        if(loginStatusChangeListeners.size() > 0){
            for(ILoginStatusChangeListener listener : loginStatusChangeListeners){
                listener.onLoginOutSuccess();
            }
        }
        //改变spToken的储存状态
        setSPOfString(TouTiaoNewsConstant.SP_TOKEN,"");
    }

    //返回登录的用户名
    public String getUserName(){
        return loginBean.getResult().getName();
    }

    //定义一个接口
    public interface ILoginStatusChangeListener{
        void onLoginSuccess(LoginBean loginBean);
        void onLoginOutSuccess();
    }

    public void setLoginStatusChangeListener(ILoginStatusChangeListener listener) {
        if (!loginStatusChangeListeners.contains(listener)) {
            loginStatusChangeListeners.add(listener);
        }
    }

    public void removeLoginStatusChangeListener(ILoginStatusChangeListener listener) {
        if (loginStatusChangeListeners.contains(listener)) {
            loginStatusChangeListeners.remove(listener);
        }
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
