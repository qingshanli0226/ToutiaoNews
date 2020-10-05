package com.example.common.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class NewsRoomBean {

    @ColumnInfo(name = "channelId")
    private String channelId;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "JsonUrl")
    private String JsonUrl;

    @ColumnInfo(name = "newsTime")
    private long newsTime;

    public long getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(long newsTime) {
        this.newsTime = newsTime;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @NonNull
    public String getJsonUrl() {
        return JsonUrl;
    }

    public void setJsonUrl(@NonNull String jsonUrl) {
        JsonUrl = jsonUrl;
    }
}
