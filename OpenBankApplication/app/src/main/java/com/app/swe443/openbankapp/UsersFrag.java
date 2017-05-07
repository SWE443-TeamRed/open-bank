package com.app.swe443.openbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.app.swe443.openbankapp.Support.Account;
import com.app.swe443.openbankapp.Support.AccountTypeEnum;
import com.app.swe443.openbankapp.Support.User;

import java.util.Date;

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
    private User user;

    private MockServerSingleton mockServer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        mockServer = MockServerSingleton.getInstance();

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        //Text field for amount
         name =  (EditText) view.findViewById(R.id.first_name);
         email =  (EditText) view.findViewById(R.id.user_email);
         phone =  (EditText) view.findViewById(R.id.phone_number);
         pass = (EditText) view.findViewById(R.id.user_password);
        username = (EditText) view.findViewById(R.id.user_username);

        Button button = (Button) view.findViewById(R.id.submit_users_info);
        button.setOnClickListener(this);

        user = mockServer.getLoggedInUser();
        name.setText(user.getName().toString());
        email.setText(user.getEmail().toString());
        phone.setText(user.getPhone().toString());
        pass.setText(user.getPassword().toString());
        username.setText(user.getUsername());


        return view;
    }
    @Override
    public void onClick(View v) {
        /*
            TODO REQUEST TO SERVER TO UPDATE USER INFO
         */

        mockServer.updateUser(name.getText().toString(),email.getText().toString(),phone.getText().toString(),
                pass.getText().toString(),username.getText().toString());

    }
}