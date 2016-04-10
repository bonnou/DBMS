package net.in.ahr.dbms.presenters.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
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

import java.util.List;
import java.util.Map;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;

/**
 * Created by str2653z on 2016/03/10.
 */
public class MusicEditFragment extends Fragment implements View.OnClickListener {

    private MusicMst music;
    private int musicPosition;

    private Spinner clearLampSpinner;
    private EditText exScoreEditText;
    private EditText bpEditText;
    private EditText memoProgressEditText;

    private Button editButton;
    private Button backButton;

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
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

        if (view == editButton) {
            // 編集処理
            editResult(view);

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

        // リザルト存在フラグを設定
        boolean resultExistFlg = true;
        if ( music.getMusicResultDBHR() == null ) {
            resultExistFlg = false;
        }

        // クリアランプの選択肢を設定
        // TODO: 一回設定しても再度編集を開くと「NO PLAY」になる
        clearLampSpinner = (Spinner) view.findViewById(R.id.musicEditFragment_clearLamp);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        String[] clearLumpValArr = {
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO,
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT
        };
        int selectedPosition = -1;
        for (int i = 0; i < clearLumpValArr.length; i++) {
            adapter.add(clearLumpValArr[i]);
            if (resultExistFlg) {
                if (clearLumpValArr[i].equals(music.getMusicResultDBHR().getClearLamp())) {
                    selectedPosition = i;
                }
            }
        }
        if (selectedPosition == -1) {
            selectedPosition = 0;
        }
        clearLampSpinner.setSelection(selectedPosition);
        clearLampSpinner.setAdapter(adapter);

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

        // Formオブジェクト保持、初期値設定（進捗メモ）
        memoProgressEditText = (EditText) view.findViewById(R.id.musicEditFragment_memoProgress);
        memoProgressEditText.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        if (resultExistFlg) {
            memoProgressEditText.setText(music.getMusicResultDBHR().getMemoProgress());
        }

        // 編集ボタンのクリックリスナーを設定
        editButton = (Button) view.findViewById(R.id.musicEditFragment_editButton);
        editButton.setOnClickListener(this);

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


    private void editResult(View view) {
        LogUtil.logEntering();

        // リザルトがない場合はインスタンスを生成しセット
        if (music.getMusicResultDBHR() == null) {
            MusicResultDBHR dbhrResult = new MusicResultDBHR();
            dbhrResult.setId(music.getId());
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

        // 編集内容を取得（進捗メモ）
        music.getMusicResultDBHR().setMemoProgress(memoProgressEditText.getText().toString());

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

        // TODO: https://blog.keiji.io/2014/02/about_fragment.html
        // TODO: 横展開
        // DB更新
        getMusicMstDao(getActivity()).insertOrReplace(music);


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
        Menu menu = toolbar.getMenu();
        MenuItem importGssItem = menu.findItem(R.id.action_import_gss);
        importGssItem.setVisible(true);
        MenuItem refineSearchItem = menu.findItem(R.id.action_refine_search);
        refineSearchItem.setVisible(true);

        LogUtil.logExiting();
    }
}
