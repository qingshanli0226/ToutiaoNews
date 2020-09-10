package com.example.toutiaonews.video;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.video.fragment.MyVideoFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class VideoFragment extends BaseFragment {

    private TabLayout mTab;
    private ViewPager viewPager;

    private String[] titles;//标题列表
    private List<Fragment> fragments = new ArrayList<>();//fragment


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_video;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mTab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewPager);

        titles = new String[]{"推荐", "音乐", "搞笑", "社会", "小品", "生活", "影视", "娱乐", "呆萌", "游戏", "原创", "开眼"};

        //添加fragment
        for (int i = 0; i < titles.length; i++) {
            fragments.add(MyVideoFragment.newInstance());
        }

        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @NonNull
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return titles.length;
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
        //设置每个Tab的内边距
        mTab.setupWithViewPager(viewPager);
    }
}
