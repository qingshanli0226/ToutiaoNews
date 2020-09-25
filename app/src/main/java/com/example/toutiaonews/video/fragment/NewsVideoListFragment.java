package com.example.toutiaonews.video.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
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
import com.example.framework2.base.BaseMVPFragment;
import com.example.toutiaonews.R;
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

public class NewsVideoListFragment extends BaseMVPFragment<NewsVideoPresenterImpl, NewsVideoContract.IVideoView> implements NewsVideoContract.IVideoView {

    private static final int NETWORKSTATE = 1;

    private String mChannelCode;//视频code
    private boolean isVideoList;
    private boolean isRecommendChannel;
    private String[] channelCodes;

    //数据集合
    private List<VideoDataBean> listData = new ArrayList<>();
    private List<VideoBean.DataBean> list = new ArrayList<>();

    private SmartRefreshLayout videolistSr;
    private RecyclerView videolistRv;
    private LinearLayout homeNewsVideoListLin;

    private NewsVideoListAdapter videoListAdapter;
    private VideoDataBean videoDataBean;

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
    }

    @Override
    protected void initView() {

        videolistSr = findViewById(R.id.videolist_sr);
        videolistRv = findViewById(R.id.videolist_rv);
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
    }

    @Override
    protected void initHttpData() {
        iHttpPresenter.getNewsVideoData(mChannelCode);
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

    }

    @Override
    public void hideLoading() {

    }

    private Set<String> netWorkDataEntities = new HashSet<>();//处理重复数据
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
                    videoDataBean = new Gson().fromJson(json, VideoDataBean.class);
                    listData.add(videoDataBean);
                }
                videoListAdapter.notifyDataSetChanged();
            }
        }
    };

    @Override
    public void onStart() {
        //开启子线程
        if (thread == null) {
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
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
