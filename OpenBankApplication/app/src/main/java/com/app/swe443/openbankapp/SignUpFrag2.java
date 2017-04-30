package com.app.swe443.openbankapp;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;


public class SignUpFrag2 extends android.support.v4.app.Fragment implements View.OnClickListener{

    private AutoCompleteTextView firstName;
    private AutoCompleteTextView lastName;
    private AutoCompleteTextView phoneNumber;
    Button backButton2;
    Button continueButton2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sign_up2, container, false);

        //Back & Continue buttons for second sign up page.
        backButton2 = (Button) view.findViewById(R.id.back_button2_4);
        continueButton2 = (Button) view.findViewById(R.id.continue_button2_4);
        backButton2.setOnClickListener(this);
        continueButton2.setOnClickListener(this);

        //First and last Name  & phone number.
        firstName = (AutoCompleteTextView) view.findViewById(R.id.first_name);
        lastName = (AutoCompleteTextView) view.findViewById(R.id.last_name);
        phoneNumber = (AutoCompleteTextView) view.findViewById(R.id.phone_number);

        // Inflate the layout for this fragment
        return view;
    }


        @Override
        public void onClick(View v) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            switch (v.getId()){
                case R.id.back_button2_4:
//                    fragmentTransaction.replace(R.id.drawer_layout,  new SignUpFrag1(), "SignUpFrag1 ");
//                    fragmentTransaction.commit();
                    getFragmentManager().popBackStack();
                    break;

                case R.id.continue_button2_4:
                    if (firstName.getText().toString().equals("")) {
                        firstName.setError("First name is required.");
                        firstName.requestFocus();
                    }
                    else if (lastName.getText().toString().equals("")) {
                        lastName.setError("Last name is required.");
                        lastName.requestFocus();
                    }
                    else if (phoneNumber.getText().toString().equals("")) {
                        phoneNumber.setError("Phone number is required.");
                        phoneNumber.requestFocus();
                    }
                    else {
                        LoginActivity activity = (LoginActivity) getActivity();
                        activity.setSingnUp2(firstName.getText().toString() + " "+
                                lastName.getText().toString(), phoneNumber.getText().toString());

                        fragmentTransaction.add(R.id.drawer_layout,  new SignUpFrag3(), "SignUpFrag3");
                        fragmentTransaction.hide(this);
                        fragmentTransaction.addToBackStack(this.getClass().getName());
                        fragmentTransaction.commit();
                    }
                break;
            }
        }

}
