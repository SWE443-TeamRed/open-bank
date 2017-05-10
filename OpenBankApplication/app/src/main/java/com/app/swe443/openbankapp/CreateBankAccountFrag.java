package com.app.swe443.openbankapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

/**
 * Created by kimberly_93pc on 4/13/17.
 */

public class CreateBankAccountFrag extends Fragment implements View.OnClickListener{

    private EditText nameOfUserInput ;
    private EditText phoneInput;
    private EditText emailInput;
    private EditText usernameInput;
    private EditText confirmpasswordInput;
    private EditText passwordInput;
    private CheckBox isAdminCheckBox;
    private EditText initalBalanceInput;
    private Spinner initalAccountType;

    //Form layout to set blank for account completion message
    private LinearLayout createAccountFormLayout;
    private LinearLayout formButtonLayout;
    private LinearLayout createAccountSuccessLayout;

    private TextView complettionMessage;
    private Button completeCreateAccountButton;

    private Button cancelCreateAccount;
    private Button confirmCreateAccount;

    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_create_bank_account, container, false);


        activity = (LoginActivity) getActivity();

        //Create form layouts
        createAccountFormLayout = (LinearLayout) v.findViewById(R.id.createAccountFormLayout);
        formButtonLayout = (LinearLayout) v.findViewById(R.id.buttonlayout);
        createAccountSuccessLayout = (LinearLayout) v.findViewById(R.id.createAccountSuccessLayout);
        createAccountSuccessLayout.setVisibility(View.GONE);

        //Form's fields
        nameOfUserInput = (EditText) v.findViewById(R.id.nameOfUserInput);
        phoneInput = (EditText) v.findViewById(R.id.phoneInput);
        emailInput = (EditText) v.findViewById(R.id.emailInput);
        usernameInput = (EditText) v.findViewById(R.id.usernameInput);
        confirmpasswordInput = (EditText) v.findViewById(R.id.confirmPasswordInput);
        passwordInput = (EditText) v.findViewById(R.id.passwordInput);
        isAdminCheckBox = (CheckBox) v.findViewById(R.id.adminCheckBox);
        initalBalanceInput = (EditText) v.findViewById(R.id.initialBalanceInput);
        initalAccountType = (Spinner) v.findViewById(R.id.accounttypeSpinner);

        cancelCreateAccount = (Button) v.findViewById(R.id.cancelCreateAccount);
        cancelCreateAccount.setOnClickListener(this);

        confirmCreateAccount = (Button) v.findViewById(R.id.confirmCreateAccount);
        confirmCreateAccount.setOnClickListener(this);

        complettionMessage = (TextView) v.findViewById(R.id.transferCompleteMessage);
        completeCreateAccountButton = (Button) v.findViewById(R.id.completeTransferButton);
        completeCreateAccountButton.setOnClickListener(this);


        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancelCreateAccount:
                System.out.println("Cancel Create Account Requested");
                getFragmentManager().popBackStack();

                break;
            case R.id.confirmCreateAccount:
                boolean incomplete = false;
                System.out.println("Create Account Requested");
                if (nameOfUserInput.getText().toString().equals("")) {
                    nameOfUserInput.setError("Missing your name");
                    //Toast.makeText(getContext(), "Missing your name", Toast.LENGTH_SHORT).show();
                    incomplete = true;
                }
                if (phoneInput.getText().toString().equals("")) {
                    phoneInput.setError("Missing phone number");
                    //Toast.makeText(getContext(), "Missing Phone", Toast.LENGTH_SHORT).show();
                    incomplete = true;
                }
                if (emailInput.getText().toString().equals("")) {
                    emailInput.setError("Missing Email");
                    //Toast.makeText(getContext(), "Missing Email", Toast.LENGTH_SHORT).show();
                    incomplete = true;
                }
                if (usernameInput.getText().toString().equals("")) {
                   usernameInput.setError("Missing Username");
                    //Toast.makeText(getContext(), "Missing Username",Toast.LENGTH_SHORT).show();
                    incomplete = true;
                }
                if (passwordInput.getText().toString().equals("")){
                   passwordInput.setError("Missing Password");
                    // Toast.makeText(getContext(), "Missing Password",Toast.LENGTH_SHORT).show();
                    incomplete = true;
                }
                if (confirmpasswordInput.getText().toString().equals("")) {
                   confirmpasswordInput.setError("Confirm your password");
                    // Toast.makeText(getContext(), "Confirm your passwrod",                            Toast.LENGTH_SHORT).show();
                    incomplete = true;
                }
                if (initalBalanceInput.getText().toString().equals("")) {
                   initalBalanceInput.setError("Please enter an initial balance");
                    // Toast.makeText(getContext(), "Please enter an initial balance of your first account", Toast.LENGTH_SHORT).show();
                    incomplete = true;
                }
                if (!(passwordInput.getText().toString().equals(confirmpasswordInput.getText().toString()))) {
                    //Toast.makeText(getContext(), "Passwords dont match", Toast.LENGTH_SHORT).show();
                    passwordInput.setError("Passwords don't match");
                    incomplete = true;
                }
                if(incomplete)
                    break;

//                int newAccountNum = mockserver.createBankAccount(
//                        isAdminCheckBox.isChecked(),
//                        initalAccountType.getSelectedItem().toString(),
//                        nameOfUserInput.getText().toString(),
//                        passwordInput.getText().toString(),
//                        phoneInput.getText().toString(),
//                        emailInput.getText().toString(),
//                        usernameInput.getText().toString(),
//                        Double.valueOf(initalBalanceInput.getText().toString()));



//               completeNewAccount(newAccountNum);

                break;
            case R.id.completeTransferButton:
                //Go to login
                getFragmentManager().popBackStack();
        }
    }

    public void completeNewAccount(int newAccountNum){
//        System.out.println("Added User, bank now has "+mockserver.getBank().getCustomerUser().size()+" users");
        createAccountFormLayout.setVisibility(View.GONE);
        formButtonLayout.setVisibility(View.GONE);
        createAccountSuccessLayout.setVisibility(View.VISIBLE);
        complettionMessage.setText("Your account is created! Your account number is "+newAccountNum+". Save this for your records.");



    }
}