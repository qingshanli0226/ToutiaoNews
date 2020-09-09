package com.example.toutiaonews.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

/**
 * 数据库
 */
@Database(entities = {NetWorkDataEntity.class}, version = 1, exportSchema = false)
public abstract class NetWorkDatabase extends RoomDatabase {

    private static final String DB_NAME = "NetWorkDatabase.db";//数据库名
    private static volatile NetWorkDatabase instance;

    public static synchronized NetWorkDatabase getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }
        return instance;
    }

    private static NetWorkDatabase create(Context context) {
        return Room.databaseBuilder(
                context,
                NetWorkDatabase.class,
                DB_NAME)
                .allowMainThreadQueries()//可以在主线程直接编写
                .build();
    }

    //提供Dao里的接口方法
    public abstract NetWorkDataDao getNetWorkDataDao();

}
