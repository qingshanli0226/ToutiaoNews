package com.example.promptpagemodule.promptpage.promptpageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.promptpagemodule.R;

public class PromptView extends LinearLayout {
    private final int KEY_NORMAL= 0, KEY_LOADING = 1, KEY_EMPTY= 2, KEY_RETRY = 3;
    private int mViewType = KEY_NORMAL;
    private PromptPageHolder mHolder;
    public PromptView(Context context) {
        super(context);
    }

    public PromptView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public void setHolder(PromptPageHolder holder){
        mHolder=holder;
        initView();
    }

    private void initView() {
        addView(mHolder.mEmptyView,0);
        addView(mHolder.mRetryView,0);
        addView(mHolder.mLoadingView,0);
        setShowOrHide();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mHolder.mEmptyView.setLayoutParams(params);
        mHolder.mRetryView.setLayoutParams(params);
        mHolder.mLoadingView.setLayoutParams(params);
    }
    public void setErrorMessage(String message){
        TextView errorMes = mHolder.mEmptyView.findViewById(R.id.errorMes);
        errorMes.setText(message);
    }
    public void showEmptyView(){
        if (mViewType==KEY_EMPTY)
            return;
        mViewType=KEY_EMPTY;
        setShowOrHide();
    }
    public void showRetryView(){
        if (mViewType==KEY_RETRY)
            return;
        mViewType=KEY_RETRY;
        setShowOrHide();
    }
    public void showLoadingView(){
        if (mViewType==KEY_LOADING)
            return;
        mViewType=KEY_LOADING;
        setShowOrHide();
    }

    private void setShowOrHide() {
        if (mHolder == null){
            throw new RuntimeException("PromptView:请先设置");
        }
        mHolder.mEmptyView.setVisibility(mViewType == KEY_EMPTY ? VISIBLE : GONE);
        mHolder.mRetryView.setVisibility(mViewType == KEY_RETRY ? VISIBLE : GONE);
        mHolder.mLoadingView.setVisibility(mViewType == KEY_LOADING ? VISIBLE : GONE);
    }


}
