package net.in.ahr.dbms.presenters.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.R;
import net.in.ahr.dbms.business.usecases.result.MusicResultUtil;
import net.in.ahr.dbms.business.usecases.result.check.ResultNumberTextWatcher;
import net.in.ahr.dbms.data.network.api.asyncTask.GetMusicRankingAsyncTask;
import net.in.ahr.dbms.data.network.api.dto.MusicResultDBHRDto;
import net.in.ahr.dbms.data.network.api.util.DbmsApiUtils;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.events.musicList.DisplayLongToastEvent;
import net.in.ahr.dbms.others.util.DbmsGreenDaoUtils;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.adapters.MusicRankingListAdapter;
import net.in.ahr.dbms.presenters.fragments.common.ChildFragmentCommon;
import net.in.ahr.dbms.presenters.tabManagers.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;
import greendao.MusicResultDBHRDao;
import greendao.MusicResultDBHR_History;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicEditFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "MusicEditFragment";

    private MusicMst music;
    private int musicPosition;

    public TextView clearLampTextView;
    private Spinner clearLampSpinner;
    private static int selectedPosition = -1;

    private TextView nameNhaTextView;

    private EditText exScoreEditText;
    private EditText bpEditText;
    private EditText remainingGaugeOrDeadNotesEditText;
    private EditText memoOtherEditText;

    private Button backButton;
    private Button playedButton;
    private Button updateButton;

    private TextView updatedTextView;
    private TextView djPointTextView;


    private TextView genreTextView;
    private TextView artistTextView;
    private TextView versionTextView;
    private TextView bpmTextView;
    private TextView difficultTextView;
    private TextView notesDbTextView;
    private TextView chargeNotesTextView;

    // TextInputLayout
    TextInputLayout exScoreTextInputLayout;
    TextInputLayout bpTextInputLayout;
    TextInputLayout remainingGaugeOrDeadNotesTextInputLayout;
    TextInputLayout memoOtherTextInputLayout;

    // TextWatcher or modFlg
    public boolean clearLampSpinnerModFlg;
    public ResultNumberTextWatcher exScoreTextWatcher;
    public ResultNumberTextWatcher bpTextWatcher;
    public ResultNumberTextWatcher clearProgressTextWatcher;
    public ResultNumberTextWatcher memoOtherTextWatcher;

    // createdView
    public View createdView;

    // Dialog
    public static DialogFragment dialogFragment;

    public ProgressBar musicRankingProgressbar;
    public TextView emptyView;

    public View headerView;
    public ListView musicRankingListView;
    public LinearLayout musicRankingListViewWrapper;

    public ListView getMusicRankingListView() {
        return musicRankingListView;
    }

    public MusicRankingListAdapter musicRankingListAdapter;

    public void setMusicRankingListAdapter(MusicRankingListAdapter musicRankingListAdapter) {
        this.musicRankingListAdapter = musicRankingListAdapter;
    }

    public MusicRankingListAdapter getMusicRakingListAdapter() {
        return musicRankingListAdapter;
    }

    /** サーチビュー */
    SearchView searchView;

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    private static MusicResultDBHRDao getMusicResultDBHRDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicResultDBHRDao();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        music = (MusicMst) bundle.getSerializable("musicForEdit");
        musicPosition = bundle.getInt("musicPosition");

        // 編集前の内容を別インスタンスで保持
        DbmsGreenDaoUtils dbmsGreenDaoUtils = new DbmsGreenDaoUtils();

        // タブ表示なしFragmentのonCreate共通処理
        ChildFragmentCommon childFragmentCommon = new ChildFragmentCommon();
        childFragmentCommon.onCreateCommon((MusicListActivity) getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_edit, container, false);
        createdView = view;

        // マスタ情報その1
        // 曲名
        nameNhaTextView = (TextView) view.findViewById(R.id.musicEditFragment_name_nha);
        nameNhaTextView.setText(music.getName());
        // N・H・Aにより背景色を設定
        if ( AppConst.MUSIC_MST_NHA_VAL_NORMAL.equals(music.getNha()) ) {
            nameNhaTextView.setBackgroundColor(Color.parseColor("#ccccff"));
        } else if ( AppConst.MUSIC_MST_NHA_VAL_HYPER.equals(music.getNha()) ) {
            nameNhaTextView.setBackgroundColor(Color.parseColor("#eeee99"));
        } else if ( AppConst.MUSIC_MST_NHA_VAL_ANOTHER.equals(music.getNha()) ) {
            nameNhaTextView.setBackgroundColor(Color.parseColor("#ff9999"));
        }

        // リザルト存在フラグを設定
        boolean resultExistFlg = true;
        if ( music.getMusicResultDBHR() == null ) {
            resultExistFlg = false;
        }

        // Spinner選択位置を初期化
        selectedPosition = -1;

        // クリアランプの選択肢を設定（同時に残ゲージor到達ノーツ数ラベルも決定）
        clearLampSpinner = (Spinner) view.findViewById(R.id.musicEditFragment_clearLamp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // クリアランプテキストをフォーカスで切り替える
        clearLampTextView = (TextView) view.findViewById(R.id.musicEditFragment_clearLamp_text);
        clearLampSpinner.setFocusable(true);
        clearLampSpinner.setFocusableInTouchMode(true);
        clearLampSpinner.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    LogUtil.logDebug("clearLampSpinnerフォーカス中");
                    clearLampTextView.setTextColor(Color.parseColor("#FF4081"));
                    // 以下を実行しないと、フォーカスしてからもう一回クリックしないとSpinnerが開かない
                    v.performClick();
                } else {
                    LogUtil.logDebug("clearLampSpinnerフォーカス中でなくなった");
                    clearLampTextView.setTextColor(Color.parseColor("#666666"));
                }
            }
        });

        // クリアランプコード値配列（定数）
        String[] clearLumpAllValArr = AppConst.CLEAR_LUMP_VAL_ARR;

        // 選択可能クリアランプの設定を取得
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        DbmsSharedPreferences dbmsSharedPreferences = new DbmsSharedPreferences(sharedPreferences);
        Set<String> clearLumpValSet = dbmsSharedPreferences.getSettingSelectableClearLamp();

        // クリアランプコード値カスタムリスト生成
        List<String> clearLumpValCustomList = new ArrayList<String>();

        // 定数配列でループ
        for (String clearLumpValConst : clearLumpAllValArr) {
            if ( clearLumpValSet.contains(clearLumpValConst) ) {
                // 選択可能クリアランプSetに含まれていればput
                clearLumpValCustomList.add(clearLumpValConst);
            } else if ( resultExistFlg && clearLumpValConst.equals(music.getMusicResultDBHR().getClearLamp()) ) {
                // 選択可能クリアランプSetに含まれていなくても、リザルトのクリアランプの場合はadd
                clearLumpValCustomList.add(clearLumpValConst);
            }
        }

        // カスタム設定後のクリアランプ値配列
        String[] clearLumpValArr = clearLumpValCustomList.toArray(new String[clearLumpValCustomList.size()]);

        // 残ゲージor到達ノーツ数ラベル配列
        String[] remainingGaugeOrDeadNotesLabelArr = AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ARR;
        // 残ゲージor到達ノーツ数ラベル
        String remainingGaugeOrDeadNotesLabel = AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_NO_PLAY;
        for (int i = 0; i < clearLumpValArr.length; i++) {
            adapter.add(clearLumpValArr[i]);
            if (resultExistFlg) {
                if (clearLumpValArr[i].equals(music.getMusicResultDBHR().getClearLamp())) {
                    selectedPosition = i;
                    remainingGaugeOrDeadNotesLabel = remainingGaugeOrDeadNotesLabelArr[i];
                }
            }
        }
        if (selectedPosition == -1) {
         selectedPosition = 0;
        }
        clearLampSpinner.post(new Runnable() {
            public void run() {
                clearLampSpinner.setSelection(selectedPosition, true);
            }
        });
        clearLampSpinner.setAdapter(adapter);

        // クリアランプの選択肢変更時の処理
        clearLampSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            // アイテムが選択された時の動作
            @Override
            public void onItemSelected(AdapterView parent,View view, int position,long id) {
                // Spinner を取得
                Spinner spinner = (Spinner) parent;
                // 選択されたアイテムのテキストを取得
                String selectedClearLamp = spinner.getSelectedItem().toString();

                // 選択肢に対応するラベルを「残ゲージor到達ノーツ数」ラベルに設定
                String[] clearLumpValArr = AppConst.CLEAR_LUMP_VAL_ARR;
                String[] remainingGaugeOrDeadNotesLabelArr = AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ARR;
                String remainingGaugeOrDeadNotesLabel = AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_NO_PLAY;
                for (int i = 0; i < clearLumpValArr.length; i++) {
                    if (clearLumpValArr[i].equals(selectedClearLamp)) {
                        selectedPosition = i;
                        remainingGaugeOrDeadNotesLabel = remainingGaugeOrDeadNotesLabelArr[i];
                        break;
                    }
                }
                remainingGaugeOrDeadNotesTextInputLayout.setHint(remainingGaugeOrDeadNotesLabel);

                // 過去リザルトから変更しているか判定
                String resultClearLamp;
                if (music.getMusicResultDBHR() == null || music.getMusicResultDBHR().getClearLamp() == null) {
                    resultClearLamp = AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY;
                } else if ( AppConst.CONST_BLANK.equals(music.getMusicResultDBHR().getClearLamp().trim()) ) {
                    resultClearLamp = AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY;
                } else {
                    resultClearLamp = music.getMusicResultDBHR().getClearLamp();
                }
                LogUtil.logDebug("■selectedClearLamp:[" + selectedClearLamp + "]");
                LogUtil.logDebug("■resultClearLamp:[" + resultClearLamp + "]");
                if ( selectedClearLamp.equals(resultClearLamp) ) {
                    clearLampSpinnerModFlg = false;
                } else {
                    clearLampSpinnerModFlg = true;
                }
                LogUtil.logDebug("■clearLampSpinnerModFlg[" + clearLampSpinnerModFlg + "]");

                // 変更時はヒントの頭に[*]を付与
                String hint = (String) clearLampTextView.getText();
                if (clearLampSpinnerModFlg) {
                    if ( !hint.startsWith(AppConst.CONST_MOD_MARK) ) {
                        clearLampTextView.setText(
                                AppConst.CONST_MOD_MARK + hint
                        );
                    }
                } else {
                    clearLampTextView.setText(
                            hint.replace(AppConst.CONST_MOD_MARK, AppConst.CONST_BLANK)
                    );
                }

            }

            // 何も選択されなかった時の動作
            @Override
            public void onNothingSelected(AdapterView parent) {
                // ありえない
                // do nothing
            }

        });

        // Formオブジェクト保持、初期値設定（EXスコア）
        exScoreEditText = (EditText) view.findViewById(R.id.musicEditFragment_exScore);
        exScoreEditText.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        );
        if (resultExistFlg) {
            exScoreEditText.setText(
                    String.valueOf(music.getMusicResultDBHR().getExScore()));
        }

        // EXスコアチェック処理を設定
        exScoreTextInputLayout = (TextInputLayout) view.findViewById(R.id.TextInputLayout_exScore);
        exScoreTextWatcher = new ResultNumberTextWatcher(
                exScoreTextInputLayout,
                music,
                ResultNumberTextWatcher.CHECK_MODE_EX_SCORE,
                null);
        exScoreEditText.addTextChangedListener(exScoreTextWatcher);

        // EXスコア入力欄フォーカス時に全選択
        exScoreEditText.setSelectAllOnFocus(true);

        // Formオブジェクト保持、初期値設定（BP）
        bpEditText = (EditText) view.findViewById(R.id.musicEditFragment_bp);
        bpEditText.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        );
        if (resultExistFlg) {
            bpEditText.setText(
                    String.valueOf(music.getMusicResultDBHR().getBp()));
        }

        // BPチェック処理
        bpTextInputLayout = (TextInputLayout) view.findViewById(R.id.TextInputLayout_bp);
        bpTextWatcher = new ResultNumberTextWatcher(
                bpTextInputLayout,
                music,
                ResultNumberTextWatcher.CHECK_MODE_BP,
                null);
        bpEditText.addTextChangedListener(bpTextWatcher);

        // BP入力欄フォーカス時に全選択
        bpEditText.setSelectAllOnFocus(true);

        // Formオブジェクト保持、初期値設定（残ゲージor到達ノーツ数）
        remainingGaugeOrDeadNotesEditText = (EditText) view.findViewById(R.id.musicEditFragment_remainingGaugeOrDeadNotes);
        remainingGaugeOrDeadNotesEditText.setInputType(
                InputType.TYPE_CLASS_NUMBER
                        | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS
        );
        String remainingGaugeOrDeadNotes = "";
        if ( resultExistFlg && music.getMusicResultDBHR().getRemainingGaugeOrDeadNotes() != null ) {
            remainingGaugeOrDeadNotes = String.valueOf(music.getMusicResultDBHR().getRemainingGaugeOrDeadNotes());
        }
        remainingGaugeOrDeadNotesEditText.setText(remainingGaugeOrDeadNotes);

        // クリアランプにより残ゲージor到達ノーツ数のヒントを切り替え
        remainingGaugeOrDeadNotesTextInputLayout = (TextInputLayout) view.findViewById(R.id.TextInputLayout_remainingGaugeOrDeadNotes);
        remainingGaugeOrDeadNotesTextInputLayout.setHint(remainingGaugeOrDeadNotesLabel);

        // 残ゲージor到達ノーツ数チェック処理を設定
        clearProgressTextWatcher = new ResultNumberTextWatcher(
                remainingGaugeOrDeadNotesTextInputLayout,
                music,
                ResultNumberTextWatcher.CHECK_MODE_CLEAR_PROGRESS,
                clearLampSpinner);
        remainingGaugeOrDeadNotesEditText.addTextChangedListener(clearProgressTextWatcher);

        // 残ゲージor到達ノーツ数入力欄フォーカス時に全選択
        remainingGaugeOrDeadNotesEditText.setSelectAllOnFocus(true);

        // Formオブジェクト保持、初期値設定（メモ）
        memoOtherEditText = (EditText) view.findViewById(R.id.musicEditFragment_memoOther);
        memoOtherEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        if (resultExistFlg) {
            memoOtherEditText.setText(music.getMusicResultDBHR().getMemoOther());
        }

        // 残ゲージor到達ノーツ数チェック処理を設定
        memoOtherTextInputLayout = (TextInputLayout) view.findViewById(R.id.TextInputLayout_memoOther);
        memoOtherTextWatcher = new ResultNumberTextWatcher(
                memoOtherTextInputLayout,
                music,
                ResultNumberTextWatcher.CHECK_MODE_MEMO_OTHER,
                null);
        memoOtherEditText.addTextChangedListener(memoOtherTextWatcher);

        // 戻るボタンのクリックリスナーを設定
        backButton = (Button) view.findViewById(R.id.musicEditFragment_backButton);
        backButton.setOnClickListener(this);

        // playedボタンのクリックリスナーを設定
        playedButton = (Button) view.findViewById(R.id.musicEditFragment_playedButton);
        playedButton.setOnClickListener(this);

        // 編集ボタンのクリックリスナーを設定
        updateButton = (Button) view.findViewById(R.id.musicEditFragment_updateButton);
        updateButton.setOnClickListener(this);

        // リザルト情報
        // djPoint
        djPointTextView = (TextView) view.findViewById(R.id.musicEditFragment_resultInfo_djPoint);
        String djPointStr = "-";
        if (resultExistFlg && music.getMusicResultDBHR().getDjPoint() != null) {
            djPointStr = String.format(Locale.US, "%.2f", music.getMusicResultDBHR().getDjPoint());
        }
        djPointTextView.setText(djPointStr);

        // 更新日時
        updatedTextView = (TextView) view.findViewById(R.id.musicEditFragment_resultInfo_updated);
        String updated = AppConst.CONST_HALF_HYPHEN;
        if (
                music.getMusicResultDBHR() != null
             && music.getMusicResultDBHR().getUpdDate() != null
        ) {
            SimpleDateFormat sdf = new SimpleDateFormat(AppConst.MUSIC_EDIT_UPDATED_FORMAT);
            updated = sdf.format(music.getMusicResultDBHR().getUpdDate());
        }
        updatedTextView.setText(updated);

        // マスタ情報その2
        // ジャンル
        genreTextView = (TextView) view.findViewById(R.id.musicEditFragment_genre);
        genreTextView.setText(music.getGenre());

        // アーティスト名
        artistTextView = (TextView) view.findViewById(R.id.musicEditFragment_artist);
        artistTextView.setText(music.getArtist());

        // バージョン
        versionTextView = (TextView) view.findViewById(R.id.musicEditFragment_version);
        String version = null;
        if ( AppConst.MUSIC_MST_VERSION_VAL_1ST.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_1ST;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_SUB.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_SUB;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_2ND.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_2ND;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_3RD.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_3RD;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_4TH.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_4TH;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_5TH.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_5TH;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_6TH.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_6TH;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_7TH.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_7TH;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_8TH.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_8TH;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_9TH.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_9TH;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_10TH.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_10TH;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_RED.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_RED;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_SKY.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_SKY;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_DD.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_DD;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_GOLD.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_GOLD;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_DJT.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_DJT;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_EMP.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_EMP;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_SIR.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_SIR;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_RA.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_RA;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_LC.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_LC;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_TRI.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_TRI;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_SPA.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_SPA;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_PEN.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_PEN;
        } else if ( AppConst.MUSIC_MST_VERSION_VAL_COP.equals(music.getVersion()) ) {
            version = AppConst.CONST_VERSION_FULL_COP;
        }
        versionTextView.setText(version);

        // BPM
        bpmTextView = (TextView) view.findViewById(R.id.musicEditFragment_bpm);
        String bpm = null;
        if (music.getBpmFrom().equals(music.getBpmTo()) ) {
            bpm = String.valueOf(music.getBpmFrom());
        } else {
            bpm = String.valueOf(music.getBpmFrom())
                + "〜"
                + String.valueOf(music.getBpmTo());
        }
        bpmTextView.setText(bpm);

        // 難易度
        difficultTextView = (TextView) view.findViewById(R.id.musicEditFragment_difficult);
        difficultTextView.setText(music.getDifficult());

        // ノーツ数（DB）
        notesDbTextView = (TextView) view.findViewById(R.id.musicEditFragment_notes_db);
        int notesDb = ( music.getNotes() - music.getScratchNotes() ) * 2;
        notesDbTextView.setText(
                String.valueOf(notesDb));

        // チャージノーツ
        chargeNotesTextView = (TextView) view.findViewById(R.id.musicEditFragment_chargeNotes_db);
        chargeNotesTextView.setText(
                String.valueOf(music.getChargeNotes() * 2));

        // インターネットランキングのロード中に表示するプログレスバー
        musicRankingProgressbar = (ProgressBar) view.findViewById(R.id.musicRankingProgressbar);

        // インターネットランキングListView
        musicRankingListAdapter = new MusicRankingListAdapter(getActivity());
        setMusicRankingListAdapter(musicRankingListAdapter);

        // リストビューの内容を一旦空で設定（GetMusicRankingAsyncTaskで設定する）
        List<MusicResultDBHRDto> musicResultDtoList = new ArrayList<MusicResultDBHRDto>();

        // アダプターにリストビュー、空データを設定
        musicRankingListAdapter.setMusicResultDtoList(musicResultDtoList);
        musicRankingListView = (ListView) view.findViewById(R.id.musicRankingListView);
        musicRankingListView.setAdapter(musicRankingListAdapter);

        // ListVIewラッパービュー
        musicRankingListViewWrapper = (LinearLayout) view.findViewById(R.id.musicRankingListViewWrapper);

        // リストビューのヘッダーを設定
        headerView = inflater.inflate(R.layout.list_music_ranking, null);
        ((TextView) headerView.findViewById(R.id.musicRanking_ranking)).setText("順位");
        ((TextView) headerView.findViewById(R.id.musicRanking_userName)).setText("プレイヤー名");
        ((TextView) headerView.findViewById(R.id.musicRanking_scoreRank)).setText("ランク");
        ((TextView) headerView.findViewById(R.id.musicRanking_exScore)).setText("スコア");
        ((TextView) headerView.findViewById(R.id.musicRanking_bp)).setText("BP");

        // リストビューのヘッダーを設定（ランキング取得が終わるまでは非表示）
        emptyView = (TextView) view.findViewById(R.id.musicRankingEmptyView);
        emptyView.setVisibility(View.GONE);

        // ランキング検索処理を実施
        MusicResultDBHRDto cond = new MusicResultDBHRDto();
        cond.setName(music.getName());
        cond.setNha(music.getNha());

        new GetMusicRankingAsyncTask(
                BuildConfig.DBMS_ONLINE_PATH + AppConst.DBMS_ONLINE_API_PATH_GET_RANKING_BY_NAME_NHA,
                cond,
                this
        ).execute(getActivity());

        return view;
    }

    @Override
    public void onClick(View view) {
        LogUtil.logEntering();

        // ソフトキーボードの表示をOFF
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        if (view == backButton) {

            // 変更箇所が1箇所でもある場合はダイアログで確認
            if (
                    clearLampSpinnerModFlg
                 || exScoreTextWatcher.modFlg
                 || bpTextWatcher.modFlg
                 || clearProgressTextWatcher.modFlg
                 || memoOtherTextWatcher.modFlg
                    ) {

                final Snackbar snackbar = Snackbar.make(view, "変更箇所があります。一覧画面に戻りますか？", Snackbar.LENGTH_LONG);
                snackbar.setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // Navigation Drowerのスワイプロックを解除
                        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                        backToTabFirstFragment();

                        snackbar.dismiss();
                    }
                });
                snackbar.show();

            } else {

                // Navigation Drowerのスワイプロックを解除
                DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                backToTabFirstFragment();

            }

/*
            // Navigation Drowerのスワイプロックを解除
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            backToTabFirstFragment();
*/

        } else if (view == playedButton) {

            // 入力エラー発生中はリジェクトしトースト表示
            if( existInputErr() ) {
                new DisplayLongToastEvent().start("入力エラーのため「PLAYED」ボタンによる更新ができません。");
                return;
            } else {

                // 変更箇所が1箇所でもあるかチェック
                if (
                        clearLampSpinnerModFlg
                                || exScoreTextWatcher.modFlg
                                || bpTextWatcher.modFlg
                                || clearProgressTextWatcher.modFlg
                                || memoOtherTextWatcher.modFlg
                        ) {

                    // 変更がある場合はUPDATEボタン押下を即す
                    new DisplayLongToastEvent().start("変更箇所がありますので、「UPDATE」ボタンを押下してください。「PLAYED」はリザルト更新できなかった履歴を残す機能です。");

                } else {

                    // 編集処理
                    boolean playedFlg = true;
                    updateResult(playedFlg);

                    // Navigation Drowerのスワイプロックを解除
                    DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
                    drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

                    backToTabFirstFragment();

                }

            }

        } else if (view == updateButton) {

            // 入力エラー発生中はリジェクトしトースト表示
            if( existInputErr() ) {
                new DisplayLongToastEvent().start("入力エラーのため「UPDATE」ボタンによる更新ができません。");
                return;
            } else {

                // 変更箇所が1箇所でもあるかチェック
                if (
                        clearLampSpinnerModFlg
                                || exScoreTextWatcher.modFlg
                                || bpTextWatcher.modFlg
                                || clearProgressTextWatcher.modFlg
                                || memoOtherTextWatcher.modFlg
                        ) {

                    // 更新共通処理
                    doUpdate();
                } else {
                    // 変更がない場合
                    new DisplayLongToastEvent().start("変更箇所がありません。");
                }

            }

        }

        LogUtil.logExiting();
    }

    private boolean existInputErr() {

        boolean result;

        String exScoreInputErrMsg = (String) exScoreTextInputLayout.getError();
        if (exScoreInputErrMsg == null) {
            exScoreInputErrMsg = AppConst.CONST_BLANK;
        }
        String bpInputErrMsg = (String) bpTextInputLayout.getError();
        if (bpInputErrMsg == null) {
            bpInputErrMsg = AppConst.CONST_BLANK;
        }
        String clearProgressInputErrMsg = (String) remainingGaugeOrDeadNotesTextInputLayout.getError();
        if (clearProgressInputErrMsg == null) {
            clearProgressInputErrMsg = AppConst.CONST_BLANK;
        }

        if (
                AppConst.CONST_BLANK.equals(exScoreInputErrMsg)
             && AppConst.CONST_BLANK.equals(bpInputErrMsg)
             && AppConst.CONST_BLANK.equals(clearProgressInputErrMsg)
        ) {
            result = false;
        } else {
            result = true;
        }

        return result;
    }

    private void doUpdate() {
        LogUtil.logEntering();

        // 編集処理
        boolean playedFlg = false;
        updateResult(playedFlg);

        // Navigation Drowerのスワイプロックを解除
        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

        backToTabFirstFragment();

        LogUtil.logExiting();
    }

    private void updateResult(boolean playedFlg) {
        LogUtil.logEntering();

        java.util.Date nowDate = new java.util.Date();

        // リザルトがない場合はインスタンスを生成しセット
        if (music.getMusicResultDBHR() == null) {
            MusicResultDBHR dbhrResult = new MusicResultDBHR();
            dbhrResult.setId(music.getId());
            dbhrResult.setName(music.getName());
            dbhrResult.setNha(music.getNha());
            dbhrResult.setInsDate(nowDate);
            music.setMusicResultDBHR(dbhrResult);
        }

        // 編集内容を取得（クリアランプ）
        String editedClearLamp;
        if (clearLampSpinner == null) {
            editedClearLamp = AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY;
        } else {
            editedClearLamp = (String) clearLampSpinner.getSelectedItem();
        }
        music.getMusicResultDBHR().setClearLamp(editedClearLamp);

        // 編集内容を取得（EXスコア）
        int editedExScore;
        if ("".equals(exScoreEditText.getText().toString())) {
            editedExScore = 0;
        } else {
            editedExScore = Integer.parseInt(exScoreEditText.getText().toString());
        }
        music.getMusicResultDBHR().setExScore(editedExScore);

        // 編集内容を取得（BP）
        int editedBp;
        if ("".equals(bpEditText.getText().toString())) {
            editedBp = 0;
        } else {
            editedBp = Integer.parseInt(bpEditText.getText().toString());
        }
        music.getMusicResultDBHR().setBp(editedBp);

        // 編集内容を取得（残ゲージor到達ノーツ数）
        int remainingGaugeOrDeadNotes;
        if ("".equals(remainingGaugeOrDeadNotesEditText.getText().toString())) {
            remainingGaugeOrDeadNotes = 0;
        } else {
            remainingGaugeOrDeadNotes = Integer.parseInt(remainingGaugeOrDeadNotesEditText.getText().toString());
        }
        music.getMusicResultDBHR().setRemainingGaugeOrDeadNotes(remainingGaugeOrDeadNotes);

        // 編集内容を取得（メモ）
        music.getMusicResultDBHR().setMemoOther(memoOtherEditText.getText().toString());

        // 各種値を算出し設定
        MusicResultUtil musicResultUtil = new MusicResultUtil();
        Map resultMap = musicResultUtil.calcRankRate(
                editedExScore,
                editedBp,
                music
        );
        music.getMusicResultDBHR().setScoreRank(
                (String) resultMap.get(MusicResultUtil.MAP_KEY_SCORE_RANK));
        music.getMusicResultDBHR().setScoreRate(
                (Double) resultMap.get(MusicResultUtil.MAP_KEY_SCORE_RATE));
        music.getMusicResultDBHR().setMissRate(
                (Double) resultMap.get(MusicResultUtil.MAP_KEY_MISS_RATE));
        music.getMusicResultDBHR().setDjPoint(
                (Double) resultMap.get(MusicResultUtil.MAP_KEY_DJPOINT));

        // updateボタンの場合のみ更新
        if (!playedFlg) {
            // 最終リザルト更新日時
            music.getMusicResultDBHR().setLastUpdateDate(nowDate);
        }

        // 最終プレイ日時
        music.getMusicResultDBHR().setUpdDate(nowDate);

        // レコードの最終更新日時
        music.getMusicResultDBHR().setUpdDate(nowDate);

        // TODO: https://blog.keiji.io/2014/02/about_fragment.html
        // TODO: 横展開
        // 曲マスタ更新
        MusicMstDao musicMstDao = getMusicMstDao(getActivity().getApplicationContext());
        musicMstDao.insertOrReplace(music);

        // リザルトテーブル更新
        MusicResultDBHRDao musicResultDBHRDao = getMusicResultDBHRDao(getActivity().getApplicationContext());
        musicResultDBHRDao.insertOrReplace(music.getMusicResultDBHR());

        // リザルト履歴テーブルINSERT
        MusicMstMaintenance musicMstMaintenance = new MusicMstMaintenance();
        musicMstMaintenance.insertMusicResultDbhrHistory(music, getActivity().getApplicationContext(), playedFlg);

        // リザルトをサーバに連携
        DbmsApiUtils.postToInsertResultApi(music, getActivity());

        // 曲マスタとリザルト履歴テーブルを結合して取得
/*
        Query<MusicMst> qc = musicMstDao.queryRawCreate(
                " LEFT JOIN " + MusicResultDBHR_HistoryDao.TABLENAME + " history"
                     + " ON T." + MusicMstDao.Properties.Name.columnName
                      + " = history." + MusicResultDBHR_HistoryDao.Properties.Name.columnName
                    + " AND T." + MusicMstDao.Properties.Nha.columnName
                      + " = history." + MusicResultDBHR_HistoryDao.Properties.Nha.columnName
              + " WHERE T." + MusicMstDao.Properties.Name.columnName + " = '" + music.getName() + "'"
                + " AND T." + MusicMstDao.Properties.Nha.columnName + " = '" + music.getNha() + "'");
        List<MusicMst> list = qc.list();
*/

        // TODO: テストソースなので未使用・・・
        List<MusicMst> list = musicMstDao.queryDeep(" WHERE T." + MusicMstDao.Properties.Name.columnName + " = '" + music.getName() + "'"
                + " AND T." + MusicMstDao.Properties.Nha.columnName + " = '" + music.getNha() + "'");

        List<MusicResultDBHR_History> historyList = list.get(0).getMusicResultDBHR_HistoryList();

        LogUtil.logExiting();
    }

    @Override
    public void onDestroyView() {
        LogUtil.logEntering();

        // タブ表示なしFragmentのonDestroyView共通処理
        ChildFragmentCommon childFragmentCommon = new ChildFragmentCommon();
        childFragmentCommon.onDestroyViewCommon((MusicListActivity) getActivity(), this);

        super.onDestroyView();

        LogUtil.logExiting();
    }

    public void backToTabFirstFragment() {
        LogUtil.logEntering();

        ((MusicListActivity)getActivity()).replaceChild(this, 0);

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

        MenuItem musicEditUpdateItem = menu.findItem(R.id.action_music_edit_update);
        musicEditUpdateItem.setVisible(true);

        LogUtil.logExiting();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtil.logEntering();

        int id = item.getItemId();

        if (id == R.id.action_music_edit_update) {

            // ※MusicListActivity#onOptionsItemSelectedメソッドの後に実施される

            // 入力エラー発生中はリジェクトしトースト表示

            if(existInputErr()) {
                new DisplayLongToastEvent().start("入力エラーのため「UPDATE」ボタンによる更新ができません。");
            } else {

                if (
                        clearLampSpinnerModFlg
                     || exScoreTextWatcher.modFlg
                     || bpTextWatcher.modFlg
                     || clearProgressTextWatcher.modFlg
                     || memoOtherTextWatcher.modFlg
                ) {
                    // 更新共通処理
                    doUpdate();
                } else {
                    // 変更がない場合
                    new DisplayLongToastEvent().start("変更がありません。");
                }
            }
        }

        LogUtil.logExiting();
        return super.onOptionsItemSelected(item);
    }

}
