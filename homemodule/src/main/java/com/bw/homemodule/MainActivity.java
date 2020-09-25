package com.bw.homemodule;


import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bw.homemodule.home.HomeFragment;
import com.example.farmework.base.BaseActivity;



public class MainActivity extends BaseActivity {
    private HomeFragment homeFragment;

    @Override
    protected void initData() {

    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
        if (homeFragment==null){
            homeFragment = new HomeFragment();
            fragmentTransaction.add(R.id.main_frame, homeFragment);
        }
        fragmentTransaction.show(homeFragment);
        fragmentTransaction.commit();
    }


}
