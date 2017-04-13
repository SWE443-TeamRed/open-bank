package com.app.swe443.openbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements HomeFrag.OnAccountSelectedListener{



    private DrawerLayout Drawer;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    private Toolbar toolbar;
    public ActionBar actionBar;

    private Fragment home_fragment;
    private Fragment account_fragment;
    private Fragment transfer_fragment;
    private Fragment savings_transaction_fragment;
    private Fragment checking_transaction_fragment;
    private Fragment credit_transaction_fragment;
    private Fragment contacts_fragment;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private ArrayList<Account> accounts = new ArrayList<Account>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();

        drawerToggle = new ActionBarDrawerToggle(this, Drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawerToggle.setDrawerIndicatorEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        Drawer.addDrawerListener(drawerToggle);

        drawerToggle.syncState();

        //Create User's and Accounts as dummy data
        JsonPersistency jsonp = new JsonPersistency();
        User tina = new User().withUserID("tina1").withPassword("tinapass");
        Account tinac = new Account().withBalance(100).withOwner(tina).withAccountnum(1).withType(AccountTypeEnum.SAVINGS);
        Account tinas = new Account().withBalance(100).withOwner(tina).withAccountnum(2).withType(AccountTypeEnum.SAVINGS);
        Account tinap = new Account().withBalance(100).withOwner(tina).withAccountnum(3).withType(AccountTypeEnum.CHECKING);
        System.out.println("TYPE OF ACCOUNT IS " + tinac.getType());
        tinac.withdraw(10);
        tinac.withdraw(20);
        tinac.withdraw(30);
        accounts.addAll(tina.getAccount());
        addDrawerItems();
        initFragments();


    }

    private void addDrawerItems() {
        navDrawerItems = new ArrayList<NavDrawerItem>();

        String[] navMenuTitles = {"Home", "Transfer Funds", "Savings Transaction Log",
                "Checking Transaction Log", "Credit Transaction Log", "Contacts" };

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[5].toString()));

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);

        View headerView = View.inflate(this, R.layout.navigation_drawer_header, null);
        drawerList.addHeaderView(headerView);

        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {

                switch (position) {
                    case 1:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, home_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                    case 2:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, transfer_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 3:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, savings_transaction_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 4:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, checking_transaction_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 5:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, credit_transaction_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 6:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, contacts_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                }
            }
        });
    }

    public void backNavigation(View view){
        Drawer.closeDrawer(Gravity.START);
    }

    public void initFragments() {

        fm = getSupportFragmentManager();

        /********Home Fragment********/
        home_fragment = new HomeFrag();

        /********Account Fragment********/
        account_fragment = new AccountFrag();

        /********Transfer Fragment********/
        transfer_fragment = new TransferFrag();

        /********Transaction Fragments********/
        savings_transaction_fragment = new SavingsTransactionFrag();
        checking_transaction_fragment = new CheckingTransactionFrag();
        credit_transaction_fragment = new CreditTransactionFrag();

        /********Contacts Fragment********/
        contacts_fragment = new ContactsFrag();

        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, home_fragment, "Home_FRAGMENT");
        transaction.commit();
    }
    public void seeAccounts(View view){
        Intent intent = new Intent(this, Accounts.class);
        startActivity(intent);
//        Intent intent = new Intent(this, Home.class);
//        startActivity(new Intent(MainActivity.this, Home.class));

    }

    public void onAccountSelected(int accountID) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        Bundle bundle = new Bundle();
        bundle.putInt("id", accountID);
        account_fragment.setArguments(bundle);
        transaction = fm.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.contentFragment, account_fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();


    }


    public Account getAccount(int index){
        return accounts.get(index);
    }

    public ArrayList<Account> getAccounts(){
        return accounts;
    }

}
