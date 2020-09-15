package com.example.toutiaonews.home.adapter;

import android.view.View;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.mode.ContentBean;
import com.example.toutiaonews.R;

import java.util.List;

public class RecommendAdapter extends BaseQuickAdapter<ContentBean, BaseViewHolder> {
    public RecommendAdapter(int layoutResId, @Nullable List<ContentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ContentBean item) {
        helper.setText(R.id.recommendTitle,item.getTitle());
        helper.setText(R.id.recommendSource,item.getSource());
//        if(item.getComment_count() != 0){
//            helper.setText(R.id.recommendCount,item.getComment_count());
//        }

        if(helper.getAdapterPosition() >= 1){
            helper.getView(R.id.recommendImg).setVisibility(View.GONE);
        } else{
            helper.getView(R.id.recommendImg).setVisibility(View.VISIBLE);
        }
    }
}
