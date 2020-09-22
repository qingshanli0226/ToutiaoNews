package com.example.videomodule.video.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.PrimaryKey;

import com.example.common.cache.CacheManager;
import com.example.common.constant.Constant;
import com.example.common.entity.NewsDetail;
import com.example.common.entity.Video;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.common.mine.BGRefrushLayout;
import com.example.common.response.NewsResponse;
import com.example.farmework.base.BaseFragment;
import com.example.farmework.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.videomodule.adapter.VideoListAdapter;
import com.example.videomodule.video.contract.VideoContract;
import com.example.videomodule.video.presenter.VideoPresenterImpl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;

public class VideoListFragments extends BaseMVPFragment<VideoPresenterImpl, VideoContract.IVideoView> implements VideoContract.IVideoView,BGRefrushLayout.IRefreshListener{
    private BGRefrushLayout videoRefrush;
    private boolean arguments;
    private String channelCode;
    private RecyclerView videoRv;
    private List<VideoDataBean> listData = new ArrayList<>();
    private VideoListAdapter videoListAdapter;


    @Override
    protected void initHttpData() {
        mPresenter.getVideoData(channelCode);
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
        channelCode  = getArguments().getString(Constant.CHANNEL_CODE);
        videoRefrush.attchRecylerView(videoRv);
        videoRefrush.addRefreshListener(this);
        videoListAdapter = new VideoListAdapter(R.layout.item_listview, listData);
        videoRv.setAdapter(videoListAdapter);
        videoRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initView() {
        videoRefrush = (BGRefrushLayout) findViewById(R.id.video_refrush);
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
    //拿到数据并进行解析
    @Override
    public void onVideoData(VideoDataBean videoBean) {
        if(videoBean != null){
            listData.add(0,videoBean);
            videoListAdapter.notifyDataSetChanged();
        }else{
            Toast.makeText(getContext(), "没有新闻了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoRefrush.cancel();
    }

    @Override
    public void onRefreshComplete() {
        mPresenter.getVideoData(channelCode);
    }
}
