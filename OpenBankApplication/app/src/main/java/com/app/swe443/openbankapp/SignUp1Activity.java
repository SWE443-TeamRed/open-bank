package com.app.swe443.openbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.swe443.openbankapp.R.id.email;

//import com.example.shelby.appforsdmlib.R;

public class SignUp1Activity extends AppCompatActivity {

    private AutoCompleteTextView userNameV;
    private EditText emailV;
    private AutoCompleteTextView pass_word;
    private EditText comfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Button backButton1 = (Button) findViewById(R.id.back_button1);
        backButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        userNameV = (AutoCompleteTextView) findViewById(R.id.user_name);
        emailV = (EditText) findViewById(email);

        pass_word = (AutoCompleteTextView) findViewById(R.id.password);
        comfirmPassword = (EditText) findViewById(R.id.password1);


        Button continueButton1 = (Button) findViewById(R.id.continue_button1);
        continueButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateEmail(emailV.getText().toString())) {
                    emailV.setError("Invalid Email.");
                    emailV.requestFocus();
                }else if(! validateuserName(userNameV.getText().toString())) {
                    userNameV.setError("Need more then 4 characters.");
                    userNameV.requestFocus();
                }else if(!validatePassword(pass_word.getText().toString())){
                    pass_word.setError("Invalid password.");
                    pass_word.requestFocus();
                }else if(!pass_word.getText().toString().equals(comfirmPassword.getText().toString())) {
                    comfirmPassword.setError("Password does not match.");
                    comfirmPassword.requestFocus();
                }else {
                    Intent intent = new Intent(getApplicationContext(), SignUp2Activity.class);
                    startActivity(intent);
                }
            }
        });
    }

    protected boolean validateEmail(String email)
    {
        String emailPattern = "^[_A-Za-z0-9_\\+]+(\\.[_A-Za-z0-9_]+)*@"+"[A-Za-z0-9]+(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    protected boolean validatePassword(String password)
    {
        if(password!= null && password.length()>4) {
            return true;
        }else{
            return false;
        }
    }

    protected boolean validateuserName(String username)
    {
        if(username!= null && username.length()>4){
            return true;
        }else{
            return false;
        }
    }
}

