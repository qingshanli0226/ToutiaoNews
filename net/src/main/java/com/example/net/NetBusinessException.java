package com.example.net;

//定义一个异常类，该异常类封装服务端返回的业务错误
public class NetBusinessException extends Exception {
    private String errorCode;
    private String errorMessage;

    public NetBusinessException(String code, String messsage) {
        super(messsage);
        this.setErrorCode(code);
        this.setErrorMessage(messsage);
    }

    public String getErrorCode() {
        return errorCode;
    }

    private void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    private void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
