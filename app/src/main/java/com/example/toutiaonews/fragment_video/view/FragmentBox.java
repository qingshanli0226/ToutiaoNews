package com.example.toutiaonews.fragment_video.view;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.R;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE;


public class FragmentBox extends BaseFragment {
    private TabLayout mTabTopTitle;
    private ViewPager mFragmentBoxVp;
    private List<Fragment> list;

    @Override
    public void onClick(View view) {

    }



    @Override
    public void initView() {
        mTabTopTitle = (TabLayout) findViewById(R.id.tab_top_title);
        mFragmentBoxVp = (ViewPager) findViewById(R.id.fragment_box_vp);
        mTabTopTitle.setTabMode(MODE_SCROLLABLE);
        String[] stringArray = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.channel_video);
        list = new ArrayList<>();
        for (String s : stringArray) {
            mTabTopTitle.addTab(mTabTopTitle.newTab().setText(s));
            list.add(new LjzFragmentVideo());
        }
        mFragmentBoxVp.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
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

        mTabTopTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mFragmentBoxVp.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mFragmentBoxVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                mTabTopTitle.setScrollPosition(position, positionOffset, true);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
        return R.layout.fragment_box;
    }
}
