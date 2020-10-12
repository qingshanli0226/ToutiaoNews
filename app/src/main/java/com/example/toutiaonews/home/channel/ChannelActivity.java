package com.example.toutiaonews.home.channel;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.framework2.base.BaseActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.channel.adapter.ChannelSelectAdapter;
import com.example.toutiaonews.home.channel.adapter.ChannelUnSelectAdapter;
import com.example.toutiaonews.main.MainActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class ChannelActivity extends BaseActivity implements View.OnClickListener,ChannelSelectAdapter.ChannelSelectInterface, ChannelUnSelectAdapter.ChannelUnSelectInterface {

    private RecyclerView channelSelectRv;
    private RecyclerView channelNoSelectRv;
    private TextView channelCompile;

    //选择的频道数据源
    ArrayList<String> selectArrayList = new ArrayList<>();
    //未选择的频道数据源
    ArrayList<String> unSelectArrayList = new ArrayList<>();
    //选择频道的适配器
    ChannelSelectAdapter channelSelectAdapter;
    //未选择频道的适配器
    ChannelUnSelectAdapter channelUnSelectAdapter;
    //用来暂时储存选择频道和未选择频道的数据源
    ArrayList<String> selectTemplate = new ArrayList<>();
    ArrayList<String> unSelectTemplate = new ArrayList<>();

    //储存选择频道fragment数据源的集合
    ArrayList<Fragment> selectFragments = new ArrayList<>();
    //储存未选择频道fragment数据源的集合
    ArrayList<Fragment> unSelectFragments = new ArrayList<>();
    //暂时储存选择频道fragment数据源的集合
    ArrayList<Fragment> selectSempleFragments = new ArrayList<>();
    //暂时储存选择频道fragment数据源的集合
    ArrayList<Fragment> unSelectSempleFragments = new ArrayList<>();

    @Override
    protected void initData() {
        //创建适配器
        channelSelectAdapter = new ChannelSelectAdapter(R.layout.item_channel_select,selectArrayList);
        channelUnSelectAdapter = new ChannelUnSelectAdapter(R.layout.item_channel_select,unSelectArrayList);
        channelSelectAdapter.setChannelSelectInterface(this::itemSelect);
        channelUnSelectAdapter.setChannelSelectInterface(this::itemUnSelect);
        //设置适配器与布局方式
        channelSelectRv.setAdapter(channelSelectAdapter);
        channelSelectRv.setLayoutManager(new GridLayoutManager(this,4));
        //设置未选择频道的数据源与布局方式
        channelNoSelectRv.setAdapter(channelUnSelectAdapter);
        channelNoSelectRv.setLayoutManager(new GridLayoutManager(this,4));
    }

    @Override
    protected void initView() {
        //通过本地获取选择与未选择的fragment资源
        selectFragments.clear();
        selectFragments.addAll(CacheManager.getCacheManager().getSelectFragments());
        unSelectFragments.clear();
        unSelectFragments.addAll(CacheManager.getCacheManager().getUnSelectFragments());
        selectSempleFragments.clear();
        selectSempleFragments.addAll(selectFragments);
        unSelectSempleFragments.clear();
        unSelectSempleFragments.addAll(unSelectFragments);

        //通过内存获取选择频道的数据源
        //第一次进入的时候赋值为false
        CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISCOMPILE,false);
        //获取是否是第一次储存未选择频道的数据状态
        boolean isOneUnSelect = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISONEUNSELECTDATA);
        //通过sp获取选择频道的数据源 与 为选择频道的数据源
        selectArrayList.clear();
        selectArrayList.addAll(new Gson().fromJson(CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.SELECTDATA),new TypeToken<ArrayList<String>>(){}.getType()));
        if(isOneUnSelect){
            unSelectArrayList.clear();
            unSelectArrayList.addAll(new Gson().fromJson(CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.UNSELECTDATA),new TypeToken<ArrayList<String>>(){}.getType()));
            //给暂时储存未选择频道的数据源赋值
            unSelectTemplate.clear();
            unSelectTemplate.addAll(unSelectArrayList);
        }
        //给暂时存储的数据源赋值
        selectTemplate.clear();
        selectTemplate.addAll(selectArrayList);

        channelSelectRv = (RecyclerView) findViewById(R.id.channelSelectRv);
        channelNoSelectRv = (RecyclerView) findViewById(R.id.channelNoSelectRv);

        //设置点击事件
        findViewById(R.id.channelClose).setOnClickListener(this);
        channelCompile = findViewById(R.id.channelCompile);
        channelCompile.setOnClickListener(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_channel;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //关闭编辑页面
            case R.id.channelClose:
                //创建Bundle传值
                Bundle bundle = new Bundle();
                bundle.putBoolean(TouTiaoNewsConstant.ISONESELECTDATA,true);
                bundle.putInt(TouTiaoNewsConstant.MAININDEX,0);
                //用startActivity跳转到主页面
                launchActivity(MainActivity.class,bundle);
                break;
                //开始编辑
            case R.id.channelCompile:
                //获取编辑的状态
                boolean isCompile = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISCOMPILE);
                if(isCompile){
                    //是编辑状态
                    channelCompile.setText("编辑");
                    //改变Sp的储存状态
                    CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISCOMPILE,false);
                    //刷新适配器
                    channelSelectAdapter.notifyDataSetChanged();
                    channelUnSelectAdapter.notifyDataSetChanged();
                    //编辑状态结束后把已选择的和未选择的频道数据源重新存入sp文件中
                    CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.SELECTDATA,new Gson().toJson(selectTemplate));
                    CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.UNSELECTDATA,new Gson().toJson(unSelectTemplate));
                    //改变是否是第一次储存未选择频道的状态
                    CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISONEUNSELECTDATA,true);
                    //储存未选择频道数据得个数
                    CacheManager.getCacheManager().setSPOfInt(TouTiaoNewsConstant.UNSELECTDATASIZE, unSelectTemplate.size());
                    //储存此时的fragment集合
                    CacheManager.getCacheManager().setSelectFragments(selectFragments);
                    CacheManager.getCacheManager().setUnSelectFragments(unSelectFragments);
                } else{
                    //不是编辑状态
                    channelCompile.setText("完成");
                    //改变Sp的储存状态
                    CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISCOMPILE,true);
                    //刷新适配器
                    channelSelectAdapter.notifyDataSetChanged();
                    channelUnSelectAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    @Override
    public void itemSelect(int position) {
        //选择频道数据源删除数据
        selectArrayList.remove(position);
        channelSelectAdapter.notifyItemRemoved(position);
        //未选择频道数据源添加数据
        unSelectArrayList.add(selectTemplate.get(position));
        //暂时管理的未选择频道数据源添加数据
        unSelectTemplate.add(selectTemplate.get(position));
        //暂时管理的数据源也删除这个位置的数据
        selectTemplate.remove(position);
        //刷新未选择频道的数据
        channelUnSelectAdapter.notifyDataSetChanged();

        selectFragments.remove(position);
        unSelectFragments.add(selectSempleFragments.get(position));
        unSelectSempleFragments.add(selectSempleFragments.get(position));
        selectSempleFragments.remove(position);
    }

    @Override
    public void itemUnSelect(int position) {
        //未选择频道数据源删除数据
        unSelectArrayList.remove(position);
        channelUnSelectAdapter.notifyItemRemoved(position);
        //选择频道数据源添加数据
        selectArrayList.add(unSelectTemplate.get(position));
        //暂时管理的选择频道数据源添加数据
        selectTemplate.add(unSelectTemplate.get(position));
        //暂时管理的未选择数据源也删除这个位置的数据
        unSelectTemplate.remove(position);
        channelSelectAdapter.notifyDataSetChanged();

        unSelectFragments.remove(position);
        selectFragments.add(unSelectSempleFragments.get(position));
        selectSempleFragments.add(unSelectSempleFragments.get(position));
        unSelectSempleFragments.remove(position);
    }
}
