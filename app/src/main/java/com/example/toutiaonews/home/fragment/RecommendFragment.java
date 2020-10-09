package com.example.toutiaonews.home.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.example.common.mode.News;
import com.example.common.thread.MyRunnable;
import com.example.common.thread.ThreadInterface;
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
import java.util.concurrent.ExecutorService;

import name.quanke.app.libs.emptylayout.EmptyLayout;


public class RecommendFragment extends BaseMVPFragment<RecommendPresenterImpl, RecommendContract.RecommendView> implements RecommendContract.RecommendView {

    private RecyclerView homeRecommendRv;
    private SmartRefreshLayout homeRecommendSmart;
    private LinearLayout homeRecommendLin;
    //数据集合
    ArrayList<HomeRecommendBean.DataBean> dataBeans = new ArrayList<>();
    //数据集合
    ArrayList<News> newsArrayList = new ArrayList<>();
    //适配器
    RecommendAdapter recommendAdapter;
    //频道值
    String stringChannel;

    boolean isOneData = true;
    private EmptyLayout emptyLayout;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_recommend;
    }

    @Override
    protected void initData() {
        //是第一个页面（推荐页面）
        if (stringChannel.equals("")) {
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

        //开启线程
        ExecutorService cachedThreadPool = CacheManager.cachedThreadPool;
        MyRunnable myRunnable = new MyRunnable(new ThreadInterface() {
            @Override
            public void readDbCache() {
                //判断无网络时请求数据库
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
        //提交事务
        cachedThreadPool.execute(myRunnable);
    }

    @Override
    protected void initView() {
        //获取传递过来的频道值
        stringChannel = getArguments().getString(TouTiaoNewsConstant.ARGUMENT_CHANNEL);
        homeRecommendRv = (RecyclerView) findViewById(R.id.homeRecommendRv);
        homeRecommendSmart = (SmartRefreshLayout) findViewById(R.id.homeRecommendSmart);
        homeRecommendLin = (LinearLayout) findViewById(R.id.homeRecommendLin);
        emptyLayout = findViewById(R.id.emptyLayout);
    }

    @Override
    protected void initHttpData() {
        //获取是否是第一次可见此Fragment的状态
        boolean isLook = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISLOOK);
        //获取用户可见时的时间戳
        String userLookTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.USERLOOKTIME);
        //如果用户可见 和 视图创建了
        if (isUserVisible && isViewCreated) {
            //第一次 一定会执行
            if (isOneData) {
                //网络数据获取
                iHttpPresenter.getRecommendData(stringChannel);
                Log.i("hj123123", "initHttpData: " + stringChannel);
                isOneData = false;
                //储存第一次执行网络请求的时间戳
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.ONETIME, String.valueOf(System.currentTimeMillis()));
            } else {
                //获取第一次网络请求的时间戳
                String oneTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.ONETIME);
                //判断用户是否是第一次可见此Fragment  和  用户可见时的时间戳 - 第一次网络请求时的时间戳 是否超过5秒
                if (isLook && Long.parseLong(userLookTime) - Long.parseLong(oneTime) > TouTiaoNewsConstant.REFRESHTIME) {
                    //清空数据
                    newsArrayList.clear();
                    //储存第二次（隔了一段时间后请求网络数据的boolean）的状态
                    CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISTWODATA, true);
                    //请求数据
                    iHttpPresenter.getRecommendData(stringChannel);
                    Log.i("hj123123---", "initHttpData: " + stringChannel);
                }
            }
        }
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
            if (dataBeans.size() == 0) {
                emptyLayout.showEmpty();
            }
            Gson gson = new Gson();
            for (int i = 0; i < dataBeans.size(); i++) {
                //把json数据转换为contentBean对象
                News news = gson.fromJson(dataBeans.get(i).getContent(), News.class);
                newsArrayList.add(news);
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
        if (checkNetworkState()) {
            emptyLayout.showLoading();
        } else {
            emptyLayout.showError();
        }
    }

    @Override
    public void hideLoading() {
        if (checkNetworkState() && dataBeans.size() != 0) {
            emptyLayout.hide();
        }
    }

    private News contentBean;
    private Set<String> netWorkDataEntities = new HashSet<>();//处理重复数据

    private static final int NETWORKSTATE = 1;
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

}
