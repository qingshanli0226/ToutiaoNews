package com.example.toutiaonews.video;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.constant.Constant;
import com.example.toutiaonews.video.adapter.VideoAdapter;
import com.example.toutiaonews.video.entity.Channel;
import com.example.toutiaonews.video.fragment.NewsVideoListFragment;
import com.google.android.material.tabs.TabLayout;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import java.util.ArrayList;
import java.util.List;


public class VideoFragment extends BaseFragment {

    private TabLayout tabChannel;
    private ImageView ivOperation;
    private ViewPager viewPager;

    private List<Channel> mChannelList = new ArrayList<>();
    private List<NewsVideoListFragment> mFrgamentList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        tabChannel = findViewById(R.id.tab_channel);
        ivOperation = findViewById(R.id.iv_operation);
        viewPager = findViewById(R.id.viewPager);

        String[] channels = getResources().getStringArray(R.array.channel_video);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code_video);

        for (int i = 0; i < channelCodes.length; i++) {
            String title = channels[i];
            String code = channelCodes[i];
            mChannelList.add(new Channel(title, code));
        }

        for (Channel channel : mChannelList) {
            NewsVideoListFragment newsFragment = new NewsVideoListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.CHANNEL_CODE, channel.channelCode);
            bundle.putBoolean(Constant.IS_VIDEO_LIST, true);//是否是视频列表页面,]true
            newsFragment.setArguments(bundle);
            mFrgamentList.add(newsFragment);//添加到集合中
        }


        VideoAdapter videoAdapter = new VideoAdapter(mFrgamentList, mChannelList, getChildFragmentManager());

        viewPager.setAdapter(videoAdapter);

        viewPager.setOffscreenPageLimit(mFrgamentList.size());

        tabChannel.setupWithViewPager(viewPager);

        tabChannel.post(new Runnable() {
            @Override
            public void run() {
                //设置最小宽度，使其可以在滑动一部分距离
                ViewGroup slidingTabStrip = (ViewGroup) tabChannel.getChildAt(0);
                slidingTabStrip.setMinimumWidth(slidingTabStrip.getMeasuredWidth() + ivOperation.getMeasuredWidth());
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当页签切换的时候，如果有播放视频，则释放资源
                GSYVideoManager.releaseAllVideos();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public String getCurrentChannelCode() {
        int currentItem = viewPager.getCurrentItem();
        return mChannelList.get(currentItem).channelCode;
    }
}
