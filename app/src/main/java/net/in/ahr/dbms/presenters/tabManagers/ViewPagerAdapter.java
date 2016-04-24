/*
 * Copyright (c) 2014, Rukmal Dias
All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
 * Redistributions of source code must retain the above copyright notice,
   this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
   notice, this list of conditions and the following disclaimer in the
   documentation and/or other materials provided with the distribution.
THIS SOFTWARE IS PROVIDED BY THE AUTHOR AND CONTRIBUTORS ``AS IS'' AND ANY
EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH
DAMAGE.
 */
package net.in.ahr.dbms.presenters.tabManagers;

        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;


        import net.in.ahr.dbms.R;
        import net.in.ahr.dbms.presenters.activities.MusicListActivity;
        import net.in.ahr.dbms.presenters.fragments.MusicEditFragment;
        import net.in.ahr.dbms.presenters.fragments.MusicListFragment;
        import net.in.ahr.dbms.presenters.fragments.WebViewFragment;

/**
 * ViewPagerAdapter is an {@link android.support.v4.app.FragmentPagerAdapter FragmentPagerAdapter}
 * that is used bind some fragments with their child fragments here.
 * @author Rukmal Dias
 */
public class ViewPagerAdapter extends FragmentPagerAdapter {

    private BaseFragment mFragmentAtPos0; // Fragment at index 0
    private BaseFragment mFragmentAtPos1; // Fragment at index 1
//    private BaseFragment mFragmentAtPos2; // Fragment at index 2
    private final FragmentManager mFragmentManager;

    private static final int NUM_OF_ITEMS = 2; // No of ViewPager items
//    private static final int NUM_OF_ITEMS = 3; // No of ViewPager items

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
        mFragmentManager = fm;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            if (mFragmentAtPos1 == null) {
                mFragmentAtPos1 = new MusicListFragment();
                mFragmentAtPos1.mListener = new PageFragmentListener() {
                    public void onSwitchToNextFragment() {
                        mFragmentManager.beginTransaction().remove(mFragmentAtPos1).commit();
                        mFragmentAtPos1 = new MusicEditFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("musicPosition", MusicListActivity.position);
                        bundle.putSerializable("musicForEdit", MusicListActivity.musicForEdit);
                        mFragmentAtPos1.setArguments(bundle);
                        mFragmentAtPos1.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                };
            }
            return mFragmentAtPos1;

        } else if (position == 1) {
            if (mFragmentAtPos0 == null) {
                mFragmentAtPos0 = new WebViewFragment();
            }
            return mFragmentAtPos0;

/*
        } else if (position == 2) {
            if (mFragmentAtPos2 == null) {
                mFragmentAtPos2 = new WebViewFragment();
            }
            return mFragmentAtPos2;
*/
        }

        // ありえない
        return null;
    }

    @Override
    public int getCount() {
        return NUM_OF_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String tabTitle = "";
        if (position == 0) {
            tabTitle = "MUSIC LIST";
        } else if (position == 1) {
            tabTitle = "WEB VIEW";
        }
        return tabTitle;
    }


    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof MusicListFragment && mFragmentAtPos1 instanceof MusicEditFragment) {
            return POSITION_NONE;
        }
/*
        else if(object instanceof ThirdPageFragment && mFragmentAtPos2 instanceof ChildFragment) {
            return POSITION_NONE;
        }
*/
        else if(object instanceof MusicEditFragment) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

    public void replaceChildFragment(BaseFragment oldFrg, int position) {
        switch (position) {
            case 0:
                mFragmentManager.beginTransaction().remove(oldFrg).commit();
                mFragmentAtPos1 = new MusicListFragment();
                mFragmentAtPos1.mListener = new PageFragmentListener() {
                    public void onSwitchToNextFragment() {
                        mFragmentManager.beginTransaction().remove(mFragmentAtPos1).commit();
                        mFragmentAtPos1 = new MusicEditFragment();
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("musicPosition", MusicListActivity.position);
                        bundle.putSerializable("musicForEdit", MusicListActivity.musicForEdit);
                        mFragmentAtPos1.setArguments(bundle);
                        mFragmentAtPos1.setShowingChild(true);
                        notifyDataSetChanged();
                    }
                };
                notifyDataSetChanged();
                break;

            case 1:
                mFragmentManager.beginTransaction().remove(oldFrg).commit();
                mFragmentAtPos0 = new WebViewFragment();
                notifyDataSetChanged();
                break;
/*
            case 2:
                mFragmentManager.beginTransaction().remove(oldFrg).commit();
                mFragmentAtPos2 = new WebViewFragment();
                notifyDataSetChanged();
                break;
*/
            default:
                break;
        }
    }



}