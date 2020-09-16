package com.example.video.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.common.entity.Channel;
import com.example.video.mvp.view.VideoListFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private List<VideoListFragment> mFragments;
    private List<Channel> mChannels;

    public FragmentAdapter(@NonNull FragmentManager fm, int behavior,List<VideoListFragment> mFragments, List<Channel> mChannels) {
        super(fm, behavior);
        this.mFragments = mFragments;
        this.mChannels = mChannels;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mChannels.get(position).title;
    }
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
