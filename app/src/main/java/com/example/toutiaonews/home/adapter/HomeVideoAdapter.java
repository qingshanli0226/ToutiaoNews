package com.example.toutiaonews.home.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.mode.VideoDataBean;
import com.example.common.untils.TimeUntil;
import com.example.toutiaonews.R;

import java.util.List;

import cn.jzvd.JzvdStd;

public class HomeVideoAdapter extends BaseQuickAdapter<VideoDataBean, BaseViewHolder> {
    public HomeVideoAdapter(int layoutResId, @Nullable List<VideoDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDataBean item) {
        JzvdStd jzvdStd = helper.getView(R.id.video_player);

        jzvdStd.batteryLevel.setVisibility(View.GONE);//隐藏电池电量
        jzvdStd.mRetryLayout.setVisibility(View.GONE);//隐藏加载错误

        helper.setText(R.id.tv_title, item.getTitle());//标题
        helper.setText(R.id.tv_duration, TimeUntil.getStringTime(item.getVideo_duration()));


        helper.setText(R.id.tv_comment_count, "" + item.getComment_count());//评论数量
        helper.setText(R.id.tv_author, item.getMedia_name());//昵称
        if(item.getRead_count() > 10000){
            helper.setText(R.id.tv_watch_count,item.getRead_count()/10000+"万");//播放次数
        } else{
            helper.setText(R.id.tv_watch_count,item.getRead_count()+"");//播放次数
        }

        Glide.with(mContext).load(item.getUser_info().getAvatar_url()).circleCrop().into((ImageView) helper.getView(R.id.iv_avatar));//作者头像
        Glide.with(mContext).load(item.getShare_large_image().getUrl()).into(jzvdStd.thumbImageView);//视频缩略图
    }
}
