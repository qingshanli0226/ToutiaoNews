package com.example.user.activity;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.user.adapter.MyViewPagerAdapter;
import com.example.user.fragment.LoginFragment;
import com.example.user.fragment.RegisterFragment;
import com.example.video.R;

import java.util.ArrayList;

import static com.example.common.ARouterCommon.USER_LOGIN;

@Route(path = USER_LOGIN)
public class LoginActivity extends BaseActivity {
    private ViewPager userLoginRegisterViewpager;
    private ArrayList<Fragment> arrayList = new ArrayList<>();
    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {
        userLoginRegisterViewpager = (ViewPager) findViewById(R.id.user_login_register_viewpager);

        arrayList.add(new LoginFragment());
        arrayList.add(new RegisterFragment());

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),arrayList);
        userLoginRegisterViewpager.setAdapter(myViewPagerAdapter);
        userLoginRegisterViewpager.setCurrentItem(0);

    }

    public void toRegister() {
        userLoginRegisterViewpager.setCurrentItem(1);
    }

    public void toLogin() {
        userLoginRegisterViewpager.setCurrentItem(0);
    }


    @Override
    public void initData() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_tologin_register;
    }
}
