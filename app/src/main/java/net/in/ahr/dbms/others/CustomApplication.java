package net.in.ahr.dbms.others;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.crashlytics.android.Crashlytics;
import com.deploygate.sdk.DeployGate;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;

import greendao.DaoMaster;
import greendao.DaoSession;
import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * GreenDao設定を追加したAndroidApplicationクラス
 */
public class CustomApplication extends Application {
    public DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();
        DeployGate.install(this); // ※debuggable="true"の場合のみ動作
        Fabric.with(getApplicationContext(), new Crashlytics());
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

        // 設定ファイルのフラグを読み取ってログ出力を切り替えます。
        boolean isShowLog = getResources().getBoolean(R.bool.isShowLog);
        LogUtil.setShowLog(isShowLog);
    }

    private void setupDatabase() {
        // TODO: MemoからMusicMstに変更した際にMUSIC_MSTが見つからないエラーが出た。dbms-dbからdbms-databaseに変更したら解消したが正しい解は？
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "dbms-database_0.0.1.00023", null);
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
}
