package com.example.video.fragment_video.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.framework2.utils.Tools;
import com.example.video.R;
import com.example.video.dao.DaoManager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE;


public class FragmentBox extends BaseFragment implements View.OnTouchListener {
    private TabLayout mTabTopTitle;
    private ViewPager mFragmentBoxVp;
    private List<Fragment> list;
    private int position;

    @Override
    public void onClick(View view) {

    }


    @Override
    public void initView() {
        mTabTopTitle = (TabLayout) findViewById(R.id.tab_top_title);
        mFragmentBoxVp = (ViewPager) findViewById(R.id.fragment_box_vp);
        mTabTopTitle.setTabMode(MODE_SCROLLABLE);
        DaoManager.getDaoMessage().init(getContext());

        String[] stringArray = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.channel_video);
        String[] stringArray1 = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.channel_code_video);
        list = new ArrayList<>();

        for (int i = 0; i < stringArray1.length; i++) {
            mTabTopTitle.addTab(mTabTopTitle.newTab().setText(stringArray[i]));
            LjzFragmentVideo ljzFragmentVideo = new LjzFragmentVideo();

            Bundle bundle = new Bundle();
            bundle.putString("index", stringArray1[i]);
            ljzFragmentVideo.setArguments(bundle);

            list.add(ljzFragmentVideo);
            Tools.getTools().putVideoCode(stringArray1[i]);
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
                position = tab.getPosition();
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

    public void onPlayPause() {
        LjzFragmentVideo fragment = (LjzFragmentVideo) list.get(position);
        fragment.onPlayPause();
    }


    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.fragment_box;
    }
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
//        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
//            mFragmentBoxVp.getParent().requestDisallowInterceptTouchEvent(true);
//        } else {
//            mFragmentBoxVp.getParent().requestDisallowInterceptTouchEvent(false);
//        }
        return false;
    }
}
