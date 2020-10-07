package com.example.common.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Update;

@Dao
public interface  NewsDao {
    //插入
    @Insert
    void insert(NewsBean... newsBeans);

    //更新
    @Update
    void update(NewsBean... newsBeans);

    //删除
    @Delete
    void delete(NewsBean... newsBeans);
}
