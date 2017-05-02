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
import android.view.MenuItem;
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
    private Fragment newhome_fragment;

    private Fragment users_fragment;

    private Fragment transaction_fragment;
    private Fragment open_account_fragment;
    private Fragment logout_fragment;
    private Fragment contacts_fragment;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    private MockServerSingleton mockBankServer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

        //INITIALIZE LEFT ACTIONBAR
        //This sets the navigation drawer and the top actionbar.
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

        fm = getSupportFragmentManager();
        //Initlaize all necessary fragments for MainActivity
        initFragments();
        //Draw the actionbar
        addDrawerItems();

        //Create User's and AccountDetails as dummy data

        JsonPersistency jsonp = new JsonPersistency();

        mockBankServer = MockServerSingleton.getInstance();



        /*
            Add Tina's account set to MainActivity AccountDetails data structure
            ArrayList<Account> needed for account list in homepage
         */
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
                        transaction.replace(R.id.contentFragment, users_fragment);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 3:
                        transaction = fm.beginTransaction();
                        transaction.replace(R.id.contentFragment, open_account_fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                        Drawer.closeDrawer(Gravity.LEFT);
                        break;
                    case 4:
                        Drawer.closeDrawer(Gravity.LEFT);
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        startActivity(intent);

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


        /********Home Fragment********/
        home_fragment = new HomeFrag();


        /********Open Account Fragment********/
        open_account_fragment = new CreateBankAccountFrag();



        /********Transaction Fragments********/
        users_fragment = new UsersFrag();


        open_account_fragment = new OpenAccountFrag();


        //Initiate homepage Fragment when app opens
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, home_fragment);//, "Home_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();

    }

    /*
        An account was clicked in the homepage, change the screen to display account specific tabs
     */
    public void onAccountSelected(int id) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        getFragmentManager().popBackStack();
        /*
            TODO TRACK WHICH ACCOUNT THE USER HAS SELECTED TO VIEW AND WORK WITH (SERVER SIDE?)
         */
        mockBankServer.setAccountIndex(id);
        Intent intent = new Intent(this, AccountDetails.class);
        startActivity(intent);
    }




    public boolean onOptionsItemSelected(MenuItem item){
        System.out.println("ON OPTIONS SELECTED IN MAIN ACTIVITY ");


        return true;

    }

    @Override
    public void onBackPressed() {
        System.out.println("Logout by back press");
        finish();

    }

}
