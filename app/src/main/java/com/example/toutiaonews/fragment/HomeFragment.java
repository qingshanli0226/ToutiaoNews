package com.example.toutiaonews.fragment;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.common.NetCommon;
import com.example.framework2.manager.CacheManager;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.net.activity_bean.ChannelBean;
import com.example.toutiaonews.ChannelActivity;
import com.example.toutiaonews.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TabLayout homeTab;
    private ImageView homeManager;
    private ViewPager homeVp;
    private final String TAB_ON_DATA_KEY = "OnTabJson";
    private final String TAB_NO_DATA_KEY = "NOTabJson";
    private  SharedPreferences sp;
    private String str;
    private List<ChannelBean> tabListAll;
    private List<ChannelBean> tabList;
    private List<Fragment> noFragment;
    private List<Fragment> fragments;
    private PagerAdapter pagerAdapter;
    public void initData() {
        fragments=new ArrayList<>();
        noFragment=new ArrayList<>();
        tabList=new ArrayList<>();
        tabListAll=new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.channel);
        for (int i = 0; i < titles.length; i++) {
            tabList.add(new ChannelBean(titles[i],true));
        }
        CacheManager.getInstance().setOnList(tabList);
        initChannel();
        initVp();
        initTab();

    }

    private void initChannel() {
        if (sp.getString(TAB_ON_DATA_KEY, null)==null){//判断是否为第一次进入
            for (int i = 0; i < tabList.size(); i++) {
                fragments.add(new MyFragment());
            }
        }else {
            //获取已选频道和未轩频道
            List<ChannelBean> onList = new Gson().fromJson(sp.getString(TAB_ON_DATA_KEY, null), new TypeToken<List<ChannelBean>>() {
            }.getType());
            List<ChannelBean> noList = new Gson().fromJson(sp.getString(TAB_NO_DATA_KEY, null), new TypeToken<List<ChannelBean>>() {
            }.getType());
            //更新数据
            CacheManager.getInstance().setNoList(noList);
            CacheManager.getInstance().setOnList(onList);
            Log.e("fff", noList.size()+"initChannel: "+onList.size() );
            for (int i = 0; i < onList.size(); i++) {
                fragments.add(new MyFragment());
            }
            for (int i = 0; i < noList.size(); i++) {
                noFragment.add(new MyFragment());
            }
        }
        //缓存fragment
        CacheManager.getInstance().setFragments(fragments);
        CacheManager.getInstance().setNoFragments(noFragment);
    }

    @Override
    public void initPresenter() {}

    @Override
    public int bandLayout() {
        return R.layout.fragment_home;
    }


    private void initVp() {
         pagerAdapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
//                Bundle bundle = new Bundle();
//                bundle.getInt("name", position + 1);
//                CacheManager.getInstance().getFragments().get(position).setArguments(bundle);
                return CacheManager.getInstance().getFragments().get(position);
            }

            @Override
            public int getCount() {
                return CacheManager.getInstance().getFragments().size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return CacheManager.getInstance().getOnList().get(position).getTitle();
            }
        };
        homeVp.setAdapter(pagerAdapter);

    }

    private void initTab() {
        homeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeTab.setupWithViewPager(homeVp);

    }

    public void initView() {
        sp=CacheManager.getInstance().getSharedPreferences();
        homeTab= (TabLayout) findViewById(R.id.home_tab);
        homeManager= (ImageView) findViewById(R.id.home_manager);
        homeVp= (ViewPager) findViewById(R.id.home_vp);
        homeManager.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.home_manager:
                Intent intent = new Intent(getActivity(), ChannelActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        pagerAdapter.notifyDataSetChanged();
        Log.e("fff", "onStart: "+CacheManager.getInstance().getFragments().size() );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        String s = new Gson().toJson(CacheManager.getInstance().getOnList());
        String s1 = new Gson().toJson(CacheManager.getInstance().getNoList());
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TAB_ON_DATA_KEY, s);
        editor.putString(TAB_NO_DATA_KEY,s1);
        editor.commit();
        Log.e(s1+"fff", "onDestroy: " +s);
    }

}