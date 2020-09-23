package com.example.video.fragment_video.view;

import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.ARouterCommon;
import com.example.framework2.mvp.view.BaseLJZFragment;
import com.example.framework2.mvp.view.LoadingView;
import com.example.framework2.utils.Tools;
import com.example.net.bean.ContentBean;
import com.example.net.bean.Recommend;
import com.example.net.connecct.NetConnect;
import com.example.net.http.HttpManager;
import com.example.video.R;
import com.example.video.bean.SqlBean;
import com.example.video.dao.DaoManager;
import com.example.video.fragment_video.adapter.MyVideoAdapter;
import com.example.video.fragment_video.contract.ContractVideo;
import com.example.video.fragment_video.model.ModelVideo;
import com.example.video.fragment_video.presenter.PresenterVideo;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class LjzFragmentVideo extends BaseLJZFragment<PresenterVideo> implements BaseQuickAdapter.OnItemChildClickListener, OnRefreshLoadMoreListener, ContractVideo.View {
    private RecyclerView mVideoListRv;
    private SmartRefreshLayout mRefreshListSrl;
    private int playIndex = 0;
    private StandardGSYVideoPlayer videoPlayer;
    private ImageView playPic;
    private ArrayList<ContentBean> videoBeanList;
    private MyVideoAdapter myVideoAdapter;
    private String indexStr;
    private LoadingView mLoadingImage;
    private List<String> list = new ArrayList<>();
    private boolean flag = false;

    LjzFragmentVideo(String str) {
        this.indexStr = str;
    }


    @Override
    protected int setContentView() {
        return R.layout.fragment_video;
    }

    /**
     * 播放RecyclerView动画
     *
     * @param animation 动画
     * @param isReverse 顺序
     */
    private void playLayoutAnimation(Animation animation, boolean isReverse) {
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.1f);
        controller.setOrder(isReverse ? LayoutAnimationController.ORDER_REVERSE : LayoutAnimationController.ORDER_NORMAL);

        mVideoListRv.setLayoutAnimation(controller);
        mVideoListRv.scheduleLayoutAnimation();
        myVideoAdapter.notifyDataSetChanged();
    }

    /**
     * 初始化视图
     */
    @Override
    protected void initView() {


        mPresenter = new PresenterVideo(new ModelVideo(), this);
        mLoadingImage = (LoadingView) rootView.findViewById(R.id.loading_image);
        mRefreshListSrl = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_list_srl);
        mVideoListRv = (RecyclerView) rootView.findViewById(R.id.video_list_rv);
        mRefreshListSrl.setOnRefreshLoadMoreListener(this);


        videoBeanList = new ArrayList<>();
        myVideoAdapter = new MyVideoAdapter(R.layout.item_video_box, videoBeanList);
        mVideoListRv.setLayoutManager(new LinearLayoutManager(getContext()));
        mVideoListRv.setAdapter(myVideoAdapter);


        myVideoAdapter.setOnItemChildClickListener(this);

        mVideoListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                videoPause(recyclerView, newState);
            }
        });
        initData();
    }

    /**
     * 在数据库中获取数据
     */
    private void initSqlData() {
        SqlBean select = DaoManager.getDaoMessage().select(indexStr);
        if (select == null) {
            return;
        }
        String videoJson = select.getVideoJson();
        Recommend recommend = HttpManager.getHttpManager().getGson().fromJson(videoJson, Recommend.class);
        for (Recommend.DataBean datum : recommend.getData()) {
            ContentBean contentBean = new Gson().fromJson(datum.getContent(), ContentBean.class);
            videoBeanList.add(contentBean);
        }
        playLayoutAnimation(getAnimationSetFromLeft(), true);

    }

    /**
     * 初始化视频播放数据
     */
    private void initData() {
        list.add("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4");
    }


    /**
     * 数据加载
     */
    @Override
    protected void lazyLoad() {
        if (NetConnect.isNetworkConnected(getContext())) {
            long videoTime = Tools.getTools().getVideoTime(indexStr + "_time");
            mPresenter.getVideoData(videoTime, indexStr);
            mLoadingImage.showEmpty();
        } else {
            initSqlData();
            showMessage("请检查网络连接");

        }

    }

    /**
     * @return 动画
     */
    private AnimationSet getAnimationSetFromLeft() {
        AnimationSet animationSet = new AnimationSet(true);
        TranslateAnimation translateX1 = new TranslateAnimation(RELATIVE_TO_SELF, -1.0f, RELATIVE_TO_SELF, 0.1f,
                RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0);
        translateX1.setDuration(300);
        translateX1.setInterpolator(new DecelerateInterpolator());
        translateX1.setStartOffset(0);

        TranslateAnimation translateX2 = new TranslateAnimation(RELATIVE_TO_SELF, 0.1f, RELATIVE_TO_SELF, -0.1f,
                RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0);
        translateX2.setStartOffset(300);
        translateX2.setInterpolator(new DecelerateInterpolator());
        translateX2.setDuration(50);

        TranslateAnimation translateX3 = new TranslateAnimation(RELATIVE_TO_SELF, -0.1f, RELATIVE_TO_SELF, 0f,
                RELATIVE_TO_SELF, 0, RELATIVE_TO_SELF, 0);
        translateX3.setStartOffset(350);
        translateX3.setInterpolator(new DecelerateInterpolator());
        translateX3.setDuration(50);

        animationSet.addAnimation(translateX1);
        animationSet.addAnimation(translateX2);
        animationSet.addAnimation(translateX3);
        animationSet.setDuration(400);

        return animationSet;
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
                        .load(videoBeanList.get(playIndex))
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
            ARouter.getInstance().build(ARouterCommon.VIDEO_PLAY_ACT).navigation();
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

    /**
     * 定义一个暂停的方法
     */
    public void onPlayPause() {
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
        }
    }

    /**
     * 播放器继续播放
     */
    @Override
    public void onResume() {
        super.onResume();
        if (videoPlayer != null) {
            videoPlayer.onVideoResume();
        }
    }

    /**
     * 播放器暂停
     */
    @Override
    public void onPause() {
        super.onPause();
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
        }
    }

    /**
     * 销毁播放器
     */
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
        mPresenter.getVideoData(System.currentTimeMillis(), indexStr);
        mRefreshListSrl.finishLoadMore(true);
        flag = true;
    }

    /**
     * 下拉刷新
     *
     * @param refreshLayout 刷新的布局
     */
    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        mPresenter.getVideoData(System.currentTimeMillis(), indexStr);
        mRefreshListSrl.finishRefresh(true);
        flag = false;
    }

    /**
     * @param view view
     */
    @Override
    public void onClick(View view) {

    }

    /**
     * @param recommend 实体类
     */
    @Override
    public void getVideoData(Recommend recommend) {
        mLoadingImage.showContent();
        showMessage("加载完成......");
        if (!flag) {
            videoBeanList.clear();
        }

        for (Recommend.DataBean datum : recommend.getData()) {
            ContentBean contentBean = new Gson().fromJson(datum.getContent(), ContentBean.class);
            videoBeanList.add(contentBean);
        }
        Log.d("LjzFragmentVideo", "videoBeanList.size():" + videoBeanList.size());

        if (videoBeanList.size() != 0) {

            playLayoutAnimation(getAnimationSetFromLeft(), true);
        } else {
            initSqlData();
        }
    }
}
