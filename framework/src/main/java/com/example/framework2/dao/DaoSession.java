package com.example.framework2.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.example.framework2.cache.entity.NewEntity;

import com.example.framework2.dao.NewEntityDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig newEntityDaoConfig;

    private final NewEntityDao newEntityDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        newEntityDaoConfig = daoConfigMap.get(NewEntityDao.class).clone();
        newEntityDaoConfig.initIdentityScope(type);

        newEntityDao = new NewEntityDao(newEntityDaoConfig, this);

        registerDao(NewEntity.class, newEntityDao);
    }
    
    public void clear() {
        newEntityDaoConfig.clearIdentityScope();
    }

    public NewEntityDao getNewEntityDao() {
        return newEntityDao;
    }

}
