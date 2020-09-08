package com.example.net.observer;

public interface IObserver<T> {
    void success(T t);

    void error(String errorCode, String errorMessage);
}
