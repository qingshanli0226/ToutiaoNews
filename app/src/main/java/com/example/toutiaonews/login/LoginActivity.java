package com.example.toutiaonews.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.common.NetCommon;
import com.example.framework.bean.BaseMVPActivity;
import com.example.net.activity_bean.LoginBean;
import com.example.toutiaonews.R;
import com.example.toutiaonews.fragment.MeFragment;
import com.example.toutiaonews.reg.RegMainActivity;

public class LoginActivity extends BaseMVPActivity<LoginPresenterImpl, LoginContract.ILoginView> implements View.OnClickListener, LoginContract.ILoginView {
    private ImageView btnLoginback;
    private EditText loginUsername;
    private EditText loginPwd;
    private Button btnLogin;
    private Button btnGoReg;
    private String FILE_NAME = "mFile"; //文件名
    private String FILE_CONTENT; //文件内容

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
        String name = loginUsername.getText().toString();
        String pwd = loginPwd.getText().toString();
        Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show();
        MeFragment.myName.setText(loginUsername.getText().toString() + "");
        NetCommon.NEW_ISLOGIN=true;
        spWrite(name,pwd,NetCommon.NEW_ISLOGIN);
        finish();
    }
    //存储到sp
    private void spWrite(String name,String pwd,boolean flag) {
        SharedPreferences sp = getSharedPreferences("username", 0);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("name",name);
        edit.putString("pwd",pwd);
        edit.putBoolean("islogin",flag);
        edit.commit();
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