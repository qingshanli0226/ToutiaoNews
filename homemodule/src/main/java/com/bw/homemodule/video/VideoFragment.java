package com.bw.homemodule.video;

import androidx.recyclerview.widget.RecyclerView;

import com.bw.homemodule.R;
import com.example.farmework.base.BaseMVPFragment;

public class VideoFragment extends BaseMVPFragment {
    private RecyclerView rvVideo;

    private String channel_code;

    public VideoFragment(String channel_code) {
        this.channel_code = channel_code;
    }

    @Override
    protected void initHttpData() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        rvVideo=findViewById(R.id.rv_video);
    }
}
