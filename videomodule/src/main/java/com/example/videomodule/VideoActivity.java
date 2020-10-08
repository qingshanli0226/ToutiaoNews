package com.example.videomodule;

import android.widget.FrameLayout;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.farmework.base.BaseActivity;
import com.example.farmework.listener.PermissionListener;
import com.example.videomodule.fragment.VideoFragment;

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
        requestPermissions(new String[]{"android.permission.INTERNET",
        "android.permission.WRITE_CONTACTS","android.permission.READ_CONTACTS","android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"}, new PermissionListener() {
            @Override
            public void Granted() {
                //授权成功
            }

            @Override
            public void onDeied(List<String> deniedPermissions) {
                //授权失败
            }
        });
    }
}
