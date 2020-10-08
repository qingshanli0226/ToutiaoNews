package com.example.homemodule.homevideo.view;


import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.cache.CacheManager;
import com.example.common.constant.Constant;
import com.example.common.entity.VideoDataBean;
import com.example.common.mine.BGRefrushLayout;
import com.example.farmework.base.BaseMVPFragment;
import com.example.homemodule.R;
import com.example.homemodule.adapter.VideoListAdapter;
import com.example.homemodule.homevideo.contract.VideoContract;
import com.example.homemodule.homevideo.presenter.VideoPresenter;

import java.util.ArrayList;

public class VideoFragment extends BaseMVPFragment<VideoPresenter, VideoContract.IVideoView> implements VideoContract.IVideoView, BGRefrushLayout.IRefreshListener {
    private RecyclerView rvVideo;
    private String channel_code;
    private long lastTime;
    private VideoListAdapter videoListAdapter;
    private ArrayList<VideoDataBean> videoDataBeans = new ArrayList<>();
    private BGRefrushLayout vdBGRefrush;
    private boolean isRefresh = false;


    public VideoFragment(String channel_code) {
        this.channel_code = channel_code;
    }

    @Override
    protected void initHttpData() {

        if (CacheManager.getInstance().isConnect()) {  //判断是否有网
            //有网就开始数据请求
            lastTime = CacheManager.getInstance().getFirstTime(channel_code, 0);
            if (lastTime == 0) {
                CacheManager.getInstance().putFirstTime(channel_code, System.currentTimeMillis());
            }

            long firstTime = CacheManager.getInstance().getFirstTime(channel_code, 0);
            if (System.currentTimeMillis() - firstTime > Constant.REFRESH_TIME || isRefresh) {
                mPresenter.getVideoData(channel_code, firstTime);
            }

        } else {
            Toast.makeText(getContext(), "当前没有网络哦", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void initPresenter() {
        mPresenter = new VideoPresenter();
    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_homevideo;
    }

    @Override
    protected void initData() {
        rvVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVideo.setAdapter(videoListAdapter);

        vdBGRefrush.addRefreshListener(this);
    }

    @Override
    protected void initView() {
        rvVideo = findViewById(R.id.rv_video);
        videoListAdapter = new VideoListAdapter(R.layout.video_item_layout, videoDataBeans);

        vdBGRefrush = findViewById(R.id.vd_Refush);
        vdBGRefrush.attchRecylerView(rvVideo);

    }

    @Override
    public void onVideoData(ArrayList<VideoDataBean> videoDataBeans) {
        this.videoDataBeans.addAll(0, videoDataBeans);
        videoListAdapter.notifyDataSetChanged();
        isRefresh = false;
    }

    @Override
    public void showError(String code, String message) {
        showMessage("code:" + code + message);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRefreshComplete() {
        isRefresh = true;
        initHttpData();
    }
}
