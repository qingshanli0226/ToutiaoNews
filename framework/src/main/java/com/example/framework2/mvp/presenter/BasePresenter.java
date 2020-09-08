package com.example.framework2.mvp.presenter;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;

public class BasePresenter<V extends IView, M extends IModel> implements IPresenter {
    protected M mModel;
    protected V mView;

    public BasePresenter(M mModel, V mView) {
        this.mModel = mModel;
        this.mView = mView;
    }

    @Override
    public void onDestroy() {
        if (mModel != null) {
            mModel.onDestroy();
            mModel = null;
        }
        if (mView != null) {
            mView = null;
        }
        System.gc();
    }
}
