package com.app.swe443.openbankapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import static android.view.View.GONE;

public class TransferFrag extends Fragment implements View.OnClickListener {

    private EditText accountTo;
    private EditText accountToConfirm;
    private EditText amount;

    private Button transferToUserButton;
    private Button transferBetweenMyAccountsButton;
    private RelativeLayout betweenUserButtonLayout;
    private RelativeLayout betweenAccountButtonLayout;
    private LinearLayout betweenAccountForm;
    private LinearLayout betweenUserForm;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_transfer, container, false);

        accountTo = (EditText) v.findViewById(R.id.accountnumToInput);
        accountToConfirm = (EditText) v.findViewById(R.id.accountnumToConfirmInput);
        amount = (EditText) v.findViewById(R.id.amountInput);

        transferToUserButton = (Button) v.findViewById(R.id.toAnotherUserButton);
        transferBetweenMyAccountsButton = (Button) v.findViewById(R.id.toMyAccountButton);
        transferBetweenMyAccountsButton.setOnClickListener(this);
        transferToUserButton.setOnClickListener(this);

        betweenAccountButtonLayout = (RelativeLayout) v.findViewById(R.id.betweenAccount);
        betweenUserButtonLayout = (RelativeLayout) v.findViewById(R.id.betweenUser);
        betweenUserForm = (LinearLayout) v.findViewById(R.id.transferFormLayout);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toAnotherUserButton:
                System.out.println("Transfer between two users requested");
                setOptionsVisibility(0);
                betweenUserForm.setVisibility(View.VISIBLE);
                break;
            case R.id.toMyAccountButton:
                System.out.println("Transfer between a user's accounts requested");
                setOptionsVisibility(0);
                betweenAccountForm.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void setOptionsVisibility(int value){
        switch(value){
            case 1:
                betweenUserButtonLayout.setVisibility(GONE);
                betweenAccountButtonLayout.setVisibility(GONE);
                break;
            case 0:
                betweenAccountButtonLayout.setVisibility(View.VISIBLE);
                betweenUserButtonLayout.setVisibility(View.VISIBLE);
        }
    }


}
