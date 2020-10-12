package com.example.toutiaonews.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.example.common.NetCommon;
import com.example.framework.bean.BaseMVPActivity;
import com.example.net.activity_bean.LoginBean;
import com.example.toutiaonews.R;

import com.example.toutiaonews.reg.RegMainActivity;

public class LoginActivity extends BaseMVPActivity<LoginPresenterImpl, LoginContract.ILoginView> implements View.OnClickListener, LoginContract.ILoginView {
    private ImageView btnLoginback;
    private EditText loginUsername;
    private EditText loginPwd;
    private Button btnLogin;
    private Button btnGoReg;
    private String token;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_loginback:
                finish();
                break;
            case R.id.btn_login:
                String trim = loginUsername.getText().toString().trim();
                String trim1 = loginPwd.getText().toString().trim();
                iHttpPresenter.login(trim, trim1);

                break;
            case R.id.btn_go_reg:
                startActivity(new Intent(LoginActivity.this, RegMainActivity.class));
                finish();
                break;
        }
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new LoginPresenterImpl();
    }

    @Override
    protected void initData() {
    }

    @Override
    protected void initView() {
        btnLoginback = (ImageView) findViewById(R.id.btn_loginback);
        loginUsername = (EditText) findViewById(R.id.login_username);
        loginPwd = (EditText) findViewById(R.id.login_pwd);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnGoReg = (Button) findViewById(R.id.btn_go_reg);
        btnLogin.setOnClickListener(this);
        btnLoginback.setOnClickListener(this);
        btnGoReg.setOnClickListener(this);
    }

        @Override
        protected int getLayoutId() {
            return R.layout.activity_login;
        }

        @Override
        public void onLogin(LoginBean loginBean) {
            String name = loginBean.getResult().getName();
            String pwd = loginBean.getResult().getPassword();
            String token = loginBean.getResult().getToken();
            Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
            NetCommon.NEW_ISLOGIN=true;
            spWrite(name,pwd,token,NetCommon.NEW_ISLOGIN);
            finish();
        }
    //存储到sp
    private void spWrite(String name,String pwd,String token,boolean flag) {
        SPUtils.getInstance().put("name",name);
        SPUtils.getInstance().put("pwd",pwd);
        SPUtils.getInstance().put("token",token);
        SPUtils.getInstance().put("islogin",flag);
    }


    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoaing() {


    }

    @Override
    public void hideLoaing() {
    }

}