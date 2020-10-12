package com.example.homemodule.home;

import android.view.View;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.common.cache.CacheManager;
import com.example.farmework.base.BaseFragment;
import com.example.homemodule.HomeChannelManagerActivity;
import com.example.homemodule.R;
import com.example.homemodule.adapter.ColorTabLayoutAdapter;
import com.example.homemodule.home.view.NewsListFragment;
import com.example.homemodule.homevideo.view.VideoFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ColorTabLayoutAdapter tabLayoutAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ImageView homeAdd;
    private ArrayList<String> channels = new ArrayList<>();
    private ArrayList<String> channel_codes = new ArrayList<>();


    @Override
    protected int bandLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        fragments.clear();
        channels.clear();
        channel_codes.clear();

        String[] channels = getResources().getStringArray(R.array.channel);
        String[] channel_codes = getResources().getStringArray(R.array.channel_code);

        if (CacheManager.getInstance().homeMindFragments.size()==0){
            for (int i = 0; i < channels.length; i++) {
                this.channels.add(channels[i]);
                this.channel_codes.add(channel_codes[i]);
                if (channel_codes[i].equals("video")) {
                    VideoFragment videoFragment = new VideoFragment(channel_codes[i], channels[i]);
                    fragments.add(videoFragment);
                    CacheManager.getInstance().homeMindFragments.add(videoFragment);
                } else {
                    NewsListFragment newsListFragment = new NewsListFragment(channel_codes[i], channels[i]);
                    fragments.add(newsListFragment);
                    CacheManager.getInstance().homeMindFragments.add(newsListFragment);
                }
            }
        }else {

        }


        tabLayoutAdapter = new ColorTabLayoutAdapter(getActivity().getSupportFragmentManager(), fragments, this.channels);
        viewPager.setAdapter(tabLayoutAdapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.home_fragment_viewpager);
        tabLayout = findViewById(R.id.home_fragment_colorTab);
        homeAdd = findViewById(R.id.home_fragment_topAdd);

        homeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsListFragment newsListFragment = (NewsListFragment) fragments.get(fragments.size() - 1);
                showMessage(newsListFragment.channel);
                tabLayoutAdapter.notifyDataSetChanged();
                launchActivity(HomeChannelManagerActivity.class, null);
            }
        });
    }
}
