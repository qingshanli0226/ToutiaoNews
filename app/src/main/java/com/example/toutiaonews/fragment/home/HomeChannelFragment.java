package com.example.toutiaonews.fragment.home;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework2.manager.CacheManager;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.net.activity_bean.entity.News;
import com.example.net.activity_bean.entity.NewsData;
import com.example.toutiaonews.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


public class HomeChannelFragment extends BaseFragment<ChannelItemPresenter> implements ChannelItemContract.View{
    private RecyclerView myRcv;
    private Bundle codeBundle;
    private ChannelItemAdapter adapter;
    private List<News> newsList;
    private SharedPreferences sp;
    private final String TIME_LAST = "time";
    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
         sp = CacheManager.getInstance().getSharedPreferences();
        newsList = new ArrayList<>();
        myRcv= (RecyclerView) findViewById(R.id.my_rcv);
        codeBundle = getArguments();
        adapter=new ChannelItemAdapter(newsList);
        myRcv.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),DividerItemDecoration.VERTICAL);
        myRcv.addItemDecoration(dividerItemDecoration);
        myRcv.setAdapter(adapter);

    }

    @Override
    public void initData() {

        long lastTime = sp.getLong(TIME_LAST, 0);
        if (lastTime==0){
            sp.edit().putLong(TIME_LAST,System.currentTimeMillis()).commit();
        }
        mPresenter.getCodeData(lastTime);
    }

    @Override
    public void initPresenter() {
        mPresenter=new ChannelItemPresenter(new ChannelItemModel(),this);
    }

    @Override
    public int bandLayout() {
        return R.layout.fragment_my;
    }

    @Override
    public String getCode() {
        Log.e("fff", "getCode: "+codeBundle.getString("code") );
        return codeBundle.getString("code");
    }

    @Override
    public void getedData(List<NewsData> listBean) {
        newsList.clear();
        for (NewsData dataBean : listBean) {
            News news = new Gson().fromJson(dataBean.content, News.class);
            newsList.add(news);
        }
        adapter.notifyDataSetChanged();
    }


}
