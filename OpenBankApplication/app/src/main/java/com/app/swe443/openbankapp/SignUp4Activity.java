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
import android.widget.RadioButton;
import android.widget.RadioGroup;

//import com.example.shelby.appforsdmlib.R;

public class SignUp4Activity extends AppCompatActivity {

    private AutoCompleteTextView InitialValue;
    RadioGroup rg;
    RadioButton rb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_4);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rg = (RadioGroup) findViewById(R.id.radio_group);

        InitialValue = (AutoCompleteTextView) findViewById(R.id.initail_value);
        Button agreeButton = (Button) findViewById(R.id.agreement);
        agreeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InitialValue.getText().toString().equals("")) {
                    InitialValue.setError("This is required.");
                    InitialValue.requestFocus();
                }else {
                    Intent intent = new Intent(getApplicationContext(), HomeFrag.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void rbclick(View v)
    {
        int radiobuttons = rg.getCheckedRadioButtonId();
        rb = (RadioButton)findViewById(radiobuttons);
    }

}
