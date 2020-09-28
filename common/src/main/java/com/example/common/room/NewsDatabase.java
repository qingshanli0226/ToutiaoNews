package com.example.common.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
@Database(entities = {NewsBean.class},version = 1,exportSchema = false)
public abstract class NewsDatabase extends RoomDatabase {
    private static final String DB_NAME = "NewsDatabase.db";
    private static volatile NewsDatabase instance;

    static synchronized NewsDatabase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static NewsDatabase create(final Context context) {
        return Room.databaseBuilder(context, NewsDatabase.class, DB_NAME).build();
    }

    public abstract NewsDao getUserDao();
}
