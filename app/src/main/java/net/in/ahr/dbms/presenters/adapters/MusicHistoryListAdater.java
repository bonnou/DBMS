package net.in.ahr.dbms.presenters.adapters;

import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.fragments.MusicListFragment;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR_History;

/**
 * Created by str2653z on 16/05/01.
 */
public class MusicHistoryListAdater extends MusicListAdapter {

    public MusicHistoryListAdater(Context context) {
        super(context);
    }

    public void searchApplyToListView(MusicListActivity musicListActivity) {
        LogUtil.logEntering();

        // WHERE句生成
        String whereStatement = "";//musicListActivity.getSearchNaviManager().generateWhereStatement();
        LogUtil.logDebug("■query:[SELECT T.*, T0.* FROM MUSIC_MST T LEFT JOIN MUSIC_RESULT_DBHR T0 ON T.'MUSIC_RESULT_ID_DBHR'=T0.'_id' " + whereStatement + ";]");

        // 検索実施
        MusicMstDao musicMstDao = getMusicMstDao(context);
        List<MusicMst> musicMstList = musicMstDao.queryDeep(whereStatement);

        MusicMstMaintenance musicMstMaintenance = new MusicMstMaintenance();

        // 変更履歴表示用
        List<MusicMst> musicHistoryList = new ArrayList<MusicMst>();

        // 曲ごとにループ
        for (MusicMst musicMst : musicMstList) {
            // 履歴を取得
            List<MusicResultDBHR_History> historyList = musicMst.getMusicResultDBHR_HistoryList();
            // 履歴分ループ
            for (MusicResultDBHR_History history : historyList) {
                // リザルト更新ありの場合のみリストに追加
                if (!history.getPlayedFlg()) {
                    // MusicMstをオリジナルから複製
                    MusicMst copyMusicMst = musicMstMaintenance.copyMusicMst(musicMst);
                    musicMstMaintenance.copyFromHistoryToResult(copyMusicMst, history);
                    musicHistoryList.add(copyMusicMst);
                }
            }
        }

        Collections.sort(musicHistoryList, new Comparator<MusicMst>() {
            @Override
            public int compare(MusicMst lhs, MusicMst rhs) {
                Date date1 = lhs.getMusicResultDBHR().getUpdDate();
                Date date2 = rhs.getMusicResultDBHR().getUpdDate();
                return (-1) * date1.compareTo(date2);
            }
        });

        // 検索結果を再設定
        this.setMusicList(musicHistoryList);
        this.setMusicListOrg(new ArrayList<MusicMst>(musicHistoryList));
/*
        // 曲名部分一致絞り込みのSearchViewに入力がある場合はフィルタする
        Toolbar toolbar = (Toolbar) musicListActivity.findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        MenuItem searchItem = menu.findItem(R.id.action_refine_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        Filter filter = this.getFilter();
        filter.filter(searchView.getQuery().toString());
*/
        // リフレッシュ
        this.notifyDataSetChanged();

        LogUtil.logExiting();
    }

    @Override
    protected String getMemoOther(MusicMst music, boolean resultExistFlg) {
//        LogUtil.logEntering();

        SimpleDateFormat sdf = new SimpleDateFormat(AppConst.MUSIC_HISTORY_LISTVIEW_UPDATED_FORMAT);
        String updDate = "ー";
        if ( music.getMusicResultDBHR().getUpdDate() != null ) {
            updDate = sdf.format(music.getMusicResultDBHR().getUpdDate());
        }

//        LogUtil.logExiting();
        return "update: " + updDate;
    }

}
