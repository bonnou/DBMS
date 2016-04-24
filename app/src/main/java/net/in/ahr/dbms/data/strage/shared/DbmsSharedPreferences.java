package net.in.ahr.dbms.data.strage.shared;

import android.content.SharedPreferences;

import net.in.ahr.dbms.others.AppConst;

/**
 * Created by str2653z on 2016/04/10.
 */
public class DbmsSharedPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public DbmsSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public DbmsSharedPreferences edit() {
        editor = sharedPreferences.edit();
        return this;
    }

    public void apply() {
        editor.apply();
        editor = null;
    }


    // 検索条件: 難易度10
    public boolean getSearchConfDiff_10() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_10, true);
    }

    public DbmsSharedPreferences putSearchConfDiff_10(boolean searchConfDiff_10) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_10, searchConfDiff_10);
        return this;
    }

    // 検索条件: 難易度11
    public boolean getSearchConfDiff_11() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_11, true);
    }

    public DbmsSharedPreferences putSearchConfDiff_11(boolean searchConfDiff_11) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_DIFFICULT_11, searchConfDiff_11);
        return this;
    }


    // 検索条件: NO PLAY
    public boolean getSearchConfClearLamp_NO_PLAY() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NO_PLAY, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_NO_PLAY(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NO_PLAY, val);
        return this;
    }

    // 検索条件: FAR AWAY
    public boolean getSearchConfClearLamp_FAR_AWAY() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAR_AWAY, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_FAR_AWAY(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAR_AWAY, val);
        return this;
    }

    // 検索条件: FAILED
    public boolean getSearchConfClearLamp_FAILED() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAILED, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_FAILED(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAILED, val);
        return this;
    }

    // 検索条件: ASSIST CLEAR
    public boolean getSearchConfClearLamp_ASSIST_CLEAR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_CLEAR, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_ASSIST_CLEAR(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_CLEAR, val);
        return this;
    }

    // 検索条件: ASSIST EASY CLEAR
    public boolean getSearchConfClearLamp_ASSIST_EASY_CLEAR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_EASY_CLEAR, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_ASSIST_EASY_CLEAR(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_EASY_CLEAR, val);
        return this;
    }

    // 検索条件: EASY CLEAR
    public boolean getSearchConfClearLamp_EASY_CLEAR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EASY_CLEAR, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_EASY_CLEAR(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EASY_CLEAR, val);
        return this;
    }

    // 検索条件: NORMAL CLEAR
    public boolean getSearchConfClearLamp_NORMAL_CLEAR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NORMAL_CLEAR, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_NORMAL_CLEAR(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NORMAL_CLEAR, val);
        return this;
    }

    // 検索条件: HARD CLEAR
    public boolean getSearchConfClearLamp_HARD_CLEAR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_HARD_CLEAR, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_HARD_CLEAR(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_HARD_CLEAR, val);
        return this;
    }

    // 検索条件: EXHARD CLEAR
    public boolean getSearchConfClearLamp_EXHARD_CLEAR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EXHARD_CLEAR, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_EXHARD_CLEAR(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EXHARD_CLEAR, val);
        return this;
    }

    // 検索条件: FULL COMBO
    public boolean getSearchConfClearLamp_FULL_COMBO() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FULL_COMBO, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_FULL_COMBO(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FULL_COMBO, val);
        return this;
    }

    // 検索条件: PERFECT
    public boolean getSearchConfClearLamp_PERFECT() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_PERFECT, true);
    }

    public DbmsSharedPreferences putSearchConfClearLamp_PERFECT(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_PERFECT, val);
        return this;
    }


    // 検索条件: AAA
    public boolean getSearchConfScoreRank_AAA() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_AAA, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_AAA(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_AAA, val);
        return this;
    }

    // 検索条件: AA
    public boolean getSearchConfScoreRank_AA() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_AA, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_AA(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_AA, val);
        return this;
    }

    // 検索条件: A
    public boolean getSearchConfScoreRank_A() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_A, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_A(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_A, val);
        return this;
    }

    // 検索条件: B
    public boolean getSearchConfScoreRank_B() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_B, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_B(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_B, val);
        return this;
    }

    // 検索条件: C
    public boolean getSearchConfScoreRank_C() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_C, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_C(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_C, val);
        return this;
    }

    // 検索条件: D
    public boolean getSearchConfScoreRank_D() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_D, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_D(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_D, val);
        return this;
    }

    // 検索条件: E
    public boolean getSearchConfScoreRank_E() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_E, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_E(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_E, val);
        return this;
    }

    // 検索条件: F
    public boolean getSearchConfScoreRank_F() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_F, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_F(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_F, val);
        return this;
    }

    // 検索条件: NO RANK
    public boolean getSearchConfScoreRank_NO_RANK() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_NO_RANK, true);
    }

    public DbmsSharedPreferences putSearchConfScoreRank_NO_RANK(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_SCORE_RANK_NO_RANK, val);
        return this;
    }


    // 検索条件: BPM LLL〜129
    public boolean getSearchConfBpmRange_LLL_129() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_LLL_129, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_LLL_129(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_LLL_129, val);
        return this;
    }

    // 検索条件: BPM 130〜139
    public boolean getSearchConfBpmRange_130_139() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_130_139, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_130_139(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_130_139, val);
        return this;
    }

    // 検索条件: BPM 140〜149
    public boolean getSearchConfBpmRange_140_149() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_140_149, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_140_149(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_140_149, val);
        return this;
    }

    // 検索条件: BPM 150〜159
    public boolean getSearchConfBpmRange_150_159() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_150_159, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_150_159(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_150_159, val);
        return this;
    }

    // 検索条件: BPM 160〜169
    public boolean getSearchConfBpmRange_160_169() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_160_169, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_160_169(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_160_169, val);
        return this;
    }

    // 検索条件: BPM 170〜179
    public boolean getSearchConfBpmRange_170_179() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_170_179, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_170_179(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_170_179, val);
        return this;
    }

    // 検索条件: BPM 180〜189
    public boolean getSearchConfBpmRange_180_189() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_180_189, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_180_189(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_180_189, val);
        return this;
    }

    // 検索条件: BPM 190〜199
    public boolean getSearchConfBpmRange_190_199() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_190_199, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_190_199(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_190_199, val);
        return this;
    }

    // 検索条件: BPM 200〜
    public boolean getSearchConfBpmRange_200_HHH() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_200_HHH, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_200_HHH(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_200_HHH, val);
        return this;
    }

    // 検索条件: ソフラン曲
    public boolean getSearchConfBpmRange_SOFLAN() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_SOFLAN, true);
    }

    public DbmsSharedPreferences putSearchConfBpmRange_SOFLAN(boolean val) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_BPM_RANGE_SOFLAN, val);
        return this;
    }


    // 検索条件: 1st style
    public boolean getSearchConfVersion_1st() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_1ST, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_1st(boolean searchConfVersion_1st) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_1ST, searchConfVersion_1st);
        return this;
    }

    // 検索条件: substream
    public boolean getSearchConfVersion_sub() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SUB, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_sub(boolean searchConfVersion_sub) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SUB, searchConfVersion_sub);
        return this;
    }

    // 検索条件: 2nd style
    public boolean getSearchConfVersion_2nd() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_2ND, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_2nd(boolean searchConfVersion_2nd) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_2ND, searchConfVersion_2nd);
        return this;
    }

    // 検索条件: 3rd style
    public boolean getSearchConfVersion_3rd() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_3RD, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_3rd(boolean searchConfVersion_3rd) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_3RD, searchConfVersion_3rd);
        return this;
    }

    // 検索条件: 4th style
    public boolean getSearchConfVersion_4th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_4TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_4th(boolean searchConfVersion_4th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_4TH, searchConfVersion_4th);
        return this;
    }

    // 検索条件: 5th style
    public boolean getSearchConfVersion_5th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_5TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_5th(boolean searchConfVersion_5th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_5TH, searchConfVersion_5th);
        return this;
    }

    // 検索条件: 6th style
    public boolean getSearchConfVersion_6th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_6TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_6th(boolean searchConfVersion_6th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_6TH, searchConfVersion_6th);
        return this;
    }

    // 検索条件: 7th style
    public boolean getSearchConfVersion_7th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_7TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_7th(boolean searchConfVersion_7th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_7TH, searchConfVersion_7th);
        return this;
    }

    // 検索条件: 8th style
    public boolean getSearchConfVersion_8th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_8TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_8th(boolean searchConfVersion_8th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_8TH, searchConfVersion_8th);
        return this;
    }

    // 検索条件: 9th style
    public boolean getSearchConfVersion_9th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_9TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_9th(boolean searchConfVersion_9th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_9TH, searchConfVersion_9th);
        return this;
    }

    // 検索条件: 10th style
    public boolean getSearchConfVersion_10th() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_10TH, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_10th(boolean searchConfVersion_10th) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_10TH, searchConfVersion_10th);
        return this;
    }

    // 検索条件: IIDX RED
    public boolean getSearchConfVersion_RED() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RED, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_RED(boolean searchConfVersion_RED) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RED, searchConfVersion_RED);
        return this;
    }

    // 検索条件: HAPPY SKY
    public boolean getSearchConfVersion_SKY() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SKY, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_SKY(boolean searchConfVersion_SKY) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SKY, searchConfVersion_SKY);
        return this;
    }

    // 検索条件: DistorteD
    public boolean getSearchConfVersion_DD() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DD, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_DD(boolean searchConfVersion_DD) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DD, searchConfVersion_DD);
        return this;
    }

    // 検索条件: GOLD
    public boolean getSearchConfVersion_GOLD() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_GOLD, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_GOLD(boolean searchConfVersion_GOLD) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_GOLD, searchConfVersion_GOLD);
        return this;
    }

    // 検索条件: DJ TROOPERS
    public boolean getSearchConfVersion_DJT() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DJT, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_DJT(boolean searchConfVersion_DJT) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_DJT, searchConfVersion_DJT);
        return this;
    }

    // 検索条件: EMPRESS
    public boolean getSearchConfVersion_EMP() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_EMP, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_EMP(boolean searchConfVersion_EMP) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_EMP, searchConfVersion_EMP);
        return this;
    }

    // 検索条件: SIRIUS
    public boolean getSearchConfVersion_SIR() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SIR, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_SIR(boolean searchConfVersion_SIR) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SIR, searchConfVersion_SIR);
        return this;
    }

    // 検索条件: Resort Anthem
    public boolean getSearchConfVersion_RA() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RA, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_RA(boolean searchConfVersion_RA) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_RA, searchConfVersion_RA);
        return this;
    }

    // 検索条件: Lincle
    public boolean getSearchConfVersion_LC() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_LC, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_LC(boolean searchConfVersion_LC) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_LC, searchConfVersion_LC);
        return this;
    }

    // 検索条件: tricoro
    public boolean getSearchConfVersion_tri() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_TRI, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_tri(boolean searchConfVersion_tri) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_TRI, searchConfVersion_tri);
        return this;
    }

    // 検索条件: SPADA
    public boolean getSearchConfVersion_SPA() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SPA, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_SPA(boolean searchConfVersion_SPA) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_SPA, searchConfVersion_SPA);
        return this;
    }

    // 検索条件: PENDUAL
    public boolean getSearchConfVersion_PEN() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_PEN , true);
    }

    public DbmsSharedPreferences putSearchConfVersion_PEN(boolean searchConfVersion_PEN) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_PEN, searchConfVersion_PEN);
        return this;
    }

    // copula
    public boolean getSearchConfVersion_cop() {
        return sharedPreferences.getBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_COP, true);
    }

    public DbmsSharedPreferences putSearchConfVersion_cop(boolean searchConfVersion_cop) {
        editor.putBoolean(AppConst.SHARED_KEY_SEARCH_CONF_VERSION_COP, searchConfVersion_cop);
        return this;
    }


    // ORDER BY（TARGET）
    public String getSearchOrderByTarget() {
        return sharedPreferences.getString(AppConst.SHARED_KEY_SEARCH_ORDER_BY_TARGET, AppConst.SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME);
    }

    public DbmsSharedPreferences putSearchOrderByTarget(String val) {
        editor.putString(AppConst.SHARED_KEY_SEARCH_ORDER_BY_TARGET, val);
        return this;
    }

    // ORDER BY（ASC、DESC）
    public String getSearchOrderSortKind() {
        return sharedPreferences.getString(AppConst.SHARED_KEY_SEARCH_ORDER_SORT_KIND, AppConst.SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC);
    }

    public DbmsSharedPreferences putSearchOrderSortKind(String val) {
        editor.putString(AppConst.SHARED_KEY_SEARCH_ORDER_SORT_KIND, val);
        return this;
    }



}
