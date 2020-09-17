package com.example.toutiaonews.home;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.adapter.HomeTabAdapter;
import com.example.toutiaonews.home.fragment.RecommendFragment;
import com.example.toutiaonews.home.fragment.RecommendFragment1;
import com.example.toutiaonews.home.fragment.RecommendFragment2;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class HomeFragment extends BaseFragment {

    private ViewPager homeVp;
    private TabLayout homeTab;

    //vp的数据源
    String[] items;
    //fragment数据源
    ArrayList<Fragment> fragments = new ArrayList<>();
    //适配器
    HomeTabAdapter homeTabAdapter;
    //fragment对象
    RecommendFragment recommendFragment;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        //通过资源文件获取vp数据
        items = getResources().getStringArray(R.array.channel);
        //添加数据
        fragments.add(recommendFragment);
        fragments.add(new RecommendFragment1());
        fragments.add(new RecommendFragment2());
        //创建适配器对象
        homeTabAdapter = new HomeTabAdapter(getChildFragmentManager(),fragments);
        homeVp.setAdapter(homeTabAdapter);
        //tab绑定vp
        homeTab.setupWithViewPager(homeVp);
        //不显示下划线
        homeTab.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        homeTab.setFocusableInTouchMode(false);
        //循环赋值tab
        for (int i = 0; i < homeTab.getTabCount() ; i++) {
            TabLayout.Tab tabAt = homeTab.getTabAt(i);
            if(tabAt != null){
                tabAt.setCustomView(getTabView(i));
            }
        }

        //第一个默认显示红色
        if(homeVp.getCurrentItem() == 0){
            TextView customView = (TextView) homeTab.getTabAt(0).getCustomView();
            if(customView != null){
                customView.setTextSize(15);
                customView.setTextColor(Color.RED);
            }
        }

        //滑动监听
        homeTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                homeVp.setCurrentItem(tab.getPosition());
                TextView customView = (TextView) tab.getCustomView();
                if(customView != null){
                    customView.setTextColor(Color.RED);
                    customView.setTextSize(15);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView customView = (TextView) tab.getCustomView();
                if(customView != null){
                    customView.setTextColor(Color.GRAY);
                    customView.setTextSize(13);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initView() {
        homeTab = (TabLayout) findViewById(R.id.homeTab);
        homeVp = (ViewPager) findViewById(R.id.homeVp);
        if(recommendFragment == null){
            recommendFragment = new RecommendFragment();
        }
    }

    //获取TabView的方法
    private TextView getTabView(int index){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_textview, null);
        TextView itemHomeTv = view.findViewById(R.id.itemHomeTv);
        itemHomeTv.setText(items[index]);
        return itemHomeTv;
    }
}
