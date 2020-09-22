package com.example.home.presenter;

import android.util.Log;

import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.home.contract.ContractRecommend;
import com.example.net.bean.Recommend;
import com.example.net.observer.BaseObserver;

public class PresenterRecommend extends BasePresenter<ContractRecommend.View,ContractRecommend.Model> {
    public PresenterRecommend(ContractRecommend.Model mModel, ContractRecommend.View mView) {
        super(mModel, mView);
    }

    public void getRecommendData(long listTime, String index) {
        mModel.getData(new BaseObserver<Recommend>() {
            @Override
            public void success(Recommend s) {
                mView.getRecommendData(s);
            }

            @Override
            public void error(String errorCode, String errorMessage) {
                Log.e("1008611", "success: " + errorMessage);

            }

        }, listTime,index);
    }
}
