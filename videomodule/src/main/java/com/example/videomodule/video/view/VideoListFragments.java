package com.example.videomodule.video.view;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.example.video.mvp.presenter.VideoPresenterImpl;
import com.example.videomodule.adapter.VideoListAdapter;
import com.example.videomodule.video.contract.VideoContract;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JzvdStd;

public class VideoListFragments extends BaseMVPFragment<VideoPresenterImpl, VideoContract.IVideoView> implements VideoContract.IVideoView,BGRefrushLayout.IRefreshListener{
    private BGRefrushLayout videoRefrush;
    private boolean arguments;
    private String channelCode;
    private List<VideoBean.DataBean> list = new ArrayList<>();
    private RecyclerView videoRv;
    private List<VideoDataBean> listData = new ArrayList<>();
    private VideoListAdapter videoListAdapter;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 1){
                videoListAdapter = new VideoListAdapter(R.layout.item_listview, listData);
                videoRv.setAdapter(videoListAdapter);
                videoRv.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
    };


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
        videoRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if(newState == RecyclerView.SCROLL_STATE_IDLE){
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) videoRv.getLayoutManager();
                    int newPosition = linearLayoutManager.findFirstCompletelyVisibleItemPosition();//获取新滑动到的Item对应的位置
                    Log.i("----position", newPosition+"");
                    videoRefrush.setposition(newPosition);
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        videoRefrush.addRefreshListener(this);
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
    public void onVideoData(VideoBean videoBean) {
        list.addAll(videoBean.getData());
        //解析
        Gson gson = new Gson();
        for (int i = 0; i < list.size(); i++) {
            String json = list.get(i).getContent();
            VideoDataBean videoDataBean = gson.fromJson(json, VideoDataBean.class);
            listData.add(videoDataBean);
            Message message = new Message();
            message.what = 1;
            message.obj = videoDataBean.getUrl();
            handler.sendMessage(message);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
        videoRefrush.cancel();
    }

    @Override
    public void onRefreshComplete() {

    }
}
