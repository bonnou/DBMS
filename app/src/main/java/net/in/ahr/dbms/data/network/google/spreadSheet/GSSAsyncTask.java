package net.in.ahr.dbms.data.network.google.spreadSheet;

import android.os.AsyncTask;

import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;

/**
 * Created by str2653z on 2016/03/29.
 */
public class GSSAsyncTask extends AsyncTask<Void, Void, Void> {

    private MusicListActivity musicListActivity;

    public GSSAsyncTask(MusicListActivity musicListActivity) {
        // 呼び出し元のアクティビティ
        this.musicListActivity = musicListActivity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        LogUtil.logEntering();

        // Google Spread Sheetを参照
        GSSImport gSSImport = new GSSImport();
        // TODO: 引数を可変にする
        gSSImport.execute(musicListActivity, "DBHR Clear Score Sheet", "シート1");

        LogUtil.logExiting();
        return null;
    }

}
