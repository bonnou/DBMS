package net.in.ahr.dbms.others;

import net.in.ahr.dbms.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by str2653z on 2016/03/19.
 */
public class AppConst {

    /*********************************************************************************
     * バージョン管理・マイグレーション
     *********************************************************************************/
    public static final int MUSIC_MST_MIG_VER_CD_1 = 1;
    public static final int MUSIC_MST_MIG_VER_CD_AFT_CNT_1 = 744;

    /*********************************************************************************
     * 難易度
     *********************************************************************************/
    public static final String MUSIC_MST_NHA_VAL_NORMAL  = "NORMAL";
    public static final String MUSIC_MST_NHA_VAL_HYPER   = "HYPER";
    public static final String MUSIC_MST_NHA_VAL_ANOTHER = "ANOTHER";

    /*********************************************************************************
     * NORMAL・HYPER・ANOTHER
     *********************************************************************************/
    public static final String MUSIC_MST_DIFFICULT_VAL_10 = "10";
    public static final String MUSIC_MST_DIFFICULT_VAL_11 = "11";

    /*********************************************************************************
     * スコアランク
     *********************************************************************************/
    public static final String MUSIC_MST_SCORE_RANK_VAL_AAA = "AAA";
    public static final String MUSIC_MST_SCORE_RANK_VAL_AA = "AA";
    public static final String MUSIC_MST_SCORE_RANK_VAL_A = "A";
    public static final String MUSIC_MST_SCORE_RANK_VAL_B = "B";
    public static final String MUSIC_MST_SCORE_RANK_VAL_C = "C";
    public static final String MUSIC_MST_SCORE_RANK_VAL_D = "D";
    public static final String MUSIC_MST_SCORE_RANK_VAL_E = "E";
    public static final String MUSIC_MST_SCORE_RANK_VAL_F = "F";

    /*********************************************************************************
     * クリアランプ
     *********************************************************************************/
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY= "NO PLAY";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_FAILED = "FAILED";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR = "ASSIST CLEAR";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR = "ASSIST EASY CLEAR";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR = "EASY CLEAR";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR = "NORMAL CLEAR";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR = "HARD CLEAR";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR = "EXHARD CLEAR";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO = "FULL COMBO";
    public static final String MUSIC_MST_CLEAR_LAMP_VAL_PERFECT = "PERFECT";

    /*********************************************************************************
     * バージョン
     *********************************************************************************/
    public static final String MUSIC_MST_VERSION_VAL_1ST  = "1st";
    public static final String MUSIC_MST_VERSION_VAL_SUB  = "sub";
    public static final String MUSIC_MST_VERSION_VAL_2ND  = "2nd";
    public static final String MUSIC_MST_VERSION_VAL_3RD  = "3rd";
    public static final String MUSIC_MST_VERSION_VAL_4TH  = "4th";
    public static final String MUSIC_MST_VERSION_VAL_5TH  = "5th";
    public static final String MUSIC_MST_VERSION_VAL_6TH  = "6th";
    public static final String MUSIC_MST_VERSION_VAL_7TH  = "7th";
    public static final String MUSIC_MST_VERSION_VAL_8TH  = "8th";
    public static final String MUSIC_MST_VERSION_VAL_9TH  = "9th";
    public static final String MUSIC_MST_VERSION_VAL_10TH = "10th";
    public static final String MUSIC_MST_VERSION_VAL_RED  = "RED";
    public static final String MUSIC_MST_VERSION_VAL_SKY  = "SKY";
    public static final String MUSIC_MST_VERSION_VAL_DD   = "DD";
    public static final String MUSIC_MST_VERSION_VAL_GOLD = "GOLD";
    public static final String MUSIC_MST_VERSION_VAL_DJT  = "DJT";
    public static final String MUSIC_MST_VERSION_VAL_EMP  = "EMP";
    public static final String MUSIC_MST_VERSION_VAL_SIR  = "SIR";
    public static final String MUSIC_MST_VERSION_VAL_RA   = "RA";
    public static final String MUSIC_MST_VERSION_VAL_LC   = "LC";
    public static final String MUSIC_MST_VERSION_VAL_TRI  = "tri";
    public static final String MUSIC_MST_VERSION_VAL_SPA  = "SPA";
    public static final String MUSIC_MST_VERSION_VAL_PEN  = "PEN";
    public static final String MUSIC_MST_VERSION_VAL_COP  = "cop";

    public static final String CONST_VERSION_FULL_1ST  = "1st style";
    public static final String CONST_VERSION_FULL_SUB  = "substream";
    public static final String CONST_VERSION_FULL_2ND  = "2nd style";
    public static final String CONST_VERSION_FULL_3RD  = "3rd style";
    public static final String CONST_VERSION_FULL_4TH  = "4th style";
    public static final String CONST_VERSION_FULL_5TH  = "5th style";
    public static final String CONST_VERSION_FULL_6TH  = "6th style";
    public static final String CONST_VERSION_FULL_7TH  = "7th style";
    public static final String CONST_VERSION_FULL_8TH  = "8th style";
    public static final String CONST_VERSION_FULL_9TH  = "9th style";
    public static final String CONST_VERSION_FULL_10TH = "10th style";
    public static final String CONST_VERSION_FULL_RED  = "IIDX RED";
    public static final String CONST_VERSION_FULL_SKY  = "HAPPY SKY";
    public static final String CONST_VERSION_FULL_DD   = "DistorteD";
    public static final String CONST_VERSION_FULL_GOLD = "GOLD";
    public static final String CONST_VERSION_FULL_DJT  = "DJ TROOPERS";
    public static final String CONST_VERSION_FULL_EMP  = "EMPRESS";
    public static final String CONST_VERSION_FULL_SIR  = "SIRIUS";
    public static final String CONST_VERSION_FULL_RA   = "Resort Anthem";
    public static final String CONST_VERSION_FULL_LC   = "Lincle";
    public static final String CONST_VERSION_FULL_TRI  = "tricoro";
    public static final String CONST_VERSION_FULL_SPA  = "SPADA";
    public static final String CONST_VERSION_FULL_PEN  = "PENDUAL";
    public static final String CONST_VERSION_FULL_COP  = "copula";

    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_1ST  = new Integer(R.id.nav_where_version_1st);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SUB  = new Integer(R.id.nav_where_version_sub);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_2ND  = new Integer(R.id.nav_where_version_2nd);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_3RD  = new Integer(R.id.nav_where_version_3rd);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_4TH  = new Integer(R.id.nav_where_version_4th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_5TH  = new Integer(R.id.nav_where_version_5th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_6TH  = new Integer(R.id.nav_where_version_6th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_7TH  = new Integer(R.id.nav_where_version_7th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_8TH  = new Integer(R.id.nav_where_version_8th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_9TH  = new Integer(R.id.nav_where_version_9th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_10TH = new Integer(R.id.nav_where_version_10th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_RED  = new Integer(R.id.nav_where_version_RED);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SKY  = new Integer(R.id.nav_where_version_SKY);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_DD   = new Integer(R.id.nav_where_version_DD);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_GOLD = new Integer(R.id.nav_where_version_GOLD);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_DJT  = new Integer(R.id.nav_where_version_DJT);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_EMP  = new Integer(R.id.nav_where_version_EMP);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SIR  = new Integer(R.id.nav_where_version_SIR);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_RA   = new Integer(R.id.nav_where_version_RA);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_LC   = new Integer(R.id.nav_where_version_LC);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_tri  = new Integer(R.id.nav_where_version_tri);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SPA  = new Integer(R.id.nav_where_version_SPA);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_PEN  = new Integer(R.id.nav_where_version_PEN);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_cop  = new Integer(R.id.nav_where_version_cop);

    private static Map<Integer, String> versionIdValMap = new HashMap<Integer, String>();

    public static Map<Integer, String> getVersionIdValMap() {
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_1ST ,  MUSIC_MST_VERSION_VAL_1ST);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_SUB ,  MUSIC_MST_VERSION_VAL_SUB);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_2ND ,  MUSIC_MST_VERSION_VAL_2ND);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_3RD ,  MUSIC_MST_VERSION_VAL_3RD);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_4TH ,  MUSIC_MST_VERSION_VAL_4TH);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_5TH ,  MUSIC_MST_VERSION_VAL_5TH);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_6TH ,  MUSIC_MST_VERSION_VAL_6TH);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_7TH ,  MUSIC_MST_VERSION_VAL_7TH);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_8TH ,  MUSIC_MST_VERSION_VAL_8TH);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_9TH ,  MUSIC_MST_VERSION_VAL_9TH);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_10TH,  MUSIC_MST_VERSION_VAL_10TH);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_RED ,  MUSIC_MST_VERSION_VAL_RED);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_SKY ,  MUSIC_MST_VERSION_VAL_SKY);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_DD  ,  MUSIC_MST_VERSION_VAL_DD);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_GOLD,  MUSIC_MST_VERSION_VAL_GOLD);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_DJT ,  MUSIC_MST_VERSION_VAL_DJT);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_EMP ,  MUSIC_MST_VERSION_VAL_EMP);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_SIR ,  MUSIC_MST_VERSION_VAL_SIR);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_RA  ,  MUSIC_MST_VERSION_VAL_RA);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_LC  ,  MUSIC_MST_VERSION_VAL_LC);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_tri ,  MUSIC_MST_VERSION_VAL_TRI);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_SPA ,  MUSIC_MST_VERSION_VAL_SPA);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_PEN ,  MUSIC_MST_VERSION_VAL_PEN);
        versionIdValMap.put(INTEGER_ID_NAV_WHERE_VERSION_cop ,  MUSIC_MST_VERSION_VAL_COP);
        return versionIdValMap;
    }

    /*********************************************************************************
     * SharedPreferencesキー（WHERE／ORDER BY）
     *********************************************************************************/
    public static final String SHARED_KEY_SEARCH_CONF_DIFFICULT_10 = "SHARED_KEY_SEARCH_CONF_DIFF_10";
    public static final String SHARED_KEY_SEARCH_CONF_DIFFICULT_11 = "SHARED_KEY_SEARCH_CONF_DIFF_11";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_1ST  = "SHARED_KEY_SEARCH_CONF_VERSION_1ST";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_SUB  = "SHARED_KEY_SEARCH_CONF_VERSION_SUB";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_2ND  = "SHARED_KEY_SEARCH_CONF_VERSION_2ND";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_3RD  = "SHARED_KEY_SEARCH_CONF_VERSION_3RD";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_4TH  = "SHARED_KEY_SEARCH_CONF_VERSION_4TH";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_5TH  = "SHARED_KEY_SEARCH_CONF_VERSION_5TH";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_6TH  = "SHARED_KEY_SEARCH_CONF_VERSION_6TH";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_7TH  = "SHARED_KEY_SEARCH_CONF_VERSION_7TH";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_8TH  = "SHARED_KEY_SEARCH_CONF_VERSION_8TH";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_9TH  = "SHARED_KEY_SEARCH_CONF_VERSION_9TH";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_10TH = "SHARED_KEY_SEARCH_CONF_VERSION_10TH";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_RED  = "SHARED_KEY_SEARCH_CONF_VERSION_RED";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_SKY  = "SHARED_KEY_SEARCH_CONF_VERSION_SKY";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_DD   = "SHARED_KEY_SEARCH_CONF_VERSION_DD";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_GOLD = "SHARED_KEY_SEARCH_CONF_VERSION_GOLD";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_DJT  = "SHARED_KEY_SEARCH_CONF_VERSION_DJT";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_EMP  = "SHARED_KEY_SEARCH_CONF_VERSION_EMP";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_SIR  = "SHARED_KEY_SEARCH_CONF_VERSION_SIR";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_RA   = "SHARED_KEY_SEARCH_CONF_VERSION_RA";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_LC   = "SHARED_KEY_SEARCH_CONF_VERSION_LC";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_TRI  = "SHARED_KEY_SEARCH_CONF_VERSION_tri";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_SPA  = "SHARED_KEY_SEARCH_CONF_VERSION_SPA";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_PEN  = "SHARED_KEY_SEARCH_CONF_VERSION_PEN";
    public static final String SHARED_KEY_SEARCH_CONF_VERSION_COP  = "SHARED_KEY_SEARCH_CONF_VERSION_cop";

    /*********************************************************************************
     * Toolbarタイトル
     *********************************************************************************/

    /** MusicList */
    public static final String TOOLBAR_TITLE_MUSIC_LIST  = "DBHR Search Result";

    /** MusicEdit */
    public static final String TOOLBAR_TITLE_MUSIC_EDIT  = "Edit Result";

    /*********************************************************************************
     * 例外情報（errCd）
     *********************************************************************************/

    /** クラッシュ発生処理（デバッグ用） */
    public static final String ERR_CD_90000 = "DBMS-90000";

    /** SimpleDateFormat関連エラー */
    public static final String ERR_CD_90001 = "DBMS-90001";

    /** Googleスプレッドシートインポート処理にて予期せぬ関連エラー発生 */
    public static final String ERR_CD_90002 = "DBMS-90002";

    /** Googleスプレッドシートインポート処理にてP12ファイル読み込みエラー発生 */
    public static final String ERR_CD_90003 = "DBMS-90003";

    /** csv読み込み時エラー */
    public static final String ERR_CD_90004 = "DBMS-90004";

    /** リフレクション処理時エラー */
    public static final String ERR_CD_90005 = "DBMS-90005";

    /** 画像読み込み時エラー */
    public static final String ERR_CD_90006 = "DBMS-90006";

    /** Googleスプレッドシートインポート処理にてキー情報不足エラー */
    public static final String ERR_CD_90007 = "DBMS-90007";

    /*********************************************************************************
     * 例外情報（errStepCd、errMsg）
     *********************************************************************************/
    public static final String ERR_STEP_CD_MLAC_00001 = "MLAC-00001";
    public static final String ERR_MESSAGE_MLAC_00001 = "NavigationDrawerのメニュー押下時リフレクションエラー";

    public static final String ERR_STEP_CD_MEAC_00001 = "MEAC-00001";
    public static final String ERR_MESSAGE_MEAC_00001 = "クラッシュ発生処理（デバッグ用）";

    public static final String ERR_STEP_CD_MUMM_00001 = "MUMM-00001";
    public static final String ERR_MESSAGE_MUMM_00001 = "SimpleDateFormatのフォーマット指定不整合";

    public static final String ERR_STEP_CD_MUMM_00002 = "MUMM-00002";
    public static final String ERR_MESSAGE_MUMM_00002 = "SimpleDateFormatのフォーマット時エラー";

    public static final String ERR_STEP_CD_GSSI_00001 = "GSSI-00001";
    public static final String ERR_MESSAGE_GSSI_00001 = "Googleスプレッドシートインポート処理にて予期せぬ関連エラー発生";

    public static final String ERR_STEP_CD_GSSI_00002 = "GSSI-00002";
    public static final String ERR_MESSAGE_GSSI_00002 = "Googleスプレッドシートインポート処理にてP12ファイル読み込み時エラー";

    public static final String ERR_STEP_CD_GSSI_00003 = "GSSI-00003";
    public static final String ERR_MESSAGE_GSSI_00003 = "Googleスプレッドシートインポート処理にてファイルクローズエラー";

    public static final String ERR_STEP_CD_GSSI_00004 = "GSSI-00004";
    public static final String ERR_MESSAGE_GSSI_00004 = "Googleスプレッドシートインポート処理にてキー情報不足エラー";

    public static final String ERR_STEP_CD_UTIL_00001 = "UTIL-00001";
    public static final String ERR_MESSAGE_UTIL_00001 = "csv読み込み時エラー";

    public static final String ERR_STEP_CD_UTIL_00002 = "UTIL-00002";
    public static final String ERR_MESSAGE_UTIL_00002 = "リフレクション時エラー";

    public static final String ERR_STEP_CD_UTIL_00003 = "UTIL-00003";
    public static final String ERR_MESSAGE_UTIL_00003 = "画像読み込み時エラー";

    public static final String ERR_STEP_CD_UTIL_00004 = "UTIL-00004";
    public static final String ERR_MESSAGE_UTIL_00004 = "画像読み込み処理にてファイルクローズエラー";


}
