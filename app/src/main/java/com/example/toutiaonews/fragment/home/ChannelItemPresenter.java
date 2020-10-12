package com.example.toutiaonews.fragment.home;

import android.util.Log;

import com.example.common.EmptyViewController;
import com.example.framework2.cache.entity.NewEntity;
import com.example.framework2.manager.CacheManager;
import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.net.activity_bean.response.NewsResponse;
import com.google.gson.Gson;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ChannelItemPresenter extends BasePresenter<ChannelItemContract.View,ChannelItemContract.Model> {
    public ChannelItemPresenter(ChannelItemContract.Model mModel, ChannelItemContract.View mView) {
        super(mModel, mView);
    }
    public synchronized void getCodeData(long lastTime){
        mModel.requestGetData(mView.getCode(),lastTime ,new Observer<NewsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(NewsResponse listBean) {
                if (listBean.data!=null){
                    Log.e("fff", "onNext: "+listBean.data.size());
                    NewEntity newEntity = new NewEntity();
                    newEntity.setCode(mView.getCode());
                    newEntity.setTime(System.currentTimeMillis());
                    String json = new Gson().toJson(listBean.data);
                    Log.e("rrr", "onNext: "+json );
                    newEntity.setJsonStr(json);
                    CacheManager.getInstance().addNewEntity(newEntity);
                    mView.getedData(listBean.data);
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
