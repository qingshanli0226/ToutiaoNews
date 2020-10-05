package com.example.videomodule.video.presenter;

import android.os.Message;
import android.util.Log;

import com.example.common.cache.CacheManager;
import com.example.common.dao.NewsDatabeans;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.Video;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.common.entity.VideoModel;
import com.example.common.response.NewsResponse;
import com.example.net.obserable.BaseObserable;
import com.example.net.retrofit.RetrofitManager;
import com.example.videomodule.video.contract.VideoContract;
import com.google.gson.Gson;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class VideoPresenterImpl extends VideoContract.VideoPresenter {
    private long firstTime;
    @Override
    public void getVideoData(String category,String channel) {
        firstTime = CacheManager.getInstance().getFirstTime("first",0);
        if(firstTime != 0){
            firstTime = System.currentTimeMillis() / 1000;
        }
        CacheManager.getInstance().putVisitTime(category, System.currentTimeMillis());
        RetrofitManager.getNewsApi().getVideoList(category,firstTime,System.currentTimeMillis()/1000)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserable<VideoBean>() {
                    @Override
                    public void onNext(VideoBean videoBean) {
                        firstTime = System.currentTimeMillis() / 1000;
                        CacheManager.getInstance().putFirstTime("first",firstTime);
                        if(videoBean != null){
                            CacheManager.getInstance().putisVisit(channel, true);
                            iHttpView.onVideoData(videoBean);
                        }else{
                            iHttpView.showError("", "请求失败");
                        }
                    }

                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        iHttpView.showError(errorCode, "请求失败,请重试");
                    }
                });
    }
}