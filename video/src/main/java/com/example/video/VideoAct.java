package com.example.video;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.common.ARouterCommon;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.net.bean.ContentBean;
import com.example.net.bean.VideoContentEntity;
import com.example.net.http.HttpManager;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;


@Route(path = ARouterCommon.VIDEO_PLAY_ACT)
public class VideoAct extends BaseActivity {
    private StandardGSYVideoPlayer mGSYPlay;
    private OrientationUtils orientationUtils;
    public String videoUrl = "http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4";
    @Autowired(name = "ContentBean")
    public String contentBean;
    private boolean isPause;
    private boolean isPlay;
    private boolean isChange = true;
    private RecyclerView mMessageList;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        mMessageList = (RecyclerView) findViewById(R.id.message_list);
        mGSYPlay = (StandardGSYVideoPlayer) findViewById(R.id.GSY_play);
        orientationUtils = new OrientationUtils(this, mGSYPlay);
    }

    @Override
    public void initData() {

        VideoContentEntity contentBean = HttpManager.getHttpManager().getGson().fromJson(this.contentBean, VideoContentEntity.class);

        mGSYPlay.setUp(videoUrl, true, "");
        mGSYPlay.startPlayLogic();
        mGSYPlay.getBackButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mGSYPlay.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isChange) {
                    isChange = false;
                    GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT);
                } else {
                    isChange = true;
                    GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);
                }
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                mGSYPlay.startWindowFullscreen(VideoAct.this, true, true);
            }
        });
    }

    @Override
    public int bandLayout() {
        return R.layout.video_layout;
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        mGSYPlay.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        mGSYPlay.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            mGSYPlay.getCurrentPlayer().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mGSYPlay.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }
}
