package com.bw.homemodule.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.homemodule.R;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.common.entity.News;

import java.util.List;

public class NewsListAdapter extends BaseMultiItemQuickAdapter<News, BaseViewHolder> {

    /**
     * 纯文字布局(文章、广告)
     */
    public static final int TEXT_NEWS = 1;
    /**
     * 居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
     */
    public static final int CENTER_SINGLE_PIC_NEWS = 2;
    /**
     * 右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    public static final int RIGHT_PIC_VIDEO_NEWS = 3;
    /**
     * 三张图片布局(文章、广告)
     */
    public static final int THREE_PICS_NEWS = 4;

    public NewsListAdapter(List<News> data) {
        super(data);
        addItemType(TEXT_NEWS, R.layout.text_news_layout);
        addItemType(RIGHT_PIC_VIDEO_NEWS, R.layout.right_pic_video_new_layouts);
        addItemType(CENTER_SINGLE_PIC_NEWS, R.layout.center_single_pic_news_layout);
        addItemType(THREE_PICS_NEWS, R.layout.three_pics_news_layout);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        int itemType = item.getItemType();

        if (itemType == TEXT_NEWS) {
            textNews(helper, item);
        } else if (itemType == CENTER_SINGLE_PIC_NEWS) {
            textNews(helper, item);
            centerSinglePicNews(helper, item);
        } else if (itemType == RIGHT_PIC_VIDEO_NEWS) {
            textNews(helper, item);
            rightPicVideoNews(helper, item);
        } else if (itemType == THREE_PICS_NEWS) {
            textNews(helper, item);
            threePicNews(helper, item);
        }

    }


    /**
     * 纯文字布局(文章、广告)
     */
    private void textNews(BaseViewHolder helper, News item) {
        helper.setText(R.id.tv_title, item.title + "");
        helper.setText(R.id.tv_author, item.source + "");
        helper.setText(R.id.tv_comment_num, item.comment_count + "评论");
        helper.setText(R.id.tv_time, item.behot_time + "");
    }


    /**
     * 右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    private void rightPicVideoNews(BaseViewHolder helper, News news) {
        Glide.with(mContext).load(news.middle_image.url)
                .fallback(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into((ImageView) helper.getView(R.id.tv_img));

        //右侧小图布局，判断是否有视频
        if (news.has_video) {
            helper.setVisible(R.id.tv_show_time, true);
            helper.setText(R.id.tv_show_time, "< " + news.video_duration);
        } else {
            helper.setVisible(R.id.tv_show_time, false);
        }
    }


    /**
     * 居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
     */
    private void centerSinglePicNews(BaseViewHolder helper, News news) {
        if (news.has_video){
            helper.setVisible(R.id.prompt_tv,false);
            helper.setVisible(R.id.play_icon,true);
            Glide.with(mContext).load(news.video_detail_info.detail_video_large_image.url).into((ImageView) helper.getView(R.id.center_pic));
        }else {
            helper.setVisible(R.id.prompt_tv,true);
            helper.setVisible(R.id.play_icon,false);
            helper.setText(R.id.prompt_tv,news.gallary_image_count+"图");
            Glide.with(mContext).load(news.image_list.get(0).url).into((ImageView) helper.getView(R.id.center_pic));
        }

    }

    private void threePicNews(BaseViewHolder helper,News news){
        Glide.with(mContext).load(news.image_list.get(0).url).into((ImageView) helper.getView(R.id.iv_img1));
        Glide.with(mContext).load(news.image_list.get(1).url).into((ImageView) helper.getView(R.id.iv_img2));
        Glide.with(mContext).load(news.image_list.get(2).url).into((ImageView) helper.getView(R.id.iv_img3));
    }

}
