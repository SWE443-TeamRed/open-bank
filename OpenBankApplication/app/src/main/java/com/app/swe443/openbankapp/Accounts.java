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


public class Accounts extends AppCompatActivity implements AccountFrag.OnAccountsCallbackListener  {


    //Variables to initalize tabs menu
    ViewPager viewerPager;
    FragmentPageAdapter fragmentPagerAdapter;
    private Toolbar toolbar;
    public ActionBar actionBar;

    //Fields of the account selected
    private int accountIndex;
    private Account account;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        //Get accountIndex from MainActivity
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        accountIndex = extras.getInt("accountIndex");
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


        //Create User's and Accounts as dummy data
        JsonPersistency jsonp = new JsonPersistency();
        User tina = new User().withUserID("tina1").withPassword("tinapass");
        Account tinac = new Account()
                .withBalance(100)
                .withOwner(tina)
                .withAccountnum(1)
                .withType(AccountTypeEnum.SAVINGS)
                .withCreationdate(new Date());
        Account tinas = new Account()
                .withBalance(100)
                .withOwner(tina)
                .withAccountnum(2)
                .withType(AccountTypeEnum.SAVINGS)
                .withCreationdate(new Date());
        Account tinap = new Account()
                .withBalance(100)
                .withOwner(tina)
                .withAccountnum(3)
                .withType(AccountTypeEnum.CHECKING)
                .withCreationdate(new Date());
        System.out.println("TYPE OF ACCOUNT IS " + tinac.getType());
        tinac.withdraw(1); //90
        tinac.withdraw(20);  //70
        tinac.withdraw(30);  //40
        tinac.deposit(1000);  //1040
        tinac.deposit(500);  //1540
        tinac.withdraw(400); // 1140
        tinac.withdraw(800); // 340
        tinac.withdraw(20);   //320


        /*
            Get the account that these fragments will use
         */
        account = tina.getAccount().get(accountIndex);

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
        account.withdraw(amount);
        System.out.println("Balance after withdraw "+account.getBalance());

        //Notify Pager adapter to update info in transaction and account fragments
        fragmentPagerAdapter.notifyDataSetChanged();
        viewerPager.invalidate();

    }





}