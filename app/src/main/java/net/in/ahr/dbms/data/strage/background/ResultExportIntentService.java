package net.in.ahr.dbms.data.strage.background;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.adapters.FileInfo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import greendao.MusicMst;
import greendao.MusicMstDao;

/**
 * Created by str2653z on 2016/04/12.
 */
public class ResultExportIntentService extends IntentService {

    public static final String WORKER_THREAD_NAME_TAG = "ResultExportIntentService";

    // ★実装必須★
    public ResultExportIntentService() {
        // ワーカスレッド名
        super(WORKER_THREAD_NAME_TAG);
    }

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        LogUtil.logEntering();

        // csvエクスポート
        List<MusicMst> musicMstList = getMusicMstDao(getApplicationContext()).loadAll();
        MusicMstMaintenance musicMstMaintenance = new MusicMstMaintenance();
        musicMstMaintenance.exportMusicInfoToCsv(musicMstList, getApplicationContext());

        // エクスポートCSVファイル一覧を取得
        File filesDir = new File(getApplicationContext().getFilesDir().getAbsolutePath());
        File[] files = filesDir.listFiles();
        List<String> deteleTargetFileNameList = new ArrayList<String>();
        for (File file : files) {
            if (
                    file.isFile()
                 && file.getName().contains(AppConst.FILENAME_PREFIX_EXPORT_CSV)
            ) {
                deteleTargetFileNameList.add(file.getName());
            }
        }

        // ファイル名降順
        Collections.sort(deteleTargetFileNameList);
        Collections.reverse(deteleTargetFileNameList);

        // 10ファイル残して削除
        final int MAX_KEEP_CNT = 10;
        int cnt = 0;
        for (String deteleTargetFileName : deteleTargetFileNameList) {
            if (cnt < 10) {
                cnt++;
            } else {
                File delFile = new File(getApplicationContext().getFilesDir().getAbsolutePath() + deteleTargetFileName);
                if (delFile.exists()) {
                    delFile.delete();
                    LogUtil.logDebug("■file delete success:" + delFile.getName());
                }
            }
        }

        // 処理完了をNotification通知
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Notification notification = new Notification();
        notification.tickerText = sdf.format(new java.util.Date()) + " 自動CSVエクスポート完了";
        // TODO: 一旦0にした
        notificationManager.notify(0, notification);

        LogUtil.logExiting();
    }

}
