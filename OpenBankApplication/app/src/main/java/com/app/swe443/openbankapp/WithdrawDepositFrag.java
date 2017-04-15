package com.app.swe443.openbankapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class WithdrawDepositFrag extends Fragment {

    private int accountID;
    MainActivity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_withdraw_deposit, container, false);

        //Main Activity reference, used to get account and transaction data through Main's methods
        activity = (MainActivity) getActivity();
        //Grab Account ID from the passed in argument
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            //Account ID
            accountID = bundle.getInt("id", -1);
        }

        return v;
    }
}
