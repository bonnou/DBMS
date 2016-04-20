package net.in.ahr.dbms.presenters.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.network.google.spreadSheet.GSSAsyncTask;
import net.in.ahr.dbms.data.strage.background.ResultExportIntentService;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.events.musicList.ProgresDialogDismissEvent;
import net.in.ahr.dbms.others.events.musicList.ProgresDialogShowEvent;
import net.in.ahr.dbms.others.events.musicList.SearchApplyEvent;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;
import net.in.ahr.dbms.presenters.fragments.MusicListFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHRDao;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MusicListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ListView musicListView;

    /** プログレスバー */
    private ProgressDialog progressDialog;

    // 難易度
    MenuItem item_nav_where_difficult_10;
    MenuItem item_nav_where_difficult_11;

    // クリアランプ
    MenuItem item_nav_where_clear_lamp_no_play;
    MenuItem item_nav_where_clear_lamp_failed;
    MenuItem item_nav_where_clear_lamp_assist_clear;
    MenuItem item_nav_where_clear_lamp_assist_easy_clear;
    MenuItem item_nav_where_clear_lamp_easy_clear;
    MenuItem item_nav_where_clear_lamp_normal_clear;
    MenuItem item_nav_where_clear_lamp_hard_clear;
    MenuItem item_nav_where_clear_lamp_exhard_clear;
    MenuItem item_nav_where_clear_lamp_full_combo;
    MenuItem item_nav_where_clear_lamp_perfect;

    // スコアランク
    MenuItem item_nav_where_score_rank_aaa;
    MenuItem item_nav_where_score_rank_aa;
    MenuItem item_nav_where_score_rank_a;
    MenuItem item_nav_where_score_rank_b;
    MenuItem item_nav_where_score_rank_c;
    MenuItem item_nav_where_score_rank_d;
    MenuItem item_nav_where_score_rank_e;
    MenuItem item_nav_where_score_rank_f;
    MenuItem item_nav_where_score_rank_no_rank;

    // BPM範囲
    MenuItem item_nav_where_bpm_range_lll_129;
    MenuItem item_nav_where_bpm_range_130_139;
    MenuItem item_nav_where_bpm_range_140_149;
    MenuItem item_nav_where_bpm_range_150_159;
    MenuItem item_nav_where_bpm_range_160_169;
    MenuItem item_nav_where_bpm_range_170_179;
    MenuItem item_nav_where_bpm_range_180_189;
    MenuItem item_nav_where_bpm_range_190_199;
    MenuItem item_nav_where_bpm_range_200_hhh;
    MenuItem item_nav_where_bpm_range_soflan;

    // バージョン
    MenuItem item_nav_where_version_1st;
    MenuItem item_nav_where_version_sub;
    MenuItem item_nav_where_version_2nd;
    MenuItem item_nav_where_version_3rd;
    MenuItem item_nav_where_version_4th;
    MenuItem item_nav_where_version_5th;
    MenuItem item_nav_where_version_6th;
    MenuItem item_nav_where_version_7th;
    MenuItem item_nav_where_version_8th;
    MenuItem item_nav_where_version_9th;
    MenuItem item_nav_where_version_10th;
    MenuItem item_nav_where_version_RED;
    MenuItem item_nav_where_version_SKY;
    MenuItem item_nav_where_version_DD;
    MenuItem item_nav_where_version_GOLD;
    MenuItem item_nav_where_version_DJT;
    MenuItem item_nav_where_version_EMP;
    MenuItem item_nav_where_version_SIR;
    MenuItem item_nav_where_version_RA;
    MenuItem item_nav_where_version_LC;
    MenuItem item_nav_where_version_tri;
    MenuItem item_nav_where_version_SPA;
    MenuItem item_nav_where_version_PEN;
    MenuItem item_nav_where_version_cop;

    // ORDER BY
    MenuItem item_nav_orderby_difficult_name;
    MenuItem item_nav_orderby_name;
    MenuItem item_nav_orderby_ex_score;
    MenuItem item_nav_orderby_bp;
    MenuItem item_nav_orderby_score_rate;
    MenuItem item_nav_orderby_miss_rate;
    MenuItem item_nav_orderby_updated;
//    MenuItem item_nav_orderby_clear_progress;

    MenuItem[] allItemNavOrderByArr;

    // ORDER SORT KIND
    MenuItem item_nav_order_sort_kind_asc;
    MenuItem item_nav_order_sort_kind_desc;

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    private static MusicResultDBHRDao getMusicResultDBHRDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicResultDBHRDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_music_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_LIST);
//            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
            setSupportActionBar(toolbar);


            // 以下エラー回避
            // Caused by: java.lang.NullPointerException: Attempt to invoke interface method 'android.view.View android.view.MenuItem.getActionView()' on a null object reference
            toolbar.inflateMenu(R.menu.menu_music);



            // 自動CSVエクスポートサービス起動
            scheduleService();





/* // FloatingActionButton削除
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            // SharedPreferencesラッパー取得
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences).edit();

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

/*
        // 新規メモをINSERT
        Memo newMemo = new Memo();
        newMemo.setText(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        getMemoDao(this).insertOrReplace(newMemo);

        // メモをSELECT
        List<Memo> list = getMemoDao(this).loadAll();
        StringBuilder sb = new StringBuilder();
        for (Memo oldMemo: list) {
            sb.append(oldMemo.getText());
            sb.append(" ");
        }
*/

            // TODO TextViewがあるとfragmentが表示されないのはなぜ？
            // TextViewへの値設定
//        TextView textView = (TextView) findViewById(R.id.musicListTextView);
//        textView.setText( sb.toString() + "TextView" );

/*
        MusicListFragment musicListFragment = new MusicListFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.musicListFragmentContainer, musicListFragment, "");
        transaction.commit();
*/

//            ((CustomApplication) this.getApplicationContext()).getDaoSession().getMusicMstDao().deleteAll();

            // 曲一覧画面を表示
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            MusicListFragment musicListFragment = new MusicListFragment();
            transaction.add(R.id.musicFragment, musicListFragment, MusicListFragment.TAG);
//            transaction.addToBackStack(MusicListFragment.TAG);
            transaction.commit();

            // 絞り込み検索
/*
            SearchView searchView = (SearchView) toolbar.getMenu().findItem(R.id.action_refine_search).getActionView();
            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            // 変更で反映
            searchView.setSubmitButtonEnabled(false);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String queryText) {
                    if (TextUtils.isEmpty(queryText)) {
                        // MusicListFragmentのフィルタクリア
                        LogUtil.logDebug("曲一覧フィルタクリア");
                        musicListView.clearTextFilter();
                    } else {
                        // MusicListFragmentのフィルタ実施
                        LogUtil.logDebug("曲一覧フィルタクリア実施：" + queryText);
                        musicListView.setFilterText(queryText);
                    }
                    return true;
                }

                @Override
                public boolean onQueryTextSubmit(String queryText) {
                    // do nothing
                    return false;
                }
            });
            // searchView.clearFocus();
*/

        } catch (Exception e) {
            // TODO: http://www.adamrocker.com/blog/288/bug-report-system-for-android.html
            throw e;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);

        // 絞り込み検索
        MenuItem searchItem = menu.findItem(R.id.action_refine_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // デフォルト英字
        searchView.setInputType(
                InputType.TYPE_CLASS_TEXT
                        | InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        // 変更で反映
        searchView.setSubmitButtonEnabled(false);
        searchView.setQueryHint("曲名絞り込み(部分一致)");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String queryText) {
                // フィルター取得
                // TODO: このstaticの使い方ってありなのか・・・？
                Filter filter = ((MusicListAdapter)getMusicListView().getAdapter()).getFilter();
                if (TextUtils.isEmpty(queryText)) {
                    // MusicListFragmentのフィルタクリア
                    LogUtil.logDebug("曲一覧フィルタクリア");
                    filter.filter(null);
                    // getMusicListView().getAdapter().clearTextFilter();
                } else {
                    // MusicListFragmentのフィルタ実施
                    LogUtil.logDebug("曲一覧フィルタクリア実施：" + queryText);
                    filter.filter(queryText);
                    // ※以下の方法だと謎のラベル表示が行われてしまう
                    //  http://stackoverflow.com/questions/23857313/filter-text-display-in-searchview-not-removing
                    // getMusicListView().setFilterText(queryText);
                }
                // TODO: trueでもfalseでも問題なく動くけど・・・？
                return false;
            }

            @Override
            public boolean onQueryTextSubmit(String queryText) {
                // do nothing
                return false;
            }
        });


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_import_gss) {
            // スプレッドシート取得
            Toast.makeText(this, "BEGIN import from Google Spread Sheet...", Toast.LENGTH_LONG).show();
            GSSAsyncTask gSSAsyncTask = new GSSAsyncTask(this);
            gSSAsyncTask.execute();
            return true;

        } else if (id == R.id.action_export_csv) {
            // 全件取得しcsvに書き込み
            Toast.makeText(this, "BEGIN export DB to CSV...", Toast.LENGTH_LONG).show();
            List<MusicMst> musicMstList = getMusicMstDao(getApplicationContext()).loadAll();
            MusicMstMaintenance musicMstMaintenance = new MusicMstMaintenance();
            musicMstMaintenance.exportMusicInfoToCsv(musicMstList, getApplicationContext());
            Toast.makeText(this, "END export DB to CSV...", Toast.LENGTH_LONG).show();

        } else if (id == R.id.action_import_csv) {
            // プログレスダイアログ生成
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("CSVインポート実施中...");
            progressDialog.setMessage("しばらくお待ちください。");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setMax(AppConst.MUSIC_MST_MIG_VER_CD_AFT_CNT_1);

            // csvインポート処理呼び出し
            MusicMstMaintenance musicMstMaintenance = new MusicMstMaintenance();
            // ※getApplicationContextだと落ちる
            musicMstMaintenance.importMusicInfoFromCsv(MusicListActivity.this);

        } else if (id == R.id.action_debug_crash) {
            // ダイアログで確認してから実施
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle(getResources().getString(R.string.action_debug_crash));
            alertDialogBuilder.setMessage("曲リザルトを全て削除し異常終了します。よろしいですか？");
            // OKボタン
            alertDialogBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // 曲マスタ＆リザルト全消し
                            getMusicMstDao(getApplicationContext()).deleteAll();
                            getMusicResultDBHRDao(getApplicationContext()).deleteAll();

                            // ランタイム例外発生
                            throw new DbmsSystemException(
                                    AppConst.ERR_CD_90000,
                                    AppConst.ERR_STEP_CD_MEAC_00001,
                                    AppConst.ERR_MESSAGE_MEAC_00001);
                        }
                    });
            // Cancelボタン
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder.setNegativeButton("Cancel",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
            AlertDialog alertDialog = alertDialogBuilder.create();
            // アラートダイアログを表示
            alertDialog.show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        // SharedPreferenceラッパー取得
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences).edit();

        // メニュー取得
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

        int id = item.getItemId();

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
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfDiff_10(item.isChecked());

        } else if (id == R.id.nav_where_difficult_11) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfDiff_11(item.isChecked());

        }

        // クリアランプ
        if (id == R.id.nav_where_clear_lamp_all_on_off) {
            // 1つでもOFFのものがあれば全てONに設定
            // そうでなければ全てOFFに設定
            if (
                    dbmsSharedPreferences.getSearchConfClearLamp_NO_PLAY()
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
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_NO_PLAY(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_failed) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_FAILED(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_assist_clear) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_CLEAR(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_assist_easy_clear) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_ASSIST_EASY_CLEAR(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_easy_clear) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_EASY_CLEAR(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_normal_clear) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_NORMAL_CLEAR(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_hard_clear) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_HARD_CLEAR(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_exhard_clear) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_EXHARD_CLEAR(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_full_combo) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_FULL_COMBO(item.isChecked());

        } else if (id == R.id.nav_where_clear_lamp_perfect) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfClearLamp_PERFECT(item.isChecked());

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
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_AAA(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_aa) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_AA(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_a) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_A(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_b) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_B(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_c) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_C(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_d) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_D(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_e) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_E(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_f) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_F(item.isChecked());

        } else if (id == R.id.nav_where_score_rank_no_rank) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfScoreRank_NO_RANK(item.isChecked());

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
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_LLL_129(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_130_139) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_130_139(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_140_149) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_140_149(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_150_159) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_150_159(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_160_169) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_160_169(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_170_179) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_170_179(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_180_189) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_180_189(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_190_199) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_190_199(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_200_hhh) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_200_HHH(item.isChecked());

        } else if (id == R.id.nav_where_bpm_range_soflan) {
            item.setChecked(!item.isChecked());
            dbmsSharedPreferences.putSearchConfBpmRange_SOFLAN(item.isChecked());

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
                item.setChecked(!item.isChecked());
                try {
                    Method method = dbmsSharedPreferences.getClass().getMethod(
                            "putSearchConfVersion_" + versionIdValMapEntry.getValue(),
                            new Class[]{boolean.class});
                    method.invoke(dbmsSharedPreferences, new Object[] {item.isChecked()});
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
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_difficult_name);

        } else if (id == R.id.nav_orderby_name) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_NAME);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_name);

        } else if (id == R.id.nav_orderby_ex_score) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_EXSCORE);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_ex_score);

        } else if (id == R.id.nav_orderby_bp) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_BP);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_bp);

        } else if (id == R.id.nav_orderby_score_rate) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_SCORE_RATE);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_score_rate);

        } else if (id == R.id.nav_orderby_miss_rate) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_MISS_RATE);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_miss_rate);

        } else if (id == R.id.nav_orderby_updated) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_UPDATED);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_updated);

        }
/*
        else if (id == R.id.nav_orderby_clear_progress) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderByTarget(AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEAR_PROGRESS);
            checkOffOther(allItemNavOrderByArr, item_nav_orderby_clear_progress);

        }
*/

        if (id == R.id.nav_order_sort_kind_asc) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderSortKind(AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC);
            item_nav_order_sort_kind_desc.setChecked(false);

        } else if (id == R.id.nav_order_sort_kind_desc) {
            item.setChecked(true);
            dbmsSharedPreferences.putSearchOrderSortKind(AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_DESC);
            item_nav_order_sort_kind_asc.setChecked(false);

        }

        // SharedPreferenceに反映
        dbmsSharedPreferences.apply();

        // 再検索
        searchApplyToListView();

        // NavigationDrawerを閉じる（閉じさせないようコメントアウト）
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

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

    /**
     * 曲リスト再検索処理
     */
    public void searchApplyToListView() {
        MusicListAdapter musicListAdapter = (MusicListAdapter) musicListView.getAdapter();
        musicListAdapter.searchApplyToListView(this);
    }



    @Override
    public boolean dispatchKeyEvent(KeyEvent e) {
        LogUtil.logEntering();

        // 戻るボタンが押されたとき
        if(e.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            // ボタンが押されたとき
            if (e.getAction() == KeyEvent.ACTION_DOWN) {

                // フラグメントのスタックに残があれば1つ前に戻る
                //   ※getSupportFragmentManagerを呼び出していたから常に0が帰ってきてハマった。
                int backStackCnt = getFragmentManager().getBackStackEntryCount();
                LogUtil.logDebug("backStackCnt:" + backStackCnt);
                if ( backStackCnt != 0 ) {
                    // Navigation Drowerを非表示ロック
                    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                    // バックスタックのフラグメントをポップ
                    getFragmentManager().popBackStack();

                    // trueを返却することでバックキーを無効化
                    return true;
                }

            }
        }

        LogUtil.logExiting();
        return super.dispatchKeyEvent(e);
    }



    public static ListView getMusicListView() {
        return musicListView;
    }

    public static void setMusicListView(ListView musicListView) {
        MusicListActivity.musicListView = musicListView;
    }

/*
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
*/

    @Override
    protected void onResume() {
        LogUtil.logEntering();

        super.onResume();
        // EventBus の登録
        EventBus.getDefault().register(this);

        LogUtil.logExiting();
    }

    @Override
    protected void onPause() {
        LogUtil.logEntering();

        // 登録の解除
        EventBus.getDefault().unregister(this);
        super.onPause();

        LogUtil.logExiting();
    }

    // EventBusイベントハンドラの宣言
    @Subscribe
    public void onEvent(SearchApplyEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★SearchApplyEvent★★★");

        searchApplyToListView();

        LogUtil.logExiting();
    }

/*
    @Subscribe
    public void onEvent(ProgresDialogIncrementEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★ProgresDialogIncrementEvent★★★");

        progressDialog.setProgress(progressDialog.getProgress() + 1);

        LogUtil.logExiting();
    }

                progressDialog.show();
*/

    @Subscribe
    public void onEvent(ProgresDialogShowEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★ProgresDialogShowEvent★★★");

        progressDialog.show();

        LogUtil.logExiting();
    }

    @Subscribe
    public void onEvent(ProgresDialogDismissEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★ProgresDialogDismissEvent★★★");

        progressDialog.dismiss();

        LogUtil.logExiting();
    }

/*
    // イベントハンドラの宣言。メインスレッド上で実行することを強制する場合
    public void onEventMainThread(CursorLoadedEvent event) {

    }
*/

    /**
     * 自動CSVエクスポートサービス起動
     */
    public void scheduleService() {
        LogUtil.logEntering();

        Context context = getBaseContext();
        Intent intent = new Intent(context, ResultExportIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, -1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // 1時間に1回実施
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.setInexactRepeating(AlarmManager.RTC, System.currentTimeMillis(), 1000 * 60 * 60, pendingIntent);
    }

    /*
    protected void cancelService(){
        Context context = getBaseContext();
        Intent intent = new Intent(context, ResultExportIntentService.class);
        PendingIntent pendingIntent = PendingIntent.getService(context, -1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
*/
}
