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

import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.dao.NetWorkDataEntity;
import com.example.common.mode.VideoBean;
import com.example.common.mode.VideoDataBean;
import com.example.common.thread.MyRunnable;
import com.example.common.thread.ThreadInterface;
import com.example.framework2.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.PlayerActivity;
import com.example.toutiaonews.home.adapter.HomeVideoAdapter;
import com.example.toutiaonews.home.contract.HomeVideoContract;
import com.example.toutiaonews.home.presenter.HomeVideoPresenterImpl;
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

public class HomeVideoFragment extends BaseMVPFragment<HomeVideoPresenterImpl, HomeVideoContract.HomeVideoView> implements HomeVideoContract.HomeVideoView {
    private RecyclerView homeVideoRv;
    private SmartRefreshLayout homeVideoSmart;
    private LinearLayout homeVideoLin;
    //频道号值
    String stringChannel;

    private VideoDataBean videoDataBean;

    boolean isOneData = true;
    private EmptyLayout emptyLayout;


    //视频数据源
    ArrayList<VideoDataBean> videoDataBeans = new ArrayList<>();
    //适配器
    HomeVideoAdapter homeVideoAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home_video;
    }

    @Override
    protected void initData() {
        //创建适配器
        homeVideoAdapter = new HomeVideoAdapter(R.layout.item_video_list, videoDataBeans);
        //设置适配器
        homeVideoRv.setAdapter(homeVideoAdapter);
        homeVideoRv.setLayoutManager(new LinearLayoutManager(getContext()));

        //上拉和下拉
        homeVideoSmart.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //加载
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME, String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getVideoData(stringChannel);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //刷新
                //清空数据
                CacheManager.getCacheManager().deleteCodeData(stringChannel);
                videoDataBeans.clear();
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME, String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getVideoData(stringChannel);
            }
        });

        //点击事件
        homeVideoAdapter.setOnItemClickListener((adapter, view, position) -> {
            //bundle传值
            Bundle bundle = new Bundle();
            bundle.putString(TouTiaoNewsConstant.WEBVIEW_URL, videoDataBeans.get(position).getArticle_url());
            launchActivity(PlayerActivity.class, bundle);
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
        homeVideoRv = (RecyclerView) findViewById(R.id.homeVideoRv);
        homeVideoSmart = (SmartRefreshLayout) findViewById(R.id.homeVideoSmart);
        homeVideoLin = (LinearLayout) findViewById(R.id.homeVideoLin);
        emptyLayout = findViewById(R.id.emptyLayout);
    }

    @Override
    protected void initHttpData() {
        //获取是否是第一次可见此Fragment的状态
        boolean isLook = CacheManager.getCacheManager().getSPOfBoolean(TouTiaoNewsConstant.ISLOOK);
        //获取用户可见时的时间戳
        String userLookTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.USERLOOKTIME);
        if (isUserVisible && isViewCreated) {
            //第一次 一定会执行
            if (isOneData) {
                //获取数据
                iHttpPresenter.getVideoData(stringChannel);
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
                    videoDataBeans.clear();
                    //储存第二次（隔了一段时间后请求网络数据的boolean）的状态
                    CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISTWODATA, true);
                    //请求数据
                    iHttpPresenter.getVideoData(stringChannel);
                    Log.i("hj123123---", "initHttpData: " + stringChannel);
                }
            }

        }
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new HomeVideoPresenterImpl();
    }

    private List<VideoBean.DataBean> data = new ArrayList<>();

    @Override
    public void onViewData(VideoBean videoBean) {

        if (!videoBean.toString().equals("")) {
            //循环添加数据
            data = videoBean.getData();
            if (data.size() == 0) {
                emptyLayout.showEmpty();
            }
            Gson gson = new Gson();
            for (int i = 0; i < data.size(); i++) {
                videoDataBean = gson.fromJson(data.get(i).getContent(), VideoDataBean.class);
                videoDataBeans.add(videoDataBean);
            }

            //停止上拉和下拉
            homeVideoSmart.finishRefresh();
            homeVideoSmart.finishLoadMore();
            //刷新适配器
            homeVideoAdapter.notifyDataSetChanged();
        } else {
            //没数据就显示提示信息 隐藏列表
            homeVideoLin.setVisibility(View.VISIBLE);
            homeVideoRv.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(String code, String message) {
        showToast(message);
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
        if (checkNetworkState() && data.size() != 0) {
            emptyLayout.hide();
        }
    }

    private static final int NETWORKSTATE = 1;
    private Set<String> netWorkDataEntities = new HashSet<>();//处理重复数据
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
                    videoDataBean = new Gson().fromJson(json, VideoDataBean.class);
                    videoDataBeans.add(videoDataBean);
                }
                homeVideoAdapter.notifyDataSetChanged();
            }
        }
    };
}
