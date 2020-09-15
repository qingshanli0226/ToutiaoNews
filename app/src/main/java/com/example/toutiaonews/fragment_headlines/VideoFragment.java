package com.example.toutiaonews.fragment_headlines;

import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.common.ARouterCommon;
import com.example.framework2.mvp.view.BaseLJZFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment_headlines.adapter.VideoAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class VideoFragment extends BaseLJZFragment implements BaseQuickAdapter.OnItemChildClickListener {
    private SmartRefreshLayout mRefreshListSrl;
    private RecyclerView mVideoListRv;
    private List<String> list;
    private VideoAdapter videoAdapter;

    @Override
    protected int setContentView() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initView() {
        mRefreshListSrl = (SmartRefreshLayout) rootView.findViewById(R.id.refresh_list_srl);
        mVideoListRv = (RecyclerView) rootView.findViewById(R.id.video_list_rv);
        mVideoListRv.setLayoutManager(new StaggeredGridLayoutManager(2, RecyclerView.VERTICAL));
        list = new ArrayList<>();
        videoAdapter = new VideoAdapter(R.layout.item_video, list);
        mVideoListRv.setAdapter(videoAdapter);
        videoAdapter.setOnItemChildClickListener(this);
    }

    public void playLayoutAnimation(Animation animation, boolean isReverse) {
        LayoutAnimationController controller = new LayoutAnimationController(animation);
        controller.setDelay(0.1f);
        controller.setOrder(isReverse ? LayoutAnimationController.ORDER_REVERSE : LayoutAnimationController.ORDER_NORMAL);

        mVideoListRv.setLayoutAnimation(controller);
        mVideoListRv.getAdapter().notifyDataSetChanged();
        mVideoListRv.scheduleLayoutAnimation();
    }

    public static AnimationSet getAnimationSetFromLeft() {
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

        playLayoutAnimation(getAnimationSetFromLeft(), true);
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        ARouter.getInstance().build(ARouterCommon.VIDEO_PLAY_L_ACT).withInt("index", position).navigation();

    }
}
