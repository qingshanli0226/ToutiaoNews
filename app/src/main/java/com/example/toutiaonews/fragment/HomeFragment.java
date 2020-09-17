package com.example.toutiaonews.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
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
    private final String TAB_DATA_KEY = "TabJson";
    private  SharedPreferences sp;
    private String str;
    private List<ChannelBean> tabListAll;
    private List<ChannelBean> tabList;
    private List<Fragment> fragments;

    public void initData() {
        fragments=new ArrayList<>();
        tabList=new ArrayList<>();
        tabListAll=new ArrayList<>();
        String[] titles = getResources().getStringArray(R.array.channel);
        for (int i = 0; i < titles.length; i++) {
            tabList.add(new ChannelBean(titles[i],true));
        }
        CacheManager.getInstance().setOnList(tabList);
        String s = new Gson().toJson(CacheManager.getInstance().getOnList());
        sp.edit().putString(TAB_DATA_KEY,s);
        str = sp.getString(TAB_DATA_KEY, null);
        if (str==null){
            for (int i = 0; i < tabList.size(); i++) {
                fragments.add(new MyFragment());
            }
        }else {
            List<ChannelBean> listAll = new Gson().fromJson(str, new TypeToken<List<ChannelBean>>() {
            }.getType());
            for (int i = 0; i < listAll.size(); i++) {
                if (listAll.get(i).isShow()) {
                    fragments.add(new MyFragment());
                }
            }
        }
        initVp();
        initTab();

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.fragment_home;
    }

    private void initVp() {
        homeVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.getInt("name",position+1);
                fragments.get(position).setArguments(bundle);
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return super.getPageTitle(position);
            }
        });

    }

    private void initTab() {
        homeTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        homeTab.setupWithViewPager(homeVp);
        if (str==null){
            for (int i = 0; i < tabList.size(); i++) {
                homeTab.getTabAt(i).setText(tabList.get(i).getTitle());
            }
        }else {
            List<ChannelBean> listAll = new Gson().fromJson(str, new TypeToken<List<ChannelBean>>() {
            }.getType());
            for (int i = 0; i < listAll.size(); i++) {
                if (listAll.get(i).isShow() == true)
                    homeTab.getTabAt(i).setText(listAll.get(i).getTitle());
            }
        }
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

}
