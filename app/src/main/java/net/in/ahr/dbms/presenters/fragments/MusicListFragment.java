package net.in.ahr.dbms.presenters.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;

import java.util.List;

import greendao.Memo;
import greendao.MemoDao;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicListFragment extends Fragment {

    ListView musicListView;
    TextView textView;

    private static MemoDao getMemoDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMemoDao();
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
        List<Memo> list = getMemoDao(getActivity()).loadAll();
        MusicListAdapter adapter = new MusicListAdapter(getActivity());
        adapter.setMusicList(list);

        musicListView = (ListView) view.findViewById(R.id.musicListView);
        musicListView.setAdapter(adapter);

        // TextViewへの値設定
//        textView = (TextView) view.findViewById(R.id.musicListFragmentTextView);
//        textView.setText("MusicListFragment test");

        return view;
    }
}
