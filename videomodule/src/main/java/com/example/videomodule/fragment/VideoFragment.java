package com.example.videomodule.fragment;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.common.constant.Constant;
import com.example.common.entity.Channel;
import com.example.farmework.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.videomodule.adapter.FragmentAdapter;
import com.example.videomodule.video.view.VideoListFragments;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.Jzvd;
import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class VideoFragment extends BaseFragment {
    private ColorTrackTabLayout videoTab;
    FragmentAdapter fragmentAdapter;
    private ImageView videoImage;
    private List<Channel> mChannelList = new ArrayList<>();

    private ViewPager videoView;
    List<VideoListFragments> fragments = new ArrayList<>();
    @Override
    protected int bandLayout() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {
        initChannelData();
        for (Channel channel:mChannelList) {
            VideoListFragments newsFragment = new VideoListFragments();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
            bundle.putBoolean(Constant.IS_VIDEO_LIST, true);//是否是视频列表页面,true
            newsFragment.setArguments(bundle);
            fragments.add(newsFragment);//添加到集合中
        }
        fragmentAdapter = new FragmentAdapter(getChildFragmentManager(), 1, fragments,mChannelList);
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

    private void initChannelData() {
        String[] channels = getResources().getStringArray(R.array.channel_video);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code_video);
        for (int i = 0; i < channelCodes.length; i++) {
            String title = channels[i];
            String code = channelCodes[i];
            mChannelList.add(new Channel(title, code));
        }
    }

    @Override
    protected void initView() {
        videoTab = (ColorTrackTabLayout) findViewById(R.id.video_tab);
        videoView = (ViewPager) findViewById(R.id.video_view);
        videoImage = (ImageView) findViewById(R.id.video_image);
    }
}
