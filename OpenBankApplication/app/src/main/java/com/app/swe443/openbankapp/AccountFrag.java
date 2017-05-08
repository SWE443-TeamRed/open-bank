package com.app.swe443.openbankapp;

import android.app.Activity;
import android.content.DialogInterface;
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
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.swe443.openbankapp.Support.Account;

import org.json.JSONException;
import org.json.JSONObject;

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

        accountnameText.setText(accountInfo[0]);
        accountnumText.setText(accountInfo[0]);
        DecimalFormat precision = new DecimalFormat("0.00");
        balanceText.setText("$ " +precision.format(Integer.valueOf(accountInfo[1])));
        ownerText.setText(accountInfo[0]);
        typeText.setText(accountInfo[0]);
        //String newDateFormat = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm").format(account.getCreationdate());
        creationText.setText(accountInfo[0]);

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
                    int amount = Integer.valueOf(depositamountText.getText().toString());

                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    onDepositSelected(amount);
                                    setDepositFieldsVisability(0);
                                    depositButton.setVisibility(View.VISIBLE);
                                    withdrawButton.setVisibility(View.VISIBLE);
                                    DecimalFormat precision = new DecimalFormat("0.00");
                                    balanceText.setText(String.valueOf(precision.format(mCallback)));
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
                    int amount = Integer.valueOf(withdrawamountText.getText().toString());
                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case DialogInterface.BUTTON_POSITIVE:
                                    onWithdrawSelected(Integer.valueOf(withdrawamountText.getText().toString()));
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
    public void onDepositSelected(int amount) {
        System.out.println("onDepositSelected mathod initiated");

        /*
            Make deposit transfer request to server
            Required:
                toAccountId:[Account user wants to deposit to]
                amount:[Amount to deposit]
                transferType : Deposit
         */
        if(!params.isEmpty())
            params.clear();
        params.put("toAccountId",accountInfo[0]);
        params.put("amount",Integer.toString(amount));
        params.put("transferType","Deposit");
        postDepositToServer();


    }

    public void postDepositToServer(){
        StringRequest stringRequest;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://54.87.197.206:8080/SparkServer/api/v1/transaction";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("OBTAINED DEPOSITE RESPONSE");
                Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();


                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getJSONObject("request").equals("success"))
                        /*
                            Update the user's new balance in the AccountDetails main activity (fragments will need to retireve new val)
                         */
                        balanceText.setText(obj.getJSONObject("newBalance").toString());

                    else
                        Toast.makeText(getContext(),obj.getJSONObject("reason").toString(),Toast.LENGTH_LONG).show();



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





    public void onWithdrawSelected(int amount) {
        System.out.println("onWithdrawSelected mathod initiated");
        //Fufill withdraw
        /*
               Post withdraw request to server
               Required:
                toAccountId:[Account user wants to deposit to]
                amount:[Amount to deposit]
                transferType : Withdraw


         */
        if(!params.isEmpty())
            params.clear();
        params.put("toAccountId",accountInfo[0]);
        params.put("amount",Integer.toString(amount));
        params.put("transferType","Withdraw");

        postWithdrawToServer();


    }

    public void postWithdrawToServer(){
        StringRequest stringRequest;
        String url = "http://54.87.197.206:8080/SparkServer/api/v1/transaction";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("RECIEVED WITHDRAW RESPONSE FROM SERVER");
                Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();


                try {
                    JSONObject obj = new JSONObject(response);
                    if(obj.getJSONObject("request").equals("success"))
                          /*
                            Update the user's new balance in the AccountDetails main activity (fragments will need to retireve new val)
                         */
                        balanceText.setText(obj.getJSONObject("newBalance").toString());
                    else
                        Toast.makeText(getContext(),obj.getJSONObject("reason").toString(),Toast.LENGTH_LONG).show();
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
