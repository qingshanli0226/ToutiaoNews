package com.example.toutiaonews.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.toutiaonews.R;
import com.example.toutiaonews.reg.RegMainActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private ImageView btnLoginback;
    private EditText loginUsername;
    private EditText loginPwd;
    private Button btnLogin;
    private Button btnGoReg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_loginback:
                finish();
                break;
            case R.id.btn_login:

                break;
            case R.id.btn_go_reg:
                startActivity(new Intent(LoginActivity.this, RegMainActivity.class));
                finish();
                break;
        }
    }
}