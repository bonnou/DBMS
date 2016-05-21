package net.in.ahr.dbms.others.util;

/**
 * Created by str2653z on 16/05/19.
 */
public class DbmsUtils {

    private static String runtime = System.getProperty("java.runtime.name");

    /**
     * 実行環境により返却値を変更
     * @param appObj
     * @param testObj
     * @param androidTestObj
     * @param <T>
     * @return
     */
    public static <T> T changeByRuntime(T appObj, T testObj, T androidTestObj) {
        T ret = null;

        if (runtime.contains("Android")) {

        }

        return ret;
    }

}
