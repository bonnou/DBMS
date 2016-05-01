package net.in.ahr.dbms.presenters.fragments.common;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.fragments.DbmsSettingFlagment;
import net.in.ahr.dbms.presenters.fragments.MusicEditFragment;

/**
 * Created by str2653z on 2016/04/29.
 */
public class ChildFragmentCommon {

    public void onCreateCommon(MusicListActivity rootActivity, Fragment fragment) {
        LogUtil.logEntering();

        // オプションメニュー有効化
        fragment.setHasOptionsMenu(true);

        // トグルボタンを戻るボタンに変更
        // ※ツールバーを渡さないコンストラクタでトグルを再設定する必要あり
        ActionBarDrawerToggle toggle = rootActivity.getToggle();
        toggle.setDrawerIndicatorEnabled(false);
        rootActivity.getDrawer().setDrawerListener(
                new ActionBarDrawerToggle(
                        rootActivity,
                        rootActivity.getDrawer(),
                        R.string.navigation_drawer_open,
                        R.string.navigation_drawer_close)
        );
        rootActivity.setSupportActionBar(rootActivity.getToolbar());

        // タブの非表示設定
        TabLayout tabLayout = (TabLayout) rootActivity.findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);

        LogUtil.logExiting();
    }

    public void onCreateViewCommon() {
        LogUtil.logEntering();

        // 現状未使用

        LogUtil.logExiting();
    }

    public void onClickCommon() {
        LogUtil.logEntering();

        // 現状未使用

        LogUtil.logExiting();
    }

    public void onDestroyViewCommon(MusicListActivity rootActivity, Fragment fragment) {
        LogUtil.logEntering();

        // トグルボタンを戻るボタンに変更 の戻し
        rootActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // タブの表示設定変更戻し
        TabLayout tabLayout = (TabLayout) rootActivity.findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);

        // ツールバーの表示設定変更戻し
        Toolbar toolbar = (Toolbar) rootActivity.findViewById(R.id.toolbar);
        toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_LIST);
        Menu menu = toolbar.getMenu();
        MenuItem settingItem = menu.findItem(R.id.action_settings);
        settingItem.setVisible(true);
        MenuItem importGssItem = menu.findItem(R.id.action_import_gss);
        importGssItem.setVisible(true);
        MenuItem exportCsvItem = menu.findItem(R.id.action_export_csv);
        exportCsvItem.setVisible(true);
        MenuItem importCsvItem = menu.findItem(R.id.action_import_csv);
        importCsvItem.setVisible(true);
        MenuItem crashItem = menu.findItem(R.id.action_debug_crash);
        crashItem.setVisible(true);
        MenuItem syncItem = menu.findItem(R.id.action_sync_cloud);
        syncItem.setVisible(true);
        MenuItem refineSearchItem = menu.findItem(R.id.action_refine_search);
        refineSearchItem.setVisible(true);

        // SearchView非表示設定の戻し
        MenuItem searchItem = menu.findItem(R.id.action_refine_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setVisibility(View.VISIBLE);
        searchView.setEnabled(true);

        ActionBarDrawerToggle newToggle = new ActionBarDrawerToggle(
                rootActivity,
                rootActivity.getDrawer(),
                rootActivity.getToolbar(),
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        rootActivity.getDrawer().setDrawerListener(newToggle);
        newToggle.syncState();
        rootActivity.setSupportActionBar(rootActivity.getToolbar());
        newToggle.setDrawerIndicatorEnabled(true);
        rootActivity.setToggle(newToggle);

        LogUtil.logExiting();
    }

    public void onCreateOptionsMenuCommon(MusicListActivity rootActivity) {
        LogUtil.logEntering();

        // トグルボタンを左矢印ボタンに変更
        rootActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LogUtil.logExiting();
    }

    public void onPrepareOptionsMenuCommon(MusicListActivity rootActivity, Menu menu, String TAG) {
        LogUtil.logEntering();

        // ツールバーのタイトル
        Toolbar toolbar = (Toolbar) rootActivity.findViewById(R.id.toolbar);
        if ( MusicEditFragment.TAG.equals(TAG) ) {
            toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_EDIT);
        } else if ( DbmsSettingFlagment.TAG.equals(TAG) ) {
            toolbar.setTitle(AppConst.TOOLBAR_TITLE_SETTINGS);
        }

        // ツールバーの表示内容変更
        MenuItem settingItem = menu.findItem(R.id.action_settings);
        settingItem.setVisible(false);
        MenuItem importGssItem = menu.findItem(R.id.action_import_gss);
        importGssItem.setVisible(false);
        MenuItem exportCsvItem = menu.findItem(R.id.action_export_csv);
        exportCsvItem.setVisible(false);
        MenuItem importCsvItem = menu.findItem(R.id.action_import_csv);
        importCsvItem.setVisible(false);
        MenuItem crashItem = menu.findItem(R.id.action_debug_crash);
        crashItem.setVisible(false);
        MenuItem syncItem = menu.findItem(R.id.action_sync_cloud);
        syncItem.setVisible(false);
        MenuItem refineSearchItem = menu.findItem(R.id.action_refine_search);
        refineSearchItem.setVisible(false);

        LogUtil.logExiting();
    }

}
