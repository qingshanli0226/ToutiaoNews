package com.example.toutiaonews;

import android.app.Application;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.common.NetCommon;
import com.example.framework2.manager.CacheManager;
import com.example.net.activity_bean.entity.ChannelBean;
import com.example.toutiaonews.fragment.home.HomeChannelFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class MyApp extends Application {
    private String spString ="";
    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.getInstance().init(this);
        getSaveData();
    }

    private void getSaveData() {
        List<Fragment> noFragment=new ArrayList<>();
        List<Fragment> fragments=new ArrayList<>();
        List<ChannelBean> tabList=new ArrayList<>();
        spString=CacheManager.getInstance().getSharedPreferences().getString(NetCommon.TAB_ON_DATA_KEY, null);
        Log.e("fff", "initChannel: sp" + spString);
        if (spString==null) {//判断是否为第一次进入
            String[] titles = getResources().getStringArray(R.array.channel);
            String[] codes = getResources().getStringArray(R.array.channel_code);
            for (int i = 0; i < titles.length; i++) {
                tabList.add(new ChannelBean(titles[i],codes[i],true));
            }
            fragments.clear();
            for (int i = 0; i < tabList.size(); i++) {
                Bundle bundle = new Bundle();
                HomeChannelFragment channelFragment = new HomeChannelFragment();
                Log.e("ffff------", "initChannel: "+ tabList.get(i).getCode());
                bundle.putString("code", tabList.get(i).getCode());
                channelFragment.setArguments(bundle);
                fragments.add(channelFragment);
            }
            CacheManager.getInstance().setOnList(tabList);
            CacheManager.getInstance().setFragments(fragments);
        } else {
            //获取已选频道和未轩频道
            List<ChannelBean> onList = new Gson().fromJson(CacheManager.getInstance().getSharedPreferences().getString(NetCommon.TAB_ON_DATA_KEY, null), new TypeToken<List<ChannelBean>>() {
            }.getType());
            List<ChannelBean> noList = new Gson().fromJson(CacheManager.getInstance().getSharedPreferences().getString(NetCommon.TAB_NO_DATA_KEY, null), new TypeToken<List<ChannelBean>>() {
            }.getType());
            //更新数据
            CacheManager.getInstance().setNoList(noList);
            CacheManager.getInstance().setOnList(onList);
            Log.e("fff", noList.size() + "initChannel: " + onList.size());
            fragments.clear();
            noFragment.clear();

            for (int i = 0; i < onList.size(); i++) {
                HomeChannelFragment homeChannelFragment = new HomeChannelFragment();
                Bundle bundle = new Bundle();
                bundle.putString("code", onList.get(i).getCode());
                Log.e("fff", "initChannel: on" + onList.get(i).getCode());
                homeChannelFragment.setArguments(bundle);
                fragments.add(homeChannelFragment);
            }
            for (int i = 0; i < noList.size(); i++) {
                HomeChannelFragment homeChannelFragment = new HomeChannelFragment();
                Bundle bundle = new Bundle();
                bundle.putString("code", noList.get(i).getCode());
                Log.e("fff", "initChannel:no " + onList.get(i).getCode());
                homeChannelFragment.setArguments(bundle);
                noFragment.add(homeChannelFragment);
            }
        }
        //缓存fragment
        CacheManager.getInstance().setFragments(fragments);
        CacheManager.getInstance().setNoFragments(noFragment);
    }
}
