package com.example.video.fragment_video.presenter;


import android.util.Log;

import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.net.bean.Recommend;
import com.example.net.observer.BaseObserver;
import com.example.video.fragment_video.contract.ContractVideo;

public class PresenterVideo extends BasePresenter<ContractVideo.View, ContractVideo.Model> {
    public PresenterVideo(ContractVideo.Model mModel, ContractVideo.View mView) {
        super(mModel, mView);
    }

    public void getVideoData(long listTime, String index) {
        mModel.getData(new BaseObserver<Recommend>() {
            @Override
            public void success(Recommend s) {
                mView.getVideoData(s);
            }

            @Override
            public void error(String errorCode, String errorMessage) {
                Log.e("7897987", "success: " + errorMessage);

            }


        }, listTime,index);
    }
}
