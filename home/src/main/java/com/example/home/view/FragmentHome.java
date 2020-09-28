package com.example.home.view;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.home.R;
import com.example.home.fragment.FragmentRecommend;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentHome extends BaseFragment {
    private EditText etHomeSearch;
    private TabLayout tabMainTopTitle;
    private ImageView ivHomeAddTitle;
    private ViewPager viewpager;
    private ArrayList<Fragment> list_fragments = new ArrayList<>();
    private int position = 0;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        etHomeSearch = (EditText) findViewById(R.id.et_home_search);
        tabMainTopTitle = (TabLayout) findViewById(R.id.tab_main_top_title);
        tabMainTopTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        ivHomeAddTitle = (ImageView) findViewById(R.id.iv_home_add_title);
        viewpager = (ViewPager) findViewById(R.id.viewpager);

        String[] stringArray = Objects.requireNonNull(getActivity()).getResources().getStringArray(R.array.channel);
        for (int i = 0; i < stringArray.length; i++) {

            if (stringArray[i].equals("视频")){
                tabMainTopTitle.addTab(tabMainTopTitle.newTab().setText(stringArray[i]));
                list_fragments.add(new LjzFragmentVideo(stringArray[i]));
            }else {
                tabMainTopTitle.addTab(tabMainTopTitle.newTab().setText(stringArray[i]));
                list_fragments.add(new FragmentRecommend(stringArray[i]));
            }

        }


        viewpager.setAdapter(new FragmentStatePagerAdapter(getChildFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return list_fragments.get(position);
            }

            @Override
            public int getCount() {
                return list_fragments.size();
            }
        });

        tabMainTopTitle.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                position = tab.getPosition();
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                tabMainTopTitle.setScrollPosition(position,positionOffset,true);
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
        return R.layout.fragment_home;
    }

}
