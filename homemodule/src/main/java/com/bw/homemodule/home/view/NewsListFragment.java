package com.bw.homemodule.home.view;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.homemodule.R;
import com.bw.homemodule.adapter.NewsListAdapter;
import com.bw.homemodule.home.contract.HomeContract;
import com.bw.homemodule.home.presenter.HomePresenterImpl;
import com.example.common.cache.CacheManager;
import com.example.common.entity.News;
import com.example.common.mine.BGRefrushLayout;
import com.example.farmework.base.BaseMVPFragment;

import java.util.ArrayList;

public class NewsListFragment extends BaseMVPFragment<HomePresenterImpl, HomeContract.IHomeView> implements HomeContract.IHomeView, BGRefrushLayout.IRefreshListener {
    private String channel_code;
    private ArrayList<News> newsList = new ArrayList<>();
    private long lastTime = 0;
    private RecyclerView newsRv;
    private NewsListAdapter newsListAdapter;
    private TextView errorText;
    private BGRefrushLayout homeRefrush;

    public NewsListFragment(String channel_code) {
        this.channel_code = channel_code;
    }

    @Override
    protected void initHttpData() {

        lastTime = CacheManager.getInstance().getFirstTime(channel_code, 0);
        if (lastTime == 0) {
            CacheManager.getInstance().putFirstTime(channel_code, System.currentTimeMillis());
        }
        mPresenter.getHomeData(channel_code, CacheManager.getInstance().getFirstTime(channel_code, 0));

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
        newsListAdapter.notifyDataSetChanged();

        errorText.setVisibility(View.GONE);
    }

    @Override
    public void showError(String code, String message) {
        errorText.setVisibility(View.VISIBLE);
        Toast.makeText(mActivity, "code:" + code + message, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onRefreshComplete() {
        initHttpData();
    }

}
