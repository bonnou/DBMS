package net.in.ahr.dbms.others;

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
