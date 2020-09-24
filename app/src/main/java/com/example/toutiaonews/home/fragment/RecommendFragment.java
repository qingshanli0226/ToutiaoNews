package com.example.toutiaonews.home.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.mode.HomeRecommendBean;
import com.example.common.mode.News;
import com.example.common.untils.ContentBeanUntil;
import com.example.framework2.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.DetailActivity;
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
    private LinearLayout homeRecommendLin;
    //数据集合
    ArrayList<HomeRecommendBean.DataBean> dataBeans;
    //数据集合
    ArrayList<News> newsArrayList = new ArrayList<>();
    //适配器
    RecommendAdapter recommendAdapter;
    //频道值
    String stringChannel;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initData() {
        //获取recommendBean数据
        HomeRecommendBean homeRecommendBean = CacheManager.getCacheManager().getHomeRecommendBean();
        if (homeRecommendBean != null) {
            //添加数据
            dataBeans = (ArrayList<HomeRecommendBean.DataBean>) homeRecommendBean.getData();
            Gson gson = new Gson();
            for (int i = 0; i < dataBeans.size(); i++) {
                //把json数据转换为contentBean对象
                News news = gson.fromJson(dataBeans.get(i).getContent(), News.class);
                //从内存获取的数据给type赋值
                ContentBeanUntil.setItemType(news);
                newsArrayList.add(news);
            }
        }

        //创建适配器
        recommendAdapter = new RecommendAdapter(newsArrayList);
        homeRecommendRv.setLayoutManager(new LinearLayoutManager(getContext()));
        homeRecommendRv.setAdapter(recommendAdapter);

        //上拉刷新 下拉加载
        homeRecommendSmart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //下拉加载
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME, String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getRecommendData(stringChannel);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //上拉刷新
                //清空数据
                newsArrayList.clear();
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME, String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getRecommendData(stringChannel);
            }
        });

        //点击事件
        recommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //使用bundle传值
                Bundle bundle = new Bundle();
                //加载的webView地址
                bundle.putString(TouTiaoNewsConstant.WEBVIEW_URL,newsArrayList.get(position).getArticle_url());
                //加载的作者标题
                bundle.putString(TouTiaoNewsConstant.WEBVIEW_TITLE,newsArrayList.get(position).getUser_info().getName());
                //加载的作者头像
                bundle.putString(TouTiaoNewsConstant.WEBVIEW_AVATAR,newsArrayList.get(position).getUser_info().getAvatar_url());
                launchActivity(DetailActivity.class,bundle);
            }
        });
    }

    @Override
    protected void initView() {
        //获取传递过来的频道值
        stringChannel = getArguments().getString(TouTiaoNewsConstant.ARGUMENT_CHANNEL);
        homeRecommendRv = (RecyclerView) findViewById(R.id.homeRecommendRv);
        homeRecommendSmart = (SmartRefreshLayout) findViewById(R.id.homeRecommendSmart);
        homeRecommendLin = (LinearLayout) findViewById(R.id.homeRecommendLin);

    }

    @Override
    protected void initHttpData() {
        //进页面请求数据
        iHttpPresenter.getRecommendData(stringChannel);
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new RecommendPresenterImpl();
    }

    @Override
    public void onRecommendData(HomeRecommendBean homeRecommendBean) {
        if(!homeRecommendBean.toString().equals("")){
            dataBeans.clear();
            dataBeans = (ArrayList<HomeRecommendBean.DataBean>) homeRecommendBean.getData();
            Gson gson = new Gson();
            for (int i = 0; i < dataBeans.size(); i++) {
                //把json数据转换为contentBean对象
                News news = gson.fromJson(dataBeans.get(i).getContent(), News.class);
                newsArrayList.add(news);
            }
            //停止上拉和下拉
            homeRecommendSmart.finishRefresh();
            homeRecommendSmart.finishLoadMore();
            recommendAdapter.notifyDataSetChanged();
        } else{
            //没数据就显示提示信息 隐藏列表
            homeRecommendLin.setVisibility(View.VISIBLE);
            homeRecommendRv.setVisibility(View.GONE);
        }
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
