package com.example.toutiaonews.fragment;


import android.app.PictureInPictureParams;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.common.CustomControl;
import com.example.toutiaonews.R;
import com.wildma.pictureselector.PictureBean;
import com.wildma.pictureselector.PictureSelector;

import java.util.ArrayList;
import java.util.List;

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

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_me,container,false);
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
        customControlSix = view. findViewById(R.id.customControl_six);

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
                Toast.makeText(getContext(), "登录/注册", Toast.LENGTH_SHORT).show();
            }
        });
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

<<<<<<< HEAD
    
=======


    }

    //activityx响应


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SELECT_REQUEST_CODE&&data!=null){
            PictureBean bean= (PictureBean) data.getExtras().get(PictureSelector.PICTURE_RESULT);
            Glide.with(getContext()).load(bean.getPath()).transform(new CircleCrop()).into(myPhoto);
        }
    }
>>>>>>> 3c9e5da62d99cb089b30fe3021295b1684fadecc
}
