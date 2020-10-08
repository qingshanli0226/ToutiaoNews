package com.example.toutiaonews.appcontract;

import android.app.Application;
import android.content.SharedPreferences;

import com.chad.library.adapter.base.util.TouchEventUtil;
import com.example.common.cache.CacheManager;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

public class TouTiaoAppLication extends Application {
   public SharedPreferences sp ;
   public boolean tofLogin;
   private static TouchEventUtil touchEventUtil;
    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance().init(this);
        UMConfigure.init(this,"5a12384aa40fa3551f0001d1"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");//58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin("wxdc1e388c3822c80b", "3baf1193c85774b3fd9d18447d76cab0");
        //豆瓣RENREN平台目前只能在服务器端配置
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad","http://sns.whalecloud.com");
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        sp = getSharedPreferences("lo", 0);
        tofLogin=false;
    }
}
