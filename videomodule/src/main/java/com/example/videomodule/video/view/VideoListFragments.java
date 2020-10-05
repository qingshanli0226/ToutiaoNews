package com.example.videomodule.video.view;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.cache.CacheManager;
import com.example.common.constant.Constant;
import com.example.common.dao.NewsRoomBean;
import com.example.common.entity.VideoBean;
import com.example.common.entity.VideoDataBean;
import com.example.common.mine.BGRefrushLayout;
import com.example.farmework.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.videomodule.adapter.VideoListAdapter;
import com.example.videomodule.video.contract.VideoContract;
import com.example.videomodule.video.presenter.VideoPresenterImpl;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class VideoListFragments extends BaseMVPFragment<VideoPresenterImpl, VideoContract.IVideoView> implements VideoContract.IVideoView,BGRefrushLayout.IRefreshListener{
    private BGRefrushLayout videoRefrush;
    private boolean arguments;
    private String channelCode;
    private String channel;
    private RecyclerView videoRv;
    private List<VideoDataBean> listVideoData = new ArrayList<>();
    private VideoListAdapter videoListAdapter;
    private long visitTime;


    private void initJsonData() {
        NewsRoomBean query = CacheManager.getInstance().queryChannel(channelCode);
        Gson gson = new Gson();
        if(query != null){
            String jsonUrl = query.getJsonUrl();
            VideoDataBean videoDataBean = gson.fromJson(jsonUrl, VideoDataBean.class);
            Log.i("----", videoDataBean.getAction_extra());
            listVideoData.add(videoDataBean);
        }
        videoListAdapter = new VideoListAdapter(R.layout.item_listview,listVideoData);
        videoRv.setAdapter(videoListAdapter);
        videoRv.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected void initHttpData() {
        boolean b = CacheManager.getInstance().getisVisit(channel, false);
        visitTime = CacheManager.getInstance().getVisitTime(channelCode, 0);
        if(b){
            if(System.currentTimeMillis() - visitTime >= 10000){
                mPresenter.getVideoData(channelCode, channel);
                CacheManager.getInstance().putisVisit(channel, false);
            }else{
                initJsonData();
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
        arguments  = getArguments().getBoolean(Constant.IS_VIDEO_LIST);
        channelCode  = getArguments().getString(Constant.CHANNEL_CODE);
        channel  = getArguments().getString("channel");
        videoRefrush.attchRecylerView(videoRv);
        videoRefrush.addRefreshListener(this);

    }

    @Override
    protected void initView() {
        videoRefrush = (BGRefrushLayout) findViewById(R.id.video_refrush);
        videoRv = (RecyclerView) findViewById(R.id.video_rv);
    }


    @Override
    public void showError(String code, String message) {
        Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
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
    public void onVideoData(VideoBean videoBean) {
        for (int i = 0; i < videoBean.getData().size(); i++) {
            NewsRoomBean newsRoomBean = new NewsRoomBean();
            newsRoomBean.setChannelId(channelCode);
            Log.i("----json", videoBean.getData().get(i).getContent());
            newsRoomBean.setJsonUrl(videoBean.getData().get(i).getContent());
            newsRoomBean.setNewsTime(System.currentTimeMillis());
            CacheManager.getInstance().insert(newsRoomBean);
        }
        initJsonData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        videoRefrush.cancel();
    }

    @Override
    public void onRefreshComplete() {
        mPresenter.getVideoData(channelCode,channel);
    }
}
