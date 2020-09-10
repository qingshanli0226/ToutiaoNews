package com.example.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class CustomControl extends LinearLayout {
    private TextView myTitle;
    private TextView myContext;
    private ImageView myBackOne;


    public CustomControl(Context context) {
        super(context);
    }

    public CustomControl(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.layout_title,this);
        myTitle = (TextView) findViewById(R.id.my_title);
        myContext = (TextView) findViewById(R.id.my_context);
        myBackOne = (ImageView) findViewById(R.id.my_back_one);


    }

    public CustomControl(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
