package com.example.net.activity_bean;

public class ChannelBean {
    private String title;
    private boolean show;
    private boolean sign;

    public boolean isSign() {
        return sign;
    }

    public void setSign(boolean sign) {
        this.sign = sign;
    }

    public ChannelBean(String title, boolean show) {
        this.title = title;
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
