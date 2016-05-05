package net.in.ahr.dbms.data.network.api.util;

import android.content.Context;

import net.in.ahr.dbms.BuildConfig;
import net.in.ahr.dbms.data.network.api.asyncTask.PostJSONAsyncTask;
import net.in.ahr.dbms.data.network.api.dto.DtoUtils;
import net.in.ahr.dbms.data.network.api.dto.MusicMstDto;
import net.in.ahr.dbms.data.network.api.dto.MusicResultDBHRDto;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;
import net.in.ahr.dbms.presenters.activities.MusicListActivity;

import java.util.ArrayList;
import java.util.List;

import greendao.MusicMst;
import greendao.MusicResultDBHR;

/**
 * Created by str2653z on 16/05/05.
 */
public class DbmsApiUtils {

    private DbmsApiUtils(){}

    public static void sleepWhileOAuthing() {
        while (MusicListActivity.nowOAuthingFlg) {
            try{
                LogUtil.logDebug("sleeping...");
                Thread.sleep(3000); //3000ミリ秒Sleepする
            }catch(InterruptedException e){}
        }
    }

    public static void postToInsertResultApi(MusicMst music, Context context) {
        LogUtil.logEntering();

        String url = BuildConfig.DBMS_ONLINE_PATH + AppConst.DBMS_ONLINE_API_PATH_INSERT_RESULT;

        MusicMstDto musicMstDto = new MusicMstDto();
        DtoUtils dtoUtils = new DtoUtils();
        dtoUtils.convertMusicMstFromEntity(music, musicMstDto);

        // TODO: ユーザ設定・認証
        musicMstDto.getMusicResultDBHR().setUserName("testUserName");

        // JSONをポスト
        new PostJSONAsyncTask(url, musicMstDto.getMusicResultDBHR()).execute(context);

        LogUtil.logExiting();
    }

    public static void postToInsertAllResultsApi(List<MusicMst> musicList, Context context) {
        LogUtil.logEntering();

        String url = BuildConfig.DBMS_ONLINE_PATH + AppConst.DBMS_ONLINE_API_PATH_INSERT_ALL_RESULTS;

        List<MusicResultDBHRDto> musicResultDtoList = new ArrayList<MusicResultDBHRDto>();
        DtoUtils dtoUtils = new DtoUtils();

        for (MusicMst music : musicList) {
            MusicResultDBHRDto musicResultDto = new MusicResultDBHRDto();
            dtoUtils.convertMusicResultFromEntity(music, music.getMusicResultDBHR(), musicResultDto);
            musicResultDto.setUserName("testUserName");
            musicResultDtoList.add(musicResultDto);
        }

        // JSONをポスト
        new PostJSONAsyncTask(url, musicResultDtoList).execute(context);

        LogUtil.logExiting();
    }

}
