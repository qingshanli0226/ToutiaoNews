package com.example.toutiaonews.video.fragment;

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
import com.example.common.constant.Constant;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.dao.NetWorkDataEntity;
import com.example.common.mode.VideoBean;
import com.example.common.mode.VideoDataBean;
import com.example.common.thread.MyRunnable;
import com.example.common.thread.ThreadInterface;
import com.example.framework2.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.PlayerActivity;
import com.example.toutiaonews.utils.UIUtils;
import com.example.toutiaonews.video.adapter.NewsVideoListAdapter;
import com.example.toutiaonews.video.contract.NewsVideoContract;
import com.example.toutiaonews.video.presenter.NewsVideoPresenterImpl;
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

public class NewsVideoListFragment extends BaseMVPFragment<NewsVideoPresenterImpl, NewsVideoContract.IVideoView> implements NewsVideoContract.IVideoView {

    private static final int NETWORKSTATE = 1;

    private String mChannelCode;//视频code
    private boolean isVideoList;
    private boolean isRecommendChannel;
    private String[] channelCodes;
    private EmptyLayout emptyLayout;


    //数据集合
    private List<VideoDataBean> listData = new ArrayList<>();
    private List<VideoBean.DataBean> list = new ArrayList<>();

    private SmartRefreshLayout videolistSr;
    private RecyclerView videolistRv;
    private LinearLayout homeNewsVideoListLin;

    private NewsVideoListAdapter videoListAdapter;
    private VideoDataBean videoDataBean;

    boolean isOneData = true;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_newsvideo;
    }

    @Override
    protected void initData() {
        mChannelCode = getArguments().getString(Constant.CHANNEL_CODE);
        isVideoList = getArguments().getBoolean(Constant.IS_VIDEO_LIST, false);

        channelCodes = UIUtils.getStringArr(R.array.channel_code_video);
        isRecommendChannel = mChannelCode.equals(channelCodes[0]);//是否是推荐频道

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
                        if (allData.get(i).getJsonUrl() != null && allData.get(i).getChannelCode().equals(mChannelCode)) {
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
        videolistSr = findViewById(R.id.videolist_sr);
        videolistRv = findViewById(R.id.videolist_rv);
        emptyLayout = findViewById(R.id.emptyLayout);

        homeNewsVideoListLin = (LinearLayout) findViewById(R.id.homeNewsVideoListLin);

        videoListAdapter = new NewsVideoListAdapter(R.layout.item_video_list, listData);
        videolistRv.setAdapter(videoListAdapter);
        videolistRv.setLayoutManager(new LinearLayoutManager(getContext()));

        //上拉刷新，下拉加载
        videolistSr.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //下拉加载
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME, String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getNewsVideoData(mChannelCode);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //上拉刷新
                //清空数据
                CacheManager.getCacheManager().deleteCodeData(mChannelCode);
                listData.clear();
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME, String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getNewsVideoData(mChannelCode);
            }
        });

        //点击事件
        videoListAdapter.setOnItemClickListener((adapter, view, position) -> {
            //bundle传值
            Bundle bundle = new Bundle();
            bundle.putString(TouTiaoNewsConstant.WEBVIEW_URL, listData.get(position).getArticle_url());
            launchActivity(PlayerActivity.class, bundle);
        });
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
                iHttpPresenter.getNewsVideoData(mChannelCode);
                Log.i("hj123123", "initHttpData: " + mChannelCode);
                isOneData = false;
                //储存第一次执行网络请求的时间戳
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.ONETIME, String.valueOf(System.currentTimeMillis()));
            } else {
                //获取第一次网络请求的时间戳
                String oneTime = CacheManager.getCacheManager().getSPOfString(TouTiaoNewsConstant.ONETIME);
                //判断用户是否是第一次可见此Fragment  和  用户可见时的时间戳 - 第一次网络请求时的时间戳 是否超过5秒
                if (isLook && Long.parseLong(userLookTime) - Long.parseLong(oneTime) > TouTiaoNewsConstant.REFRESHTIME) {
                    //清空数据
                    listData.clear();
                    //储存第二次（隔了一段时间后请求网络数据的boolean）的状态
                    CacheManager.getCacheManager().setSPOfBoolean(TouTiaoNewsConstant.ISTWODATA, true);
                    //请求数据
                    iHttpPresenter.getNewsVideoData(mChannelCode);
                    Log.i("hj123123---", "initHttpData: " + mChannelCode);
                }
            }
        }
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new NewsVideoPresenterImpl();
    }

    //获取数据
    @Override
    public void onVideoData(VideoBean videoBean) {
        if (!videoBean.toString().equals("")) {
            list.addAll(videoBean.getData());
            if (list.size() == 0) {
                emptyLayout.showEmpty();
            }
            Gson gson = new Gson();
            if (videoBean != null) {
                for (int i = 0; i < list.size(); i++) {
                    String json = list.get(i).getContent();
                    videoDataBean = gson.fromJson(json, VideoDataBean.class);
                    listData.add(videoDataBean);
                    //插入数据库
                    NetWorkDataEntity netWorkDataEntity = new NetWorkDataEntity();
                    netWorkDataEntity.setChannelCode(mChannelCode);
                    netWorkDataEntity.setJsonUrl(json);

                    CacheManager.getCacheManager().insert(netWorkDataEntity);
                }
                videoListAdapter.notifyDataSetChanged();
            } else {
                //没数据就显示提示信息 隐藏列表
                homeNewsVideoListLin.setVisibility(View.VISIBLE);
                videolistRv.setVisibility(View.GONE);
            }
        }

        //停止上拉和下拉
        videolistSr.finishRefresh();
        videolistSr.finishLoadMore();
        videoListAdapter.notifyDataSetChanged();
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
        if (checkNetworkState() && list.size() != 0) {
            emptyLayout.hide();
        }
    }

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
                    listData.add(videoDataBean);
                }
                videoListAdapter.notifyDataSetChanged();
            }
        }
    };

}
