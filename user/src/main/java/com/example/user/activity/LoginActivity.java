package com.example.user.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.example.framework2.mvp.view.BaseActivity;
import com.example.user.MainActivity;
import com.example.user.bean.UserBean;
import com.example.user.dao.DaoMaster;
import com.example.user.dao.DaoSession;
import com.example.user.dao.UserBeanDao;
import com.example.video.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.greenrobot.greendao.query.Query;

import java.util.List;

import static com.example.common.ARouterCommon.USER_LOGIN;

@Route(path = USER_LOGIN)
public class LoginActivity extends BaseActivity {
    private EditText etLoginUsernameId;
    private EditText etLoginPasswordId;
    private Button btLoginId;
    private TextView tvLoginForgetId;
    private TextView tvLoginToRegisterId;
    private UserBeanDao userBeanDaos;

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

    private void foundPassword() {
        Toast.makeText(this, "请直接联系客服", Toast.LENGTH_SHORT).show();
    }

    //点击登录
    private void clickLogin() {
        String username_login = etLoginUsernameId.getText().toString();
        String password_login = etLoginPasswordId.getText().toString();

        if (username_login.isEmpty() != true && password_login.isEmpty() != true){
            Query<UserBean> build = userBeanDaos.queryBuilder()
                    .where(UserBeanDao.Properties.Username.eq(username_login))
                    .build();

            List<UserBean> list = build.list();
            Log.d("ljl", "clickLogin: "+list.size());
            UserBean user = list.get(0);
            Log.d("ljl", "clickLogin: "+username_login+"和"+list.get(0).getUsername());

            if (list.size() != 0){
                if (user.getPassword().equals(password_login)){

                    Toast.makeText(this, "登陆成功", Toast.LENGTH_SHORT).show();

                    saveLoginStatus(true,username_login);
                    Log.d("ljl", "clickLogin: 已记录登录状态");

                    Intent data = new Intent();
                    data.putExtra("username",username_login);
                    data.putExtra("actionNum",user.getActionNum()+"");
                    data.putExtra("fansNum",user.getFansNum()+"");
                    data.putExtra("sevenNum",user.getSevenNum()+"");
                    setResult(RESULT_OK,data);

//                    startActivity(new Intent(this, MainActivity.class));
                    this.finish();

                }else {
                    Toast.makeText(this, "密码不正确", Toast.LENGTH_SHORT).show();
                    Log.d("ljl", "clickLogin: "+password_login);
                }
            }else {
                Toast.makeText(this, "没有此用户，请注册", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show();
        }
    }

    //跳转注册页面
    private void toRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void initView() {
        //注册EventBus
        EventBus.getDefault().register(this);
        Log.d("ljl", "clickRegister: 已注册");

        etLoginUsernameId = (EditText) findViewById(R.id.et_login_username_id);
        etLoginPasswordId = (EditText) findViewById(R.id.et_login_password_id);
        btLoginId = (Button) findViewById(R.id.bt_login_id);
        tvLoginForgetId = (TextView) findViewById(R.id.tv_login_forget_id);
        tvLoginToRegisterId = (TextView) findViewById(R.id.tv_login_toRegister_id);

        btLoginId.setOnClickListener(this);
        tvLoginForgetId.setOnClickListener(this);
        tvLoginToRegisterId.setOnClickListener(this);

        //初始化数据库
        initSql();

    }

    //初始化数据库
    private void initSql() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "user.dp", null);
        SQLiteDatabase writableDatabase = devOpenHelper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(writableDatabase);
        DaoSession daoSession = daoMaster.newSession();
        userBeanDaos = daoSession.getUserBeanDao();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String event){
        etLoginUsernameId.setText(event);
        Log.d("ljl", "clickLogin: 已接收");
    }

    @Override
    public void initData() {

    }

    @Override
    public int bandLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        Log.d("ljl", "clickRegister: 已注销");

    }

    /**
     *保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,String userName){
        //saveLoginStatus(true, userName);
        //loginInfo表示文件名  SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        SharedPreferences sp=getSharedPreferences("loginInfo", MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin", status);
        //存入登录状态时的用户名
        editor.putString("loginUserName", userName);
        //提交修改
        editor.commit();
        Log.d("ljl", "clickLogin: 已记录登录状态");

    }
}
