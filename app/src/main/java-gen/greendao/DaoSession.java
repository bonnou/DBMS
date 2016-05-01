package greendao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.identityscope.IdentityScopeType;
import de.greenrobot.dao.internal.DaoConfig;

import greendao.MusicMst;
import greendao.MusicResultDBHR;
import greendao.MusicResultDBHR_History;

import greendao.MusicMstDao;
import greendao.MusicResultDBHRDao;
import greendao.MusicResultDBHR_HistoryDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig musicMstDaoConfig;
    private final DaoConfig musicResultDBHRDaoConfig;
    private final DaoConfig musicResultDBHR_HistoryDaoConfig;

    private final MusicMstDao musicMstDao;
    private final MusicResultDBHRDao musicResultDBHRDao;
    private final MusicResultDBHR_HistoryDao musicResultDBHR_HistoryDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        musicMstDaoConfig = daoConfigMap.get(MusicMstDao.class).clone();
        musicMstDaoConfig.initIdentityScope(type);

        musicResultDBHRDaoConfig = daoConfigMap.get(MusicResultDBHRDao.class).clone();
        musicResultDBHRDaoConfig.initIdentityScope(type);

        musicResultDBHR_HistoryDaoConfig = daoConfigMap.get(MusicResultDBHR_HistoryDao.class).clone();
        musicResultDBHR_HistoryDaoConfig.initIdentityScope(type);

        musicMstDao = new MusicMstDao(musicMstDaoConfig, this);
        musicResultDBHRDao = new MusicResultDBHRDao(musicResultDBHRDaoConfig, this);
        musicResultDBHR_HistoryDao = new MusicResultDBHR_HistoryDao(musicResultDBHR_HistoryDaoConfig, this);

        registerDao(MusicMst.class, musicMstDao);
        registerDao(MusicResultDBHR.class, musicResultDBHRDao);
        registerDao(MusicResultDBHR_History.class, musicResultDBHR_HistoryDao);
    }
    
    public void clear() {
        musicMstDaoConfig.getIdentityScope().clear();
        musicResultDBHRDaoConfig.getIdentityScope().clear();
        musicResultDBHR_HistoryDaoConfig.getIdentityScope().clear();
    }

    public MusicMstDao getMusicMstDao() {
        return musicMstDao;
    }

    public MusicResultDBHRDao getMusicResultDBHRDao() {
        return musicResultDBHRDao;
    }

    public MusicResultDBHR_HistoryDao getMusicResultDBHR_HistoryDao() {
        return musicResultDBHR_HistoryDao;
    }

}
