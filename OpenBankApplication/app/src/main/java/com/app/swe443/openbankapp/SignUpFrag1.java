package com.app.swe443.openbankapp;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AutoCompleteTextView;
import android.widget.Button;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpFrag1 extends android.support.v4.app.Fragment implements View.OnClickListener {


    private String  encryptedPass;
    private String encryptedConfirmPass;
    Button backButton1;
    Button continueButton1;
    AutoCompleteTextView userNameV;
    AutoCompleteTextView emailV;
    AutoCompleteTextView pass_word;
    AutoCompleteTextView comfirmPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Back & Continue buttons for first sign up page.
        View view = inflater.inflate(R.layout.fragment_sign_up1, container, false);
        backButton1 = (Button) view.findViewById(R.id.back_button1_4);
        continueButton1 = (Button) view.findViewById(R.id.continue_button1_4);
        backButton1.setOnClickListener(this);
        continueButton1.setOnClickListener(this);

        //User name email & password.
        userNameV = (AutoCompleteTextView) view.findViewById(R.id.user_name);
        emailV = (AutoCompleteTextView) view.findViewById(R.id.email);
        pass_word = (AutoCompleteTextView) view.findViewById(R.id.password);
        comfirmPassword = (AutoCompleteTextView) view.findViewById(R.id.password1);

//        try {//Encrypting password.
//            MessageDigest md = MessageDigest.getInstance("SHA-256");
//            md.update(pass_word.getText().toString().getBytes());
//            byte[] encryptedPass = md.digest();
//            MessageDigest md2 = MessageDigest.getInstance("SHA-256");
//            md2.update(comfirmPassword.getText().toString().getBytes());
//            byte[] encryptedConfirmPass = md.digest();
//        }catch (NoSuchAlgorithmException e){
//            System.out.println("Something wrong with encryption");
//        }


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.back_button1_4:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.continue_button1_4:

                if (!validateEmail(emailV.getText().toString())) {
                    emailV.setError("Invalid Email.");
                    emailV.requestFocus();
                }
                else if(! validateuserName(userNameV.getText().toString())) {
                    userNameV.setError("Need more then 4 characters.");
                    userNameV.requestFocus();
                }
                else if(!validatePassword(pass_word.getText().toString())){
                    pass_word.setError("Invalid password, 4 characters or more .");
                    pass_word.requestFocus();
                }
                else if(!pass_word.getText().toString().equals(comfirmPassword.getText().toString())) {
                    comfirmPassword.setError("Password does not match.");
                    comfirmPassword.requestFocus();
                }
                else {
//                    LoginActivity activity = (LoginActivity) getActivity();
//                    activity.setSingnUp1(userNameV.getText().toString(),
//                            pass_word.getText().toString(), emailV.getText().toString());

                    //To pass the values from fragments to fragments.
                    SignUpFrag2 signUpFrag2 = new SignUpFrag2 ();
                    Bundle args = new Bundle();

                    ArrayList<String> values = new ArrayList<String>();
                    values.add(userNameV.getText().toString());
                    values.add(pass_word.getText().toString());
                    values.add(emailV.getText().toString());

                    args.putStringArrayList("signupData", values);
                    signUpFrag2.setArguments(args);

                    //Inflate the fragment
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.add(R.id.drawer_layout, signUpFrag2, "SignUpFrag2");
                    fragmentTransaction.hide(this);
                    fragmentTransaction.addToBackStack(this.getClass().getName());
                    fragmentTransaction.commit();
                }
                break;
        }
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

