package net.in.ahr.dbms.presenters.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;
import net.in.ahr.dbms.presenters.tabManagers.BaseFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

import greendao.MusicMst;
import greendao.MusicMstDao;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicListFragment extends BaseFragment {

    public static final String TAG = "MusicListFragment";

    ListView musicListView;
    public MusicListAdapter adapter;
    TextView textView;

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_list, container, false);

        // タイトルを設定
        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_LIST);

        // リストビューにGreenDaoから取得した値を設定
        adapter = new MusicListAdapter( getActivity() );
        adapter.searchApplyToListView( (MusicListActivity) getActivity() );
        musicListView = (ListView) view.findViewById(R.id.musicListView);
        musicListView.setAdapter(adapter);

        // 絞り込み検索を可能にする
        musicListView.setTextFilterEnabled(true);

        // 曲編集画面に遷移した時に一番上に表示されていたビューまで移動
        musicListView.setSelection(MusicListActivity.dispTopViewPosition);

        // リストビューのクリックイベント
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // クリックされたアイテムを取得
                ListView listView = (ListView) parent;
                MusicMst music = (MusicMst) listView.getItemAtPosition(position);
//                Toast.makeText(getActivity(), music.getName(), Toast.LENGTH_SHORT).show();

                // 現在表示されているListViewの一番上のpositionを保持
                int nowDispTopViewPosition = listView.getFirstVisiblePosition();

                // フラグメントマネージャ取得
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                // 曲一覧画面を非表示
                Fragment musicListFragment = manager.findFragmentByTag(MusicListFragment.TAG);
                transaction.hide(musicListFragment);

                // Navigation Drowerを非表示ロック
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

                // 曲編集画面へ遷移
                MusicEditFragment musicEditFragment = new MusicEditFragment();
/*
                Bundle bundle = new Bundle();
                bundle.putSerializable("musicPosition", position);
                bundle.putSerializable("musicForEdit", music);
                musicEditFragment.setArguments(bundle);
*/

/*
                // TODO: addだと時々Fragmentが重なったからreplaceに変更して様子見
                transaction.replace(R.id.musicFragment, musicEditFragment).addToBackStack(null);
                transaction.commit();
*/
                MusicListActivity musicListActivity = (MusicListActivity)getActivity();
                musicListActivity.position = position;
                musicListActivity.musicForEdit = music;
                musicListActivity.dispTopViewPosition = nowDispTopViewPosition;
                if(mListener != null) {
                    mListener.onSwitchToNextFragment();
                }


            }
        });

        // 絞り込み検索用に、曲一覧のListViewを取得し保持
        MusicListActivity.setMusicListView(musicListView);

        return view;
    }

    public void updateListView(int position) {
        LogUtil.logEntering();

        LogUtil.logDebug("position: " + position);

        // TODO:http://qiita.com/gari_jp/items/9b70cfaaa478ad368c92
        adapter.notifyDataSetChanged();

        LogUtil.logExiting();
    }




    public ListView getMusicListView() {
        return musicListView;
    }



/*
    @Override
    public void onDestroyView() {
        Toolbar toolbar = (Toolbar)getView().findViewById(R.id.toolbar);
        toolbar.setOnMenuItemClickListener(null);
        super.onDestroyView();
    }
*/

}
