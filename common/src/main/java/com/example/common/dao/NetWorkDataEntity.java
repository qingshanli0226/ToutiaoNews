package com.example.common.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

/**
 * 网络请求数据实体类
 */
@Entity(tableName = "NetWorkTable", primaryKeys = {"dataTime", "channelCode"})
public class NetWorkDataEntity {

    //时间戳
    @NonNull
    private long dataTime;

    //列表名称
    @NonNull
    private String channelCode;

    //数据标题字段用于查重
    @ColumnInfo(name = "title")
    private String title;

    //Json地址
    @ColumnInfo(name = "jsonUrl")
    private String jsonUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public long getDataTime() {
        return dataTime;
    }

    public void setDataTime(long dataTime) {
        this.dataTime = dataTime;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }
}
