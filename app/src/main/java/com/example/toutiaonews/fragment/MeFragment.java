package com.example.toutiaonews.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.farmework.base.BaseFragment;
import com.example.toutiaonews.R;
import com.example.toutiaonews.appcontract.TouTiaoAppLication;
import com.example.toutiaonews.activity.LoginActivity;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;
import static com.wildma.pictureselector.PictureSelector.PICTURE_RESULT;
public class MeFragment extends BaseFragment {
    private ImageView handPic;
    private ImageView backUnlogin;
    private Button unLogin;
    private RelativeLayout loginAfter;
    private TouTiaoAppLication application;
    @Override
    protected int bandLayout() {
        return R.layout.me_fragment;
    }
    @Override
    protected void initData() {
        //设置头像
        handPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PictureSelector.create(getActivity(), 100).selectPicture(true);
            }
        });
        //登录
        unLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            }
        });
        //退出登录
        backUnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(application, "退出登录", Toast.LENGTH_SHORT).show();
                application.tofLogin=false;
                autoLogin();
                SharedPreferences.Editor edit = application.sp.edit();
                edit.putBoolean("flog",false);
                edit.commit();
            }
        });

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("sg", "onAttach: " );
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("sg", "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("sg", "onResume: " );
        autoLogin();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("sg", "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("sg", "onStop: " );
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.e("sg", "onDetach: " );
    }

    @Override
    protected void initView() {
        unLogin = (Button) findViewById(R.id.unLogin);
        loginAfter = (RelativeLayout) findViewById(R.id.loginAfter);
        handPic = (ImageView) findViewById(R.id.handPic);
        backUnlogin = (ImageView) findViewById(R.id.backUnlogin);
        Glide.with(this).load(R.mipmap.my_avatar).transform(new CircleCrop()).into(handPic);
         application = (TouTiaoAppLication) getActivity().getApplication();
         autoLogin();
    }

    private void autoLogin() {
        if (application.tofLogin){
            unLogin.setVisibility(View.GONE);
            loginAfter.setVisibility(View.VISIBLE);
        }else {
            unLogin.setVisibility(View.VISIBLE);
            loginAfter.setVisibility(View.GONE);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null) {
            PictureBean o = (PictureBean) data.getExtras().get(PICTURE_RESULT);
            if (o.getPath()!=null && o!=null){
                Glide.with(getActivity()).load(o.getPath()).transform(new CircleCrop()).into(handPic);
            }
        }
    }
}
