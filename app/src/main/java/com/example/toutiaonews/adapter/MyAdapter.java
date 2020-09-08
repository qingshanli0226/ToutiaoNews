package com.example.toutiaonews.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework2.adapter.BaseRVAdapter;

public class MyAdapter extends BaseRVAdapter {

    @Override
    protected int getLayoutId(int id) {
        return 0;
    }

    @Override
    protected void convert(Object itemData, BaseViewHolder baseViewHolder, int position) {

    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }
}
