package com.app.swe443.openbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;

public class MainActivity extends AppCompatActivity
        implements HomeFrag.OnAccountSelectedListener, AccountFrag.OnMainActivityCallbackListener{



    private DrawerLayout Drawer;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    private Toolbar toolbar;
    public ActionBar actionBar;

    private Fragment home_fragment;
    private Fragment account_fragment;
    private Fragment transfer_fragment;
    private Fragment users_fragment;

    private Fragment transaction_fragment;
    private Fragment open_account_fragment;
    private Fragment logout_fragment;
    private Fragment contacts_fragment;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;
    private ArrayList<Account> accounts = new ArrayList<Account>();

    //Accounts tabs
    ViewPager viewerPager;
    FragmentPageAdapter fragmentPagerAdapter;

    private DrawerLayout Drawertabs;
    private ListView drawerListtabs;
    private Toolbar toolbartabs;
    public ActionBar actionBartabs;
    private int accountID;

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
            Add Tina's account set to MainActivity Accounts data structure
            ArrayList<Account> needed for account list in homepage
         */
        accounts.addAll(tina.getAccount());
        addDrawerItems();
        initFragments();




    }

    private void addDrawerItems() {
        navDrawerItems = new ArrayList<NavDrawerItem>();

        String[] navMenuTitles = {"Home", "Contacts", "Update My Information", "Open Account", "Logout" };

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[4].toString()));

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
                        transaction.replace(R.id.contentFragment, contacts_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 3:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, users_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 4:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, open_account_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 5:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, logout_fragment

                        );
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

        /********Open Account Fragment********/
        open_account_fragment = new OpenAccountFrag();


        /********Logout Fragment********/
        logout_fragment = new LogoutFrag();


        /********Transfer Fragment********/
        transfer_fragment = new TransferFrag();

        /********Transaction Fragments********/
        transaction_fragment = new TransactionFrag();

        /********Transaction Fragments********/
        users_fragment = new UsersFrag();


        //Initiate the Homepage fragment
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, home_fragment, "Home_FRAGMENT");
        transaction.commit();
    }

    /*
        An account was clicked in the homepage, change the screen to display account specific tabs
     */
    public void onAccountSelected(int accountID) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
//        Bundle bundle = new Bundle();
//        bundle.putInt("id", accountID);
//        account_fragment.setArguments(bundle);
 //         transaction = fm.beginTransaction();
//        // Replace whatever is in the fragment_container view with this fragment,
//        // and add the transaction to the back stack so the user can navigate back
     //      transaction.replace(R.id.contentFragment, account_fragment);
//        transaction.addToBackStack(null);
//        // Commit the transaction
//        transaction.commit();
        getFragmentManager().popBackStack();
        RelativeLayout mainView = (RelativeLayout) findViewById(R.id.mainView);
        RelativeLayout tabsView = (RelativeLayout) findViewById(R.id.tabsView);

        mainView.setVisibility(View.INVISIBLE);
        tabsView.setVisibility(View.VISIBLE);


        System.out.println("ACCOUNT SELECTED");
        fragmentPagerAdapter = new FragmentPageAdapter(getSupportFragmentManager(), accountID);
        viewerPager = (ViewPager)findViewById(R.id.pager);
        viewerPager.setAdapter(fragmentPagerAdapter);

        Drawertabs = (DrawerLayout) findViewById(R.id.drawer_layouttabs);
        drawerListtabs = (ListView) findViewById(R.id.left_drawertabs);
        toolbartabs = (Toolbar) findViewById(R.id.toolbartabs);

        setSupportActionBar(toolbartabs);
        actionBartabs = getSupportActionBar();
        actionBartabs.setDisplayHomeAsUpEnabled(true);
        actionBartabs.setHomeButtonEnabled(true);



    }



    public void onDepositSelected(int accountID) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        Bundle bundle = new Bundle();
        bundle.putInt("id", accountID);
        transaction_fragment = new TransactionFrag();
        transaction_fragment.setArguments(bundle);
        transaction = fm.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.contentFragment, transaction_fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();


    }

    public void onWithdrawSelected(int accountID) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        Bundle bundle = new Bundle();
        bundle.putInt("id", accountID);
        transaction_fragment = new TransactionFrag();
        transaction_fragment.setArguments(bundle);
        transaction = fm.beginTransaction();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack so the user can navigate back
        transaction.replace(R.id.contentFragment, transaction_fragment);
        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();


    }


    public Account getAccount(int index){
        return accounts.get(index);
    }

    public LinkedList<Transaction> getTransactions(int index){
//        ArrayList<Transaction> trans = new ArrayList<Transaction>();
//        trans.addAll(accounts.get(index).getDebit());
//        trans.addAll(accounts.get(index).getCredit());
//        System.out.println("TRANSACTIONS COUNT IS "+trans.size() + " at index " + index);
//        Collections.sort(trans, new Comparator<Transaction>() {
//            public int compare(Transaction o1, Transaction o2) {
//                return o1.getDate().compareTo(o2.getDate());
//            }
//        });
        return accounts.get(index).getAccountTransactions();
    }

    //Give fragments arrayList of accounts
    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }

    @Override
    public void onBackPressed() {
        Log.d("LOGOUT", "Logout by back press");
        RelativeLayout mainview = (RelativeLayout) findViewById(R.id.mainView);
        RelativeLayout tabsview = (RelativeLayout) findViewById(R.id.tabsView);
        if(mainview.getVisibility()==View.INVISIBLE) {
            mainview.setVisibility(View.VISIBLE);
            tabsview.setVisibility(View.INVISIBLE);
        }else{
            finish();
        }
    }



}
