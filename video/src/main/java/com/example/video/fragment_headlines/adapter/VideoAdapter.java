package com.example.video.fragment_headlines.adapter;

import android.widget.ImageView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.video.R;

import java.util.List;

public class VideoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public VideoAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.addOnClickListener(R.id.item_go_rl);
        Glide.with(mContext)
                .setDefaultRequestOptions(
                        new RequestOptions()
                                .frame(600000)
                                .centerCrop())
                .load(item)
                .into((ImageView) helper.getView(R.id.item_image_video));

    }

}
