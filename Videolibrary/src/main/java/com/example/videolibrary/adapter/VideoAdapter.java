package com.example.videolibrary.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.videolibrary.R;
import com.example.videolibrary.VideoDataBean;

import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

public class VideoAdapter extends BaseQuickAdapter<VideoDataBean, BaseViewHolder> {
    public VideoAdapter(@Nullable List<VideoDataBean> data) {
        super(R.layout.item_video, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDataBean item) {


        JZVideoPlayerStandard jzVideoPlayerStandard = helper.getView(R.id.item_video_jiaozi);

//        Log.i("getUrl  ", "convert:   getUrl "+item.getShare_large_image().getUrl());
        Log.i("getUrl  ", "convert:   getUrl " + item.getArticle_url());
        Glide.with(mContext).load(item.getShare_large_image().getUrl()).into(jzVideoPlayerStandard.thumbImageView);
        Glide.with(mContext).load(item.getUser_info().getAvatar_url()).apply(new RequestOptions().circleCrop()).into((ImageView) helper.getView(R.id.item_video_user_pic));
        helper.setText(R.id.item_video_user_name, item.getUser_info().getName())
                .setText(R.id.item_video_comment_count, item.getUser_info().getFollower_count() + "");

        helper.addOnClickListener(R.id.item_video_jiaozi);

    }
}
