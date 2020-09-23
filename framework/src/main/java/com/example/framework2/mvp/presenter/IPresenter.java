package com.example.framework2.mvp.presenter;

import com.example.framework2.mvp.model.IModel;
import com.example.framework2.mvp.view.IView;

public interface IPresenter<V extends IView, M extends IModel> {
    void onDestroy();

    void detachView();//解除关联,避免内存泄漏
}
