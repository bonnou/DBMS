package net.in.ahr.dbms.data.network.api.asyncTask;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.in.ahr.dbms.data.network.api.dto.MusicResultDBHRDto;
import net.in.ahr.dbms.data.network.api.util.DbmsApiUtils;
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.events.musicList.DoOauthEvent;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by str2653z on 16/05/01.
 */
public class PostJSONAsyncTask extends AsyncTask<Context, Void, String> {

    OkHttpClient mOkHttpClient = new OkHttpClient.Builder().build();

    private String url;
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    private Object obj;
    public Object getObj() { return obj; }
    public void setObj(Object obj) { this.obj = obj; }

    public PostJSONAsyncTask(String url, Object obj) {
        super();
        this.setUrl(url);
        this.setObj(obj);
    }

    @Override
    protected String doInBackground(Context... params) {
        LogUtil.logEntering();

        // 認証処理中は処理を実施せず認証終了を待つ
        DbmsApiUtils.sleepWhileOAuthing();

        // SharedPreferencesよりセッションID情報を取得
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(params[0]);
        DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences).edit();
        String sessionIdCookieName = dbmsSharedPreferences.getDbmsOnlineSessionIdCookieName();
        String sessionIdCookieValue = dbmsSharedPreferences.getDbmsOnlineSessionIdCookieValue();

        // タイムアウト無視フラグ
        boolean ignoreSocketTimeoutFlg = false;

        String res = null;
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(obj);

            if ( obj instanceof List && ((List)obj).get(0) instanceof MusicResultDBHRDto ) {
                ignoreSocketTimeoutFlg = true;
            }

            LogUtil.logDebug("■request json:");
            LogUtil.logDebug(json);

            String result = run(getUrl(), json, sessionIdCookieName, sessionIdCookieValue);

            res = result;

            LogUtil.logDebug("■response json:");
            LogUtil.logDebug(result);

        } catch(SocketTimeoutException ste) {

            if (ignoreSocketTimeoutFlg) {
                // TODO:


            } else {
                ste.printStackTrace();
                throw new DbmsSystemException(
                        AppConst.ERR_CD_90011,
                        AppConst.ERR_STEP_CD_NETR_00002,
                        AppConst.ERR_MESSAGE_NETR_00002);
            }

        } catch(IOException e) {
            e.printStackTrace();
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90011,
                    AppConst.ERR_STEP_CD_NETR_00001,
                    AppConst.ERR_MESSAGE_NETR_00001);
        }

        // 未認証の場合
        if ( res.contains("Unauthorized") ) {

            // 認証処理イベントを発行
            new DoOauthEvent().start();

            // 認証処理中にし、認証処理が完了するまでスリープ
            MusicListActivity.nowOAuthingFlg = true;
            DbmsApiUtils.sleepWhileOAuthing();

            // 再帰実行
            res = doInBackground(params);
        }

        LogUtil.logExiting();

        return res;
    }

    // OKHttpを使った通信処理
    public String run(String url, String json, String sessionIdCookieName, String sessionIdCookieValue) throws IOException {
        LogUtil.logEntering();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .addHeader("Cookie", sessionIdCookieName + "=" + sessionIdCookieValue)
                .url(url)
                .post(body)
                .build();

        Response response = mOkHttpClient.newCall(request).execute();

        LogUtil.logExiting();
        return response.body().string();
    }

}
