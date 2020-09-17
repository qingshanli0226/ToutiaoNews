package com.example.user.activity;

import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.user.bean.UserBean;
import com.example.user.dao.DaoMaster;
import com.example.user.dao.DaoSession;
import com.example.user.dao.UserBeanDao;
import com.example.video.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.greendao.query.Query;

import java.util.List;

import static com.example.common.ARouterCommon.USER_REGISTER;

@Route(path = USER_REGISTER)
public class RegisterActivity extends BaseActivity {
    private EditText etRegisterUsernameId;
    private EditText etRegisterPasswordId;
    private EditText etRegisterPasswordAgain;
    private Button btRegisterId;
    private TextView tvRegisterHaveId;
    private TextView tvRegisterToLoginId;
    private UserBeanDao userBeanDao;

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

    private void havedUser() {
        Toast.makeText(this, "已有帐号可以直接进行登录", Toast.LENGTH_SHORT).show();
    }

    private void clickRegister() {
        String username_register = etRegisterUsernameId.getText().toString();
        String password_register = etRegisterPasswordId.getText().toString();
        String password_again_register = etRegisterPasswordAgain.getText().toString();
        int size = userBeanDao.loadAll().size();

        if (username_register.length() >= 6 && password_register.length() >= 6){

            Query<UserBean> build = userBeanDao.queryBuilder()
                    .where(UserBeanDao.Properties.Username.eq(username_register))
                    .build();
            List<UserBean> list = build.list();

            if (list.size() == 0){
                if (password_register.equals(password_again_register)){
                    Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
                    userBeanDao.insert(new UserBean(Long.valueOf(size+=1),username_register,password_register, password_again_register,0,0,0));
                    Log.d("ljl", "clickRegister: 已加入数据库");
                    //发送到登录fragment
                    EventBus.getDefault().post(username_register);
                    Log.d("ljl", "clickRegister: 已发送");
                    finish();
                    Log.d("ljl", "clickRegister: 跳转成功");

                }else {
                    Toast.makeText(this, "两次密码不同", Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(this, "用户已存在", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "用户名或密码长度错误", Toast.LENGTH_SHORT).show();
        }
    }

    private void toLogin() {
        finish();
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

        initSql();
    }

    private void initSql() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "user.dp", null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        userBeanDao = daoSession.getUserBeanDao();
    }

    @Override
    public void initData() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_register;
    }
}
