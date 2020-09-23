package com.example.framework2.mvp.presenter;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;

import io.reactivex.disposables.Disposable;

public class BasePresenter<V extends IView, M extends IModel> implements IPresenter {
    protected M mModel;
    protected V mView;
    protected Disposable mDisposable;

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
        detachView();
        System.gc();
    }

    @Override
    public void detachView() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        this.mView = null;
    }
}
