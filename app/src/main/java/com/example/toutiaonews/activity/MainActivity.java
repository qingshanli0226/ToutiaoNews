package com.example.toutiaonews.activity;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import com.example.farmework.base.BaseActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment.MeFragment;
import com.example.toutiaonews.fragment.MicroFragment;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity {
    private int[] selectIcon={R.mipmap.tab_home_selected,R.mipmap.tab_video_selected,R.mipmap.tab_micro_selected,R.mipmap.tab_me_selected};
    private int[] normalIcon={R.mipmap.tab_home_normal,R.mipmap.tab_video_normal,R.mipmap.tab_micro_normal,R.mipmap.tab_me_normal};
    private List<Fragment> listFragment=new ArrayList<>();
    private EasyNavigationBar easyBar;
    @Override
    protected int bandLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        String[] stringArray = getResources().getStringArray(R.array.tab);
        listFragment.add(new MicroFragment());
        listFragment.add(new MicroFragment());
        listFragment.add(new MicroFragment());
        listFragment.add(new MeFragment());
        easyBar = (EasyNavigationBar) findViewById(R.id.easyBar);
        easyBar.fragmentManager(getSupportFragmentManager())
                .mode(EasyNavigationBar.NavigationMode.MODE_NORMAL)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .selectTextColor(getColor(R.color.textSelectColor))
                .titleItems(stringArray)
                .fragmentList(listFragment)
                .build();
    }

}
