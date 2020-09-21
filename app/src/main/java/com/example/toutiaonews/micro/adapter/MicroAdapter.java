package com.example.toutiaonews.micro.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.toutiaonews.R;

import java.util.List;

public class MicroAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public MicroAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

        Glide.with(mContext)
                .load(item)
                .centerCrop()
                .into((ImageView) helper.getView(R.id.item_micro_iv));

    }
}
