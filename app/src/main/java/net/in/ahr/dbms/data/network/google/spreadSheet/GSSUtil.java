package net.in.ahr.dbms.data.network.google.spreadSheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.Arrays;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.Map;
import com.google.api.client.extensions.android.http.AndroidHttp;

import android.content.Context;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import com.google.gdata.client.spreadsheet.SpreadsheetQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.client.spreadsheet.WorksheetQuery;
import com.google.gdata.data.PlainTextConstruct;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.ListEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetFeed;

/**
 * Created by str2653z on 2016/03/28.
 */
public class GSSUtil {

    // アプリケーション名 (任意)
    private static final String APPLICATION_NAME = "dbms-spreadsheet-application";

    // アカウント
    private static final String ACCOUNT_P12_ID = "googlespreadsheetdbms@dbms-1264.iam.gserviceaccount.com";
    private static File P12FILE;

    // 認証スコープ
    private static final List<String> SCOPES = Arrays.asList(
            "https://docs.google.com/feeds",
            "https://spreadsheets.google.com/feeds");

    // Spreadsheet API URL
    private static final String SPREADSHEET_URL = "https://spreadsheets.google.com/feeds/spreadsheets/private/full";

    private static final URL SPREADSHEET_FEED_URL;

    private Context c;

    static {
        try {
            SPREADSHEET_FEED_URL = new URL(SPREADSHEET_URL);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    private GSSUtil() {

    }

    public GSSUtil(Context c) {
        this.c = c;
        init();
    }

    private void init() {
        // P12ファイルをassetsフォルダから読み込み
        InputStream is = null;
        try {
            is = c.getResources().getAssets().open("auth/GoogleSpreadSheetDBMS.p12");
            File tempFile = File.createTempFile("tmp", ".dat");
            tempFile.deleteOnExit();
            FileOutputStream fos = new FileOutputStream(tempFile);
            IOUtils.copy(is, fos);
            P12FILE = tempFile;
        } catch (IOException ioe) {
            ioe.printStackTrace();
            // TODO: 適切な例外処理
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                    // TODO: 適切な例外処理
                }
            }
        }
    }

    private static Credential authorize() throws Exception {
        System.out.println("authorize in");

        HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
        JsonFactory jsonFactory = new JacksonFactory();

        GoogleCredential credential = new GoogleCredential.Builder()
                .setTransport(httpTransport)
                .setJsonFactory(jsonFactory)
                .setServiceAccountId(ACCOUNT_P12_ID)
                .setServiceAccountPrivateKeyFromP12File(P12FILE)
                .setServiceAccountScopes(SCOPES)
                .build();

        boolean ret = credential.refreshToken();
        // debug dump
        System.out.println("refreshToken:" + ret);

        // debug dump
        if (credential != null) {
            System.out.println("AccessToken:" + credential.getAccessToken());
        }

        System.out.println("authorize out");

        return credential;
    }

    public static SpreadsheetService getService() throws Exception {
        System.out.println("service in");

        SpreadsheetService service = new SpreadsheetService(APPLICATION_NAME);
        // TODO: シートに編集用サービスアカウントのメールアドレスが追加されていない場合の処理
        service.setProtocolVersion(SpreadsheetService.Versions.V3);

        Credential credential = authorize();
        service.setOAuth2Credentials(credential);

        // debug dump
        System.out.println("Schema: " + service.getSchema().toString());
        System.out.println("Protocol: " + service.getProtocolVersion().getVersionString());
        System.out.println("ServiceVersion: " + service.getServiceVersion());

        System.out.println("service out");

        return service;
    }

    public static SpreadsheetEntry findSpreadsheetByName(SpreadsheetService service, String spreadsheetName) throws Exception {
        System.out.println("findSpreadsheetByName in");
        SpreadsheetQuery sheetQuery = new SpreadsheetQuery(SPREADSHEET_FEED_URL);
        sheetQuery.setTitleQuery(spreadsheetName);
        SpreadsheetFeed feed = service.query(sheetQuery, SpreadsheetFeed.class);
        SpreadsheetEntry ssEntry = null;
        if (feed.getEntries().size() > 0) {
            ssEntry = feed.getEntries().get(0);
        }
        System.out.println("findSpreadsheetByName out");
        return ssEntry;
    }

    public static WorksheetEntry findWorksheetByName(SpreadsheetService service, SpreadsheetEntry ssEntry, String sheetName) throws Exception {
        System.out.println("findWorksheetByName in");
        WorksheetQuery worksheetQuery = new WorksheetQuery(ssEntry.getWorksheetFeedUrl());
        worksheetQuery.setTitleQuery(sheetName);
        WorksheetFeed feed = service.query(worksheetQuery, WorksheetFeed.class);
        WorksheetEntry wsEntry = null;
        if (feed.getEntries().size() > 0){
            wsEntry = feed.getEntries().get(0);
        }
        System.out.println("findWorksheetByName out");
        return wsEntry;
    }

}
