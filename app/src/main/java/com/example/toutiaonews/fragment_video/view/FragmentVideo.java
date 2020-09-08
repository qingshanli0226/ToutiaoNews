package com.example.toutiaonews.fragment_video.view;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment_video.adapter.VideoAdapter;
import com.example.toutiaonews.fragment_video.presenter.PresenterVideo;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class FragmentVideo extends BaseFragment<PresenterVideo> {
    private RecyclerView mVideoListRv;
    private SmartRefreshLayout mRefreshListSrl;


    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        mRefreshListSrl = (SmartRefreshLayout) findViewById(R.id.refresh_list_srl);
        mVideoListRv = (RecyclerView) findViewById(R.id.video_list_rv);
        mRefreshListSrl.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mRefreshListSrl.finishLoadMore(true);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mRefreshListSrl.finishRefresh(true);
            }
        });
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(String.valueOf(i));
        }
        VideoAdapter videoAdapter = new VideoAdapter(R.layout.item_video_box, list);
        mVideoListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mVideoListRv.setAdapter(videoAdapter);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.fragment_video;
    }
}
