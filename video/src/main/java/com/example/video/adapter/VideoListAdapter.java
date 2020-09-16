package com.example.video.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.common.response.NewsResponse;
import com.example.toutiaonews.R;

import java.util.List;

import cn.jzvd.JzvdStd;

public class VideoListAdapter extends BaseQuickAdapter<VideoDataBean, BaseViewHolder >{

    public VideoListAdapter(int layoutResId, @Nullable List<VideoDataBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, VideoDataBean item) {
        JzvdStd MyJzvdStd = helper.getView(R.id.list_jiaozi);
    }
}
