package com.example.user.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.framework2.mvp.view.BaseFragment;
import com.example.user.activity.LoginActivity;
import com.example.video.R;


public class RegisterFragment extends BaseFragment {
    private EditText etRegisterUsernameId;
    private EditText etRegisterPasswordId;
    private EditText etRegisterPasswordAgain;
    private Button btRegisterId;
    private TextView tvRegisterHaveId;
    private TextView tvRegisterToLoginId;

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.bt_register_id) {
            clickRegister();
        } else if (id == R.id.tv_register_have_id) {
            havedUser();
        } else if (id == R.id.tv_register_toLogin_id) {
            toLogin();
        }
    }

    private void toLogin() {
        LoginActivity activity = (LoginActivity) getActivity();
        activity.toLogin();
    }

    private void havedUser() {
        Toast.makeText(getContext(), "已有帐号可以直接进行登录", Toast.LENGTH_SHORT).show();
    }

    private void clickRegister() {
        String username_register = etRegisterUsernameId.getText().toString();
        String password_register = etRegisterPasswordId.getText().toString();
        String password_again_register = etRegisterPasswordAgain.getText().toString();

        if (password_register.equals(password_again_register)){
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("data", Context.MODE_PRIVATE).edit();
            editor.putString("username",username_register);
            editor.putString("password",password_register);
            editor.putString("password_again",password_again_register);
            editor.commit();
        }else {
            Toast.makeText(getContext(), "两次密码不同", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(getContext(), "登录成功", Toast.LENGTH_SHORT).show();
        Log.d("ljl", "clickRegister: 已加入数据库");
        toLogin();
        Log.d("ljl", "clickRegister: 跳转成功");

    }

    @Override
    public void initView() {
        etRegisterUsernameId = (EditText) findViewById(R.id.et_register_username_id);
        etRegisterPasswordId = (EditText) findViewById(R.id.et_register_password_id);
        etRegisterPasswordAgain = (EditText) findViewById(R.id.et_register_password_again);
        btRegisterId = (Button) findViewById(R.id.bt_register_id);
        tvRegisterHaveId = (TextView) findViewById(R.id.tv_register_have_id);
        tvRegisterToLoginId = (TextView) findViewById(R.id.tv_register_toLogin_id);

        btRegisterId.setOnClickListener(this);
        tvRegisterHaveId.setOnClickListener(this);
        tvRegisterToLoginId.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void initPresenter() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_register;
    }
}
