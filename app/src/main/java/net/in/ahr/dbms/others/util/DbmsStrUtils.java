package net.in.ahr.dbms.others.util;

/**
 * Created by str2653z on 2016/04/14.
 */
public class DbmsStrUtils {

    /**
     * 文字列をint型に変換（出来ない場合は0に変換）
     * @param numStr 変換対象文字列
     * @return 変換後数値、変換出来ない場合は0を返却
     */
    public static int parseIntOrRetZero(String numStr) {
        int ret;
        try {
            ret = Integer.parseInt(numStr);
        } catch (NumberFormatException nfe) {
            ret = 0;
        }
        return ret;
    }

    /**
     * 文字列をdouble型に変換（出来ない場合は0に変換）
     * @param numStr 変換対象文字列
     * @return 変換後数値、変換出来ない場合は0を返却
     */
    public static double parseDoubleOrRetZero(String numStr) {
        double ret;
        try {
            ret = Double.parseDouble(numStr);
        } catch (NumberFormatException nfe) {
            ret = 0;
        }
        return ret;
    }

}
