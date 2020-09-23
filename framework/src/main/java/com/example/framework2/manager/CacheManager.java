package com.example.framework2.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.common.NetCommon;
import com.example.net.activity_bean.entity.ChannelBean;


import java.util.ArrayList;
import java.util.List;

public class CacheManager {
    private CacheManager() {
    }
    private List<Fragment> fragments;
    private List<Fragment> noFragments=new ArrayList<>();
    private List<ChannelBean> onList;
    private List<ChannelBean> noList=new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private List<Activity> activityList=new ArrayList<>();
    private static CacheManager instance;
    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }

        return instance;
    }
    public List<Fragment> getNoFragments() {
        return noFragments;
    }

    public void setNoFragments(List<Fragment> noFragments) {
        this.noFragments = noFragments;
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<Fragment> fragments) {
        this.fragments = fragments;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
    public void addChannel(int position){
        Log.e("fff",noFragments.size()+ "addChannel: "+fragments.size() );
        ChannelBean removeC = noList.remove(position);
        removeC.setShow(true);
        onList.add(0,removeC);
        Fragment removeF = noFragments.remove(position);
        fragments.add(0,removeF);
    }
    public void deleteChannel(int position){
        ChannelBean removeC = onList.remove(position);
        removeC.setShow(false);
        noList.add(0,removeC);
        Fragment removeF = fragments.remove(position);
        noFragments.add(0,removeF);
    }
    public List<ChannelBean> getOnList() {
        onList.get(0).setSign(false);
        return onList;
    }

    public void setOnList(List<ChannelBean> onList) {
        onList.get(0).setSign(false);
        this.onList = onList;
    }

    public List<ChannelBean> getNoList() {
        return noList;
    }

    public void setNoList(List<ChannelBean> noList) {
        this.noList = noList;
    }


    public void init(Context context){
        sharedPreferences = context.getSharedPreferences(NetCommon.SP_NAME, Context.MODE_PRIVATE);

    }


    public void addActivity(Activity activity) {
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    public void removeActivity(Activity activity) {
        if (activityList.contains(activity)) {
            activityList.remove(activity);
        }
    }

    public List<Activity> getActivityList() {
        return activityList;
    }



}
