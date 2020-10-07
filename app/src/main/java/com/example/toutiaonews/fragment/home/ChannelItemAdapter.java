package com.example.toutiaonews.fragment.home;

import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.net.activity_bean.entity.News;
import com.example.toutiaonews.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ChannelItemAdapter extends BaseMultiItemQuickAdapter<News, BaseViewHolder> {


    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChannelItemAdapter(List<News> data) {
        super(data);
        addItemType(100, R.layout.channel_item_text);
        addItemType(200, R.layout.channel_item_center);
        addItemType(300, R.layout.channel_item_right);
        addItemType(400, R.layout.channel_item_three);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        switch (helper.getItemViewType()){
            case 100:
                addText(helper,item);
                break;
            case 200:
                addText(helper,item);
                addCenterImg(helper,item);
                break;
            case 300:
                addText(helper,item);
                addRightImg(helper,item);
                break;
            case 400:
                addText(helper,item);
                addThreeImg(helper,item);
                break;
        }
    }

    private void addThreeImg(BaseViewHolder helper, News item) {
        Glide.with(mContext).load(item.image_list.get(0).url).into((ImageView) helper.getView(R.id.channel_item_img_one));
        Glide.with(mContext).load(item.image_list.get(1).url).into((ImageView) helper.getView(R.id.channel_item_img_two));
        Glide.with(mContext).load(item.image_list.get(2).url).into((ImageView) helper.getView(R.id.channel_item_img_three));
    }

    private void addRightImg(BaseViewHolder helper, News item) {
        ImageView right = helper.getView(R.id.channel_item_right);
        if (item.has_video){
            helper.setText(R.id.channel_item_playtime,"▶"+secToTime(item.video_duration));
        }else {
//            helper.setText(R.id.channel_item_playtime,item.gallary_image_count+"图");
            helper.setVisible(R.id.channel_item_playtime,false);
        }
        Glide.with(mContext).load(item.middle_image.url).into(right);
    }

    public static String secToTime(int time) {

        String timeStr = null;
        int hour = time / 3600;
        int minute = time / 60 % 60;
        int second = time % 60;
        timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
        return timeStr;
    }
    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    private void addCenterImg(BaseViewHolder helper, News item) {
        ImageView play = helper.getView(R.id.channel_item_center);
        if (item.has_video){
            helper.setVisible(R.id.channel_item_play,true);
            helper.setText(R.id.channel_item_img_number,"▶"+secToTime(item.video_duration));
            Glide.with(mContext).load(item.video_detail_info.detail_video_large_image.url).into(play);
        }else {
            helper.setVisible(R.id.channel_item_play,false);
            helper.setText(R.id.channel_item_img_number,item.gallary_image_count+"图");
            Glide.with(mContext).load(item.image_list.get(0).url).into(play);
        }
    }


    public String getLongToString(long longTime){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = sdf.format(longTime);
        return time;
    }
    private void addText(BaseViewHolder helper, News item) {
        helper.setText(R.id.channel_item_title,item.title);
        helper.setText(R.id.channel_item_author,item.source);
        helper.setText(R.id.channel_item_common,item.comment_count+"评论");
        helper.setText(R.id.channel_item_datetime,getLongToString(item.behot_time));
        helper.addOnClickListener(R.id.channel_item_center_linear);
        helper.addOnClickListener(R.id.channel_item_right_linear);
        helper.addOnClickListener(R.id.channel_item_three_linear);
        helper.addOnClickListener(R.id.channel_itemt_text_linear);


    }

    @Override
    public int getItemViewType(int position) {
        return getData().get(position).getItemType();
    }
}
