package com.example.common.dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.common.bean.NewsRoomBean;

import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news_table")
    List<NewsRoomBean> getAll();

    @Query("SELECT * FROM news_table WHERE id =:sId")
    NewsRoomBean getNewsBean(int sId);

    @Insert
    void insertAll(NewsRoomBean... newsRoomBeans);

    @Update
    void updateStudent(NewsRoomBean... newsRoomBeans);

    @Delete
    void delete(NewsRoomBean... newsRoomBeans);
}
