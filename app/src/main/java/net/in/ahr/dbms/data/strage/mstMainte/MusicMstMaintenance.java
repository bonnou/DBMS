package net.in.ahr.dbms.data.strage.mstMainte;

import android.content.Context;

import com.opencsv.CSVWriter;
import com.opencsv.bean.BeanToCsv;
import com.opencsv.bean.ColumnPositionMappingStrategy;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.data.strage.util.CSVParser;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;

import static net.in.ahr.dbms.others.AppConst.*;

/**
 * Created by str2653z on 2016/03/16.
 */
public class MusicMstMaintenance {

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    public void execute(Context c) {
        LogUtil.logEntering();

        // TODO: バージョンアップ時は分岐を変える必要あり
        LogUtil.logDebug("BuildConfig.VERSION_CODE:" + BuildConfig.VERSION_CODE);

        // 件数取得
        MusicMstDao musicMstDao = getMusicMstDao(c);
        long cnt = musicMstDao.count();
        LogUtil.logDebug("musicMst cnt:" + cnt);

        if ( BuildConfig.VERSION_CODE == MUSIC_MST_MIG_VER_CD_1) {
            LogUtil.logDebug("要マスタ全件インポート");

            // バージョンに対応するマスタ件数でない場合はマスタを初期化
            if ( cnt != MUSIC_MST_MIG_VER_CD_AFT_CNT_1 ) {
                // 全消し
                musicMstDao.deleteAll();

                // csvを読み込み
                CSVParser csvParser = new CSVParser();
                List<String[]> csvArrList = csvParser.parse(c, "csv/musicMst/musicMst_0001.csv");

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

    public void exportMusicInfoCsv(List<MusicMst> musicMstList, Context context) {
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
                    "id",
                    "name",
                    "nha",
                    "version",
                    "genre",
                    "artist",
                    "bpmFrom",
                    "bpmTo",
                    "difficult",
                    "notes",
                    "scratchNotes",
                    "chargeNotes",
                    "backSpinScratchNotes",
                    "sortNumInDifficult",
                    "mstVersion",
                    "insDate",
                    "updDate",
                    "musicResultIdDBHR",
                    // MusicResultDBHR
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "id",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "clearLamp",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "exScore",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "bp",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "scoreRank",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "scoreRate",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "missRate",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "remainingGaugeOrDeadNotes",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "memoOther",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "pGreat",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "great",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "good",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "bad",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "poor",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "comboBreak",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "insDate",
                    AppConst.CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR + "updDate"
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
                            musicMst.getMusicResultDBHR().getMemoOther(),
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
            }
/*
            musicMstStrat.setColumnMapping(musicMstColumns);
            BeanToCsv musicMsteanToCsv = new BeanToCsv();
            musicMsteanToCsv.write(musicMstStrat, musicMstWriter, musicMstList);

            // MusicMstリスト件数分のMusicResultDBHRリストを作成（nullの場合はデフォルト値で生成）
            List<MusicResultDBHR> musicResultDBHRList = new ArrayList<MusicResultDBHR>();
            for (MusicMst musicMst : musicMstList) {
                if ( musicMst.getMusicResultDBHR() != null ) {
                    musicResultDBHRList.add( musicMst.getMusicResultDBHR() );
                } else {
                    // nullの場合のデフォルトオブジェクト
                    MusicResultDBHR defaultMusicResultDBHR = new MusicResultDBHR();
                    defaultMusicResultDBHR.setId(musicMst.getId());
                    defaultMusicResultDBHR.setClearLamp(AppConst.CONST_BLANK);     // インポート時のnull判断はクリアランプ空文字で実施
                    defaultMusicResultDBHR.setExScore(0);
                    defaultMusicResultDBHR.setBp(0);
                    defaultMusicResultDBHR.setScoreRank(AppConst.CONST_BLANK);
                    defaultMusicResultDBHR.setScoreRate(0.0);
                    defaultMusicResultDBHR.setMissRate(0.0);
                    defaultMusicResultDBHR.setRemainingGaugeOrDeadNotes(0);
                    defaultMusicResultDBHR.setMemoOther(AppConst.CONST_BLANK);
                    defaultMusicResultDBHR.setPGreat("");
                    defaultMusicResultDBHR.setGreat("");
                    defaultMusicResultDBHR.setGood("");
                    defaultMusicResultDBHR.setBad("");
                    defaultMusicResultDBHR.setPoor("");
                    defaultMusicResultDBHR.setComboBreak("");
                    defaultMusicResultDBHR.setInsDate(new Date());
                    defaultMusicResultDBHR.setUpdDate(new Date());
                    musicResultDBHRList.add(defaultMusicResultDBHR);
                }
            }

            // MusicMstをcsv出力
            musicResultDBHRFos = context.openFileOutput(
                    AppConst.FILENAME_PREFIX_EXPORT_CSV + dateStr + AppConst.FILENAME_SUFFIX_MUSIC_RESULT_DBHR_CSV,
                    Context.MODE_PRIVATE);
            musicResultDBHRWriter = new CSVWriter(new PrintWriter(musicResultDBHRFos));
            ColumnPositionMappingStrategy<MusicResultDBHR> musicResultDBHRStrat = new ColumnPositionMappingStrategy<MusicResultDBHR>();
            String[] musicResultDBHRColumns = new String[]{

            };
            musicResultDBHRStrat.setColumnMapping(musicResultDBHRColumns);
            BeanToCsv musicResultDBHRBeanToCsv = new BeanToCsv();
            musicResultDBHRBeanToCsv.write(musicResultDBHRStrat, musicResultDBHRWriter, musicResultDBHRList);

*/


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
}
