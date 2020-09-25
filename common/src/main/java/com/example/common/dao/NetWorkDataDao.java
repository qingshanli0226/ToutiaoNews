package com.example.common.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
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
    @Query("SELECT DISTINCT * FROM  NetWorkTable")
    List<NetWorkDataEntity> getAllData();


    /*------------添加------------*/
    //添加数据
    //封装了Room解决冲突的相关策略
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(NetWorkDataEntity netWorkDataEntities);


    /*------------更新&x修改------------*/
    //更新某一项
    @Update
    void update(NetWorkDataEntity... netWorkDataEntities);

    /*------------删除------------*/
    //删除某一项
    @Delete
    void delete(NetWorkDataEntity... netWorkDataEntities);

    //删除全部
    @Query("DELETE FROM NetWorkTable")
    void deleteAll();

    //删除某一页的数据
    @Query("DELETE FROM NetWorkTable WHERE channelCode=:channelCode")
    void deleteCodeData(String channelCode);


}
