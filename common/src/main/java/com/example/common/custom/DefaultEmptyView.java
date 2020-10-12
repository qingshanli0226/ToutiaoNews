package com.example.common.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.common.R;

public class DefaultEmptyView extends RelativeLayout {
    private TextView mTvEmpty;
    private ImageView mIvEmpty;
    public DefaultEmptyView(Context context) {
        super(context);
    }
    public DefaultEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTvEmpty = (TextView) findViewById(R.id.tv_empty);
        mIvEmpty = (ImageView) findViewById(R.id.iv_empty);
    }
    public void setEmptyText(int resid) {
        mTvEmpty.setText(resid);
    }
    public void setEmptyText(String text) {
        mTvEmpty.setText(text);
    }
    public void setEmptyIcon(int resid) {
        mIvEmpty.setImageResource(resid);
    }
}
