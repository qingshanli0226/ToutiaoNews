package com.example.toutiaonews.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farmework.base.BaseMVPActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.appcontract.TouTiaoAppLication;
import com.example.toutiaonews.bean.LoginEntity;
import com.example.toutiaonews.contract.LoginContract;
import com.example.toutiaonews.presenter.LoginPresenter;
import com.example.toutiaonews.view.LoadDialog;

public class LoginActivity extends BaseMVPActivity<LoginPresenter, LoginContract.ILoginView> implements LoginContract.ILoginView {
    private EditText username;
    private EditText pas;
    private Button login;
    private String usernamestr;
    private SharedPreferences sp;
    private String passtr;
    private TouTiaoAppLication application;
    private Button req;
    private LoadDialog loadDialog;
    @Override
    protected void initPresenter() {
        iHttpPresenter=new LoginPresenter();
    }

    @Override
    protected void initData() {
        //登录
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernamestr = username.getText().toString().trim();
                passtr = pas.getText().toString().trim();
                iHttpPresenter.getLoginData(usernamestr,passtr);
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
        loadDialog=new LoadDialog(this);
    }


//    private void getonLogin(){
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("name",usernamestr);
//        builder.add("password",passtr);
//        FormBody body = builder.build();
//
//        OkGo.<String>post("http://49.233.93.155:8080/login")
//                .upRequestBody(body)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        String json = response.body();
//                        Gson gson = new Gson();
//                        LoginEntity loginEntity = gson.fromJson(json, LoginEntity.class);
//                        Log.e("cx", "onSuccess: 000000000000"+json+"------"+usernamestr+"+++++"+passtr );
//                        Toast.makeText(LoginActivity.this, loginEntity.getMessage(), Toast.LENGTH_SHORT).show();
//                        if (loginEntity.getCode().equals("200")){
//                            Log.e("cx", "onSuccess: 11111111111111" );
//                            LoginEntity.ResultBean result = loginEntity.getResult();
//                            String token = result.getToken();
//                            Log.e("cx", "onSuccess: "+token );
//                            SharedPreferences.Editor edit = sp.edit();
//                            edit.putString("token",token);
//                            edit.putBoolean("flog",true);
//                            edit.commit();
//                            application.tofLogin=true;
//                            finish();
//                        }
//                    }
//                });
//    }

    @Override
    public void onLoginData(LoginEntity loginEntity) {
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

    @Override
    public void showError(String code, String message) {

    }

    @Override
    public void showLoading() {
        loadDialog.show();
    }

    @Override
    public void hideLoading() {
        loadDialog.hide();
    }
}
