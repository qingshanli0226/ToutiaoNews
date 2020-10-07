package com.example.common.custom;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.RequiresApi;

import com.example.common.R;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class CustomToolbar extends Toolbar {
    private View view;
    private ImageView toolbarBack;
    private TextView toolbarTitle;



    public CustomToolbar(Context context) {
        super(context);
    }

    public CustomToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (view==null) {
            view = LayoutInflater.from(context).inflate(R.layout.layout_toolbar, null);
            toolbarBack = view.findViewById(R.id.toolbar_back);
            toolbarTitle = view.findViewById(R.id.toolbar_title);
            //如果没有这行代码，title不会居中显示
            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(view,layoutParams);
        }
    }

    public CustomToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }




}
