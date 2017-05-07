package com.app.swe443.openbankapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.app.swe443.openbankapp.Support.Account;


import com.app.swe443.openbankapp.Support.AccountTypeEnum;
import com.app.swe443.openbankapp.Support.Transaction;
import com.app.swe443.openbankapp.Support.User;

import java.util.Date;
import java.util.LinkedList;


public class AccountDetails extends AppCompatActivity implements AccountFrag.OnAccountsCallbackListener ,TransferFrag.OnTransferCallbackListener{


    //Variables to initalize tabs menu
    ViewPager viewerPager;
    FragmentPageAdapter fragmentPagerAdapter;
    private Toolbar toolbar;
    public ActionBar actionBar;

    //Fields of the account selected
    private int accountIndex;
    private Account account;
    private MockServerSingleton mockBankServer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        mockBankServer = MockServerSingleton.getInstance();

        //Get accountIndex from MainActivity
        accountIndex = mockBankServer.getAccountIndex();
        System.out.println("Obtained Account Index "+accountIndex);

        //Set of the Pager fragments
        fragmentPagerAdapter = new FragmentPageAdapter(getSupportFragmentManager(), accountIndex);
        viewerPager = (ViewPager)findViewById(R.id.pager);
        viewerPager.setAdapter(fragmentPagerAdapter);

        DrawerLayout Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView drawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        //Create User's and AccountDetails as dummy data
        JsonPersistency jsonp = new JsonPersistency();

        /*
            Get the account that these fragments will use
         */
        account = mockBankServer.getLoggedInUser().getAccount().get(accountIndex);

    }

    //On back press by the user, go back to the MainActivity and display HomeFrag
    public void backNavigation(View view){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    //When user selects to go back from the top left arrow option on the header, go to Mainactivity
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }


    //Return the account associated to these fragments.
    //Accessed by AccountFrag
    public Account getAccount(){
        return account;
    }

    //Return the transactions associated to this account
    //Used by TransactionFrag
    public LinkedList<Transaction> getTransactions(){
        return account.getAccountTransactions();
    }


    //Callback when the user deposits an amount on in AccountFrag
    public void onDepositSelected(int amount) {
        System.out.println("onDepositSelected mathod initiated, initial balance of account is "+ account.getBalance());

        //Fufill deposit on this account
        account.deposit(amount);

        System.out.println("Balance after deposit "+account.getBalance());

        fragmentPagerAdapter.notifyDataSetChanged();
        viewerPager.invalidate();

    }

    public void onWithdrawSelected(int amount) {
        System.out.println("onWithdrawSelected mathod initiated, initial balance of account is "+ account.getBalance());
        //Fufill withdraw
        /*
                TODO WITHDRAW SHOULD NOT OCCUR HERE
         */
        account.withdraw(amount);
        System.out.println("Balance after withdraw "+account.getBalance());

        //Notify Pager adapter to update info in transaction and account fragments
        fragmentPagerAdapter.notifyDataSetChanged();
        viewerPager.invalidate();

    }

    public void onTransferSelected() {
        System.out.println("onTransferSelected method initiated, balance of account is "+ account.getBalance());
        //Notify Pager adapter to update info in transaction and account fragments
        fragmentPagerAdapter.notifyDataSetChanged();
        viewerPager.invalidate();

    }







}