package com.app.swe443.openbankapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.swe443.openbankapp.Support.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by kimberly_93pc on 4/30/17.
 */

public class OpenFirstAccountFrag extends Fragment implements View.OnClickListener {
    boolean savings = false;
    boolean checking = false;
    BigInteger initialBalance;
    String REGISTER_URL;
    Map<String, String> params;

    Button create;
    Button back;
    Button complete;

    AccountTypeEnum type;
    User user;
    Account account;

    LinearLayout createAccountSuccessLayout;
    LinearLayout createAccountLayout;
    TextView complettionMessage;
    EditText balance = null;

    ArrayList<String>values;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_open_first_account, container, false);
        createAccountLayout = (LinearLayout) view.findViewById(R.id.create_accountFrom);
        createAccountSuccessLayout = (LinearLayout) view.findViewById(R.id.createAccountSuccessLayout);
        createAccountSuccessLayout.setVisibility(View.GONE);
        complettionMessage = (TextView) view.findViewById(R.id.transferCompleteMessage);

        //Open account button
        create = (Button) view.findViewById(R.id.create_account);
        create.setOnClickListener(this);
        back = (Button) view.findViewById(R.id.back_button5_5);
        back.setOnClickListener(this);
        complete = (Button) view.findViewById(R.id.completeTransferButton);
        complete.setOnClickListener(this);

        //Text field for amount
        balance =  (EditText) view.findViewById(R.id.initail_value);

        //Radio buttons for account type
        RadioGroup rad = (RadioGroup) view.findViewById(R.id.radio_group);
        rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.checking_rad:
                        checking = true;
                        break;
                    case R.id.savings_rad:
                        savings = true;
                        break;
                }
            }
        });

        values = getArguments().getStringArrayList("signupData");
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //Clicked on back button.
            case R.id.back_button5_5:
                getFragmentManager().popBackStack();
                break;

            case R.id.completeTransferButton:
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                break;

            //If clicked on open account button.
            case R.id.create_account:
                if (savings)
                    type = AccountTypeEnum.SAVINGS;
                else if (checking)
                    type = AccountTypeEnum.CHECKING;

                else {//Alert for not checking the rad button.
                    new AlertDialog.Builder(this.getContext())
                            .setTitle("Missing Field")
                            .setMessage("Please select account type")
                            .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .show();
                }//Initial balance must have a value.
                if (balance.getText().toString().equals("")) {
                    balance.setError("Initial Balance Required.");
                    balance.requestFocus();
                }
                else {
                    //First set info before calling OpenAccountPostRequest to make post request.
                    //Initial account balance must be BigInteger
                    String userbalanceInput = balance.getText().toString();
                    Toast.makeText(getContext(),userbalanceInput,Toast.LENGTH_LONG).show();
                    System.out.println("USER INPUT WAS "+userbalanceInput);
                    String[] binput = {" "," "};
                    //Balance contains cents
                    if(userbalanceInput.contains(".")) {
                        binput = userbalanceInput.split("\\.");
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
                        binput[0] = userbalanceInput;
                        binput[0] = binput[0].replaceAll("[$,.]", "");
                        binput[1] = "00";
                    }
                    StringBuilder finalinputbalance = new StringBuilder();
                    finalinputbalance.append(binput[0]).append(binput[1]).append("0000000");
                    initialBalance = new BigInteger(finalinputbalance.toString());
                    System.out.println("USER INITIAL BALANCE IS "+ initialBalance.toString());






                    String username = values.get(0);
                    String password = values.get(1);
                    String email = values.get(2);
                    String name = values.get(3);
                    String phone = values.get(4);

                    //To create the user.
                    REGISTER_URL = "http://54.87.197.206:8080/SparkServer/api/v1/user";

                    params = new HashMap<String, String>();
                    params.put("username", username);
                    params.put("password", password);
                    params.put("name", name);
                    params.put("phoneNumber", phone);
                    params.put("isAdmin", "false");
                    params.put("email", email);

                    openAccountPostRequest(false, REGISTER_URL, getActivity(), params);
                    // TODO: 5/1/17 Erase latter
//                    createAccount("1136056093");//For testing

                }
                break;
        }
    }

    public void completeNewAccount(int newAccountNum){
        createAccountLayout.setVisibility(View.GONE);
        createAccountSuccessLayout.setVisibility(View.VISIBLE);
        complettionMessage.setText("Your account is created! Your account number is "+newAccountNum+". Save this for your records.");
    }

    //Function that makes the post request.
    StringRequest stringRequest;
    RequestQueue requestQueue;
    public void openAccountPostRequest (boolean accountCreated, String REGISTER_URL, Context activity, Map<String, String> params){
            stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //TODO Erase latter, for testing.
//                            System.out.println("Success********"+response +"********");
                            Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
                            try {
                                JSONObject obj = new JSONObject(response);

                                //Created user, still need to create account.
                                if(!accountCreated){
                                    response = obj.get("userID").toString();
                                    createAccount(response);
                                }
                                //Account created, give account number to user and change views.
                                else if(accountCreated)completeNewAccount(Integer.parseInt(obj.get("accountNum").toString()));

                            }catch(JSONException e){
                                e.printStackTrace();
                                Log.d(TAG,response);
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //TODO Erase latter, for testing.
                            System.out.println("Error********"+error +"********");
                            Toast.makeText(activity,error.toString(),Toast.LENGTH_LONG).show();
                        }
                    }){
                @Override
                protected Map<String,String> getParams(){
                    return params;
                }
            };
            requestQueue = Volley.newRequestQueue(activity);
            requestQueue.add(stringRequest);
    }

    public void createAccount(String response) {
        //To create the account from the response userid.
        HashMap<String, String> params = new HashMap<String, String>();
        String REGISTER_URL2 = "http://54.87.197.206:8080/SparkServer/api/v1/account";

        params.put("id", response);
        //TODO Erase latter, for testing.
        System.out.println(response + " "+type.toString() + " "+  initialBalance.toString());
        params.put("accountType", type.toString());
        params.put("initialBalance", initialBalance.toString());

        openAccountPostRequest(true, REGISTER_URL2, getContext(), params);
    }
}