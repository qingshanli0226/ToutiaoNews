package com.example.user.activity;

import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.video.R;

import static com.example.common.ARouterCommon.USER_ACTION;

@Route(path = USER_ACTION)
public class Action1Activity extends BaseActivity {

    @Override
    public void onClick(View view) {

    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_action1;
    }
}
