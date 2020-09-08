package com.example.net.activity_bean;

public class BaseBean<T> {
    private String Message;
    private String code;
    private T result;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public BaseBean() {
    }

    public BaseBean(String message, String code) {
        Message = message;
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
