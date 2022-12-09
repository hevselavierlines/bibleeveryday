package tk.hevselavierlines.bibleeveryday.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import tk.hevselavierlines.bibleeveryday.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.book, R.string.chapter, R.string.verse, R.string.search};
    private final Context mContext;
    private PlaceholderFragment[] fragments;
    private SearchFragment searchFragment;

    public SectionsPagerAdapter(Context context, FragmentManager fm, PlaceholderFragment[] fragments, SearchFragment searchFragment) {
        super(fm);
        mContext = context;
        this.fragments = fragments;
        this.searchFragment = searchFragment;
    }

    @Override
    public Fragment getItem(int position) {
        if(position <= 2) {
            return fragments[position];
        } else {
            return searchFragment;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}