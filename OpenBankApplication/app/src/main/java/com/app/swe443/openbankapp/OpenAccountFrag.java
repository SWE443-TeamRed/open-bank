package com.app.swe443.openbankapp;


import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import org.json.JSONException;
import org.json.JSONObject;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by kimberly_93pc on 4/13/17.
 */

public class OpenAccountFrag extends Fragment implements View.OnClickListener{

    //Form layout to set blank for account completion message
    private LinearLayout createAccountSuccessLayout;

    private Button create;
    private Button back;
    private Button complete;
    private EditText balance;

    private boolean checking = false;
    private boolean savings = false;

    private BigInteger initialBalance;
    private String type;
    private HashMap<String, String> params;
    private String REGISTER_URL;
    private FragmentTransaction transaction;
    private FragmentManager fm;
    private OnOpenAccountSelected mCallback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_open_account, container, false);
        createAccountSuccessLayout = (LinearLayout) view.findViewById(R.id.createAccountSuccessLayout);
        createAccountSuccessLayout.setVisibility(View.GONE);

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
        fm = getFragmentManager();
        mCallback = (OnOpenAccountSelected)getActivity();
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //Clicked on back button.
            case R.id.back_button5_5:
                transaction = fm.beginTransaction();
                transaction.add(new HomeFrag(), "HomeFrag");
                break;
            case R.id.completeTransferButton:
                transaction = fm.beginTransaction();
                transaction.add(new HomeFrag(), "HomeFrag");
                break;
            //If clicked on open account button.
            case R.id.create_account:
                if (savings)
                    type = "SAVINGS";
                else if (checking)
                    type = "CHECKING";
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
                } else {
                    //First set info before calling OpenAccountPostRequest to make post request.
                    //Initial account balance must be BigInteger
                    String userbalanceInput = balance.getText().toString();
//                    Toast.makeText(getContext(),userbalanceInput,Toast.LENGTH_LONG).show();
//                    System.out.println("USER INPUT WAS "+userbalanceInput);
                    String[] binput = {" ", " "};
                    //Balance contains cents
                    if (userbalanceInput.contains(".")) {
                        binput = userbalanceInput.split("\\.");
                        System.out.println("SIZE IS " + binput.length);
                        //Toast.makeText(getContext(),binput.length,Toast.LENGTH_LONG).show();
                        //Format decimal values
                        if (binput[1].length() == 1)
                            binput[1] = binput[1] + "0";
                        else if (binput[1].length() == 0)
                            binput[1] = "00";
                        else {
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
                    System.out.println("USER INITIAL BALANCE IS " + initialBalance.toString());

                    //To create the user.
                    params = new HashMap<String, String>();
                    REGISTER_URL = "http://54.87.197.206:8080/SparkServer/api/v1/account";
                    String id = mCallback.getUsersInfo()[4];
                    params.put("id", id);
                    params.put("accountType", type);
                    params.put("initialBalance", initialBalance.toString());
                    openAccountPostRequest(false, REGISTER_URL, getActivity(), params);
                }
                break;
        }
    }
        //Function that makes the post request.
        StringRequest stringRequest;
        RequestQueue requestQueue;
    public void openAccountPostRequest (boolean accountCreated, String REGISTER_URL, Context
            activity, Map<String, String> params){
        stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(activity,response,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            mCallback.updateUserAccounts();
//                            Toast.makeText(activity,obj.get("accountNum").toString(),Toast.LENGTH_LONG).show();
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
    // Container Activity must implement this interface
    public interface OnOpenAccountSelected {
        public String[] getUsersInfo();
        public void updateUserAccounts();
    }
}