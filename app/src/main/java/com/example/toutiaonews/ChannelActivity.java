package com.example.toutiaonews;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.framework2.manager.CacheManager;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.net.activity_bean.ChannelBean;

import java.util.Collections;
import java.util.List;

public class ChannelActivity extends BaseActivity {

    private boolean isEdit=false;
    private TextView myChannel;
    private TextView channelHint;
    private TextView channelEdit;
    private RecyclerView channelOnSelect;
    private RecyclerView channelNoSelect;
    private ChannelAdapter adapter1;
    private ChannelAdapter adapter2;
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.channel_edit:
                edit();
                break;

        }
    }

    private void edit() {

        if (isEdit){
            isEdit=false;
            channelEdit.setText(getString(R.string.edit));
            channelHint.setText(getString(R.string.onclick_in_channel));
            for (ChannelBean channelBean : CacheManager.getInstance().getOnList()) {
                channelBean.setSign(isEdit);
            }
            CacheManager.getInstance().getOnList();
            adapter1.notifyDataSetChanged();
        }else {
            isEdit=true;
            channelEdit.setText(getString(R.string.complete));
            channelHint.setText(getString(R.string.channel_order));
            for (ChannelBean channelBean : CacheManager.getInstance().getOnList()) {
                channelBean.setSign(isEdit);
            }
            CacheManager.getInstance().getOnList();
            adapter1.notifyDataSetChanged();

        }
    }

    @Override
    public void initView() {
        channelHint=findViewById(R.id.channel_hint);
        channelEdit=findViewById(R.id.channel_edit);
        channelNoSelect=findViewById(R.id.channel_no_select);
        channelOnSelect=findViewById(R.id.channel_on_select);
        channelEdit.setOnClickListener(this);
        initTouch();
        channelOnSelect.setLayoutManager(new GridLayoutManager(this,4));
        channelNoSelect.setLayoutManager(new GridLayoutManager(this,4));
        adapter1=new ChannelAdapter(CacheManager.getInstance().getOnList());
        channelOnSelect.setAdapter(adapter1);
        adapter2=new ChannelAdapter(CacheManager.getInstance().getNoList());
        channelNoSelect.setAdapter(adapter2);
        adapter1.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (position==0){
                    return;
                }
                if (isEdit){
//                    ChannelBean remove = CacheManager.getInstance().getOnList().remove(position);
//                    remove.setSign(false);
//                    CacheManager.getInstance().getNoList().add(remove);
                    CacheManager.getInstance().getOnList().get(position).setSign(false);
                    CacheManager.getInstance().deleteChannel(position);
                    adapter2.notifyDataSetChanged();
                    adapter1.notifyItemRemoved(position);
                }else {

                }
            }
        });
        adapter2.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ChannelBean remove = CacheManager.getInstance().getNoList().remove(position);
//                if (isEdit){
//                    remove.setSign(isEdit);
//                }
//                CacheManager.getInstance().getOnList().add(remove);

                CacheManager.getInstance().getNoList().get(position).setSign(isEdit);
                CacheManager.getInstance().addChannel(position);
                adapter1.notifyDataSetChanged();
                adapter2.notifyItemRemoved(position);
            }
        });
    }

    private void initTouch() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == 0) {
                    return 0;
                }
                if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
                     int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                     int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                } else {
                     int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                     int swipeFlags = 0;
                    return makeMovementFlags(dragFlags, swipeFlags);
                }

            }


            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                int fromPosition = viewHolder.getAdapterPosition();//得到拖动ViewHolder的position
                int toPosition = target.getAdapterPosition();//得到目标ViewHolder的position
                if (fromPosition==0||toPosition==0){
                    return false;
                }
                if (fromPosition < toPosition) {
                    for (int i = fromPosition; i < toPosition; i++) {
                        Collections.swap(CacheManager.getInstance().getOnList(), i, i + 1);
                    }
                } else {
                    for (int i = fromPosition; i > toPosition; i--) {
                        Collections.swap(CacheManager.getInstance().getOnList(), i, i - 1);
                    }
                }
                adapter1.notifyItemMoved(fromPosition, toPosition);
                return true;

            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            }
        });
        itemTouchHelper.attachToRecyclerView(channelOnSelect);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_channel;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isEdit=true;
        edit();
        finish();
    }

}
