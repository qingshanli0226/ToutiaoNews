package com.example.user.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.framework2.utils.Users;
import com.example.user.activity.LoginActivity;
import com.example.video.R;

import org.greenrobot.eventbus.EventBus;

public class LoginFragment extends BaseFragment {
    private SharedPreferences.Editor editor;
    private EditText etLoginUsernameId;
    private EditText etLoginPasswordId;
    private Button btLoginId;
    private TextView tvLoginForgetId;
    private TextView tvLoginToRegisterId;

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bt_login_id) {
            clickLogin();
        } else if (id == R.id.tv_login_forget_id) {
            foundPassword();
        } else if (id == R.id.tv_login_toRegister_id) {
            toRegister();
        }
    }

    private void clickLogin() {
        String username_login = etLoginUsernameId.getText().toString();
        String password_login = etLoginPasswordId.getText().toString();

        SharedPreferences pref = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
        String nameSP = pref.getString("username","" );
        String password = pref.getString("password", "");
        editor = pref.edit();

        if (username_login.equals(nameSP) && password_login.equals(password)) {
            //先存数据到SP
            editor.putString("username", username_login);
            editor.putString("password", password_login);
        } else {
            //清空editor
            editor.putString("username", "");
            editor.putString("password", "");
            Toast.makeText(getContext(), "请先注册", Toast.LENGTH_SHORT).show();
            return;
            }

        Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        Users user01 = new Users(username_login, password_login, true);
        //发送到登录fragment
        EventBus.getDefault().post(user01);
        //提交数据
        editor.commit();
        getActivity().finish();

    }

    private void foundPassword() {
        Toast.makeText(getContext(), "请直接联系客服", Toast.LENGTH_SHORT).show();
    }

    private void toRegister() {
        LoginActivity activity = (LoginActivity) getActivity();
        activity.toRegister();
    }

    @Override
    public void initView() {

        etLoginUsernameId = (EditText) findViewById(R.id.et_login_username_id);
        etLoginPasswordId = (EditText) findViewById(R.id.et_login_password_id);
        btLoginId = (Button) findViewById(R.id.bt_login_id);
        tvLoginForgetId = (TextView) findViewById(R.id.tv_login_forget_id);
        tvLoginToRegisterId = (TextView) findViewById(R.id.tv_login_toRegister_id);

        btLoginId.setOnClickListener(this);
        tvLoginForgetId.setOnClickListener(this);
        tvLoginToRegisterId.setOnClickListener(this);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_login;
    }

}
