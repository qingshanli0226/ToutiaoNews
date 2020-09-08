package com.example.toutiaonews.fragment_video.presenter;

import com.example.framework2.mvp.presenter.BasePresenter;
import com.example.toutiaonews.fragment_video.contract.ContractVideo;

public class PresenterVideo extends BasePresenter<ContractVideo.View, ContractVideo.Model> {
    public PresenterVideo(ContractVideo.Model mModel, ContractVideo.View mView) {
        super(mModel, mView);
    }
}
