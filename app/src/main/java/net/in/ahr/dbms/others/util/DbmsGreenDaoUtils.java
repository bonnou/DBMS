package net.in.ahr.dbms.others.util;

import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;

import java.util.List;

import greendao.MusicMst;
import greendao.MusicResultDBHR;

/**
 * Created by str2653z on 2016/04/16.
 */
public class DbmsGreenDaoUtils {

    /**
     * 文字列リストをIN句パラメータ形式に変換。
     * @param strList
     * @return
     */
    public static String inStatementStr(List<String> strList) {
        LogUtil.logEntering();

        StringBuilder sb = new StringBuilder();
        for (String str : strList) {
            sb.append(AppConst.CONST_HALF_QUOTE);
            sb.append(str);
            sb.append(AppConst.CONST_HALF_QUOTE);
            sb.append(AppConst.CONST_HALF_COMMA);
        }
        String result = sb.toString();
        if (result.length() > 0) {
            result = result.substring(0, result.length()-1);
        }

        LogUtil.logExiting();
        return result;
    }

    public MusicResultDBHR deepCopyMusicResultDBHR(MusicResultDBHR src) {

        MusicResultDBHR copy;

        if (src == null) {
            copy = null;

        } else {
            copy = new MusicResultDBHR(
                    src.getId(),
                    src.getName(),
                    src.getNha(),
                    src.getClearLamp(),
                    src.getExScore(),
                    src.getBp(),
                    src.getScoreRank(),
                    src.getScoreRate(),
                    src.getMissRate(),
                    src.getTag(),
                    src.getFav(),
                    src.getClearLamp_DBR(),
                    src.getClearLamp_DBRR(),
                    src.getClearLamp_DBM(),
                    src.getClearLamp_DBSR(),
                    src.getClearLamp_DBM_NONAS(),
                    src.getClearLamp_RH(),
                    src.getClearLamp_LH(),
                    src.getMyDifficult(),
                    src.getDjPoint(),
                    src.getClearProgressRate(),
                    src.getLastPlayDate(),
                    src.getLastUpdateDate(),
                    src.getRemainingGaugeOrDeadNotes(),
                    src.getMemoOther(),
                    src.getPGreat(),
                    src.getGreat(),
                    src.getGood(),
                    src.getBad(),
                    src.getPoor(),
                    src.getComboBreak(),
                    src.getInsDate(),
                    src.getUpdDate()
            );

        }

        return copy;
    }

    public MusicMst deepCopyMusicMst(MusicMst src) {

        MusicMst copy;

        if (src == null) {
            copy = null;

        } else {
            copy = new MusicMst(
                    src.getId(),
                    src.getName(),
                    src.getNha(),
                    src.getVersion(),
                    src.getGenre(),
                    src.getArtist(),
                    src.getBpmFrom(),
                    src.getBpmTo(),
                    src.getDifficult(),
                    src.getNotes(),
                    src.getScratchNotes(),
                    src.getChargeNotes(),
                    src.getBackSpinScratchNotes(),
                    src.getSortNumInDifficult(),
                    src.getMstVersion(),
                    src.getInsDate(),
                    src.getUpdDate(),
                    src.getMusicResultIdDBHR()
            );

            MusicResultDBHR copyResult = deepCopyMusicResultDBHR(src.getMusicResultDBHR());
            copy.setMusicResultDBHR(copyResult);

        }

        return copy;
    }

}
