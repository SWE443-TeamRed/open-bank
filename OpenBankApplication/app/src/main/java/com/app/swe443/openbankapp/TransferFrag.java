package com.app.swe443.openbankapp;

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
                    betweenUserForm.setVisibility(View.VISIBLE);

                break;
            case R.id.toMyAccountButton:
                System.out.println("Transfer between a user's accounts requested");
                setOptionsVisibility(0);
                betweenAccountForm.setVisibility(View.VISIBLE);
                break;
            case R.id.cancelTransfer:
                System.out.println("Cancel Transfer Requested");
                betweenAccountForm.setVisibility(View.GONE);
                setOptionsVisibility(1);
                break;
            case R.id.confirmTransfer:
                System.out.println("Confirmation of Transfer requested");
                if(accountTo.getText().equals("") || amount.getText().equals("")|| accountToConfirm.getText().equals("")){
                    Toast.makeText(getContext(), "Required field is missing",
                            Toast.LENGTH_SHORT).show();
                }else if(!(accountTo.getText().toString().equals(accountToConfirm.getText().toString()))){
                    Toast.makeText(getContext(), "Account numbers are not the same, please confirm same account number",
                            Toast.LENGTH_SHORT).show();
                }else {
                        checkIfInputsAreFilled();
                        setOptionsVisibility(0);
                        betweenAccountForm.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    public void checkIfInputsAreFilled(){

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            /*
                                TODO handle transfer info
                             */
                            Toast.makeText(getContext(), "Sending....COMPLETE!",
                                    Toast.LENGTH_SHORT).show();
                            break;

                        case DialogInterface.BUTTON_NEGATIVE:
                            //No button clicked
                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you want to transfer " + amount.getText().toString() + " to account  " + accountTo.getText() + "?").setPositiveButton("Yes", dialogClickListener)
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
        }
    }


}
