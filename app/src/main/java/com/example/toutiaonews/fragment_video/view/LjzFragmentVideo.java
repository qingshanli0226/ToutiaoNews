package com.example.toutiaonews.fragment_video.view;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.NavigationCallback;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.ARouterCommon;
import com.example.framework2.mvp.view.BaseLJZFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment_video.adapter.VideoAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LjzFragmentVideo extends BaseLJZFragment implements BaseQuickAdapter.OnItemChildClickListener, OnRefreshLoadMoreListener {
    private RecyclerView mVideoListRv;
    private SmartRefreshLayout mRefreshListSrl;
    private int playIndex = 0;
    private StandardGSYVideoPlayer videoPlayer;
    private ImageView playPic;
    private List<String> list;
    private VideoAdapter videoAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_video;
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView() {
        mRefreshListSrl = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_list_srl);
        mVideoListRv = (RecyclerView) rootView.findViewById(R.id.video_list_rv);
        mRefreshListSrl.setOnRefreshLoadMoreListener(this);
        list = new ArrayList<>();
        videoAdapter = new VideoAdapter(R.layout.item_video_box, list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mVideoListRv.setLayoutManager(linearLayoutManager);
        mVideoListRv.setAdapter(videoAdapter);
        videoAdapter.setOnItemChildClickListener(this);

        mVideoListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                videoPause(recyclerView, newState);
            }
        });
    }

    /**
     * 数据加载
     */
    @Override
    protected void lazyLoad() {
        list.add("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4");

        videoAdapter.notifyDataSetChanged();
    }

    /**
     * 当正在播放的视频 超出视图时将视频暂停
     *
     * @param recyclerView 列表
     * @param newState     触摸的事件
     */
    private void videoPause(@NonNull RecyclerView recyclerView, int newState) {
        int firstVisibleItemPosition = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findFirstVisibleItemPosition();
        int lastVisibleItemPosition = ((LinearLayoutManager) Objects.requireNonNull(recyclerView.getLayoutManager())).findLastVisibleItemPosition();
        if ((firstVisibleItemPosition > playIndex || lastVisibleItemPosition < playIndex) && newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (videoPlayer != null) {
                videoPlayer.onVideoPause();
                playPic.setVisibility(View.VISIBLE);
                RequestOptions requestOptions = new RequestOptions().frame(600000).centerCrop();
                Glide.with(mVideoListRv)
                        .setDefaultRequestOptions(requestOptions)
                        .load(list.get(playIndex))
                        .into(playPic);
            }
        }
    }

    /**
     * 适配器子控件的点击事件
     *
     * @param adapter  适配器
     * @param view     视图
     * @param position 点击的位置
     */
    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        int id = view.getId();
        if (id == R.id.item_video_pic) {
            setVideoPlayer(position);
        }

        if (id == R.id.item_more_img) {
            ARouter.getInstance().build(ARouterCommon.VIDEO_PLAY_ACT).withString("videoUrl", list.get(position)).navigation();
        }
    }

    /**
     * 根据点击的位置 播放视频
     *
     * @param position 点击的位置
     */
    private void setVideoPlayer(int position) {
        View viewByPosition = Objects.requireNonNull(mVideoListRv.getLayoutManager()).findViewByPosition(position);
        assert viewByPosition != null;
        StandardGSYVideoPlayer player = viewByPosition.findViewById(R.id.item_video_GSY);
        ImageView pic = viewByPosition.findViewById(R.id.item_video_pic);
        pic.setVisibility(View.INVISIBLE);
        player.setUp(list.get(position), false, "");
        player.startPlayLogic();
        videoPlayer = player;
        playIndex = position;
        playPic = pic;
    }


    public void onPlayPause() {
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayer != null) {
            videoPlayer.onVideoResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (videoPlayer != null) {
            GSYVideoManager.releaseAllVideos();
        }
    }

    /**
     * 用户是否离开此界面，离开时将视频暂停
     *
     * @param isVisibleToUser 是否显示这个视图
     */
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (videoPlayer != null) {
                videoPlayer.onVideoResume();
            }
        } else {
            if (videoPlayer != null) {
                videoPlayer.onVideoPause();
            }
        }
    }

    /**
     * 上拉加载
     *
     * @param refreshLayout 加载的布局
     */
    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        mRefreshListSrl.finishLoadMore(true);
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout 刷新的布局
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mRefreshListSrl.finishRefresh(true);
    }

    @Override
    public void onClick(View view) {

    }
}
