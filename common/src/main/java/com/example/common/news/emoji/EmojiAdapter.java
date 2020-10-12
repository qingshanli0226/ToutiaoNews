package com.example.common.news.emoji;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import com.example.common.R;

import java.util.List;

public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.MyViewHolder> {
    private List<EmojiEntity> emojiEntityList;

    public EmojiAdapter(List<EmojiEntity> emojiEntityList) {
        this.emojiEntityList = emojiEntityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoji, parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvEmoji.setText(emojiEntityList.get(position).getUnicode());
    }

    @Override
    public int getItemCount() {
        return emojiEntityList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmoji;

        public MyViewHolder(View itemView) {
            super(itemView);
            tvEmoji = itemView.findViewById(R.id.tv_emoji);
        }
    }
}
