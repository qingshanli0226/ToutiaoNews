package com.example.video.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.common.constants.Constant;
import com.example.farmework.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.video.adapter.FragmentAdapter;
import com.example.video.mvp.view.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import cn.jzvd.JzvdStd;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class VideoFragment extends BaseFragment {
    private ColorTrackTabLayout videoTab;
    FragmentAdapter fragmentAdapter;
    private ImageView videoImage;


    private ViewPager videoView;
    List<Fragment> fragments = new ArrayList<>();
    @Override
    protected int bandLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {
        String[] stringArray = getResources().getStringArray(R.array.channel_video);
        for (int i = 0; i < stringArray.length; i++) {
            VideoListFragment videoListFragment = new VideoListFragment();
            Bundle bundle = new Bundle();
            bundle.putBoolean(Constant.IS_VIDEO_LIST, true);
            videoListFragment.setArguments(bundle);
            fragments.add(videoListFragment);
        }
        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), 1, fragments, stringArray);
        videoView.setAdapter(fragmentAdapter);
        videoTab.setSelectedTabIndicatorHeight(0);
        videoTab.setupWithViewPager(videoView);
        videoTab.post(new Runnable() {
            @Override
            public void run() {
                //滑到最后往左偏移
                ViewGroup childAt = (ViewGroup) videoTab.getChildAt(0);
                childAt.setMinimumWidth(childAt.getMeasuredWidth() + videoImage.getMeasuredWidth());
            }
        });
        videoView.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Jzvd.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    protected void initView() {
        videoTab = (ColorTrackTabLayout) findViewById(R.id.video_tab);
        videoView = (ViewPager) findViewById(R.id.video_view);
        videoImage = (ImageView) findViewById(R.id.video_image);
    }
}
