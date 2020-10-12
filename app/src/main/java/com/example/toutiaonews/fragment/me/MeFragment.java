package com.example.toutiaonews.fragment.me;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.common.EmptyViewController;
import com.example.common.custom.CustomControl;
import com.example.common.NetCommon;
import com.example.toutiaonews.EventMessage;
import com.example.toutiaonews.login.LoginActivity;
import com.example.toutiaonews.R;
import com.example.toutiaonews.reg.RegMainActivity;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import static com.wildma.pictureselector.PictureSelector.SELECT_REQUEST_CODE;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {

    View view;
    private ImageView myPhoto;
    private TextView myName;
    private ImageView myParticulars;
    private TextView myDynamic;
    private TextView myFans;
    private TextView myVisitor;
    private CustomControl customControlOne;
    private CustomControl customControlTwo;
    private CustomControl customControlThree;
    private CustomControl customControlFour;
    private CustomControl customControlFive;
    private CustomControl customControlSix;
    private EventMessage eventMessage;
    private boolean isLogin;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        isLogin = SPUtils.getInstance().getBoolean("islogin", false);
        if (isLogin) {
            String name = SPUtils.getInstance().getString("name", "");
            myName.setText(name);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_me, container, false);
        myPhoto = view.findViewById(R.id.my_photo);
        myName = view.findViewById(R.id.my_name);
        myParticulars = view.findViewById(R.id.my_particulars);
        myDynamic = view.findViewById(R.id.my_dynamic);
        myFans = view.findViewById(R.id.my_fans);
        myVisitor = view.findViewById(R.id.my_visitor);
        customControlOne = view.findViewById(R.id.customControl_one);
        customControlTwo = view.findViewById(R.id.customControl_two);
        customControlThree = view.findViewById(R.id.customControl_three);
        customControlFour = view.findViewById(R.id.customControl_four);
        customControlFive = view.findViewById(R.id.customControl_five);
        customControlSix = view.findViewById(R.id.customControl_six);

        initview();
        initlogin();
        initPhoto();
        return view;
    }

    //头像
    private void initPhoto() {
        myPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PictureSelector.create(MeFragment.this, SELECT_REQUEST_CODE).selectPicture();
            }
        });
    }

    //登录
    private void initlogin() {
        myName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //未登录状态才可以登录显示.
                if (!isLogin){
                    //pop
                    View inflate = LayoutInflater.from(getContext()).inflate(R.layout.login_pop, null, false);
                    PopupWindow popupWindow = new PopupWindow(inflate, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    popupWindow.showAtLocation(myPhoto, Gravity.CENTER, 0, 0);
                    Button loginBtn = inflate.findViewById(R.id.login_btn);
                    Button regiterBtn = inflate.findViewById(R.id.regihter_btn);
                    loginBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            login();
                        }
                    });
                    regiterBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            register();
                        }
                    });
                }else {
                    NetCommon.NEW_ISLOGIN=false;
                    SPUtils.getInstance().put("islogin",NetCommon.NEW_ISLOGIN);
                    Toast.makeText(getContext(), "退出登录   状态："+ NetCommon.NEW_ISLOGIN, Toast.LENGTH_SHORT).show();
                    myName.setText("登录/注册");
                }
            }
        });
    }

    //注册
    private void register() {
        Intent intent = new Intent(getContext(), RegMainActivity.class);
        startActivity(intent);

    }

    //登录
    private void login() {
        Intent intent = new Intent(getContext(), LoginActivity.class);
        startActivityForResult(intent, 1);
    }

    private void initview() {
        TextView title1 = customControlOne.findViewById(R.id.my_title);
        TextView context1 = customControlOne.findViewById(R.id.my_context);
        TextView title2 = customControlTwo.findViewById(R.id.my_title);
        TextView context2 = customControlTwo.findViewById(R.id.my_context);
        TextView title3 = customControlThree.findViewById(R.id.my_title);
        TextView context3 = customControlThree.findViewById(R.id.my_context);
        TextView title4 = customControlFour.findViewById(R.id.my_title);
        TextView context4 = customControlFour.findViewById(R.id.my_context);
        TextView title5 = customControlFive.findViewById(R.id.my_title);
        TextView context5 = customControlFive.findViewById(R.id.my_context);
        TextView title6 = customControlSix.findViewById(R.id.my_title);
        TextView context6 = customControlSix.findViewById(R.id.my_context);
        title1.setText("消息通知");
        title2.setText("头条商城");
        title3.setText("京东特供");
        title4.setText("我要爆料");
        title5.setText("用户反馈");
        title6.setText("系统设置");
        context1.setText(" ");
        context2.setText("邀请好友得200元现金奖励");
        context3.setText("新人领188元红包");
        context4.setText(" ");
        context5.setText(" ");
        context6.setText(" ");

        title1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_REQUEST_CODE && data != null) {
            PictureBean bean = (PictureBean) data.getExtras().get(PictureSelector.PICTURE_RESULT);
            Glide.with(getContext()).load(bean.getPath()).transform(new CircleCrop()).into(myPhoto);
        }

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onPause() {
        super.onPause();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}
