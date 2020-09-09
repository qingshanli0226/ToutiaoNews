package com.bw.homemodule;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.farmework.base.BaseActivity;
import com.example.farmework.base.BaseMVPActivity;

import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class MainActivity extends BaseMVPActivity {
    private String[] titles;
    private FrameLayout frameLayout;

    @Override
    protected void initPresenter() {

    }

    @Override
    protected void initData() {
        titles = this.getResources().getStringArray(R.array.channel);

    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        frameLayout=findViewById(R.id.main_frame);


    }
}
