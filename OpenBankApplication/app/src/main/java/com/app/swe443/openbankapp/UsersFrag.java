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
import com.app.swe443.openbankapp.Support.User;

import java.util.Date;

/**
 * Created by kimberly_93pc on 4/13/17.
 */

public class UsersFrag extends Fragment implements View.OnClickListener{

    Account newAccount;
    User tina;
    EditText name;
    EditText lastName;
    EditText email;
    EditText phone;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Tep data
        tina = new User().withUserID("tina1").withPassword("tinapass");
        tina.setName("Tina");
        tina.setEmail("My@email.com");
        tina.setPhone("703-201-1193");
        newAccount = new Account()
                .withOwner(tina)
                .withAccountnum(2)
                .withCreationdate(new Date())
                .withBalance(30.00);

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);

        //Text field for amount
        EditText name =  (EditText) view.findViewById(R.id.first_name);
//        EditText lastName =  (EditText) view.findViewById(R.id.last_name);
        EditText email =  (EditText) view.findViewById(R.id.email);
        EditText phone =  (EditText) view.findViewById(R.id.phone_number);
        Button button = (Button) view.findViewById(R.id.submit_users_info);

        name.setText(tina.getName());
//      lastName.setText(tina.getName());
        phone.setText(tina.getPhone());
        email.setText(tina.getEmail());

        return view;
    }
    @Override
    public void onClick(View v) {
        //If clicked on open account button.
        if(v == getView().findViewById(R.id.submit_users_info)) {
            tina.setName(name.getText().toString());
            tina.setEmail(email.getText().toString());
            tina.setPhone(phone.getText().toString());
        }
        Intent Intent = new Intent(getContext(), MainActivity.class);
        getContext().startActivity(Intent);
    }
}