package com.example.video.mvp.presenter;

import com.example.common.entity.Video;
import com.example.common.entity.VideoModel;
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
    public void getVideoData(String srcurl) {
        RetrofitManager.getNewsApi().getVideoHtml("https://pv.vlogdownloader.com")
                .flatMap(new Function<String, ObservableSource<VideoModel>>() {
                    @Override
                    public ObservableSource<VideoModel> apply(String s) throws Exception {
                        Pattern compile = Pattern.compile("var hash = \"(.+)\"");
                        Matcher matcher = compile.matcher(s);
                        if(matcher.find()){
                            String group = matcher.group(1);
                            String format = String.format("http://pv.vlogdownloader.com/api.php?url=%s&hash=%s", srcurl, group);
                            return RetrofitManager.getNewsApi().getVideoData(format);
                        }
                        return null;
                    }
                })
                .map(new Function<VideoModel, Video>() {
                    @Override
                    public Video apply(VideoModel videoModel) throws Exception {
                        List<Video> video = videoModel.video;
                        if(video.isEmpty()){
                            return video.get(video.size()-1);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserable<Video>() {
                    @Override
                    public void onNext(Video video) {
                        if(video != null){
                            iHttpView.onVideoData(video);
                        }else{
                            onRequestError("","视频解析失败,请重试");
                        }
                    }

                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        iHttpView.showError(errorCode, "视频解析失败,请重试");
                    }
                });
    }
}