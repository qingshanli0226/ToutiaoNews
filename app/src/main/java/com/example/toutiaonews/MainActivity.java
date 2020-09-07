package com.example.toutiaonews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.toutiaonews.fragment_headlines.FragmentHeadlines;
import com.example.toutiaonews.fragment_home.FragmentHome;
import com.example.toutiaonews.fragment_mine.FragmentMine;
import com.example.toutiaonews.fragment_video.FragmentVideo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private BottomBarLayout bbl;
    private List<Fragment> list;
    @Override
    public void initView() {
        bbl = findViewById(R.id.bbl);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(new FragmentHome());
        list.add(new FragmentVideo());
        list.add(new FragmentHeadlines());
        list.add(new FragmentMine());
        initFragment();
        bbl.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                isShow(currentPosition);
            }
        });
    }


    private void isShow(int currentPosition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            if (i == currentPosition) {
                fragmentTransaction.show(list.get(i));
            } else {
                fragmentTransaction.hide(list.get(i));
            }
        }
        fragmentTransaction.commit();
    }


    @Override
    public void initData() {

    }

    private void initFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < list.size(); i++) {
            fragmentTransaction.add(R.id.fragment_box, list.get(i));
        }
        fragmentTransaction.commit();
    }

    @Override
    public int bandLayout() {
        return R.layout.activity_main;
    }


    @Override
    public void onClick(View view) {

    }
}
