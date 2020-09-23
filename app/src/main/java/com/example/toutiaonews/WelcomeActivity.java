package com.example.toutiaonews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.framework2.manager.CacheManager;
<<<<<<< HEAD
import com.example.net.activity_bean.NewsListBean;
import com.example.net.activity_bean.response.NewsResponse;
=======
>>>>>>> 8d1dbcd... 数据+首页适配+多布局
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
<<<<<<< HEAD

//        long l = System.currentTimeMillis()/1000;
//
//        //请求数据
//        HttpManager.getHttpManager()
//                .getRetrofit()
//                .getNewsList("video", 1261235165, l)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NewsResponse>() {
=======
        HttpManager.getHttpManager().setPath("http://is.snssdk.com/");
        //请求数据
//        HttpManager.getHttpManager()
//                .getRetrofit()
//                .getNewsList()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<NewsListBean>() {
>>>>>>> 8d1dbcd... 数据+首页适配+多布局
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
<<<<<<< HEAD
//                    public void onNext(NewsResponse newsResponse) {
//                        long a = System.currentTimeMillis()/1000;
//
//                        Log.i("WelcomeActivity  onNext", "onNext: " + newsResponse.data.get(0).content);
=======
//                    public void onNext(NewsListBean newsListBean) {
//                        if (newsListBean!=null&&newsListBean.getMessage().equals("success")){
//                            CacheManager.getInstance().setNewsListBean(newsListBean);
//                            Log.e("fff", "onNext: "+newsListBean.getData().size() );
//                        }
>>>>>>> 8d1dbcd... 数据+首页适配+多布局
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
