package com.example.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.home.R;
import com.example.net.bean.ContentBean;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private Context context;
    private ArrayList<ContentBean> list;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.item01TvTitleId.setText(list.get(position).getAbstractX()+"");

        holder.item01TvAddressId.setText(list.get(position).getMedia_name()+"");
        if (position != 0){
            holder.item01TvToTopId.setVisibility(View.INVISIBLE);
        }else {
            holder.item01TvToTopId.setVisibility(View.VISIBLE);
        }
        holder.item01TvPlNumId.setText(list.get(position).getRepin_count()+"");
        holder.item01TvTimeId.setText("刚刚");

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
