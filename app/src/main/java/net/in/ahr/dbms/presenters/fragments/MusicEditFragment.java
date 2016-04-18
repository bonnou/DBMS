package net.in.ahr.dbms.presenters.fragments;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.business.usecases.result.MusicResultUtil;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;

import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;
import greendao.MusicResultDBHRDao;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicEditFragment extends Fragment implements View.OnClickListener {

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

    private TextView genreTextView;
    private TextView artistTextView;
    private TextView versionTextView;
    private TextView bpmTextView;
    private TextView difficultTextView;
    private TextView notesDbTextView;
    private TextView chargeNotesTextView;

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
    }

    @Override
    public void onClick(View view) {
        LogUtil.logEntering();

        if (view == updateButton) {
            // 編集処理
            updateResult(view);

            // Navigation Drowerを非表示ロック
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // バックスタックのフラグメントをポップ
            getFragmentManager().popBackStack();

        } else if (view == backButton) {
            // Navigation Drowerを非表示ロック
            DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
            drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // バックスタックのフラグメントをポップ
            getFragmentManager().popBackStack();
        }

        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        LogUtil.logExiting();
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
        // クリアランプコード値配列
        String[] clearLumpValArr = AppConst.CLEAR_LUMP_VAL_ARR;
        // 残ゲージor到達ノーツ数ラベル配列
        String[] remainingGaugeOrDeadNotesLabelArr = AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ARR;
        // 残ゲージor到達ノーツ数ラベル
        String remainingGaugeOrDeadNotesLabel = AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL__NO_PLAY;
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
                String remainingGaugeOrDeadNotesLabel = AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL__NO_PLAY;
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
                Html.fromHtml(remainingGaugeOrDeadNotesLabel + AppConst.CONST_HALF_COLON + AppConst.CONST_HALF_SPACE));

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

/*
        // バックキー押下時
        view.setFocusableInTouchMode(true);
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                LogUtil.logDebug("event.getKeyCode():" + event.getKeyCode());
                LogUtil.logDebug("event.getAction():" + event.getAction());
                if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        getFragmentManager().popBackStack();
                        LogUtil.logDebug("testtest");
                        return true;
                    } else {
                        return true;
                    }
                }
                return false;
            }
        });

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });
*/

        // マスタ情報その1
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

        // フラグメント用にメニューを変更
        // TODO: フラグメント専用のメニューを追加する場合はどうしよう
        initToolbar();
//        getActivity().invalidateOptionsMenu();

/*
        toolbar.inflateMenu(R.menu.menu_music_edit);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.action_debug_crash) {
                    throw new RuntimeException("action_debug_crash");
                }
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
*/

        return view;
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


        // 更新内容でリストビューを再描画（選択箇所のみ）
        // ※行わない場合、スクロールで外して再表示しないと更新内容が曲一覧側で見れない
        ((MusicListFragment)getActivity().getFragmentManager().findFragmentByTag(MusicListFragment.TAG))
                .updateListView(musicPosition);

        LogUtil.logExiting();
    }

    @Override
    public void onDestroyView() {
        revertToolbar();
        super.onDestroyView();
    }

    /**
     * 他画面からの遷移時にツールバーの内容を編集
     */
    private void initToolbar() {
        LogUtil.logEntering();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_EDIT);
//        toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        Menu menu = toolbar.getMenu();
        MenuItem importGssItem = menu.findItem(R.id.action_import_gss);
        importGssItem.setVisible(false);
        MenuItem refineSearchItem = menu.findItem(R.id.action_refine_search);
        refineSearchItem.setVisible(false);

        // TODO: refineSearch中の場合にSearch部分が残ったまま画面遷移してしまう

        LogUtil.logExiting();
    }

    /**
     * 他画面への遷移時にツールバーの内容を編集
     */
    private void revertToolbar() {
        LogUtil.logEntering();

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle(AppConst.TOOLBAR_TITLE_MUSIC_LIST);
//            toolbar.setTitleTextColor(Color.parseColor("#ffffff"));
        Menu menu = toolbar.getMenu();
        MenuItem importGssItem = menu.findItem(R.id.action_import_gss);
        importGssItem.setVisible(true);
        MenuItem refineSearchItem = menu.findItem(R.id.action_refine_search);
        refineSearchItem.setVisible(true);

        LogUtil.logExiting();
    }
}
