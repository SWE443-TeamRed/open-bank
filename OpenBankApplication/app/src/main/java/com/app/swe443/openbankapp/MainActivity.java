package com.app.swe443.openbankapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
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

public class MainActivity extends AppCompatActivity {



    private DrawerLayout Drawer;
    private ActionBarDrawerToggle drawerToggle;
    private ListView drawerList;
    private Toolbar toolbar;
    public ActionBar actionBar;

    private Fragment home_fragment;
    private Fragment users_fragment;
    private Fragment open_account_fragment;
    private Fragment logout_fragment;
    private Fragment contacts_fragment;

    private FragmentManager fm;
    private FragmentTransaction transaction;

    private ArrayList<NavDrawerItem> navDrawerItems;
    private NavDrawerListAdapter adapter;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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


        /********Contacts Fragment********/
        contacts_fragment = new ContactsFrag();

        /********Users Info Fragment********/
        users_fragment = new UsersFrag();


        /********Open Account Fragment********/
        open_account_fragment = new OpenAccountFrag();


        /********Logout Fragment********/
        logout_fragment = new LogoutFrag();


        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, home_fragment, "Home_FRAGMENT");
        transaction.commit();
    }

    //This sets the the home page view
    public void seeAccounts(View view){
        Intent intent = new Intent(this, Accounts.class);
        startActivity(new Intent(MainActivity.this, Accounts.class));

    }

}
