package com.app.swe443.openbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

//import com.example.shelby.appforsdmlib.R;

public class SignUp2Activity extends AppCompatActivity {

    private AutoCompleteTextView firstName;
    private AutoCompleteTextView lastName;
    private AutoCompleteTextView phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstName = (AutoCompleteTextView) findViewById(R.id.first_name);
        lastName = (AutoCompleteTextView) findViewById(R.id.last_name);
        phoneNumber = (AutoCompleteTextView) findViewById(R.id.phone_number);

        Button backButton2 = (Button) findViewById(R.id.back_button2);
        backButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp1Activity.class );
                startActivity(intent);
            }
        });
        Button continueButton2 = (Button) findViewById(R.id.continue_button2);
        continueButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (firstName.getText().toString().equals("")) {
                    firstName.setError("First name is required.");
                    firstName.requestFocus();
                } else if (lastName.getText().toString().equals("")) {
                    lastName.setError("Last name is required.");
                    lastName.requestFocus();
                } else if (phoneNumber.getText().toString().equals("")) {
                    phoneNumber.setError("Phone number is required.");
                    phoneNumber.requestFocus();
                } else {
                    Intent intent = new Intent(getApplicationContext(), SignUp3Activity.class);
                    startActivity(intent);
                }
            }
        });
    }

}
