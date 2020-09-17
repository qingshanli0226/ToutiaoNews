package com.example.toutiaonews.home.fragment;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.CacheManager;
import com.example.common.mode.HomeRecommendContentBean;
import com.example.common.mode.HomeRecommendBean;
import com.example.framework2.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.adapter.RecommendAdapter;
import com.example.toutiaonews.welcome.contract.RecommendContract;
import com.example.toutiaonews.welcome.presenter.RecommendPresenterImpl;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

public class RecommendFragment extends BaseMVPFragment<RecommendPresenterImpl, RecommendContract.RecommendView> implements RecommendContract.RecommendView {

    private RecyclerView homeRecommendRv;
    private SmartRefreshLayout homeRecommendSmart;

    //数据集合
    ArrayList<HomeRecommendBean.DataBean> dataBeans;
    //数据集合
    ArrayList<HomeRecommendContentBean> homeRecommendContentBeans = new ArrayList<>();
    //适配器
    RecommendAdapter recommendAdapter;

    String items[];

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initData() {
        items = getResources().getStringArray(R.array.channel_code);
        //获取recommendBean数据
        HomeRecommendBean homeRecommendBean = CacheManager.getCacheManager().getHomeRecommendBean();
        if(homeRecommendBean != null){
            //添加数据
            dataBeans = (ArrayList<HomeRecommendBean.DataBean>) homeRecommendBean.getData();
            Gson gson = new Gson();
            for (int i = 0; i < dataBeans.size() ; i++) {
                //把json数据转换为contentBean对象
                HomeRecommendContentBean homeRecommendContentBean = gson.fromJson(dataBeans.get(i).getContent(), HomeRecommendContentBean.class);
                homeRecommendContentBeans.add(homeRecommendContentBean);
            }
        }

        //创建适配器
        recommendAdapter = new RecommendAdapter(R.layout.item_home_recommend, homeRecommendContentBeans);
        homeRecommendRv.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecommendRv.setAdapter(recommendAdapter);

        //上拉刷新 下拉加载
        homeRecommendSmart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //下拉加载
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME,String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getRecommendData(items[0]);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //上拉刷新
                //清空数据
                homeRecommendContentBeans.clear();
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME,String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getRecommendData(items[0]);
            }
        });
    }

    @Override
    protected void initView() {
        homeRecommendRv = (RecyclerView) findViewById(R.id.homeRecommendRv);
        homeRecommendSmart = (SmartRefreshLayout) findViewById(R.id.homeRecommendSmart);
    }

    @Override
    protected void initHttpData() {

    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new RecommendPresenterImpl();
    }

    @Override
    public void onRecommendData(HomeRecommendBean homeRecommendBean) {
        dataBeans.clear();
        dataBeans = (ArrayList<HomeRecommendBean.DataBean>) homeRecommendBean.getData();
        Gson gson = new Gson();
        for (int i = 0; i < dataBeans.size() ; i++) {
            //把json数据转换为contentBean对象
            HomeRecommendContentBean homeRecommendContentBean = gson.fromJson(dataBeans.get(i).getContent(), HomeRecommendContentBean.class);
            homeRecommendContentBeans.add(homeRecommendContentBean);
        }

        //停止上拉和下拉
        homeRecommendSmart.finishRefresh();
        homeRecommendSmart.finishLoadMore();

        recommendAdapter.notifyDataSetChanged();
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
