package com.example.toutiaonews.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.farmework.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.activity.ParticularActivity;

public class MicroFragment extends BaseFragment {
    private LinearLayout txt;
    private LinearLayout pic;
    private LinearLayout video;
    @Override
    protected int bandLayout() {
        return R.layout.micro_fragment;
    }

    @Override
    protected void initView() {
        txt = (LinearLayout) findViewById(R.id.txt);
        pic = (LinearLayout) findViewById(R.id.pic);
        video = (LinearLayout) findViewById(R.id.video);
    }
    @Override
    protected void initData() {


    }
}
