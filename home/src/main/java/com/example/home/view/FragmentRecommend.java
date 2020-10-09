package com.example.home.view;

import android.content.Intent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.ARouterCommon;
import com.example.framework2.mvp.view.BaseLJZFragment;
import com.example.home.R;
import com.example.home.adapter.MyAdapter;
import com.example.home.contract.ContractRecommend;
import com.example.home.model.ModelRecommend;
import com.example.home.presenter.PresenterRecommend;
import com.example.net.bean.ContentBean;
import com.example.net.bean.Recommend;
import com.example.net.connecct.NetConnect;
import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class FragmentRecommend extends BaseLJZFragment<PresenterRecommend> implements ContractRecommend.View, OnRefreshLoadMoreListener {
    private ArrayList<ContentBean> list_content = new ArrayList<>();
    private SmartRefreshLayout refreshListSrlRecommend;
    private RecyclerView videoListRvRecommend;
    private MyAdapter myAdapter;
    private String indexStr;
    private PresenterRecommend presenterRecommend;
    private boolean flag = false;

    public FragmentRecommend(String str){
        this.indexStr = str;
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_fragment_recommend;
    }

    @Override
    protected void initView() {

        presenterRecommend = new PresenterRecommend(new ModelRecommend(), this);

        refreshListSrlRecommend = rootView.findViewById(R.id.refresh_list_srl_recommend);
        videoListRvRecommend = rootView.findViewById(R.id.video_list_rv_recommend);
        refreshListSrlRecommend.setOnRefreshLoadMoreListener(this);

        myAdapter = new MyAdapter(getContext(),list_content);
        videoListRvRecommend.setAdapter(myAdapter);
        videoListRvRecommend.setLayoutManager(new LinearLayoutManager(getContext()));

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                toDetailPage(list_content.get(position).getUrl());
            }
        });
    }

    @Override
    protected void lazyLoad() {
        if (NetConnect.isNetworkConnected(getContext())) {
            presenterRecommend.getRecommendData(System.currentTimeMillis(), indexStr);

        } else {
            showMessage("请检查网络连接");
        }
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void getRecommendData(Recommend recommendBean) {

        if (!flag) {
            list_content.clear();
        }

        for (Recommend.DataBean datum : recommendBean.getData()) {
            ContentBean contentBean = new Gson().fromJson(datum.getContent(), ContentBean.class);
            list_content.add(contentBean);
        }

        playLayoutAnimation(getAnimationSetFromLeft(), true);

    }

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

    private void playLayoutAnimation(AnimationSet animations, boolean isReverse) {
        LayoutAnimationController controller = new LayoutAnimationController(animations);
        controller.setDelay(0.1f);
        controller.setOrder(isReverse ? LayoutAnimationController.ORDER_REVERSE : LayoutAnimationController.ORDER_NORMAL);

        videoListRvRecommend.setLayoutAnimation(controller);
        videoListRvRecommend.scheduleLayoutAnimation();
        myAdapter.notifyDataSetChanged();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        presenterRecommend.getRecommendData(System.currentTimeMillis(), indexStr);
        refreshListSrlRecommend.finishRefresh(true);
        flag = false;
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        presenterRecommend.getRecommendData(System.currentTimeMillis(), indexStr);
        refreshListSrlRecommend.finishLoadMore(true);
        flag = true;
    }

    public void toDetailPage(String url){
        ARouter.getInstance().build(ARouterCommon.NEWS_DETAIL_PAGE).withString("url",url).navigation();
    }
}
