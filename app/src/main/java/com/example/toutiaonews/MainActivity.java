package com.example.toutiaonews;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.view.View;

import com.chaychan.library.BottomBarItem;
import com.chaychan.library.BottomBarLayout;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.video.fragment_headlines.FragmentHeadlines;
import com.example.toutiaonews.fragment_home.FragmentHome;
import com.example.user.fragment.FragmentMine;
import com.example.video.fragment_video.view.FragmentBox;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private BottomBarLayout bbl;
    private List<Fragment> list;

    /**
     * 初始化视图
     */
    @Override
    public void initView() {
        bbl = findViewById(R.id.bbl);
        initFragment();
        bbl.setOnItemSelectedListener(new BottomBarLayout.OnItemSelectedListener() {
            @Override
            public void onItemSelected(final BottomBarItem bottomBarItem, int previousPosition, int currentPosition) {
                isShow(currentPosition);
            }
        });
    }


    /**
     * 参数:点击的下标  根据下标,进行show hide事务
     *
     * @param currentPosition
     */
    private void isShow(int currentPosition) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        for (int i = 0; i < list.size(); i++) {

            if (i == currentPosition) {
                fragmentTransaction.show(list.get(i));
            } else {
                fragmentTransaction.hide(list.get(i));
                if (i == 1) {
                    FragmentBox fragment = (FragmentBox) list.get(i);
                    fragment.onPlayPause();
                }
            }
        }
        fragmentTransaction.commit();
    }

    @Override
    public void initData() {
    }

    /**
     * 切换 夜间模式
     */
    public void setNight() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * 切换 白天模式
     */
    public void setDay() {
        getDelegate().setLocalNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    /**
     * 初始化Fragment 页面
     */
    private void initFragment() {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(new FragmentHome());
        list.add(new FragmentBox());
        list.add(new FragmentHeadlines());
        list.add(new FragmentMine());
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
