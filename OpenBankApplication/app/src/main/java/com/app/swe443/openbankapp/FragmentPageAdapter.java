package com.app.swe443.openbankapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kimberly_93pc on 4/9/17.
 * Adapter for sliding ability.
 */

public class FragmentPageAdapter extends FragmentStatePagerAdapter {

    private int accountnum;
    public FragmentPageAdapter(FragmentManager fm, int accountnum) {
        super(fm);
        this.accountnum = accountnum;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:

                Fragment account_fragment = new AccountFrag();
                return account_fragment;
            case 1:

                Fragment trans_fragment = new TransactionFrag();
                return trans_fragment;
            case 2:
                return new TransferFrag();
            default:

                Fragment account2_fragment = new AccountFrag();
                return account2_fragment;
        }
    }

    @Override
    public int getItemPosition(Object object) {
// POSITION_NONE makes it possible to reload the PagerAdapter
        return POSITION_NONE;
    }
    @Override
    public int getCount() {

        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Account";
            case 1:
                return "Transactions";
            case 2:
                return "Transfer";
        }
        return "Account Basics";
    }
}