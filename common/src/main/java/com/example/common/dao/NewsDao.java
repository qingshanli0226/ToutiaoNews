package com.example.common.dao;



import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import java.util.List;

@Dao
public interface NewsDao {
    @Query("SELECT * FROM news_table")
    List<NewsRoomBean> getAll();

    @Query("SELECT * FROM news_table WHERE channelId =:channelId")
    NewsRoomBean getNewsBean(String channelId);

    @Insert
    void insertNews(NewsRoomBean... newsRoomBeans);

    @Update
    void updateNews(NewsRoomBean... newsRoomBeans);

    @Delete
    void delete(NewsRoomBean... newsRoomBeans);


    @Query("DELETE FROM news_table WHERE newsTime =:newsTime")
    void deleteNewsBean(long newsTime);

}
