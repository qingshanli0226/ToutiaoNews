package com.example.videolibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class VideoActivity extends AppCompatActivity {
    private StandardGSYVideoPlayer gsyplayer;
    private OrientationUtils orientationUtils;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //隐藏状态栏
//        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN); //显示状态栏
        initView();
        initData();
    }

    private void initView() {
        gsyplayer =  findViewById(R.id.gsy_player);
    }


    private void initData() {
        String url = "http://ips.ifeng.com/video19.ifeng.com/video09/2014/06/16/1989823-102-086-0009.mp4";
        String title = "节目";

        gsyplayer.setUp(url, false, title);
        //设置旋转
        orientationUtils = new OrientationUtils(this, gsyplayer);
        //设置全屏按键功能
        gsyplayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                time = gsyplayer.getGSYVideoManager().getDuration();
//                gsyplayer.getGSYVideoManager().seekTo(time);
                orientationUtils.resolveByClick();  //此方法是切换屏幕旋转，例如现在是竖屏，调用后变横屏，反正一样，设置这个之前，先把Activity禁止横竖屏切换，并且竖屏模式，不然此方法无
            }
        });

        gsyplayer.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (VideoActivity.this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
                    finish();
                } else {
                    Toast.makeText(VideoActivity.this, "返回竖屏模式", Toast.LENGTH_SHORT).show();
                    orientationUtils.resolveByClick();
                }

            }
        });
        gsyplayer.startPlayLogic();

    }


    @Override
    protected void onPause() {
        super.onPause();
        gsyplayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gsyplayer.onVideoResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            gsyplayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        gsyplayer.setVideoAllCallBack(null);
        super.onBackPressed();
    }

}