package com.example.toutiaonews.main.mode;


import com.flyco.tablayout.listener.CustomTabEntity;

public class MainCommonBean implements CustomTabEntity {

    private String message;
    private int selectIcon;
    private int unSelectIcon;

    public MainCommonBean(String message, int selectIcon, int unSelectIcon) {
        this.message = message;
        this.selectIcon = selectIcon;
        this.unSelectIcon = unSelectIcon;
    }

    @Override
    public String getTabTitle() {
        return message;
    }

    @Override
    public int getTabSelectedIcon() {
        return selectIcon;
    }

    @Override
    public int getTabUnselectedIcon() {
        return unSelectIcon;
    }
}
