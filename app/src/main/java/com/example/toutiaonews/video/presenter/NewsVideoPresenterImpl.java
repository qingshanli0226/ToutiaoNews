package com.example.toutiaonews.video.presenter;

import com.example.common.mode.VideoBean;
import com.example.net.RetroCreator;
import com.example.toutiaonews.video.contract.NewsVideoContract;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class NewsVideoPresenterImpl extends NewsVideoContract.NewsVideoPresenter {


    long lastTime;

    @Override
    public void getNewsVideoData(String category, long currentTime) {
        lastTime = System.currentTimeMillis() / 1000;
        RetroCreator.getInvestApiService().getNewsVideoList(category, lastTime, currentTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        gDisposable = disposable;
                    }
                })
                .subscribe(new Observer<VideoBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoBean videoBean) {
                        if (videoBean.getMessage().equals("success")) {
                            iHttpView.onVideoData(videoBean);
                        } else {
                            iHttpView.showError("404", videoBean.getMessage());
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
