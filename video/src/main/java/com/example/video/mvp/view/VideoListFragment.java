package com.example.video.mvp.view;

import android.os.Bundle;
import android.widget.VideoView;

import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import com.example.common.bean.NewsRoomBean;
import com.example.common.constant.Constant;
import com.example.common.entity.Video;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.common.response.NewsResponse;
import com.example.farmework.base.BaseFragment;
import com.example.farmework.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.video.mvp.contract.VideoContract;
import com.example.video.mvp.presenter.VideoPresenterImpl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class VideoListFragment extends BaseMVPFragment<VideoPresenterImpl, VideoContract.IVideoView> implements VideoContract.IVideoView{

    private boolean arguments;
    private List<VideoBean> list = new ArrayList<>();
    private RecyclerView videoRv;
    private List<VideoDataBean> listData = new ArrayList<>();

    @Override
    protected void initHttpData() {
        mPresenter.getVideoData("subv_voice",Long.valueOf("9600137364322"));
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
        Gson gson = new Gson();
        for (int i = 0; i < list.size(); i++) {
            VideoDataBean videoDataBean = gson.fromJson(list.get(i).getData().toString(), VideoDataBean.class);
            listData.add(videoDataBean);
        }

    }

    @Override
    protected void initView() {
        videoRv = (RecyclerView) findViewById(R.id.video_rv);
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

    @Override
    public void onVideoData(VideoBean videoBean) {
        list.add(videoBean);
    }
}
