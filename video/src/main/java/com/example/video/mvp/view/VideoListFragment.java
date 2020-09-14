package com.example.video.mvp.view;

import android.os.Bundle;
import android.widget.VideoView;

import com.example.common.bean.NewsRoomBean;
import com.example.common.constant.Constant;
import com.example.farmework.base.BaseFragment;
import com.example.farmework.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.video.mvp.contract.VideoContract;
import com.example.video.mvp.presenter.VideoPresenterImpl;

public class VideoListFragment extends BaseMVPFragment<VideoPresenterImpl, VideoContract.IVideoView> implements VideoContract.IVideoView{

    private boolean arguments;

    @Override
    protected void initHttpData() {

    }

    @Override
    protected void initPresenter() {
        mPresenter = new VideoPresenterImpl();
    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_videolist;
    }

    @Override
    protected void initData() {
        arguments  = getArguments().getBoolean(Constant.IS_VIDEO_LIST);
    }

    @Override
    protected void initView() {

    }

    @Override
    public void onVideoData(NewsRoomBean newsRoomBean) {

    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
