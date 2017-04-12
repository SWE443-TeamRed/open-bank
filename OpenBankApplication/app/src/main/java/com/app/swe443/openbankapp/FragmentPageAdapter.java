package com.app.swe443.openbankapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kimberly_93pc on 4/9/17.
 * Adapter for sliding ability.
 */

public class FragmentPageAdapter extends FragmentStatePagerAdapter {
    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new SavingsFrag();
            case 1:
                return new CheckingFrag();
            case 2:
                return new CreditFrag();
            default:
            Fragment fragment = new SavingsFrag();
            return fragment;
        }
    }

    @Override
    public int getCount() {

        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Savings";
            case 1:
                return "Checking";
            case 2:
                return "Credit";
        }
        return "Savings";
    }
}
