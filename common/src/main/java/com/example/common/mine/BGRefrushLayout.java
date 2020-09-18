package com.example.common.mine;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.example.common.R;

public class BGRefrushLayout extends LinearLayout {
    private View RefrushHeadView;
    public BGRefrushLayout(Context context) {
        super(context);
        init(context,null,0);
    }

    public BGRefrushLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs,0);
    }

    public BGRefrushLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs,defStyleAttr);
    }

    public void init(Context context,AttributeSet attributeSet,int defStyleAttr){
        View inflate = LayoutInflater.from(context)
                .inflate(R.layout.view_refrushlayout, this);
        LinearLayout viewById = inflate.findViewById(R.id.refrush);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onTouchEvent(event);
    }
}
