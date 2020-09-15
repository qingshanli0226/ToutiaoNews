package com.example.toutiaonews;

import android.app.Application;
import android.content.res.Resources;

import com.example.common.manager.CacheManager;
import com.shuyu.gsyvideoplayer.utils.GSYVideoType;

public class MyApp extends Application {


    private static Resources sRes;

    @Override
    public void onCreate() {
        super.onCreate();



//默认显示比例
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_DEFAULT);

//16:9
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_16_9);

//全屏裁减显示，为了显示正常 CoverImageView 建议使用FrameLayout作为父布局
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_FULL);

//全屏拉伸显示，使用这个属性时，surface_container建议使用FrameLayout
        GSYVideoType.setShowType(GSYVideoType.SCREEN_MATCH_FULL);

        //4:3
        GSYVideoType.setShowType(GSYVideoType.SCREEN_TYPE_4_3);


        /*--------------注册-------------*/
        CacheManager.getCacheManager().init(this);//数据缓存注册

    }

}
