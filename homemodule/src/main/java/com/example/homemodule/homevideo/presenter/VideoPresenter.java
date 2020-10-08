package com.example.homemodule.homevideo.presenter;

import com.example.common.cache.CacheManager;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.homemodule.homevideo.contract.VideoContract;
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
    public void getVideoData(final String category, long lastTime) {
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
                        videoDataBeans.clear();
                        for (int i = 0; i < data.size(); i++) {
                            VideoBean.DataBean dataBean = data.get(i);
                            VideoDataBean videoDataBean = new Gson().fromJson(dataBean.getContent(), VideoDataBean.class);
                            videoDataBeans.add(videoDataBean);
                        }

                        if (videoDataBeans.size()!=0){
                            iHttpView.onVideoData(videoDataBeans);
                        }else {
                            iHttpView.showError("100","没有更多的数据了。。");
                        }

                        //更改刷新时间
                        CacheManager.getInstance().putFirstTime(category,System.currentTimeMillis());

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
