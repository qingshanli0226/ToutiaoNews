package com.example.promptpagemodule.promptpage.promptpageview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.promptpagemodule.R;

public class PromptPageViewHolder extends PromptPageHolder {
    private Context context;
    private RetryBtnListener mListener;
    public PromptPageViewHolder(Context context) {
        super(context);
        this.context=context;
    }
    public void setOnLinstener(RetryBtnListener linstener){
        mListener=linstener;
    }

    @Override
    protected View setRetry(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.base_empty,null);
    }

    @Override
    protected View setEmpty(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.base_empty,null);
    }

    @Override
    protected View setLoading(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.base_empty,null);
    }
    public interface RetryBtnListener{
        void onListener();
    }
}
