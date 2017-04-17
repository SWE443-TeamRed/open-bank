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

public class SignUp3Activity extends AppCompatActivity {
    private AutoCompleteTextView AddressLine1;
    private AutoCompleteTextView AddressLine2;
    private AutoCompleteTextView City;
    private AutoCompleteTextView State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AddressLine1 = (AutoCompleteTextView) findViewById(R.id.address_line1);
        AddressLine2 = (AutoCompleteTextView) findViewById(R.id.address_line2);
        City = (AutoCompleteTextView) findViewById(R.id.city);
        State = (AutoCompleteTextView) findViewById(R.id.state);

        Button backButton3 = (Button) findViewById(R.id.back_button3);
        backButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SignUp2Activity.class );
                startActivity(intent);
            }
        });
        Button continueButton3 = (Button) findViewById(R.id.continue_button3);
        continueButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if (AddressLine1.getText().toString().equals("")) {
                        AddressLine1.setError("Address is required.");
                        AddressLine1.requestFocus();
                    } else if (City.getText().toString().equals("")) {
                        City.setError("City is required.");
                        City.requestFocus();
                    } else if (State.getText().toString().equals("")) {
                        State.setError("State is required.");
                        State.requestFocus();
                    } else {
                        Intent intent = new Intent(getApplicationContext(), SignUp4Activity.class);
                        startActivity(intent);
                    }
            }
        });
    }
}
