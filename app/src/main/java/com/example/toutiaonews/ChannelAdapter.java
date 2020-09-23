package com.example.toutiaonews;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.net.activity_bean.entity.ChannelBean;

import java.util.List;

public class ChannelAdapter extends BaseQuickAdapter<ChannelBean, BaseViewHolder> {
    public ChannelAdapter( @Nullable List<ChannelBean> data) {
        super(R.layout.grid_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelBean item) {
        ImageView sign =helper.getView(R.id.grid_item_sign);
        TextView name =helper.getView(R.id.grid_item_name);
        if (item.isSign()){
            sign.setVisibility(View.VISIBLE);
        }else {
            sign.setVisibility(View.GONE);
        }
        helper.setText(R.id.grid_item_name,item.getTitle());

    }
}
