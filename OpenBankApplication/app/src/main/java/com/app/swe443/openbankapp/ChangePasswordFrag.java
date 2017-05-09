package com.app.swe443.openbankapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;
import static android.content.ContentValues.TAG;

/**
 * Created by kimberly_93pc on 5/3/17.
 */

public class ChangePasswordFrag extends Fragment implements View.OnClickListener {

    String URL;
    Button back1;
    Button back2;
    Button cont2;
    Button back3;
    Button cont3;
    Button sendCode;
    EditText email;
    EditText code;
    EditText newPass;
    EditText confirmPass;
    LinearLayout layout1;
    LinearLayout layout2;
    LinearLayout layout3;
    boolean codeSent = false;
    boolean emailSent = false;
    boolean newPassSent = false;
    boolean showContinue = false;
    Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_change_password, container, false);
        layout1 = (LinearLayout) view.findViewById(R.id.change_pass_content1);
        layout2 = (LinearLayout) view.findViewById(R.id.change_pass_content2);
        layout3 = (LinearLayout) view.findViewById(R.id.change_pass_content3);

        email = (EditText) view.findViewById(R.id.username_newpass);
        code = (EditText) view.findViewById(R.id.code_input);
        newPass = (EditText) view.findViewById(R.id.newPassword);
        confirmPass = (EditText) view.findViewById(R.id.confirm_NewPassword);

        back1 = (Button) view.findViewById(R.id.back_button1);
        back2 = (Button) view.findViewById(R.id.back_button2);
        cont2 = (Button) view.findViewById(R.id.continue_button2);
        back3 = (Button) view.findViewById(R.id.back_button3);
        cont3 = (Button) view.findViewById(R.id.continue_button3);
        sendCode = (Button) view.findViewById(R.id.send_code);

        sendCode.setOnClickListener(this);
        back1.setOnClickListener(this);
        back2.setOnClickListener(this);
        cont2.setOnClickListener(this);
        back3.setOnClickListener(this);
        cont3.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        intent = new Intent(getActivity(), LoginActivity.class);
        HashMap<String, String> params = new HashMap<String, String>();
        switch (v.getId()) {
            case R.id.back_button1:
                startActivity(intent);
                break;
            case R.id.back_button2:
                startActivity(intent);
                break;
            case R.id.back_button3:
                startActivity(intent);
                break;
            case R.id.send_code:
                if(TextUtils.isEmpty(email.getText())){
                    email.setError("Need email.");
                    email.requestFocus();
                } else {
                    emailSent = true;
                    //TODO change this when server is updated
                    URL = "http://54.87.197.206:8080/SparkServer/api/v1/sendEmail";
                    String emailString = email.getText().toString().trim();
                    params.put("email", emailString);
                    changeCodeRequest(URL, params);
                }
                break;
            case R.id.continue_button2:
                if(TextUtils.isEmpty(code.getText())){
                    code.setError("Need the code.");
                    code.requestFocus();
                }else{
                    emailSent = false;
                    codeSent = true;
                    //TODO change this when server is updated
                    URL = "http://54.87.197.206:8080/SparkServer/api/v1/verifyCode";
                    params.clear();
                    params.put("code", code.getText().toString());
                    changeCodeRequest(URL,params);
                }
                break;
            case R.id.continue_button3:
                String newPassString = newPass.getText().toString().trim();
                 if(TextUtils.isEmpty(newPass.getText())){
                    newPass.setError("Need the new password.");
                    newPass.requestFocus();
                } else if(TextUtils.isEmpty(confirmPass.getText())){
                    confirmPass.setError("Need to confirm the password.");
                    confirmPass.requestFocus();
                } else if (!confirmPass.getText().toString().equals(newPass.getText().toString())){
                    confirmPass.setError("Passwords do not match.");
                    confirmPass.requestFocus();
                } else{
                    codeSent = false;
                    newPassSent = true;
                    //TODO change this when server is updated
                    URL = "http://54.87.197.206:8080/SparkServer/api/v1/passwordReset";
                    params.clear();
                    params.put("id", userID);
                    params.put("newPassword", newPassString);
                    changeCodeRequest(URL,params);
                }
        }

    }

    //This makes the post request to verify username & password and login.
    StringRequest stringRequest;
    RequestQueue requestQueue;

    public void changeCodeRequest(String REGISTER_URL, Map<String, String> params){
        stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //TODO Erase latter, for testing.
                        // System.out.println("Success********"+response +"********");
                        Toast.makeText(getContext(),response,Toast.LENGTH_LONG).show();
                        try {
                            JSONObject obj = new JSONObject(response);
                            responseHandler(obj);

                        }catch(JSONException e){
                            e.printStackTrace();
                            Log.d(TAG,response);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO Erase latter, for testing.
                //System.out.println("Error********"+error +"********");
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    String answer;
    String userID;
    //Deals with the response received by the post request.
    private void responseHandler(JSONObject obj) {
        try {
            answer = obj.get("request").toString();
            if(emailSent){
                if(answer.equals("failed")){
                   if(obj.get("reason").toString().equals("email not registered to a user"))
                    {//case the email was not registered.
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Email is not registered")
                                .setMessage("Please, make sure you are using the correct email.")
                                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                    else {//Case of any other errors from server.
                       new AlertDialog.Builder(this.getContext())
                               .setTitle("Error")
                               .setMessage("Unfortunately, we have have ran in to an error.")
                               .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                   public void onClick(DialogInterface dialog, int which) {
                                       dialog.cancel();
                                       startActivity(intent);
                                   }
                               })
                               .setIcon(android.R.drawable.ic_dialog_alert)
                               .show();
                   }

                }
                else{//Success
                    showContinue = true;
                    layout1.setVisibility(View.GONE);
                    layout2.setVisibility(View.VISIBLE);
                    userID = obj.get("id").toString();
                }
            }else if(codeSent){
                if(answer.equals("failed")) {
                    if (obj.get("reason").toString().equals("code does not match")) {
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Code do no match")
                                .setMessage("Please, make sure the code is correct.")
                                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {//Case of any other errors from server.
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Error")
                                .setMessage("Unfortunately, we have have ran in to an error.")
                                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(intent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
                else{//Success.
                    layout2.setVisibility(View.GONE);
                    layout3.setVisibility(View.VISIBLE);
                }

            }else if(newPassSent){
                if(answer.equals("failed")) {
                    if (obj.get("reason").toString().equals("wrong username")){
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Code do no match")
                                .setMessage("Please, make sure ure are using the correct username.")
                                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    } else {//Case of any other errors from server.
                        new AlertDialog.Builder(this.getContext())
                                .setTitle("Error")
                                .setMessage("Unfortunately, we have have ran in to an error.")
                                .setNeutralButton("ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                        startActivity(intent);
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                } else {//Success.
                    intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d(TAG, "Problem with response");
        }
    }
}
