package com.example.homemodule.home.view;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.cache.CacheManager;
import com.example.common.constant.Constant;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.News;
import com.example.common.entity.NewsData;
import com.example.common.mine.BGRefrushLayout;
import com.example.farmework.base.BaseMVPFragment;
import com.example.homemodule.App;
import com.example.homemodule.R;
import com.example.homemodule.adapter.NewsListAdapter;
import com.example.homemodule.home.contract.HomeContract;
import com.example.homemodule.home.presenter.HomePresenterImpl;
import com.example.homemodule.untils.NewsItemTypeUntil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class NewsListFragment extends BaseMVPFragment<HomePresenterImpl, HomeContract.IHomeView> implements HomeContract.IHomeView, BGRefrushLayout.IRefreshListener {
    private String channel_code;
    private ArrayList<News> newsList = new ArrayList<>();
    private long lastTime = 0;
    private RecyclerView newsRv;
    private NewsListAdapter newsListAdapter;
    private TextView errorText;
    private BGRefrushLayout homeRefrush;
    private boolean isRefresh = false;


    public NewsListFragment(String channel_code) {
        this.channel_code = channel_code;
    }

    @Override
    protected void initHttpData() {

        if (CacheManager.getInstance().isConnect()){  //判断是否有网

            //有网就开始数据请求
            lastTime = CacheManager.getInstance().getFirstTime(channel_code, 0);
            if (lastTime == 0) {
                CacheManager.getInstance().putFirstTime(channel_code, System.currentTimeMillis());
            }

            //懒加载控制数据刷新的逻辑
            long firstTime = CacheManager.getInstance().getFirstTime(channel_code, 0);
            if (System.currentTimeMillis() - firstTime > Constant.REFRESH_TIME || isRefresh || System.currentTimeMillis() - firstTime <= 1000) {
                mPresenter.getHomeData(channel_code, firstTime);
            }else {
                showSqlDatas();
            }

        } else {
            Toast.makeText(getContext(), "当前没有网络哦", Toast.LENGTH_SHORT).show();
            showSqlDatas();
        }


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

        //添加recycleView 的分割线
        newsRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        homeRefrush.attchRecylerView(newsRv);
        homeRefrush.addRefreshListener(this);

    }

    @Override
    protected void initView() {
        newsRv = findViewById(R.id.newsRv);
        newsListAdapter = new NewsListAdapter(newsList);
        errorText = findViewById(R.id.show_error);
        homeRefrush = findViewById(R.id.home_refrush);
    }

    @Override
    public void onHomeData(ArrayList<News> newsList) {
        this.newsList.addAll(0, newsList);
        errorText.setVisibility(View.GONE);
        newsListAdapter.notifyDataSetChanged();
        isRefresh = false;

    }

    @Override
    public void showError(String code, String message) {
        errorText.setVisibility(View.VISIBLE);
        showMessage("code" + code + message);
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRefreshComplete() {
        isRefresh = true;
        initHttpData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        homeRefrush.cancel();
    }

    private void showSqlDatas(){
        List<NewsRoomBean> newsRoomBeans = CacheManager.getInstance().query();
        for (int i = 0; i < newsRoomBeans.size(); i++) {
            NewsRoomBean newsRoomBean = newsRoomBeans.get(i);
            if (newsRoomBean.getChannelId().equals(channel_code)) {
                if (newsRoomBean != null) {
                    String jsonUrl = newsRoomBean.getJsonUrl();
                    ArrayList<NewsData> newsDatas = new Gson().fromJson(jsonUrl, new TypeToken<ArrayList<NewsData>>() {
                    }.getType());
                    for (int j = 0; j < newsDatas.size(); j++) {
                        News news = new Gson().fromJson(newsDatas.get(j).content, News.class);
                        NewsItemTypeUntil.ChangeItemType(news);
                        newsList.add(news);
                    }

                }
            }
        }
        newsListAdapter.notifyDataSetChanged();
    }
}
