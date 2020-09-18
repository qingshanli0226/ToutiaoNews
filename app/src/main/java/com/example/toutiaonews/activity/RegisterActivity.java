package com.example.toutiaonews.activity;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.farmework.base.BaseActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.bean.RegisterEntity;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import okhttp3.FormBody;

public class RegisterActivity extends BaseActivity {
    private EditText username;
    private EditText pas;
    private Button register;
    private String usernamestr;
    private String passtr;
    @Override
    protected void initData() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 usernamestr = username.getText().toString().trim();
                 passtr = pas.getText().toString().trim();
                getonregister();
            }
        });
    }



    @Override
    protected int bandLayout() {
        return R.layout.activity_register;
    }

    @Override
    protected void initView() {
        username = (EditText) findViewById(R.id.username);
        pas = (EditText) findViewById(R.id.pas);
        register = (Button) findViewById(R.id.register);
    }
    private void getonregister() {
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("name",usernamestr);
        builder.add("password",passtr);
        FormBody body = builder.build();
        OkGo.<String>post("http://49.233.93.155:8080/register")
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String json = response.body();
                        Gson gson = new Gson();
                        RegisterEntity registerEntity = gson.fromJson(json, RegisterEntity.class);
                        Log.e("cx", "onSuccess: "+json );
                        Toast.makeText(RegisterActivity.this, registerEntity.getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }

}
