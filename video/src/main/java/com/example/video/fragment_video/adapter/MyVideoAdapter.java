package com.example.video.fragment_video.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.net.bean.ContentBean;
import com.example.video.R;

import java.util.List;


public class MyVideoAdapter extends BaseQuickAdapter<ContentBean, BaseViewHolder> {
    public MyVideoAdapter(int layoutResId, @Nullable List<ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentBean item) {


        if (item.getShare_info().getWeixin_cover_image() != null) {
            Glide.with(mContext).load(item.getShare_info().getWeixin_cover_image().getUrl()).into((ImageView) helper.getView(R.id.item_video_pic));
        }
        if (item.getMedia_info() != null) {
            Glide.with(mContext).load(item.getMedia_info().getAvatar_url()).transform(new CircleCrop()).into((ImageView) helper.getView(R.id.item_head_pic));
            helper.setText(R.id.item_name_txt, item.getMedia_info().getName());
        }
<<<<<<< HEAD
        helper.addOnClickListener(R.id.item_video_pic);
=======
>>>>>>> 661a40778eab2af40835b849e3b94b8ca35a8ba0
    }
}
