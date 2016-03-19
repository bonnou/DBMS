package net.in.ahr.dbms.data.strage.mstMainte;

import android.content.Context;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.data.strage.util.CSVParser;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.CustomApplication;
import static net.in.ahr.dbms.others.AppConst.*;

import greendao.MemoDao;

/**
 * Created by str2653z on 2016/03/16.
 */
public class MusicMstMaintenance {

    private static MemoDao getMemoDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMemoDao();
    }

    public void execute(Context c) {
        LogUtil.logDebug("ENTERING");

        if ( BuildConfig.VERSION_CODE == MUSIC_MST_MIG_VER_CD_1 ) {
            // 件数チェック
            MemoDao memoDao = getMemoDao(c);
            long cnt = memoDao.count();
            LogUtil.logDebug("memo cnt:" + cnt);
            if ( cnt != MUSIC_MST_MIG_VER_CD_AFT_CNT_1 ) {
                CSVParser csvParser = new CSVParser();
                csvParser.parse(c, "csv/musicMst/musicMst_0001.csv");
            }
        }

        LogUtil.logDebug("EXITING");
    }
}
