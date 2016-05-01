package net.in.ahr.dbms.presenters.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.business.usecases.result.MusicResultUtil;
import net.in.ahr.dbms.data.strage.util.AssetsImgUtil;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import greendao.MusicMst;
import greendao.MusicMstDao;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicListAdapter extends BaseAdapter implements Filterable {

    Context context;
    LayoutInflater layoutInflater = null;
    private final Object mLock = new Object();

    static final AlphaAnimation clearLampAnimation = new AlphaAnimation(0.7f, 1f);

    protected static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    // TODO: マスタではなくスコアを含める必要あり
    public List<MusicMst> musicList;

    public List<MusicMst> getMusicList() {
        return this.musicList;
    }

    public void setMusicList(List<MusicMst> musicList) {
        this.musicList = musicList;
    }

    // フィルタ初期化用
    public List<MusicMst> musicListOrg;

    public List<MusicMst> getMusicListOrg() {
        return musicListOrg;
    }

    public void setMusicListOrg(List<MusicMst> musicListOrg) {
        this.musicListOrg = musicListOrg;
    }

    public MusicListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        View view = convertView;
        ViewHolder holder;

        // 受け取ったビューがnullなら新しくビュー・ホルダーを生成（再利用による性能改善）
        if (view == null) {
            view = layoutInflater.inflate(R.layout.list_music_list, parent, false);

            holder = new ViewHolder();
            // クリアランプ
            TextView clearLampView = (TextView) view.findViewById(R.id.musicResult_clearLamp);
            holder.clearLampView = clearLampView;
            // 難易度
            TextView difficultView = (TextView) view.findViewById(R.id.music_difficult);
            holder.difficultView = difficultView;
            // 曲名
            TextView nameView = (TextView) view.findViewById(R.id.music_name);
            holder.nameView = nameView;
            // スコアグラフ（実線）
            TextView scoreGraphPositiveView = (TextView) view.findViewById(R.id.musicResult_scoreGraph);
            holder.scoreGraphPositiveView = scoreGraphPositiveView;
            // スコアグラフ（透明線）
            TextView scoreGraphNegativeView = (TextView) view.findViewById(R.id.musicResult_scoreGraphNegative);
            holder.scoreGraphNegativeView = scoreGraphNegativeView;
            // クリアグラフ（実線）
            TextView clearGraphPositiveView = (TextView) view.findViewById(R.id.musicResult_clearGraph);
            holder.clearGraphPositiveView = clearGraphPositiveView;
            // スコアグラフ（透明線）
            TextView clearGraphNegativeView = (TextView) view.findViewById(R.id.musicResult_clearGraphNegative);
            holder.clearGraphNegativeView = clearGraphNegativeView;
            // スコアランク
            ImageView scoreRankView = (ImageView) view.findViewById(R.id.musicResult_scoreRank);
            holder.scoreRankView = scoreRankView;
            // スコア情報
            TextView scoreInfoView = (TextView) view.findViewById(R.id.musicResult_scoreInfo);
            holder.scoreInfoView = scoreInfoView;
            // ミス情報
            TextView missInfoView = (TextView) view.findViewById(R.id.musicResult_missInfo);
            holder.missInfoView = missInfoView;
            // メモ
            TextView memoOtherView = (TextView) view.findViewById(R.id.musicResult_memoOther);
            holder.memoOtherView = memoOtherView;

            // ホルダーをビューにセット
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        // TODO: サーチ部分の色変更をやめる（クリアランプ、難易度の幅が考慮できていなかった）

        // レコードを取得
        MusicMst music = musicList.get(position);

        // リザルト存在フラグを設定
        boolean resultExistFlg = true;
        if (music.getMusicResultDBHR() == null) {
            resultExistFlg = false;
        }

        // クリアランプ
        // TODO: DBHR以外にプレイスタイルが増えた場合は文字を変えたい
        holder.clearLampView.setText(Html.fromHtml("H<br />R"));

        String clearLamp = AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY;
        if (resultExistFlg && music.getMusicResultDBHR().getClearLamp() != null) {
            clearLamp = music.getMusicResultDBHR().getClearLamp();
        }
        if (!resultExistFlg) {
            // TODO: resのcolorsのほうがいい？
            // 色設定
            holder.clearLampView.setBackgroundColor(Color.parseColor("#333333"));
            holder.clearLampView.setTextColor(Color.parseColor("#DDDDDD"));

            // ランプ点滅停止
            holder.clearLampView.clearAnimation();

        } else {

            if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#330000"));
                holder.clearLampView.setTextColor(Color.parseColor("#ff0000"));

                // ランプ点滅
                clearLampAnimation.setDuration(50);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                holder.clearLampView.startAnimation(clearLampAnimation);

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#666666"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅
                clearLampAnimation.setDuration(50);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                holder.clearLampView.startAnimation(clearLampAnimation);

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#ba55d3"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#00ffff"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#00ff00"));
//                holder.clearLampView.setBackgroundColor(Color.parseColor("#00ff7f"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR.equals(clearLamp)) {
                // 色設定
//                holder.clearLampView.setBackgroundColor(Color.parseColor("#ff0000"));
                holder.clearLampView.setBackgroundColor(Color.parseColor("#dc143c"));
                holder.clearLampView.setTextColor(Color.parseColor("#dddddd"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#EFEFEF"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#ffa500"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅
                clearLampAnimation.setDuration(500);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                holder.clearLampView.startAnimation(clearLampAnimation);

            } else if (AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT.equals(clearLamp)) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#ffff00"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅
                clearLampAnimation.setDuration(500);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                holder.clearLampView.startAnimation(clearLampAnimation);

            } else {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#333333"));
                holder.clearLampView.setTextColor(Color.parseColor("#DDDDDD"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            }
        }

        // 難易度
        holder.difficultView.setText(music.getDifficult());
        String nha = music.getNha();
        if ("NORMAL".equals(nha)) {
            holder.difficultView.setBackgroundColor(Color.parseColor("#333366"));
        } else if ("HYPER".equals(nha)) {
            holder.difficultView.setBackgroundColor(Color.parseColor("#666633"));
        } else if ("ANOTHER".equals(nha)) {
            holder.difficultView.setBackgroundColor(Color.parseColor("#663333"));
        }

        // 曲名
        holder.nameView.setText(music.getName());

        // スコアランク画像取得
        Bitmap scoreRankImg = null;
        boolean scoreExistFlg = false;
        if (
                resultExistFlg
                        && !TextUtils.isEmpty(music.getMusicResultDBHR().getScoreRank())
                        && music.getMusicResultDBHR().getExScore() != null
                        && music.getMusicResultDBHR().getExScore().intValue() != 0) {
            AssetsImgUtil assetsImgUtil = new AssetsImgUtil();
            scoreRankImg = assetsImgUtil.getAssetsImg(
                    context,
                    "img/scoreRank/" + music.getMusicResultDBHR().getScoreRank() + ".gif");
        }
        // スコアランク
        holder.scoreRankView.setImageBitmap(scoreRankImg);

        // スコアグラフ
        double scoreRate;
        if (resultExistFlg) {
            scoreRate = music.getMusicResultDBHR().getScoreRate();
        } else {
            scoreRate = 0;
        }
        int scoreGraphWeight = (int) (100 * scoreRate);
        int scoreGraphNegativeWeight = 10000 - scoreGraphWeight;

        // スコアグラフ（実線）
        LinearLayout.LayoutParams scoreGraphWeightParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        scoreGraphWeightParam.weight = scoreGraphWeight;
        holder.scoreGraphPositiveView.setLayoutParams(scoreGraphWeightParam);

        // スコアグラフ（透明線）
        LinearLayout.LayoutParams scoreGraphWeightNegativeParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        scoreGraphWeightNegativeParam.weight = scoreGraphNegativeWeight;
        holder.scoreGraphNegativeView.setLayoutParams(scoreGraphWeightNegativeParam);

        // クリアグラフ
        double clearProgressRate;
        int remainingGaugeOrDeadNotes;
        if (resultExistFlg && music.getMusicResultDBHR().getRemainingGaugeOrDeadNotes() != null) {
            remainingGaugeOrDeadNotes = music.getMusicResultDBHR().getRemainingGaugeOrDeadNotes().intValue();
        } else {
            remainingGaugeOrDeadNotes = 0;
        }
        if (resultExistFlg) {
            if (remainingGaugeOrDeadNotes == 0) {
                clearProgressRate = 0;
            } else if (
                    AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY.equals(clearLamp)
                            || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED.equals(clearLamp)
                            || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR.equals(clearLamp)
                            || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR.equals(clearLamp)
                            || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR.equals(clearLamp)
                    ) {
                // 次のランプが増加型の場合
                clearProgressRate = (double) remainingGaugeOrDeadNotes / (double) 80 * 100;
            } else if (
                    AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR.equals(clearLamp)
                            || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR.equals(clearLamp)
                    ) {
                // 次のランプが減少型の場合
                MusicResultUtil musicResultUtil = new MusicResultUtil();
                int maxNotes = musicResultUtil.retMaxScore(music);
                clearProgressRate = (double) remainingGaugeOrDeadNotes / (double) maxNotes * 100;
            } else if (
                    AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR.equals(clearLamp)
                            || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO.equals(clearLamp)
                            || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT.equals(clearLamp)
                    ) {
                // 次のランプが特定ノーツ数0の場合
                MusicResultUtil musicResultUtil = new MusicResultUtil();
                int maxNotes = musicResultUtil.retMaxScore(music);
                clearProgressRate = (double) (maxNotes - remainingGaugeOrDeadNotes) / (double) maxNotes * 100;
            } else {
                // NO PLAY
                clearProgressRate = 0;
            }

        } else {
            clearProgressRate = 0;
        }
        int clearGraphWeight = (int) (100 * clearProgressRate);
        int clearGraphNegativeWeight = 10000 - clearGraphWeight;

        // クリアグラフ（実線）
        LinearLayout.LayoutParams clearGraphWeightParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        clearGraphWeightParam.weight = clearGraphWeight;
        holder.clearGraphPositiveView.setLayoutParams(clearGraphWeightParam);

        // クリアグラフ（透明線）
        LinearLayout.LayoutParams clearGraphWeightNegativeParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        clearGraphWeightNegativeParam.weight = clearGraphNegativeWeight;
        holder.clearGraphNegativeView.setLayoutParams(clearGraphWeightNegativeParam);

        // スタイル情報
        String inpactBeginTag = "<b><big>";
        String inpactEndTag = "</big></b>";

        // スコア情報
        final String SCORE_INFO_FORMAT = "score: " + inpactBeginTag + "{0}" + inpactEndTag + " ({1}%)";
        String[] scoreInfoReplaceArr;
        if (resultExistFlg) {
            scoreInfoReplaceArr = new String[]{
                    String.valueOf(music.getMusicResultDBHR().getExScore()),
                    String.format("%.2f", music.getMusicResultDBHR().getScoreRate())
            };
        } else {
            scoreInfoReplaceArr = new String[]{
                    "ー",
                    "ー"
            };
        }
        String scoreInfo = MessageFormat.format(SCORE_INFO_FORMAT, (Object[]) scoreInfoReplaceArr);
        holder.scoreInfoView.setText(Html.fromHtml(scoreInfo));

        // ミス情報
        final String MISS_INFO_FORMAT = "bp: " + inpactBeginTag + "{0}" + inpactEndTag + " ({1}%)";
        String[] missInfoReplaceArr;
        if (resultExistFlg) {
            missInfoReplaceArr = new String[]{
                    String.valueOf(music.getMusicResultDBHR().getBp()),
                    String.format("%.2f", music.getMusicResultDBHR().getMissRate())
            };
        } else {
            missInfoReplaceArr = new String[]{
                    "ー",
                    "ー"
            };
        }
        String missInfo = MessageFormat.format(MISS_INFO_FORMAT, (Object[]) missInfoReplaceArr);
        holder.missInfoView.setText(Html.fromHtml(missInfo));

        // メモ
        String memoOther = getMemoOther(music, resultExistFlg);
        holder.memoOtherView.setText(memoOther);

        return view;
    }

    // ArrayAdapterの実装をパクリ、フィルタ元のみロード時のListに変更
    @Override
    public Filter getFilter() {
        LogUtil.logEntering();
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence prefix) {
                FilterResults results = new FilterResults();

/* 初期化済のはずなので、ArrayAdapterの実装から削除
                if (mOriginalValues == null) {
                    synchronized (mLock) {
                        mOriginalValues = new ArrayList<T>(mObjects);
                    }
                }
*/
                if (prefix == null || prefix.length() == 0) {
                    ArrayList<MusicMst> list;
                    synchronized (mLock) {
                        list = new ArrayList<MusicMst>(musicListOrg);
                    }
                    results.values = list;
                    results.count = list.size();
                } else {
                    String prefixString = prefix.toString().toLowerCase();

                    ArrayList<MusicMst> values;
                    synchronized (mLock) {
                        values = new ArrayList<MusicMst>(musicListOrg);
                    }

                    final int count = values.size();
                    final ArrayList<MusicMst> newValues = new ArrayList<MusicMst>();

                    for (int i = 0; i < count; i++) {
                        final MusicMst value = values.get(i);
                        final String valueText = value.toString().toLowerCase();

                        // First match against the whole, non-splitted value
                        if (valueText.startsWith(prefixString)) {
                            newValues.add(value);
                        } else {
                            final String[] words = valueText.split(" ");
                            final int wordCount = words.length;

                            // Start at index 0, in case valueText starts with space(s)
                            for (int k = 0; k < wordCount; k++) {
                                if (words[k].startsWith(prefixString)) {
                                    newValues.add(value);
                                    break;
                                }
                            }
                        }
                    }

                    results.values = newValues;
                    results.count = newValues.size();
                }

                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                //noinspection unchecked
                musicList = (List<MusicMst>) results.values;
                if (results.count > 0) {
                    notifyDataSetChanged();
                } else {
                    notifyDataSetInvalidated();
                }
            }
        };
    }


    static class ViewHolder {
        // クリアランプ
        TextView clearLampView;
        // 難易度
        TextView difficultView;
        // 曲名
        TextView nameView;
        // スコアグラフ（実線）
        TextView scoreGraphPositiveView;
        // スコアグラフ（透明線）
        TextView scoreGraphNegativeView;
        // クリアグラフ（実線）
        TextView clearGraphPositiveView;
        // クリアグラフ（透明線）
        TextView clearGraphNegativeView;
        // スコアランク
        ImageView scoreRankView;
        // スコア情報
        TextView scoreInfoView;
        // ミス情報
        TextView missInfoView;
        // メモ
        TextView memoOtherView;
    }

    public void searchApplyToListView(MusicListActivity musicListActivity) {
        LogUtil.logEntering();

        // WHERE句生成
        String whereStatement = musicListActivity.getSearchNaviManager().generateWhereStatement();
        LogUtil.logDebug("■query:[SELECT T.*, T0.* FROM MUSIC_MST T LEFT JOIN MUSIC_RESULT_DBHR T0 ON T.'MUSIC_RESULT_ID_DBHR'=T0.'_id' " + whereStatement + ";]");

        // 検索実施
        MusicMstDao musicMstDao = getMusicMstDao(context);
        List<MusicMst> musicMstList = musicMstDao.queryDeep(whereStatement);

        // 検索結果を再設定
        this.setMusicList(musicMstList);
        this.setMusicListOrg(new ArrayList<MusicMst>(musicMstList));

        // 曲名部分一致絞り込みのSearchViewに入力がある場合はフィルタする
        Toolbar toolbar = (Toolbar) musicListActivity.findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        MenuItem searchItem = menu.findItem(R.id.action_refine_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        Filter filter = this.getFilter();
        filter.filter(searchView.getQuery().toString());

        // リフレッシュ
        this.notifyDataSetChanged();

        LogUtil.logExiting();
    }

    protected String getMemoOther(MusicMst music, boolean resultExistFlg) {
        LogUtil.logEntering();

        final String MEMO_OTHER_FORMAT = "メモ: {0}";
        String[] memoOtherReplaceArr;
        if (resultExistFlg) {
            memoOtherReplaceArr = new String[]{
                    String.valueOf(music.getMusicResultDBHR().getMemoOther())
            };
        } else {
            memoOtherReplaceArr = new String[]{
                    "ー"
            };
        }
        String memoOther = MessageFormat.format(MEMO_OTHER_FORMAT, (Object[]) memoOtherReplaceArr);

        LogUtil.logExiting();
        return memoOther;
    }

}