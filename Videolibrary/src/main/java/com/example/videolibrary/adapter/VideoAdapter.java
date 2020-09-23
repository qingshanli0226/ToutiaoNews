package com.example.videolibrary.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.videolibrary.R;
import com.example.videolibrary.VideoDataBean;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

public class VideoAdapter extends BaseQuickAdapter<VideoDataBean, BaseViewHolder> {
    public VideoAdapter(@Nullable List<VideoDataBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDataBean item) {


        JZVideoPlayerStandard jzVideoPlayerStandard = helper.getView(R.id.item_video_jiaozi);

        Glide.with(mContext).load(item.getShare_large_image().getUrl()).into(jzVideoPlayerStandard.thumbImageView);
        Glide.with(mContext).load(item.getUser_info().getAvatar_url()).apply(new RequestOptions().circleCrop()).into((ImageView) helper.getView(R.id.item_video_user_pic));
        helper.setText(R.id.item_video_user_name, item.getUser_info().getName());
        helper.setText(R.id.item_video_comment_count, item.getUser_info().getFollower_count()+"");


//        gsy.setUp(item.getShare_large_image().getUri(), false, item.getTitle());
//
//
//        //设置返回键
//        gsy.getBackButton().setVisibility(View.GONE);
//        //设置全屏按键功能
//        gsy.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                gsy.startWindowFullscreen(mContext, false, true);
//            }
//        });


//是否根据视频尺寸，自动选择竖屏全屏或者横屏全屏
//        gsy.setAutoFullWithSize(true);
////音频焦点冲突时是否释放
//        gsy.setReleaseWhenLossAudio(false);
////全屏动画
//        gsy.setShowFullAnimation(true);
////小屏时不触摸滑动
//        gsy.setIsTouchWiget(false);

    }
}
