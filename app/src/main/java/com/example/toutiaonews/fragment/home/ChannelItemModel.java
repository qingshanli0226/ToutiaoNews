package com.example.toutiaonews.fragment.home;

import com.example.framework2.mvp.model.BaseModel;
import com.example.net.activity_bean.response.NewsResponse;
import com.example.net.http.HttpManager;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class ChannelItemModel extends BaseModel implements ChannelItemContract.Model{
    @Override
    public void requestGetData(String code,long lastTime, Observer<NewsResponse> observer) {
        HttpManager.getHttpManager()
                .getRetrofit()
                .getNewsList(code,lastTime,System.currentTimeMillis())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
