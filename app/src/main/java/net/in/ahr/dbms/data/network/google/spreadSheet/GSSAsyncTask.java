package net.in.ahr.dbms.data.network.google.spreadSheet;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;

/**
 * Created by str2653z on 2016/03/29.
 */
public class GSSAsyncTask extends AsyncTask<Void, Void, Void> {

    private Activity mainActivity;

    public GSSAsyncTask(Activity activity) {

        // 呼び出し元のアクティビティ
        this.mainActivity = activity;
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Google Spread Sheetを参照
        GSSImport gSSImport = new GSSImport();
        // TODO: 可変にする
        gSSImport.execute(mainActivity.getApplicationContext(), "DBHR Clear Score Sheet", "シート1");

        return null;
    }
}
