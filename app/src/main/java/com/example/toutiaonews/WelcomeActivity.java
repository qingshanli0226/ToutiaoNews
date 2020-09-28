package com.example.toutiaonews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.common.NetCommon;
import com.example.framework2.manager.CacheManager;
import com.example.net.activity_bean.NewsListBean;
import com.example.net.activity_bean.response.NewsResponse;
import com.example.net.http.HttpManager;
import com.example.toutiaonews.fragment.MeFragment;

import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WelcomeActivity extends AppCompatActivity {

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
        
        //sp读取
        if (NetCommon.NEW_ISLOGIN){
            SharedPreferences sp = getSharedPreferences("username", 0);
            String name = sp.getString("name", null);
            boolean islogin = sp.getBoolean("islogin", false);
            MeFragment.myName.setText(name);
            Log.e("sp",name+"----------------"+islogin);
        }else {
            Toast.makeText(this, "没有存储", Toast.LENGTH_SHORT).show();
        }
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
