package com.example.video.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.video.bean.SqlBean;

import java.util.List;

public class DaoManager {
    private static DaoManager daoMessage;
    private SqlBeanDao sqlBeanDao;
    private Context mContext;


    public static DaoManager getDaoMessage() {
        if (daoMessage == null) {
            daoMessage = new DaoManager();
        }
        return daoMessage;
    }

    private DaoManager() {

    }

    public void init(Context context) {
        this.mContext = context;
    }

    public SqlBeanDao getSql() {
        DaoMaster.DevOpenHelper video = new DaoMaster.DevOpenHelper(mContext, "video");
        SQLiteDatabase db = video.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        DaoSession daoSession = daoMaster.newSession();
        return daoSession.getSqlBeanDao();
    }

    private void initSql() {
        if (sqlBeanDao == null) {
            sqlBeanDao = getSql();
            Log.e("hq", "insert: ");
        }
    }

    public long insert(String title, String videoJson) {
        initSql();
        long size = sqlBeanDao.loadAll().size();
        return sqlBeanDao.insert(new SqlBean(size + 1, title, videoJson));
    }

    public SqlBean select(String title) {
        initSql();

        List<SqlBean> sqlBeans = selectAll();
        for (SqlBean sqlBean : sqlBeans) {
            if (sqlBean.getTitle().equals(title))
                return sqlBean;
        }
        return null;
    }

    public void change(String title, String videoJson) {
        List<SqlBean> sqlBeans = selectAll();
        for (SqlBean sqlBean : sqlBeans) {
            if (sqlBean.getTitle().equals(title))
                sqlBeanDao.update(new SqlBean(sqlBean.getId(), sqlBean.getTitle(), videoJson));
        }

    }


    public List<SqlBean> selectAll() {
        initSql();

        return sqlBeanDao.loadAll();
    }

    public void delAll() {
        initSql();

        sqlBeanDao.deleteAll();
    }

    public void onDestroy() {
        if (daoMessage != null) {
            daoMessage = null;
        }
    }
}
