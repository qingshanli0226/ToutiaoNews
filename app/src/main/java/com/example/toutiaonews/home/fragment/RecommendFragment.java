package com.example.toutiaonews.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.dao.NetWorkDataEntity;
import com.example.common.mode.HomeRecommendBean;
import com.example.common.mode.HomeRecommendContentBean;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;


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
                CacheManager.getCacheManager().deleteCodeData(stringChannel);
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
                bundle.putString(TouTiaoNewsConstant.WEBVIEW_URL, newsArrayList.get(position).getArticle_url());
                //加载的作者标题
                bundle.putString(TouTiaoNewsConstant.WEBVIEW_TITLE, newsArrayList.get(position).getUser_info().getName());
                //加载的作者头像
                bundle.putString(TouTiaoNewsConstant.WEBVIEW_AVATAR, newsArrayList.get(position).getUser_info().getAvatar_url());
                launchActivity(DetailActivity.class, bundle);
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
        if (!homeRecommendBean.toString().equals("")) {
            if (dataBeans != null) {
                dataBeans.clear();
            }
            dataBeans = (ArrayList<HomeRecommendBean.DataBean>) homeRecommendBean.getData();
            Gson gson = new Gson();
            for (int i = 0; i < dataBeans.size(); i++) {
                //把json数据转换为contentBean对象
                News news = gson.fromJson(dataBeans.get(i).getContent(), News.class);
                newsArrayList.add(news);
                HomeRecommendContentBean homeRecommendContentBean = gson.fromJson(dataBeans.get(i).getContent(), HomeRecommendContentBean.class);

                //插入数据库
                NetWorkDataEntity netWorkDataEntity = new NetWorkDataEntity();
                netWorkDataEntity.setChannelCode(stringChannel);
                netWorkDataEntity.setJsonUrl(dataBeans.get(i).getContent());

                CacheManager.getCacheManager().insert(netWorkDataEntity);
            }
            //停止上拉和下拉
            homeRecommendSmart.finishRefresh();
            homeRecommendSmart.finishLoadMore();
            recommendAdapter.notifyDataSetChanged();
        } else {
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

    private News contentBean;
    private Set<String> netWorkDataEntities = new HashSet<>();//处理重复数据

    private static final int NETWORKSTATE = 1;
    Thread thread;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == NETWORKSTATE) {
                //去重后的集合
                ArrayList<String> stringArrayList = new ArrayList<>(netWorkDataEntities);
                //遍历集合拿到唯一数据
                for (int i = 0; i < stringArrayList.size(); i++) {
                    String json = stringArrayList.get(i).toString();
                    News news = new Gson().fromJson(json, News.class);
                    newsArrayList.add(news);
                }
                recommendAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onStart() {
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    if (!checkNetworkState()) {
                        List<NetWorkDataEntity> allData = CacheManager.getCacheManager().getAllData();
                        for (int i = 0; i < allData.size(); i++) {
                            //做判断是否是这个页面的数据
                            if (allData.get(i).getJsonUrl() != null && allData.get(i).getChannelCode().equals(stringChannel)) {
                                String jsonUrl = allData.get(i).getJsonUrl();
                                netWorkDataEntities.add(jsonUrl);//存入set集合
                            }
                        }
                        //在全部查找完成后发送通知
                        handler.sendEmptyMessage(NETWORKSTATE);
                    }
                }
            });
            thread.start();
        }
        super.onStart();
    }

    @Override
    public void onDestroy() {
        if (thread != null) {
            thread.interrupt();
        }
        super.onDestroy();
    }
}
