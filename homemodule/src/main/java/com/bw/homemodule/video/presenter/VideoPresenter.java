package com.bw.homemodule.video.presenter;

import com.bw.homemodule.video.contract.VideoContract;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.farmework.base.BasePresenter;
import com.example.net.retrofit.RetrofitManager;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class VideoPresenter extends VideoContract.VideoPresenter {
    @Override
    public void getVideoData(String category, long lastTime) {
        RetrofitManager.getNewsApi()
                .getVideoList(category,lastTime,System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<VideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoBean videoBean) {
                        List<VideoBean.DataBean> data = videoBean.getData();

                        ArrayList<VideoDataBean> videoDataBeans =new ArrayList<>();
                        for (int i = 0; i < data.size(); i++) {
                            VideoBean.DataBean dataBean = data.get(i);
                            VideoDataBean videoDataBean = new Gson().fromJson(dataBean.getContent(), VideoDataBean.class);
                            videoDataBeans.add(videoDataBean);
                        }

                        iHttpView.onVideoData(videoDataBeans);
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
