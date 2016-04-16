package net.in.ahr.dbms.data.strage.mstMainte;

import android.content.Context;
import android.os.Environment;
import android.support.v4.util.SparseArrayCompat;
import android.text.TextUtils;
import android.widget.Toast;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.data.strage.util.CSVParser;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.events.musicList.ProgresDialogDismissEvent;
import net.in.ahr.dbms.others.events.musicList.ProgresDialogShowEvent;
import net.in.ahr.dbms.others.events.musicList.SearchApplyEvent;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;
import net.in.ahr.dbms.others.util.MyStrUtils;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;
import net.in.ahr.dbms.presenters.adapters.FileInfo;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;
import net.in.ahr.dbms.presenters.dialogs.FileInfoSelectionDialog;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;
import greendao.MusicResultDBHRDao;

import net.in.ahr.dbms.others.AppConst.*;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by str2653z on 2016/03/16.
 */
public class MusicMstMaintenance {

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    private static MusicResultDBHRDao getMusicResultDBHRDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicResultDBHRDao();
    }

    public void execute(Context c) {
        LogUtil.logEntering();

        // TODO: バージョンアップ時は分岐を変える必要あり
        LogUtil.logDebug("BuildConfig.VERSION_CODE:" + BuildConfig.VERSION_CODE);

        // 件数取得
        MusicMstDao musicMstDao = getMusicMstDao(c);
        long cnt = musicMstDao.count();
        LogUtil.logDebug("musicMst cnt:" + cnt);

        if ( BuildConfig.VERSION_CODE == AppConst.MUSIC_MST_MIG_VER_CD_1) {
            LogUtil.logDebug("要マスタ全件インポート");

            // バージョンに対応するマスタ件数でない場合はマスタを初期化
            if ( cnt != AppConst.MUSIC_MST_MIG_VER_CD_AFT_CNT_1 ) {
                // 全消し
                musicMstDao.deleteAll();

                // csvを読み込み
                CSVParser csvParser = new CSVParser();
                List<String[]> csvArrList = csvParser.parse(c, AppConst.MUSIC_MST_MIG_VER_CD_1_CSV_PATH, true);

                // insert用に1つ生成
//                MusicMst musicMst = new MusicMst();

                // 全件insert
                boolean headFlg = true;
//                long id = 1;
                for (String[] csvArr : csvArrList) {
                    // TODO: インスタンス生成しないとautoincrementが効かない様子、マジで？
                    MusicMst musicMst = new MusicMst();
                    if (headFlg) {
                        LogUtil.logDebug("一行目はヘッダ部なので無視");
                        headFlg = false;
                        continue;
                    }
                    csv2MusicMst(musicMst, csvArr);
//                    musicMst.setId(id);
                    musicMstDao.insertOrReplace(musicMst);
//                    id++;
                }

            } else {
                LogUtil.logDebug("MusicMstMaintenance不要");
            }
        }

        LogUtil.logExiting();
    }

    private void csv2MusicMst(MusicMst musicMst, String[] csvArr) {

        musicMst.setGenre(
                csvArr[0]);
        musicMst.setName(
                csvArr[1]);
        musicMst.setArtist(
                csvArr[2]);
        musicMst.setNha(
                csvArr[3]);
        musicMst.setVersion(
                csvArr[4]);

        String[] bpmArr = csvArr[5].split("～");
        if ( bpmArr.length == 1 ) {
            bpmArr =  new String[] {bpmArr[0], bpmArr[0]};
        }
        musicMst.setBpmFrom(
                Integer.parseInt(bpmArr[0]));
        musicMst.setBpmTo(
                Integer.parseInt(bpmArr[1]));

        musicMst.setDifficult(
                csvArr[6]);
        musicMst.setNotes(
                Integer.parseInt(csvArr[7]));
        musicMst.setScratchNotes(
                Integer.parseInt(csvArr[8]));
        musicMst.setChargeNotes(
                Integer.parseInt(csvArr[9]));
        musicMst.setBackSpinScratchNotes(
                Integer.parseInt(csvArr[10]));
        musicMst.setSortNumInDifficult(
                Integer.parseInt(csvArr[11]));
        musicMst.setMstVersion(
                csvArr[12]);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(AppConst.MUSIC_MST_CSV_DATE_FORMAT, Locale.ENGLISH);
            java.util.Date insDate = sdf.parse(csvArr[13]);

            if ( !sdf.format(insDate).equals(csvArr[13]) ) {
                LogUtil.logError("sdf.format(insDate):" + sdf.format(insDate));
                LogUtil.logError("csvArr[13]:" + csvArr[13]);
                throw new DbmsSystemException(
                        AppConst.ERR_CD_90001,
                        AppConst.ERR_STEP_CD_MUMM_00001,
                        AppConst.ERR_MESSAGE_MUMM_00001);
            }

            musicMst.setInsDate(insDate);
        } catch (ParseException pe) {
            LogUtil.logError("csvArr[13]:" + csvArr[13]);
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90001,
                    AppConst.ERR_STEP_CD_MUMM_00002,
                    AppConst.ERR_MESSAGE_MUMM_00002);
        }

        musicMst.setUpdDate(new java.util.Date());

    }

    public void exportMusicInfoToCsv(List<MusicMst> musicMstList, Context context) {
        LogUtil.logEntering();

        FileOutputStream musicMstFos = null;
//        BufferedWriter out = null;
        CSVWriter writer = null;
//        FileOutputStream musicResultDBHRFos = null;
//        CSVWriter musicResultDBHRWriter = null;
        try {
            // 処理日時を取得
            String dateStr = new SimpleDateFormat(AppConst.CONST_YMDHMS_FORMAT).format(new Date());

            // MusicMstをcsv出力
            musicMstFos = context.openFileOutput(
                    AppConst.FILENAME_PREFIX_EXPORT_CSV + dateStr + AppConst.FILENAME_SUFFIX_MUSIC_INFO_CSV,
                    Context.MODE_PRIVATE);
//            out = new BufferedWriter(new OutputStreamWriter(musicMstFos));
            writer = new CSVWriter(new PrintWriter(musicMstFos));

            // レコード（初期値はヘッダ部）
            String[] csvRecord = new String[]{
                    // MusicMst
                    AppConst.MUSICMST_KEY_NAME_ID,
                    AppConst.MUSICMST_KEY_NAME_NAME,
                    AppConst.MUSICMST_KEY_NAME_NHA,
                    AppConst.MUSICMST_KEY_NAME_VERSION,
                    AppConst.MUSICMST_KEY_NAME_GENRE,
                    AppConst.MUSICMST_KEY_NAME_ARTIST,
                    AppConst.MUSICMST_KEY_NAME_BPMFROM,
                    AppConst.MUSICMST_KEY_NAME_BPMTO,
                    AppConst.MUSICMST_KEY_NAME_DIFFICULT,
                    AppConst.MUSICMST_KEY_NAME_NOTES,
                    AppConst.MUSICMST_KEY_NAME_SCRATCHNOTES,
                    AppConst.MUSICMST_KEY_NAME_CHARGENOTES,
                    AppConst.MUSICMST_KEY_NAME_BACKSPINSCRATCHNOTES,
                    AppConst.MUSICMST_KEY_NAME_SORTNUMINDIFFICULT,
                    AppConst.MUSICMST_KEY_NAME_MSTVERSION,
                    AppConst.MUSICMST_KEY_NAME_INSDATE,
                    AppConst.MUSICMST_KEY_NAME_UPDDATE,
                    AppConst.MUSICMST_KEY_NAME_MUSICRESULTIDDBHR,
                    // MusicResultDBHR
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_ID,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_CLEARLAMP,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_EXSCORE,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_BP,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_SCORERANK,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_SCORERATE,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_MISSRATE,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_REMAININGGAUGEORDEADNOTES,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_MEMOOTHER,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_PGREAT,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_GREAT,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_GOOD,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_BAD,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_POOR,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_COMBOBREAK,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_INSDATE,
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_UPDDATE,
            };

            // ヘッダ部を書き込み
            writer.writeNext(csvRecord, true);

            SimpleDateFormat sdf = new SimpleDateFormat(AppConst.MUSIC_MST_CSV_DATE_FORMAT, Locale.ENGLISH);

            // データ部を書き込み
            for (MusicMst musicMst : musicMstList) {
                if ( musicMst.getMusicResultDBHR() == null ) {
                    csvRecord = new String[]{
                            // MusicMst
                            String.valueOf(musicMst.getId()),
                            musicMst.getName(),
                            musicMst.getNha(),
                            musicMst.getVersion(),
                            musicMst.getGenre(),
                            musicMst.getArtist(),
                            String.valueOf(musicMst.getBpmFrom()),
                            String.valueOf(musicMst.getBpmTo()),
                            musicMst.getDifficult(),
                            String.valueOf(musicMst.getNotes()),
                            String.valueOf(musicMst.getScratchNotes()),
                            String.valueOf(musicMst.getChargeNotes()),
                            String.valueOf(musicMst.getBackSpinScratchNotes()),
                            String.valueOf(musicMst.getSortNumInDifficult()),
                            musicMst.getMstVersion(),
                            sdf.format(musicMst.getInsDate()),
                            sdf.format(musicMst.getUpdDate()),
                            String.valueOf(musicMst.getMusicResultIdDBHR()),
                            // MusicResultDBHR
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK,
                            AppConst.CONST_BLANK
                    };
                } else {
                    String insDateStr = "";
                    if ( musicMst.getMusicResultDBHR().getInsDate() != null ) {
                        insDateStr = sdf.format(musicMst.getMusicResultDBHR().getInsDate());
                    }
                    String updDateStr = "";
                    if ( musicMst.getMusicResultDBHR().getUpdDate() != null ) {
                        updDateStr = sdf.format(musicMst.getMusicResultDBHR().getUpdDate());
                    }
                    csvRecord = new String[]{
                            // MusicMst
                            String.valueOf(musicMst.getId()),
                            musicMst.getName(),
                            musicMst.getNha(),
                            musicMst.getVersion(),
                            musicMst.getGenre(),
                            musicMst.getArtist(),
                            String.valueOf(musicMst.getBpmFrom()),
                            String.valueOf(musicMst.getBpmTo()),
                            musicMst.getDifficult(),
                            String.valueOf(musicMst.getNotes()),
                            String.valueOf(musicMst.getScratchNotes()),
                            String.valueOf(musicMst.getChargeNotes()),
                            String.valueOf(musicMst.getBackSpinScratchNotes()),
                            String.valueOf(musicMst.getSortNumInDifficult()),
                            musicMst.getMstVersion(),
                            sdf.format(musicMst.getInsDate()),
                            sdf.format(musicMst.getUpdDate()),
                            String.valueOf(musicMst.getMusicResultIdDBHR()),
                            // MusicResultDBHR
                            String.valueOf(musicMst.getMusicResultDBHR().getId()),
                            musicMst.getMusicResultDBHR().getClearLamp(),
                            String.valueOf(musicMst.getMusicResultDBHR().getExScore()),
                            String.valueOf(musicMst.getMusicResultDBHR().getBp()),
                            musicMst.getMusicResultDBHR().getScoreRank(),
                            String.valueOf(musicMst.getMusicResultDBHR().getScoreRate()),
                            String.valueOf(musicMst.getMusicResultDBHR().getMissRate()),
                            String.valueOf(musicMst.getMusicResultDBHR().getRemainingGaugeOrDeadNotes()),
                            musicMst.getMusicResultDBHR().getMemoOther().replaceAll("\r\n", "<br>").replaceAll("\r", "<br>").replaceAll("\n", "<br>"),
                            musicMst.getMusicResultDBHR().getPGreat(),
                            musicMst.getMusicResultDBHR().getGreat(),
                            musicMst.getMusicResultDBHR().getGood(),
                            musicMst.getMusicResultDBHR().getBad(),
                            musicMst.getMusicResultDBHR().getPoor(),
                            musicMst.getMusicResultDBHR().getComboBreak(),
                            insDateStr,
                            updDateStr
                    };
                }
                writer.writeNext(csvRecord, true);
                // ※flushしておかないと出力されないことがある
                // http://java.akjava.com/library/opencsv
                writer.flush();

            }

        } catch (FileNotFoundException fnfe) {
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90008,
                    AppConst.ERR_STEP_CD_MUMM_00003,
                    AppConst.ERR_MESSAGE_MUMM_00003);
        } catch (IOException ioe) {
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90008,
                    AppConst.ERR_STEP_CD_MUMM_00004,
                    AppConst.ERR_MESSAGE_MUMM_00004);
        } finally {
            try {
                if (musicMstFos != null) {
                    musicMstFos.close();
                }
                if (writer != null) {
                    writer.close();
                }
/*
                if (musicMstWriter != null) {
                    musicMstWriter.close();
                }
                if (musicResultDBHRFos != null) {
                    musicResultDBHRFos.close();
                }
                if (musicResultDBHRWriter != null) {
                    musicResultDBHRWriter.close();
                }
*/
            } catch (IOException ioe) {
                throw new DbmsSystemException(
                        AppConst.ERR_CD_90008,
                        AppConst.ERR_STEP_CD_MUMM_00005,
                        AppConst.ERR_MESSAGE_MUMM_00005);
            }
        }

        LogUtil.logExiting();
    }

    public void importMusicInfoFromCsv(final Context context) {
        LogUtil.logEntering();

        // リスナーのコールバックを定義
        FileInfoSelectionDialog.OnFileSelectListener listener
                = new FileInfoSelectionDialog.OnFileSelectListener() {
            @Override
            public void onFileSelect(File file) {
                LogUtil.logEntering();

                // プログレスバーを表示する
                new ProgresDialogShowEvent().start();

//                Toast.makeText(context, "BEGIN import CSV to DB...", Toast.LENGTH_LONG).show();

                // csvを読み込み
                CSVParser csvParser = new CSVParser();
                // filesフォルダからの相対パスを作成し読み込み
                String csvRelativePath = file.getPath()
                        .replace(context.getFilesDir().getAbsolutePath(), AppConst.CONST_BLANK)
                        .replace(AppConst.CONST_HALF_SLASH, AppConst.CONST_BLANK);
                List<String[]> csvArrList = csvParser.parse(context, csvRelativePath, false);

                // ヘッダ部Mapの生成
                Map<String, String> indexHeadMap = new HashMap<String, String>();

                // csvを1行づつ処理
                for (int i = 0; i < csvArrList.size(); i++) {
                    // csvレコード取得
                    String[] csvArr = csvArrList.get(i);

                    if (i == 0) {
                        // ヘッダ部のMapを作成
                        for (int j = 0; j < csvArr.length; j++) {
                            indexHeadMap.put(String.valueOf(j), csvArr[j]);
                        }
                    } else {
                        // ローカル変数
                        String pkName = "";
                        String pkNha = "";
                        MusicMst music = null;
                        MusicResultDBHR musicResultDBHR = null;

                        // DBアクセス用
                        MusicMstDao musicMstDao = getMusicMstDao(context);
                        MusicResultDBHRDao musicResultDBHRDao = getMusicResultDBHRDao(context);

                        // 日時パース用
                        SimpleDateFormat sdf = new SimpleDateFormat(AppConst.MUSIC_MST_CSV_DATE_FORMAT, Locale.ENGLISH);

                        boolean noResultFlg = false;

                        for (int j = 0; j < csvArr.length; j++) {
                            // 処理対象
                            String head = indexHeadMap.get(String.valueOf(j));
                            String value = csvArr[j];

                            // MusicMst
                            if (AppConst.MUSICMST_KEY_NAME_ID.equals(head)) {
                                // do nothing

                            } else if (AppConst.MUSICMST_KEY_NAME_NAME.equals(head)) {
                                pkName = value;

                            } else if (AppConst.MUSICMST_KEY_NAME_NHA.equals(head)) {
                                pkNha = value;

                                // この時点でキー情報が無いのは想定外
                                if (TextUtils.isEmpty(pkName) && TextUtils.isEmpty(pkNha)) {
                                    LogUtil.logError("■pkName" + pkName);
                                    LogUtil.logError("■pkNha" + pkNha);
                                    throw new DbmsSystemException(
                                            AppConst.ERR_CD_90009,
                                            AppConst.ERR_STEP_CD_MUMM_00006,
                                            AppConst.ERR_MESSAGE_MUMM_00006);
                                }

                                // MusicMst取得
                                music = getMusicMstByPk(musicMstDao, pkName, pkNha);

                            } else if (AppConst.MUSICMST_KEY_NAME_VERSION.equals(head)) {
                                music.setVersion(value);

                            } else if (AppConst.MUSICMST_KEY_NAME_GENRE.equals(head)) {
                                music.setGenre(value);

                            } else if (AppConst.MUSICMST_KEY_NAME_ARTIST.equals(head)) {
                                music.setArtist(value);

                            } else if (AppConst.MUSICMST_KEY_NAME_BPMFROM.equals(head)) {
                                music.setBpmFrom(
                                        MyStrUtils.parseIntOrRetZero(value));

                            } else if (AppConst.MUSICMST_KEY_NAME_BPMTO.equals(head)) {
                                music.setBpmTo(
                                        MyStrUtils.parseIntOrRetZero(value));

                            } else if (AppConst.MUSICMST_KEY_NAME_DIFFICULT.equals(head)) {
                                music.setDifficult(value);

                            } else if (AppConst.MUSICMST_KEY_NAME_NOTES.equals(head)) {
                                music.setNotes(
                                        MyStrUtils.parseIntOrRetZero(value));

                            } else if (AppConst.MUSICMST_KEY_NAME_SCRATCHNOTES.equals(head)) {
                                music.setScratchNotes(
                                        MyStrUtils.parseIntOrRetZero(value));

                            } else if (AppConst.MUSICMST_KEY_NAME_CHARGENOTES.equals(head)) {
                                music.setChargeNotes(
                                        MyStrUtils.parseIntOrRetZero(value));

                            } else if (AppConst.MUSICMST_KEY_NAME_BACKSPINSCRATCHNOTES.equals(head)) {
                                music.setBackSpinScratchNotes(
                                        MyStrUtils.parseIntOrRetZero(value));

                            } else if (AppConst.MUSICMST_KEY_NAME_SORTNUMINDIFFICULT.equals(head)) {
                                music.setSortNumInDifficult(
                                        MyStrUtils.parseIntOrRetZero(value));

                            } else if (AppConst.MUSICMST_KEY_NAME_MSTVERSION.equals(head)) {
                                music.setMstVersion(value);

                            } else if (AppConst.MUSICMST_KEY_NAME_INSDATE.equals(head)) {
                                if (!TextUtils.isEmpty(value)) {
                                    java.util.Date insDate;
                                    try {
                                        insDate = sdf.parse(value);
                                    } catch (ParseException pe) {
                                        throw new DbmsSystemException(
                                                AppConst.ERR_CD_90009,
                                                AppConst.ERR_STEP_CD_MUMM_00007,
                                                AppConst.ERR_MESSAGE_MUMM_00007);
                                    }
                                    music.setInsDate(insDate);
                                }

                            } else if (AppConst.MUSICMST_KEY_NAME_UPDDATE.equals(head)) {
                                if (!TextUtils.isEmpty(value)) {
                                    java.util.Date updDate;
                                    try {
                                        updDate = sdf.parse(value);
                                    } catch (ParseException pe) {
                                        throw new DbmsSystemException(
                                                AppConst.ERR_CD_90009,
                                                AppConst.ERR_STEP_CD_MUMM_00008,
                                                AppConst.ERR_MESSAGE_MUMM_00008);
                                    }
                                    music.setUpdDate(updDate);
                                }

                            } else if (AppConst.MUSICMST_KEY_NAME_MUSICRESULTIDDBHR.equals(head)) {
                                music.setMusicResultIdDBHR(
                                        Long.parseLong(value));

                            }
                            // MusicResultDBHR
                            else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_ID).equals(head)) {
                                // 空の場合はリザルトオブジェクト未設定
                                if ( AppConst.CONST_BLANK.equals(value) ) {
                                    noResultFlg = true;
                                    musicResultDBHR = new MusicResultDBHR();
                                    musicResultDBHR.setId(music.getId());
                                    music.setMusicResultDBHR(musicResultDBHR);
                                } else {
                                    noResultFlg = false;
                                    musicResultDBHR = music.getMusicResultDBHR();
                                    if (musicResultDBHR == null) {
                                        musicResultDBHR = new MusicResultDBHR();
                                    }
                                    musicResultDBHR.setId(music.getId());
                                }
                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_CLEARLAMP).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setClearLamp(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_EXSCORE).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setExScore(
                                            MyStrUtils.parseIntOrRetZero(value));
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_BP).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setBp(
                                            MyStrUtils.parseIntOrRetZero(value));
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_SCORERANK).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setScoreRank(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_SCORERATE).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setScoreRate(
                                            MyStrUtils.parseDoubleOrRetZero(value));
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_MISSRATE).equals(head)) {
                                musicResultDBHR.setMissRate(
                                        MyStrUtils.parseDoubleOrRetZero(value));

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_REMAININGGAUGEORDEADNOTES).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setRemainingGaugeOrDeadNotes(
                                            MyStrUtils.parseIntOrRetZero(value));
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_MEMOOTHER).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setMemoOther(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_PGREAT).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setPGreat(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_GREAT).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setGreat(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_GOOD).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setGood(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_BAD).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setBad(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_POOR).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setPoor(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_COMBOBREAK).equals(head)) {
                                if (!noResultFlg) {
                                    musicResultDBHR.setComboBreak(value);
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_INSDATE).equals(head)) {
                                if (!noResultFlg) {
                                    if (!TextUtils.isEmpty(value)) {
                                        java.util.Date insDate;
                                        try {
                                            insDate = sdf.parse(value);
                                        } catch (ParseException pe) {
                                            throw new DbmsSystemException(
                                                    AppConst.ERR_CD_90009,
                                                    AppConst.ERR_STEP_CD_MUMM_00007,
                                                    AppConst.ERR_MESSAGE_MUMM_00007);
                                        }
                                        musicResultDBHR.setInsDate(insDate);
                                    }
                                }

                            } else if ((AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + AppConst.MUSICRESULTDBHR_KEY_NAME_UPDDATE).equals(head)) {
                                if (!noResultFlg) {
                                    if (!TextUtils.isEmpty(value)) {
                                        java.util.Date updDate;
                                        try {
                                            updDate = sdf.parse(value);
                                        } catch (ParseException pe) {
                                            throw new DbmsSystemException(
                                                    AppConst.ERR_CD_90009,
                                                    AppConst.ERR_STEP_CD_MUMM_00008,
                                                    AppConst.ERR_MESSAGE_MUMM_00008);
                                        }
                                        musicResultDBHR.setUpdDate(updDate);
                                    }
                                }

                            }

                            // 全カラム処理完了したら更新
                            if (j == csvArr.length - 1) {
                                music.setMusicResultIdDBHR(music.getId());
                                if (!noResultFlg) {
                                    // リザルトありの場合は更新
                                    musicMstDao.insertOrReplace(music);
                                    musicResultDBHRDao.insertOrReplace(musicResultDBHR);
                                } else {
                                    // リザルトなしの場合は削除
                                    music.setMusicResultIdDBHR(0);
                                    musicMstDao.insertOrReplace(music);
                                    deleteMusicResultDbhrByPk(musicMstDao, musicResultDBHRDao, music.getName(), music.getNha());
                                }
                                LogUtil.logDebug("MusicMst:" + music.getName() + "[" + music.getNha() + "] update finish!");
                            }
                        }
                    }
                }

                // 再検索通知
                new SearchApplyEvent().start();

                // プログレスバーを閉じる
                new ProgresDialogDismissEvent().start();

                Toast.makeText(context, "END import CSV to DB...", Toast.LENGTH_LONG).show();
                LogUtil.logExiting();
            };
        };

        // ダイアログ表示
        FileInfoSelectionDialog dileDialog = new FileInfoSelectionDialog(context, listener, FileInfoSelectionDialog.MODE_IMPORT_CSV);
        dileDialog.show(new File(context.getFilesDir().getAbsolutePath()));

        LogUtil.logExiting();
    }

    /**
     * 曲名・難易度により曲マスタレコード取得
     * @param musicMstDao
     * @param name
     * @param nha
     * @return
     */
    public MusicMst getMusicMstByPk(MusicMstDao musicMstDao, String name, String nha) {
        LogUtil.logEntering();

        List<MusicMst> list = musicMstDao.queryBuilder()
                .where(MusicMstDao.Properties.Name.eq(name.replace("'", "\'")))      // .replace(",", "\\,")
                .where(MusicMstDao.Properties.Nha.eq(nha))
                .list();

        if (list.size() == 0) {
            LogUtil.logError("input.name:[" + name + "]");
            LogUtil.logError("input.nha:[" + nha + "]");
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90010,
                    AppConst.ERR_STEP_CD_MUMM_00009,
                    AppConst.ERR_MESSAGE_MUMM_00009);
        }

        return list.get(0);
    }


    /**
     * 曲名・難易度によりリザルトレコード削除（存在する場合のみ）
     * @param musicMstDao
     * @param musicResultDBHRDao
     * @param name
     * @param nha
     * @return
     */
    public void deleteMusicResultDbhrByPk(MusicMstDao musicMstDao, MusicResultDBHRDao musicResultDBHRDao, String name, String nha) {
        LogUtil.logEntering();

        List<MusicMst> list = musicMstDao.queryBuilder()
                .where(MusicMstDao.Properties.Name.eq(name.replace("'", "\'")))      // .replace(",", "\\,")
                .where(MusicMstDao.Properties.Nha.eq(nha))
                .list();

        if (list.size() != 0) {
            MusicResultDBHR musicResultDBHR = list.get(0).getMusicResultDBHR();
            if (musicResultDBHR != null) {
                musicResultDBHRDao.delete(musicResultDBHR);
                LogUtil.logDebug("musicResultDBHR delete finish:" + name + "(" + nha + ")");
            } else {
                LogUtil.logDebug("musicResultDBHR recordなし");
            }
        }

        LogUtil.logExiting();
    }

}
