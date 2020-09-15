package com.bw.homemodule.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bw.homemodule.R;
import com.bw.homemodule.adapter.ColorTabLayoutAdapter;
import com.bw.homemodule.home.view.DebugFragment;
import com.example.farmework.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;


public class HomeFragment extends BaseFragment implements View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ColorTabLayoutAdapter tabLayoutAdapter;
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private String[] channels,channel_codes;
    private ImageView homeAdd;

    @Override
    protected int bandLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        fragments.clear();
        channels = null;
        channel_codes=null;

        channels = getResources().getStringArray(R.array.channel);
        channel_codes = getResources().getStringArray(R.array.channel_code);

        for (int i = 0; i < channels.length; i++) {
            fragments.add(new DebugFragment(channels[i],channel_codes[i]));
        }

        tabLayoutAdapter = new ColorTabLayoutAdapter(getActivity().getSupportFragmentManager(), fragments, channels);
        viewPager.setAdapter(tabLayoutAdapter);
        viewPager.setOffscreenPageLimit(channels.length);
        tabLayout.setupWithViewPager(viewPager);


    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.home_fragment_viewpager);
        tabLayout = findViewById(R.id.home_fragment_colorTab);
        homeAdd = findViewById(R.id.home_fragment_topAdd);

        homeAdd.setOnClickListener(this);
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.home_fragment_topAdd:
                Toast.makeText(getActivity(), "add", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
