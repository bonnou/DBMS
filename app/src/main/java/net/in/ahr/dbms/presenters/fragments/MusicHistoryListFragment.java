package net.in.ahr.dbms.presenters.fragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.in.ahr.dbms.R;
import net.in.ahr.dbms.data.strage.util.LogUtil;
import net.in.ahr.dbms.presenters.adapters.MusicHistoryListAdater;
import net.in.ahr.dbms.presenters.adapters.MusicListAdapter;

/**
 * Created by str2653z on 16/05/01.
 */
public class MusicHistoryListFragment extends MusicListFragment {

    @Override
    protected MusicListAdapter getMusicListAdapter() {
        return new MusicHistoryListAdater( getActivity() );
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        LogUtil.logEntering();

        MenuItem settingItem = menu.findItem(R.id.action_settings);
        settingItem.setVisible(false);
        MenuItem refineSearchItem = menu.findItem(R.id.action_refine_search);
        refineSearchItem.setVisible(false);
        MenuItem musicEditUpdateItem = menu.findItem(R.id.action_music_edit_update);
        musicEditUpdateItem.setVisible(false);

        LogUtil.logExiting();
    }

}
