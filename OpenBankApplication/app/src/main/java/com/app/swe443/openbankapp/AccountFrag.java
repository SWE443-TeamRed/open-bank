package com.app.swe443.openbankapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class AccountFrag extends Fragment {
    private Account account;
    private TextView accountnameText;
    private TextView accountnumText;
    private TextView balanceText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v =  inflater.inflate(R.layout.fragment_account,container,false);

        MainActivity activity = (MainActivity) getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            account = activity.getAccount(bundle.getInt("id",-1));
        }

        accountnameText = (TextView) v.findViewById(R.id.accountnameText);
        accountnumText = (TextView) v.findViewById(R.id.accountnumText);
        balanceText = (TextView) v.findViewById(R.id.balanceText);
        accountnameText.setText(String.valueOf(account.getType()));
        accountnumText.setText(String.valueOf(account.getAccountnum()));
        balanceText.setText(String.valueOf(account.getBalance()));


        //System.out.println("OBTAINED INT VALUE OF SELECTED ACCOUNT "+ accountID);

        return v;
    }
}
