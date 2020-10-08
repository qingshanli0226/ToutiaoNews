package com.example.video.fragment_video.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.net.bean.ContentBean;
import com.example.net.bean.VideoContentEntity;
import com.example.video.R;

import java.util.List;


public class MyVideoAdapter extends BaseQuickAdapter<VideoContentEntity, BaseViewHolder> {
    public MyVideoAdapter(int layoutResId, @Nullable List<VideoContentEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, VideoContentEntity item) {

        if (item.getShare_info().getWeixin_cover_image() != null) {
            String url = item.getMiddle_image().getUrl();
            Glide.with(mContext).load(url).into((ImageView) helper.getView(R.id.item_video_pic));
        }
        if (item.getMedia_info() != null) {
            Glide.with(mContext).load(item.getMedia_info().getAvatar_url()).transform(new CircleCrop()).into((ImageView) helper.getView(R.id.item_head_pic));
            helper.setText(R.id.item_name_txt, item.getMedia_info().getName());
        }
//        helper.setText(R.id.item_title_video_top, item.getAbstractX());


        helper.addOnClickListener(R.id.item_video_pic)
                .addOnClickListener(R.id.item_head_pic)
                .addOnClickListener(R.id.item_gz_txt)
                .addOnClickListener(R.id.item_message_txt)
                .addOnClickListener(R.id.item_more_img)
                .addOnClickListener(R.id.item_name_txt);

    }
}
