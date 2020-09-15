package com.example.toutiaonews.video.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.toutiaonews.video.entity.Channel;
import com.example.toutiaonews.video.fragment.NewsVideoListFragment;

import java.util.List;

public class VideoAdapter extends FragmentStatePagerAdapter {
    private List<NewsVideoListFragment> mFragments;
    private List<Channel> mChannels;

    public VideoAdapter(List<NewsVideoListFragment> fragmentList, List<Channel> channelList, FragmentManager fm) {
        super(fm);
        mFragments = fragmentList;
        mChannels = channelList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).title;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
