package com.example.toutiaonews.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.farmework.base.BaseMVPActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.appcontract.TouTiaoAppLication;
import com.example.toutiaonews.bean.AutoLoginEntity;
import com.example.toutiaonews.contract.AutoLoginContract;
import com.example.toutiaonews.fragment.MeFragment;
import com.example.toutiaonews.fragment.MicroFragment;
import com.example.toutiaonews.presenter.AutoLoginPresenter;
import com.example.toutiaonews.service.TouTiaoIntentService;
import com.example.toutiaonews.view.LoadDialog;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseMVPActivity<AutoLoginPresenter, AutoLoginContract.IAutoLoginView> implements AutoLoginContract.IAutoLoginView {
    private int[] selectIcon = {R.mipmap.tab_home_selected, R.mipmap.tab_video_selected, R.mipmap.tab_micro_selected, R.mipmap.tab_me_selected};
    private int[] normalIcon = {R.mipmap.tab_home_normal, R.mipmap.tab_video_normal, R.mipmap.tab_micro_normal, R.mipmap.tab_me_normal};
    private List<Fragment> listFragment = new ArrayList<>();
    private EasyNavigationBar easyBar;
    private SharedPreferences sp;
    private String token;
    private TouTiaoAppLication application;
    private LoadDialog loadDialog;

    @Override
    protected int bandLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initPresenter() {
        iHttpPresenter = new AutoLoginPresenter();

        application = (TouTiaoAppLication) getApplication();
        sp = application.sp;
        boolean flog = this.sp.getBoolean("flog", false);

        if (flog) {
            token = this.sp.getString("token", "token已经失效");
//            getonautoLogin();
            if (token.equals("token已经失效")) {
                Log.e("cx", "initData: 失效");
            } else {
                iHttpPresenter.getAutoLoginData(token);
            }
        }

    }

    @Override
    protected void initData() {
        startActivity(new Intent(MainActivity.this, WelcomeActivity.class));

    }


    @Override
    protected void initView() {
        loadDialog=new LoadDialog(this);
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

        startService(new Intent(this, TouTiaoIntentService.class));
    }

//    private void getonautoLogin() {
//        //自动登录网络请求
//        FormBody.Builder builder = new FormBody.Builder();
//        builder.add("token",token);
//        FormBody body = builder.build();
//        OkGo.<String>post("http://49.233.93.155:8080/autoLogin")
//                .upRequestBody(body)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(Response<String> response) {
//                        String json = response.body();
//                        Gson gson = new Gson();
//                        AutoLoginEntity autoLoginEntity = gson.fromJson(json, AutoLoginEntity.class);
//                        if (autoLoginEntity.getCode().equals("200")){
//                            Toast.makeText(MainActivity.this, autoLoginEntity.getMessage(), Toast.LENGTH_SHORT).show();
//                            application.tofLogin=true;
//                        }
//                    }
//                });
//    }


    @Override
    public void onAutoLoginData(AutoLoginEntity autoLoginEntity) {

        if (autoLoginEntity.getCode().equals("200")) {
            Toast.makeText(MainActivity.this, autoLoginEntity.getMessage(), Toast.LENGTH_SHORT).show();
            application.tofLogin = true;
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
