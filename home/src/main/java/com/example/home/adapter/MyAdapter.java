package com.example.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.R;
import com.example.home.view.FragmentRecommend;
import com.example.net.bean.ContentBean;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ContentBean> list;

    //点击事件
    //第一步 定义接口
    public interface OnItemClickListener {
        void onClick(int position);
    }

    private OnItemClickListener listener;

    //第二步， 写一个公共的方法
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    //长按点击
    public interface OnItemLongClickListener {
        void onClick(int position);
    }

    private OnItemLongClickListener longClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public MyAdapter(Context context, ArrayList<ContentBean> list) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout01,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.item01TvTitleId.setText(list.get(position).getAbstractX()+"");

        holder.item01TvAddressId.setText(list.get(position).getMedia_name()+"");
        if (position != 0){
            holder.item01TvToTopId.setVisibility(View.INVISIBLE);
        }else {
            holder.item01TvToTopId.setVisibility(View.VISIBLE);
        }
        holder.item01TvPlNumId.setText(list.get(position).getRepin_count()+"");
        holder.item01TvTimeId.setText("刚刚");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (longClickListener != null) {
                    longClickListener.onClick(position);
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item01TvTitleId;
        TextView item01TvToTopId;
        TextView item01TvAddressId;
        TextView item01TvPlNumId;
        TextView item01TvTimeId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item01TvTitleId = itemView.findViewById(R.id.item01_tv_title_id);
            item01TvToTopId = itemView.findViewById(R.id.item01_tv_toTop_id);
            item01TvAddressId = itemView.findViewById(R.id.item01_tv_address_id);
            item01TvPlNumId = itemView.findViewById(R.id.item01_tv_plNum_id);
            item01TvTimeId = itemView.findViewById(R.id.item01_tv_time_id);
        }
    }
}
