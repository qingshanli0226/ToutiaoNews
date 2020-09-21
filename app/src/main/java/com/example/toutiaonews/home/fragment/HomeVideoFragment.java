package com.example.toutiaonews.home.fragment;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.CacheManager;
import com.example.common.constant.TouTiaoNewsConstant;
import com.example.common.mode.VideoBean;
import com.example.common.mode.VideoDataBean;
import com.example.framework2.base.BaseMVPFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.home.adapter.HomeVideoAdapter;
import com.example.toutiaonews.home.contract.HomeVideoContract;
import com.example.toutiaonews.home.presenter.HomeVideoPresenterImpl;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

public class HomeVideoFragment extends BaseMVPFragment<HomeVideoPresenterImpl, HomeVideoContract.HomeVideoView> implements HomeVideoContract.HomeVideoView {
    private RecyclerView homeVideoRv;
    private SmartRefreshLayout homeVideoSmart;
    private LinearLayout homeVideoLin;
    //频道号值
    String stringChannel;
    boolean isVideo;

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
        homeVideoAdapter = new HomeVideoAdapter(R.layout.item_video_list,videoDataBeans);
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
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME,String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getVideoData(stringChannel);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                //刷新
                //清空数据
                videoDataBeans.clear();
                long currentTime = System.currentTimeMillis();
                //并把当前的时间戳存入sp文件中
                CacheManager.getCacheManager().setSPOfString(TouTiaoNewsConstant.CURRENT_TIME,String.valueOf(currentTime));
                //发起网络请求
                iHttpPresenter.getVideoData(stringChannel);
            }
        });
    }

    @Override
    protected void initView() {
        //获取传递过来的频道值
        stringChannel = getArguments().getString(TouTiaoNewsConstant.ARGUMENT_CHANNEL);
        homeVideoRv = (RecyclerView) findViewById(R.id.homeVideoRv);
        homeVideoSmart = (SmartRefreshLayout) findViewById(R.id.homeVideoSmart);
        homeVideoLin = (LinearLayout) findViewById(R.id.homeVideoLin);
    }

    @Override
    protected void initHttpData() {
        //获取数据
        iHttpPresenter.getVideoData(stringChannel);

    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new HomeVideoPresenterImpl();
    }

    @Override
    public void onViewData(VideoBean videoBean) {

        if(!videoBean.toString().equals("")){
            //循环添加数据
            List<VideoBean.DataBean> data = videoBean.getData();
            Gson gson = new Gson();
            for (int i = 0; i < data.size() ; i++) {
                VideoDataBean videoDataBean = gson.fromJson(data.get(i).getContent(), VideoDataBean.class);
                videoDataBeans.add(videoDataBean);
            }

            //停止上拉和下拉
            homeVideoSmart.finishRefresh();
            homeVideoSmart.finishLoadMore();
            //刷新适配器
            homeVideoAdapter.notifyDataSetChanged();
        } else{
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

    }

    @Override
    public void hideLoading() {

    }
}
