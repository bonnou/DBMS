package net.in.ahr.dbms.others;

import net.in.ahr.dbms.R;

import java.util.HashMap;
import java.util.Map;

import greendao.MusicResultDBHRDao;

/**
 * Created by str2653z on 2016/03/19.
 */
public class AppConst {

    /*********************************************************************************
     * アプリ情報
     *********************************************************************************/
    public static final String APP_PACKAGE_NAME = "net.in.ahr.dbms";

    /*********************************************************************************
     * バージョン管理・マイグレーション
     *********************************************************************************/
    public static final int MUSIC_MST_MIG_VER_CD_1 = 1;
    public static String MUSIC_MST_MIG_VER_CD_1_CSV_PATH = "csv/musicMst/musicMst_0001.csv"; // TODO:
    public static final int MUSIC_MST_MIG_VER_CD_AFT_CNT_1 = 744;

    /*********************************************************************************
     * APIパス(BuildConfig.DBMS_ONLINE_PATHの最後の"/"の後から)
     *********************************************************************************/
    /** CSVエクスポートファイル名サフィックス：MUSIC_MST */
    public static final String DBMS_ONLINE_API_PATH_INSERT_RESULT = "api/insertResult";
    public static final String DBMS_ONLINE_API_PATH_INSERT_ALL_RESULTS = "api/insertAllResults";
    public static final String DBMS_ONLINE_API_PATH_GET_ALL_RESULT = "api/getAllResult";
    public static final String DBMS_ONLINE_API_PATH_GET_RANKING_BY_NAME_NHA = "api/getRankingByNameNha";
    public static final String DBMS_ONLINE_PAGE_PATH_LOGIN = "login";


    public static final int DBMS_ONLINE_API_TIMEOUT_MSEC = 10000;

    /*********************************************************************************
     * キー名（MUSIC_MST）
     *********************************************************************************/
    public static final String MUSICMST_KEY_NAME_ID = "id";
    public static final String MUSICMST_KEY_NAME_NAME = "name";
    public static final String MUSICMST_KEY_NAME_NHA = "nha";
    public static final String MUSICMST_KEY_NAME_VERSION = "version";
    public static final String MUSICMST_KEY_NAME_GENRE = "genre";
    public static final String MUSICMST_KEY_NAME_ARTIST = "artist";
    public static final String MUSICMST_KEY_NAME_BPMFROM = "bpmFrom";
    public static final String MUSICMST_KEY_NAME_BPMTO = "bpmTo";
    public static final String MUSICMST_KEY_NAME_DIFFICULT = "difficult";
    public static final String MUSICMST_KEY_NAME_NOTES = "notes";
    public static final String MUSICMST_KEY_NAME_SCRATCHNOTES = "scratchNotes";
    public static final String MUSICMST_KEY_NAME_CHARGENOTES = "chargeNotes";
    public static final String MUSICMST_KEY_NAME_BACKSPINSCRATCHNOTES = "backSpinScratchNotes";
    public static final String MUSICMST_KEY_NAME_SORTNUMINDIFFICULT = "sortNumInDifficult";
    public static final String MUSICMST_KEY_NAME_MSTVERSION = "mstVersion";
    public static final String MUSICMST_KEY_NAME_INSDATE = "insDate";
    public static final String MUSICMST_KEY_NAME_UPDDATE = "updDate";
    public static final String MUSICMST_KEY_NAME_MUSICRESULTIDDBHR = "musicResultIdDBHR";

    /*********************************************************************************
     * キー名（MUSIC_RESULT_DBHR）
     *********************************************************************************/
    public static final String MUSICRESULTDBHR_KEY_NAME_ID = "id";
    public static final String MUSICRESULTDBHR_KEY_NAME_CLEARLAMP = "clearLamp";
    public static final String MUSICRESULTDBHR_KEY_NAME_EXSCORE = "exScore";
    public static final String MUSICRESULTDBHR_KEY_NAME_BP = "bp";
    public static final String MUSICRESULTDBHR_KEY_NAME_SCORERANK = "scoreRank";
    public static final String MUSICRESULTDBHR_KEY_NAME_SCORERATE = "scoreRate";
    public static final String MUSICRESULTDBHR_KEY_NAME_MISSRATE = "missRate";
    public static final String MUSICRESULTDBHR_KEY_NAME_REMAININGGAUGEORDEADNOTES = "remainingGaugeOrDeadNotes";
    public static final String MUSICRESULTDBHR_KEY_NAME_MEMOOTHER = "memoOther";
    public static final String MUSICRESULTDBHR_KEY_NAME_PGREAT = "pGreat";
    public static final String MUSICRESULTDBHR_KEY_NAME_GREAT = "great";
    public static final String MUSICRESULTDBHR_KEY_NAME_GOOD = "good";
    public static final String MUSICRESULTDBHR_KEY_NAME_BAD = "bad";
    public static final String MUSICRESULTDBHR_KEY_NAME_POOR = "poor";
    public static final String MUSICRESULTDBHR_KEY_NAME_COMBOBREAK = "comboBreak";
    public static final String MUSICRESULTDBHR_KEY_NAME_INSDATE = "insDate";
    public static final String MUSICRESULTDBHR_KEY_NAME_UPDDATE = "updDate";



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

    public static final String[] CLEAR_LUMP_VAL_ARR = {
            AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY,
            AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY,
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

    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_NO_PLAY= "EASY残ゲージ(%)";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_FAR_AWAY = "EASY残ゲージ(%)";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_FAILED = "EASY残ゲージ(%)";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ASSIST_CLEAR = "EASY残ゲージ(%)";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ASSIST_EASY_CLEAR = "EASY残ゲージ(%)";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_EASY_CLEAR = "NORMAL残ゲージ(%)";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_NORMAL_CLEAR = "HARD到達ノーツ数";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_HARD_CLEAR = "EXHARD到達ノーツ数";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_EXHARD_CLEAR = "COMBO BREAK";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_FULL_COMBO = "GOOD数";
    public static final String REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_PERFECT = "GREAT数";

    public static final String[] REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ARR = {
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_NO_PLAY,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_FAR_AWAY,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_FAILED,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ASSIST_CLEAR,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_ASSIST_EASY_CLEAR,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_EASY_CLEAR,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_NORMAL_CLEAR,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_HARD_CLEAR,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_EXHARD_CLEAR,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_FULL_COMBO,
            AppConst.REMAINING_GAUGE_OR_DEAD_NOTES_LABEL_PERFECT
    };

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

    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_1ST  = Integer.valueOf(R.id.nav_where_version_1st);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SUB  = Integer.valueOf(R.id.nav_where_version_sub);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_2ND  = Integer.valueOf(R.id.nav_where_version_2nd);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_3RD  = Integer.valueOf(R.id.nav_where_version_3rd);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_4TH  = Integer.valueOf(R.id.nav_where_version_4th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_5TH  = Integer.valueOf(R.id.nav_where_version_5th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_6TH  = Integer.valueOf(R.id.nav_where_version_6th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_7TH  = Integer.valueOf(R.id.nav_where_version_7th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_8TH  = Integer.valueOf(R.id.nav_where_version_8th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_9TH  = Integer.valueOf(R.id.nav_where_version_9th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_10TH = Integer.valueOf(R.id.nav_where_version_10th);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_RED  = Integer.valueOf(R.id.nav_where_version_RED);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SKY  = Integer.valueOf(R.id.nav_where_version_SKY);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_DD   = Integer.valueOf(R.id.nav_where_version_DD);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_GOLD = Integer.valueOf(R.id.nav_where_version_GOLD);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_DJT  = Integer.valueOf(R.id.nav_where_version_DJT);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_EMP  = Integer.valueOf(R.id.nav_where_version_EMP);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SIR  = Integer.valueOf(R.id.nav_where_version_SIR);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_RA   = Integer.valueOf(R.id.nav_where_version_RA);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_LC   = Integer.valueOf(R.id.nav_where_version_LC);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_tri  = Integer.valueOf(R.id.nav_where_version_tri);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_SPA  = Integer.valueOf(R.id.nav_where_version_SPA);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_PEN  = Integer.valueOf(R.id.nav_where_version_PEN);
    private static final Integer INTEGER_ID_NAV_WHERE_VERSION_cop  = Integer.valueOf(R.id.nav_where_version_cop);

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
     * SharedPreferencesキー（WHERE）：難易度
     *********************************************************************************/
    public static final String SHARED_KEY_SEARCH_CONF_DIFFICULT_10 = "SHARED_KEY_SEARCH_CONF_DIFF_10";
    public static final String SHARED_KEY_SEARCH_CONF_DIFFICULT_11 = "SHARED_KEY_SEARCH_CONF_DIFF_11";

    /*********************************************************************************
     * SharedPreferencesキー（WHERE）：クリアランプ
     *********************************************************************************/
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NO_PLAY           = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NO_PLAY";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAR_AWAY          = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAR_AWAY";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAILED            = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FAILED";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_CLEAR      = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_CLEAR";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_EASY_CLEAR = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_ASSIST_EASY_CLEAR";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EASY_CLEAR        = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EASY_CLEAR";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NORMAL_CLEAR      = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_NORMAL_CLEAR";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_HARD_CLEAR        = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_HARD_CLEAR";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EXHARD_CLEAR      = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_EXHARD_CLEAR";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FULL_COMBO        = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_FULL_COMBO";
    public static final String SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_PERFECT           = "SHARED_KEY_SEARCH_CONF_CLEAR_LAMP_PERFECT";

    /*********************************************************************************
     * SharedPreferencesキー（WHERE）：スコアランク
     *********************************************************************************/
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_AAA     = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_AAA";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_AA      = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_AA";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_A       = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_A";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_B       = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_B";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_C       = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_C";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_D       = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_D";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_E       = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_E";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_F       = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_F";
    public static final String SHARED_KEY_SEARCH_CONF_SCORE_RANK_NO_RANK = "SHARED_KEY_SEARCH_CONF_SCORE_RANK_NO_RANK";

    /*********************************************************************************
     * SharedPreferencesキー（WHERE）：BPM範囲
     *********************************************************************************/
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_LLL_129 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_LLL_129";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_130_139 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_130_139";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_140_149 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_140_149";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_150_159 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_150_159";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_160_169 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_160_169";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_170_179 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_170_179";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_180_189 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_180_189";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_190_199 = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_190_199";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_200_HHH = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_200_HHH";
    public static final String SHARED_KEY_SEARCH_CONF_BPM_RANGE_SOFLAN  = "SHARED_KEY_SEARCH_CONF_BPM_RANGE_SOFLAN";

    /*********************************************************************************
     * SharedPreferencesキー（WHERE）：バージョン
     *********************************************************************************/
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
     * SharedPreferencesキー（ORDER BY）
     *********************************************************************************/
    public static final String SHARED_KEY_SEARCH_ORDER_BY_TARGET  = "SHARED_KEY_SEARCH_ORDER_BY_TARGET";
    public static final String SHARED_KEY_SEARCH_ORDER_SORT_KIND  = "SHARED_KEY_SEARCH_ORDER_SORT_KIND";

    /*********************************************************************************
     * SharedPreferencesバリュー（ORDER BY）
     *********************************************************************************/
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME  = "SHARED_VALUE_SEARCH_ORDER_BY_DIFFICULT_NAME";
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_NAME            = "SHARED_VALUE_SEARCH_ORDER_BY_NAME";
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_CLEARLAMP       = "SHARED_VALUE_SEARCH_ORDER_BY_CLEARLAMP";
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_EXSCORE         = "SHARED_VALUE_SEARCH_ORDER_BY_EXSCORE";
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_BP              = "SHARED_VALUE_SEARCH_ORDER_BY_BP";
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_SCORE_RATE      = "SHARED_VALUE_SEARCH_ORDER_BY_SCORE_RATE";
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_MISS_RATE       = "SHARED_VALUE_SEARCH_ORDER_BY_MISS_RATE";
    public static final String SHARED_VALUE_SEARCH_ORDER_BY_UPDATED         = "SHARED_VALUE_SEARCH_ORDER_BY_UPDATED";
//    public static final String SHARED_VALUE_SEARCH_ORDER_BY_CLEAR_PROGRESS  = "SHARED_VALUE_SEARCH_ORDER_BY_CLEAR_PROGRESS";
    public static final String SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC  = "SHARED_VALUE_SEARCH_ORDER_SORT_KIND_ASC";
    public static final String SHARED_VALUE_SEARCH_ORDER_SORT_KIND_DESC = "SHARED_VALUE_SEARCH_ORDER_SORT_KIND_DESC";

    /*********************************************************************************
     * SharedPreferencesキー（設定画面）
     *********************************************************************************/
    public static final String SHARED_KEY_SETTING_SELECTABLE_CLEAR_LAMP = "SHARED_KEY_SETTING_SELECTABLE_CLEAR_LAMP";

    /*********************************************************************************
     * SharedPreferencesキー（処理中）
     *********************************************************************************/
    public static final String SHARED_KEY_PROC_SESSION_ID_COOKIE_NAME = "SHARED_KEY_PROC_SESSION_ID_COOKIE_NAME";
    public static final String SHARED_KEY_PROC_SESSION_ID_COOKIE_VALUE = "SHARED_KEY_PROC_SESSION_ID_COOKIE_VALUE";

    /*********************************************************************************
     * ViewPagerのindex
     *********************************************************************************/
    public static int CONST_VIEW_PAGER_INDEX_0_MUSIC_LIST = 0;
    public static int CONST_VIEW_PAGER_INDEX_1_MUSIC_HISTORY = 1;

    /*********************************************************************************
     * SQL
     *********************************************************************************/

    // ORDER BYでクリアランプをソート
    public static final String CONST_SQL_ORDER_BY_CLEAR_LAMP_CASE
            = " case T0." + MusicResultDBHRDao.Properties.ClearLamp.columnName
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY           + "' then 0 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_FAR_AWAY          + "' then 1 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_FAILED            + "' then 2 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_CLEAR      + "' then 3 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_ASSIST_EASY_CLEAR + "' then 4 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_EASY_CLEAR        + "' then 5 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_NORMAL_CLEAR      + "' then 6 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_HARD_CLEAR        + "' then 7 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_EXHARD_CLEAR      + "' then 8 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_FULL_COMBO        + "' then 9 "
            + " when '" + MUSIC_MST_CLEAR_LAMP_VAL_PERFECT           + "' then 10 end ";

    /*********************************************************************************
     * Toolbarタイトル
     *********************************************************************************/

    /** MusicList */
    public static final String TOOLBAR_TITLE_MUSIC_LIST  = "DBHR Search Result";

    /** MusicEdit */
    public static final String TOOLBAR_TITLE_MUSIC_EDIT  = "Edit Result";

    /** MusicHistoryList */
    public static final String TOOLBAR_TITLE_MUSIC_HISTORY_LIST  = "Update History";

    /** DbmsSetting */
    public static final String TOOLBAR_TITLE_SETTINGS  = "Settings";

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

    /** CSVエクスポート処理にてエラー発生 */
    public static final String ERR_CD_90008 = "DBMS-90008";

    /** CSVインポート処理にてエラー発生 */
    public static final String ERR_CD_90009 = "DBMS-90009";

    /** 曲マスタのPK取得結果0件エラー（想定外） */
    public static final String ERR_CD_90010 = "DBMS-90010";

    /** JSONリクエスト送信時JSON生成IOエラー */
    public static final String ERR_CD_90011 = "DBMS-90011";

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

    public static final String ERR_STEP_CD_MUMM_00003 = "MUMM-00003";
    public static final String ERR_MESSAGE_MUMM_00003 = "CSVエクスポート処理にてFileNotFoundException発生";

    public static final String ERR_STEP_CD_MUMM_00004 = "MUMM-00004";
    public static final String ERR_MESSAGE_MUMM_00004 = "CSVエクスポート処理にてIOException発生";

    public static final String ERR_STEP_CD_MUMM_00005 = "MUMM-00005";
    public static final String ERR_MESSAGE_MUMM_00005 = "CSVエクスポート処理にてクローズ時IOException発生";

    public static final String ERR_STEP_CD_MUMM_00006 = "MUMM-00006";
    public static final String ERR_MESSAGE_MUMM_00006 = "CSVインポート処理にてキー情報不足エラー";

    public static final String ERR_STEP_CD_MUMM_00007 = "MUMM-00007";
    public static final String ERR_MESSAGE_MUMM_00007 = "CSVインポート処理にてMUSIC_MST登録日時パースエラー発生";

    public static final String ERR_STEP_CD_MUMM_00008 = "MUMM-00008";
    public static final String ERR_MESSAGE_MUMM_00008 = "CSVインポート処理にてMUSIC_MST更新日時パースエラー発生";

    public static final String ERR_STEP_CD_MUMM_00009 = "MUMM-00009";
    public static final String ERR_MESSAGE_MUMM_00009 = "曲マスタのPK取得結果0件エラー（想定外）";

    public static final String ERR_STEP_CD_MUMM_00010 = "MUMM-00010";
    public static final String ERR_MESSAGE_MUMM_00010 = "CSVインポート処理にて予期せぬエラー発生";

    public static final String ERR_STEP_CD_MUMM_00011 = "MUMM-00011";
    public static final String ERR_MESSAGE_MUMM_00011 = "CSVインポート処理にて予期せぬエラー発生";

    public static final String ERR_STEP_CD_MUMM_00012 = "MUMM-00012";
    public static final String ERR_MESSAGE_MUMM_00012 = "リザルト履歴INSERT時リフレクションエラー（InvocationTargetException）";

    public static final String ERR_STEP_CD_MUMM_00013 = "MUMM-00012";
    public static final String ERR_MESSAGE_MUMM_00013 = "リザルト履歴INSERT時リフレクションエラー（IllegalAccessException）";

    public static final String ERR_STEP_CD_GSSI_00001 = "GSSI-00001";
    public static final String ERR_MESSAGE_GSSI_00001 = "Googleスプレッドシートインポート処理にてIOException発生";

    public static final String ERR_STEP_CD_GSSI_00002 = "GSSI-00002";
    public static final String ERR_MESSAGE_GSSI_00002 = "Googleスプレッドシートインポート処理にてP12ファイル読み込み時エラー";

    public static final String ERR_STEP_CD_GSSI_00003 = "GSSI-00003";
    public static final String ERR_MESSAGE_GSSI_00003 = "Googleスプレッドシートインポート処理にてファイルクローズエラー";

    public static final String ERR_STEP_CD_GSSI_00004 = "GSSI-00004";
    public static final String ERR_MESSAGE_GSSI_00004 = "Googleスプレッドシートインポート処理にてキー情報不足エラー";

    public static final String ERR_STEP_CD_GSSI_00005 = "GSSI-00005";
    public static final String ERR_MESSAGE_GSSI_00005 = "Googleスプレッドシートインポート処理にてServiceException発生";

    public static final String ERR_STEP_CD_GSSI_00006 = "GSSI-00006";
    public static final String ERR_MESSAGE_GSSI_00006 = "Googleスプレッドシートインポート処理にてGeneralSecurityException発生";

    public static final String ERR_STEP_CD_UTIL_00001 = "UTIL-00001";
    public static final String ERR_MESSAGE_UTIL_00001 = "csv読み込み時エラー";

    public static final String ERR_STEP_CD_UTIL_00002 = "UTIL-00002";
    public static final String ERR_MESSAGE_UTIL_00002 = "リフレクション時エラー";

    public static final String ERR_STEP_CD_UTIL_00003 = "UTIL-00003";
    public static final String ERR_MESSAGE_UTIL_00003 = "画像読み込み時エラー";

    public static final String ERR_STEP_CD_UTIL_00004 = "UTIL-00004";
    public static final String ERR_MESSAGE_UTIL_00004 = "画像読み込み処理にてファイルクローズエラー";

    public static final String ERR_STEP_CD_NETR_00001 = "NETR-00001";
    public static final String ERR_MESSAGE_NETR_00001 = "JSONリクエスト送信時JSON生成IOエラー";

    public static final String ERR_STEP_CD_NETR_00002 = "NETR-00002";
    public static final String ERR_MESSAGE_NETR_00002 = "JSONリクエスト送信時タイムアウトエラー";


    /*********************************************************************************
     * CSVデータ部プレフィックス
     *********************************************************************************/
    /** CSVデータ部プレフィックス：MusicResultDBHR */
    public static final String CSV_HEAD_PREFIX_MUSIC_RESULT_DBHR = "MusicResultDBHR_";

    /*********************************************************************************
     * ファイル名プレフィックス
     *********************************************************************************/
    /** CSVエクスポートファイルパス */
    public static final String FILENAME_PREFIX_EXPORT_CSV = "exportCsv_";

    /*********************************************************************************
     * ファイル名サフィックス
     *********************************************************************************/
    /** CSVエクスポートファイル名サフィックス：MUSIC_MST */
    public static final String FILENAME_SUFFIX_MUSIC_INFO_CSV = "_MUSIC_INFO.csv";

    /*********************************************************************************
     * その他定数(弐寺特有)
     *********************************************************************************/
    public static final int GUAGE_CLEAR_BORDER = 80;
    public static final int GUAGE_REVERSE_BORDER = 78;

    /*********************************************************************************
     * その他定数(アプリ特有)
     *********************************************************************************/
    public static final String CONST_MOD_MARK = "[*] ";

    /*********************************************************************************
     * その他定数（一般）
     *********************************************************************************/

    public static final String MUSIC_MST_CSV_DATE_FORMAT = "EEE MMM dd HH:mm:ss 'JST' yyyy";
    public static final String MUSIC_EDIT_UPDATED_FORMAT = "yyyy/MM/dd HH:mm:ss";
    public static final String MUSIC_HISTORY_LISTVIEW_UPDATED_FORMAT = "yy/MM/dd HH:mm";
    public static final String CONST_YMDHMS_FORMAT = "yyyyMMddHHmmss";
    public static final String CONST_BLANK = "";
    public static final String CONST_HALF_COLON = ":";
    public static final String CONST_HALF_SPACE = " ";
    public static final String CONST_HALF_SLASH = "/";
    public static final String CONST_HALF_QUOTE = "'";
    public static final String CONST_HALF_COMMA = ",";
    public static final String CONST_HALF_HYPHEN = "-";
    public static final String CONST_HALF_SHARP = "#";

    public static final String CONST_NO_HIT_DUMMY_STRING = "CONST_NO_HIT_DUMMY_STRING";

}
