package com.app.swe443.openbankapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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

public class OpenAccountFrag extends Fragment implements AdapterView.OnItemSelectedListener {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        JsonPersistency jsonPersistency = new JsonPersistency();
        Account account;
        //Temp code for testing create account until register creates this.
        account = new Account();

        User tina = new User()
                .withName("Tina")
                .withUserID("tina1")
                .withPassword("tinapass")
                .withIsAdmin(false);
        Account checking = new Account()
                .withAccountnum(1)
                .withOwner(tina)
                .withBalance(100)
                .withCreationdate(new Date())
                .withCredit()
                .withDebit();
        tina.login("tina1", "tinapass");

//        tina.logout();

        ///////////////////////////////////////////////////////////////////
//        account = jsonPersistency.fromJson(tina.getUserID());

        Spinner spinner = (Spinner) getView().findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Savings");
        categories.add("Checking");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_open_account, container, false);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item
        String item = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

