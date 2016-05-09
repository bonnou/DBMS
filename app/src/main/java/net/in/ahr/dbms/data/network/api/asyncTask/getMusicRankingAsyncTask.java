package net.in.ahr.dbms.data.network.api.asyncTask;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import net.in.ahr.dbms.data.network.api.dto.MusicResultDBHRDto;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.presenters.adapters.MusicRankingListAdapter;
import net.in.ahr.dbms.presenters.fragments.MusicEditFragment;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

/**
 * Created by str2653z on 16/05/01.
 */
public class GetMusicRankingAsyncTask extends PostJSONAsyncTask {

    MusicEditFragment fragment;

    public GetMusicRankingAsyncTask(String url, Object obj, MusicEditFragment fragment) {
        super(url, obj);
        this.fragment = fragment;
    }

    Context context;

    @Override
    protected String doInBackground(Context... params) {
        LogUtil.logEntering();

        context = params[0];

        String resJson = super.doInBackground(params);

        LogUtil.logExiting();

        return resJson;
    }

    @Override
    protected void onPostExecute(String result) {
        LogUtil.logEntering();

        // デシリアライズ
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        Gson gson = builder.create();
        Type collectionType = new TypeToken<List<MusicResultDBHRDto>>(){}.getType();
        List<MusicResultDBHRDto> musicRankingList = gson.fromJson(result, collectionType);

        // リストビューの内容をランキング取得結果で更新
        MusicRankingListAdapter musicRankingListAdapter = fragment.getMusicRakingListAdapter();
        musicRankingListAdapter.setMusicResultDtoList(musicRankingList);

        // ListViewの高さを求め設定
        ListView musicRankingListView = fragment.musicRankingListView;
        if ( musicRankingList == null || musicRankingList.size() == 0 ) {
            fragment.musicRankingListViewWrapper.setVisibility(View.GONE);
            musicRankingListView.setVisibility(View.GONE);
            fragment.emptyView.setVisibility(View.VISIBLE);
            musicRankingListView.addHeaderView(null);

            // ネットワークエラーの場合は表示文言を変更する。
            if (networkErrFlg) {
                fragment.emptyView.setText("Network Error...");
            }

        } else {
            fragment.musicRankingListViewWrapper.setVisibility(View.VISIBLE);
            musicRankingListView.setVisibility(View.VISIBLE);
            fragment.emptyView.setVisibility(View.GONE);
            musicRankingListView.addHeaderView(fragment.headerView);

            // リストビューの高さを算出し設定（ヘッダがあるので1足す）
            View item = musicRankingListAdapter.getView(0, null, musicRankingListView);
            item.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                         View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            int itemHeight = item.getMeasuredHeight() + 2;
            int listViewAllSize = musicRankingList.size() + 1;
            int listViewHeight = itemHeight * listViewAllSize;// + (int)(0.5 * (double)listViewAllSize);
            fragment.musicRankingListViewWrapper.setLayoutParams(
                    new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, listViewHeight));

        }

        // 再描画
        fragment.getMusicRankingListView().invalidateViews();
        musicRankingListAdapter.notifyDataSetChanged();

        // プログレスバーを非表示
        fragment.musicRankingProgressbar.setVisibility(View.GONE);

        LogUtil.logExiting();
    }

}
