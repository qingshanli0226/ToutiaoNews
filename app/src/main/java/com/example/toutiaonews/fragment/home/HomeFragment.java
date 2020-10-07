package com.example.toutiaonews.fragment.home;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.common.NetCommon;
import com.example.framework2.manager.CacheManager;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.net.activity_bean.entity.ChannelBean;
import com.example.toutiaonews.ChannelActivity;
import com.example.toutiaonews.R;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TabLayout homeTab;
    private ImageView homeManager;
    private ViewPager homeVp;
    private EditText homeEdit;

    private  SharedPreferences sp;
    private List<ChannelBean> tabList;
    private List<Fragment> noFragment;
    private List<Fragment> fragments;
    private PagerAdapter pagerAdapter;
    private String spString ="";
    public void initData() {


//        initChannel();
        initVp();
        initTab();

    }

    private void initChannel() {

        spString=sp.getString(NetCommon.TAB_ON_DATA_KEY, null);
            Log.e("fff", "initChannel: sp" + spString);
            if (spString==null) {//判断是否为第一次进入

                fragments.clear();
                for (int i = 0; i < tabList.size(); i++) {
                    Bundle bundle = new Bundle();
                    HomeChannelFragment channelFragment = new HomeChannelFragment();
                Log.e("ffff------", "initChannel: "+ tabList.get(i).getCode());
                    bundle.putString("code", tabList.get(i).getCode());
                    channelFragment.setArguments(bundle);
                    fragments.add(channelFragment);
                }
                CacheManager.getInstance().setFragments(fragments);
            } else {
                //获取已选频道和未轩频道
                List<ChannelBean> onList = new Gson().fromJson(sp.getString(NetCommon.TAB_ON_DATA_KEY, null), new TypeToken<List<ChannelBean>>() {
                }.getType());
                List<ChannelBean> noList = new Gson().fromJson(sp.getString(NetCommon.TAB_NO_DATA_KEY, null), new TypeToken<List<ChannelBean>>() {
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

    @Override
    public void initPresenter() {}

    @Override
    public int bandLayout() {
        return R.layout.fragment_home;
    }


    private void initVp() {
         pagerAdapter = new FragmentStatePagerAdapter(getChildFragmentManager()) {
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
//        homeVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                homeTab.setScrollPosition(position,positionOffset,true);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    private void initTab() {
        homeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeTab.setupWithViewPager(homeVp);

    }

    public void initView() {
        fragments=new ArrayList<>();
        noFragment=new ArrayList<>();
        tabList=new ArrayList<>();
        sp=CacheManager.getInstance().getSharedPreferences();
        homeTab= (TabLayout) findViewById(R.id.home_tab);
        homeManager= (ImageView) findViewById(R.id.home_manager);
        homeVp= (ViewPager) findViewById(R.id.home_vp);
        homeEdit= (EditText) findViewById(R.id.home_edit);
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
//        if (CacheManager.getInstance().getFragments().size()==0){
//            initChannel();
//        }
        Log.e("rrr", CacheManager.getInstance().getNoFragments().size()+"edit: "+CacheManager.getInstance().getFragments().size() );
        pagerAdapter.notifyDataSetChanged();
        homeVp.setCurrentItem(0);
        Log.e("ttt---", "onStart: " );
        Log.e("fff", "onStart: "+CacheManager.getInstance().getFragments().size() );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }


    @Override
    public void onResume() {
        super.onResume();
        Log.e("ttt---", "onResume: " );
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("ttt---", "onStop: " );
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("ttt---", "onPause: " );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("ttt---", "onDetach: " );
    }
}