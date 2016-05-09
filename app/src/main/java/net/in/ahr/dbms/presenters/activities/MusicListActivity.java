package net.in.ahr.dbms.presenters.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
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
import android.view.View;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.network.api.util.DbmsApiUtils;
import net.in.ahr.dbms.data.network.google.spreadSheet.GSSAsyncTask;
import net.in.ahr.dbms.data.network.api.asyncTask.PostJSONAsyncTask;
import net.in.ahr.dbms.data.strage.background.ResultExportIntentService;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.events.musicList.DisplayLongToastEvent;
import net.in.ahr.dbms.others.events.musicList.DoOauthEvent;
import net.in.ahr.dbms.others.events.musicList.ProgresDialogDismissEvent;
import net.in.ahr.dbms.others.events.musicList.ProgresDialogShowEvent;
import net.in.ahr.dbms.others.events.musicList.SearchApplyEvent;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;
import net.in.ahr.dbms.presenters.fragments.DbmsSettingFlagment;
import net.in.ahr.dbms.presenters.fragments.MusicEditFragment;
import net.in.ahr.dbms.presenters.fragments.MusicHistoryListFragment;
import net.in.ahr.dbms.presenters.others.SearchNaviManager;
import net.in.ahr.dbms.presenters.tabManagers.BaseFragment;
import net.in.ahr.dbms.presenters.tabManagers.CustomViewPager;
import net.in.ahr.dbms.presenters.tabManagers.ViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHRDao;

/**
 * 曲一覧画面Activity。本アプリのルートActivity。
 */
public  class MusicListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   ViewPager.OnPageChangeListener {

    public static final int TAB_PAGE_SIZE = 2;
    public static final int TAB_PAGE_0 = 0;
    public static final int TAB_PAGE_1 = 1;

    /** OAuth認証処理中フラグ */
    public static boolean nowOAuthingFlg = false;

    /** 曲一覧 */
    public static ListView musicListView;

    /** ViewPager */
    TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    public void replaceChild(BaseFragment oldFrg, int position) {
        viewPagerAdapter.replaceChildFragment(oldFrg, position);
    }
    public static int position;
    public static MusicMst musicForEdit;
    public static int dispTopViewPosition = 0;

    /** プログレスバー */
    private ProgressDialog progressDialog;

    /** ツールバー */
    public Toolbar toolbar;
    public Toolbar getToolbar() { return toolbar; }

    /** NavigationDrawer用トグル */
    public ActionBarDrawerToggle toggle;
    public ActionBarDrawerToggle getToggle() { return toggle; }

    public void setToggle(ActionBarDrawerToggle toggle) { this.toggle = toggle; }

    public DrawerLayout drawer;
    public DrawerLayout getDrawer() { return drawer; }


    /** 検索・ソート条件管理オブジェクト */
    SearchNaviManager searchNaviManager;
    public SearchNaviManager getSearchNaviManager() {
        return searchNaviManager;
    }

    public void setSearchNaviManager(SearchNaviManager searchNaviManager) {
        this.searchNaviManager = searchNaviManager;
    }


    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    private static MusicResultDBHRDao getMusicResultDBHRDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicResultDBHRDao();
    }

    /**
     * 曲一覧画面ActivityのonCreateメソッド。本アプリの（ほぼ）mainメソッド。
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.logEntering();
/*
        // debugリリースの場合のみStrictModeを使用
        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .penaltyDeath()
                    .build());
        }
*/
        super.onCreate(savedInstanceState);

        // レイアウトXML指定
        setContentView(R.layout.activity_music_list);

        // タブ設定
        // ※スワイプ遷移不可のCustomViewPagerを使用
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (CustomViewPager)findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        // タブ変更リスナを設定
        viewPager.addOnPageChangeListener(this);

        // 自動CSVエクスポートサービス起動
        scheduleService();

        initToolbar();


        // TODO TextViewがあるとfragmentが表示されないのはなぜ？
        // TextViewへの値設定
//        TextView textView = (TextView) findViewById(R.id.musicListTextView);
//        textView.setText( sb.toString() + "TextView" );

/*
        // 曲一覧画面を表示
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MusicListFragment musicListFragment = new MusicListFragment();
        transaction.add(R.id.musicFragment, musicListFragment, MusicListFragment.TAG);
//            transaction.addToBackStack(MusicListFragment.TAG);
        transaction.commit();
*/

        LogUtil.logExiting();
    }

    private void initToolbar() {
        LogUtil.logEntering();

        // SharedPreferencesラッパー取得
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences).edit();

        // ツールバー設定
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_music);

        // 検索・ソートNavigationDrawer設定
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // http://stackoverflow.com/questions/26588917/appcompat-v7-toolbar-onoptionsitemselected-not-called
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        toggle = new ActionBarDrawerToggle(this, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 検索・ソートNavigationDrawer管理オブジェクト生成・保持
        SearchNaviManager searchNaviManager = new SearchNaviManager(navigationView, dbmsSharedPreferences);
        setSearchNaviManager(searchNaviManager);

        // アプリ起動時の検索・ソートNavigationDrawerの条件設定処理
        searchNaviManager.settingAtOnCreate();

        LogUtil.logExiting();
    }

    /**
     * Override onBackPressed()
     */
    @Override
    public void onBackPressed() {
        LogUtil.logEntering();

        // NavigationDrawerトグルオープン時はトグルクローズのみ実施
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();

            Fragment fragment = (Fragment) getSupportFragmentManager().
                    findFragmentByTag("android:switcher:" + R.id.pager + ":" + viewPager.getCurrentItem());

            if (fragment != null && fragment instanceof BaseFragment) // could be null if not instantiated yet
            {
                if (fragment.getView() != null) {
                    BaseFragment bf = (BaseFragment)fragment;
                    if(bf.isShowingChild()) {
                        replaceChild(bf, viewPager.getCurrentItem());
                    }
                    else {
                        finish();
                    }
                }
            }

        }

        LogUtil.logExiting();
    }

    /**
     * Override onCreateOptionsMenu()
     */
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
                Filter filter = ((MusicListAdapter) getMusicListView().getAdapter()).getFilter();
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
        LogUtil.logEntering();

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            // 設定画面を表示
            android.app.FragmentManager manager = getFragmentManager();
            android.app.FragmentTransaction transaction = manager.beginTransaction();
            DbmsSettingFlagment dbmsSettingFlagment = new DbmsSettingFlagment();
//            transaction.replace(R.id.musicFragment, dbmsSettingFlagment).addToBackStack(null);
            transaction.add(R.id.dbmsSettingFragment, dbmsSettingFlagment, DbmsSettingFlagment.TAG).addToBackStack(null);
            transaction.commit();

            // タブを一時的に非表示にする
            tabLayout.setVisibility(View.GONE);

            // スライドメニューをCLOSEでロック
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);


        } else if (id == R.id.action_import_gss) {
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

        } else if (id == R.id.action_debug_api_getAllResult) {
            String url = BuildConfig.DBMS_ONLINE_PATH + AppConst.DBMS_ONLINE_API_PATH_GET_ALL_RESULT;

            // 全件取得API
            new PostJSONAsyncTask(url, null).execute(this);

        } else if (id == R.id.action_debug_api_insertResult) {

            // 1件登録API
            MusicMst music = (MusicMst) musicListView.getAdapter().getItem(musicListView.getFirstVisiblePosition());
            DbmsApiUtils.postToInsertResultApi(music, this);

        } else if (id == R.id.action_debug_api_insertAllResults) {

            // 1件登録API
            List<MusicMst> musicList = getMusicMstDao(this).loadAll();
            DbmsApiUtils.postToInsertAllResultsApi(musicList, this);

        } else if (id == R.id.action_debug_reset_session_id) {

            // SharedPreferencesに設定しているセッションID情報をクリア
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences).edit();
            dbmsSharedPreferences.putDbmsOnlineSessionIdCookieName(AppConst.CONST_BLANK);
            dbmsSharedPreferences.putDbmsOnlineSessionIdCookieValue(AppConst.CONST_BLANK);
            dbmsSharedPreferences.apply();

        }

        LogUtil.logExiting();
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        LogUtil.logEntering();

        // NavigationDrawerの項目押下時設定処理
        getSearchNaviManager().settingAtOnNaviSelected(item);

        // 再検索
        searchApplyToListView();

        // NavigationDrawerを閉じる（閉じさせないようコメントアウト）
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);

        LogUtil.logExiting();
        return true;
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

                    // タブを表示する
                    // ※設定画面表示時にGONE（詰めて消す）に設定しているため
                    tabLayout.setVisibility(View.VISIBLE);

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


        Intent intent = getIntent();
        String action = intent.getAction();

        if (Intent.ACTION_VIEW.equals(action)) {
            Uri uri = intent.getData();
            if (uri != null) {

/*
                String code = sharedPreferences.getString("CODE","ERROR");
                // TODO: 乱数持ち回りチェック処理
                //さっき送り出したcodeと合っていれば任意の処理を入れます
                if(param1.equals(code)){
*/

                // ブラウザからセッションID情報を取得
                String sessionIdCookieName = uri.getQueryParameter("sessionIdCookieName");
                LogUtil.logDebug("callbackParam[sessionIdCookieName]:" + sessionIdCookieName);
                String sessionIdCookieValue = uri.getQueryParameter("sessionIdCookieValue");
                LogUtil.logDebug("callbackParam[sessionIdCookieValue]:" + sessionIdCookieValue);
                Toast.makeText(this, "END OAuth...", Toast.LENGTH_LONG).show();

                // SharedPreferencesにセッションID情報を設定
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences).edit();
                dbmsSharedPreferences.putDbmsOnlineSessionIdCookieName(sessionIdCookieName);
                dbmsSharedPreferences.putDbmsOnlineSessionIdCookieValue(sessionIdCookieValue);
                dbmsSharedPreferences.apply();

            }
        }

        // 認証処理終了
        this.nowOAuthingFlg = false;

        LogUtil.logExiting();
    }

    //AndroidManifestのactivityのlaunchModeをsingleTaskにした場合は、onNewIntentを入れてgetIntent()できるようにします
    @Override
    public void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onPause() {
        LogUtil.logEntering();

        // 登録の解除
        EventBus.getDefault().unregister(this);
        super.onPause();

        LogUtil.logExiting();
    }

    //----------------------------
    // EventBusイベントハンドラ
    //----------------------------

    @Subscribe
    public void onEvent(final DisplayLongToastEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★DisplayLongToastEvent★★★");

        // 「java.lang.RuntimeException: Can't create handler inside thread that has not called Looper.prepare()」対策
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MusicListActivity.this, event.getText(), Toast.LENGTH_LONG).show();
            }
        });

        LogUtil.logExiting();
    }

    @Subscribe
    public void onEvent(DoOauthEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★DoOauthEvent★★★");

        // http://d.hatena.ne.jp/Kazzz/20101125/p1
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // ダイアログで確認してから実施
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MusicListActivity.this);
                alertDialogBuilder.setTitle("OAuth");
                alertDialogBuilder.setMessage("ブラウザによるOAuth認証を行います。よろしいですか？");
                // OKボタン
                alertDialogBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // ブラウザ起動（必ず新規タブを開きたいので「#日付」のフラグメントをURLに結合）
                                // TODO: ランダム文字をワンタイムパスワードとして持ちまわったほうがいいかも・・・
                                // http://qiita.com/f-aoyama/items/07ecbefa54eefd62406d
                                String ymd = new SimpleDateFormat(AppConst.CONST_YMDHMS_FORMAT).format(new Date());
                                Uri uri = Uri.parse(
                                        BuildConfig.DBMS_ONLINE_PATH
                                      + AppConst.DBMS_ONLINE_PAGE_PATH_LOGIN
                                      + AppConst.CONST_HALF_SHARP
                                      + ymd);
                                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                startActivity(intent);
                            }
                        });
                // Cancelボタン
                alertDialogBuilder.setCancelable(true);
                alertDialogBuilder.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // 認証処理終了
                                MusicListActivity.nowOAuthingFlg = false;
                                Toast.makeText(MusicListActivity.this, "Cancel OAuth... WARNING! dont sync local and cloud!", Toast.LENGTH_LONG).show();
                            }
                        });
                AlertDialog alertDialog = alertDialogBuilder.create();
                // アラートダイアログを表示
                alertDialog.show();

            }
        });



        LogUtil.logExiting();
    }

    @Subscribe
    public void onEvent(ProgresDialogDismissEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★ProgresDialogDismissEvent★★★");

        progressDialog.dismiss();

        LogUtil.logExiting();
    }

    @Subscribe
    public void onEvent(ProgresDialogShowEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★ProgresDialogShowEvent★★★");

        progressDialog.show();

        LogUtil.logExiting();
    }

    @Subscribe
    public void onEvent(SearchApplyEvent event) {
        LogUtil.logEntering();
        LogUtil.logDebug("★★★SearchApplyEvent★★★");

        searchApplyToListView();

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


    //----------------------------
    // ViewPager.OnPageChangeListener implementes methods
    //----------------------------

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        LogUtil.logDebug("onPageScrolled() position=" + position);
    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.logEntering();
        LogUtil.logDebug("onPageSelected() position=" + position);

        if (position == AppConst.CONST_VIEW_PAGER_INDEX_0_MUSIC_LIST) {
            // 曲一覧画面

            // ツールバーのタイトル
            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_LIST);

            // スライドメニューのトグルボタン表示オン
            ActionBarDrawerToggle toggle = this.getToggle();
            toggle.setDrawerIndicatorEnabled(true);
            this.getDrawer().setDrawerListener(
                    new ActionBarDrawerToggle(
                            this,
                            this.getDrawer(),
                            toolbar,
                            R.string.navigation_drawer_open,
                            R.string.navigation_drawer_close)
            );
            this.setSupportActionBar(this.getToolbar());

            // スライドメニューアンロック
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        } else if (position == AppConst.CONST_VIEW_PAGER_INDEX_1_MUSIC_HISTORY) {
            // リザルト更新履歴画面

            // リザルト履歴画面の内容をリフレッシュ
            MusicHistoryListFragment musicHistoryListFragment
                    = (MusicHistoryListFragment) ((ViewPagerAdapter)viewPager.getAdapter()).getmFragmentAtPos1();
            musicHistoryListFragment.adapter.searchApplyToListView(this);

            // ツールバーのタイトル
            Toolbar toolbar = (Toolbar) this.findViewById(R.id.toolbar);
            toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_HISTORY_LIST);

            // スライドメニューのトグルボタン表示オフ
            ActionBarDrawerToggle toggle = this.getToggle();
            toggle.setDrawerIndicatorEnabled(false);
            this.getDrawer().setDrawerListener(
                    new ActionBarDrawerToggle(
                            this,
                            this.getDrawer(),
                            R.string.navigation_drawer_open,
                            R.string.navigation_drawer_close)
            );
            this.setSupportActionBar(this.getToolbar());

            // スライドメニューロック
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            // ツールバーのメニュー内容変更はFragmentのonCreateOptionsMenu参照
            // ・MusicListFragment#onPrepareOptionsMenu()メソッド
            // ・MusicEditFragment#onPrepareOptionsMenu()メソッド
            // ・MusicHistoryListFragment#onPrepareOptionsMenu()メソッド
        }

        LogUtil.logExiting();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        LogUtil.logDebug("onPageScrolled() state=" + state);
    }

    @Override
    public boolean onSupportNavigateUp () {
        LogUtil.logEntering();

        // 各種設定画面フラグメントを取得し
        android.app.FragmentManager fm = getFragmentManager();
        DbmsSettingFlagment dbmsSettingFlagment = (DbmsSettingFlagment) fm.findFragmentByTag(DbmsSettingFlagment.TAG);

        BaseFragment baseFragment = ((ViewPagerAdapter)viewPager.getAdapter()).getmFragmentAtPos0();
        if ( baseFragment instanceof MusicEditFragment ) {
            MusicEditFragment musicEditFragment = (MusicEditFragment) baseFragment;
            replaceChild(musicEditFragment, 0);

            // 呼ぶと、編集画面の左上のボタンで戻った後に編集画面に行くと左上のボタンが矢印の絵にならない
//            initToolbar();

            // Navigation Drowerのスワイプロックを解除
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        } else if (dbmsSettingFlagment != null) {
            fm.popBackStack();
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        } else {
            // Navigation Drowerをオープン（オープンだけだとスライドできなくなるのでアンロックも）
            // TODO:なぜ編集画面を経由すると、自分でオープン処理を書かないといけなくなったのか・・・？
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
            getDrawer().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        }

        LogUtil.logExiting();
        return false;
    }

}
