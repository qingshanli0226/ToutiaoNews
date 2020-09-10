package com.example.video;

import android.util.Log;
import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmework.base.BaseActivity;
import com.example.toutiaonews.R;
import com.example.video.fragment.VideoFragment;

import java.util.List;

public class VideoActivity extends BaseActivity {
    private FrameLayout mainFrame;
    @Override
    protected void initData() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = supportFragmentManager.beginTransaction();
        transaction.add(R.id.main_frame, new VideoFragment());
        transaction.commit();
    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_video;
    }

    @Override
    protected void initView() {
        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
    }
}
