package com.example.common.mode;

import java.util.List;

/**
 * @author ChayChan
 * @description: 视频实体类
 * @date 2017/7/9  10:44
 */

public class VideoEntity {
    /**
     * group_flags : 32832
     * video_type : 0
     * video_preloading_flag : 1
     * video_url : []
     * direct_play : 1
     * detail_video_large_image : {"url":"http://p1.pstatp.com/video1609/2130000392cc3ddb8076","width":580,"url_list":[{"url":"http://p1.pstatp.com/video1609/2130000392cc3ddb8076"},{"url":"http://pb3.pstatp.com/video1609/2130000392cc3ddb8076"},{"url":"http://pb9.pstatp.com/video1609/2130000392cc3ddb8076"}],"uri":"video1609/2130000392cc3ddb8076","height":326}
     * show_pgc_subscribe : 1
     * video_third_monitor_url :
     * video_id : eb0eab0d76274b13a3fd0649ba1d0f74
     * video_watching_count : 0
     * video_watch_count : 657298
     */

    public int group_flags;
    public int video_type;
    public int video_preloading_flag;
    public int direct_play;
    public ImageEntity detail_video_large_image;
    public int show_pgc_subscribe;
    public String video_third_monitor_url;
    public String video_id;
    public int video_watching_count;
    public int video_watch_count;
    public List<?> video_url;
    //自己新增的字段，记录视频播放的进度，用于同步视频列表也和详情页的进度
    public long progress;
    public String parse_video_url; //解析出来的视频地址

    public int getGroup_flags() {
        return group_flags;
    }

    public void setGroup_flags(int group_flags) {
        this.group_flags = group_flags;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getVideo_preloading_flag() {
        return video_preloading_flag;
    }

    public void setVideo_preloading_flag(int video_preloading_flag) {
        this.video_preloading_flag = video_preloading_flag;
    }

    public int getDirect_play() {
        return direct_play;
    }

    public void setDirect_play(int direct_play) {
        this.direct_play = direct_play;
    }

    public ImageEntity getDetail_video_large_image() {
        return detail_video_large_image;
    }

    public void setDetail_video_large_image(ImageEntity detail_video_large_image) {
        this.detail_video_large_image = detail_video_large_image;
    }

    public int getShow_pgc_subscribe() {
        return show_pgc_subscribe;
    }

    public void setShow_pgc_subscribe(int show_pgc_subscribe) {
        this.show_pgc_subscribe = show_pgc_subscribe;
    }

    public String getVideo_third_monitor_url() {
        return video_third_monitor_url;
    }

    public void setVideo_third_monitor_url(String video_third_monitor_url) {
        this.video_third_monitor_url = video_third_monitor_url;
    }

    public String getVideo_id() {
        return video_id;
    }

    public void setVideo_id(String video_id) {
        this.video_id = video_id;
    }

    public int getVideo_watching_count() {
        return video_watching_count;
    }

    public void setVideo_watching_count(int video_watching_count) {
        this.video_watching_count = video_watching_count;
    }

    public int getVideo_watch_count() {
        return video_watch_count;
    }

    public void setVideo_watch_count(int video_watch_count) {
        this.video_watch_count = video_watch_count;
    }

    public List<?> getVideo_url() {
        return video_url;
    }

    public void setVideo_url(List<?> video_url) {
        this.video_url = video_url;
    }

    public long getProgress() {
        return progress;
    }

    public void setProgress(long progress) {
        this.progress = progress;
    }

    public String getParse_video_url() {
        return parse_video_url;
    }

    public void setParse_video_url(String parse_video_url) {
        this.parse_video_url = parse_video_url;
    }
}
