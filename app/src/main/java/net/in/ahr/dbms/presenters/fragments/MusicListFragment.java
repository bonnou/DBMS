package net.in.ahr.dbms.presenters.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;

import java.util.List;
import java.util.HashMap;

import greendao.MusicMst;
import greendao.MusicMstDao;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicListFragment extends Fragment {

    public static final String TAG = "MusicListFragment";

    ListView musicListView;
    MusicListAdapter adapter;
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

        // リストビューにGreenDaoから取得した値を設定
        List<MusicMst> list = getMusicMstDao(getActivity()).loadAll();
        adapter = new MusicListAdapter(getActivity());
        adapter.setMusicList(list);
        musicListView = (ListView) view.findViewById(R.id.musicListView);
        musicListView.setAdapter(adapter);

        // リストビューのクリックイベント
        musicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // クリックされたアイテムを取得
                ListView listView = (ListView) parent;
                MusicMst music = (MusicMst) listView.getItemAtPosition(position);
//                Toast.makeText(getActivity(), music.getName(), Toast.LENGTH_SHORT).show();

                // フラグメントマネージャ取得
                FragmentManager manager = getFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();

                // 曲一覧画面を非表示
                Fragment musicListFragment = manager.findFragmentByTag(MusicListFragment.TAG);
                transaction.hide(musicListFragment);

                // 曲編集画面へ遷移
                MusicEditFragment musicEditFragment = new MusicEditFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("musicPosition", position);
                bundle.putSerializable("musicForEdit", music);
                musicEditFragment.setArguments(bundle);
                transaction.add(R.id.musicFragment, musicEditFragment).addToBackStack(null);
                transaction.commit();

            }
        });


        return view;
    }

    public void updateListView(int position) {
        LogUtil.logEntering();

        LogUtil.logDebug("position: " + position);

        // TODO:http://qiita.com/gari_jp/items/9b70cfaaa478ad368c92
        adapter.notifyDataSetChanged();

        LogUtil.logExiting();
    }

}
