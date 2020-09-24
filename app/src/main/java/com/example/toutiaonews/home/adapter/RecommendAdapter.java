package com.example.toutiaonews.home.adapter;


import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.mode.News;
import com.example.toutiaonews.R;

import java.util.List;


public class RecommendAdapter extends BaseMultiItemQuickAdapter<News, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public RecommendAdapter(List<News> data) {
        super(data);
        addItemType(1, R.layout.item_home_text_news);
        addItemType(2, R.layout.item_home_center_single_pic_news_layout);
        addItemType(3, R.layout.item_home_right_pic_video_new_layouts);
        addItemType(4, R.layout.item_home_three_pics_news_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        switch (item.getItemType()){
            case 1:
                DataOneAdapter(helper,item);
                break;
            case 2:
                DataTwoAdapter(helper,item);
                break;
            case 3:
                DataThreeAdapter(helper,item);
                break;
            case 4:
                DataFourAdapter(helper,item);
                break;
        }
    }

    private void DataFourAdapter(BaseViewHolder helper, News item) {
        helper.setText(R.id.threeTitle,item.getTitle());
        Glide.with(mContext).load(item.getImage_list().get(0).url).into((ImageView) helper.getView(R.id.threeImgOne));
        Glide.with(mContext).load(item.getImage_list().get(1).url).into((ImageView) helper.getView(R.id.threeImgTwo));
        Glide.with(mContext).load(item.getImage_list().get(2).url).into((ImageView) helper.getView(R.id.threeImgThree));
        helper.setText(R.id.threeAuthor,item.getSource());
        helper.setText(R.id.threeRecommendCount,item.getComment_count()+"");
//        helper.setText(R.id.threeTime,item.getBehot_time()+"");
    }

    private void DataThreeAdapter(BaseViewHolder helper, News item) {
        helper.setText(R.id.rightTitle,item.getTitle());
        helper.setText(R.id.rightAuthor,item.getSource());
        helper.setText(R.id.rightCommentCount,item.getComment_count()+"");
//        helper.setText(R.id.rightTime,item.getBehot_time()+"");
        Glide.with(mContext).load(item.getMiddle_image().url).into((ImageView) helper.getView(R.id.rightImg));
//        helper.setText(R.id.rightTime, TimeUntil.getStringTime(item.getVideo_duration()));
    }

    private void DataTwoAdapter(BaseViewHolder helper, News item) {
        helper.setText(R.id.centerCommentCount,item.getComment_count()+"");
        helper.setText(R.id.centerTitle,item.getTitle());
//        Glide.with(mContext).load(item.getVideo_detail_info().getDetail_video_large_image().url).into((ImageView) helper.getView(R.id.centerImg));
        Glide.with(mContext).load(item.getMiddle_image().url).into((ImageView) helper.getView(R.id.centerImg));
        helper.setText(R.id.centerPrompt,item.getGallary_image_count()+"图");
        helper.setText(R.id.centerAuthor,item.getSource());
//        helper.setText(R.id.centerTime,item.getBehot_time()+"");
    }

    private void DataOneAdapter(BaseViewHolder helper, News item) {
        helper.setText(R.id.textSource,item.getSource());
        helper.setText(R.id.textRecommendCount,item.getComment_count()+"");
        helper.setText(R.id.textTitle,item.getTitle());
        if(helper.getAdapterPosition() >= 1){
            helper.getView(R.id.textImg).setVisibility(View.GONE);
        } else{
            helper.getView(R.id.textImg).setVisibility(View.VISIBLE);
        }
    }
}
