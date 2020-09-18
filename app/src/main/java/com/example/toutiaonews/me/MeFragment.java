package com.example.toutiaonews.me;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.framework2.manager.UserManager;
import com.example.common.mode.LoginBean;
import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.user.LoginOutActivity;
import com.example.user.LoginRegisterActivity;
import com.example.user.UserFeedBackActivity;

public class MeFragment extends BaseFragment implements View.OnClickListener, UserManager.ILoginStatusChangeListener {

    private TextView meUserName;
    private ImageView meUserTouImg;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onStart() {
        super.onStart();
        updateUiLoginStatusChange();
    }

    //根据登录的状态去改变UI
    private void updateUiLoginStatusChange(){
        //判断是否登录
        if(UserManager.getInstance().isUserLogin()){
            meUserName.setText(UserManager.getInstance().getUserName());
            meUserTouImg.setImageResource(R.mipmap.my_avatar);
        } else{
            meUserName.setText("未登录");
            meUserTouImg.setImageResource(R.mipmap.my_user_default);
        }
    }

    @Override
    protected void initView() {
        findViewById(R.id.meTvNight).setOnClickListener(this);
        findViewById(R.id.meToLogin).setOnClickListener(this);
        findViewById(R.id.meUserFeedBack).setOnClickListener(this);
        meUserName = findViewById(R.id.meUserName);
        meUserTouImg = findViewById(R.id.meUserTouImg);
        UserManager.getInstance().setLoginStatusChangeListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            //切换夜间模式
            case R.id.meTvNight:
                break;
                //跳转到登录页面
            case R.id.meToLogin:
                meToLogin();
                break;
                //跳转到用户反馈页面
            case R.id.meUserFeedBack:
                launchActivity(UserFeedBackActivity.class,null);
                break;
        }
    }

    private void meToLogin() {
        //先判断登录状态
        if(UserManager.getInstance().isUserLogin()){
            //登录过  点击跳转到退出登录的页面
            launchActivity(LoginOutActivity.class,null);
        } else{
            //没登录  点击跳转到登录注册页面
            launchActivity(LoginRegisterActivity.class,null);
        }
    }


    @Override
    public void onLoginSuccess(LoginBean loginBean) {
        updateUiLoginStatusChange();
    }

    @Override
    public void onLoginOutSuccess() {
        updateUiLoginStatusChange();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解除
        UserManager.getInstance().removeLoginStatusChangeListener(this);
    }
}
