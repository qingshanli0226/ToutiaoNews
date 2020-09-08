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
        RequestOptions requestOptions = new RequestOptions().frame(40000).circleCrop();
        Glide.with(mContext).load(R.mipmap.ic_launcher).into((ImageView) helper.getView(R.id.item_video_pic));
    }
}
