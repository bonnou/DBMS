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

    public Map calcRankRate (int exScore, int bp, MusicMst musicMst) {
        LogUtil.logEntering();

        // 満点を算出
        int maxScore = ( musicMst.getNotes() - musicMst.getScratchNotes() ) * 2 * 2
                       + musicMst.getChargeNotes() * 2 * 4;
        // 全ノーツ数を算出
        int notes = ( musicMst.getNotes() - musicMst.getScratchNotes() ) * 2
                    + musicMst.getChargeNotes() * 2;

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
        } else {
            scoreRank = AppConst.MUSIC_MST_SCORE_RANK_VAL_F;
        }

        // スコアレートを判定
        double scoreRate = (double) exScore / (double) maxScore * 100;

        // ミスレートを判定
        double missRate =  (double) bp / (double) notes * 100;

        Map resultMap = new HashMap();
        resultMap.put(MAP_KEY_SCORE_RANK, scoreRank);
        resultMap.put(MAP_KEY_SCORE_RATE, new Double(scoreRate));
        resultMap.put(MAP_KEY_MISS_RATE, new Double(missRate));

        LogUtil.logExiting();
        return resultMap;
    }
}
