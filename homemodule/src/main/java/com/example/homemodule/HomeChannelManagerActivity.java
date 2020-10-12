package com.example.homemodule;

import android.view.View;

import com.example.farmework.base.BaseActivity;

public class HomeChannelManagerActivity extends BaseActivity {


    @Override
    protected void initData() {

    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_home_channelmanager;
    }

    @Override
    protected void initView() {


        findViewById(R.id.channels_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
