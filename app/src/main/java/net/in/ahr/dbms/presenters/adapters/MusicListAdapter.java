package net.in.ahr.dbms.presenters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.in.ahr.dbms.R;

import java.util.List;

import greendao.MusicMst;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;

    // TODO: マスタではなくスコアを含める必要あり
    List<MusicMst> musicList;

    public MusicListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setMusicList(List<MusicMst> musicList) {
        this.musicList = musicList;
    }

    @Override
    public int getCount() {
        return musicList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return musicList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.list_music_list, parent, false);

        ((TextView)convertView.findViewById(R.id.memo_id)).setText("i");
        ((TextView)convertView.findViewById(R.id.memo_text)).setText(musicList.get(position).getName());
        ((TextView)convertView.findViewById(R.id.memo_date)).setText("dateTest");

        return convertView;
    }
}
