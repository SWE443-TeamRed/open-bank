package com.app.swe443.openbankapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;


public class Accounts extends AppCompatActivity {
    ViewPager viewerPager;
    FragmentPageAdapter fragmentPagerAdapter;

    private DrawerLayout Drawer;
    private ListView drawerList;
    private Toolbar toolbar;
    public ActionBar actionBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accounts);

        //Sliding between accounts
        fragmentPagerAdapter = new FragmentPageAdapter(getSupportFragmentManager());
        viewerPager = (ViewPager)findViewById(R.id.pager);
        viewerPager.setAdapter(fragmentPagerAdapter);

        //Sliding Tabs
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewerPager);


        //Toolbar and Drawer
        Drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);



    }

    public void backNavigation(View view){
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                finish();
    }

    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivityForResult(myIntent, 0);
        return true;

    }



}
