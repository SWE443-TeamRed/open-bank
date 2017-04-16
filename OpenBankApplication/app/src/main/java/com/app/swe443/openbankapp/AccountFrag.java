package com.app.swe443.openbankapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class AccountFrag extends Fragment implements View.OnClickListener {
    private Account account;
    private int accountID;
    private TextView accountnameText;
    private TextView accountnumText;
    private TextView balanceText;
    private OnTransactionSelectedListener mCallback;

    public AccountFrag(){

    }

    // Container Activity must implement this interface
    public interface OnTransactionSelectedListener {
        public void onTransactionSelected(int accountID);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnTransactionSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTransactionSelectedListener ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v =  inflater.inflate(R.layout.fragment_account,container,false);

        MainActivity activity = (MainActivity) getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            accountID = bundle.getInt("id",-1);
            account = activity.getAccount(bundle.getInt("id",-1));
        }

        accountnameText = (TextView) v.findViewById(R.id.accountnameText);
        accountnumText = (TextView) v.findViewById(R.id.accountnumText);
        balanceText = (TextView) v.findViewById(R.id.balanceText);
        accountnameText.setText(String.valueOf(account.getType()));
        accountnumText.setText(String.valueOf(account.getAccountnum()));
        balanceText.setText(String.valueOf(account.getBalance()));

        Button transClicked = (Button) v.findViewById(R.id.transactions);
        transClicked.setOnClickListener(this);
        //System.out.println("OBTAINED INT VALUE OF SELECTED ACCOUNT "+ accountID);

        return v;
    }


    /*
        A button is clicked in the Account fragment, perform action based on button
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.transactions:
                mCallback.onTransactionSelected(accountID);
                break;
        }
    }


}
