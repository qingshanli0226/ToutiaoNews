package com.example.user.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framework2.manager.UserManager;
import com.example.framework2.base.BaseMVPFragment;
import com.example.common.mode.LoginBean;
import com.example.common.mode.RegisterBean;
import com.example.user.LoginRegisterActivity;
import com.example.user.R;
import com.example.user.contract.LoginRegisterContract;
import com.example.user.presenter.LoginRegisterImpl;

public class LoginFragment extends BaseMVPFragment<LoginRegisterImpl, LoginRegisterContract.LoginRegisterView> implements LoginRegisterContract.LoginRegisterView {

    private EditText loginUsername;
    private EditText loginPassword;
    private Button loginButton;
    private TextView loginToRegister;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login;
    }

    @Override
    protected void initData() {
        //跳转到注册fragment
        loginToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity) getActivity();
                loginRegisterActivity.changeFragment(1);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断用户名 密码 并登陆
                judge();
            }
        });
    }

    private void judge() {
        if(loginUsername.getText().toString().trim().equals("")){
            showToast("登录用户名不能为空");
            return;
        }

        if(loginPassword.getText().toString().trim().equals("")){
            showToast("登录密码不能为空");
            return;
        }

        //登录
        iHttpPresenter.login("http://49.233.93.155:8080/login?name="+loginUsername.getText().toString().trim()+"&password="+loginPassword.getText().toString().trim());

    }

    @Override
    protected void initView() {
        loginUsername = (EditText) findViewById(R.id.loginUsername);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
        loginButton = (Button) findViewById(R.id.loginButton);
        loginToRegister = (TextView) findViewById(R.id.loginToRegister);
    }

    @Override
    protected void initHttpData() {

    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new LoginRegisterImpl();
    }

    @Override
    public void onRegisterData(RegisterBean registerBean) {

    }

    @Override
    public void onLoginData(LoginBean loginBean) {
        showToast("登录成功");
        UserManager.getInstance().setLoginBean(loginBean);
        //跳转到主页面  就相当于结束这个页面
        getActivity().finish();

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
