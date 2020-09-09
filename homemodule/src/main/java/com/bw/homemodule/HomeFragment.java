package com.bw.homemodule;

import androidx.viewpager.widget.ViewPager;

import com.example.farmework.base.BaseMVPFragment;

import me.weyye.library.colortrackview.ColorTrackTabLayout;

public class HomeFragment extends BaseMVPFragment {
    private ColorTrackTabLayout tabLayout;
    private ViewPager viewPager;


    @Override
    protected void initHttpData() {

    }

    @Override
    protected void initPresenter() {

    }

    @Override
    protected int bandLayout() {
        return R.layout.fragment_home;

    }

    @Override
    protected void onFragmentFirstVisible() {
        super.onFragmentFirstVisible();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);


    }
}
