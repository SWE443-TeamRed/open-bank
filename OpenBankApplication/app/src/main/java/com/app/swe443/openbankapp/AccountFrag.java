package com.app.swe443.openbankapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class AccountFrag extends Fragment implements View.OnClickListener {
    private TextView accountnameText;
    private TextView accountnumText;
    private TextView balanceText;
    private TextView ownerText;
    private TextView typeText;
    private TextView creationText;
    private AccountDetails activity;
    private OnAccountFragCallbackListener mCallback;


    private Button depositButton;
    private Button withdrawButton;
    private LinearLayout depositWithdrawLayout;
    private ProgressBar mProgressView;
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
    private String[] accountInfo;

    private RequestQueue queue;
    private HashMap<String, String> params;


    // Container Activity must implement this interface
    public interface OnAccountFragCallbackListener {
        public String[] getAccountInfo();
        public void updateAccountInfo(String[] accountInfo);


    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnAccountFragCallbackListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAccountsCallbackListener ");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View v =  inflater.inflate(R.layout.fragment_account,container,false);


        //Server request queue
        queue = Volley.newRequestQueue(getContext());
        //Server request params storage
        params = new HashMap<String, String>();

        //Get the selected account info
        // [0]= accountnum
        // [1] = balance
        // [2] = type
        accountInfo = mCallback.getAccountInfo();
        System.out.println("DISPLAYING ACCOUNT WITH NUMBER "+ accountInfo[0]);


        depositWithdrawLayout = (LinearLayout) v.findViewById(R.id.depositWithdraw);
        mProgressView = (ProgressBar) v.findViewById(R.id.depositwithdraw_progress);

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

       // ownerText = (TextView) v.findViewById(R.id.ownerText);
        typeText = (TextView) v.findViewById(R.id.typeText);
       // creationText = (TextView) v.findViewById(R.id.creationText);

        accountnameText.setText(accountInfo[2]);
        accountnumText.setText(accountInfo[0]);
        DecimalFormat precision = new DecimalFormat("0.00");

        balanceText.setText("$ " +convertBigInt(accountInfo[1]));
        //ownerText.setText(accountInfo[0]);
        typeText.setText(accountInfo[0]);

       // creationText.setText(accountInfo[0]);

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
                    depositamountText.setError("Please enter a value");

                } else {
                    String amount = Double.valueOf(depositamountText.getText().toString()).toString();

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    onDepositSelected(amount);
                                    showProgress(true);

                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to deposit " + amount + " into your " + accountInfo[2] + " account?").setPositiveButton("Yes", dialogClickListener)
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
                    String amount = Double.valueOf(withdrawamountText.getText().toString()).toString();
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    onWithdrawSelected(amount);
                                    setWithdrawFieldsVisability(0);
                                    depositButton.setVisibility(View.VISIBLE);
                                    withdrawButton.setVisibility(View.VISIBLE);
                                    break;

                                case DialogInterface.BUTTON_NEGATIVE:
                                    //No button clicked
                                    break;
                            }
                        }
                    };
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("Are you sure you want to withdraw " + amount + " from your " + accountInfo[2] + " account?")
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

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            depositWithdrawLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            depositWithdrawLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    depositWithdrawLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            depositWithdrawLayout.setVisibility(show ? View.GONE : View.VISIBLE);
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


    //Callback when the user deposits an amount on in AccountFrag
    public void onDepositSelected(String amount) {
        System.out.println("onDepositSelected mathod initiated");

        /*
            Make deposit transfer request to server
            Required:
                toAccount:[Account user wants to deposit to]
                amount:[Amount to deposit]
                transferType : DEPOSIT
         */
        if(!params.isEmpty())
            params.clear();
        params.put("toAccount",accountInfo[0]);
        params.put("amount",formatUserAmountInput(amount));
        params.put("transferType","DEPOSIT");
        postDepositToServer();


    }
    public String convertBigInt(String b){
        String s = "";
        if(b.length() > 9)
            s = b.substring(0,b.length()-9)+"."+b.substring(b.length()-9,b.length()-7);
        else if(b.length() == 9)
            s = "0."+b.substring(0,b.length()-7);
        else if(b.length() == 8)
            s= "0.0"+b.substring(0,b.length()-7);
        else if(b.length() <= 7)
            s= "0.00";
        return s;
    }
    public void postDepositToServer(){
        StringRequest stringRequest;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://54.87.197.206:8080/SparkServer/api/v1/transaction";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("OBTAINED DEPOSITE RESPONSE");
                //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();

                System.out.println("DEPOSIT REQUEST RESPONSE "+ response);
                try {
                    JSONObject obj = new JSONObject(response);
                    String request = obj.get("request").toString();
                    if(request.equals("success")) {
                        /*
                            Update the user's new balance in the AccountDetails main activity (fragments will need to retireve new val)
                         */
                        showProgress(false);
                        setDepositFieldsVisability(0);
                        depositButton.setVisibility(View.VISIBLE);
                        withdrawButton.setVisibility(View.VISIBLE);
                       // DecimalFormat precision = new DecimalFormat("0.00");
                        String newBalance = (obj.get("balance").toString());
                        balanceText.setText("$ "+ convertBigInt(newBalance));
                        accountInfo[1] = newBalance;
                        //Update balance in the parent acitivty so it displays on other views
                        mCallback.updateAccountInfo(accountInfo);

                    }else {
                       // Toast.makeText(getContext(), obj.get("reason").toString(), Toast.LENGTH_LONG).show();
                        showProgress(false);
                        setDepositFieldsVisability(0);
                        depositButton.setVisibility(View.VISIBLE);
                        withdrawButton.setVisibility(View.VISIBLE);
                    }



                }catch(JSONException e){
                    e.printStackTrace();
                    Log.d(TAG,response);
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        System.out.println("REQUESTING DEPOSIT TRANSACTION");
        queue.add(stringRequest);
    }


    public String formatServerBalance(String amount){
        String temp2 = amount.substring(0,amount.length()-7);
        String decimal = temp2.substring(temp2.length()-2,temp2.length());
        String whole = temp2.substring(0,temp2.length()-2);
        return whole+"."+decimal;
    }

    public String formatUserAmountInput(String amount){
        String[] binput = {" "," "};
        //Balance contains cents
        if(amount.contains(".")) {
            binput = amount.split("\\.");
            System.out.println("SIZE IS "+binput.length);
            //Toast.makeText(getContext(),binput.length,Toast.LENGTH_LONG).show();
            //Format decimal values
            if(binput[1].length()==1)
                binput[1] = binput[1]+"0";
            else if(binput[1].length()==0)
                binput[1] = "00";
            else{
                StringBuilder s = new StringBuilder();
                s.append(binput[1].charAt(0)).append(binput[1].charAt(1));
                binput[1] = s.toString();
            }
        }
        //Balance is a whole number
        else {
            binput[0] = amount;
            binput[0] = binput[0].replaceAll("[$,.]", "");
            binput[1] = "00";
        }
        StringBuilder finalinputbalance = new StringBuilder();
        finalinputbalance.append(binput[0]).append(binput[1]).append("0000000");
        BigInteger initialBalance = new BigInteger(finalinputbalance.toString());
        System.out.println("USER INITIAL BALANCE IS "+ initialBalance.toString());
        return initialBalance.toString();
    }

    public void onWithdrawSelected(String amount) {
        System.out.println("onWithdrawSelected mathod initiated");
        //Fufill withdraw
        /*
               Post withdraw request to server
               Required:
                toAccount:[Account user wants to deposit to]
                amount:[Amount to deposit]
                transferType : WITHDRAW

         */
        if(!params.isEmpty())
            params.clear();
        params.put("fromAccount",accountInfo[0]);
        params.put("amount",formatUserAmountInput(amount));
        params.put("transferType","WITHDRAW");

        postWithdrawToServer();


    }

    public void postWithdrawToServer(){
        StringRequest stringRequest;
        String url = "http://54.87.197.206:8080/SparkServer/api/v1/transaction";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("RECIEVED WITHDRAW RESPONSE FROM SERVER "+ response);
                //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();


                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("request").equals("success")){
                          /*
                            Update the user's new balance in the AccountDetails main activity (fragments will need to retireve new val)
                         */
                        showProgress(false);
                        setDepositFieldsVisability(0);
                        depositButton.setVisibility(View.VISIBLE);
                        withdrawButton.setVisibility(View.VISIBLE);
                        // DecimalFormat precision = new DecimalFormat("0.00");
                        String newBalance = (obj.get("balance").toString());
                        balanceText.setText("$ "+ convertBigInt(newBalance));
                        accountInfo[1] = newBalance;
                        //Update account info in parent activity
                        mCallback.updateAccountInfo(accountInfo);



                    }else {
                       // Toast.makeText(getContext(), obj.get("reason").toString(), Toast.LENGTH_LONG).show();
                        showProgress(false);
                        setDepositFieldsVisability(0);
                        depositButton.setVisibility(View.VISIBLE);
                        withdrawButton.setVisibility(View.VISIBLE);
                }
                }catch(JSONException e){
                    e.printStackTrace();
                    Log.d(TAG,response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        System.out.println("REQUESTING WITHDRAW TRANSACTION");
        queue.add(stringRequest);
    }

}
