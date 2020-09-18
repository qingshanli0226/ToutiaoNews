package com.example.user.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> list;

    public MyViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragmentList) {
        super(fm);
        this.list = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
