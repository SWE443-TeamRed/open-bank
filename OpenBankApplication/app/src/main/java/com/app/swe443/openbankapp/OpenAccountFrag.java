package com.app.swe443.openbankapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpenAccountFrag extends Fragment {

    private TextView initialValue;
    private Button submitButton;
    private Spinner type;
    private MockServerSingleton mockserver;



    public OpenAccountFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_open_account, container, false);
        mockserver = MockServerSingleton.getInstance();

        initialValue = (TextView) v.findViewById(R.id.intial_value_input);
        type = (Spinner) v.findViewById(R.id.accounttypeSpinner);
        submitButton = (Button) v.findViewById(R.id.agreement);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (initialValue.getText().toString().equals("")) {
                    initialValue.setError("Initial Balance is required");
                    initialValue.requestFocus();
                } else {
                    System.out.println("Open account requested");
                    mockserver.openAccount(type.getSelectedItem().toString(),Double.valueOf(initialValue.getText().toString()));
                    getFragmentManager().popBackStack();
                }
            }
        });
        return v;
    }

}
