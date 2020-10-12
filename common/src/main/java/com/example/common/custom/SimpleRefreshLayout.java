package com.example.common.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.example.common.R;

public class SimpleRefreshLayout extends ViewGroup {
    private View mHeader;
    private View mFooter;
    private TextView pullText;
    private onRefreshListener mRefreshListener;
    private int mLastMoveY;
    private int effectiveScrollY = 200;
    private Scroller mLayoutScroller;
    private boolean isPullDown = false;
    private int mLayoutContentHeight;


    public SimpleRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHeader = LayoutInflater.from(context).inflate(R.layout.item_header_layout, null);
        pullText = mHeader.findViewById(R.id.srl_tv_pull_down);
        mFooter = LayoutInflater.from(context).inflate(R.layout.item_footer_layout, null);
        mLayoutScroller = new Scroller(context);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        mHeader.setLayoutParams(params);
        mFooter.setLayoutParams(params);
        addView(mHeader);
        addView(mFooter);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //测量子类
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }

    }


    //布局
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mLayoutContentHeight = 0;
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if (child == mHeader) {
                child.layout(0, 0 - child.getMeasuredHeight(), child.getMeasuredWidth(), 0);

            } else if (child == mFooter) {
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), child.getMeasuredHeight() + mLayoutContentHeight);

            } else {//内容
                child.layout(0, mLayoutContentHeight, child.getMeasuredWidth(), mLayoutContentHeight + child.getMeasuredHeight());
                mLayoutContentHeight += child.getMeasuredHeight();

            }
        }

    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastMoveY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                int dy = mLastMoveY - y;
                if (dy < 0) {//下拉
                    isPullDown = true;
                    if (Math.abs(getScrollY()) <= mHeader.getMeasuredHeight() / 2) {
                        scrollBy(0, dy);
                        if (Math.abs(getScrollY())/2 <= effectiveScrollY) {
                            pullText.setText("下拉刷新");
                        }else {
                            pullText.setText("松开刷新");
                        }
                    }
                } else {//上滑
                    if (Math.abs(getScrollY()) + Math.abs(dy) < mFooter.getMeasuredHeight() / 2) {
                        scrollBy(0, dy);
                        isPullDown = false;
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (isPullDown) {
                    if (Math.abs(getScrollY()) >= effectiveScrollY) {
                        if (mRefreshListener != null) {
                            mRefreshListener.onRefresh();
                        }
                        mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY() - effectiveScrollY);
                        invalidate();
                    } else {
                        mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                        invalidate();
                    }
                } else {
                    if (Math.abs(getScrollY()) >= effectiveScrollY) {
                        if (mRefreshListener != null) {
                            mRefreshListener.onBottomRefresh();
                        }
                        mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY() + effectiveScrollY);
                        invalidate();
                    } else {
                        mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY());
                        invalidate();
                    }
                }
                break;
        }
        mLastMoveY = y;
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mLayoutScroller.computeScrollOffset()) {
            scrollTo(0, mLayoutScroller.getCurrY());
        }
        invalidate();
    }

    public void stopRefresh() {
        mLayoutScroller.startScroll(0, getScrollY(), 0, -getScrollY());
        invalidate();
    }

    public interface onRefreshListener {
        void onRefresh();
        void onBottomRefresh();
    }

    public void setRefreshListener(onRefreshListener listener) {
        mRefreshListener = listener;
    }
}
