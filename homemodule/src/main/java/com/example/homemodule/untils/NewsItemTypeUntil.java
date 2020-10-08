package com.example.homemodule.untils;

import android.text.TextUtils;

import com.example.common.entity.News;

/**
 *  处理item多布局的区分
 */
public class NewsItemTypeUntil {

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

    public synchronized static void ChangeItemType(News news) {
        if (news.has_video) {
            //如果有视频
            if (news.video_style == 0) {
                //右侧视频
                if (news.middle_image == null || TextUtils.isEmpty(news.middle_image.url)) {
                    news.itemType = TEXT_NEWS;
                    return;
                }

                news.itemType = RIGHT_PIC_VIDEO_NEWS;
                return;
            } else if (news.video_style == 2) {
                //居中视频
                news.itemType = CENTER_SINGLE_PIC_NEWS;
                return;
            }
        } else {
            //非视频新闻
            if (!news.has_image) {
                //纯文字新闻
                news.itemType = TEXT_NEWS;
                return;
            } else {
                if (news.image_list == null || news.image_list.size() == 0) {
                    //图片列表为空，则是右侧图片
                    news.itemType = RIGHT_PIC_VIDEO_NEWS;
                    return;
                }

                if (news.gallary_image_count == 3) {
                    //图片数为3，则为三图
                    news.itemType = THREE_PICS_NEWS;
                    return;
                }

                //中间大图，右下角显示图数
                news.itemType = CENTER_SINGLE_PIC_NEWS;
                return;
            }
        }

        news.itemType = TEXT_NEWS;
        return;
    }

}
