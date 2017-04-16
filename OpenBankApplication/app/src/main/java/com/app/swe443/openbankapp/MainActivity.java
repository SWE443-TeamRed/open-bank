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
    private Fragment newhome_fragment;

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
    private User currentUser;
    //Accounts tabs
    ViewPager viewerPager;
    FragmentPageAdapter fragmentPagerAdapter;

    private DrawerLayout Drawertabs;
    private ListView drawerListtabs;
    private Toolbar toolbartabs;
    public ActionBar actionBartabs;
    private int accountID;

    //Main Activity Stores the FragmentPageAdapter
    //Displays the three tabs, AccountFrag as the first tab (See FragmentPageAdapter for the strucutre)
    private RelativeLayout tabsView;
    private RelativeLayout mainView;

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

        currentUser = tina;
        addDrawerItems();
        initFragments();

        mainView = (RelativeLayout) findViewById(R.id.mainView);
        tabsView = (RelativeLayout) findViewById(R.id.tabsView);


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
                System.out.println("NAV ITEM PRESSED AT POSITION "+position);
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
        System.out.println("BACKNAVIGATION FOR HEADER");
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


        //Initiate homepage Fragment when app opens
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, home_fragment, "Home_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /*
        An account was clicked in the homepage, change the screen to display account specific tabs
     */
    public void onAccountSelected(int id) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        this.accountID = id;
        getFragmentManager().popBackStack();


        mainView.setVisibility(View.INVISIBLE);
        tabsView.setVisibility(View.VISIBLE);


        System.out.println("ACCOUNT SELECTED ID IS "+ accountID);
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



    public void onDepositSelected(int amount) {
        System.out.println("onDepositSelected mathod initiated, initial balance of account is "+ accounts.get(accountID).getBalance());

        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        accounts.clear();
        currentUser.getAccount().get(accountID).deposit(amount);

        accounts.addAll(currentUser.getAccount());
        System.out.println("Balance after deposit "+accounts.get(accountID).getBalance());

        fragmentPagerAdapter.notifyDataSetChanged();
        viewerPager.invalidate();

    }

    public void onWithdrawSelected(int amount) {
        System.out.println("onWithdrawSelected mathod initiated, initial balance of account is "+ accounts.get(accountID).getBalance());
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article

        accounts.clear();
        currentUser.getAccount().get(accountID).withdraw(amount);
        accounts.addAll(currentUser.getAccount());
        System.out.println("Balance after withdraw "+accounts.get(accountID).getBalance());
        fragmentPagerAdapter.notifyDataSetChanged();
        viewerPager.invalidate();

    }





    public Account getAccount(int index){
        return accounts.get(index);
    }

    public LinkedList<Transaction> getTransactions(int index){
        return accounts.get(index).getAccountTransactions();
    }

    //Give fragments arrayList of accounts
    public ArrayList<Account> getAccounts(){
        return accounts;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("ON OPTIONS SELECTED IN MAIN ACTIVITY ");
        //Do not create a new Intent for Main Activity here as this will destory all data being tracked
        //Set the (Account Details, Transfer, Transaction) view to invisible and the mainView to visible to display homepage
        //Initiate the Homepage fragment
        transaction = fm.beginTransaction();
        newhome_fragment = new HomeFrag();
        transaction.replace(home_fragment.getId(), newhome_fragment, "Home_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
        mainView.setVisibility(View.VISIBLE);
        tabsView.setVisibility(View.INVISIBLE);

        return true;

    }

    @Override
    public void onBackPressed() {

        System.out.println("Logout by back press");

        if(mainView.getVisibility()==View.INVISIBLE) {
            transaction = fm.beginTransaction();
            newhome_fragment = new HomeFrag();
            transaction.replace(home_fragment.getId(), newhome_fragment, "Home_FRAGMENT");
            transaction.addToBackStack(null);
            transaction.commit();
            mainView.setVisibility(View.VISIBLE);
            tabsView.setVisibility(View.INVISIBLE);
        }else{
            finish();
        }
    }



}
