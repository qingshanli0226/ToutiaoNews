package com.example.common.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.OvershootInterpolator;
import android.widget.ScrollView;
        import android.widget.Scroller;

public class CustomScrollView extends ScrollView {
    private Scroller mScroller;
    public CustomScrollView(Context context) {
        super(context);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context,new OvershootInterpolator());
        initGet();
    }

    private void initGet() {
        int mScrollerCurrX = mScroller.getCurrX();//获取mScroller当前水平滚动的位置
        int mScrollerCurrY = mScroller.getCurrY();//获取mScroller当前竖直滚动的位置
        mScroller.forceFinished(true); //停止一切滑动
        mScroller.computeScrollOffset();//判断是否还在滑动中， true滑动中 ，false滑动完成


//        mScroller.startScroll();  //设置滑动 ，执行这个方法，之后执行invalidate()才会触发View里面的computeScroll方法的回调的
    }


}
