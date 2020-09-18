package com.example.common.dao;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 网络请求数据实体类
 */
@Entity
public class NetWorkDataEntity {

    @PrimaryKey(autoGenerate = true)//设置主键自增
    private int id;//主键id


    private String videoTitle;//视频标题
    private int videoCommentCount;//视频评论
    private String userInfoName;//作者昵称
    private String userAvatarUrl;//作者头像
    private String videoLargeImg;//视频缩略图

    public String getVideoLargeImg() {
        return videoLargeImg;
    }

    public void setVideoLargeImg(String videoLargeImg) {
        this.videoLargeImg = videoLargeImg;
    }

    public String getVideoTitle() {
        return videoTitle;
    }

    public void setVideoTitle(String videoTitle) {
        this.videoTitle = videoTitle;
    }

    public int getVideoCommentCount() {
        return videoCommentCount;
    }

    public void setVideoCommentCount(int videoCommentCount) {
        this.videoCommentCount = videoCommentCount;
    }

    public String getUserInfoName() {
        return userInfoName;
    }

    public void setUserInfoName(String userInfoName) {
        this.userInfoName = userInfoName;
    }

    public String getUserAvatarUrl() {
        return userAvatarUrl;
    }

    public void setUserAvatarUrl(String userAvatarUrl) {
        this.userAvatarUrl = userAvatarUrl;
    }



    //图片地址
    private String imgUrl;
    //Web地址
    private String webUrl;
    //视频地址
    private String videoUrl;
    //Json地址
    private String jsonUrl;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }
}
