package com.example.farmework.base;

public interface IPresenter<V extends IView> {
    void attachView(V iHttpView);//和P层建立连接
    void detachView();//解除关联，避免内存泄漏
}
