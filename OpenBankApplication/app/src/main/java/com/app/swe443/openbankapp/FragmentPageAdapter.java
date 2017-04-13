package com.app.swe443.openbankapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by kimberly_93pc on 4/9/17.
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
                return new AccountFrag();
            case 2:
                return new CreditFrag();
            // The other sections of the app are dummy placeholders.
            default:
            Fragment fragment = new SavingsFrag();
//            Bundle args = new Bundle();
//            args.putInt(, position + 1);
//            fragment.setArguments(args);
            return fragment;
        }
    }

    @Override
    public int getCount() {

        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OBJECT " + (position + 1);
    }
}
