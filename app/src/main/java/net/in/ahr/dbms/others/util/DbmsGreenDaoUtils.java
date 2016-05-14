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

}
