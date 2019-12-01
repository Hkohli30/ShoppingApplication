package com.example.hkohli.shoppingapplication;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Hkohli on 9/29/2016.
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter{

    private int TAB_LENGTH;

    public MyPagerAdapter(FragmentManager fm,int tab_length) {
        super(fm);
        TAB_LENGTH = tab_length;
    }

    @Override
    public Fragment getItem(int position) {
       Fragment fragment = null;

        switch (position)
        {
            case 0 : fragment = new FeedFragment(); break;
            case 1 : fragment = new CategoriesFragment(); break;
        }

        return fragment;
    }

    @Override
    public int getCount() {
        return TAB_LENGTH;
    }
}
