package com.example.video.mvp.view;

import android.os.Bundle;

import com.example.common.constant.Constant;
import com.example.farmework.base.BaseFragment;
import com.example.farmework.base.BaseMVPFragment;
import com.example.toutiaonews.R;

public class VideoListFragment extends BaseFragment {

    private boolean arguments;
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
}
