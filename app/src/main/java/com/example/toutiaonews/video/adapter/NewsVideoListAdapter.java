package com.example.toutiaonews.video.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.mode.VideoDataBean;
import com.example.toutiaonews.R;

import java.util.List;

import cn.jzvd.JzvdStd;

public class NewsVideoListAdapter extends BaseQuickAdapter<VideoDataBean, BaseViewHolder> {
    public NewsVideoListAdapter(int layoutResId, @Nullable List<VideoDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDataBean item) {

        JzvdStd jzvdStd = helper.getView(R.id.video_player);

        jzvdStd.batteryLevel.setVisibility(View.GONE);//隐藏电池电量
        jzvdStd.mRetryLayout.setVisibility(View.GONE);//隐藏加载错误

        helper.setText(R.id.tv_title, item.getMedia_name());//标题
        helper.setText(R.id.tv_duration, item.getContent_decoration());//时长
        helper.setText(R.id.tv_comment_count, "" + item.getComment_count());//评论数量
        helper.setText(R.id.tv_author, item.getUser_info().getName());//昵称
        Glide.with(mContext).load(item.getUser_info().getAvatar_url()).circleCrop().into((ImageView) helper.getView(R.id.iv_avatar));//作者头像
        Glide.with(mContext).load(item.getShare_large_image().getUrl()).into(jzvdStd.thumbImageView);//视频缩略图


    }
}
