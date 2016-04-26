package net.in.ahr.dbms.presenters.others;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;
import net.in.ahr.dbms.others.util.DbmsGreenDaoUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import greendao.MusicMstDao;
import greendao.MusicResultDBHRDao;

/**
 * 検索・ソートNavigationDrawerの管理用オブジェクト。
 */
public class SearchNaviManager {

    /**
     * コンストラクタ
     * @param navigationView
     * @param dbmsSharedPreferences 本アプリ用SharedPreferenceラッパー
     */
    public SearchNaviManager(NavigationView navigationView, DbmsSharedPreferences dbmsSharedPreferences) {
        setNavigationView(navigationView);
        setDbmsSharedPreferences(dbmsSharedPreferences);
    }

    // NavigationView
    private NavigationView navigationView;

    // 本アプリ用SharedPreferenceラッパー
    private DbmsSharedPreferences dbmsSharedPreferences;

    // メニュー項目（難易度）
    private MenuItem item_nav_where_difficult_10;
    private MenuItem item_nav_where_difficult_11;

    // メニュー項目（クリアランプ)
    private MenuItem item_nav_where_clear_lamp_no_play;
    private MenuItem item_nav_where_clear_lamp_far_away;
    private MenuItem item_nav_where_clear_lamp_failed;
    private MenuItem item_nav_where_clear_lamp_assist_clear;
    private MenuItem item_nav_where_clear_lamp_assist_easy_clear;
    private MenuItem item_nav_where_clear_lamp_easy_clear;
    private MenuItem item_nav_where_clear_lamp_normal_clear;
    private MenuItem item_nav_where_clear_lamp_hard_clear;
    private MenuItem item_nav_where_clear_lamp_exhard_clear;
    private MenuItem item_nav_where_clear_lamp_full_combo;
    private MenuItem item_nav_where_clear_lamp_perfect;

    // メニュー項目（スコアランク）
    private MenuItem item_nav_where_score_rank_aaa;
    private MenuItem item_nav_where_score_rank_aa;
    private MenuItem item_nav_where_score_rank_a;
    private MenuItem item_nav_where_score_rank_b;
    private MenuItem item_nav_where_score_rank_c;
    private MenuItem item_nav_where_score_rank_d;
    private MenuItem item_nav_where_score_rank_e;
    private MenuItem item_nav_where_score_rank_f;
    private MenuItem item_nav_where_score_rank_no_rank;

    // メニュー項目（BPM範囲）
    private MenuItem item_nav_where_bpm_range_lll_129;
    private MenuItem item_nav_where_bpm_range_130_139;
    private MenuItem item_nav_where_bpm_range_140_149;
    private MenuItem item_nav_where_bpm_range_150_159;
    private MenuItem item_nav_where_bpm_range_160_169;
    private MenuItem item_nav_where_bpm_range_170_179;
    private MenuItem item_nav_where_bpm_range_180_189;
    private MenuItem item_nav_where_bpm_range_190_199;
    private MenuItem item_nav_where_bpm_range_200_hhh;
    private MenuItem item_nav_where_bpm_range_soflan;

    // メニュー項目（バージョン）
    private MenuItem item_nav_where_version_1st;
    private MenuItem item_nav_where_version_sub;
    private MenuItem item_nav_where_version_2nd;
    private MenuItem item_nav_where_version_3rd;
    private MenuItem item_nav_where_version_4th;
    private MenuItem item_nav_where_version_5th;
    private MenuItem item_nav_where_version_6th;
    private MenuItem item_nav_where_version_7th;
    private MenuItem item_nav_where_version_8th;
    private MenuItem item_nav_where_version_9th;
    private MenuItem item_nav_where_version_10th;
    private MenuItem item_nav_where_version_RED;
    private MenuItem item_nav_where_version_SKY;
    private MenuItem item_nav_where_version_DD;
    private MenuItem item_nav_where_version_GOLD;
    private MenuItem item_nav_where_version_DJT;
    private MenuItem item_nav_where_version_EMP;
    private MenuItem item_nav_where_version_SIR;
    private MenuItem item_nav_where_version_RA;
    private MenuItem item_nav_where_version_LC;
    private MenuItem item_nav_where_version_tri;
    private MenuItem item_nav_where_version_SPA;
    private MenuItem item_nav_where_version_PEN;
    private MenuItem item_nav_where_version_cop;

    // メニュー項目（ORDER BY）
    private MenuItem item_nav_orderby_difficult_name;
    private MenuItem item_nav_orderby_name;
    private MenuItem item_nav_orderby_clear_lamp;
    private MenuItem item_nav_orderby_ex_score;
    private MenuItem item_nav_orderby_bp;
    private MenuItem item_nav_orderby_score_rate;
    private MenuItem item_nav_orderby_miss_rate;
    private MenuItem item_nav_orderby_updated;
//    private MenuItem item_nav_orderby_clear_progress;

    MenuItem[] allItemNavOrderByArr;

    // メニュー項目（ORDER SORT KIND）
    private MenuItem item_nav_order_sort_kind_asc;
    private MenuItem item_nav_order_sort_kind_desc;

    //----------------------------
    // 公開メソッド
    //----------------------------

    /**
     * アプリ起動時の検索・ソートNavigationDrawerの条件設定処理。
     * SharedPreferenceの設定値により検索・ソート条件の選択状態を復元する。
     * メインActivityのonCreateにて呼び出す。
     */
    public void settingAtOnCreate() {
        LogUtil.logEntering();

        // 難易度
        item_nav_where_difficult_10 = navigationView.getMenu().findItem(R.id.nav_where_difficult_10);
        item_nav_where_difficult_10.setChecked(
                dbmsSharedPreferences.getSearchConfDiff_10()
        );

        item_nav_where_difficult_11 = navigationView.getMenu().findItem(R.id.nav_where_difficult_11);
        item_nav_where_difficult_11.setChecked(
                dbmsSharedPreferences.getSearchConfDiff_11()
        );

        // クリアランプ
        item_nav_where_clear_lamp_no_play = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_no_play);
        item_nav_where_clear_lamp_no_play.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_NO_PLAY()
        );
        item_nav_where_clear_lamp_far_away = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_far_away);
        item_nav_where_clear_lamp_far_away.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_FAR_AWAY()
        );
        item_nav_where_clear_lamp_failed = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_failed);
        item_nav_where_clear_lamp_failed.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_FAILED()
        );
        item_nav_where_clear_lamp_assist_clear = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_assist_clear);
        item_nav_where_clear_lamp_assist_clear.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_EASY_CLEAR()
        );
        item_nav_where_clear_lamp_assist_easy_clear = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_assist_easy_clear);
        item_nav_where_clear_lamp_assist_easy_clear.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_CLEAR()
        );
        item_nav_where_clear_lamp_easy_clear = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_easy_clear);
        item_nav_where_clear_lamp_easy_clear.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_EASY_CLEAR()
        );
        item_nav_where_clear_lamp_normal_clear = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_normal_clear);
        item_nav_where_clear_lamp_normal_clear.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_NORMAL_CLEAR()
        );
        item_nav_where_clear_lamp_hard_clear = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_hard_clear);
        item_nav_where_clear_lamp_hard_clear.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_HARD_CLEAR()
        );
        item_nav_where_clear_lamp_exhard_clear = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_exhard_clear);
        item_nav_where_clear_lamp_exhard_clear.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_EXHARD_CLEAR()
        );
        item_nav_where_clear_lamp_full_combo = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_full_combo);
        item_nav_where_clear_lamp_full_combo.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_FULL_COMBO()
        );
        item_nav_where_clear_lamp_perfect = navigationView.getMenu().findItem(R.id.nav_where_clear_lamp_perfect);
        item_nav_where_clear_lamp_perfect.setChecked(
                dbmsSharedPreferences.getSearchConfClearLamp_PERFECT()
        );

        // スコアランク
        item_nav_where_score_rank_aaa = navigationView.getMenu().findItem(R.id.nav_where_score_rank_aaa);
        item_nav_where_score_rank_aaa.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_AAA()
        );
        item_nav_where_score_rank_aa = navigationView.getMenu().findItem(R.id.nav_where_score_rank_aa);
        item_nav_where_score_rank_aa.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_AA()
        );
        item_nav_where_score_rank_a = navigationView.getMenu().findItem(R.id.nav_where_score_rank_a);
        item_nav_where_score_rank_a.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_A()
        );
        item_nav_where_score_rank_b = navigationView.getMenu().findItem(R.id.nav_where_score_rank_b);
        item_nav_where_score_rank_b.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_B()
        );
        item_nav_where_score_rank_c = navigationView.getMenu().findItem(R.id.nav_where_score_rank_c);
        item_nav_where_score_rank_c.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_C()
        );
        item_nav_where_score_rank_d = navigationView.getMenu().findItem(R.id.nav_where_score_rank_d);
        item_nav_where_score_rank_d.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_D()
        );
        item_nav_where_score_rank_e = navigationView.getMenu().findItem(R.id.nav_where_score_rank_e);
        item_nav_where_score_rank_e.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_E()
        );
        item_nav_where_score_rank_f = navigationView.getMenu().findItem(R.id.nav_where_score_rank_f);
        item_nav_where_score_rank_f.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_F()
        );
        item_nav_where_score_rank_no_rank = navigationView.getMenu().findItem(R.id.nav_where_score_rank_no_rank);
        item_nav_where_score_rank_no_rank.setChecked(
                dbmsSharedPreferences.getSearchConfScoreRank_NO_RANK()
        );

        // BPM範囲
        item_nav_where_bpm_range_lll_129 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_lll_129);
        item_nav_where_bpm_range_lll_129.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_LLL_129()
        );
        item_nav_where_bpm_range_130_139 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_130_139);
        item_nav_where_bpm_range_130_139.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_130_139()
        );
        item_nav_where_bpm_range_140_149 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_140_149);
        item_nav_where_bpm_range_140_149.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_140_149()
        );
        item_nav_where_bpm_range_150_159 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_150_159);
        item_nav_where_bpm_range_150_159.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_150_159()
        );
        item_nav_where_bpm_range_160_169 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_160_169);
        item_nav_where_bpm_range_160_169.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_160_169()
        );
        item_nav_where_bpm_range_170_179 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_170_179);
        item_nav_where_bpm_range_170_179.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_170_179()
        );
        item_nav_where_bpm_range_180_189 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_180_189);
        item_nav_where_bpm_range_180_189.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_180_189()
        );
        item_nav_where_bpm_range_190_199 = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_190_199);
        item_nav_where_bpm_range_190_199.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_190_199()
        );
        item_nav_where_bpm_range_200_hhh = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_200_hhh);
        item_nav_where_bpm_range_200_hhh.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_200_HHH()
        );
        item_nav_where_bpm_range_soflan = navigationView.getMenu().findItem(R.id.nav_where_bpm_range_soflan);
        item_nav_where_bpm_range_soflan.setChecked(
                dbmsSharedPreferences.getSearchConfBpmRange_SOFLAN()
        );

        // バージョン
        item_nav_where_version_1st = navigationView.getMenu().findItem(R.id.nav_where_version_1st);
        item_nav_where_version_1st.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_1st()
        );
        item_nav_where_version_sub = navigationView.getMenu().findItem(R.id.nav_where_version_sub);
        item_nav_where_version_sub.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_sub()
        );
        item_nav_where_version_2nd = navigationView.getMenu().findItem(R.id.nav_where_version_2nd);
        item_nav_where_version_2nd.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_2nd()
        );
        item_nav_where_version_3rd = navigationView.getMenu().findItem(R.id.nav_where_version_3rd);
        item_nav_where_version_3rd.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_3rd()
        );
        item_nav_where_version_4th = navigationView.getMenu().findItem(R.id.nav_where_version_4th);
        item_nav_where_version_4th.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_4th()
        );
        item_nav_where_version_5th = navigationView.getMenu().findItem(R.id.nav_where_version_5th);
        item_nav_where_version_5th.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_5th()
        );
        item_nav_where_version_6th = navigationView.getMenu().findItem(R.id.nav_where_version_6th);
        item_nav_where_version_6th.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_6th()
        );
        item_nav_where_version_7th = navigationView.getMenu().findItem(R.id.nav_where_version_7th);
        item_nav_where_version_7th.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_7th()
        );
        item_nav_where_version_8th = navigationView.getMenu().findItem(R.id.nav_where_version_8th);
        item_nav_where_version_8th.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_8th()
        );
        item_nav_where_version_9th = navigationView.getMenu().findItem(R.id.nav_where_version_9th);
        item_nav_where_version_9th.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_9th()
        );
        item_nav_where_version_10th = navigationView.getMenu().findItem(R.id.nav_where_version_10th);
        item_nav_where_version_10th.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_10th()
        );
        item_nav_where_version_RED = navigationView.getMenu().findItem(R.id.nav_where_version_RED);
        item_nav_where_version_RED.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_RED()
        );
        item_nav_where_version_SKY = navigationView.getMenu().findItem(R.id.nav_where_version_SKY);
        item_nav_where_version_SKY.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_SKY()
        );
        item_nav_where_version_DD = navigationView.getMenu().findItem(R.id.nav_where_version_DD);
        item_nav_where_version_DD.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_DD()
        );
        item_nav_where_version_GOLD = navigationView.getMenu().findItem(R.id.nav_where_version_GOLD);
        item_nav_where_version_GOLD.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_GOLD()
        );
        item_nav_where_version_DJT = navigationView.getMenu().findItem(R.id.nav_where_version_DJT);
        item_nav_where_version_DJT.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_DJT()
        );
        item_nav_where_version_EMP = navigationView.getMenu().findItem(R.id.nav_where_version_EMP);
        item_nav_where_version_EMP.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_EMP()
        );
        item_nav_where_version_SIR = navigationView.getMenu().findItem(R.id.nav_where_version_SIR);
        item_nav_where_version_SIR.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_SIR()
        );
        item_nav_where_version_RA = navigationView.getMenu().findItem(R.id.nav_where_version_RA);
        item_nav_where_version_RA.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_RA()
        );
        item_nav_where_version_LC = navigationView.getMenu().findItem(R.id.nav_where_version_LC);
        item_nav_where_version_LC.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_LC()
        );
        item_nav_where_version_tri = navigationView.getMenu().findItem(R.id.nav_where_version_tri);
        item_nav_where_version_tri.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_tri()
        );
        item_nav_where_version_SPA = navigationView.getMenu().findItem(R.id.nav_where_version_SPA);
        item_nav_where_version_SPA.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_SPA()
        );
        item_nav_where_version_PEN = navigationView.getMenu().findItem(R.id.nav_where_version_PEN);
        item_nav_where_version_PEN.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_PEN()
        );
        item_nav_where_version_cop = navigationView.getMenu().findItem(R.id.nav_where_version_cop);
        item_nav_where_version_cop.setChecked(
                dbmsSharedPreferences.getSearchConfVersion_cop()
        );

        // ORDER BY
        item_nav_orderby_difficult_name = navigationView.getMenu().findItem(R.id.nav_orderby_difficult_name);
        item_nav_orderby_difficult_name.setChecked(false);
        item_nav_orderby_name = navigationView.getMenu().findItem(R.id.nav_orderby_name);
        item_nav_orderby_name.setChecked(false);
        item_nav_orderby_clear_lamp = navigationView.getMenu().findItem(R.id.nav_orderby_clear_lamp);
        item_nav_orderby_clear_lamp.setChecked(false);
        item_nav_orderby_ex_score = navigationView.getMenu().findItem(R.id.nav_orderby_ex_score);
        item_nav_orderby_ex_score.setChecked(false);
        item_nav_orderby_bp = navigationView.getMenu().findItem(R.id.nav_orderby_bp);
        item_nav_orderby_bp.setChecked(false);
        item_nav_orderby_score_rate = navigationView.getMenu().findItem(R.id.nav_orderby_score_rate);
        item_nav_orderby_score_rate.setChecked(false);
        item_nav_orderby_miss_rate = navigationView.getMenu().findItem(R.id.nav_orderby_miss_rate);
        item_nav_orderby_miss_rate.setChecked(false);
        item_nav_orderby_updated = navigationView.getMenu().findItem(R.id.nav_orderby_updated);
        item_nav_orderby_updated.setChecked(false);
//            item_nav_orderby_clear_progress = navigationView.getMenu().findItem(R.id.nav_orderby_clear_progress);
//            item_nav_orderby_clear_progress.setChecked(false);

        String searchOrderByTarget = dbmsSharedPreferences.getSearchOrderByTarget();

        if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME.equals(searchOrderByTarget) ) {
            item_nav_orderby_difficult_name.setChecked(true);

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_NAME.equals(searchOrderByTarget) ) {
            item_nav_orderby_name.setChecked(true);

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEARLAMP.equals(searchOrderByTarget) ) {
            item_nav_orderby_clear_lamp.setChecked(true);

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_EXSCORE.equals(searchOrderByTarget) ) {
            item_nav_orderby_ex_score.setChecked(true);

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_BP.equals(searchOrderByTarget) ) {
            item_nav_orderby_bp.setChecked(true);

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_SCORE_RATE.equals(searchOrderByTarget) ) {
            item_nav_orderby_score_rate.setChecked(true);

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_MISS_RATE.equals(searchOrderByTarget) ) {
            item_nav_orderby_miss_rate.setChecked(true);

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_UPDATED.equals(searchOrderByTarget) ) {
            item_nav_orderby_updated.setChecked(true);

        }
/*
            else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEAR_PROGRESS.equals(searchOrderByTarget) ) {
                item_nav_orderby_clear_progress.setChecked(true);

            }
*/

        allItemNavOrderByArr = new MenuItem[]{
                item_nav_orderby_difficult_name,
                item_nav_orderby_name,
                item_nav_orderby_clear_lamp,
                item_nav_orderby_ex_score,
                item_nav_orderby_bp,
                item_nav_orderby_score_rate,
                item_nav_orderby_miss_rate,
                item_nav_orderby_updated
//                    ,
//                    item_nav_orderby_clear_progress
        };

        // ORDER SORT KIND
        item_nav_order_sort_kind_asc = navigationView.getMenu().findItem(R.id.nav_order_sort_kind_asc);
        item_nav_order_sort_kind_asc.setChecked(false);
        item_nav_order_sort_kind_desc = navigationView.getMenu().findItem(R.id.nav_order_sort_kind_desc);
        item_nav_order_sort_kind_desc.setChecked(false);

        String searchOrderSortKind = dbmsSharedPreferences.getSearchOrderSortKind();

        if ( AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC.equals(searchOrderSortKind) ) {
            item_nav_order_sort_kind_asc.setChecked(true);

        } else {
            item_nav_order_sort_kind_desc.setChecked(true);

        }

        LogUtil.logExiting();
    }

    /**
     * NavigationDrawerの項目押下時設定処理。
     * 画面上の選択・非選択の変更および、SharedPreferenceの設定変更を行う。
     * メインActivityのonNavigationItemSelectedにて呼び出す。
     */
    public void settingAtOnNaviSelected(MenuItem selectedItem) {
        LogUtil.logEntering();

        // 編集モード
        dbmsSharedPreferences = this.dbmsSharedPreferences.edit();

        int id = selectedItem.getItemId();

        // TODO: リフレクション
        // 難易度
        if (id == R.id.nav_where_difficult_all_on_off) {
            // 1つでもOFFのものがあれば全てONに設定
            // そうでなければ全てOFFに設定
            if (
                    dbmsSharedPreferences.getSearchConfDiff_10()
                            && dbmsSharedPreferences.getSearchConfDiff_11()
                    ) {
                item_nav_where_difficult_10.setChecked(false);
                dbmsSharedPreferences.putSearchConfDiff_10(false);
                item_nav_where_difficult_11.setChecked(false);
                dbmsSharedPreferences.putSearchConfDiff_11(false);
            } else {
                item_nav_where_difficult_10.setChecked(true);
                dbmsSharedPreferences.putSearchConfDiff_10(true);
                item_nav_where_difficult_11.setChecked(true);
                dbmsSharedPreferences.putSearchConfDiff_11(true);
            }
        } else if (id == R.id.nav_where_difficult_10) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfDiff_10(selectedItem.isChecked());

        } else if (id == R.id.nav_where_difficult_11) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfDiff_11(selectedItem.isChecked());

        }

        // クリアランプ
        if (id == R.id.nav_where_clear_lamp_all_on_off) {
            // 1つでもOFFのものがあれば全てONに設定
            // そうでなければ全てOFFに設定
            if (
                    dbmsSharedPreferences.getSearchConfClearLamp_NO_PLAY()
                            && dbmsSharedPreferences.getSearchConfClearLamp_FAR_AWAY()
                            && dbmsSharedPreferences.getSearchConfClearLamp_FAILED()
                            && dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_CLEAR()
                            && dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_EASY_CLEAR()
                            && dbmsSharedPreferences.getSearchConfClearLamp_EASY_CLEAR()
                            && dbmsSharedPreferences.getSearchConfClearLamp_NORMAL_CLEAR()
                            && dbmsSharedPreferences.getSearchConfClearLamp_HARD_CLEAR()
                            && dbmsSharedPreferences.getSearchConfClearLamp_EXHARD_CLEAR()
                            && dbmsSharedPreferences.getSearchConfClearLamp_FULL_COMBO()
                            && dbmsSharedPreferences.getSearchConfClearLamp_EXHARD_CLEAR()
                    ) {
                item_nav_where_clear_lamp_no_play.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_NO_PLAY(false);
                item_nav_where_clear_lamp_far_away.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_FAR_AWAY(false);
                item_nav_where_clear_lamp_failed.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_FAILED(false);
                item_nav_where_clear_lamp_assist_clear.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_CLEAR(false);
                item_nav_where_clear_lamp_assist_easy_clear.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_EASY_CLEAR(false);
                item_nav_where_clear_lamp_easy_clear.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_EASY_CLEAR(false);
                item_nav_where_clear_lamp_normal_clear.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_NORMAL_CLEAR(false);
                item_nav_where_clear_lamp_hard_clear.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_HARD_CLEAR(false);
                item_nav_where_clear_lamp_exhard_clear.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_EXHARD_CLEAR(false);
                item_nav_where_clear_lamp_full_combo.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_FULL_COMBO(false);
                item_nav_where_clear_lamp_perfect.setChecked(false);
                dbmsSharedPreferences.putSearchConfClearLamp_EXHARD_CLEAR(false);
            } else {
                item_nav_where_clear_lamp_no_play.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_NO_PLAY(true);
                item_nav_where_clear_lamp_far_away.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_FAR_AWAY(true);
                item_nav_where_clear_lamp_failed.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_FAILED(true);
                item_nav_where_clear_lamp_assist_clear.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_CLEAR(true);
                item_nav_where_clear_lamp_assist_easy_clear.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_EASY_CLEAR(true);
                item_nav_where_clear_lamp_easy_clear.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_EASY_CLEAR(true);
                item_nav_where_clear_lamp_normal_clear.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_NORMAL_CLEAR(true);
                item_nav_where_clear_lamp_hard_clear.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_HARD_CLEAR(true);
                item_nav_where_clear_lamp_exhard_clear.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_EXHARD_CLEAR(true);
                item_nav_where_clear_lamp_full_combo.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_FULL_COMBO(true);
                item_nav_where_clear_lamp_perfect.setChecked(true);
                dbmsSharedPreferences.putSearchConfClearLamp_EXHARD_CLEAR(true);
            }

        } else if (id == R.id.nav_where_clear_lamp_no_play) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_NO_PLAY(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_far_away) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_FAR_AWAY(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_failed) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_FAILED(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_assist_clear) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_CLEAR(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_assist_easy_clear) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_EASY_CLEAR(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_easy_clear) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_EASY_CLEAR(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_normal_clear) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_NORMAL_CLEAR(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_hard_clear) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_HARD_CLEAR(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_exhard_clear) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_EXHARD_CLEAR(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_full_combo) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_FULL_COMBO(selectedItem.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_perfect) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_PERFECT(selectedItem.isChecked());

        }

        // スコアランク
        if (id == R.id.nav_where_score_rank_all_on_off) {
            // 1つでもOFFのものがあれば全てONに設定
            // そうでなければ全てOFFに設定
            if (
                    dbmsSharedPreferences.getSearchConfScoreRank_AAA()
                            && dbmsSharedPreferences.getSearchConfScoreRank_AA()
                            && dbmsSharedPreferences.getSearchConfScoreRank_A()
                            && dbmsSharedPreferences.getSearchConfScoreRank_B()
                            && dbmsSharedPreferences.getSearchConfScoreRank_C()
                            && dbmsSharedPreferences.getSearchConfScoreRank_D()
                            && dbmsSharedPreferences.getSearchConfScoreRank_E()
                            && dbmsSharedPreferences.getSearchConfScoreRank_F()
                            && dbmsSharedPreferences.getSearchConfScoreRank_NO_RANK()
                    ) {
                item_nav_where_score_rank_aaa.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_AAA(false);
                item_nav_where_score_rank_aa.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_AA(false);
                item_nav_where_score_rank_a.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_A(false);
                item_nav_where_score_rank_b.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_B(false);
                item_nav_where_score_rank_c.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_C(false);
                item_nav_where_score_rank_d.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_D(false);
                item_nav_where_score_rank_e.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_E(false);
                item_nav_where_score_rank_f.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_F(false);
                item_nav_where_score_rank_no_rank.setChecked(false);
                dbmsSharedPreferences.putSearchConfScoreRank_NO_RANK(false);
            } else {
                item_nav_where_score_rank_aaa.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_AAA(true);
                item_nav_where_score_rank_aa.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_AA(true);
                item_nav_where_score_rank_a.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_A(true);
                item_nav_where_score_rank_b.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_B(true);
                item_nav_where_score_rank_c.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_C(true);
                item_nav_where_score_rank_d.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_D(true);
                item_nav_where_score_rank_e.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_E(true);
                item_nav_where_score_rank_f.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_F(true);
                item_nav_where_score_rank_no_rank.setChecked(true);
                dbmsSharedPreferences.putSearchConfScoreRank_NO_RANK(true);
            }

        } else if (id == R.id.nav_where_score_rank_aaa) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_AAA(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_aa) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_AA(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_a) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_A(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_b) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_B(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_c) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_C(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_d) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_D(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_e) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_E(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_f) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_F(selectedItem.isChecked());

        } else if (id == R.id.nav_where_score_rank_no_rank) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_NO_RANK(selectedItem.isChecked());

        }

        // BPM範囲
        if (id == R.id.nav_where_bpm_range_all_on_off) {
            // 1つでもOFFのものがあれば全てONに設定
            // そうでなければ全てOFFに設定
            if (
                    dbmsSharedPreferences.getSearchConfBpmRange_LLL_129()
                            && dbmsSharedPreferences.getSearchConfBpmRange_130_139()
                            && dbmsSharedPreferences.getSearchConfBpmRange_140_149()
                            && dbmsSharedPreferences.getSearchConfBpmRange_150_159()
                            && dbmsSharedPreferences.getSearchConfBpmRange_160_169()
                            && dbmsSharedPreferences.getSearchConfBpmRange_170_179()
                            && dbmsSharedPreferences.getSearchConfBpmRange_180_189()
                            && dbmsSharedPreferences.getSearchConfBpmRange_190_199()
                            && dbmsSharedPreferences.getSearchConfBpmRange_200_HHH()
                            && dbmsSharedPreferences.getSearchConfBpmRange_SOFLAN()
                    ) {
                item_nav_where_bpm_range_lll_129.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_LLL_129(false);
                item_nav_where_bpm_range_130_139.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_130_139(false);
                item_nav_where_bpm_range_140_149.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_140_149(false);
                item_nav_where_bpm_range_150_159.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_150_159(false);
                item_nav_where_bpm_range_160_169.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_160_169(false);
                item_nav_where_bpm_range_170_179.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_170_179(false);
                item_nav_where_bpm_range_180_189.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_180_189(false);
                item_nav_where_bpm_range_190_199.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_190_199(false);
                item_nav_where_bpm_range_200_hhh.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_200_HHH(false);
                item_nav_where_bpm_range_soflan.setChecked(false);
                dbmsSharedPreferences.putSearchConfBpmRange_SOFLAN(false);
            } else {
                item_nav_where_bpm_range_lll_129.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_LLL_129(true);
                item_nav_where_bpm_range_130_139.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_130_139(true);
                item_nav_where_bpm_range_140_149.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_140_149(true);
                item_nav_where_bpm_range_150_159.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_150_159(true);
                item_nav_where_bpm_range_160_169.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_160_169(true);
                item_nav_where_bpm_range_170_179.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_170_179(true);
                item_nav_where_bpm_range_180_189.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_180_189(true);
                item_nav_where_bpm_range_190_199.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_190_199(true);
                item_nav_where_bpm_range_200_hhh.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_200_HHH(true);
                item_nav_where_bpm_range_soflan.setChecked(true);
                dbmsSharedPreferences.putSearchConfBpmRange_SOFLAN(true);
            }

        } else if (id == R.id.nav_where_bpm_range_lll_129) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_LLL_129(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_130_139) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_130_139(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_140_149) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_140_149(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_150_159) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_150_159(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_160_169) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_160_169(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_170_179) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_170_179(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_180_189) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_180_189(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_190_199) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_190_199(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_200_hhh) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_200_HHH(selectedItem.isChecked());

        } else if (id == R.id.nav_where_bpm_range_soflan) {
            selectedItem.setChecked(!selectedItem.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_SOFLAN(selectedItem.isChecked());

        }

        // バージョン
        if (id == R.id.nav_where_version_all_on_off) {
            // 1つでもOFFのものがあれば全てONに設定
            // そうでなければ全てOFFに設定
            if (
                    dbmsSharedPreferences.getSearchConfVersion_1st()
                            && dbmsSharedPreferences.getSearchConfVersion_sub()
                            && dbmsSharedPreferences.getSearchConfVersion_2nd()
                            && dbmsSharedPreferences.getSearchConfVersion_3rd()
                            && dbmsSharedPreferences.getSearchConfVersion_4th()
                            && dbmsSharedPreferences.getSearchConfVersion_5th()
                            && dbmsSharedPreferences.getSearchConfVersion_6th()
                            && dbmsSharedPreferences.getSearchConfVersion_7th()
                            && dbmsSharedPreferences.getSearchConfVersion_8th()
                            && dbmsSharedPreferences.getSearchConfVersion_9th()
                            && dbmsSharedPreferences.getSearchConfVersion_10th()
                            && dbmsSharedPreferences.getSearchConfVersion_RED()
                            && dbmsSharedPreferences.getSearchConfVersion_SKY()
                            && dbmsSharedPreferences.getSearchConfVersion_DD()
                            && dbmsSharedPreferences.getSearchConfVersion_GOLD()
                            && dbmsSharedPreferences.getSearchConfVersion_DJT()
                            && dbmsSharedPreferences.getSearchConfVersion_EMP()
                            && dbmsSharedPreferences.getSearchConfVersion_SIR()
                            && dbmsSharedPreferences.getSearchConfVersion_RA()
                            && dbmsSharedPreferences.getSearchConfVersion_LC()
                            && dbmsSharedPreferences.getSearchConfVersion_tri()
                            && dbmsSharedPreferences.getSearchConfVersion_SPA()
                            && dbmsSharedPreferences.getSearchConfVersion_PEN()
                            && dbmsSharedPreferences.getSearchConfVersion_cop()
                    ) {
                item_nav_where_version_1st.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_1st(false);
                item_nav_where_version_sub.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_sub(false);
                item_nav_where_version_2nd.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_2nd(false);
                item_nav_where_version_3rd.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_3rd(false);
                item_nav_where_version_4th.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_4th(false);
                item_nav_where_version_5th.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_5th(false);
                item_nav_where_version_6th.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_6th(false);
                item_nav_where_version_7th.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_7th(false);
                item_nav_where_version_8th.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_8th(false);
                item_nav_where_version_9th.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_9th(false);
                item_nav_where_version_10th.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_10th(false);
                item_nav_where_version_RED.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_RED(false);
                item_nav_where_version_SKY.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_SKY(false);
                item_nav_where_version_DD.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_DD(false);
                item_nav_where_version_GOLD.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_GOLD(false);
                item_nav_where_version_DJT.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_DJT(false);
                item_nav_where_version_EMP.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_EMP(false);
                item_nav_where_version_SIR.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_SIR(false);
                item_nav_where_version_RA.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_RA(false);
                item_nav_where_version_LC.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_LC(false);
                item_nav_where_version_tri.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_tri(false);
                item_nav_where_version_SPA.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_SPA(false);
                item_nav_where_version_PEN.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_PEN(false);
                item_nav_where_version_cop.setChecked(false);
                dbmsSharedPreferences.putSearchConfVersion_cop(false);
            } else {
                item_nav_where_version_1st.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_1st(true);
                item_nav_where_version_sub.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_sub(true);
                item_nav_where_version_2nd.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_2nd(true);
                item_nav_where_version_3rd.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_3rd(true);
                item_nav_where_version_4th.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_4th(true);
                item_nav_where_version_5th.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_5th(true);
                item_nav_where_version_6th.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_6th(true);
                item_nav_where_version_7th.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_7th(true);
                item_nav_where_version_8th.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_8th(true);
                item_nav_where_version_9th.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_9th(true);
                item_nav_where_version_10th.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_10th(true);
                item_nav_where_version_RED.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_RED(true);
                item_nav_where_version_SKY.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_SKY(true);
                item_nav_where_version_DD.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_DD(true);
                item_nav_where_version_GOLD.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_GOLD(true);
                item_nav_where_version_DJT.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_DJT(true);
                item_nav_where_version_EMP.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_EMP(true);
                item_nav_where_version_SIR.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_SIR(true);
                item_nav_where_version_RA.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_RA(true);
                item_nav_where_version_LC.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_LC(true);
                item_nav_where_version_tri.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_tri(true);
                item_nav_where_version_SPA.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_SPA(true);
                item_nav_where_version_PEN.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_PEN(true);
                item_nav_where_version_cop.setChecked(true);
                dbmsSharedPreferences.putSearchConfVersion_cop(true);
            }
        }
        Map<Integer, String> versionIdValMap = AppConst.getVersionIdValMap();
        for (Map.Entry<Integer, String> versionIdValMapEntry : versionIdValMap.entrySet()) {
            if ( id == versionIdValMapEntry.getKey() ) {
                selectedItem.setChecked(!selectedItem.isChecked());
                try {
                    Method method = dbmsSharedPreferences.getClass().getMethod(
                            "putSearchConfVersion_" + versionIdValMapEntry.getValue(),
                            new Class[]{boolean.class});
                    method.invoke(dbmsSharedPreferences, new Object[] {selectedItem.isChecked()});
                } catch (Exception e) {
                    throw new DbmsSystemException(
                            AppConst.ERR_CD_90005,
                            AppConst.ERR_STEP_CD_MLAC_00001,
                            AppConst.ERR_MESSAGE_MLAC_00001);
                }
                break;
            }
        }

        // ORDER BY
        if (id == R.id.nav_orderby_difficult_name) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_difficult_name);

        } else if (id == R.id.nav_orderby_name) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_NAME);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_name);

        } else if (id == R.id.nav_orderby_clear_lamp) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEARLAMP);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_clear_lamp);

        } else if (id == R.id.nav_orderby_ex_score) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_EXSCORE);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_ex_score);

        } else if (id == R.id.nav_orderby_bp) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_BP);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_bp);

        } else if (id == R.id.nav_orderby_score_rate) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_SCORE_RATE);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_score_rate);

        } else if (id == R.id.nav_orderby_miss_rate) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_MISS_RATE);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_miss_rate);

        } else if (id == R.id.nav_orderby_updated) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_UPDATED);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_updated);

        }
/*
        else if (id == R.id.nav_orderby_clear_progress) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEAR_PROGRESS);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_clear_progress);

        }
*/

        if (id == R.id.nav_order_sort_kind_asc) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderSortKind(AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC);
            item_nav_order_sort_kind_desc.setChecked(false);

        } else if (id == R.id.nav_order_sort_kind_desc) {
            selectedItem.setChecked(true);
            dbmsSharedPreferences.putSearchOrderSortKind(AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_DESC);
            item_nav_order_sort_kind_asc.setChecked(false);

        }

        // SharedPreferenceに反映
        dbmsSharedPreferences.apply();

        LogUtil.logExiting();
    }

    /**
     * SharedPreferenceの設定値から、検索時のWHERE句を生成する。
     */
    public String generateWhereStatement() {
        LogUtil.logEntering();

        // 条件リストを作成
        // 難易度
        List<String> difficultCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfDiff_10() ) {
            difficultCondList.add(AppConst.MUSIC_MST_DIFFICULT_VAL_10);
        }
        if ( dbmsSharedPreferences.getSearchConfDiff_11() ) {
            difficultCondList.add(AppConst.MUSIC_MST_DIFFICULT_VAL_11);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( difficultCondList.size() == 0 ) {
            difficultCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // クリアランプ
        boolean clearLampIsNullFlg = false;
        List<String> clearLampCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfClearLamp_NO_PLAY() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY);
            clearLampIsNullFlg = true;
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_FAR_AWAY() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_FAILED() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_EASY_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_EASY_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_NORMAL_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_HARD_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_EXHARD_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_FULL_COMBO() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_PERFECT() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( clearLampCondList.size() == 0 ) {
            clearLampCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // スコアランク
        boolean scoreRankIsNullFlg = false;
        List<String> scoreRankCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfScoreRank_AAA() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_AAA);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_AA() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_AA);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_A() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_A);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_B() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_B);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_C() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_C);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_D() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_D);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_E() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_E);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_F() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_F);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_NO_RANK() ) {
            scoreRankIsNullFlg = true;
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( scoreRankCondList.size() == 0 && !scoreRankIsNullFlg) {
            scoreRankCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // BPM範囲
        int bpmRangeSelectedCnt = 0;
        List<List<String>> bpmRangeCondAndListOrList = new ArrayList<List<String>>();
        if ( dbmsSharedPreferences.getSearchConfBpmRange_LLL_129() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 130 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_130_139()) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 130 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 140 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_140_149() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 140 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 150 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_150_159() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 150 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 160 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_160_169() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 160 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 170 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_170_179() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 170 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 180 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_180_189() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 180 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 190 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_190_199() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 190 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 200 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_200_HHH() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 200 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_SOFLAN() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " != " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( bpmRangeCondAndListOrList.size() == 0 ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " == 9999 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }

        // バージョン
        List<String> versionCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfVersion_1st() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_1ST);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_sub() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SUB);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_2nd() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_2ND);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_3rd() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_3RD);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_4th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_4TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_5th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_5TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_6th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_6TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_7th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_7TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_8th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_8TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_9th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_9TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_10th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_10TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_RED() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_RED);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_SKY() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SKY);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_DD() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_DD);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_GOLD() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_GOLD);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_DJT() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_DJT);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_EMP() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_EMP);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_SIR() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SIR);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_RA() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_RA);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_LC() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_LC);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_tri() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_TRI);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_SPA() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SPA);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_PEN() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_PEN);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_cop() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_COP);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( versionCondList.size() == 0 ) {
            versionCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // 初回のみwhere、使用後は"and"を設定
        String sqlWhereAnd = "where";

        // 検索
        StringBuilder whereSb = new StringBuilder();

        // 検索条件：難易度
        String difficultCondStr = DbmsGreenDaoUtils.inStatementStr(difficultCondList);
        if (difficultCondStr.length() > 0) {
            whereSb.append(
                    sqlWhereAnd + " T." + MusicMstDao.Properties.Difficult.columnName + " in(" + difficultCondStr + ") "
            );
            sqlWhereAnd = "and";
        }

        // 検索条件：クリアランプ
        String clearLampCondStr = DbmsGreenDaoUtils.inStatementStr(clearLampCondList);
        if (clearLampCondStr.length() > 0) {

            whereSb.append(sqlWhereAnd);
            // NO PLAYの場合は結合して値NULLの場合も対象（前かっこ結合）
            if (clearLampIsNullFlg) {
                whereSb.append("(");
            }
            whereSb.append(
                    " T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + " in(" + clearLampCondStr + ") "
            );
            // NO PLAYの場合は結合して値NULLの場合も対象
            if (clearLampIsNullFlg) {
                whereSb.append(
                        "or T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + " IS NULL) "
                );
            }
            sqlWhereAnd = "and";
        }

        // 検索条件：スコアランク
        String scoreRankCondStr = DbmsGreenDaoUtils.inStatementStr(scoreRankCondList);
        if (scoreRankCondStr.length() > 0) {

            whereSb.append(sqlWhereAnd);
            // NO RANKの場合は結合して値NULLの場合も対象（前かっこ結合）
            if (scoreRankIsNullFlg) {
                whereSb.append("(");
            }
            whereSb.append(
                    " T0." + MusicResultDBHRDao.Properties.ScoreRank.columnName + " in(" + scoreRankCondStr + ") "
            );
            // NO RANKの場合は結合して値NULLの場合も対象
            if (scoreRankIsNullFlg) {
                whereSb.append(
                        "or T0." + MusicResultDBHRDao.Properties.ScoreRank.columnName + " IS NULL) "
                );
            }
            sqlWhereAnd = "and";
        } else {
            // NO RANKの場合は結合して値NULLの場合も対象（前かっこ結合）
            if (scoreRankIsNullFlg) {
                whereSb.append(sqlWhereAnd);
                whereSb.append(" T0." + MusicResultDBHRDao.Properties.ScoreRank.columnName + " IS NULL ");
            }
        }

        // 検索条件：BPM範囲
        if (bpmRangeCondAndListOrList.size() > 0) {

            whereSb.append(sqlWhereAnd);
            whereSb.append("(");

            boolean firstCondListFlg = true;
            for (List<String> bpmRangeCondAndList : bpmRangeCondAndListOrList) {

                if (!firstCondListFlg) {
                    whereSb.append("or ");
                }
                whereSb.append("(");

                boolean firstCondFlg = true;
                for (String bpmRangeCond : bpmRangeCondAndList) {

                    if (!firstCondFlg) {
                        whereSb.append("and ");
                    }

                    whereSb.append(bpmRangeCond);
                    whereSb.append(" ");
                    firstCondFlg = false;

                }

                whereSb.append(")");
                firstCondListFlg = false;

            }

            whereSb.append(")");
            sqlWhereAnd = "and";

        }

        // 検索条件：バージョン
        String versionCondStr = DbmsGreenDaoUtils.inStatementStr(versionCondList);
        if (versionCondStr.length() > 0) {
            whereSb.append(
                    sqlWhereAnd + " T." + MusicMstDao.Properties.Version.columnName + " in(" + versionCondStr + ") "
            );
            sqlWhereAnd = "and";
        }

        // ORDER SORT KIND
        String orderSortKind;
        String searchOrderSortKind = dbmsSharedPreferences.getSearchOrderSortKind();
        if ( AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC.equals(searchOrderSortKind) ) {
            orderSortKind = " ASC ";
        } else {
            orderSortKind = " DESC ";
        }

        // ORDER BY
        String searchOrderByTarget = dbmsSharedPreferences.getSearchOrderByTarget();
        if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_NAME.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEARLAMP.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by " + AppConst.CONST_SQL_ORDER_BY_CLEAR_LAMP_CASE + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_EXSCORE.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.ExScore.columnName       + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_BP.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.Bp.columnName            + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_SCORE_RATE.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.ScoreRate.columnName     + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_MISS_RATE.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.MissRate.columnName      + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_UPDATED.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.UpdDate.columnName       + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        }
/*
        else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEAR_PROGRESS.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + orderSortKind
                         + ", case T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + " "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR
                            + "' then (" + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                        + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                                 + " - T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + ") / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO
                            + "' then (" + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                        + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                                 + " - T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + ") / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT + "' then 1 end "
                    + orderSortKind
            );

        }
*/

        LogUtil.logExiting();
        return whereSb.toString();
    }


    //----------------------------
    // 非公開メソッド
    //----------------------------

    /**
     * idの配列と除外idを受け取り、除外id以外を選択OFFにする
     */
    private void checkOffOther(MenuItem[] otherIdItemArr, MenuItem zyogaiIdItem) {
        for (MenuItem otherIdItem: otherIdItemArr) {
            if (otherIdItem != zyogaiIdItem) {
                otherIdItem.setChecked(false);
            }
        }
    }

    //----------------------------
    // setter / getter
    //----------------------------
    public NavigationView getNavigationView() {
        return navigationView;
    }

    public void setNavigationView(NavigationView navigationView) {
        this.navigationView = navigationView;
    }

    public DbmsSharedPreferences getDbmsSharedPreferences() {
        return dbmsSharedPreferences;
    }
    public void setDbmsSharedPreferences(DbmsSharedPreferences dbmsSharedPreferences) {
        this.dbmsSharedPreferences = dbmsSharedPreferences;
    }

    public MenuItem getItem_nav_where_difficult_10() {
        return item_nav_where_difficult_10;
    }

    public void setItem_nav_where_difficult_10(MenuItem item_nav_where_difficult_10) {
        this.item_nav_where_difficult_10 = item_nav_where_difficult_10;
    }

    public MenuItem getItem_nav_where_difficult_11() {
        return item_nav_where_difficult_11;
    }

    public void setItem_nav_where_difficult_11(MenuItem item_nav_where_difficult_11) {
        this.item_nav_where_difficult_11 = item_nav_where_difficult_11;
    }

    public MenuItem getItem_nav_where_clear_lamp_no_play() {
        return item_nav_where_clear_lamp_no_play;
    }

    public void setItem_nav_where_clear_lamp_no_play(MenuItem item_nav_where_clear_lamp_no_play) {
        this.item_nav_where_clear_lamp_no_play = item_nav_where_clear_lamp_no_play;
    }

    public MenuItem getItem_nav_where_clear_lamp_far_away() {
        return item_nav_where_clear_lamp_far_away;
    }

    public void setItem_nav_where_clear_lamp_far_away(MenuItem item_nav_where_clear_lamp_far_away) {
        this.item_nav_where_clear_lamp_far_away = item_nav_where_clear_lamp_far_away;
    }

    public MenuItem getItem_nav_where_clear_lamp_failed() {
        return item_nav_where_clear_lamp_failed;
    }

    public void setItem_nav_where_clear_lamp_failed(MenuItem item_nav_where_clear_lamp_failed) {
        this.item_nav_where_clear_lamp_failed = item_nav_where_clear_lamp_failed;
    }

    public MenuItem getItem_nav_where_clear_lamp_assist_clear() {
        return item_nav_where_clear_lamp_assist_clear;
    }

    public void setItem_nav_where_clear_lamp_assist_clear(MenuItem item_nav_where_clear_lamp_assist_clear) {
        this.item_nav_where_clear_lamp_assist_clear = item_nav_where_clear_lamp_assist_clear;
    }

    public MenuItem getItem_nav_where_clear_lamp_assist_easy_clear() {
        return item_nav_where_clear_lamp_assist_easy_clear;
    }

    public void setItem_nav_where_clear_lamp_assist_easy_clear(MenuItem item_nav_where_clear_lamp_assist_easy_clear) {
        this.item_nav_where_clear_lamp_assist_easy_clear = item_nav_where_clear_lamp_assist_easy_clear;
    }

    public MenuItem getItem_nav_where_clear_lamp_easy_clear() {
        return item_nav_where_clear_lamp_easy_clear;
    }

    public void setItem_nav_where_clear_lamp_easy_clear(MenuItem item_nav_where_clear_lamp_easy_clear) {
        this.item_nav_where_clear_lamp_easy_clear = item_nav_where_clear_lamp_easy_clear;
    }

    public MenuItem getItem_nav_where_clear_lamp_normal_clear() {
        return item_nav_where_clear_lamp_normal_clear;
    }

    public void setItem_nav_where_clear_lamp_normal_clear(MenuItem item_nav_where_clear_lamp_normal_clear) {
        this.item_nav_where_clear_lamp_normal_clear = item_nav_where_clear_lamp_normal_clear;
    }

    public MenuItem getItem_nav_where_clear_lamp_hard_clear() {
        return item_nav_where_clear_lamp_hard_clear;
    }

    public void setItem_nav_where_clear_lamp_hard_clear(MenuItem item_nav_where_clear_lamp_hard_clear) {
        this.item_nav_where_clear_lamp_hard_clear = item_nav_where_clear_lamp_hard_clear;
    }

    public MenuItem getItem_nav_where_clear_lamp_exhard_clear() {
        return item_nav_where_clear_lamp_exhard_clear;
    }

    public void setItem_nav_where_clear_lamp_exhard_clear(MenuItem item_nav_where_clear_lamp_exhard_clear) {
        this.item_nav_where_clear_lamp_exhard_clear = item_nav_where_clear_lamp_exhard_clear;
    }

    public MenuItem getItem_nav_where_clear_lamp_full_combo() {
        return item_nav_where_clear_lamp_full_combo;
    }

    public void setItem_nav_where_clear_lamp_full_combo(MenuItem item_nav_where_clear_lamp_full_combo) {
        this.item_nav_where_clear_lamp_full_combo = item_nav_where_clear_lamp_full_combo;
    }

    public MenuItem getItem_nav_where_clear_lamp_perfect() {
        return item_nav_where_clear_lamp_perfect;
    }

    public void setItem_nav_where_clear_lamp_perfect(MenuItem item_nav_where_clear_lamp_perfect) {
        this.item_nav_where_clear_lamp_perfect = item_nav_where_clear_lamp_perfect;
    }

    public MenuItem getItem_nav_where_score_rank_aaa() {
        return item_nav_where_score_rank_aaa;
    }

    public void setItem_nav_where_score_rank_aaa(MenuItem item_nav_where_score_rank_aaa) {
        this.item_nav_where_score_rank_aaa = item_nav_where_score_rank_aaa;
    }

    public MenuItem getItem_nav_where_score_rank_aa() {
        return item_nav_where_score_rank_aa;
    }

    public void setItem_nav_where_score_rank_aa(MenuItem item_nav_where_score_rank_aa) {
        this.item_nav_where_score_rank_aa = item_nav_where_score_rank_aa;
    }

    public MenuItem getItem_nav_where_score_rank_a() {
        return item_nav_where_score_rank_a;
    }

    public void setItem_nav_where_score_rank_a(MenuItem item_nav_where_score_rank_a) {
        this.item_nav_where_score_rank_a = item_nav_where_score_rank_a;
    }

    public MenuItem getItem_nav_where_score_rank_b() {
        return item_nav_where_score_rank_b;
    }

    public void setItem_nav_where_score_rank_b(MenuItem item_nav_where_score_rank_b) {
        this.item_nav_where_score_rank_b = item_nav_where_score_rank_b;
    }

    public MenuItem getItem_nav_where_score_rank_c() {
        return item_nav_where_score_rank_c;
    }

    public void setItem_nav_where_score_rank_c(MenuItem item_nav_where_score_rank_c) {
        this.item_nav_where_score_rank_c = item_nav_where_score_rank_c;
    }

    public MenuItem getItem_nav_where_score_rank_d() {
        return item_nav_where_score_rank_d;
    }

    public void setItem_nav_where_score_rank_d(MenuItem item_nav_where_score_rank_d) {
        this.item_nav_where_score_rank_d = item_nav_where_score_rank_d;
    }

    public MenuItem getItem_nav_where_score_rank_e() {
        return item_nav_where_score_rank_e;
    }

    public void setItem_nav_where_score_rank_e(MenuItem item_nav_where_score_rank_e) {
        this.item_nav_where_score_rank_e = item_nav_where_score_rank_e;
    }

    public MenuItem getItem_nav_where_score_rank_f() {
        return item_nav_where_score_rank_f;
    }

    public void setItem_nav_where_score_rank_f(MenuItem item_nav_where_score_rank_f) {
        this.item_nav_where_score_rank_f = item_nav_where_score_rank_f;
    }

    public MenuItem getItem_nav_where_score_rank_no_rank() {
        return item_nav_where_score_rank_no_rank;
    }

    public void setItem_nav_where_score_rank_no_rank(MenuItem item_nav_where_score_rank_no_rank) {
        this.item_nav_where_score_rank_no_rank = item_nav_where_score_rank_no_rank;
    }

    public MenuItem getItem_nav_where_bpm_range_lll_129() {
        return item_nav_where_bpm_range_lll_129;
    }

    public void setItem_nav_where_bpm_range_lll_129(MenuItem item_nav_where_bpm_range_lll_129) {
        this.item_nav_where_bpm_range_lll_129 = item_nav_where_bpm_range_lll_129;
    }

    public MenuItem getItem_nav_where_bpm_range_130_139() {
        return item_nav_where_bpm_range_130_139;
    }

    public void setItem_nav_where_bpm_range_130_139(MenuItem item_nav_where_bpm_range_130_139) {
        this.item_nav_where_bpm_range_130_139 = item_nav_where_bpm_range_130_139;
    }

    public MenuItem getItem_nav_where_bpm_range_140_149() {
        return item_nav_where_bpm_range_140_149;
    }

    public void setItem_nav_where_bpm_range_140_149(MenuItem item_nav_where_bpm_range_140_149) {
        this.item_nav_where_bpm_range_140_149 = item_nav_where_bpm_range_140_149;
    }

    public MenuItem getItem_nav_where_bpm_range_150_159() {
        return item_nav_where_bpm_range_150_159;
    }

    public void setItem_nav_where_bpm_range_150_159(MenuItem item_nav_where_bpm_range_150_159) {
        this.item_nav_where_bpm_range_150_159 = item_nav_where_bpm_range_150_159;
    }

    public MenuItem getItem_nav_where_bpm_range_160_169() {
        return item_nav_where_bpm_range_160_169;
    }

    public void setItem_nav_where_bpm_range_160_169(MenuItem item_nav_where_bpm_range_160_169) {
        this.item_nav_where_bpm_range_160_169 = item_nav_where_bpm_range_160_169;
    }

    public MenuItem getItem_nav_where_bpm_range_170_179() {
        return item_nav_where_bpm_range_170_179;
    }

    public void setItem_nav_where_bpm_range_170_179(MenuItem item_nav_where_bpm_range_170_179) {
        this.item_nav_where_bpm_range_170_179 = item_nav_where_bpm_range_170_179;
    }

    public MenuItem getItem_nav_where_bpm_range_180_189() {
        return item_nav_where_bpm_range_180_189;
    }

    public void setItem_nav_where_bpm_range_180_189(MenuItem item_nav_where_bpm_range_180_189) {
        this.item_nav_where_bpm_range_180_189 = item_nav_where_bpm_range_180_189;
    }

    public MenuItem getItem_nav_where_bpm_range_190_199() {
        return item_nav_where_bpm_range_190_199;
    }

    public void setItem_nav_where_bpm_range_190_199(MenuItem item_nav_where_bpm_range_190_199) {
        this.item_nav_where_bpm_range_190_199 = item_nav_where_bpm_range_190_199;
    }

    public MenuItem getItem_nav_where_bpm_range_200_hhh() {
        return item_nav_where_bpm_range_200_hhh;
    }

    public void setItem_nav_where_bpm_range_200_hhh(MenuItem item_nav_where_bpm_range_200_hhh) {
        this.item_nav_where_bpm_range_200_hhh = item_nav_where_bpm_range_200_hhh;
    }

    public MenuItem getItem_nav_where_bpm_range_soflan() {
        return item_nav_where_bpm_range_soflan;
    }

    public void setItem_nav_where_bpm_range_soflan(MenuItem item_nav_where_bpm_range_soflan) {
        this.item_nav_where_bpm_range_soflan = item_nav_where_bpm_range_soflan;
    }

    public MenuItem getItem_nav_where_version_1st() {
        return item_nav_where_version_1st;
    }

    public void setItem_nav_where_version_1st(MenuItem item_nav_where_version_1st) {
        this.item_nav_where_version_1st = item_nav_where_version_1st;
    }

    public MenuItem getItem_nav_where_version_sub() {
        return item_nav_where_version_sub;
    }

    public void setItem_nav_where_version_sub(MenuItem item_nav_where_version_sub) {
        this.item_nav_where_version_sub = item_nav_where_version_sub;
    }

    public MenuItem getItem_nav_where_version_2nd() {
        return item_nav_where_version_2nd;
    }

    public void setItem_nav_where_version_2nd(MenuItem item_nav_where_version_2nd) {
        this.item_nav_where_version_2nd = item_nav_where_version_2nd;
    }

    public MenuItem getItem_nav_where_version_3rd() {
        return item_nav_where_version_3rd;
    }

    public void setItem_nav_where_version_3rd(MenuItem item_nav_where_version_3rd) {
        this.item_nav_where_version_3rd = item_nav_where_version_3rd;
    }

    public MenuItem getItem_nav_where_version_4th() {
        return item_nav_where_version_4th;
    }

    public void setItem_nav_where_version_4th(MenuItem item_nav_where_version_4th) {
        this.item_nav_where_version_4th = item_nav_where_version_4th;
    }

    public MenuItem getItem_nav_where_version_5th() {
        return item_nav_where_version_5th;
    }

    public void setItem_nav_where_version_5th(MenuItem item_nav_where_version_5th) {
        this.item_nav_where_version_5th = item_nav_where_version_5th;
    }

    public MenuItem getItem_nav_where_version_6th() {
        return item_nav_where_version_6th;
    }

    public void setItem_nav_where_version_6th(MenuItem item_nav_where_version_6th) {
        this.item_nav_where_version_6th = item_nav_where_version_6th;
    }

    public MenuItem getItem_nav_where_version_7th() {
        return item_nav_where_version_7th;
    }

    public void setItem_nav_where_version_7th(MenuItem item_nav_where_version_7th) {
        this.item_nav_where_version_7th = item_nav_where_version_7th;
    }

    public MenuItem getItem_nav_where_version_8th() {
        return item_nav_where_version_8th;
    }

    public void setItem_nav_where_version_8th(MenuItem item_nav_where_version_8th) {
        this.item_nav_where_version_8th = item_nav_where_version_8th;
    }

    public MenuItem getItem_nav_where_version_9th() {
        return item_nav_where_version_9th;
    }

    public void setItem_nav_where_version_9th(MenuItem item_nav_where_version_9th) {
        this.item_nav_where_version_9th = item_nav_where_version_9th;
    }

    public MenuItem getItem_nav_where_version_10th() {
        return item_nav_where_version_10th;
    }

    public void setItem_nav_where_version_10th(MenuItem item_nav_where_version_10th) {
        this.item_nav_where_version_10th = item_nav_where_version_10th;
    }

    public MenuItem getItem_nav_where_version_RED() {
        return item_nav_where_version_RED;
    }

    public void setItem_nav_where_version_RED(MenuItem item_nav_where_version_RED) {
        this.item_nav_where_version_RED = item_nav_where_version_RED;
    }

    public MenuItem getItem_nav_where_version_SKY() {
        return item_nav_where_version_SKY;
    }

    public void setItem_nav_where_version_SKY(MenuItem item_nav_where_version_SKY) {
        this.item_nav_where_version_SKY = item_nav_where_version_SKY;
    }

    public MenuItem getItem_nav_where_version_DD() {
        return item_nav_where_version_DD;
    }

    public void setItem_nav_where_version_DD(MenuItem item_nav_where_version_DD) {
        this.item_nav_where_version_DD = item_nav_where_version_DD;
    }

    public MenuItem getItem_nav_where_version_GOLD() {
        return item_nav_where_version_GOLD;
    }

    public void setItem_nav_where_version_GOLD(MenuItem item_nav_where_version_GOLD) {
        this.item_nav_where_version_GOLD = item_nav_where_version_GOLD;
    }

    public MenuItem getItem_nav_where_version_DJT() {
        return item_nav_where_version_DJT;
    }

    public void setItem_nav_where_version_DJT(MenuItem item_nav_where_version_DJT) {
        this.item_nav_where_version_DJT = item_nav_where_version_DJT;
    }

    public MenuItem getItem_nav_where_version_EMP() {
        return item_nav_where_version_EMP;
    }

    public void setItem_nav_where_version_EMP(MenuItem item_nav_where_version_EMP) {
        this.item_nav_where_version_EMP = item_nav_where_version_EMP;
    }

    public MenuItem getItem_nav_where_version_SIR() {
        return item_nav_where_version_SIR;
    }

    public void setItem_nav_where_version_SIR(MenuItem item_nav_where_version_SIR) {
        this.item_nav_where_version_SIR = item_nav_where_version_SIR;
    }

    public MenuItem getItem_nav_where_version_RA() {
        return item_nav_where_version_RA;
    }

    public void setItem_nav_where_version_RA(MenuItem item_nav_where_version_RA) {
        this.item_nav_where_version_RA = item_nav_where_version_RA;
    }

    public MenuItem getItem_nav_where_version_LC() {
        return item_nav_where_version_LC;
    }

    public void setItem_nav_where_version_LC(MenuItem item_nav_where_version_LC) {
        this.item_nav_where_version_LC = item_nav_where_version_LC;
    }

    public MenuItem getItem_nav_where_version_tri() {
        return item_nav_where_version_tri;
    }

    public void setItem_nav_where_version_tri(MenuItem item_nav_where_version_tri) {
        this.item_nav_where_version_tri = item_nav_where_version_tri;
    }

    public MenuItem getItem_nav_where_version_SPA() {
        return item_nav_where_version_SPA;
    }

    public void setItem_nav_where_version_SPA(MenuItem item_nav_where_version_SPA) {
        this.item_nav_where_version_SPA = item_nav_where_version_SPA;
    }

    public MenuItem getItem_nav_where_version_PEN() {
        return item_nav_where_version_PEN;
    }

    public void setItem_nav_where_version_PEN(MenuItem item_nav_where_version_PEN) {
        this.item_nav_where_version_PEN = item_nav_where_version_PEN;
    }

    public MenuItem getItem_nav_where_version_cop() {
        return item_nav_where_version_cop;
    }

    public void setItem_nav_where_version_cop(MenuItem item_nav_where_version_cop) {
        this.item_nav_where_version_cop = item_nav_where_version_cop;
    }

    public MenuItem getItem_nav_orderby_difficult_name() {
        return item_nav_orderby_difficult_name;
    }

    public void setItem_nav_orderby_difficult_name(MenuItem item_nav_orderby_difficult_name) {
        this.item_nav_orderby_difficult_name = item_nav_orderby_difficult_name;
    }

    public MenuItem getItem_nav_orderby_name() {
        return item_nav_orderby_name;
    }

    public void setItem_nav_orderby_name(MenuItem item_nav_orderby_name) {
        this.item_nav_orderby_name = item_nav_orderby_name;
    }

    public MenuItem getItem_nav_orderby_ex_score() {
        return item_nav_orderby_ex_score;
    }

    public void setItem_nav_orderby_ex_score(MenuItem item_nav_orderby_ex_score) {
        this.item_nav_orderby_ex_score = item_nav_orderby_ex_score;
    }

    public MenuItem getItem_nav_orderby_bp() {
        return item_nav_orderby_bp;
    }

    public void setItem_nav_orderby_bp(MenuItem item_nav_orderby_bp) {
        this.item_nav_orderby_bp = item_nav_orderby_bp;
    }

    public MenuItem getItem_nav_orderby_score_rate() {
        return item_nav_orderby_score_rate;
    }

    public void setItem_nav_orderby_score_rate(MenuItem item_nav_orderby_score_rate) {
        this.item_nav_orderby_score_rate = item_nav_orderby_score_rate;
    }

    public MenuItem getItem_nav_orderby_miss_rate() {
        return item_nav_orderby_miss_rate;
    }

    public void setItem_nav_orderby_miss_rate(MenuItem item_nav_orderby_miss_rate) {
        this.item_nav_orderby_miss_rate = item_nav_orderby_miss_rate;
    }

    public MenuItem getItem_nav_orderby_updated() {
        return item_nav_orderby_updated;
    }

    public void setItem_nav_orderby_updated(MenuItem item_nav_orderby_updated) {
        this.item_nav_orderby_updated = item_nav_orderby_updated;
    }

    public MenuItem[] getAllItemNavOrderByArr() {
        return allItemNavOrderByArr;
    }

    public void setAllItemNavOrderByArr(MenuItem[] allItemNavOrderByArr) {
        this.allItemNavOrderByArr = allItemNavOrderByArr;
    }

    public MenuItem getItem_nav_order_sort_kind_asc() {
        return item_nav_order_sort_kind_asc;
    }

    public void setItem_nav_order_sort_kind_asc(MenuItem item_nav_order_sort_kind_asc) {
        this.item_nav_order_sort_kind_asc = item_nav_order_sort_kind_asc;
    }

    public MenuItem getItem_nav_order_sort_kind_desc() {
        return item_nav_order_sort_kind_desc;
    }

    public void setItem_nav_order_sort_kind_desc(MenuItem item_nav_order_sort_kind_desc) {
        this.item_nav_order_sort_kind_desc = item_nav_order_sort_kind_desc;
    }

}
