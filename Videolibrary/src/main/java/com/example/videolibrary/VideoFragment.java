package com.example.videolibrary;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.net.activity_bean.VideoBean;
import com.example.net.activity_bean.entity.Channel;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    private TabLayout fragmentVideoTab;
    private ViewPager fragmentVideoVp;
    private List<Fragment> fragmentList;

    private List<Channel> mChannelList;
    private SQLiteDatabase db;

    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView fragmentVideoSearchPic;

        fragmentVideoTab = view.findViewById(R.id.fragment_video_tab);
        fragmentVideoSearchPic = view.findViewById(R.id.fragment_video_search_pic);
        fragmentVideoVp = view.findViewById(R.id.fragment_video_vp);
        fragmentList = new ArrayList<>();


        //添加tab数据
        tabAdd();

        List<VideoBean> videoBeans = new ArrayList<>();

        for (int i = 0; i < mChannelList.size(); i++) {
            VideoChildFragment videoChildFragment = new VideoChildFragment();
            Bundle bundle = new Bundle();
            bundle.putString("category", mChannelList.get(i).channelCode);
            videoChildFragment.setArguments(bundle);
            fragmentList.add(videoChildFragment);
        }

        fragmentVideoVp.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            }
        });

        tabAndVp();
    }

    private void tabAndVp() {
        fragmentVideoTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                fragmentVideoVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        fragmentVideoVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                fragmentVideoTab.setScrollPosition(position, positionOffset, true);

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    private void tabAdd() {

        String[] channels = getResources().getStringArray(R.array.channel_video);
        String[] channelCodes = getResources().getStringArray(R.array.channel_code_video);
        mChannelList = new ArrayList<>();
        for (int i = 0; i < channelCodes.length; i++) {
            String title = channels[i];
            String code = channelCodes[i];
            mChannelList.add(new Channel(title, code));
            fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText(mChannelList.get(i).title));
        }

    }
}
