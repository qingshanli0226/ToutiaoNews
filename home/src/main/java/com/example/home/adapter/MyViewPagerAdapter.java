package com.example.home.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class MyViewPagerAdapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> list;
    private ArrayList<String> list_items;

    public MyViewPagerAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragmentList, ArrayList<String> list_items) {
        super(fm);
        this.list = fragmentList;
        this.list_items = list_items;
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return list_items.get(position);
    }
}
