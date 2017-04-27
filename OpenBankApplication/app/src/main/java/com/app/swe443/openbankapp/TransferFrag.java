package com.app.swe443.openbankapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.view.View.GONE;

public class TransferFrag extends Fragment implements View.OnClickListener {

    private EditText accountTo;
    private EditText accountToConfirm;
    private EditText amount;

    private Button transferToUserButton;
    private Button transferBetweenMyAccountsButton;
    private Button cancelTransfer;
    private Button confirmTransfer;
    private RelativeLayout betweenUserButtonLayout;
    private RelativeLayout betweenAccountButtonLayout;
    private LinearLayout betweenAccountForm;
    private LinearLayout betweenUserForm;

    private MockServerSingleton mockserver;

    private TransferFrag.OnTransferCallbackListener mCallback;







    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Container Activity must implement this interface
    public interface OnTransferCallbackListener {
        public void onTransferSelected();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (TransferFrag.OnTransferCallbackListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTransferSelectedListener ");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =inflater.inflate(R.layout.fragment_transfer, container, false);


        mockserver = MockServerSingleton.getInstance();

        accountTo = (EditText) v.findViewById(R.id.accountnumToInput);
        accountToConfirm = (EditText) v.findViewById(R.id.accountnumToConfirmInput);
        amount = (EditText) v.findViewById(R.id.amountInput);

        transferToUserButton = (Button) v.findViewById(R.id.toAnotherUserButton);
        transferBetweenMyAccountsButton = (Button) v.findViewById(R.id.toMyAccountButton);
        transferBetweenMyAccountsButton.setOnClickListener(this);
        transferToUserButton.setOnClickListener(this);

        betweenAccountButtonLayout = (RelativeLayout) v.findViewById(R.id.betweenAccount);
        betweenUserButtonLayout = (RelativeLayout) v.findViewById(R.id.betweenUser);
        betweenUserForm = (LinearLayout) v.findViewById(R.id.transferToUserFormLayout);
        betweenAccountForm = (LinearLayout) v.findViewById(R.id.transferToAccountFormLayout);

        cancelTransfer = (Button) v.findViewById(R.id.cancelTransfer);
        confirmTransfer = (Button) v.findViewById(R.id.confirmTransfer);
        cancelTransfer.setOnClickListener(this);
        confirmTransfer.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.toAnotherUserButton:
                System.out.println("Transfer between two users requested");

                    setOptionsVisibility(0);
                    betweenAccountForm.setVisibility(GONE);
                    betweenUserForm.setVisibility(View.VISIBLE);

                break;
            case R.id.toMyAccountButton:
                System.out.println("Transfer between a user's accounts requested");
                setOptionsVisibility(0);
                betweenAccountForm.setVisibility(View.VISIBLE);
                betweenUserForm.setVisibility(GONE);
                break;
            case R.id.cancelTransfer:
                System.out.println("Cancel Transfer Requested");
                setOptionsVisibility(1);
                break;
            case R.id.confirmTransfer:
                System.out.println("Confirmation of Transfer requested");
                /*
                    TODO CHECK IF TOACCOUNT EXISTS, NEED TO GET ALL ACCOUNTS IN SERVER
                 */
                boolean incomplete = false;
                if(mockserver.doesAccountExists(Integer.valueOf(accountTo.getText().toString()))) {
                    accountTo.setError("Account does not exist");
                    incomplete = true;
                }
                if(accountTo.getText().toString().equals("") ) {
                    accountTo.setError("Required field is missing");
                    incomplete = true;
                }
                if(accountToConfirm.getText().toString().equals("")) {
                    accountToConfirm.setError("Required field is missing");
                    incomplete = true;
                }
                if(!(accountTo.getText().toString().equals(accountToConfirm.getText().toString()))){
                    accountToConfirm.setError("Values do not match");
                    incomplete = true;
                }
                if(amount.getText().toString().equals("")) {
                    amount.setError("Required field is missing");
                    incomplete = true;
                }
                else if(Double.valueOf(amount.getText().toString()) <= 0){
                    amount.setError("Can't transfer a negative amount");
                    incomplete = true;
                }
                else if(Double.valueOf(amount.getText().toString()) >
                        mockserver.getLoggedInUser().getAccount().get(mockserver.getAccountIndex()).getBalance()){
                    amount.setError("Can't transfer more than account balance");
                    incomplete = true;
                }
                if(incomplete)
                    break;
                checkIfInputsAreFilled();
        }
    }


    public void checkIfInputsAreFilled(){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            /*
                                TODO SERVER HANDLES TRASNFER OCCURING
                             */
                            mockserver.transfer(Integer.valueOf(accountTo.getText().toString())
                                    ,Double.valueOf(amount.getText().toString()));
                            Toast.makeText(getContext(), "Sending....COMPLETE!",
                                    Toast.LENGTH_SHORT).show();
                            //Contact Accounts activity that it needs to refresh the tabs with new trasnfer info
                            mCallback.onTransferSelected();

                            setOptionsVisibility(1);
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you want to transfer " + amount.getText().toString() + " to  "
                    + mockserver.getRecieverInfo(Integer.valueOf(accountTo.getText().toString()))+ "?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }

    public void setOptionsVisibility(int value){
        switch(value){
            case 0:
                betweenUserButtonLayout.setVisibility(GONE);
                betweenAccountButtonLayout.setVisibility(GONE);
                break;
            case 1:
                betweenAccountButtonLayout.setVisibility(View.VISIBLE);
                betweenUserButtonLayout.setVisibility(View.VISIBLE);
                betweenUserForm.setVisibility(GONE);
                betweenAccountForm.setVisibility(GONE);
        }
    }


}
