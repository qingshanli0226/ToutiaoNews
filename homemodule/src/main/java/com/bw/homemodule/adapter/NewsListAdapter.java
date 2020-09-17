package com.bw.homemodule.adapter;

import android.text.TextUtils;
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
    public static final int TEXT_NEWS = 100;
    /**
     * 居中大图布局(1.单图文章；2.单图广告；3.视频，中间显示播放图标，右侧显示时长)
     */
    public static final int CENTER_SINGLE_PIC_NEWS = 200;
    /**
     * 右侧小图布局(1.小图新闻；2.视频类型，右下角显示视频时长)
     */
    public static final int RIGHT_PIC_VIDEO_NEWS = 300;
    /**
     * 三张图片布局(文章、广告)
     */
    public static final int THREE_PICS_NEWS = 400;

    public NewsListAdapter(List<News> data) {
        super(data);
        addItemType(TEXT_NEWS, R.layout.text_news_layout);
        addItemType(RIGHT_PIC_VIDEO_NEWS, R.layout.right_pic_video_new_layouts);
    }

    @Override
    protected void convert(BaseViewHolder helper, News item) {
        if (item.has_video) {
            //如果有视频
            if (item.video_style == 0) {
                //右侧视频
                if (item.middle_image == null || TextUtils.isEmpty(item.middle_image.url)) {
                    item.itemType = TEXT_NEWS;
                    textNews(helper, item);
                    return;
                }
                item.itemType = RIGHT_PIC_VIDEO_NEWS;
                rightPicVideoNews(helper, item);
                return;
            } else if (item.video_style == 2) {
                //居中视频
            }
        } else {
            //非视频新闻
            if (!item.has_image) {
                //纯文字新闻
                item.itemType = TEXT_NEWS;
                textNews(helper, item);
                return;
            } else {
                if (item.image_list==null||item.image_list.size()==0) {
                    //图片列表为空，则是右侧图片
                    item.itemType = RIGHT_PIC_VIDEO_NEWS;
                    rightPicVideoNews(helper, item);
                    return;
                }

                if (item.gallary_image_count == 3) {
                    //图片数为3，则为三图
                }

                //中间大图，右下角显示图数
            }
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
        helper.setText(R.id.tv_title, news.title + "");
        helper.setText(R.id.tv_author, news.source + "");
        helper.setText(R.id.tv_comment_num, news.comment_count + "评论");
        helper.setText(R.id.tv_time, news.behot_time + "");

//        if (news.middle_image!=null){
//            Glide.with(mContext).load(news.middle_image.url).fallback(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher_round).into((ImageView) helper.getView(R.id.tv_image));
//        }

//        Glide.with(mContext).load("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=3469017704,4035622631&fm=26&gp=0.jpg").into((ImageView) helper.getView(R.id.tv_img));
    }
}
