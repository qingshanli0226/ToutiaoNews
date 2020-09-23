package com.example.videolibrary;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.Toast;

import com.example.framework.bean.BaseMVPFragment;
import com.example.net.activity_bean.VideoBean;
import com.example.videolibrary.adapter.VideoAdapter;
import com.example.videolibrary.mvp.VideoChildContract;
import com.example.videolibrary.mvp.VideoChildPresenterImpl;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;


public class VideoChildFragment extends BaseMVPFragment<VideoChildPresenterImpl, VideoChildContract.IVideoChildView> implements VideoChildContract.IVideoChildView, OnRefreshLoadMoreListener {
    private static final String TAG = "VideoChildFragment AAA";
    private RecyclerView fragmentVideoChildRv;
    private SmartRefreshLayout fragmentVideoChildSmart;
    private VideoAdapter videoAdapter;
    private List<VideoDataBean> videoDataBeans;
    private String category;
    private boolean isRefresh = true;

    public VideoChildFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_child;
    }

    @Override
    protected void initData() {
        videoDataBeans = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoDataBeans);
        fragmentVideoChildRv.setAdapter(videoAdapter);
        fragmentVideoChildRv.setLayoutManager(new LinearLayoutManager(getContext()));

        fragmentVideoChildSmart.setOnRefreshLoadMoreListener(this);
    }

    @Override
    protected void initView() {
        category = getArguments().getString("category", "");
        fragmentVideoChildRv = findViewById(R.id.fragment_video_child_rv);
        fragmentVideoChildSmart = findViewById(R.id.fragment_video_child_smart);
    }

    @Override
    protected void initHttpData() {
        Log.i(TAG, "initHttpData:     category       " + category);
        ihttpPresenter.getVideoChildData(category);


    }

    @Override
    protected void initPresenter() {
        ihttpPresenter = new VideoChildPresenterImpl();
    }

    @Override
    public void onVideoChildData(VideoBean videoBean) {
        Gson gson = new Gson();
        List<VideoBean.DataBean> data = videoBean.getData();

        //如果没有数据 通知用户 并且不执行以下代码
        if (data.size() == 0) {
            Toast.makeText(getContext(), "您刷新的频率太快了,暂无数据!!!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (isRefresh) {
            videoDataBeans.clear();
        }

        for (int i = 0; i < data.size(); i++) {
            String content = data.get(i).getContent();
            VideoDataBean videoDataBean = gson.fromJson(content.toString(), VideoDataBean.class);
            String articleUrl = videoDataBean.getArticle_url();
            Log.i(TAG, "onVideoChildData:         " + articleUrl);
            videoDataBeans.add(videoDataBean);
            videoAdapter.notifyDataSetChanged();
        }
        Log.i(TAG, "initHttpData:     videoDataBeans.size()       " + videoDataBeans.size());
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoaing() {

    }

    @Override
    public void hideLoaing() {

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        fragmentVideoChildSmart.finishLoadMore();
        isRefresh = false; //说明是加载
        ihttpPresenter.getVideoChildData(category);

    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        fragmentVideoChildSmart.finishRefresh();
        isRefresh = true;//说明是刷新 需要清除list的数据
        ihttpPresenter.getVideoChildData(category);
    }


}
