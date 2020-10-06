package com.example.toutiaonews.fragment.home;

import android.util.Log;

import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.net.activity_bean.response.NewsResponse;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ChannelItemPresenter extends BasePresenter<ChannelItemContract.View,ChannelItemContract.Model> {
    public ChannelItemPresenter(ChannelItemContract.Model mModel, ChannelItemContract.View mView) {
        super(mModel, mView);
    }
    public void getCodeData(long lastTime){
        mModel.requestGetData(mView.getCode(),lastTime ,new Observer<NewsResponse>() {
            @Override
            public void onSubscribe(Disposable d) {
            }
            @Override
            public void onNext(NewsResponse listBean) {
                if (listBean!=null){
                    Log.e("fff", "onNext: "+listBean.data.size());

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
