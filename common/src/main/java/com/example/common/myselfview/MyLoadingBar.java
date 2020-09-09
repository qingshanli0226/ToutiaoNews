package com.example.common.myselfview;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.common.R;

public class MyLoadingBar extends RelativeLayout {
    private AnimationDrawable drawable;

    public MyLoadingBar(Context context) {
        super(context);
        init(context);
    }

    public MyLoadingBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyLoadingBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.loadingbar_view, this);

        ImageView iv = findViewById(R.id.my_loadingbar);
        drawable = (AnimationDrawable) iv.getDrawable();
        drawable.start();
    }

    //停止动画
    public void stopAnimation() {
        drawable.stop();
    }
}
