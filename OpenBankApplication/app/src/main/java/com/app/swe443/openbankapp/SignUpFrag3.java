package com.app.swe443.openbankapp;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


public class SignUpFrag3 extends android.support.v4.app.Fragment  implements View.OnClickListener {
    private AutoCompleteTextView AddressLine1;
    private AutoCompleteTextView AddressLine2;
    private AutoCompleteTextView City;
    private AutoCompleteTextView State;
    Button backButton3;
    Button continueButton3;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up3, container, false);

        //Back & Continue buttons for third sign up page.
        backButton3 = (Button) view.findViewById(R.id.back_button3_4);
        continueButton3 = (Button) view.findViewById(R.id.continue_button3_4);
        backButton3.setOnClickListener(this);
        continueButton3.setOnClickListener(this);

        //User's address.
        AddressLine1 = (AutoCompleteTextView) view.findViewById(R.id.address_line1);
        AddressLine2 = (AutoCompleteTextView) view.findViewById(R.id.address_line2);
        City = (AutoCompleteTextView) view.findViewById(R.id.city);
        State = (AutoCompleteTextView) view.findViewById(R.id.state);

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        switch(v.getId()){
            case R.id.back_button3_4:
//                fragmentTransaction.replace(R.id.drawer_layout, new SignUpFrag2(), "SignUpFrag2");
//                fragmentTransaction.commit();
                getFragmentManager().popBackStack();
                break;

            case R.id.continue_button3_4:
                if (AddressLine1.getText().toString().equals("")) {
                    AddressLine1.setError("Address is required.");
                    AddressLine1.requestFocus();
                }
                else if (City.getText().toString().equals("")) {
                    City.setError("City is required.");
                    City.requestFocus();
                }
                else if (State.getText().toString().equals("")) {
                    State.setError("State is required.");
                    State.requestFocus();
                }
                else {
                    fragmentTransaction.add(R.id.drawer_layout, new SignUpFrag4(), "SignUpFrag4");
                    fragmentTransaction.hide(this);
                    fragmentTransaction.addToBackStack(this.getClass().getName());
                    fragmentTransaction.commit();
                }break;
        }
    }
}
