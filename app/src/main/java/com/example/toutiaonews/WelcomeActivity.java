package com.example.toutiaonews;

import android.view.WindowManager;

import com.example.framework2.base.BaseActivity;
import com.example.toutiaonews.main.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {

    Timer timer;
    int timeCount = 0;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        //隐藏顶部状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //2秒后进入主页面
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(timeCount >= 2){
                    //跳转页面
                    launchActivity(MainActivity.class,null);
                    finish();
                    timer.cancel();
                }
                //计数
                timeCount++;
            }
        },0,1000);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //手动关闭timer
        timer.cancel();
    }
}
