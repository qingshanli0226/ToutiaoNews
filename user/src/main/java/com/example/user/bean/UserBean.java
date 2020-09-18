package com.example.user.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

@Entity
public class UserBean {
    @Id(autoincrement = true)
    Long user_id;
    private String username;
    private String password;
    private String passwordAgain;
    private int actionNum;
    private int fansNum;
    private int sevenNum;
    @Generated(hash = 577129318)
    public UserBean(Long user_id, String username, String password,
            String passwordAgain, int actionNum, int fansNum, int sevenNum) {
        this.user_id = user_id;
        this.username = username;
        this.password = password;
        this.passwordAgain = passwordAgain;
        this.actionNum = actionNum;
        this.fansNum = fansNum;
        this.sevenNum = sevenNum;
    }

    @Generated(hash = 1203313951)
    public UserBean() {
    }

    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPasswordAgain() {
        return this.passwordAgain;
    }
    public void setPasswordAgain(String passwordAgain) {
        this.passwordAgain = passwordAgain;
    }
    public int getActionNum() {
        return this.actionNum;
    }
    public void setActionNum(int actionNum) {
        this.actionNum = actionNum;
    }
    public int getFansNum() {
        return this.fansNum;
    }
    public void setFansNum(int fansNum) {
        this.fansNum = fansNum;
    }
    public int getSevenNum() {
        return this.sevenNum;
    }
    public void setSevenNum(int sevenNum) {
        this.sevenNum = sevenNum;
    }
    public Long getUser_id() {
        return this.user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

}
