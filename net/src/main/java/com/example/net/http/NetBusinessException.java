package com.example.net.http;

public class NetBusinessException extends Exception  {
    private String Code;
    private String Message;

    @Override
    public String toString() {
        return "NetBusinessException{" +
                "Code='" + Code + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public NetBusinessException(String code, String message) {
        super(message);
        Code = code;
        Message = message;
    }
}
