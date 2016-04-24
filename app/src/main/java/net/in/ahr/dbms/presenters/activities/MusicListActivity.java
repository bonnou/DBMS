package net.in.ahr.dbms.presenters.activities;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
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
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.R;
import net.in.ahr.dbms.presenters.fragments.DbmsSettingFlagment;

import net.in.ahr.dbms.presenters.fragments.WebViewFragment;
import net.in.ahr.dbms.presenters.others.SearchNaviManager;
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
import net.in.ahr.dbms.presenters.tabManagers.BaseFragment;
import net.in.ahr.dbms.presenters.tabManagers.CustomViewPager;
import net.in.ahr.dbms.presenters.tabManagers.ViewPagerAdapter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHRDao;

/**
 * 曲一覧画面Activity。本アプリのルートActivity。
 */
public class MusicListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                   ViewPager.OnPageChangeListener {

    public static final int TAB_PAGE_SIZE = 2;
    public static final int TAB_PAGE_0 = 0;
    public static final int TAB_PAGE_1 = 1;

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

        super.onCreate(savedInstanceState);

        // レイアウトXML指定
        setContentView(R.layout.activity_music_list);

        // ツールバー設定
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.menu_music);

/*
        FragmentTabHost tabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        tabHost.setup(this, getSupportFragmentManager(), R.id.musicFragment);

        TabHost.TabSpec tab1Spec = tabHost.newTabSpec("TAB1");
        Button tab1Button = new Button(this);
        //タブに表示するビュー
        tab1Button.setText("Tab1");
        tab1Spec.setIndicator(tab1Button);
        tabHost.addTab(tab1Spec, MusicListFragment.class, null);

        TabHost.TabSpec tab2Spec = tabHost.newTabSpec("TAB2");
        Button tab2Button = new Button(this);
        tab2Button.setText("Tab2");
        tab2Spec.setIndicator(tab2Button);
        tabHost.addTab(tab2Spec, WebViewFragment.class, null);
*/
/*
        // タブ設定
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (ViewPager) findViewById(R.id.pager);

        FragmentPagerAdapter adapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                LogUtil.logEntering();

                Fragment fragment = null;
                if (position == TAB_PAGE_0) {
                    if (editflg) {
                        LogUtil.logDebug("def");
                        fragment = new WebViewFragment();
                    } else {
                        LogUtil.logDebug("ghi");
                        // 曲一覧画面を表示
                        FragmentManager manager = getSupportFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        MusicListFragment musicListFragment = new MusicListFragment();
                        fragment = musicListFragment;
//                    transaction.add(R.id.musicFragment, musicListFragment, MusicListFragment.TAG);
//            transaction.addToBackStack(MusicListFragment.TAG);
//                    transaction.commit();
                    }
                } else if (position == TAB_PAGE_1) {
                    fragment = new WebViewFragment();

                }

                LogUtil.logExiting();
                return fragment;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "tab " + (position + 1);
            }

            @Override
            public int getCount() {
                return TAB_PAGE_SIZE;
            }
        };
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);

        //オートマチック方式: これだけで両方syncする
        tabLayout.setupWithViewPager(viewPager);
*/

        // タブ設定
        // ※スワイプ遷移不可のCustomViewPagerを使用
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager = (CustomViewPager)findViewById(R.id.pager);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

/*
        tabLayout.addTab(tabLayout.newTab().setText("DBHR RESULT"));
        tabLayout.addTab(tabLayout.newTab().setText("TEXTAGE"));
*/
/*
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    >


    <WebView
        android:id="@+id/webView"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        >
    </WebView>

</FrameLayout>

public class WebViewFragment extends Fragment {
    WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_web_view, container, false);

        webView= (WebView)v.findViewById(R.id.webView);

        webView.setWebViewClient(new WebViewClient());

        webView.loadUrl("http://textage.cc/");

        return v;
    }


*/
        // SharedPreferencesラッパー取得
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences).edit();

        // 自動CSVエクスポートサービス起動
        scheduleService();

        // 検索・ソートNavigationDrawer設定
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 検索・ソートNavigationDrawer管理オブジェクト生成・保持
        SearchNaviManager searchNaviManager = new SearchNaviManager(navigationView, dbmsSharedPreferences);
        setSearchNaviManager(searchNaviManager);

        // アプリ起動時の検索・ソートNavigationDrawerの条件設定処理
        searchNaviManager.settingAtOnCreate();

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
            transaction.add(R.id.dbmsSettingFragment, dbmsSettingFlagment).addToBackStack(null);
            transaction.commit();

            // タブを一時的に非表示にする
            tabLayout.setVisibility(View.GONE);


        }else if (id == R.id.action_import_gss) {
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

                // タブを表示する
                // ※設定画面表示時にGONE（詰めて消す）に設定しているため
                tabLayout.setVisibility(View.VISIBLE);

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


    //----------------------------
    // ViewPager.OnPageChangeListener implementes methods
    //----------------------------

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        LogUtil.logDebug("onPageSelected() position=" + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

}
