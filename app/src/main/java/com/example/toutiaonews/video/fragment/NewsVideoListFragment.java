package com.example.toutiaonews.video.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.blankj.utilcode.util.LogUtils;
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
import java.util.List;

public class NewsVideoListFragment extends BaseMVPFragment<NewsVideoPresenterImpl, NewsVideoContract.IVideoView> implements NewsVideoContract.IVideoView {
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
                    VideoDataBean videoDataBean = gson.fromJson(json, VideoDataBean.class);
                    listData.add(videoDataBean);
                    //插入数据库
                    NetWorkDataEntity netWorkDataEntity = new NetWorkDataEntity();
                    netWorkDataEntity.setJsonUrl(json);

                    CacheManager.getCacheManager().insert(netWorkDataEntity);
                }
                videoListAdapter.notifyDataSetChanged();
            } else {
                //没数据就显示提示信息 隐藏列表
                homeNewsVideoListLin.setVisibility(View.VISIBLE);
                videolistRv.setVisibility(View.GONE);
            }
        } else {
            onRoomVideoData();
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

    //拿到数据库中的缓存视频页数据
    private void onRoomVideoData() {
        //从数据库中拿到数据
        List<NetWorkDataEntity> allData = CacheManager.getCacheManager().getAllData();
        for (int i = 0; i < allData.size(); i++) {
            String jsonUrl = allData.get(i).getJsonUrl();
            LogUtils.json(jsonUrl);
            VideoDataBean videoDataBean = new Gson().fromJson(jsonUrl, VideoDataBean.class);
            listData.add(videoDataBean);
        }
    }


}
