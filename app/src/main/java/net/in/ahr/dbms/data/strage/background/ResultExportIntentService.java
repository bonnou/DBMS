package net.in.ahr.dbms.data.strage.background;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by str2653z on 2016/04/12.
 */
public class ResultExportIntentService extends IntentService {

    // ★実装必須★
    public ResultExportIntentService() {
        // ワーカスレッド名
        super("ResultExportIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
