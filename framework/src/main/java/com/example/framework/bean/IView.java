package com.example.framework.bean;

/**
 * 定义一个通用的接口
 */
public interface IView {

    void showError(String code, String message);//显示网络申请错误

    void showLoaing();//网络加载的提示

    void hideLoaing();//关闭网络加载的提示

}
