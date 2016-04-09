package net.in.ahr.dbms.presenters.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.network.google.spreadSheet.GSSAsyncTask;
import net.in.ahr.dbms.data.network.google.spreadSheet.GSSImport;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;
import net.in.ahr.dbms.presenters.fragments.MusicEditFragment;
import net.in.ahr.dbms.presenters.fragments.MusicListFragment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MusicListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ListView musicListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_music_list);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            // 以下エラー回避
            // Caused by: java.lang.NullPointerException: Attempt to invoke interface method 'android.view.View android.view.MenuItem.getActionView()' on a null object reference
            toolbar.inflateMenu(R.menu.menu_music);








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

            // 編集画面へ遷移
            FragmentManager manager = getFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();

            // 曲一覧画面をセット
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
        } else if (id == R.id.action_debug_crash) {
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90000,
                    AppConst.ERR_STEP_CD_MEAC_00001,
                    AppConst.ERR_MESSAGE_MEAC_00001);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camara) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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


}
