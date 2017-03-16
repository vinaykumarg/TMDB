package com.example.vinayg.tmdb.adapters;
/**
 * Created by vinay.g on 08-Feb-17.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.vinayg.tmdb.fragments.FavoriteFragment;
import com.example.vinayg.tmdb.fragments.PopularScreenFragment;
import com.example.vinayg.tmdb.fragments.TopRatedFragment;

public class pagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;
    public pagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                PopularScreenFragment tab1 = new PopularScreenFragment();
                return tab1;
            case 1:
//                TabFragment2 tab2 = new TabFragment2();
//                return tab2;
                TopRatedFragment tab2 = new TopRatedFragment();
                return tab2;
            case 2:
                FavoriteFragment tab3 = new FavoriteFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}