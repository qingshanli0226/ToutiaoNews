package com.example.user;

import android.view.View;
import android.widget.TextView;

import com.example.common.mode.LoginOutBean;
import com.example.framework2.base.BaseMVPActivity;
import com.example.framework2.manager.UserManager;
import com.example.user.contract.LoginOutContract;
import com.example.user.presenter.LoginOutPresenterImpl;

public class LoginOutActivity extends BaseMVPActivity<LoginOutPresenterImpl, LoginOutContract.LoginOutView> implements LoginOutContract.LoginOutView {

    private TextView loginOutTv;

    @Override
    protected void initData() {
        loginOutTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //发起退出登录的请求
                iHttpPresenter.loginOut("http://49.233.93.155:8080/logout");
            }
        });
    }

    @Override
    protected void initView() {
        loginOutTv = (TextView) findViewById(R.id.loginOutTv);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_loginout;
    }

    @Override
    protected void initHttpData() {

    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new LoginOutPresenterImpl();
    }

    @Override
    public void onLoginOutData(LoginOutBean loginOutBean) {
        showToast("退出登录成功");
        UserManager.getInstance().loginOutSuccess();
        //销毁这个页面
        finish();
    }

    @Override
    public void showError(String code, String message) {
        showToast(message);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
