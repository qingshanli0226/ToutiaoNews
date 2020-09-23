package com.example.video.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
        }
    }

    /**
     *  向数据库中插入数据
     * @param title 视频页面的数据类型
     * @param videoJson 视频页面数据
     * @return 成功的个数
     */
    public long insert(String title, String videoJson) {
        initSql();
        long size = sqlBeanDao.loadAll().size();
        return sqlBeanDao.insert(new SqlBean(size + 1, title, videoJson));
    }

    /**
     * 根据条件获取
     * @param title 视频数据类型
     * @return 符合条件的实体类
     */
    public SqlBean select(String title) {
        initSql();
        List<SqlBean> sqlBeans = selectAll();
        for (SqlBean sqlBean : sqlBeans) {
            if (sqlBean.getTitle().equals(title))
                return sqlBean;
        }
        return null;
    }

    /**
     * 更新数据
     * @param title 视频页面的数据类型
     * @param videoJson 视频页面的数据
     */
    public void change(String title, String videoJson) {
        List<SqlBean> sqlBeans = selectAll();
        for (SqlBean sqlBean : sqlBeans) {
            if (sqlBean.getTitle().equals(title))
                sqlBeanDao.update(new SqlBean(sqlBean.getId(), sqlBean.getTitle(), videoJson));
        }
    }

    /**
     * 获取全部
     * @return Bean类的集合
     */
    public List<SqlBean> selectAll() {
        initSql();
        return sqlBeanDao.loadAll();
    }

    /**
     * 删除全部
     */
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
