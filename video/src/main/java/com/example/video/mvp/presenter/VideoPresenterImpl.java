package com.example.video.mvp.presenter;

import android.util.Log;

import com.example.common.entity.Video;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoModel;
import com.example.common.response.NewsResponse;
import com.example.net.obserable.BaseObserable;
import com.example.net.retrofit.RetrofitManager;
import com.example.video.mvp.contract.VideoContract;

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

    @Override
    public void getVideoData(String category,long lastTime) {
        RetrofitManager.getNewsApi().getNewsList(category, Long.parseLong("160013736432"),lastTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserable<VideoBean>() {
                    @Override
                    public void onNext(VideoBean videoBean) {
                        Log.d("---", "1111");
                        if(videoBean != null){
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