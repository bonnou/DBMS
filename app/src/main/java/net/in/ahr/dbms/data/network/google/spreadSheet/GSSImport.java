package net.in.ahr.dbms.data.network.google.spreadSheet;

import android.app.Activity;
import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gdata.client.spreadsheet.CellQuery;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.CellEntry;
import com.google.gdata.data.spreadsheet.CellFeed;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.WorksheetEntry;
import com.google.gdata.util.ServiceException;

import net.in.ahr.dbms.business.usecases.result.MusicResultUtil;
import net.in.ahr.dbms.data.strage.mstMainte.MusicMstMaintenance;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.others.CustomApplication;
import net.in.ahr.dbms.others.events.musicList.SearchApplyEvent;
import net.in.ahr.dbms.others.exceptions.DbmsSystemException;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;
import net.in.ahr.dbms.presenters.fragments.MusicListFragment;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

import greendao.MusicMst;
import greendao.MusicMstDao;
import greendao.MusicResultDBHR;
import greendao.MusicResultDBHRDao;

/**
 * Created by str2653z on 2016/03/28.
 */
public class GSSImport {

    public static Activity activity;

    private static MusicMstDao getMusicMstDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicMstDao();
    }

    private static MusicResultDBHRDao getMusicResultDBHRDao(Context c) {
        return ((CustomApplication) c.getApplicationContext()).getDaoSession().getMusicResultDBHRDao();
    }

    public void execute(final Activity activity, String ssName, String wsName) {
        this.activity = activity;
        Context c = activity.getApplicationContext();
        GSSUtil gSSUtil = new GSSUtil(c);
        try {
            SpreadsheetService service = gSSUtil.getService();

            // 登録日時・更新日時
            java.util.Date nowDate = new java.util.Date();

            // スプレッドシート取得
            SpreadsheetEntry ssEntry = gSSUtil.findSpreadsheetByName(service, ssName);
            WorksheetEntry wsEntry = gSSUtil.findWorksheetByName(service, ssEntry, wsName);
            LogUtil.logDebug("スプレッドシート名：" + ssEntry.getTitle().getPlainText());

            // TODO: プログレスバーを表示したい
            for (int i = 3; i <= 702; i++) {
                CellQuery cellQuery = new CellQuery(wsEntry.getCellFeedUrl());
                cellQuery.setRange("D" + i + ":AK" + i);
                cellQuery.setReturnEmpty(true);
                CellFeed cellFeed = service.query(cellQuery, CellFeed.class);

                int cnt = 0;
                String sheetMusicName = null;
                String sheetMusicDbhrClearLamp = null;
                String sheetMusicExScore = null;
                String sheetMusicBp = null;
                String sheetMusicProgress = null;
                String sheetMusicMemo = null;
                String sheetMusicNha = null;
                for (CellEntry cellEntry : cellFeed.getEntries()) {
                    if (cnt % 34 == 0) {
                        sheetMusicName = cellEntry.getCell().getValue();
//                        LogUtil.logDebug(cellEntry.getCell().getRow() + cellEntry.getCell().getCol() + ":sheetMusicName[" + cnt % 34 + "]:" + sheetMusicName);
                    } else if (cnt % 34 == 3) {
                        sheetMusicDbhrClearLamp = cellEntry.getCell().getValue();
//                        LogUtil.logDebug(cellEntry.getCell().getRow() + cellEntry.getCell().getCol() + ":sheetMusicDbhrClearLamp[" + cnt % 34 + "]:" + sheetMusicDbhrClearLamp);
                    } else if (cnt % 34 == 4) {
                        sheetMusicExScore = cellEntry.getCell().getValue();
//                        LogUtil.logDebug(cellEntry.getCell().getRow() + cellEntry.getCell().getCol() + ":sheetMusicExScore[" + cnt % 34 + "]:" + sheetMusicExScore);
                    } else if (cnt % 34 == 5) {
                        sheetMusicBp = cellEntry.getCell().getValue();
//                        LogUtil.logDebug(cellEntry.getCell().getRow() + cellEntry.getCell().getCol() + ":sheetMusicBp[" + cnt % 34 + "]:" + sheetMusicBp);
                    } else if (cnt % 34 == 6) {
                        sheetMusicProgress = cellEntry.getCell().getValue();
//                        LogUtil.logDebug(cellEntry.getCell().getRow() + cellEntry.getCell().getCol() + ":sheetMusicProgress[" + cnt % 34 + "]:" + sheetMusicProgress);
                    } else if (cnt % 34 == 7) {
                        sheetMusicMemo = cellEntry.getCell().getValue();
//                        LogUtil.logDebug(cellEntry.getCell().getRow() + cellEntry.getCell().getCol() + ":sheetMusicMemo[" + cnt % 34 + "]:" + sheetMusicMemo);
                    } else if (cnt % 34 == 33) {
                        sheetMusicNha = cellEntry.getCell().getValue();
//                        LogUtil.logDebug(cellEntry.getCell().getRow() + cellEntry.getCell().getCol() + ":sheetMusicNha[" + cnt % 34 + "]:" + sheetMusicNha);

                        if ( sheetMusicDbhrClearLamp == null ) {
                            sheetMusicDbhrClearLamp = "NO PLAY";
                        } else if ( "NOPLAY".equals(sheetMusicDbhrClearLamp) ) {
                            sheetMusicDbhrClearLamp = AppConst.MUSIC_MST_CLEAR_LAMP_VAL_NO_PLAY;
                        } else if ( "FAILED".equals(sheetMusicDbhrClearLamp) ) {
                            sheetMusicDbhrClearLamp = AppConst.MUSIC_MST_CLEAR_LAMP_VAL_FAILED;
                        } else {
                            sheetMusicDbhrClearLamp += " CLEAR";
                        }
                        if ( sheetMusicExScore == null ) {
                            sheetMusicExScore = "0";
                        }
                        if ( sheetMusicBp == null ) {
                            sheetMusicBp = "0";
                        }
                        if ( sheetMusicProgress == null ) {
                            sheetMusicProgress = "";
                        }
                        if ( sheetMusicMemo == null ) {
                            sheetMusicMemo = "";
                        }
                        if ( sheetMusicName == null || sheetMusicNha == null ) {
                            throw new DbmsSystemException(
                                    AppConst.ERR_CD_90007,
                                    AppConst.ERR_STEP_CD_GSSI_00004,
                                    AppConst.ERR_MESSAGE_GSSI_00004);
                        }

                        // 曲名・難易度によりレコード取得
                        MusicMstDao musicMstDao = getMusicMstDao(c);
                        MusicMstMaintenance musicMstMaintenance = new MusicMstMaintenance();
                        MusicMst music = musicMstMaintenance.getMusicMstByPk(musicMstDao, sheetMusicName, sheetMusicNha);

                        LogUtil.logDebug("[forDiff]sheetMusicName:[" + sheetMusicName + "]");
                        LogUtil.logDebug("[forDiff]music.getName():[" + music.getName() + "]");

                        if (music != null) {
                            MusicResultDBHR resultDBHR = music.getMusicResultDBHR();
                            if (resultDBHR == null) {
                                resultDBHR = new MusicResultDBHR();
                                resultDBHR.setId(music.getId());
                                resultDBHR.setName(music.getName());
                                resultDBHR.setNha(music.getNha());
                                music.setMusicResultIdDBHR(music.getId());
                            }

                            resultDBHR.setClearLamp(sheetMusicDbhrClearLamp);
                            resultDBHR.setExScore(Integer.parseInt(sheetMusicExScore));
                            resultDBHR.setBp(Integer.parseInt(sheetMusicBp));
                            resultDBHR.setMemoOther(sheetMusicProgress + AppConst.CONST_HALF_SPACE + sheetMusicMemo);

                            // 各種値を算出し設定
                            MusicResultUtil musicResultUtil = new MusicResultUtil();
                            Map resultMap = musicResultUtil.calcRankRate(
                                    resultDBHR.getExScore(),
                                    resultDBHR.getBp(),
                                    music
                            );
                            resultDBHR.setScoreRank(
                                    (String) resultMap.get(MusicResultUtil.MAP_KEY_SCORE_RANK));
                            resultDBHR.setScoreRate(
                                    (Double) resultMap.get(MusicResultUtil.MAP_KEY_SCORE_RATE));
                            resultDBHR.setMissRate(
                                    (Double) resultMap.get(MusicResultUtil.MAP_KEY_MISS_RATE));
                            resultDBHR.setDjPoint(
                                    (Double) resultMap.get(MusicResultUtil.MAP_KEY_DJPOINT));

                            resultDBHR.setInsDate(nowDate);
                            resultDBHR.setUpdDate(nowDate);
                            resultDBHR.setLastPlayDate(nowDate);
                            resultDBHR.setLastUpdateDate(nowDate);

                            music.setMusicResultDBHR(resultDBHR);

                            // 更新
                            MusicResultDBHRDao musicResultDBHRDao = getMusicResultDBHRDao(c);
                            musicMstDao.insertOrReplace(music);
                            musicResultDBHRDao.insertOrReplace(resultDBHR);
                            LogUtil.logDebug("MusicMst:" + music.getName() + "[" + music.getNha() + "] update finish!");

                            // TODO: ここでAsyncTaskをexecuteすると、現AsyncTaskの全曲インポート完了後、各曲ポストのAsyncTaskが実施される。

                        } else {
                            LogUtil.logDebug(sheetMusicName.replace("'", "\\'").replace(",", "\\,") + "[" + sheetMusicNha + "] is null");
                        }
                    }

                    cnt++;
                }
            }

            // 再検索通知
            new SearchApplyEvent().start();
/*
            // UIスレッドでリストビュー再描画
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LogUtil.logDebug("runOnUiThread(GSSImport end)");
                    MusicListFragment musicListFragment = (MusicListFragment) GSSImport.activity.getFragmentManager().findFragmentByTag(MusicListFragment.TAG);
                    MusicListAdapter arrayAdapter = musicListFragment.adapter;
                    MusicMstDao musicMstDao = getMusicMstDao(activity.getApplicationContext());
                    List<MusicMst> list = musicMstDao.loadAll();
                    arrayAdapter.setMusicList(list);
                    arrayAdapter.notifyDataSetChanged();

                    Toast.makeText(GSSImport.activity, "END import from Google Spread Sheet...", Toast.LENGTH_LONG).show();
                }
            });
*/

        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90002,
                    AppConst.ERR_STEP_CD_GSSI_00001,
                    AppConst.ERR_MESSAGE_GSSI_00001);
        } catch (ServiceException se) {
            se.printStackTrace();
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90002,
                    AppConst.ERR_STEP_CD_GSSI_00005,
                    AppConst.ERR_MESSAGE_GSSI_00005);
        } catch (GeneralSecurityException se) {
            se.printStackTrace();
            throw new DbmsSystemException(
                    AppConst.ERR_CD_90002,
                    AppConst.ERR_STEP_CD_GSSI_00006,
                    AppConst.ERR_MESSAGE_GSSI_00006);
        }

    }



}
