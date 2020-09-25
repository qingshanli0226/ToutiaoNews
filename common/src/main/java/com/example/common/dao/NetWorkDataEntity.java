package com.example.common.dao;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * 网络请求数据实体类
 */
@Entity(tableName = "NetWorkTable")
public class NetWorkDataEntity {


    //列表名称
    @ColumnInfo(name = "channelCode")
    private String channelCode;

    //Json地址
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "jsonUrl")
    private String jsonUrl;


    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getJsonUrl() {
        return jsonUrl;
    }

    public void setJsonUrl(String jsonUrl) {
        this.jsonUrl = jsonUrl;
    }
}
