package net.in.ahr.dbms.presenters.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import net.in.ahr.dbms.R;

/**
 * Created by str2653z on 2016/04/23.
 */
public class DbmsSettingFlagment extends PreferenceFragment {

    public static final String TAG = "DbmsSettingFlagment";

    public DbmsSettingFlagment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.dbms_setting_fragment);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        ListPreference listPreference = (ListPreference)findPreference(getString(R.string.pref_key_list_preference));
        listPreference.setValueIndex(0);
    }

}
