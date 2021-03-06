package net.in.ahr.dbms.others;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.multidex.MultiDexApplication;

import com.crashlytics.android.Crashlytics;
import com.deploygate.sdk.DeployGate;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;

import java.util.Properties;

import greendao.DaoMaster;
import greendao.DaoSession;
import io.fabric.sdk.android.Fabric;

/**
 * GreenDao設定を追加したAndroidApplicationクラス
 */
public class CustomApplication extends MultiDexApplication {
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DeployGate.install(this); // ※debuggable="true"の場合のみ動作

        setupCrashlytics();

/*
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("font/Roboto-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("font/GenShinGothic-Regular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build()
        );
*/
        setupDatabase();

        // システムプロパティ一覧出力
        Properties props = System.getProperties();
        props.list(System.out);

        // 設定ファイルのフラグを読み取ってログ出力を切り替えます。
        boolean isShowLog = getResources().getBoolean(R.bool.isShowLog);
        LogUtil.setShowLog(isShowLog);
    }

    protected void setupCrashlytics() {
        Fabric.with(
                getApplicationContext(),
                new Crashlytics.Builder().disabled(true).build());
    }

    private void setupDatabase() {
        // TODO: MemoからMusicMstに変更した際にMUSIC_MSTが見つからないエラーが出た。dbms-dbからdbms-databaseに変更したら解消したが正しい解は？
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dbms-database_0.0.1.00026", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();

        // Musicマスタのメンテ
        // TODO: ここだとログがでない・・・
        MusicMstMaintenance musicMstMaintenance = new MusicMstMaintenance();
        musicMstMaintenance.execute(this.getApplicationContext());
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    @Override
    protected void attachBaseContext(Context base) {
        try {
            super.attachBaseContext(base);
        } catch (Exception e) {
            String vmName = System.getProperty("java.vm.name");
            if (!vmName.startsWith("Java")) {
                // MultiDexにしたアプリケーションでRobolectricがコケる
                // →JavaVM上での実行なら例外を握りつぶす
                // http://sys1yagi.hatenablog.com/entry/2014/12/13/161001
                throw e;
            }
        }
    }
}
