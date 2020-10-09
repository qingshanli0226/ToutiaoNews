package com.example.videomodule.video.view;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.cache.CacheManager;
import com.example.common.constant.Constant;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.common.mine.BGRefrushLayout;
import com.example.common.runnable.MyRunnable;
import com.example.common.runnable.ThreadInterface;
import com.example.farmework.base.BaseMVPFragment;
import com.example.promptpagemodule.promptpage.promptpageview.PromptPageViewHolder;
import com.example.promptpagemodule.promptpage.promptpageview.PromptView;
import com.example.videomodule.R;
import com.example.videomodule.adapter.VideoListAdapter;
import com.example.videomodule.video.contract.VideoContract;
import com.example.videomodule.video.presenter.VideoPresenterImpl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;

public class VideoListFragments extends BaseMVPFragment<VideoPresenterImpl, VideoContract.IVideoView> implements VideoContract.IVideoView,BGRefrushLayout.IRefreshListener{
    private BGRefrushLayout videoRefrush;
    private String channelCode;
    private String channel;
    private RecyclerView videoRv;
    private List<VideoDataBean> listVideoData = new ArrayList<>();
    private VideoListAdapter videoListAdapter;
    private long visitTime;
    private PromptView videoPrompt;

    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                videoListAdapter.notifyDataSetChanged();
            }else if(msg.what == 1){
                putRoomData();
            }
        }
    };
    private void putRoomData() {
        MyRunnable myRunnable = new MyRunnable(new ThreadInterface() {
            @Override
            public void readDbCache() {
                //每次加载数据库前把之前集合清干净，防止出现重复
                listVideoData.clear();
                //查询全部
                List<NewsRoomBean> query = CacheManager.getInstance().queryChannel(channelCode);
                for (int i = 0; i < query.size(); i++) {
                    NewsRoomBean newsRoomBean = query.get(i);
                    //找到当前标签的json
                    String jsonUrl = newsRoomBean.getJsonUrl();
                    Log.i("----json", jsonUrl);
                    initGsonData(jsonUrl,1);
                    long newsTime = newsRoomBean.getNewsTime();
                    //下次调用如果当前标签数据存的时间大于100000，删掉
                    if(System.currentTimeMillis() - newsTime >= 100000){
                        CacheManager.getInstance().deletTime(newsTime);
                    }

                }
            }
        });
        CacheManager.executorService.execute(myRunnable);

    }

    private void initGsonData(String jsonurl,int type) {
        Gson gson = new Gson();
        //解析
        VideoDataBean videoDataBean = gson.fromJson(jsonurl, VideoDataBean.class);
        //添加到集合第0项
        if(type == 0){
            listVideoData.add(0,videoDataBean);
        }else{
            listVideoData.add(videoDataBean);
        }
        handler.sendEmptyMessage(0);
    }

    @Override
    protected void initHttpData() {
        if(CacheManager.getInstance().isConnect()){
            //是否请求过网络数据
            boolean isVisit = CacheManager.getInstance().getIsVisit(channel, false);
            if(isVisit){
                visitTime = CacheManager.getInstance().getVisitTime(channelCode, 0);
                //时间戳，下次加载时间
                if(System.currentTimeMillis() - visitTime >= 50000){
                    mPresenter.getVideoData(channelCode, channel);
                    CacheManager.getInstance().putIsVisit(channel, false);
                }else{
                    handler.sendEmptyMessage(1);
                }
            }else{
                mPresenter.getVideoData(channelCode, channel);
            }
        }else{
            handler.sendEmptyMessage(1);
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
        PromptPageViewHolder promptPageViewHolder = new PromptPageViewHolder(getContext());
        videoPrompt.setHolder(promptPageViewHolder);
    }

    @Override
    protected void initView() {
        videoRefrush = (BGRefrushLayout) findViewById(R.id.video_refrush);
        videoRv = (RecyclerView) findViewById(R.id.video_rv);
        videoPrompt = (PromptView) findViewById(R.id.video_prompt);
    }


    @Override
    public void showError(String code, String message) {
        videoPrompt.setErrorMessage(message);
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
        initGsonData(videoBean,0);
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
