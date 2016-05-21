package net.in.ahr.dbms.presenters.tabManagers;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import net.in.ahr.dbms.data.strage.util.LogUtil;

/**
 * Created by str2653z on 16/05/21.
 */
public class LifeCycleFragment extends Fragment {

    private String TAG = "*** LifeCycleFragment: TAG is not set ***";

    @Override
    public void onAttach(Activity activity) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName + " (savedInstanceState: " + getBundleInfo(savedInstanceState) + ")");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName + " (savedInstanceState: " + getBundleInfo(savedInstanceState) + ")");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName + " (savedInstanceState: " + getBundleInfo(savedInstanceState) + ")");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName + " (savedInstanceState: " + getBundleInfo(savedInstanceState) + ")");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onStart();
    }

    @Override
    public void onResume() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onResume();
    }

    // called when setting 'android:configChanges="orientation|screenSize"' in you manifest
    // http://developer.android.com/guide/topics/resources/runtime-changes.html#HandlingTheChange
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onConfigurationChanged(newConfig);
    }

    // called when calling 'setHasOptionsMenu(true)'
    // http://developer.android.com/reference/android/app/Fragment.html#setHasOptionsMenu(boolean)
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPause() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onPause();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName + " (outState: " + getBundleInfo(outState) + ")");
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStop() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        LogUtil.logDebug(methodName);
        super.onDetach();
    }

    private static String getBundleInfo(Bundle bundle) {
        String info = bundle == null
                ? "null"
                : "Bundle@" + Integer.toHexString(System.identityHashCode(bundle));
        return info;
    }
    
}
