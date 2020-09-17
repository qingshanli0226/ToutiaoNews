package com.example.toutiaonews.micro;

import android.view.View;
import android.widget.LinearLayout;

import com.example.framework2.base.BaseFragment;
import com.example.toutiaonews.R;

public class MicroFragment extends BaseFragment implements View.OnClickListener {

    private LinearLayout llText;
    private LinearLayout llPhoto;
    private LinearLayout llVideo;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_micro;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        llText = findViewById(R.id.ll_text);
        llPhoto = findViewById(R.id.ll_photo);
        llVideo = findViewById(R.id.ll_video);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_text:
                break;
            case R.id.ll_photo:
                break;
            case R.id.ll_video:
                break;
        }
    }
}
