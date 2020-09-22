package com.example.framework2.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Tools {
    private Context context;
    private static Tools tools;
    private SharedPreferences.Editor edito;
    private SharedPreferences video;

    public static Tools getTools() {
        if (tools == null) {
            tools = new Tools();
        }
        return tools;
    }

    public void setContext(Context context) {
        this.context = context;
        initVideoSp();
    }

    @SuppressLint("CommitPrefEdits")
    private void initVideoSp() {
        video = context.getSharedPreferences("channel_code_video", Context.MODE_PRIVATE);
        edito = video.edit();
    }



    public boolean putVideoTime(String str, Long time) {
        edito.putLong(str, time);
        return edito.commit();
    }

    public long getVideoTime(String str) {

        return video.getLong(str, 0L);
    }

    public boolean putVideoCode(String str) {

        edito.putString(str, str);

        return edito.commit();
    }


}
