package com.example.framework2.manager;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.common.NetCommon;
import com.example.framework2.cache.dao.DaoMaster;
import com.example.framework2.cache.dao.DaoSession;
import com.example.framework2.cache.dao.NewEntityDao;
import com.example.framework2.cache.entity.NewEntity;
import com.example.net.activity_bean.entity.ChannelBean;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.List;

public class CacheManager {
    private CacheManager() {
    }


    private NewEntityDao newEntityDao;
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
    public void saveSp(){
        sharedPreferences=getSharedPreferences();
        String s = new Gson().toJson(getOnList());
        String s1 = new Gson().toJson(getNoList());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(NetCommon.TAB_ON_DATA_KEY, s);
        editor.putString(NetCommon.TAB_NO_DATA_KEY,s1);
        editor.commit();
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
        onList.add(removeC);
        Fragment removeF = noFragments.remove(position);
        fragments.add(removeF);
    }
    public void deleteChannel(int position){
        ChannelBean removeC = onList.remove(position);
        removeC.setShow(false);
        noList.add(0,removeC);
        Fragment removeF = fragments.remove(position);
        noFragments.add(0,removeF);
    }
    public List<ChannelBean> getOnList() {
        return onList;
    }

    public synchronized void setOnList(List<ChannelBean> onList) {
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
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, "news.db");
        DaoMaster daoMaster = new DaoMaster(openHelper.getWritableDb());
        DaoSession daoSession = daoMaster.newSession();
        newEntityDao=daoSession.getNewEntityDao();

    }
    public synchronized void addNewEntity(NewEntity newEntity){
        newEntityDao.insert(newEntity);
    }
    public synchronized void deleteNewEntity(NewEntity newEntity){
        newEntityDao.delete(newEntity);
    }
    public NewEntity findOneEntity(String code){
        List<NewEntity> list = newEntityDao.queryBuilder().where(NewEntityDao.Properties.Code.eq(code)).orderAsc(NewEntityDao.Properties.Time).limit(1).list();
        Log.e("rrr", "findOneEntity: "+list.size() );
        if (list.size()==0){
            return null;
        }
        return list.get(0);
    }
    public synchronized List<NewEntity> findNewEntity(){
        List<NewEntity> list = newEntityDao.queryBuilder().list();
        return list;
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
