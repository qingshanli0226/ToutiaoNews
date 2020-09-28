package com.example.common.room;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class NewsBean {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    private int id;
    private String shareIma;
    private String userInfo;
    private String userInfoUsername;
    private String followerCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShareIma() {
        return shareIma;
    }

    public void setShareIma(String shareIma) {
        this.shareIma = shareIma;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getUserInfoUsername() {
        return userInfoUsername;
    }

    public void setUserInfoUsername(String userInfoUsername) {
        this.userInfoUsername = userInfoUsername;
    }

    public String getFollowerCount() {
        return followerCount;
    }

    public void setFollowerCount(String followerCount) {
        this.followerCount = followerCount;
    }
}
