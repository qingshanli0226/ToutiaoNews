package com.example.common.dao;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(entities = NewsRoomBean.class,version = 1,exportSchema = false)
public abstract class NewsDatabeans extends RoomDatabase {

    private static final String DB_NMAE = "TwoGroup";
    private static NewsDatabeans instance;

    public static synchronized NewsDatabeans getInstance(Context context){
        if (instance == null){
            instance = create(context);
        }

        return instance;
    }

    private static NewsDatabeans create(Context context) {
        return Room.databaseBuilder(context, NewsDatabeans.class, DB_NMAE)
                .allowMainThreadQueries()
                .build();
    }


    public abstract NewsDao getNewsDao();
}
