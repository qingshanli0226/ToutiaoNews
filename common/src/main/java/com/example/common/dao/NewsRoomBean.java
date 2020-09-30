package com.example.common.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class NewsRoomBean {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "Id")
    private int id;

    @ColumnInfo(name = "channelId")
    private String channelId;

    @ColumnInfo(name = "newsImageUrl")
    private String newsImageUrl;

    @ColumnInfo(name = "newsThumImage")
    private String newsThumImage;

    @ColumnInfo(name = "newsTitle")
    private String newTitle;

    @ColumnInfo(name = "newsName")
    private String newsName;

    @ColumnInfo(name = "newsTime")
    private long newsTime;

    @ColumnInfo(name = "newsCommon")
    private int newsCommon;

    @ColumnInfo(name = "newsCount")
    private int newsCount = 0;

    public int getNewsCount() {
        return newsCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNewsCount(int newsCount) {
        this.newsCount = newsCount;
    }

    public int getNewsCommon() {
        return newsCommon;
    }

    public void setNewsCommon(int newsCommon) {
        this.newsCommon = newsCommon;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getNewsThumImage() {
        return newsThumImage;
    }

    public void setNewsThumImage(String newsThumImage) {
        this.newsThumImage = newsThumImage;
    }

    public String getNewsImageUrl() {
        return newsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        this.newsImageUrl = newsImageUrl;
    }

    public String getNewTitle() {
        return newTitle;
    }

    public void setNewTitle(String newTitle) {
        this.newTitle = newTitle;
    }

    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    public long getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(long newsTime) {
        this.newsTime = newsTime;
    }
}
