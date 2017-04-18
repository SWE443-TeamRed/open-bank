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

import com.app.swe443.openbankapp.Support.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kimberly_93pc on 4/13/17.
 */

public class OpenAccountFrag extends Fragment implements View.OnClickListener {
    boolean savings = false;
    boolean checking = false;

    EditText balance = null;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_open_account, container, false);

        //Open account button
        Button button = (Button) view.findViewById(R.id.open_account_button);
        button.setOnClickListener(this);
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

      //If clicked on open account button.
      if(v == getView().findViewById(R.id.open_account_button))
      {
          User tina = new User().withUserID("tina1").withPassword("tinapass");
          Account newAccount = new Account()
                  .withOwner(tina)
                  .withAccountnum(1)
                  .withCreationdate(new Date());
          newAccount.withBalance(Integer.parseInt(balance.getText().toString()));
          Intent Intent = new Intent(getContext(), MainActivity.class);

          if(savings)
          {
              newAccount.withType(AccountTypeEnum.SAVINGS);
              getContext().startActivity(Intent);

          }

          else if(checking)
          {
              newAccount.withType(AccountTypeEnum.CHECKING);
              getContext().startActivity(Intent);

          }
          else
          {
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
          }

      }
    }
}

