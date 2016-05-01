package net.in.ahr.dbms.data.network.request;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by str2653z on 16/05/01.
 */
public class PostJSONAsyncTask extends AsyncTask<Void, Void, String> {

    OkHttpClient client = new OkHttpClient();

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
    protected String doInBackground(Void... params) {
        LogUtil.logEntering();

        String res = null;
        try {
            Gson gson = new GsonBuilder().serializeNulls().create();
            String json = gson.toJson(obj);

            LogUtil.logDebug("■request json:");
            LogUtil.logDebug(json);

            String result = run(getUrl(), json);

            res = result;

            LogUtil.logDebug("■response json:");
            LogUtil.logDebug(result);

        } catch(IOException e) {
            e.printStackTrace();
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90011,
                    AppConst.ERR_STEP_CD_NETR_00001,
                    AppConst.ERR_MESSAGE_NETR_00001);
        }

        LogUtil.logExiting();
        return res;
    }

    // OKHttpを使った通信処理
    public String run(String url, String json) throws IOException {
        LogUtil.logEntering();

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        LogUtil.logExiting();
        return response.body().string();
    }

}
