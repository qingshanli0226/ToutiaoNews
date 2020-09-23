package com.example.toutiaonews;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class ViewPage extends FragmentPagerAdapter {
    private List<Fragment> list;

    public ViewPage(@NonNull FragmentManager fm, List<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Bundle bundle = new Bundle();
        bundle.getInt("name",position+1);
        list.get(position).setArguments(bundle);
        return list.get(position);
    }

    @Override
    public int getCount() {
        return 0;
    }
}
