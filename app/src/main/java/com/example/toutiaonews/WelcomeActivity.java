package com.example.toutiaonews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.framework2.manager.CacheManager;
import com.example.net.activity_bean.NewsListBean;
import com.example.net.http.HttpManager;

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
    }

    private void initData() {
        //请求数据
        HttpManager.getHttpManager()
                .getRetrofit()
                .getNewsList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsListBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsListBean newsListBean) {
                        if (newsListBean!=null&&newsListBean.getMessage().equals("success")){
                            CacheManager.getInstance().setNewsListBean(newsListBean);
                            Log.e("fff", "onNext: "+newsListBean.getData().size() );
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
