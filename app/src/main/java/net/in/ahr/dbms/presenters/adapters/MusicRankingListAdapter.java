package net.in.ahr.dbms.presenters.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.network.api.dto.MusicResultDBHRDto;

import java.util.List;

/**
 * Created by str2653z on 16/05/07.
 */
public class MusicRankingListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;
    public List<MusicResultDBHRDto> musicResultDtoList;
    public List<MusicResultDBHRDto> getMusicResultDtoList() {
        return this.musicResultDtoList;
    }
    public void setMusicResultDtoList(List<MusicResultDBHRDto> musicResultDtoList) {
        this.musicResultDtoList = musicResultDtoList;
    }

    // 縦幅設定済フラグ
    boolean heightSetFinFlg = false;

    TextView rankingTextView;
    TextView userNameTextView;
    TextView scoreRankTextView;
    TextView exScoreTextView;
    TextView bpTextView;

    public MusicRankingListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return musicResultDtoList.size();
    }

    @Override
    public Object getItem(int position) {
        return musicResultDtoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return musicResultDtoList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // ListViewの1行を取得（nullの場合は生成）
        View view = convertView;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_music_ranking, parent, false);
        }

        // レコードを取得
        MusicResultDBHRDto musicResultDto = musicResultDtoList.get(position);

        // 順位
        rankingTextView = (TextView) view.findViewById(R.id.musicRanking_ranking);
        rankingTextView.setText(
                String.valueOf(position + 1));

        // ユーザ名
        userNameTextView = (TextView) view.findViewById(R.id.musicRanking_userName);
        userNameTextView.setText(
                musicResultDto.getUserName());

        // スコアランク
        scoreRankTextView = (TextView) view.findViewById(R.id.musicRanking_scoreRank);
        scoreRankTextView.setText(
                musicResultDto.getScoreRank());

        // EXスコア
        exScoreTextView = (TextView) view.findViewById(R.id.musicRanking_exScore);
        exScoreTextView.setText(
                String.valueOf(musicResultDto.getExScore()));

        // BP
        bpTextView = (TextView) view.findViewById(R.id.musicRanking_bp);
        bpTextView.setText(
                String.valueOf(musicResultDto.getBp()));

        return view;
    }
}
