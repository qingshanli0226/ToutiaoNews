package com.example.toutiaonews.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.farmework.base.BaseActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.TouTiaoAppLication;
import com.example.toutiaonews.bean.LoginEntity;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import okhttp3.FormBody;

public class LoginActivity extends BaseActivity {
    private EditText username;
    private EditText pas;
    private Button login;
    private String usernamestr;
    private SharedPreferences sp;
    private String passtr;
    private TouTiaoAppLication application;
    private Button req;
    @Override
    protected void initData() {
        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamestr = username.getText().toString().trim();
                passtr = pas.getText().toString().trim();
                getonLogin();
            }
        });
        //注册
        req.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    @Override
    protected int bandLayout() {
        return R.layout.activity_login;
    }

    @Override
    protected void initView() {
        req = (Button) findViewById(R.id.req);
        username = (EditText) findViewById(R.id.username);
        pas = (EditText) findViewById(R.id.pas);
        login = (Button) findViewById(R.id.login);
         application = (TouTiaoAppLication) getApplication();
        sp = application.sp;

    }


    private void getonLogin(){
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("name",usernamestr);
        builder.add("password",passtr);
        FormBody body = builder.build();

        OkGo.<String>post("http://49.233.93.155:8080/login")
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String json = response.body();
                        Gson gson = new Gson();
                        LoginEntity loginEntity = gson.fromJson(json, LoginEntity.class);
                        Log.e("cx", "onSuccess: 000000000000"+json+"------"+usernamestr+"+++++"+passtr );
                        Toast.makeText(LoginActivity.this, loginEntity.getMessage(), Toast.LENGTH_SHORT).show();
                        if (loginEntity.getCode().equals("200")){
                            Log.e("cx", "onSuccess: 11111111111111" );
                            LoginEntity.ResultBean result = loginEntity.getResult();
                            String token = result.getToken();
                            Log.e("cx", "onSuccess: "+token );
                            SharedPreferences.Editor edit = sp.edit();
                            edit.putString("token",token);
                            edit.putBoolean("flog",true);
                            edit.commit();
                            application.tofLogin=true;
                            finish();
                        }
                    }
                });
    }

}
