package com.example.common.untils;

import com.example.common.mode.News;

public class ContentBeanUntil {

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

    public synchronized static void setItemType(News news){
        if(news.isHas_video()){
            //有视频
            if(news.getVideo_style() == 0){
                //右侧的视频
                if(news.middle_image == null || news.middle_image.toString().equals("")){
                    news.setItemType(TEXT_NEWS);
                    return;
                }
                news.setItemType(RIGHT_PIC_VIDEO_NEWS);
                return;
            } else if(news.getVideo_style() == 2){
                //居中的视频
                news.setItemType(CENTER_SINGLE_PIC_NEWS);
                return;
            }
        } else{
            //没有视频 非视频新闻
            if(!news.isHas_image()){
                //纯文字新闻
                news.setItemType(TEXT_NEWS);
                return;
            } else{
                if(news.image_list == null || news.image_list.size() == 0){
                    //图片列表为空 是右侧图片
                    news.setItemType(RIGHT_PIC_VIDEO_NEWS);
                    return;
                }

                if(news.getGallary_image_count() == 3){
                    //是3图的类型
                    news.setItemType(THREE_PICS_NEWS);
                    return;
                }

                //不然就是中间大图 右下角显示图片
                news.setItemType(CENTER_SINGLE_PIC_NEWS);
                return;
            }
        }
        news.setItemType(TEXT_NEWS);
        return;
    }

}
