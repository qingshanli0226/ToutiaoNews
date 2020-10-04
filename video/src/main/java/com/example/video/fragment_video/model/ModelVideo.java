package com.example.video.fragment_video.model;


import com.example.common.NetCommon;
import com.example.framework2.mvp.model.BaseModel;
import com.example.framework2.utils.Tools;
import com.example.net.api_srever.ApiServer;
import com.example.net.bean.Recommend;
import com.example.net.bean.VideoEntity;
import com.example.net.http.HttpManager;
import com.example.net.observer.BaseObserver;
import com.example.video.fragment_video.contract.ContractVideo;

import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class ModelVideo extends BaseModel implements ContractVideo.Model {


    @Override
    public void getData(BaseObserver<VideoEntity> baseObserver, Consumer<Disposable> consumer, long listTime, String index) {

        long currentTimeMillis = System.currentTimeMillis();
        Tools.getTools().putVideoTime(index + "_time", currentTimeMillis);

        HttpManager.getHttpManager().setPath(NetCommon.BASE_URL);
        HttpManager.getHttpManager()
                .getRetrofit()
                .create(ApiServer.class)
                .getVideoData(index, listTime / 1000, currentTimeMillis / 1000)
                .delay(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe(consumer)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(baseObserver);
    }

}
