package com.example.toutiaonews.fragment_video.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;

import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toutiaonews.R;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public VideoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Log.d(TAG, "convert: ");
        helper.setText(R.id.item_name_txt, "UserName");
        Glide.with(mContext).load(R.mipmap.ic_launcher_round).transform(new CircleCrop()).into((ImageView) helper.getView(R.id.item_head_pic));
        RequestOptions requestOptions = new RequestOptions().frame(400000).error(R.drawable.ic_launcher_foreground).placeholder(R.drawable.ic_launcher_background).centerCrop();
        Glide.with(mContext)
                .setDefaultRequestOptions(requestOptions)
                .load(item)
                .into((ImageView) helper.getView(R.id.item_video_pic));
        helper.addOnClickListener(R.id.item_gz_txt)
                .addOnClickListener(R.id.item_more_img)
                .addOnClickListener(R.id.item_message_txt)
                .addOnClickListener(R.id.item_video_GSY)
                .addOnClickListener(R.id.item_video_pic)
                .addOnClickListener(R.id.item_head_pic);


    }

}
