package net.in.ahr.dbms.presenters.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.MultiSelectListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.fragments.common.ChildFragmentCommon;

import java.util.Set;

/**
 * Created by str2653z on 2016/04/23.
 */
public class DbmsSettingFlagment extends PreferenceFragment {

    public static final String TAG = "DbmsSettingFlagment";

    public DbmsSettingFlagment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.dbms_setting_fragment);

        //----------------------------
        // ChildFragmentCommon#onCreateCommonメソッドのFragment非support用処理：開始
        //----------------------------

        // ルートActivity取得
        MusicListActivity rootActivity = (MusicListActivity) getActivity();

        // オプションメニュー有効化
        setHasOptionsMenu(true);

        // トグルボタンを戻るボタンに変更
        ActionBarDrawerToggle toggle = rootActivity.getToggle();
        toggle.setDrawerIndicatorEnabled(false);
        rootActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // ※ツールバーを渡さないコンストラクタでトグルを再設定する必要あり
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

        //----------------------------
        // ChildFragmentCommon#onCreateCommonメソッドのFragment非support用処理：終了
        //----------------------------

        LogUtil.logExiting();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        LogUtil.logEntering();

        // SharedPreferencesラッパークラスのオブジェクトを取得
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences);

        // 選択可能クリアランプの初期値設定
        Set<String> clearLumpValSet = dbmsSharedPreferences.getSettingSelectableClearLamp();
        MultiSelectListPreference multiSelectListPreference = (MultiSelectListPreference) findPreference(AppConst.SHARED_KEY_SETTING_SELECTABLE_CLEAR_LAMP);
        multiSelectListPreference.setValues(clearLumpValSet);

        LogUtil.logExiting();
    }

    @Override
    public void onDestroyView() {
        LogUtil.logEntering();

        //----------------------------
        // ChildFragmentCommon#onCreateCommonメソッドのFragment非support用処理：開始
        //----------------------------

        // ルートActivity取得
        MusicListActivity rootActivity = (MusicListActivity) getActivity();

        // トグルボタンを戻るボタンに変更 の戻し
        rootActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        // タブの表示設定変更戻し
        TabLayout tabLayout = (TabLayout) rootActivity.findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);

        // ツールバーの表示設定変更戻し
        Toolbar toolbar = (Toolbar) rootActivity.findViewById(R.id.toolbar);
        toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_LIST);
        Menu menu = toolbar.getMenu();
        MenuItem importGssItem = menu.findItem(R.id.action_import_gss);
        importGssItem.setVisible(true);
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

        super.onDestroyView();

        LogUtil.logExiting();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        LogUtil.logEntering();

        // タブ表示なしFragmentのonCreateOptionsMenu共通処理
        ChildFragmentCommon childFragmentCommon = new ChildFragmentCommon();
        childFragmentCommon.onCreateOptionsMenuCommon((MusicListActivity) getActivity());

        super.onCreateOptionsMenu(menu, inflater);

        LogUtil.logExiting();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        LogUtil.logEntering();

        // タブ表示なしFragmentのonPrepareOptionsMenu共通処理
        ChildFragmentCommon childFragmentCommon = new ChildFragmentCommon();
        childFragmentCommon.onPrepareOptionsMenuCommon((MusicListActivity) getActivity(), menu, TAG);

        LogUtil.logExiting();
    }

}
