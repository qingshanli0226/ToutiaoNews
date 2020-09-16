package com.bw.homemodule.myview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.bw.homemodule.R;

public class ToolBar extends LinearLayout {
    public ToolBar(Context context) {
        super(context);
        init(context);
    }

    public ToolBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        View view = LayoutInflater.from(context).inflate(R.layout.activity_main, this);


    }

}
