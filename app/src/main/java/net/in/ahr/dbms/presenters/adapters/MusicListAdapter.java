package net.in.ahr.dbms.presenters.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.preference.PreferenceManager;
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
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.AssetsImgUtil;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.util.DbmsGreenDaoUtils;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.greenrobot.dao.query.Join;
import de.greenrobot.dao.query.QueryBuilder;
import de.greenrobot.dao.query.WhereCondition;
import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;
import greendao.MusicResultDBHRDao;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicListAdapter extends BaseAdapter implements Filterable {

    Context context;
    LayoutInflater layoutInflater = null;
    private final Object mLock = new Object();

    static final AlphaAnimation clearLampAnimation = new AlphaAnimation(0.7f, 1f);

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    // TODO: マスタではなくスコアを含める必要あり
    public static List<MusicMst> musicList;

    public static List<MusicMst> getMusicList() {
        return musicList;
    }

    public static void setMusicList(List<MusicMst> musicList) {
        MusicListAdapter.musicList = musicList;
    }

    // フィルタ初期化用
    public static List<MusicMst> musicListOrg;

    public static List<MusicMst> getMusicListOrg() {
        return musicListOrg;
    }

    public static void setMusicListOrg(List<MusicMst> musicListOrg) {
        MusicListAdapter.musicListOrg = musicListOrg;
    }

    public MusicListAdapter(Context context) {
        this.context = context;
        this.layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
        if ( music.getMusicResultDBHR() == null ) {
            resultExistFlg = false;
        }

        // クリアランプ
        // TODO: DBHR以外にプレイスタイルが増えた場合は文字を変えたい
        holder.clearLampView.setText(Html.fromHtml("H<br />R"));

        String clearLamp = AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY;
        if ( resultExistFlg && music.getMusicResultDBHR().getClearLamp() != null ) {
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

            if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#330000"));
                holder.clearLampView.setTextColor(Color.parseColor("#ff0000"));

                // ランプ点滅
                clearLampAnimation.setDuration(50);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                holder.clearLampView.startAnimation(clearLampAnimation);

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#666666"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅
                clearLampAnimation.setDuration(50);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                holder.clearLampView.startAnimation(clearLampAnimation);

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#ba55d3"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#00ffff"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#00ff00"));
//                holder.clearLampView.setBackgroundColor(Color.parseColor("#00ff7f"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR.equals(clearLamp) ) {
                // 色設定
//                holder.clearLampView.setBackgroundColor(Color.parseColor("#ff0000"));
                holder.clearLampView.setBackgroundColor(Color.parseColor("#dc143c"));
                holder.clearLampView.setTextColor(Color.parseColor("#dddddd"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#EFEFEF"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#ffa500"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅停止
                holder.clearLampView.clearAnimation();

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO.equals(clearLamp) ) {
                // 色設定
                holder.clearLampView.setBackgroundColor(Color.parseColor("#ffffff"));
                holder.clearLampView.setTextColor(Color.parseColor("#333333"));

                // ランプ点滅
                clearLampAnimation.setDuration(500);
                clearLampAnimation.setRepeatCount(Animation.INFINITE);
                holder.clearLampView.startAnimation(clearLampAnimation);

            } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT.equals(clearLamp) ) {
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
        if ( "NORMAL".equals(nha) ) {
            holder.difficultView.setBackgroundColor(Color.parseColor("#333366"));
        } else if ( "HYPER".equals(nha) ) {
            holder.difficultView.setBackgroundColor(Color.parseColor("#666633"));
        } else if ( "ANOTHER".equals(nha) ) {
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
        if(resultExistFlg) {
            scoreRate = music.getMusicResultDBHR().getScoreRate();
        } else {
            scoreRate = 0;
        }
        int scoreGraphWeight = (int) (100 * scoreRate);
        int scoreGraphNegativeWeight = 10000 - scoreGraphWeight;

        // スコアグラフ（実線）
        LinearLayout.LayoutParams scoreGraphWeightParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        scoreGraphWeightParam.weight = scoreGraphWeight;
        holder.scoreGraphPositiveView.setLayoutParams(scoreGraphWeightParam);

        // スコアグラフ（透明線）
        LinearLayout.LayoutParams scoreGraphWeightNegativeParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        scoreGraphWeightNegativeParam.weight = scoreGraphNegativeWeight;
        holder.scoreGraphNegativeView.setLayoutParams(scoreGraphWeightNegativeParam);

        // クリアグラフ
        double clearProgressRate;
        int remainingGaugeOrDeadNotes;
        if ( resultExistFlg && music.getMusicResultDBHR().getRemainingGaugeOrDeadNotes() != null ) {
            remainingGaugeOrDeadNotes = music.getMusicResultDBHR().getRemainingGaugeOrDeadNotes().intValue();
        } else {
            remainingGaugeOrDeadNotes = 0;
        }
        if(resultExistFlg) {
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
        LinearLayout.LayoutParams clearGraphWeightParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        clearGraphWeightParam.weight = clearGraphWeight;
        holder.clearGraphPositiveView.setLayoutParams(clearGraphWeightParam);

        // クリアグラフ（透明線）
        LinearLayout.LayoutParams clearGraphWeightNegativeParam = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        clearGraphWeightNegativeParam.weight = clearGraphNegativeWeight;
        holder.clearGraphNegativeView.setLayoutParams(clearGraphWeightNegativeParam);

        // スタイル情報
        String inpactBeginTag = "<b><big>";
        String inpactEndTag = "</big></b>";

        // スコア情報
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
        holder.scoreInfoView.setText(Html.fromHtml(scoreInfo));

        // ミス情報
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
        holder.missInfoView.setText(Html.fromHtml(missInfo));

        // メモ
        final String MEMO_OTHER_FORMAT = "メモ: {0}";
        String[] memoOtherReplaceArr;
        if (resultExistFlg) {
            memoOtherReplaceArr = new String[] {
                    String.valueOf(music.getMusicResultDBHR().getMemoOther())
            };
        } else {
            memoOtherReplaceArr = new String[] {
                    "ー"
            };
        }
        String memoOther = MessageFormat.format(MEMO_OTHER_FORMAT, (Object[])memoOtherReplaceArr);
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
        // SharedPreferenceラッパー取得
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences);

        // 条件リストを作成
        // 難易度
        List<String> difficultCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfDiff_10() ) {
            difficultCondList.add(AppConst.MUSIC_MST_DIFFICULT_VAL_10);
        }
        if ( dbmsSharedPreferences.getSearchConfDiff_11() ) {
            difficultCondList.add(AppConst.MUSIC_MST_DIFFICULT_VAL_11);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( difficultCondList.size() == 0 ) {
            difficultCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // クリアランプ
        boolean clearLampIsNullFlg = false;
        List<String> clearLampCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfClearLamp_NO_PLAY() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY);
            clearLampIsNullFlg = true;
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_FAR_AWAY() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_FAILED() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_ASSIST_EASY_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_EASY_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_NORMAL_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_HARD_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_EXHARD_CLEAR() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_FULL_COMBO() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO);
        }
        if ( dbmsSharedPreferences.getSearchConfClearLamp_PERFECT() ) {
            clearLampCondList.add(AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( clearLampCondList.size() == 0 ) {
            clearLampCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // スコアランク
        boolean scoreRankIsNullFlg = false;
        List<String> scoreRankCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfScoreRank_AAA() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_AAA);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_AA() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_AA);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_A() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_A);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_B() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_B);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_C() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_C);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_D() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_D);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_E() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_E);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_F() ) {
            scoreRankCondList.add(AppConst.MUSIC_MST_SCORE_RANK_VAL_F);
        }
        if ( dbmsSharedPreferences.getSearchConfScoreRank_NO_RANK() ) {
            scoreRankIsNullFlg = true;
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( scoreRankCondList.size() == 0 && !scoreRankIsNullFlg) {
            scoreRankCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // BPM範囲
        int bpmRangeSelectedCnt = 0;
        List<List<String>> bpmRangeCondAndListOrList = new ArrayList<List<String>>();
        if ( dbmsSharedPreferences.getSearchConfBpmRange_LLL_129() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 130 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_130_139()) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 130 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 140 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_140_149() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 140 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 150 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_150_159() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 150 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 160 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_160_169() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 160 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 170 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_170_179() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 170 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 180 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_180_189() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 180 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 190 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_190_199() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 190 ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " < 200 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_200_HHH() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " = " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " >= 200 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        if ( dbmsSharedPreferences.getSearchConfBpmRange_SOFLAN() ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " != " + MusicMstDao.Properties.BpmTo.columnName + " ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( bpmRangeCondAndListOrList.size() == 0 ) {
            List<String> bpmRangeCondAndList = new ArrayList<String>();
            bpmRangeCondAndList.add("T." + MusicMstDao.Properties.BpmFrom.columnName + " == 9999 ");
            bpmRangeCondAndListOrList.add(bpmRangeCondAndList);
        }

        // バージョン
        List<String> versionCondList = new ArrayList<String>();
        if ( dbmsSharedPreferences.getSearchConfVersion_1st() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_1ST);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_sub() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SUB);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_2nd() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_2ND);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_3rd() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_3RD);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_4th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_4TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_5th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_5TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_6th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_6TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_7th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_7TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_8th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_8TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_9th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_9TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_10th() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_10TH);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_RED() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_RED);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_SKY() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SKY);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_DD() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_DD);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_GOLD() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_GOLD);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_DJT() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_DJT);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_EMP() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_EMP);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_SIR() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SIR);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_RA() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_RA);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_LC() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_LC);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_tri() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_TRI);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_SPA() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_SPA);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_PEN() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_PEN);
        }
        if ( dbmsSharedPreferences.getSearchConfVersion_cop() ) {
            versionCondList.add(AppConst.MUSIC_MST_VERSION_VAL_COP);
        }
        // 全て選択されていない場合、絶対に条件を満たさない値を追加することで全非表示
        if ( versionCondList.size() == 0 ) {
            versionCondList.add(AppConst.CONST_NO_HIT_DUMMY_STRING);
        }

        // 初回のみwhere、使用後は"and"を設定
        String sqlWhereAnd = "where";

        // 検索
        MusicMstDao musicMstDao = getMusicMstDao(context);
        StringBuilder whereSb = new StringBuilder();

        // 検索条件：難易度
        String difficultCondStr = DbmsGreenDaoUtils.inStatementStr(difficultCondList);
        if (difficultCondStr.length() > 0) {
            whereSb.append(
                    sqlWhereAnd + " T." + MusicMstDao.Properties.Difficult.columnName + " in(" + difficultCondStr + ") "
            );
            sqlWhereAnd = "and";
        }

        // 検索条件：クリアランプ
        String clearLampCondStr = DbmsGreenDaoUtils.inStatementStr(clearLampCondList);
        if (clearLampCondStr.length() > 0) {

            whereSb.append(sqlWhereAnd);
            // NO PLAYの場合は結合して値NULLの場合も対象（前かっこ結合）
            if (clearLampIsNullFlg) {
                whereSb.append("(");
            }
            whereSb.append(
                    " T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + " in(" + clearLampCondStr + ") "
            );
            // NO PLAYの場合は結合して値NULLの場合も対象
            if (clearLampIsNullFlg) {
                whereSb.append(
                        "or T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + " IS NULL) "
                );
            }
            sqlWhereAnd = "and";
        }

        // 検索条件：スコアランク
        String scoreRankCondStr = DbmsGreenDaoUtils.inStatementStr(scoreRankCondList);
        if (scoreRankCondStr.length() > 0) {

            whereSb.append(sqlWhereAnd);
            // NO RANKの場合は結合して値NULLの場合も対象（前かっこ結合）
            if (scoreRankIsNullFlg) {
                whereSb.append("(");
            }
            whereSb.append(
                    " T0." + MusicResultDBHRDao.Properties.ScoreRank.columnName + " in(" + scoreRankCondStr + ") "
            );
            // NO RANKの場合は結合して値NULLの場合も対象
            if (scoreRankIsNullFlg) {
                whereSb.append(
                        "or T0." + MusicResultDBHRDao.Properties.ScoreRank.columnName + " IS NULL) "
                );
            }
            sqlWhereAnd = "and";
        } else {
            // NO RANKの場合は結合して値NULLの場合も対象（前かっこ結合）
            if (scoreRankIsNullFlg) {
                whereSb.append(sqlWhereAnd);
                whereSb.append(" T0." + MusicResultDBHRDao.Properties.ScoreRank.columnName + " IS NULL ");
            }
        }

        // 検索条件：BPM範囲
        if (bpmRangeCondAndListOrList.size() > 0) {

            whereSb.append(sqlWhereAnd);
            whereSb.append("(");

            boolean firstCondListFlg = true;
            for (List<String> bpmRangeCondAndList : bpmRangeCondAndListOrList) {

                if (!firstCondListFlg) {
                    whereSb.append("or ");
                }
                whereSb.append("(");

                boolean firstCondFlg = true;
                for (String bpmRangeCond : bpmRangeCondAndList) {

                    if (!firstCondFlg) {
                        whereSb.append("and ");
                    }

                    whereSb.append(bpmRangeCond);
                    whereSb.append(" ");
                    firstCondFlg = false;

                }

                whereSb.append(")");
                firstCondListFlg = false;

            }

            whereSb.append(")");
            sqlWhereAnd = "and";

        }

        // 検索条件：バージョン
        String versionCondStr = DbmsGreenDaoUtils.inStatementStr(versionCondList);
        if (versionCondStr.length() > 0) {
            whereSb.append(
                    sqlWhereAnd + " T." + MusicMstDao.Properties.Version.columnName + " in(" + versionCondStr + ") "
            );
            sqlWhereAnd = "and";
        }

        // ORDER SORT KIND
        String orderSortKind;
        String searchOrderSortKind = dbmsSharedPreferences.getSearchOrderSortKind();
        if ( AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC.equals(searchOrderSortKind) ) {
            orderSortKind = " ASC ";
        } else {
            orderSortKind = " DESC ";
        }

        // ORDER BY
        String searchOrderByTarget = dbmsSharedPreferences.getSearchOrderByTarget();
        if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                         + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_NAME.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_EXSCORE.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.ExScore.columnName       + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_BP.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.Bp.columnName            + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_SCORE_RATE.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.ScoreRate.columnName     + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_MISS_RATE.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.MissRate.columnName      + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        } else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_UPDATED.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.UpdDate.columnName       + orderSortKind
                            + ", T." + MusicMstDao.Properties.Difficult.columnName          + orderSortKind
                            + ", T." + MusicMstDao.Properties.SortNumInDifficult.columnName + orderSortKind
            );

        }
/*
        else if ( AppConst.SHARED_VALUE_SEARCH_ORDER_BY_CLEAR_PROGRESS.equals(searchOrderByTarget) ) {
            whereSb.append(
                    "order by T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + orderSortKind
                         + ", case T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName + " "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / 80 "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR
                            + "' then T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + " / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR
                            + "' then (" + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                        + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                                 + " - T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + ") / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO
                            + "' then (" + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                        + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                                 + " - T0." + MusicResultDBHRDao.Properties.RemainingGaugeOrDeadNotes.columnName + ") / "
                                     + "((T." + MusicMstDao.Properties.Notes.columnName + " - T." + MusicMstDao.Properties.ScratchNotes.columnName + ") * 2 "
                                    + " + T." + MusicMstDao.Properties.ChargeNotes.columnName + " * 2) "
                            + "when '" + AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT + "' then 1 end "
                    + orderSortKind
            );

        }
*/


        String whereStatement = whereSb.toString();
        LogUtil.logDebug("■query:[SELECT T.*, T0.* FROM MUSIC_MST T LEFT JOIN MUSIC_RESULT_DBHR T0 ON T.'MUSIC_RESULT_ID_DBHR'=T0.'_id' " + whereStatement + ";]");

        List<MusicMst> musicMstList = musicMstDao.queryDeep(whereStatement);
/*

                        musicMstDao
        QueryBuilder<MusicMst> qb = musicMstDao.queryBuilder();
        qb.where(MusicMstDao.Properties.Difficult.in(difficultCondList));
        int bpmRangeIndex = 0;

        while (bpmRangeIndex < bpmRangeSelectedCnt * 3) {
            qb.or(
                    qb.and(
                            bpmRangeCondList.get(bpmRangeIndex++),
                            bpmRangeCondList.get(bpmRangeIndex++),
                            bpmRangeCondList.get(bpmRangeIndex++)
                    )
            );
        }

        qb.where(MusicMstDao.Properties.Version.in(versionCondList));
/*
        qb.join(MusicResultDBHR.class, MusicResultDBHRDao.Properties.Id);
                .where(MusicResultDBHRDao.Properties.ClearLamp.in(clearLampCondList))
                .where(MusicResultDBHRDao.Properties.ScoreRank.in(scoreRankCondList));


        List<MusicMst> musicMstList = qb.list();
*/

        // 検索結果を再設定
        this.setMusicList(musicMstList);
        this.setMusicListOrg(new ArrayList<MusicMst>(musicMstList));

        // 曲名部分一致絞り込みのSearchViewに入力がある場合はフィルタする
        Toolbar toolbar = (Toolbar) musicListActivity.findViewById(R.id.toolbar);
        Menu menu = toolbar.getMenu();
        MenuItem searchItem = menu.findItem(R.id.action_refine_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);

        Filter filter = this.getFilter();
        filter.filter( searchView.getQuery().toString() );

        // リフレッシュ
        this.notifyDataSetChanged();
    }


/*
    @Override
    public Filter getFilter() {
        LogUtil.logEntering();
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                LogUtil.logEntering();

                FilterResults results = new FilterResults();
                List<MusicMst> filterMusicList = new ArrayList<MusicMst>();

                String filterString = constraint.toString().toLowerCase();
                String filterableString;

                if (!TextUtils.isEmpty(constraint)) {
                    for (MusicMst music : musicList) {
                        filterableString = music.getName().toLowerCase();
                        if (filterableString.contains(filterString)) {
                            filterMusicList.add(music);
                        }
                    }
                } else {
                    filterMusicList = musicList;
                }

                results.values = filterMusicList;
                results.count = filterMusicList.size();

                LogUtil.logExiting();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                LogUtil.logEntering();

                if (results.count > 0) {
                    @SuppressWarnings("unchecked")
                    List<MusicMst> filterItems = (ArrayList<MusicMst>)results.values;

                    notifyDataSetChanged();
                    MusicListAdapter.musicList.clear();

                    for (MusicMst filterItem : filterItems) {
                        MusicListAdapter.musicList.add(filterItem);
                    }
                }

                LogUtil.logExiting();
            }
        };
    }
*/
}
