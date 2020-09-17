package com.example.video;

import android.view.View;

import com.example.common.entity.Video;
import com.example.common.entity.VideoModel;
import com.example.net.retrofit.RetrofitManager;
import com.example.toutiaonews.R;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public abstract class VideoPathDecoder {
    public static final String TAG = VideoPathDecoder.class.getSimpleName();

    public void decodePath(String srcUrl) {
        RetrofitManager.getNewsApi().getVideoHtml("https://pv.vlogdownloader.com")
                .flatMap(new Function<String, ObservableSource<? extends VideoModel>>() {
                    @Override
                    public ObservableSource<? extends VideoModel> apply(String s) throws Exception {
                        Pattern pattern = Pattern.compile("var hash = \"(.+)\"");
                        Matcher matcher = pattern.matcher(s);
                        if (matcher.find()) {
                            String hash = matcher.group(1);
                            String url = String.format("http://pv.vlogdownloader.com/api.php?url=%s&hash=%s", srcUrl, hash);
                            return RetrofitManager.getNewsApi().getVideoData(url);
                        }
                        return null;
                    }
                })
                .map(new Function<VideoModel, Video>() {
                    @Override
                    public Video apply(VideoModel videoModel) throws Exception {
                        List<Video> video = videoModel.video;
                        if (!video.isEmpty()){
                            //取数组中最后一个
                            return video.get(video.size() - 1);
                        }
                        return null;
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Video>() {
                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        onDecodeError("解析视频失败，请重试");
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Video video) {
                        if (video != null){
                            onSuccess(video.url);
                        }else{
                            onDecodeError("解析视频失败，请重试");
                        }
                    }
                });
    }

    public abstract void onSuccess(String url);
    public abstract void onDecodeError(String errorMsg);
}
