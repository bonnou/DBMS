package net.in.ahr.dbms.data.strage.util;

import android.util.Log;

import com.crashlytics.android.Crashlytics;

public class LogUtil {

    // **********************************************************************
    // 定数
    // **********************************************************************

    private static final String TAG = "After";

    // **********************************************************************
    // メンバ
    // **********************************************************************

    private static boolean mIsShowLog = false;

    // **********************************************************************
    // パブリックメソッド
    // **********************************************************************

    public static void setShowLog(boolean isShowLog) {
        mIsShowLog = isShowLog;
    }

    public static void logDebug() {
        outputLog(Log.DEBUG, null, null);
    }

    public static void logDebug(String message) {
        outputLog(Log.DEBUG, message, null);
    }

    public static void logError(String message) {
        Crashlytics.log(message);
        outputLog(Log.ERROR, message, null);
    }

    public static void logDebug(String message, Throwable throwable) {
        outputLog(Log.DEBUG, message, throwable);
    }

    public static void logEntering() {
        outputLog(Log.DEBUG, "ENTERING", null);
    }

    public static void logExiting() {
        outputLog(Log.DEBUG, "EXITING", null);
    }

    // **********************************************************************
    // プライベートメソッド
    // **********************************************************************

    private static void outputLog(int type, String message, Throwable throwable) {
        if (!mIsShowLog) {
            // ログ出力フラグが立っていない場合は何もしません。
            return;
        }

        // ログのメッセージ部分にスタックトレース情報を付加します。
        if (message == null) {
            message = getStackTraceInfo();
        } else {
            message = getStackTraceInfo() + message;
        }

        // ログを出力!
        switch (type) {
            case Log.DEBUG:
                if (throwable == null) {
                    Log.d(TAG, message);
                } else {
                    Log.d(TAG, message, throwable);
                }
                break;
        }
    }

    /**
     * スタックトレースから呼び出し元の基本情報を取得。
     *
     * @return <<className#methodName:lineNumber>>
     */
    private static String getStackTraceInfo() {
        // 現在のスタックトレースを取得。
        // 0:VM 1:スレッド 2:getStackTraceInfo() 3:outputLog() 4:logDebug()等 5:呼び出し元
        StackTraceElement element = Thread.currentThread().getStackTrace()[5];

        String fullName = element.getClassName();
        String className = fullName.substring(fullName.lastIndexOf(".") + 1);
        String methodName = element.getMethodName();
        int lineNumber = element.getLineNumber();

        return "★DBMS★<<" + className + "#" + methodName + ":" + lineNumber + ">> ";
    }
}