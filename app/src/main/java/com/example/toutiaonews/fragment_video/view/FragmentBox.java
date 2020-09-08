package com.example.toutiaonews.fragment_video.view;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment_video.presenter.PresenterVideo;
import com.google.android.material.tabs.TabLayout;

import static com.google.android.material.tabs.TabLayout.MODE_SCROLLABLE;

public class FragmentBox extends BaseFragment<PresenterVideo> {
    private TabLayout mTabTopTitle;
    private ViewPager mFragmentBoxVp;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        mTabTopTitle = (TabLayout) findViewById(R.id.tab_top_title);
        mFragmentBoxVp = (ViewPager) findViewById(R.id.fragment_box_vp);
        mTabTopTitle.setTabMode(MODE_SCROLLABLE);
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("推荐"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("音乐"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("搞笑"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("社会"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("小品"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("影视"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("娱乐"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("呆萌"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("游戏"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("原创"));
        mTabTopTitle.addTab(mTabTopTitle.newTab().setText("开眼"));
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
