package net.in.ahr.dbms.others;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import greendao.DaoMaster;
import greendao.DaoSession;

/**
 * GreenDao設定を追加したAndroidApplicationクラス
 */
public class CustomApplication extends Application {
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        setupDatabase();
    }

    private void setupDatabase() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dbms-db", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
