package com.example.toutiaonews;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.net.activity_bean.ChannelBean;

import java.util.List;

public class MyAdapter extends BaseQuickAdapter<ChannelBean, BaseViewHolder> {
    public MyAdapter( @Nullable List<ChannelBean> data) {
        super(R.layout.my_item_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChannelBean item) {
        helper.setText(R.id.my_item_title,item.getTitle());
    }
}
