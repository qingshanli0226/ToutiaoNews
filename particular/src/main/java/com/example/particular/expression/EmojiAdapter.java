package com.example.particular.expression;


import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.particular.R;

import java.util.List;

public class EmojiAdapter extends BaseQuickAdapter<EmojiEntity, BaseViewHolder> {

    public EmojiAdapter(int layoutResId, @Nullable List<EmojiEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, EmojiEntity item) {
        helper.setText(R.id.tv_emoji,item.getUnicode());
    }
}
//public class EmojiAdapter extends RecyclerView.Adapter<EmojiAdapter.MyViewHolder> {
//    private List<EmojiEntity> emojiEntityList;
//
//    public EmojiAdapter(List<EmojiEntity> emojiEntityList) {
//        this.emojiEntityList = emojiEntityList;
//    }
//
//    @Override
//    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_emoji, parent, false));
//    }
//
//    @Override
//    public void onBindViewHolder(MyViewHolder holder, int position) {
//        holder.tvEmoji.setText(emojiEntityList.get(position).getUnicode());
//    }
//
//    @Override
//    public int getItemCount() {
//        return emojiEntityList.size();
//    }
//
//    class MyViewHolder extends RecyclerView.ViewHolder {
//        TextView tvEmoji;
//
//        public MyViewHolder(View itemView) {
//            super(itemView);
//            tvEmoji = itemView.findViewById(R.id.tv_emoji);
//        }
//    }
//}
