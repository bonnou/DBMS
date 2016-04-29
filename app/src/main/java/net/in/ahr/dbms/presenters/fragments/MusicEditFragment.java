package net.in.ahr.dbms.presenters.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.SearchView;
import android.text.Html;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.business.usecases.result.MusicResultUtil;
import net.in.ahr.dbms.data.strage.shared.DbmsSharedPreferences;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.fragments.common.ChildFragmentCommon;
import net.in.ahr.dbms.presenters.tabManagers.BaseFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;
import greendao.MusicResultDBHRDao;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicEditFragment extends BaseFragment implements View.OnClickListener {

    public static final String TAG = "MusicEditFragment";

    private MusicMst music;
    private int musicPosition;

    private Spinner clearLampSpinner;
    private static int selectedPosition = -1;

    private TextView nameNhaTextView;

    private EditText exScoreEditText;
    private EditText bpEditText;
    private TextView remainingGaugeOrDeadNotesTextView;
    private EditText remainingGaugeOrDeadNotesEditText;
    private EditText memoOtherEditText;

    private Button updateButton;
    private Button backButton;

    private TextView updatedTextView;

    private TextView genreTextView;
    private TextView artistTextView;
    private TextView versionTextView;
    private TextView bpmTextView;
    private TextView difficultTextView;
    private TextView notesDbTextView;
    private TextView chargeNotesTextView;

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

        // タブ表示なしFragmentのonCreate共通処理
        ChildFragmentCommon childFragmentCommon = new ChildFragmentCommon();
        childFragmentCommon.onCreateCommon((MusicListActivity) getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_music_edit, container, false);

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
        String[] clearLumpValArr = (String[]) clearLumpValCustomList.toArray(new String[0]);

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
                remainingGaugeOrDeadNotesTextView.setText(
                        Html.fromHtml(remainingGaugeOrDeadNotesLabel + AppConst.CONST_HALF_COLON + AppConst.CONST_HALF_SPACE));
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

        // 残ゲージor到達ノーツ数ラベル
        remainingGaugeOrDeadNotesTextView = (TextView) view.findViewById(R.id.musicEditFragment_remainingGaugeOrDeadNotesLabel);
        remainingGaugeOrDeadNotesTextView.setText(
                remainingGaugeOrDeadNotesLabel + AppConst.CONST_HALF_COLON + AppConst.CONST_HALF_SPACE);

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

        // Formオブジェクト保持、初期値設定（メモ）
        memoOtherEditText = (EditText) view.findViewById(R.id.musicEditFragment_memoOther);
        memoOtherEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        if (resultExistFlg) {
            memoOtherEditText.setText(music.getMusicResultDBHR().getMemoOther());
        }

        // 編集ボタンのクリックリスナーを設定
        updateButton = (Button) view.findViewById(R.id.musicEditFragment_updateButton);
        updateButton.setOnClickListener(this);

        // 戻るボタンのクリックリスナーを設定
        backButton = (Button) view.findViewById(R.id.musicEditFragment_backButton);
        backButton.setOnClickListener(this);

        // リザルト情報
        // ジャンル
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

        return view;
    }

    @Override
    public void onClick(View view) {
        LogUtil.logEntering();

        if (view == updateButton) {
            // 編集処理
            updateResult(view);

            // Navigation Drowerのスワイプロックを解除
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            backToTabFirstFragment();

        } else if (view == backButton) {
            // Navigation Drowerのスワイプロックを解除
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            backToTabFirstFragment();

        }

//        // TODO: これなんだっけ・・・？アプリ上のソフトキーボード制御？
//        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        LogUtil.logExiting();
    }

    private void updateResult(View view) {
        LogUtil.logEntering();

        java.util.Date nowDate = new java.util.Date();

        // リザルトがない場合はインスタンスを生成しセット
        if (music.getMusicResultDBHR() == null) {
            MusicResultDBHR dbhrResult = new MusicResultDBHR();
            dbhrResult.setId(music.getId());
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
        if ( "".equals(exScoreEditText.getText().toString()) ) {
            editedExScore = 0;
        } else {
            editedExScore = Integer.parseInt(exScoreEditText.getText().toString());
        }
        music.getMusicResultDBHR().setExScore(editedExScore);

        // 編集内容を取得（BP）
        int editedBp;
        if ( "".equals(bpEditText.getText().toString()) ) {
            editedBp = 0;
        } else {
            editedBp = Integer.parseInt(bpEditText.getText().toString());
        }
        music.getMusicResultDBHR().setBp(editedBp);

        // 編集内容を取得（残ゲージor到達ノーツ数）
        int remainingGaugeOrDeadNotes;
        if ( "".equals(remainingGaugeOrDeadNotesEditText.getText().toString()) ) {
            remainingGaugeOrDeadNotes = 0;
        } else {
            remainingGaugeOrDeadNotes = Integer.parseInt(remainingGaugeOrDeadNotesEditText.getText().toString());
        }
        music.getMusicResultDBHR().setRemainingGaugeOrDeadNotes(remainingGaugeOrDeadNotes);

        // 編集内容を取得（メモ）
        music.getMusicResultDBHR().setMemoOther(memoOtherEditText.getText().toString());

        // スコアランク、スコア率、BP率を算出し設定
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

        music.getMusicResultDBHR().setUpdDate(nowDate);

        // TODO: https://blog.keiji.io/2014/02/about_fragment.html
        // TODO: 横展開
        // DB更新
        getMusicMstDao(getActivity().getApplicationContext()).insertOrReplace(music);
        getMusicResultDBHRDao(getActivity().getApplicationContext()).insertOrReplace(music.getMusicResultDBHR());

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

    private void backToTabFirstFragment() {
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

        LogUtil.logExiting();
    }

}
