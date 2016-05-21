package net.in.ahr.dbms.testCommon;

import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.others.AppConst;

import java.lang.reflect.Field;

/**
 * Created by str2653z on 16/05/18.
 */
public class DbmsUnitTestUtils {

    public static void replaceMusicMstCsvForUt() {
        LogUtil.logEntering();

/*
        Class clazz = AppConst.class;
        try {
            Field field = clazz.getDeclaredField("MUSIC_MST_MIG_VER_CD_1_CSV_PATH");
            field.setAccessible(true);
            field.set(field, "csv/musicMst/forUT_musicMst_0001.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
*/

        AppConst.MUSIC_MST_MIG_VER_CD_1_CSV_PATH = "csv/musicMst/forUT_musicMst_0001.csv";

        LogUtil.logExiting();
    }

}
