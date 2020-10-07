package com.example.toutiaonews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.common.NetCommon;
import com.example.net.http.HttpManager;
import com.example.toutiaonews.service.MyLoginService;
import java.util.Timer;
import java.util.TimerTask;
public class WelcomeActivity extends AppCompatActivity {
    private String token;
    @Override
    protected void onStart() {
        super.onStart();
        //sp读取
        boolean isLogin = SPUtils.getInstance().getBoolean("islogin", false);
        if (isLogin){
            token = SPUtils.getInstance().getString("token", null);
        }else {
            Toast.makeText(this, "没有登录记录", Toast.LENGTH_SHORT).show();
        }
        initService(this,token);

    }
    //自动登录的服务
    private void initService(Context context,String token) {
        Intent intent = new Intent();
        intent.setClass(context, MyLoginService.class);
        startService(intent);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                MyLoginService.MyLoginBinder myLoginBinder= (MyLoginService.MyLoginBinder) service;
                myLoginBinder.getService().autoLogin(token);
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        },Context.BIND_AUTO_CREATE);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        initData();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                finish();
                timer.cancel();
            }
        },3000);
        

    }

    private void initData() {

//        long l = System.currentTimeMillis()/1000;
//
//        //请求数据
//        HttpManager.getHttpManager()
//                .getRetrofit()
//                .getNewsList("video", 1261235165, l)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NewsResponse>() {
        HttpManager.getHttpManager().setPath("http://is.snssdk.com/");
        //请求数据
//        HttpManager.getHttpManager()
//                .getRetrofit()
//                .getNewsList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NewsListBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override

//                    public void onNext(NewsResponse newsResponse) {
//                        long a = System.currentTimeMillis()/1000;
//
//                        Log.i("WelcomeActivity  onNext", "onNext: " + newsResponse.data.get(0).content);

//                    public void onNext(NewsListBean newsListBean) {
//                        if (newsListBean!=null&&newsListBean.getMessage().equals("success")){
//                            CacheManager.getInstance().setNewsListBean(newsListBean);
//                            Log.e("fff", "onNext: "+newsListBean.getData().size() );
//                        }

//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }
}
