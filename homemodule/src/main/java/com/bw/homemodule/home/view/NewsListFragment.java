package com.bw.homemodule.home.view;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.homemodule.R;
import com.bw.homemodule.adapter.NewsListAdapter;
import com.bw.homemodule.home.contract.HomeContract;
import com.bw.homemodule.home.presenter.HomePresenterImpl;
import com.example.common.entity.News;
import com.example.farmework.base.BaseMVPFragment;

import java.util.ArrayList;


public class NewsListFragment extends BaseMVPFragment<HomePresenterImpl, HomeContract.IHomeView> implements HomeContract.IHomeView {
    private String channel;
    private String channel_code;
    private ArrayList<News> newsList=new ArrayList<>();
    private long lastTime=0;
    private RecyclerView newsRv;
    private NewsListAdapter newsListAdapter;

    public NewsListFragment(String channel, String channel_code) {
        this.channel = channel;
        this.channel_code = channel_code;
    }

    @Override
    protected void initHttpData() {
        if (lastTime==0){
            lastTime=System.currentTimeMillis();
        }
        mPresenter.getHomeData(channel_code, lastTime);
        lastTime=System.currentTimeMillis();
    }

    @Override
    protected void initPresenter() {
        mPresenter = new HomePresenterImpl();
    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_debug;
    }

    @Override
    protected void initData() {
        newsRv.setAdapter(newsListAdapter);
        newsRv.setLayoutManager(new LinearLayoutManager(getActivity()));

        newsRv.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
    }

    @Override
    protected void initView() {
        newsRv = findViewById(R.id.newsRv);
        newsListAdapter= new NewsListAdapter(newsList);
    }

    @Override
    public void onHomeData(ArrayList<News> newsList) {
        this.newsList.clear();
        this.newsList.addAll(newsList);
        newsListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
