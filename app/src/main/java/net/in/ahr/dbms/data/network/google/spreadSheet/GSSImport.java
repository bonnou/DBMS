package net.in.ahr.dbms.data.network.google.spreadSheet;

import android.content.Context;

import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;

import net.in.ahr.dbms.data.strage.util.LogUtil;

/**
 * Created by str2653z on 2016/03/28.
 */
public class GSSImport {

    public void execute(Context c, String ssName, String wsName) {
        GSSUtil gSSUtil = new GSSUtil(c);
        try {
            SpreadsheetService service = gSSUtil.getService();


            SpreadsheetEntry ssEntry = gSSUtil.findSpreadsheetByName(service, ssName);
            WorksheetEntry wsEntry = gSSUtil.findWorksheetByName(service, ssEntry, wsName);

            LogUtil.logDebug("スプレッドシート名：" + ssEntry.getTitle().getPlainText());

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: 適切な例外処理
        }

    }
}
