package com.example.video;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework2.mvp.view.BaseActivity;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.ArrayList;
import java.util.List;

public class VideoLAct extends BaseActivity {
    private RecyclerView mVideoListRv;
    private StandardGSYVideoPlayer videoPlayer;
    private MyAdapter myAdapter;
    private int currentPosition;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        mVideoListRv = (RecyclerView) findViewById(R.id.video_list_rv);

        List<String> list = new ArrayList<>();
        list.add("http://vfx.mtime.cn/Video/2019/03/18/mp4/190318214226685784.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319104618910544.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/19/mp4/190319125415785691.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/17/mp4/190317150237409904.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314223540373995.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/14/mp4/190314102306987969.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/13/mp4/190313094901111138.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312143927981075.mp4");
        list.add("http://vfx.mtime.cn/Video/2019/03/12/mp4/190312083533415853.mp4");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mVideoListRv.setLayoutManager(linearLayoutManager);
        myAdapter = new MyAdapter(R.layout.item_layout, list);

        mVideoListRv.setAdapter(myAdapter);
        LinearSnapHelper linearSnapHelper = new LinearSnapHelper();
        linearSnapHelper.attachToRecyclerView(mVideoListRv);
        mVideoListRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int firstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition();
                View viewByPosition = recyclerView.getLayoutManager().findViewByPosition(firstVisibleItemPosition);

                if (firstVisibleItemPosition > currentPosition) {
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        VideoPlay(firstVisibleItemPosition);
                    }
                } else if (firstVisibleItemPosition < currentPosition && viewByPosition.getX() == 0) {
                    VideoPlay(firstVisibleItemPosition);
                }

            }

        });


    }

    private void VideoPlay(int position) {
        View viewByPosition = mVideoListRv.getLayoutManager().findViewByPosition(position);
        StandardGSYVideoPlayer viewById = viewByPosition.findViewById(R.id.video_play);
        viewByPosition.findViewById(R.id.video_pic).setVisibility(View.GONE);
        if (videoPlayer != null) {
            videoPlayer.onVideoPause();
            videoPlayer.setVisibility(View.GONE);
            videoPlayer = null;
        }
        viewById.setVisibility(View.VISIBLE);
        viewById.setUp(myAdapter.getData().get(position), false, "");
        viewById.startPlayLogic();
        videoPlayer = viewById;
        currentPosition = position;

    }


    @Override
    public void initData() {

    }

    @Override
    public int bandLayout() {
        return R.layout.video_l_layout;
    }
}
