package net.in.ahr.dbms.presenters.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import net.in.ahr.dbms.data.strage.util.LogUtil;

/**
 * Created by str2653z on 16/05/20.
 * http://qiita.com/suzukihr/items/90a93e79dc67c585de75
 */
public class LifeCycleActivity extends AppCompatActivity {

    private String TAG = "*** LifeCycleActivity: TAG is not set ***";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.logDebug(getClass().getSimpleName() + " start ﹕********************");
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName+ " (savedInstanceState: " + getBundleInfo(savedInstanceState) + ")");
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onStart() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onStart();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName + " (savedInstanceState: " + getBundleInfo(savedInstanceState) + ")");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onResume() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onResume();
    }

    @Override
    public void onAttachedToWindow() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onAttachedToWindow();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        return true;
    }

    // called when setting 'android:configChanges="orientation|screenSize"' in your manifest
    // http://developer.android.com/guide/topics/resources/runtime-changes.html#HandlingTheChange
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPause() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName + " (outState: " + getBundleInfo(outState) + ")");
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onDestroy();
    }

    @Override
    public void onDetachedFromWindow() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onDetachedFromWindow();
    }

    private static String getBundleInfo(Bundle bundle) {
        String info = bundle == null
                ? "null"
                : "Bundle@" + Integer.toHexString(System.identityHashCode(bundle));
        return info;
    }
}