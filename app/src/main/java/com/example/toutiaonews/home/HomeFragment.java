package com.example.toutiaonews.home;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.adapter.HomeTabAdapter;
import com.example.toutiaonews.home.channel.ChannelActivity;
import com.example.toutiaonews.home.fragment.HomeVideoFragment;
import com.example.toutiaonews.home.fragment.RecommendFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class HomeFragment extends BaseFragment{

    private ViewPager homeVp;
    private TabLayout homeTab;
    private ImageView homeAddChannel;

    //vp的数据源
    String[] items;
    //频道号的数据源
    String[] itemsCode;
    //适配器
    HomeTabAdapter homeTabAdapter;
    //fragment对象
    RecommendFragment recommendFragment;

    //已选择的频道数据源
    ArrayList<String> selectArrayList = new ArrayList<>();
    //已选择的频道所对应的fragment数据源
    ArrayList<Fragment> selectFragments = new ArrayList<>();
    //未选择的频道数据源
    ArrayList<String> unSelectArrayList = new ArrayList<>();
    //未选择的频道所对应的fragment数据源
    ArrayList<Fragment> unSelectFragments = new ArrayList<>();
    //暂时保存的频道数据源 一直都是21个 后面一直实时更新
    ArrayList<String> selectTemplate = new ArrayList<>();
    //暂时保存的未频道数据源 一直都是21个 后面一直实时更新
    ArrayList<String> unSelectTemplate = new ArrayList<>();
    //暂时保存的频道数据源 一直都是21个 后面一直实时更新
    ArrayList<String> selectTemplateArrayList = new ArrayList<>();
    //暂时保存的未频道数据源 一直都是21个 后面一直实时更新
    ArrayList<String> unSelectTemplateArrayList = new ArrayList<>();

    HashMap<String,Fragment> selectFragmentMaps = new HashMap<>();
    HashMap<String,Fragment> unSelectFragmentMaps = new HashMap<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        //通过资源文件获取vp数据
        items = getResources().getStringArray(R.array.channel);
        itemsCode = getResources().getStringArray(R.array.channel_code);
        //获取是否是第一次启动app的状态
        boolean isStartApp = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISSTARTAPP);
        if(!isStartApp){
            //是第一次储存频道数据
            //把String数组转换成List集合
            List<String> strings = Arrays.asList(items);
            //把List集合转换成ArrayList集合
            ArrayList<String> arrayList = new ArrayList<>(strings);
            //储存在Sp中
            CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.SELECTDATA,new Gson().toJson(arrayList));
            CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISSTARTAPP,true);
            //添加数据 并且给map集合添加数据
            for (int i = 0; i < items.length; i++) {
                //创建bundle传值对象
                Bundle bundle = new Bundle();
                //把所对应的channel频道号传值
                bundle.putString(TouTiaoNewsConstant.ARGUMENT_CHANNEL,itemsCode[i]);
                if(i != 1){
                    //添加到集合
                    RecommendFragment recommendFragment = new RecommendFragment();
                    recommendFragment.setArguments(bundle);
                    selectFragments.add(recommendFragment);
                    selectFragmentMaps.put(items[i],recommendFragment);
                } else{
                    HomeVideoFragment homeVideoFragment = new HomeVideoFragment();
                    bundle.putString(TouTiaoNewsConstant.ARGUMENT_CHANNEL,itemsCode[i]);
                    homeVideoFragment.setArguments(bundle);
                    selectFragments.add(homeVideoFragment);
                    selectFragmentMaps.put(items[i],homeVideoFragment);
                }
            }
            //给暂时的频道号数据源添加数据
            selectTemplate.clear();
            selectTemplate.addAll(arrayList);
            selectArrayList.addAll(arrayList);
            selectTemplateArrayList.clear();
            selectTemplateArrayList.addAll(arrayList);
            //把此时的选择的频道数据源和未选择的频道数据源 储存到cacheManager
            CacheManager.getCacheManager().setSelectFragments(selectFragments);
            CacheManager.getCacheManager().setUnSelectFragments(unSelectFragments);

        } else {
            //不是第一次启动app 获取sp里储存的频道号 通过频道号创建fragment
            //获取sp此时的频道页面的数据
            selectArrayList.clear ( );
            selectArrayList.addAll(new Gson().fromJson(CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.SELECTDATA),new TypeToken<ArrayList<String>>(){}.getType()));
            //获取此时的未选择的频道数据
            ArrayList<String> list = new Gson().fromJson(CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.UNSELECTDATA), new TypeToken<ArrayList<String>>() {
            }.getType());
            if(list != null){
                unSelectArrayList.clear();
                unSelectArrayList.addAll(list);
            }
            //给map集合添加数据
            for (int i = 0; i < selectArrayList.size() ; i++) {
                //创建bundle传值对象
                Bundle bundle = new Bundle();
                //把所对应的channel频道号传值
                bundle.putString(TouTiaoNewsConstant.ARGUMENT_CHANNEL,itemsCode[i]);
                if(selectArrayList.get(i).equals("video")){
                    //此时的已选则的频道号包含video模块
                    HomeVideoFragment homeVideoFragment = new HomeVideoFragment();
                    bundle.putString(TouTiaoNewsConstant.ARGUMENT_CHANNEL,itemsCode[i]);
                    homeVideoFragment.setArguments(bundle);
                    selectFragments.add(homeVideoFragment);
                    selectFragmentMaps.put(selectArrayList.get(i),homeVideoFragment);
                } else {
                    //此时的已选则的频道号未包含video模块
                    //添加到集合
                    RecommendFragment recommendFragment = new RecommendFragment();
                    recommendFragment.setArguments(bundle);
                    selectFragments.add(recommendFragment);
                    selectFragmentMaps.put(selectArrayList.get(i),recommendFragment);
                }
            }
            //创建未选择的fragment数据源
            for (int i = 0; i < unSelectArrayList.size() ; i++) {
                //创建bundle传值对象
                Bundle bundle = new Bundle();
                //把所对应的channel频道号传值
                bundle.putString(TouTiaoNewsConstant.ARGUMENT_CHANNEL,itemsCode[i]);
                if(unSelectArrayList.get(i).equals("video")){
                    //包含video
                    HomeVideoFragment homeVideoFragment = new HomeVideoFragment();
                    bundle.putString(TouTiaoNewsConstant.ARGUMENT_CHANNEL,itemsCode[i]);
                    homeVideoFragment.setArguments(bundle);
                    unSelectFragments.add(homeVideoFragment);
                    unSelectFragmentMaps.put(itemsCode[i],homeVideoFragment);
                } else{
                    //未包含video
                    RecommendFragment recommendFragment = new RecommendFragment();
                    recommendFragment.setArguments(bundle);
                    unSelectFragments.add(recommendFragment);
                    unSelectFragmentMaps.put(itemsCode[i],recommendFragment);
                }
            }
            //给暂时的频道号数据源添加数据
            selectTemplate.clear();
            selectTemplate.addAll(selectArrayList);
            selectTemplateArrayList.addAll(selectArrayList);
            unSelectTemplate.clear();
            unSelectTemplate.addAll(unSelectArrayList);
            unSelectTemplateArrayList.addAll(unSelectArrayList);
            //给一番操作后的未选择的fragment数据源赋值状态
            CacheManager.getCacheManager().setSPOfInt(TouTiaoNewsConstant.MAINUNSELECTDATASIZE,unSelectFragments.size());
            //把此时的选择的频道数据源和未选择的频道数据源 储存到cacheManager
            CacheManager.getCacheManager().setSelectFragments(selectFragments);
            CacheManager.getCacheManager().setUnSelectFragments(unSelectFragments);
        }

        //创建适配器对象
        homeTabAdapter = new HomeTabAdapter(getChildFragmentManager(),selectFragments,selectArrayList);
        homeVp.setAdapter(homeTabAdapter);
        //tab绑定vp
        homeTab.setupWithViewPager(homeVp);
        //不显示下划线
        homeTab.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        homeTab.setFocusableInTouchMode(false);


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

        //点击添加频道号
        homeAddChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //跳转到编辑频道页面
                launchActivity(ChannelActivity.class,null);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //第一次启动app状态肯定为false 不会走onStart里面的操作
        boolean isOneSelectData = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISONESELECTDATA);
        if(isOneSelectData){
          //获取储存的fragment数据源
            selectFragments.clear();
            selectFragments.addAll(CacheManager.getCacheManager().getSelectFragments());
            selectArrayList.clear ( );
            selectArrayList.addAll(new Gson().fromJson(CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.SELECTDATA),new TypeToken<ArrayList<String>>(){}.getType()));

            homeTabAdapter.notifyDataSetChanged();

            CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISONESELECTDATA,false);
        }

    }

    @Override
    protected void initView() {
        homeTab = (TabLayout) findViewById(R.id.homeTab);
        homeVp = (ViewPager) findViewById(R.id.homeVp);
        homeAddChannel = (ImageView) findViewById(R.id.homeAddChannel);
        if(recommendFragment == null){
            recommendFragment = new RecommendFragment();
        }
    }

    //获取TabView的方法
    private TextView getTabView(int index){
        View view = LayoutInflater.from(getContext()).inflate(R.layout.item_home_textview, null);
        TextView itemHomeTv = view.findViewById(R.id.itemHomeTv);
        itemHomeTv.setText(selectArrayList.get(index));
        return itemHomeTv;
    }

}
