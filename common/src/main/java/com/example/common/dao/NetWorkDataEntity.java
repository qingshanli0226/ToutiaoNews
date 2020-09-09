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

    //图片地址
    private String imgUrl;
    //Web地址
    private String webUrl;
    //视频地址
    private String vidoeUrl;
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

    public String getVidoeUrl() {
        return vidoeUrl;
    }

    public void setVidoeUrl(String vidoeUrl) {
        this.vidoeUrl = vidoeUrl;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }
}
