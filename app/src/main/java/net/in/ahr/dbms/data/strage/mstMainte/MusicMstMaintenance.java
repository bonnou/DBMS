package net.in.ahr.dbms.data.strage.mstMainte;

import android.content.Context;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.data.strage.util.CSVParser;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.CustomApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import greendao.MusicMst;
import greendao.MusicMstDao;

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
        if ( BuildConfig.VERSION_CODE == MUSIC_MST_MIG_VER_CD_1 ) {
            // 件数取得
            MusicMstDao musicMstDao = getMusicMstDao(c);
            long cnt = musicMstDao.count();
            LogUtil.logDebug("musicMst cnt:" + cnt);

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

        String[] bpmArr = csvArr[4].split("～");
        if ( bpmArr.length == 1 ) {
            bpmArr =  new String[] {bpmArr[0], bpmArr[0]};
        }
        musicMst.setBpmFrom(
                Integer.parseInt(bpmArr[0]));
        musicMst.setBpmTo(
                Integer.parseInt(bpmArr[1]));

        musicMst.setDifficult(
                csvArr[5]);
        musicMst.setNotes(
                Integer.parseInt(csvArr[6]));
        musicMst.setScratchNotes(
                Integer.parseInt(csvArr[7]));
        musicMst.setChargeNotes(
                Integer.parseInt(csvArr[8]));
        musicMst.setBackSpinScratchNotes(
                Integer.parseInt(csvArr[9]));
        musicMst.setSortNumInDifficult(
                Integer.parseInt(csvArr[10]));
        musicMst.setMstVersion(
                csvArr[11]);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM d HH:mm:ss 'JST' yyyy");
            java.util.Date insDate = sdf.parse(csvArr[12]);

            if ( !sdf.format(insDate).equals(csvArr[12]) ) {
                throw new IllegalStateException("SimpleDateFormatのフォーマット指定不正");
            }

            musicMst.setInsDate(insDate);
        } catch (ParseException pe) {
            // TODO:適切な例外処理は？
            pe.printStackTrace();
        }

    }
}
