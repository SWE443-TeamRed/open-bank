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

    private int accountIndex;
    public FragmentPageAdapter(FragmentManager fm, int accountIndex) {
        super(fm);
        this.accountIndex = accountIndex;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                Bundle bundle = new Bundle();
                bundle.putInt("id", accountIndex);
                Fragment account_fragment = new AccountFrag();
                account_fragment.setArguments(bundle);
                return account_fragment;
            case 1:
                Bundle bundle1 = new Bundle();
                bundle1.putInt("id", accountIndex);
                Fragment trans_fragment = new TransactionFrag();
                trans_fragment.setArguments(bundle1);
                return trans_fragment;
            case 2:
                return new Transfer();
            default:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("id", accountIndex);
                Fragment account2_fragment = new AccountFrag();
                account2_fragment.setArguments(bundle2);
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