package com.example.toutiaonews;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.framework2.mvp.view.BaseActivity;



import com.example.toutiaonews.fragment.home.HomeFragment;
import com.example.toutiaonews.fragment.me.MeFragment;
import com.example.toutiaonews.fragment.micro.MicroFragment;
import com.example.videolibrary.VideoFragment;


public class MainActivity extends BaseActivity {

    private FrameLayout mainFrame;
    private RadioButton mainTabHome;
    private RadioButton mainTabVideo;
    private RadioButton mainTabMicro;
    private RadioButton mainTabMe;
    private RadioGroup mainTabRg;

    private Fragment homeFragment,videoFragment,microFragment,meFragment;


    public void initData() {
        //第一次进入显示home
        mainTabHome.setChecked(true);
        homeFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_frame,homeFragment)
                .commit();

        //导航栏的切换
        mainTabRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();


                switch (i) {

                    case R.id.main_tab_home:
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.main_frame, homeFragment);
                            showFragment(fragmentTransaction, homeFragment);
                        } else {
                            showFragment(fragmentTransaction, homeFragment);
                        }
                        break;
                    case R.id.main_tab_video:
                        if (videoFragment == null) {
                            videoFragment = new VideoFragment();
                            fragmentTransaction.add(R.id.main_frame, videoFragment);
                            showFragment(fragmentTransaction, videoFragment);

                        }else {
                            showFragment(fragmentTransaction, videoFragment);
                        }
                        break;
                    case R.id.main_tab_micro:
                        if (microFragment == null) {
                            microFragment = new MicroFragment();
                            fragmentTransaction.add(R.id.main_frame, microFragment);
                            showFragment(fragmentTransaction, microFragment);

                        }else {
                            showFragment(fragmentTransaction, microFragment);
                        }
                        break;
                    case R.id.main_tab_me:
                        if (meFragment == null) {
                            meFragment = new MeFragment();
                            fragmentTransaction.add(R.id.main_frame, meFragment);
                            showFragment(fragmentTransaction, meFragment);

                        }else {
                            showFragment(fragmentTransaction, meFragment);
                        }
                        break;

                }
                fragmentTransaction.commit();
            }

            private void showFragment(FragmentTransaction fragmentTransaction,Fragment fragment) {
                if (homeFragment != null) {
                    fragmentTransaction.hide(homeFragment);
                }
                if (videoFragment != null) {
                    fragmentTransaction.hide(videoFragment);
                }
                if (microFragment != null) {
                    fragmentTransaction.hide(microFragment);
                }
                if (meFragment != null) {
                    fragmentTransaction.hide(meFragment);
                }
                fragmentTransaction.show(fragment);
            }
        });

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_main;
    }

    public void initView() {
        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mainTabHome = (RadioButton) findViewById(R.id.main_tab_home);
        mainTabVideo = (RadioButton) findViewById(R.id.main_tab_video);
        mainTabMicro = (RadioButton) findViewById(R.id.main_tab_micro);
        mainTabMe = (RadioButton) findViewById(R.id.main_tab_me);
        mainTabRg = (RadioGroup) findViewById(R.id.main_tab_rg);
    }


    @Override
    public void onClick(View view) {

    }
}
