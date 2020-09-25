package com.example.net.activity_bean.entity;

public class ChannelBean {
    private String title;
    private String code;
    private boolean show;//是否显示该fragment
    private boolean sign;//是否显示删除标志

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public ChannelBean(String title,String code, boolean show) {
        this.title = title;
        this.code=code;
        this.show = show;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isShow() {
        return show;
    }

    public void setShow(boolean show) {
        this.show = show;
    }
}
