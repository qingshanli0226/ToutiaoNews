package com.example.toutiaonews.me;

import android.view.View;


import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.user.LoginRegisterActivity;

public class MeFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        findViewById(R.id.meTvNight).setOnClickListener(this);
        findViewById(R.id.meToLogin).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //切换夜间模式
            case R.id.meTvNight:
                break;
                //跳转到登录页面
            case R.id.meToLogin:
                launchActivity(LoginRegisterActivity.class,null);
                break;
        }
    }


}
