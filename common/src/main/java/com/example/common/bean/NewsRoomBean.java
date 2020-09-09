package com.example.common.bean;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "news_table")
public class NewsRoomBean {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "newsImageUrl")
    private String newsImageUrl;

    @ColumnInfo(name = "newsTitle")
    private String newTitle;

    @ColumnInfo(name = "newsName")
    private String newsName;

    @ColumnInfo(name = "newsTime")
    private long newsTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
