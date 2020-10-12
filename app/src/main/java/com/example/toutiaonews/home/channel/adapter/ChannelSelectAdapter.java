package com.example.toutiaonews.home.channel.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.toutiaonews.R;

import java.util.List;

public class ChannelSelectAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    private ChannelSelectInterface channelSelectInterface;

    public ChannelSelectAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.itemChannelSelectTitle,item);
        //获取是否是编辑状态的boolean
        boolean isCompile = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISCOMPILE);
        if(isCompile && helper.getAdapterPosition() > 0){
            //是编辑状态 显示
            helper.getView(R.id.itemChannelSelectClose).setVisibility(View.VISIBLE);
        } else{
            //不是编辑状态 隐藏
            helper.getView(R.id.itemChannelSelectClose).setVisibility(View.GONE);
        }
        //用接口回调的方式在channelActivity中处理
        ImageView view = helper.getView(R.id.itemChannelSelectClose);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                channelSelectInterface.itemSelect(helper.getAdapterPosition());
            }
        });
    }

    public interface ChannelSelectInterface{
        void itemSelect(int position);
    }

    public void setChannelSelectInterface(ChannelSelectInterface channelSelectInterface){
        this.channelSelectInterface = channelSelectInterface;
    }

}
