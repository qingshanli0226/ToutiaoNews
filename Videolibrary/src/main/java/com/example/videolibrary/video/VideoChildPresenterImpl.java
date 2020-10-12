package com.example.videolibrary.video;

import android.util.Log;

import com.blankj.utilcode.util.SPUtils;
import com.example.net.activity_bean.VideoBean;
import com.example.net.http.HttpManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoChildPresenterImpl extends VideoChildContract.IVideoChildPresenter {

    @Override
    public void getVideoChildData(String category) {

        long firstTime = SPUtils.getInstance().getLong("firstTime",System.currentTimeMillis() / 1000);

        HttpManager.getHttpManager().getRetrofit()
                .getVideoList(category, firstTime, System.currentTimeMillis() / 1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i("liuxuan", "onSubscribe: "+d.toString());
                    }

                    @Override
                    public void onNext(VideoBean videoBean) {

                        SPUtils.getInstance().put("firstTime", System.currentTimeMillis() / 1000);
                        iHttpView.onVideoChildData(videoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("liuxuan", "onSubscribe: "+e.toString());
                        Log.i("liuxuan", "onSubscribe: "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
