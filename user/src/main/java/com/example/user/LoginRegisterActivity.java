package com.example.user;

import android.view.MotionEvent;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.framework2.base.BaseActivity;
import com.example.user.adapter.LoginRegisterAdapter;
import com.example.user.fragment.LoginFragment;
import com.example.user.fragment.RegisterFragment;

import java.util.ArrayList;

public class LoginRegisterActivity extends BaseActivity {
    private ViewPager loginRegisterVp;
    //fragment实例
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    //fragment集合对象
    ArrayList<Fragment> fragments = new ArrayList<>();
    //适配器
    LoginRegisterAdapter loginRegisterAdapter;

    @Override
    protected void initData() {
        fragments.clear();
        //添加fragment对象
        fragments.add(loginFragment);
        fragments.add(registerFragment);
        //适配器
        loginRegisterAdapter = new LoginRegisterAdapter(getSupportFragmentManager(),fragments);
        //设置适配器
        loginRegisterVp.setAdapter(loginRegisterAdapter);
        //设置不能左右滑动
        loginRegisterVp.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    protected void initView() {
        loginRegisterVp = (ViewPager) findViewById(R.id.loginRegisterVp);
        //创建fragment实例对象
        createFragment();
    }

    private void createFragment() {
        if(loginFragment == null){
            loginFragment = new LoginFragment();
        }

        if(registerFragment == null){
            registerFragment = new RegisterFragment();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_login_register;
    }

    //切换fragment的方法
    public void changeFragment(int index){
        loginRegisterVp.setCurrentItem(index);
    }
}
