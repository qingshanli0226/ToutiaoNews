package com.example.toutiaonews;

import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.toutiaonews.fragment.HomeFragment;
import com.example.toutiaonews.fragment.MeFragment;
import com.example.toutiaonews.fragment.MicroFragment;
import com.example.toutiaonews.fragment.VideoFragment;


public class MainActivity extends AppCompatActivity {

    private FrameLayout mainFrame;
    private RadioButton mainTabHome;
    private RadioButton mainTabVideo;
    private RadioButton mainTabMicro;
    private RadioButton mainTabMe;
    private RadioGroup mainTabRg;

    private Fragment homeFragment,videoFragment,microFragment,meFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();


    }

    private void initData() {
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

    private void initView() {
        mainFrame = (FrameLayout) findViewById(R.id.main_frame);
        mainTabHome = (RadioButton) findViewById(R.id.main_tab_home);
        mainTabVideo = (RadioButton) findViewById(R.id.main_tab_video);
        mainTabMicro = (RadioButton) findViewById(R.id.main_tab_micro);
        mainTabMe = (RadioButton) findViewById(R.id.main_tab_me);
        mainTabRg = (RadioGroup) findViewById(R.id.main_tab_rg);
    }


}
