package com.example.video.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class SqlBean {
    @Id
    public Long id;
    private String title;
    private String videoJson;

    @Generated(hash = 897844403)
    public SqlBean(Long id, String title, String videoJson) {
        this.id = id;
        this.title = title;
        this.videoJson = videoJson;
    }

    @Generated(hash = 2066760633)
    public SqlBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoJson() {
        return this.videoJson;
    }

    public void setVideoJson(String videoJson) {
        this.videoJson = videoJson;
    }

}
