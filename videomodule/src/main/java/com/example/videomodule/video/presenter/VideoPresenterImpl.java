package com.example.videomodule.video.presenter;


import android.util.Log;

import com.example.common.cache.CacheManager;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.net.obserable.BaseObserable;
import com.example.net.retrofit.RetrofitManager;
import com.example.videomodule.video.contract.VideoContract;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class VideoPresenterImpl extends VideoContract.VideoPresenter {
    private long firstTime;
    private List<VideoBean.DataBean> dataBeans = new ArrayList<>();
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
                        if(!videoBean.toString().equals("")){
                            dataBeans = videoBean.getData();
                            if(dataBeans.size() != 0){
                                //请求过网络数据
                                CacheManager.getInstance().putIsVisit(channel, true);
                                for (int i = 0; i < videoBean.getData().size(); i++) {
                                    String content = videoBean.getData().get(i).getContent();
                                    NewsRoomBean newsRoomBean = new NewsRoomBean();
                                    newsRoomBean.setChannelId(category);
                                    newsRoomBean.setJsonUrl(content);
                                    newsRoomBean.setNewsTime(System.currentTimeMillis());
                                    CacheManager.getInstance().insert(newsRoomBean);
                                    iHttpView.onVideoData(content);
                                }
                            }
                        }else{
                            iHttpView.showError("1", "请求不到");
                        }
                    }

                    @Override
                    public void onRequestError(String errorCode, String errorMessage) {
                        iHttpView.showError(errorCode, "请求失败,请重试");
                    }
                });
    }
}