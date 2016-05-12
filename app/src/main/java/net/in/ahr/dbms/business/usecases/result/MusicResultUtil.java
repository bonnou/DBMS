package net.in.ahr.dbms.business.usecases.result;

import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;

import java.util.Map;
import java.util.HashMap;

import greendao.MusicMst;

/**
 * Created by str2653z on 2016/03/27.
 */
public class MusicResultUtil {

    public static final String MAP_KEY_SCORE_RANK = "scoreRank";
    public static final String MAP_KEY_SCORE_RATE = "scoreRate";
    public static final String MAP_KEY_MISS_RATE = "missRate";
    public static final String MAP_KEY_DJPOINT = "djPoint";

    public static int retMaxScore(MusicMst musicMst) {
        LogUtil.logEntering();

        int notes = ( musicMst.getNotes() - musicMst.getScratchNotes() ) * 2 * 2
                + musicMst.getChargeNotes() * 2 * 4;

        LogUtil.logExiting();
        return notes;
    }

    public static int retMaxNotes(MusicMst musicMst) {
        LogUtil.logEntering();

        int notes = ( musicMst.getNotes() - musicMst.getScratchNotes() ) * 2
                + musicMst.getChargeNotes() * 2;

        LogUtil.logExiting();
        return notes;
    }

    /**
     * scoreRank、scoreRate、missRate、djPointを算出
     * ※musicMst.musicResultIdDBHR.clearLampに値が設定済であること。
     * @param exScore
     * @param bp
     * @param musicMst
     * @return
     */
    public static Map calcRankRate(int exScore, int bp, MusicMst musicMst) {
        LogUtil.logEntering();

        // 満点を算出
        int maxScore = retMaxScore(musicMst);
        // 全ノーツ数を算出
        int notes = retMaxNotes(musicMst);

        // スコアランクを判定
        String scoreRank;
        if ( exScore > maxScore / 9 * 8 ) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_AAA;
        } else if ( exScore > maxScore / 9 * 7 ) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_AA;
        } else if ( exScore > maxScore / 9 * 6 ) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_A;
        } else if ( exScore > maxScore / 9 * 5 ) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_B;
        } else if ( exScore > maxScore / 9 * 4 ) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_C;
        } else if ( exScore > maxScore / 9 * 3 ) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_D;
        } else if ( exScore > maxScore / 9 * 2 ) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_E;
        } else if (exScore != 0) {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_F;
        } else {
            scoreRank = null;
        }

        // スコアレートを判定
        double scoreRate = (double) exScore / (double) maxScore * 100;

        // ミスレートを判定
        double missRate =  (double) bp / (double) notes * 100;

        // djPointを算出
        // ※http://bemaniwiki.com/index.php?beatmania%20IIDX%2022%20PENDUAL%2F%B1%A3%A4%B7%CD%D7%C1%C7#i96ac82e
/*
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY           = "NO PLAY";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY          = "FAR AWAY";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_FAILED            = "FAILED";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR      = "ASSIST CLEAR";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR = "ASSIST EASY CLEAR";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR        = "EASY CLEAR";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR      = "NORMAL CLEAR";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR        = "HARD CLEAR";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR      = "EXHARD CLEAR";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO        = "FULL COMBO";
        public static final String MUSIC_MST_CLEAR_LAMP_VAL_PERFECT           = "PERFECT";
*/

        // djPointのクリアマークボーナス
        String clearLamp;
        if ( musicMst.getMusicResultDBHR() == null ) {
            clearLamp = "";
        } else {
            clearLamp = musicMst.getMusicResultDBHR().getClearLamp();
        }
        int clearLampBonus;
        if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR.equals(clearLamp) ) {
            clearLampBonus = 5;
        } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR.equals(clearLamp) ) {
            clearLampBonus = 10;
        } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR.equals(clearLamp) ) {
            clearLampBonus = 20;
        } else if ( AppConst.MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR.equals(clearLamp) ) {
            clearLampBonus = 25;
        } else if (
                AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO.equals(clearLamp)
             || AppConst.MUSIC_MST_CLEAR_LAMP_VAL_PERFECT.equals(clearLamp)
        ) {
            clearLampBonus = 30;
        } else {
            clearLampBonus = 0;
        }

        // djPointのDJ LEVELボーナス
        int djLevelBonus;
        if ( AppConst.MUSIC_MST_SCORE_RANK_VAL_A.equals(scoreRank) ) {
            djLevelBonus = 10;
        } else if ( AppConst.MUSIC_MST_SCORE_RANK_VAL_AA.equals(scoreRank) ) {
            djLevelBonus = 15;
        } else if ( AppConst.MUSIC_MST_SCORE_RANK_VAL_AAA.equals(scoreRank) ) {
            djLevelBonus = 20;
        } else {
            djLevelBonus = 0;
        }

        double djPoint = (double) exScore * (double) (100 + clearLampBonus + djLevelBonus) / (double) 10000;

        LogUtil.logDebug("■exScore:" + exScore);
        LogUtil.logDebug("■clearLampBonus:" + clearLampBonus);
        LogUtil.logDebug("■djLevelBonus:" + djLevelBonus);
        LogUtil.logDebug("■djPoint:" + djPoint);

        Map resultMap = new HashMap();
        resultMap.put(MAP_KEY_SCORE_RANK, scoreRank);
        resultMap.put(MAP_KEY_SCORE_RATE, new Double(scoreRate));
        resultMap.put(MAP_KEY_MISS_RATE, new Double(missRate));
        resultMap.put(MAP_KEY_DJPOINT, new Double(djPoint));

        LogUtil.logExiting();
        return resultMap;
    }
}
