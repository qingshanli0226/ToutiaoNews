package com.example.toutiaonews.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

/**
 * 数据访问对象
 */
@Dao
public interface NetWorkDataDao {

    /*------------查询------------*/
    //返回整个实体类集合
    //查询所有
    @Query("SELECT DISTINCT * FROM  networkdataentity")
    List<NetWorkDataEntity> getAllData();

    //查询图片地址---返回图片地址
    @Query("SELECT DISTINCT * FROM networkdataentity WHERE imgUrl=:imgUrl")
    NetWorkDataEntity getImgUrl(String imgUrl);

    //查询网页地址---返回网页地址
    @Query("SELECT DISTINCT * FROM networkdataentity WHERE webUrl=:webUrl")
    NetWorkDataEntity getWebUrl(String webUrl);

    //查询视频地址---返回视频地址
    @Query("SELECT DISTINCT * FROM networkdataentity WHERE vidoeUrl=:vidoeUrl")
    NetWorkDataEntity getVideoUrl(String vidoeUrl);

    //查询Json地址---返回Json地址
    @Query("SELECT DISTINCT * FROM networkdataentity WHERE jsonUrl=:jsonUrl")
    NetWorkDataEntity getJsonUrl(String jsonUrl);


    /*------------添加------------*/

    //添加数据
    @Insert
    void inster(NetWorkDataEntity... netWorkDataEntities);

    //添加对象
    @Insert
    void insertData(NetWorkDataEntity netWorkDataEntity);

    //添加对象集合
    @Insert
    void insertList(List<NetWorkDataEntity> netWorkDataEntities);

    /*------------更新&x修改------------*/
    //更新某一项
    @Update
    void update(NetWorkDataEntity... netWorkDataEntities);

    /*------------删除------------*/
    //删除某一项
    @Delete
    void delete(NetWorkDataEntity... netWorkDataEntities);

    //删除全部
    @Query("DELETE FROM networkdataentity")
    void deleteAll();

}
