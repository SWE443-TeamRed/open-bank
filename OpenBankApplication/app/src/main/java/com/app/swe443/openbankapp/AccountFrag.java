package com.app.swe443.openbankapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.transition.Visibility;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class AccountFrag extends Fragment implements View.OnClickListener {
    private Account account;
    private int accountIndex;
    private TextView accountnameText;
    private TextView accountnumText;
    private TextView balanceText;
    private TextView ownerText;
    private TextView typeText;
    private TextView creationText;
    private Accounts activity;
    private OnAccountsCallbackListener mCallback;


    private Button depositButton;
    private Button withdrawButton;
    //Deposit UI fields
    private TextView depositamounttitleText;
    private EditText depositamountText;
    private Button confirmDeposit;
    private Button cancelDeposit;
    private LinearLayout depositButtons;


    //Withdraw UI fields
    private TextView withdrawamounttitleText;
    private EditText withdrawamountText;
    private Button confirmWithdraw;
    private Button cancelWithdraw;
    private LinearLayout withdrawButtons;


    public AccountFrag(){

    }

    // Container Activity must implement this interface
    public interface OnAccountsCallbackListener {
        public void onWithdrawSelected(int amount);
        public void onDepositSelected(int ammount);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnAccountsCallbackListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAccountsCallbackListener ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v =  inflater.inflate(R.layout.fragment_account,container,false);

        activity = (Accounts) getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            accountIndex = bundle.getInt("id",-1);
            account = activity.getAccount();
            System.out.println("DISPLAYING ACCOUNT WITH NUMBER "+ account.getAccountnum() + " AT INDEX "+accountIndex);

        }

        depositButton = (Button) v.findViewById(R.id.deposit);
        depositamounttitleText = (TextView) v.findViewById(R.id.depositamounttitleText);
        depositamountText = (EditText) v.findViewById(R.id.depositamountText);
        confirmDeposit = (Button) v.findViewById(R.id.confirmdeposit);
        cancelDeposit = (Button) v.findViewById(R.id.canceldeposit);
        depositButtons = (LinearLayout) v.findViewById(R.id.depositButtons);

        withdrawButton = (Button) v.findViewById(R.id.withdraw);
        withdrawamounttitleText = (TextView) v.findViewById(R.id.withdrawamounttitleText);
        withdrawamountText = (EditText) v.findViewById(R.id.withdrawamountText);
        confirmWithdraw = (Button) v.findViewById(R.id.confirmwithdraw);
        cancelWithdraw = (Button) v.findViewById(R.id.cancelwithdraw);
        withdrawButtons = (LinearLayout) v.findViewById(R.id.withdrawButtons);




        depositButton.setOnClickListener(this);
        withdrawButton.setOnClickListener(this);
        confirmWithdraw.setOnClickListener(this);
        cancelWithdraw.setOnClickListener(this);
        cancelDeposit.setOnClickListener(this);
        confirmDeposit.setOnClickListener(this);


        accountnameText = (TextView) v.findViewById(R.id.accountnameText);
        accountnumText = (TextView) v.findViewById(R.id.accountnumText);
        balanceText = (TextView) v.findViewById(R.id.balanceText);

        ownerText = (TextView) v.findViewById(R.id.ownerText);
        typeText = (TextView) v.findViewById(R.id.typeText);
        creationText = (TextView) v.findViewById(R.id.creationText);

        accountnameText.setText(String.valueOf(account.getType()));
        accountnumText.setText(String.valueOf(account.getAccountnum()));
        balanceText.setText(String.valueOf(account.getBalance()));
        ownerText.setText(String.valueOf(account.getOwner().getUserID()));
        typeText.setText(String.valueOf(account.getType()));
        String newDateFormat = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm").format(account.getCreationdate());
        creationText.setText(newDateFormat);
        //System.out.println("OBTAINED INT VALUE OF SELECTED ACCOUNT "+ accountID);

        return v;
    }


    /*
        A button is clicked in the Account fragment, perform action based on button
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.withdraw:
                System.out.println("Withdraw Requested");
                depositButton.setVisibility(View.GONE);
                withdrawButton.setVisibility(View.GONE);
                //Show withdraw fields
                setWithdrawFieldsVisability(1);

                //mCallback.onWithdrawSelected(accountID);
                break;
            case R.id.deposit:
                System.out.println("Deposit Requested");
                depositButton.setVisibility(View.GONE);
                withdrawButton.setVisibility(View.GONE);
                //Show withdraw fields
                setDepositFieldsVisability(1);
                //mCallback.onDepositSelected(accountID);
                break;
            case R.id.confirmdeposit:
                System.out.println("Confirmation for Deposit Requested");
                if (depositamountText.getText().toString().equals("")) {
                    //Alert User that he must have an amount
                    Toast.makeText(getContext(), "Please enter a value",
                            Toast.LENGTH_SHORT).show();
                } else {
                    int amount = Integer.valueOf(depositamountText.getText().toString());

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    mCallback.onDepositSelected(Integer.valueOf(depositamountText.getText().toString()));
                                    setDepositFieldsVisability(0);
                                    depositButton.setVisibility(View.VISIBLE);
                                    withdrawButton.setVisibility(View.VISIBLE);
                                    balanceText.setText(String.valueOf(account.getBalance()));
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to deposit " + amount + " into your " + account.getType().toString() + " account?").setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();
                }

                //Show withdraw fields
                //mCallback.onDepositSelected(accountID);
                break;
            case R.id.confirmwithdraw:
                System.out.println("Confirmation for Withdraw Requested");

                if (withdrawamountText.getText().toString().equals("")) {
                    //Alert User that he must have an amount
                    Toast.makeText(getContext(), "Please enter a value",
                            Toast.LENGTH_SHORT).show();
                } else {
                    int amount = Integer.valueOf(withdrawamountText.getText().toString());
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    mCallback.onWithdrawSelected(Integer.valueOf(withdrawamountText.getText().toString()));
                                    setWithdrawFieldsVisability(0);
                                    depositButton.setVisibility(View.VISIBLE);
                                    withdrawButton.setVisibility(View.VISIBLE);
                                    balanceText.setText(String.valueOf(account.getBalance()));


                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to withdraw " + amount + " from your " + account.getType().toString() + " account?")
                            .setPositiveButton("Yes", dialogClickListener)
                            .setNegativeButton("No", dialogClickListener).show();

                }
                break;
            case R.id.canceldeposit:
                System.out.println("Cancel Withdraw Requested");
                depositButton.setVisibility(View.VISIBLE);
                withdrawButton.setVisibility(View.VISIBLE);
                //Show withdraw fields
                setDepositFieldsVisability(0);

                //mCallback.onWithdrawSelected(accountID);
                break;
            case R.id.cancelwithdraw:
                System.out.println("Cancel Deposit Requested");
                depositButton.setVisibility(View.VISIBLE);
                withdrawButton.setVisibility(View.VISIBLE);
                //Show withdraw fields
                setWithdrawFieldsVisability(0);
                //mCallback.onDepositSelected(accountID);
                break;
        }
    }


    public void setDepositFieldsVisability(int value){
        if(value==0) {
            depositamounttitleText.setVisibility(View.GONE);
            depositamountText.setVisibility(View.GONE);
            depositButtons.setVisibility(View.GONE);
        }else{
            depositamounttitleText.setVisibility(View.VISIBLE);
            depositamountText.setVisibility(View.VISIBLE);
            if(!depositamountText.getText().equals(""))
                depositamountText.setText("");
            depositButtons.setVisibility(View.VISIBLE);
        }


    }
    public void setWithdrawFieldsVisability(int value) {
        //Withdraw UI fields
        if (value == 0) {
            withdrawamounttitleText.setVisibility(View.GONE);
            withdrawamountText.setVisibility(View.GONE);
            withdrawButtons.setVisibility(View.GONE);
        } else {
            withdrawamounttitleText.setVisibility(View.VISIBLE);
            withdrawamountText.setVisibility(View.VISIBLE);
            if(!withdrawamountText.getText().equals(""))
                withdrawamountText.setText("");
            withdrawButtons.setVisibility(View.VISIBLE);
        }
    }



}
