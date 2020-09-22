package com.example.toutiaonews.activity;

import com.example.farmework.base.BaseActivity;
import com.example.toutiaonews.R;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private int timp = 5;

    @Override
    protected void initData() {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                timp--;
                if (timp <= 0) {
                    timer.cancel();
                    finish();
                }
            }
        }, 0, 1000);
    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

    }
}
