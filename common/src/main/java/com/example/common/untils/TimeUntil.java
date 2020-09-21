package com.example.common.untils;

public class TimeUntil {

    public static String getStringTime(int time) {
        if (time < 60) {
            //小于1分钟
            return "00:" + time / 10 + time % 10;
        } else if (time >= 60 && time < 600) {
            //1分钟-10分钟
            return "0" + time / 60 + ":" + time % 60 / 10 + time % 60 % 10;
        } else if (time >= 600 && time < 3600) {
            //10分钟-1个小时
            return time / 600 + time % 600 / 60 + ":" + time % 600 % 60 / 10 + time % 600 % 60 % 10;
        }
        return "";
    }
}
