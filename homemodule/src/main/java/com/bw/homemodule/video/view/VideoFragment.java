package com.bw.homemodule.video.view;

import android.util.Log;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.homemodule.R;
import com.bw.homemodule.adapter.VideoListAdapter;
import com.bw.homemodule.video.contract.VideoContract;
import com.bw.homemodule.video.presenter.VideoPresenter;
import com.example.common.cache.CacheManager;
import com.example.common.entity.VideoDataBean;
import com.example.common.mine.BGRefrushLayout;
import com.example.farmework.base.BaseMVPFragment;

import java.util.ArrayList;

public class VideoFragment extends BaseMVPFragment<VideoPresenter, VideoContract.IVideoView> implements VideoContract.IVideoView, BGRefrushLayout.IRefreshListener {
    private RecyclerView rvVideo;

    private String channel_code;
    private long lastTime;
    private VideoListAdapter videoListAdapter;
    private ArrayList<VideoDataBean> videoDataBeans = new ArrayList<>();
    private BGRefrushLayout vdBGRefrush;

    public VideoFragment(String channel_code) {
        this.channel_code = channel_code;
    }

    @Override
    protected void initHttpData() {
        lastTime = CacheManager.getInstance().getFirstTime(channel_code, 0);
        if (lastTime == 0) {
            CacheManager.getInstance().putFirstTime(channel_code, System.currentTimeMillis());
        }
        mPresenter.getVideoData(channel_code, CacheManager.getInstance().getFirstTime(channel_code, 0));
    }

    @Override
    protected void initPresenter() {
        mPresenter = new VideoPresenter();
    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {
        rvVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        rvVideo.setAdapter(videoListAdapter);

        vdBGRefrush.addRefreshListener(this);
    }

    @Override
    protected void initView() {
        rvVideo=findViewById(R.id.rv_video);
        videoListAdapter= new VideoListAdapter(R.layout.video_item_layout,videoDataBeans);

        vdBGRefrush=findViewById(R.id.vd_Refush);

    }

    @Override
    public void onVideoData(ArrayList<VideoDataBean> videoDataBeans) {
        this.videoDataBeans.addAll(0,videoDataBeans);
        videoListAdapter.notifyDataSetChanged();
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
    public void onRefreshComplete() {
        initHttpData();
    }
}
