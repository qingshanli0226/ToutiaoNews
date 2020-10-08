package com.example.toutiaonews;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.framework2.mvp.view.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class Welcome extends BaseActivity {
    private final int TAG = 1001;
    private int time = 5;
    private TextView mTimeDao;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == TAG) {
                mTimeDao.setText(String.valueOf(time));
                if (time == 0) {
                    startActivity(new Intent(Welcome.this, MainActivity.class));
                }
                time--;
            }
        }
    };

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        mTimeDao = (TextView) findViewById(R.id.time_dao);
    }

    @Override
    public void initData() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(TAG);
            }
        }, 0, 1000);
    }

    @Override
    public int bandLayout() {
        return R.layout.layout_welcome;
    }
}
