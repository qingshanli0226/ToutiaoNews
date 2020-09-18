package com.example.video.fragment_headlines;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.common.ARouterCommon;
import com.example.framework2.mvp.view.BaseFragment;
import com.example.video.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class FragmentHeadlines extends BaseFragment {
    private TabLayout mTopTitleTab;
    private ViewPager mVideoFragmentBox;

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.publish_video_top) {
            ARouter.getInstance().build(ARouterCommon.VIDEO_LU_ACT).navigation();
        }
    }

    @Override
    public void initView() {
        mTopTitleTab = (TabLayout) findViewById(R.id.top_title_tab);
        findViewById(R.id.publish_video_top).setOnClickListener(this);
        mVideoFragmentBox = (ViewPager) findViewById(R.id.video_fragment_box);
        mTopTitleTab.addTab(mTopTitleTab.newTab().setText("关注"));
        mTopTitleTab.addTab(mTopTitleTab.newTab().setText("推荐"));
        mTopTitleTab.addTab(mTopTitleTab.newTab().setText("游戏"));


        List<Fragment> list = new ArrayList<>();
        list.add(new ConcernFragment());
        list.add(new VideoFragment());
        list.add(new VideoFragment());
        mVideoFragmentBox.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        mTopTitleTab.setScrollPosition(1, 0, true);
        mVideoFragmentBox.setCurrentItem(1);

        mVideoFragmentBox.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTopTitleTab.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mTopTitleTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mVideoFragmentBox.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.fragment_headlines;
    }
}
