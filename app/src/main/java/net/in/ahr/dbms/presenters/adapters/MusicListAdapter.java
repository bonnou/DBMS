package net.in.ahr.dbms.presenters.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.util.AssetsImgUtil;
import net.in.ahr.dbms.others.AppConst;

import java.text.MessageFormat;
import java.util.List;

import greendao.MusicMst;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater = null;

    static final AlphaAnimation clearLampAnimation = new AlphaAnimation(1, 0.01f);

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

        // TODO: サーチ部分の色変更をやめる（クリアランプ、難易度の幅が考慮できていなかった）

        // レコードを取得
        MusicMst music = musicList.get(position);

        // リザルト存在フラグを設定
        boolean resultExistFlg = true;
        if ( music.getMusicResultDBHR() == null ) {
            resultExistFlg = false;
        }

        // クリアランプ
        TextView clearLampView = (TextView)convertView.findViewById(R.id.musicResult_clearLump);

        // TODO: DBHR以外にプレイスタイルが増えた場合は文字を変えたい
        clearLampView.setText(Html.fromHtml("H<br />R"));

        if (!resultExistFlg) {
            // TODO: resのcolorsのほうがいい？
            clearLampView.setBackgroundColor(Color.parseColor("#333333"));
            clearLampView.setTextColor(Color.parseColor("#DDDDDD"));
        } else {
            String clearLamp = music.getMusicResultDBHR().getClearLamp();

            if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED.equals(clearLamp) ) {
                // ランプ点滅
                clearLampAnimation.setDuration(100);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                clearLampView.startAnimation(clearLampAnimation);
                clearLampView.setBackgroundColor(Color.parseColor("#666666"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#ba55d3"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#00ffff"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#00ff7f"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#dc143c"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#EFEFEF"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#ffa500"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#ffffff"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
                // ランプ点滅
                clearLampAnimation.setDuration(250);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                clearLampView.startAnimation(clearLampAnimation);
            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT.equals(clearLamp) ) {
                clearLampView.setBackgroundColor(Color.parseColor("#ffff00"));
                clearLampView.setTextColor(Color.parseColor("#333333"));
                // ランプ点滅
                clearLampAnimation.setDuration(250);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                clearLampView.startAnimation(clearLampAnimation);
            } else {
                clearLampView.setBackgroundColor(Color.parseColor("#333333"));
                clearLampView.setTextColor(Color.parseColor("#DDDDDD"));
            }
        }

        // 難易度
        TextView difficultView = (TextView)convertView.findViewById(R.id.music_difficult);
        difficultView.setText(music.getDifficult());
        String nha = music.getNha();
        if ( "NORMAL".equals(nha) ) {
            difficultView.setBackgroundColor(Color.parseColor("#333366"));
        } else if ( "HYPER".equals(nha) ) {
            difficultView.setBackgroundColor(Color.parseColor("#666633"));
        } else if ( "ANOTHER".equals(nha) ) {
            difficultView.setBackgroundColor(Color.parseColor("#663333"));
        }

        // 曲名
        TextView nameView = (TextView)convertView.findViewById(R.id.music_name);
        nameView.setText(music.getName());

        // スコアランク画像取得
        Bitmap scoreRankImg = null;
        if (resultExistFlg) {
            AssetsImgUtil assetsImgUtil = new AssetsImgUtil();
            scoreRankImg = assetsImgUtil.getAssetsImg(
                    context,
                    "img/scoreRank/" + music.getMusicResultDBHR().getScoreRank() + ".gif");
        }

        // スコアグラフ
        double scoreRate;
        if(resultExistFlg) {
            scoreRate = music.getMusicResultDBHR().getScoreRate();
        } else {
            scoreRate = 0;
        }
        int scoreGraphWeight = (int) (100 * scoreRate);
        int scoreGraphNegativeWeight = 10000 - scoreGraphWeight;

        LinearLayout.LayoutParams scoreGraphWeightParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        scoreGraphWeightParam.weight = scoreGraphWeight;
        ((TextView)convertView.findViewById(R.id.musicResult_scoreGraph)).setLayoutParams(scoreGraphWeightParam);

        LinearLayout.LayoutParams scoreGraphWeightNegativeParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        scoreGraphWeightNegativeParam.weight = scoreGraphNegativeWeight;
        ((TextView)convertView.findViewById(R.id.musicResult_scoreGraphNegative)).setLayoutParams(scoreGraphWeightNegativeParam);

        // スコアランク
        ImageView scoreRankView = (ImageView)convertView.findViewById(R.id.musicResult_scoreRank);
        scoreRankView.setImageBitmap(scoreRankImg);

        // スタイル情報
        String inpactBeginTag = "<b><big>";
        String inpactEndTag = "</big></b>";

        // スコア情報
        TextView scoreInfoView = (TextView)convertView.findViewById(R.id.musicResult_scoreInfo);
        final String SCORE_INFO_FORMAT = "score: " + inpactBeginTag + "{0}" + inpactEndTag + " ({1}%)";
        String[] scoreInfoReplaceArr;
        if (resultExistFlg) {
            scoreInfoReplaceArr = new String[] {
                    String.valueOf(music.getMusicResultDBHR().getExScore()),
                    String.format("%.2f", music.getMusicResultDBHR().getScoreRate())
            };
        } else {
            scoreInfoReplaceArr = new String[] {
                    "ー",
                    "ー"
            };
        }
        String scoreInfo = MessageFormat.format(SCORE_INFO_FORMAT, (Object[])scoreInfoReplaceArr);
        scoreInfoView.setText(Html.fromHtml(scoreInfo));

        // ミス情報
        TextView missInfoView = (TextView)convertView.findViewById(R.id.musicResult_missInfo);
        final String MISS_INFO_FORMAT = "bp: " + inpactBeginTag + "{0}" + inpactEndTag + " ({1}%)";
        String[] missInfoReplaceArr;
        if (resultExistFlg) {
            missInfoReplaceArr = new String[] {
                    String.valueOf(music.getMusicResultDBHR().getBp()),
                    String.format("%.2f", music.getMusicResultDBHR().getMissRate())
            };
        } else {
            missInfoReplaceArr = new String[] {
                    "ー",
                    "ー"
            };
        }
        String missInfo = MessageFormat.format(MISS_INFO_FORMAT, (Object[])missInfoReplaceArr);
        missInfoView.setText(Html.fromHtml(missInfo));

        // 進捗メモ
        TextView memoProgresView = (TextView)convertView.findViewById(R.id.musicResult_memoProgress);
        final String MEMO_PROGRESS_FORMAT = "進捗: {0}";
        String[] memoProgressReplaceArr;
        if (resultExistFlg) {
            memoProgressReplaceArr = new String[] {
                    String.valueOf(music.getMusicResultDBHR().getMemoProgress())
            };
        } else {
            memoProgressReplaceArr = new String[] {
                    "ー"
            };
        }
        String memoProgress = MessageFormat.format(MEMO_PROGRESS_FORMAT, (Object[])memoProgressReplaceArr);
        memoProgresView.setText(memoProgress);

        return convertView;
    }
}
