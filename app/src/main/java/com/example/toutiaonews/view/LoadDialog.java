package com.example.toutiaonews.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.widget.ProgressBar;

public class LoadDialog extends Dialog {
    private ProgressBar progressBar;
    public LoadDialog(Context context) {
        super(context);
        setCancelable(false);
        getWindow().setDimAmount(0f);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(init());
    }

    private View init() {
         progressBar = new ProgressBar(getContext());
        return progressBar;
    }

    public LoadDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected LoadDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }



}
