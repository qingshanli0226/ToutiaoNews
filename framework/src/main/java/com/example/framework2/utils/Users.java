package com.example.framework2.utils;

public class Users {
    private String username;
    private String password;
    private boolean isLogin;

    public Users(String username, String password, boolean isLogin) {
        this.username = username;
        this.password = password;
        this.isLogin = isLogin;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
