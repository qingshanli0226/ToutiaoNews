package com.example.video;

import android.content.res.Configuration;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
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
    @Autowired(name = "index")
    public int index;
    private boolean isPause;
    private boolean isPlay;
    private boolean isChange = true;
    private RecyclerView mMessageList;
    private ImageView mVideoHeadPic;
    private TextView mVideoNameTxt;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        mMessageList = (RecyclerView) findViewById(R.id.message_list);
        mGSYPlay = (StandardGSYVideoPlayer) findViewById(R.id.GSY_play);
        mVideoHeadPic = (ImageView) findViewById(R.id.video_head_pic);
        mVideoNameTxt = (TextView) findViewById(R.id.video_name_txt);

        orientationUtils = new OrientationUtils(this, mGSYPlay);
    }

    @Override
    public void initData() {

        Log.d("VideoAct", contentBean);
        Log.d("VideoAct", "index:" + index);
        VideoContentEntity contentBean = HttpManager.getHttpManager().getGson().fromJson(this.contentBean, VideoContentEntity.class);
        if (contentBean.getMedia_info() != null) {
            Glide.with(this).load(contentBean.getMedia_info().getAvatar_url()).transform(new CircleCrop()).into(mVideoHeadPic)            ;
            mVideoNameTxt.setText(contentBean.getMedia_info().getName());
        }
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
