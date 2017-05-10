package com.app.swe443.openbankapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.swe443.openbankapp.Support.Account;
import com.app.swe443.openbankapp.Support.AccountTypeEnum;
import com.app.swe443.openbankapp.Support.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by kimberly_93pc on 4/13/17.
 */

public class UsersFrag extends Fragment implements View.OnClickListener{

    private User tina;
    private EditText name;
    private EditText email;
    private EditText phone;
    private EditText pass;
    private EditText username;

    private LinearLayout contentLayout;
    private ProgressBar progressBar;

    private UsersFrag.OnUserFragMethodSelectedListener mCallback;
    private String[] userInfo;
    private RequestQueue queue;
    private HashMap<String,String> params;
    private String userID;


    // Main Activity must implement this interface in order to communicate with HomeFrag
    public interface OnUserFragMethodSelectedListener {
        public String[] getAllUserInfo();
        public void updateAllUserInfo(String[] accountInfo);

    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (UsersFrag.OnUserFragMethodSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement UsersFrag.OnUserFragMethodSelectedListener ");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        contentLayout = (LinearLayout) view.findViewById(R.id.content);
        progressBar = (ProgressBar) view.findViewById(R.id.userInfoprogress);

        //Server request queue
        queue = Volley.newRequestQueue(getContext());
        params = new HashMap<String, String>();
        //Text field for amount
         name =  (EditText) view.findViewById(R.id.first_name);
         email =  (EditText) view.findViewById(R.id.user_email);
         phone =  (EditText) view.findViewById(R.id.phone_number);
         //pass = (EditText) view.findViewById(R.id.user_password);
        username = (EditText) view.findViewById(R.id.user_username);

        Button button = (Button) view.findViewById(R.id.submit_users_info);
        button.setOnClickListener(this);

        /*
         userInfo[0] = name;
        userInfo[1] = username;
        userInfo[2] = email;
        userInfo[3] = phone;
         */
        userInfo = mCallback.getAllUserInfo();

        name.setText(userInfo[0]);
        email.setText(userInfo[2]);
        phone.setText(userInfo[3]);
        username.setText(userInfo[1]);
        userID = userInfo[4];


        return view;
    }
    @Override
    public void onClick(View v) {
        /*
            TODO REQUEST TO SERVER TO UPDATE USER INFO
         */

        showProgress(true);
        if(!params.isEmpty())
            params.clear();
        /*
        id : [User id] (Required)
        name : [User’s name] (Optional)
        email : [User email] (Optional)
        phone : [User phone Number] (Option && May remove this in the future)
        password : [User password] (Optional)
        username : [User username] (Optional)

         */
        params.put("id",userID);
        params.put("name",name.getText().toString());
        params.put("email",email.getText().toString());
        params.put("phone",phone.getText().toString());
        params.put("username",username.getText().toString());

        postUserInfoToServer();


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

            contentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            contentLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    contentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            contentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    public void postUserInfoToServer(){
        StringRequest stringRequest;
        String url = "http://54.87.197.206:8080/SparkServer/api/v1/user/update";
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("RECIEVED UPDATE USER INFO RESPONSE "+ response);
                //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();


                try {
                    JSONObject obj = new JSONObject(response);
                    if (obj.get("request").equals("success")){
                          /*
                            Update the user's new balance in the AccountDetails main activity (fragments will need to retireve new val)
                         */
                        userInfo[0] = name.getText().toString();
                        userInfo[2] =email.getText().toString();
                        userInfo[3] = phone.getText().toString();
                        userInfo[1] = username.getText().toString();
                        mCallback.updateAllUserInfo(userInfo);
                        getFragmentManager().popBackStack();
                        showProgress(false);



                    }else {
                        //Toast.makeText(getContext(), obj.get("reason").toString(), Toast.LENGTH_LONG).show();
                        showProgress(false);
                        new AlertDialog.Builder(getContext())
                                .setTitle("Unable to update data")
                                .setMessage(obj.get("reason").toString())
                                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                })
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
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
        System.out.println("REQUESTING USER UPDATE INFO TO SERVER");
        queue.add(stringRequest);
    }
}