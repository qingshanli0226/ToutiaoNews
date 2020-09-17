package com.example.user.fragment;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.framework2.base.BaseMVPFragment;
import com.example.common.mode.LoginBean;
import com.example.common.mode.RegisterBean;
import com.example.user.LoginRegisterActivity;
import com.example.user.R;
import com.example.user.contract.LoginRegisterContract;
import com.example.user.presenter.LoginRegisterImpl;

public class RegisterFragment extends BaseMVPFragment<LoginRegisterImpl, LoginRegisterContract.LoginRegisterView> implements LoginRegisterContract.LoginRegisterView {

    private EditText registerUsername;
    private EditText registerPassword;
    private EditText registerPasswordConfirm;
    private Button registerButton;
    private TextView registerToLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_register;
    }

    @Override
    protected void initData() {
        //跳转到登录Fragment
        registerToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity) getActivity();
                loginRegisterActivity.changeFragment(0);
            }
        });
        //注册
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断用户名 密码 并注册
                judge();
            }
        });


    }

    private void judge() {
        if(registerUsername.getText().toString().trim().equals("")){
            showToast("注册用户名不能为空");
            return;
        }

        if(registerPassword.getText().toString().trim().equals("")){
            showToast("注册密码不能为空");
            return;
        }

        if(registerPasswordConfirm.getText().toString().trim().equals("")){
            showToast("再次输入的密码不能为空");
            return;
        }

        if(!registerPassword.getText().toString().trim().equals(registerPasswordConfirm.getText().toString().trim())){
            showToast("两次输入的密码不一致");
            return;
        }

        iHttpPresenter.register("http://49.233.93.155:8080/register?name="+registerUsername.getText().toString().trim()+"&password="+registerPassword.getText().toString().trim());
    }

    @Override
    protected void initView() {
        registerUsername = (EditText) findViewById(R.id.registerUsername);
        registerPassword = (EditText) findViewById(R.id.registerPassword);
        registerPasswordConfirm = (EditText) findViewById(R.id.registerPasswordConfirm);
        registerButton = (Button) findViewById(R.id.registerButton);
        registerToLogin = (TextView) findViewById(R.id.registerToLogin);
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
        showToast(registerBean.getResult());
        //跳转到登录fragment
        LoginRegisterActivity loginRegisterActivity = (LoginRegisterActivity) getActivity();
        loginRegisterActivity.changeFragment(0);
    }

    @Override
    public void onLoginData(LoginBean loginBean) {

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
