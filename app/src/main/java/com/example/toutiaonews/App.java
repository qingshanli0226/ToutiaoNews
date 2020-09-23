package com.example.toutiaonews;

import android.app.Application;
import com.alibaba.android.arouter.launcher.ARouter;

public class App extends Application {
    public static App app;
    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            ARouter.openDebug();
            ARouter.openLog();
        }
        app = this;
//        AbnormalManager.getAbnormalManager().init(this);
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }
}
