package com.example.video.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.entity.VideoDataBean;
import com.example.toutiaonews.R;

import java.util.List;

import cn.jzvd.JzvdStd;

public class VideoListAdapter extends BaseQuickAdapter<VideoDataBean, BaseViewHolder> {

    public VideoListAdapter(int layoutResId, @Nullable List<VideoDataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, VideoDataBean item) {
        JzvdStd MyJzvdStd = helper.getView(R.id.list_jiaozi);
        helper.setText(R.id.list_title, item.getMedia_name());
        View view = helper.getView(R.id.list_image);
        Glide.with(mContext)
                .load(item.getUser_info().getAvatar_url())
                .circleCrop()
                .into((ImageView) view);
        helper.setText(R.id.list_ping, item.getComment_count()+"");
    }
}
