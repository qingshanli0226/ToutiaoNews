package com.example.promptpagemodule.promptpage.promptpageview;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.example.promptpagemodule.R;

public abstract class PromptPageHolder {
    public View mLoadingView;
    public View mEmptyView;
    public View mRetryView;

    public PromptPageHolder(Context context) {
        init(context);
    }

    private void init(Context context) {
        mEmptyView=setEmpty(context);
        mRetryView=setRetry(context);
        mLoadingView=setLoading(context);
    }

    protected abstract View setRetry(Context context);

    protected abstract View setEmpty(Context context);

    protected abstract View setLoading(Context context);
}
