package com.example.framework2.cache.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NewEntity {
    @Id(autoincrement = true)
    long id;
    private String code;
    private long time;
    private String jsonStr;
    @Generated(hash = 1535269073)
    public NewEntity(long id, String code, long time, String jsonStr) {
        this.id = id;
        this.code = code;
        this.time = time;
        this.jsonStr = jsonStr;
    }
    @Generated(hash = 957491840)
    public NewEntity() {
    }
    public long getId() {
        return this.id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public long getTime() {
        return this.time;
    }
    public void setTime(long time) {
        this.time = time;
    }
    public String getJsonStr() {
        return this.jsonStr;
    }
    public void setJsonStr(String jsonStr) {
        this.jsonStr = jsonStr;
    }
}
