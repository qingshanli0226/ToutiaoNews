package com.example.toutiaonews.reg;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.framework.bean.BaseMVPActivity;
import com.example.net.activity_bean.RegisterBean;
import com.example.toutiaonews.R;

public class RegMainActivity extends BaseMVPActivity<RegPresenterImpl,RegContract.IRegView> implements View.OnClickListener,RegContract.IRegView {
    private ImageView btnRegback;
    private EditText regUsername;
    private EditText regPwd;
    private EditText regPwdd;
    private Button btnReg;

    @Override
    protected void initPresenter() {
       iHttpPresenter=new RegPresenterImpl();

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        btnRegback = (ImageView) findViewById(R.id.btn_regback);
        regUsername = (EditText) findViewById(R.id.reg_username);
        regPwd = (EditText) findViewById(R.id.reg_pwd);
        regPwdd = (EditText) findViewById(R.id.reg_pwdd);
        btnReg = (Button) findViewById(R.id.btn_reg);
        btnReg.setOnClickListener(this);
        btnRegback.setOnClickListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_reg_main;
    }

    @Override
    public void onReg(RegisterBean registerBean) {
        Log.e("zcx", "onReg: "+registerBean.getCode()+"++"+registerBean.getMessage() );
        Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
        finish();
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


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_regback:
                finish();
                break;
            case R.id.btn_reg:
                Log.i("zcx", "注册onClick: "+regUsername.getText().toString()+regPwd.getText().toString()+regPwdd.getText().toString());
                iHttpPresenter.reg(regUsername.getText().toString(), regPwd.getText().toString());
                Toast.makeText(this, "点击注册 ", Toast.LENGTH_SHORT).show();
                break;
        }
    }

}