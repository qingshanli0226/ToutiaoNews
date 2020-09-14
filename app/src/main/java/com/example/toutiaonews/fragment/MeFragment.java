package com.example.toutiaonews.fragment;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.example.farmework.base.BaseFragment;
import com.example.toutiaonews.R;

public class MeFragment extends BaseFragment {
    private ImageView handPic;
    @Override
    protected int bandLayout() {
        return R.layout.me_fragment;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        handPic = (ImageView) findViewById(R.id.handPic);
        Glide.with(this).load(R.mipmap.my_avatar).transform(new CircleCrop()).into(handPic);
    }
}
