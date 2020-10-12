package com.example.videomodule.video.view;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.cache.CacheManager;
import com.example.common.constant.Constant;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.VideoDataBean;
import com.example.common.mine.BGRefrushLayout;
import com.example.common.runnable.MyRunnable;
import com.example.common.runnable.ThreadInterface;
import com.example.farmework.base.BaseMVPFragment;
import com.example.particular.ParticularActivity;
import com.example.videomodule.R;
import com.example.videomodule.adapter.VideoListAdapter;
import com.example.videomodule.video.contract.VideoContract;
import com.example.videomodule.video.presenter.VideoPresenterImpl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VideoListFragments extends BaseMVPFragment<VideoPresenterImpl, VideoContract.IVideoView> implements VideoContract.IVideoView,BGRefrushLayout.IRefreshListener{
    private BGRefrushLayout videoRefrush;
    private String channelCode;
    private String channel;
    private RecyclerView videoRv;
    private List<VideoDataBean> listVideoData = new ArrayList<>();
    private VideoListAdapter videoListAdapter;
    private long visitTime;
    private VideoDataBean videoDataBean;
    private TextView videoText;
    private Set<String> netWorkDataEntities = new HashSet<>();//处理重复数据
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                initSetData();
            }
        }
    };
    //每一次查找数据库或者进行网络请求都会进行去重
    private void initSetData() {
        videoRv.setVisibility(View.VISIBLE);
        videoText.setVisibility(View.GONE);
        listVideoData.clear();
        //去重后的集合
        ArrayList<String> stringArrayList = new ArrayList<>(netWorkDataEntities);
        //遍历集合拿到唯一数据
        for (int i = 0; i < stringArrayList.size(); i++) {
            String json = stringArrayList.get(i);
            videoDataBean = new Gson().fromJson(json, VideoDataBean.class);
            listVideoData.add(0,videoDataBean);
        }
        videoListAdapter.notifyDataSetChanged();

    }
    //查询数据库
    private void putRoomData() {
        MyRunnable myRunnable = new MyRunnable(new ThreadInterface() {
            @Override
            public void readDbCache() {
                //查询全部
                List<NewsRoomBean> query = CacheManager.getInstance().queryChannel(channelCode);
                for (int i = 0; i < query.size(); i++) {
                    NewsRoomBean newsRoomBean = query.get(i);
                    //找到当前标签的json
                    String jsonUrl = newsRoomBean.getJsonUrl();
                    netWorkDataEntities.add(jsonUrl);
                    long newsTime = newsRoomBean.getNewsTime();
                    //下次调用如果当前标签数据存的时间大于100000，删掉
                    if(System.currentTimeMillis() - newsTime >= 100000){
                        CacheManager.getInstance().deletTime(newsTime);
                    }
                }
                handler.sendEmptyMessage(0);
            }
        });
        CacheManager.executorService.execute(myRunnable);

    }

    @Override
    protected void initHttpData() {
        //是否请求过网络数据
        boolean isVisit = CacheManager.getInstance().getIsVisit(channel, false);
        if(isVisit){
            visitTime = CacheManager.getInstance().getVisitTime(channelCode, 0);
            //时间戳，下次加载时间
            if(System.currentTimeMillis() - visitTime >= 50000){
                mPresenter.getVideoData(channelCode, channel);
                CacheManager.getInstance().putIsVisit(channel, false);
            }else{
                putRoomData();
            }
        }else{
            mPresenter.getVideoData(channelCode, channel);
        }
    }


    @Override
    protected void initPresenter() {
        mPresenter = new VideoPresenterImpl();
    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_videolist;
    }

    @Override
    protected void initData() {
        channelCode  = getArguments().getString(Constant.CHANNEL_CODE);
        channel  = getArguments().getString("channel");
        videoRefrush.attchRecylerView(videoRv);
        videoRefrush.addRefreshListener(this);
        videoListAdapter = new VideoListAdapter(R.layout.item_listview,listVideoData);
        videoRv.setAdapter(videoListAdapter);
        videoRv.setLayoutManager(new LinearLayoutManager(getContext()));
        //没有网络查找数据库
        if(!CacheManager.getInstance().isConnect()){
            putRoomData();
            showError("3", "没有网络,请打开网络重试");
        }
        videoListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                VideoDataBean videoDataBean = listVideoData.get(position);
                Intent intent = new Intent(getContext(), ParticularActivity.class);
                intent.putExtra("htmlurl", videoDataBean.getUrl());
                intent.putExtra("picurl", videoDataBean.getShare_large_image().getUrl());
                startActivity(intent);
            }
        });
        if(listVideoData.size() == 0){
            showError("2", "当前无数据");
        }
    }

    @Override
    protected void initView() {
        videoRefrush = (BGRefrushLayout) findViewById(R.id.video_refrush);
        videoRv = (RecyclerView) findViewById(R.id.video_rv);
        videoText = (TextView) findViewById(R.id.video_text);
    }


    @Override
    public void showError(String code, String message) {
        videoRv.setVisibility(View.GONE);
        videoText.setVisibility(View.VISIBLE);
        videoText.setText(message);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), ""+message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    //拿到数据并进行解析
    @Override
    public void onVideoData(String videoBean) {
        netWorkDataEntities.add(videoBean);
        handler.sendEmptyMessage(0);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoRefrush.cancel();
        handler.removeMessages(0);
    }

    @Override
    public void onRefreshComplete() {
        mPresenter.getVideoData(channelCode,channel);
    }


}
