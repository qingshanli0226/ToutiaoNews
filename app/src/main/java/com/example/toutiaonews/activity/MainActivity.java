package com.example.toutiaonews.activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.farmework.base.BaseActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.TouTiaoAppLication;
import com.example.toutiaonews.bean.AutoLoginEntity;
import com.example.toutiaonews.fragment.MeFragment;
import com.example.toutiaonews.fragment.MicroFragment;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;


public  class MainActivity extends BaseActivity {
    private int[] selectIcon={R.mipmap.tab_home_selected,R.mipmap.tab_video_selected,R.mipmap.tab_micro_selected,R.mipmap.tab_me_selected};
    private int[] normalIcon={R.mipmap.tab_home_normal,R.mipmap.tab_video_normal,R.mipmap.tab_micro_normal,R.mipmap.tab_me_normal};
    private List<Fragment> listFragment=new ArrayList<>();
    private EasyNavigationBar easyBar;
    private SharedPreferences sp;
    private String token;
    private TouTiaoAppLication application;
    @Override
    protected void initData() {
        startActivity(new Intent(MainActivity.this,WelcomeActivity.class));
         application = (TouTiaoAppLication) getApplication();
        sp = application.sp;
        boolean flog = this.sp.getBoolean("flog", false);
         if (flog){
            token = this.sp.getString("token", "token已经失效");
            getonautoLogin();
        }
    }
    @Override
    protected int bandLayout() {
        return R.layout.activity_main;
    }

    private void getonautoLogin() {
        //自动登录网络请求
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("token",token);
        FormBody body = builder.build();
        OkGo.<String>post("http://49.233.93.155:8080/autoLogin")
                .upRequestBody(body)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        String json = response.body();
                        Gson gson = new Gson();
                        AutoLoginEntity autoLoginEntity = gson.fromJson(json, AutoLoginEntity.class);
                        if (autoLoginEntity.getCode().equals("200")){
                            Toast.makeText(MainActivity.this, autoLoginEntity.getMessage(), Toast.LENGTH_SHORT).show();
                            application.tofLogin=true;
                        }
                    }
                });
    }

    @Override
    protected void initView() {
        String[] stringArray = getResources().getStringArray(R.array.tab);
        listFragment.add(new MicroFragment());
        listFragment.add(new MicroFragment());
        listFragment.add(new MicroFragment());
        listFragment.add(new MeFragment());
        easyBar = (EasyNavigationBar) findViewById(R.id.easyBar);
        easyBar.fragmentManager(getSupportFragmentManager())
                .mode(EasyNavigationBar.NavigationMode.MODE_NORMAL)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .selectTextColor(getColor(R.color.textSelectColor))
                .titleItems(stringArray)
                .fragmentList(listFragment)
                .build();
    }

}
