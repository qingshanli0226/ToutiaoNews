package com.example.videolibrary;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;

import com.example.framework.bean.BaseMVPFragment;
import com.example.net.activity_bean.VideoBean;
import com.example.videolibrary.adapter.VideoAdapter;
import com.example.videolibrary.mvp.VideoChildContract;
import com.example.videolibrary.mvp.VideoChildPresenterImpl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoChildFragment extends BaseMVPFragment<VideoChildPresenterImpl, VideoChildContract.IVideoChildView> implements VideoChildContract.IVideoChildView {
    private static final String TAG = "VideoChildFragment AAA";
    private RecyclerView fragmentVideoChildRv;

    private VideoAdapter videoAdapter;
    private List<VideoDataBean> videoDataBeans;

    private String category;


    public VideoChildFragment() {
        // Required empty public constructor
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video_child;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        category = getArguments().getString("category","");
        Log.i(TAG, "initView:      category     " + category);
        fragmentVideoChildRv = findViewById(R.id.fragment_video_child_rv);

        videoDataBeans = new ArrayList<>();
        videoAdapter = new VideoAdapter(videoDataBeans);
        fragmentVideoChildRv.setAdapter(videoAdapter);
        fragmentVideoChildRv.setLayoutManager(new LinearLayoutManager(getContext()));
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
        Log.i(TAG, "onVideoChildData:      12345678980 ");
        Gson gson = new Gson();
        List<VideoBean.DataBean> data = videoBean.getData();
        Log.i(TAG, "onVideoChildData:   data.size  " + data.size());

        for (int i = 0; i < data.size(); i++) {
            String content = data.get(i).getContent();
            VideoDataBean videoDataBean = gson.fromJson(content.toString(), VideoDataBean.class);
            String articleUrl = videoDataBean.getArticle_url();
            Log.i(TAG, "onVideoChildData:         " + articleUrl);
            videoDataBeans.add(videoDataBean);
            videoAdapter.notifyDataSetChanged();
        }
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
}
