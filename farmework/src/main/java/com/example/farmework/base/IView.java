package com.example.farmework.base;

public interface IView {
    void showError(String code,String message);
    void showMessage(String message);
    void showLoading();
    void hideLoading();
}
