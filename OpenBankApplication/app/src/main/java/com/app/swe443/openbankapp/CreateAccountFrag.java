package com.app.swe443.openbankapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kimberly_93pc on 4/30/17.
 */

public class CreateAccountFrag extends Fragment implements View.OnClickListener {
    boolean savings = false;
    boolean checking = false;
    Button create;
    Button back;
    AccountTypeEnum type;
    public static final String REGISTER_URL = "http://54.87.197.206:8080/SparkServer/api/v1/user";

    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_NAME = "name";
    public static final String KEY_PHONE = "phoneNumber";
    public static final String KEY_BALANCE = "initialBalance";
    public static final String KEY_TYPE = "accountType";
    public static final String KEY_ADMIN = "isAdmin";


    EditText balance = null;
    double initialBalance;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_create_bank_account, container, false);

        //Open account button
        create = (Button) view.findViewById(R.id.create_account);
        create.setOnClickListener(this);
        back = (Button) view.findViewById(R.id.back_button5_5);
        back.setOnClickListener(this);


        //Text field for amount
        balance =  (EditText) view.findViewById(R.id.initail_value);

        //Radio buttons for account type
        RadioGroup rad = (RadioGroup) view.findViewById(R.id.radio_group);
        rad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch(checkedId) {
                    case R.id.checking_rad:
                        savings = true;
                        break;
                    case R.id.savings_rad:
                        checking = true;
                        break;
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            //Clicked on back button.
            case R.id.back_button5_5:
                getFragmentManager().popBackStack();
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
                if (balance.getText().toString() == null) {
                    balance.setError("Initial Balance Required.");
                    balance.requestFocus();
                } else {
                    initialBalance = Double.parseDouble(balance.getText().toString());
                    createAccount();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }

    //Creating account by making a post request to server.
    private void createAccount(){

        Bundle b = getActivity().getIntent().getExtras();
        String username = b.getString("username");
        String passoword = b.getString("password");
        String name = b.getString("name");
        String phone = b.getString("phone");
        String email = b.getString("email");


        User user = new User()
            .withName(name)
            .withPassword(passoword)
            .withPhone(phone)
            .withEmail(email)
            .withIsAdmin(false)
            .withUsername(username);

        user.withAccount(new Account()
                .withType(type)
                .withOwner(user)
                .withCreationdate(new Date())
                .withBalance(initialBalance));
        JSONObject obj = new JSONObject();

        try {
            obj.put(KEY_ADMIN, false);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity(),response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(),error.toString(),Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put(KEY_USERNAME, user.getName());
                params.put(KEY_PASSWORD, user.getPassword());
                params.put(KEY_EMAIL, user.getEmail());
                params.put(KEY_NAME, user.getName());
                params.put(KEY_PHONE,user.getPhone());
//                params.put(KEY_ADMIN, user.isIsAdmin());
//                params.put(KEY_BALANCE, );
//                params.put(KEY_TYPE, type);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);



    }

//    private void createAccount(){
        //        int newAccountNum= mockserver.getUniqueAccountNum();
//        user.withAccount(new Account()
//                .withAccountnum(newAccountNum)
//                .withType(type)
//                .withOwner(user)
//                .withCreationdate(new Date())
//                .withBalance(Double.valueOf(initalBalanceInput.getText().toString())));
//        mockserver.getBank().withCustomerUser(user);
//        completeNewAccount(newAccountNum);

//    }
}