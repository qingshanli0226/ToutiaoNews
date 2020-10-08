package com.example.videomodule.video.presenter;


import com.example.common.cache.CacheManager;
import com.example.common.entity.VideoBean;
import com.example.net.obserable.BaseObserable;
import com.example.net.retrofit.RetrofitManager;
import com.example.videomodule.video.contract.VideoContract;
import io.reactivex.android.schedulers.AndroidSchedulers;
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
                        //参数时间戳
                        firstTime = System.currentTimeMillis() / 1000;
                        CacheManager.getInstance().putFirstTime("first",firstTime);
                        //请求过网络数据
                        CacheManager.getInstance().putisVisit(channel, true);
                        iHttpView.onVideoData(videoBean);
                    }

                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        iHttpView.showError(errorCode, "请求失败,请重试");
                    }
                });
    }
}