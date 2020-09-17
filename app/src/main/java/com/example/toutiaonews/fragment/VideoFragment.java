package com.example.toutiaonews.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.net.activity_bean.VideoBean;
import com.example.toutiaonews.R;
import com.example.toutiaonews.adapter.VideoAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment {
    private TabLayout fragmentVideoTab;
    private ImageView fragmentVideoSearchPic;
    private ViewPager fragmentVideoVp;
    private List<Fragment> fragmentList;
    private List<VideoBean> videoBeans;



    public VideoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView(view);

        //添加tab数据
        tabAdd();



        videoBeans = new ArrayList<>();
        videoBeans.add(new VideoBean("http://domhttp.kksmg.com/2020/09/15/h264_450k_mp4_SHNewsHD3000000202009151830080000466_aac.mp4",
                "新闻王达到哇嘎哇嘎我给我噶我国", "https://i2.sinaimg.cn/dy/deco/2012/0613/yocc20120613img01/news_logo.png",
                "新闻王", 23));
        videoBeans.add(new VideoBean("http://domhttp.kksmg.com/2020/09/15/h264_450k_mp4_SHNewsHD3000000202009151830080000466_aac.mp4",
                "ASEDRFTYHJ", "https://i2.sinaimg.cn/dy/deco/2012/0613/yocc20120613img01/news_logo.png",
                "新闻王", 23));
        videoBeans.add(new VideoBean("http://domhttp.kksmg.com/2020/09/15/h264_450k_mp4_SHNewsHD3000000202009151830080000466_aac.mp4",
                "SFDGSDHGFJRK", "https://i2.sinaimg.cn/dy/deco/2012/0613/yocc20120613img01/news_logo.png",
                "124323", 23));

        for (int i = 0; i < fragmentVideoTab.getTabCount(); i++) {
            fragmentList.add(new VideoChildFragment(videoBeans));
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
        });

        //tab和Vp的关联
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

    private void initView(@NonNull View view) {
        fragmentVideoTab = view.findViewById(R.id.fragment_video_tab);
        fragmentVideoSearchPic = view.findViewById(R.id.fragment_video_search_pic);
        fragmentVideoVp = view.findViewById(R.id.fragment_video_vp);
        fragmentList = new ArrayList<>();

    }

    private void tabAdd() {
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("推荐"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("音乐"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("搞笑"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("社会"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("小品"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("生活"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("影视"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("影视"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("影视"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("影视"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("影视"));
        fragmentVideoTab.addTab(fragmentVideoTab.newTab().setText("影视"));
    }
}
