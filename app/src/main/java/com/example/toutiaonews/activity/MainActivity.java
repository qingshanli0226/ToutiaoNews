package com.example.toutiaonews.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import androidx.fragment.app.Fragment;
import com.example.farmework.base.BaseActivity;
import com.example.homemodule.home.HomeFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.appcontract.TouTiaoAppLication;
import com.example.toutiaonews.fragment.MeFragment;
import com.example.toutiaonews.fragment.MicroFragment;
import com.example.toutiaonews.service.TouTiaoIntentService;
import com.example.toutiaonews.view.LoadDialog;
import com.example.videomodule.fragment.VideoFragment;
import com.next.easynavigation.view.EasyNavigationBar;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private int[] selectIcon = {R.mipmap.tab_home_selected, R.mipmap.tab_video_selected, R.mipmap.tab_micro_selected, R.mipmap.tab_me_selected};
    private int[] normalIcon = {R.mipmap.tab_home_normal, R.mipmap.tab_video_normal, R.mipmap.tab_micro_normal, R.mipmap.tab_me_normal};
    private List<Fragment> listFragment = new ArrayList<>();
    private EasyNavigationBar easyBar;
    private SharedPreferences sp;
    private String token;
    private TouTiaoAppLication application;
    private LoadDialog loadDialog;

    @Override
    protected void initData() {
        initService(this);
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
    }
    //自动登录的服务
    private void initService(Context context) {
        Intent intent = new Intent();
        intent.setClass(context,TouTiaoIntentService.class);
        startService(intent);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                TouTiaoIntentService.TouTiaoBinder touTiaoBinder = (TouTiaoIntentService.TouTiaoBinder) service;
                touTiaoBinder.getService().autoLogin(getToken());
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        },Context.BIND_AUTO_CREATE);
    }
    //返回自动登录的token
    private String getToken() {
        application = (TouTiaoAppLication) getApplication();
        sp = application.sp;
        boolean flog = this.sp.getBoolean("flog", false);
        if (flog) {
            token = this.sp.getString("token", "token已经失效");
        }
        return token;
    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        loadDialog=new LoadDialog(this);
        String[] stringArray = getResources().getStringArray(R.array.tab);
        listFragment.add(new HomeFragment());
        listFragment.add(new VideoFragment());
        listFragment.add(new MicroFragment());
        listFragment.add(new MeFragment());
        easyBar = (EasyNavigationBar) findViewById(R.id.easyBar);
        easyBar.fragmentManager(getSupportFragmentManager())
                .mode(EasyNavigationBar.NavigationMode.MODE_NORMAL)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .selectTextColor(getColor(R.color.textSelectColor))
                .titleItems(stringArray)
                .fragmentList(listFragment)
                .build();
        //启动服务
        startService(new Intent(this, TouTiaoIntentService.class));
    }

}
