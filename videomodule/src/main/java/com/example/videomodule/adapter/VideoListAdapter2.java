package com.example.videomodule.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.VideoDataBean;
import com.example.toutiaonews.R;

import java.util.List;

import cn.jzvd.JzvdStd;

public class VideoListAdapter2 extends BaseQuickAdapter<NewsRoomBean, BaseViewHolder> {

    public VideoListAdapter2(int layoutResId, @Nullable List<NewsRoomBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, NewsRoomBean item) {
        if(item.getNewsName() == null){
            return;
        }
        JzvdStd MyJzvdStd = helper.getView(R.id.list_jiaozi);
        //作者信息
        helper.setText(R.id.list_title, item.getNewsName());
        View view = helper.getView(R.id.list_image);
        //作者头像显示
        Glide.with(mContext)
                .load(item.getNewsImageUrl())
                .circleCrop()
                .into((ImageView) view);
        helper.setText(R.id.list_ping, item.getNewsCommon()+"");
        //缩略图显示
        Glide.with(mContext)
                .load(item.getNewsThumImage())
                .into(MyJzvdStd.thumbImageView);
        //视频标题
        helper.setText(R.id.list_text, item.getNewTitle());
        //播放次数
//        helper.setText(R.id.tv_watch_count, item.getNewsCount()+"次播放");
        //拿到当前item的标题布局
        View view1 = helper.getView(R.id.ll_title);
        //设置在最顶层
        view1.bringToFront();
        //隐藏播放器的返回按钮
        MyJzvdStd.backButton.setVisibility(View.VISIBLE);
        //隐藏播放器电量
        MyJzvdStd.batteryLevel.setVisibility(View.VISIBLE);
    }
}
