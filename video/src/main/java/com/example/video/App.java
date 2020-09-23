package com.example.video;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;
import com.qiniu.pili.droid.shortvideo.PLShortVideoEnv;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
        PLShortVideoEnv.init(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
