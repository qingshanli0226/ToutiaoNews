package com.example.homemodule.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.entity.VideoDataBean;
import com.example.homemodule.R;

import java.util.List;

public class VideoListAdapter extends BaseQuickAdapter<VideoDataBean, BaseViewHolder> {
    public VideoListAdapter(int layoutResId, @Nullable List<VideoDataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoDataBean item) {
        helper.setText(R.id.vd_title,item.getTitle()+"");

        int watchCount = item.getVideo_detail_info().getVideo_watch_count();
        String countUnit="";
        if (watchCount>10000){
            watchCount = watchCount /10000;
            countUnit = "万";
        }

        helper.setText(R.id.vd_watch_count,watchCount+countUnit+"次播放");

        Glide.with(mContext).load(item.getUser_info().getAvatar_url()).circleCrop().into((ImageView) helper.getView(R.id.vd_user_img));

        helper.setText(R.id.vd_user_name,item.getUser_info().getName()+"");

        helper.setText(R.id.vd_comment_num,item.getComment_count()+"");

        Glide.with(mContext).load(item.getVideo_detail_info().getDetail_video_large_image().getUrl()).into((ImageView) helper.getView(R.id.vd_show_img));


    }
}
