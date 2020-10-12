package com.example.toutiaonews.main;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.framework2.base.BaseActivity;
import com.example.framework2.listener.PermissionListener;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.HomeFragment;
import com.example.toutiaonews.main.mode.MainCommonBean;
import com.example.toutiaonews.me.MeFragment;
import com.example.toutiaonews.micro.MicroFragment;
import com.example.toutiaonews.video.VideoFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private CommonTabLayout mainCommon;
    Fragment currentFragment;
    HomeFragment homeFragment;
    VideoFragment videoFragment;
    MicroFragment microFragment;
    MeFragment meFragment;
    private static long mPreTime;

    //底部导航栏切换的数据
    ArrayList<CustomTabEntity> mainBottomCustomTabEntities = new ArrayList<>();

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //获取传过来的参数
        boolean isOneSelectData = intent.getBooleanExtra(TouTiaoNewsConstant.ISONESELECTDATA, false);
        //给sp里面存储的是否第一次获取的状态
        CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISONESELECTDATA,isOneSelectData);
    }

    @Override
    protected void initData() {
        //添加底部导航栏数据
        mainBottomCustomTabEntities.add(new MainCommonBean("首页", R.mipmap.tab_home_selected, R.mipmap.tab_home_normal));
        mainBottomCustomTabEntities.add(new MainCommonBean("视频", R.mipmap.tab_video_selected, R.mipmap.tab_video_normal));
        mainBottomCustomTabEntities.add(new MainCommonBean("微头条", R.mipmap.tab_micro_selected, R.mipmap.tab_micro_normal));
        mainBottomCustomTabEntities.add(new MainCommonBean("我的", R.mipmap.tab_me_selected, R.mipmap.tab_me_normal));
        //设置数据
        mainCommon.setTabData(mainBottomCustomTabEntities);
        //底部导航监听
        mainCommon.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switch (position) {
                    case 0:
                        switchFragment(homeFragment);
                        break;
                    case 1:
                        switchFragment(videoFragment);
                        break;
                    case 2:
                        switchFragment(microFragment);
                        break;
                    case 3:
                        switchFragment(meFragment);
                        break;
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });

    }

    @Override
    protected void initView() {
        //页面销毁的时候储存为false
        CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISONESELECTDATA,false);
        //动态授权
        requestRuntimePermission(new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, new PermissionListener() {
            @Override
            public void onGranted() {
                //授权成功
            }

            @Override
            public void onDenied(List<String> deniedPermissions) {
                //授权失败
            }
        });


        mainCommon = (CommonTabLayout) findViewById(R.id.mainCommon);
        //创建Fragment实例
        createFragment();
        //进入默认显示homeFragment
        switchFragment(homeFragment);
    }

    private void createFragment() {
        if (homeFragment == null) {
            homeFragment = new HomeFragment();
        }
        if (videoFragment == null) {
            videoFragment = new VideoFragment();
        }
        if (microFragment == null) {
            microFragment = new MicroFragment();
        }
        if (meFragment == null) {
            meFragment = new MeFragment();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    //切换Fragment的方法
    private void switchFragment(Fragment fragment) {

        if (currentFragment == fragment) {
            return;
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        if (currentFragment != null) {
            beginTransaction.hide(currentFragment);
        }

        if (fragment.isAdded()) {
            beginTransaction.show(fragment).commit();
        } else {
            beginTransaction.add(R.id.mainFrame, fragment, fragment.getClass().getSimpleName()).commit();
        }

        currentFragment = fragment;
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - mPreTime > 2000) {// 两次点击间隔大于2秒
            //再按一次退出应用
            showToast("再按一次退出应用");
            mPreTime = System.currentTimeMillis();
            return;
        }
        super.onBackPressed();// finish()
    }

    @Override
    protected void onStart() {
        //网络状态
        boolean b = checkNetworkState();
        super.onStart();
    }

}
