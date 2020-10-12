package com.example.common;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.common.custom.DefaultEmptyView;
/**
 * 将布局上的任何一个控件替换成空页面。我的思路是先在被替换的控件的父视图找到它然后移除，用空页面去替换
 */

public class EmptyViewController {
    private Context mContext;
    private CommonEmptyHelper mHelper;
    //targetView 需要展示需要替换为empty界面的同级布局(比如listview)
    public EmptyViewController(Context context, View targetView) {
        if (targetView == null) {
            throw new IllegalArgumentException("You must return a right target view for empty view");
        }
        this.mContext = context;
        this.mHelper = new CommonEmptyHelper(targetView);
    }
    /**
     * 显示默认空页面
     */
    public void showEmptyView(String subtitle) {
        View emptyView = getDefaultEmptyView();
        if (emptyView instanceof DefaultEmptyView) {
            if (!TextUtils.isEmpty(subtitle)) {
                ((DefaultEmptyView) emptyView).setEmptyText(subtitle);
            }
        }
        showLayout(emptyView);
    }
    /**
     * 显示自定义的空页面
     */
    public void showLayout(View emptyLayout) {
        mHelper.showLayout(emptyLayout);
    }
    /**
     * 返回默认空数据layout
     */
    private View getDefaultEmptyView() {
        DefaultEmptyView emptyDefaultView = (DefaultEmptyView) LayoutInflater.from(mContext).inflate(R.layout.item_default, null);
        emptyDefaultView.setEmptyText(R.string.none_data);
        return emptyDefaultView;
    }
    /**
     * 恢复之前的视图
     */
    public void restoreView() {
        mHelper.restoreView();
    }
    private class CommonEmptyHelper {
        private View targetView;
        private ViewGroup parentView;
        private int viewIndex;
        private ViewGroup.LayoutParams params;
        private View currentView;
        private CommonEmptyHelper(View view) {
            super();
            this.targetView = view;
        }
        private void init() {
            params = targetView.getLayoutParams();
            //找到被替换view的父视图
            if (targetView.getParent() != null) {
                parentView = (ViewGroup) targetView.getParent();
            } else {
                parentView = (ViewGroup) targetView.getRootView().findViewById(android.R.id.content);
            }
            //找到该view在父视图中的索引
            int count = parentView.getChildCount();
            for (int index = 0; index < count; index++) {
                if (targetView == parentView.getChildAt(index)) {
                    viewIndex = index;
                    break;
                }
            }
            currentView = targetView;
        }
        public View getCurrentLayout() {
            return currentView;
        }
        private void restoreView() {
            showLayout(targetView);
        }
        private void showLayout(View emptyView) {
            if (parentView == null) {
                init();
            }
            this.currentView = emptyView;
            //如果已经是那个view，那就不需要再进行替换操作了
            if (parentView.getChildAt(viewIndex) != emptyView) {
                ViewGroup parent = (ViewGroup) emptyView.getParent();
                if (parent != null) {
                    parent.removeView(emptyView);
                }
                parentView.removeViewAt(viewIndex);
                parentView.addView(emptyView, viewIndex, params);
            }
        }
        public View inflate(int layoutId) {
            return LayoutInflater.from(targetView.getContext()).inflate(layoutId, null);
        }
        public Context getContext() {
            return targetView.getContext();
        }
        public View getView() {
            return targetView;
        }
    }
}
