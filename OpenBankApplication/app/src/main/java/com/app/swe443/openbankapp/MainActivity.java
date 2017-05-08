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
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity
        implements HomeFrag.OnHomeFragMethodSelectedListener{




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

    String userID;
    String username;
    private ArrayList<AccountDisplay> userAccounts;



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




        //TODO Getting username and id, with this can get accounts info from server.
       userID = getIntent().getStringArrayExtra("UsernameAndID")[1];
       username = getIntent().getStringArrayExtra("UsernameAndID")[0];

        System.out.println("Got username and id from loginactivity "+ userID+ "  "+username);


        mockBankServer = MockServerSingleton.getInstance();

        //Initlaize all necessary fragments for MainActivity
        initFragments();
        //Draw the actionbar
        addDrawerItems();
        getUserAccountsFromServer();
        /*
            Add Tina's account set to MainActivity AccountDetails data structure
            ArrayList<Account> needed for account list in homepage
         */
    }

    private void addDrawerItems() {
        navDrawerItems = new ArrayList<NavDrawerItem>();

        String[] navMenuTitles = {"Home", "Update My Information", "Open Account", "Logout" };

        // adding nav drawer items to array
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[0].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[1].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[2].toString()));
        navDrawerItems.add(new NavDrawerItem(navMenuTitles[3].toString()));

        // setting the nav drawer list adapter
        adapter = new NavDrawerListAdapter(getApplicationContext(), navDrawerItems);

        View headerView = View.inflate(this, R.layout.navigation_drawer_header, null);
        drawerList.addHeaderView(headerView);

        drawerList.setAdapter(adapter);

        drawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                System.out.println("CLICKED ON "+position);

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



    }


    /*
        An account was clicked in the homepage, change the screen to display account specific tabs
     */
    public void onAccountSelected(int id) {
        // The user selected the headline of an article from the HeadlinesFragment
        // Do something here to display that article
        getFragmentManager().popBackStack();
        /*
            Send the selected account information to the AccountFrag to display the accounts details
         */
        Intent intent = new Intent(this, AccountDetails.class);
        intent.putExtra("type", userAccounts.get(id).getdType());
        intent.putExtra("accountnum", userAccounts.get(id).getdAccountnum());
        intent.putExtra("balance", userAccounts.get(id).getdBalance());

        startActivity(intent);
    }


    public ArrayList<AccountDisplay> getAccounts(){
        return userAccounts;
    }
    public String getUsername(){
        return username;
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

    public void getUserAccountsFromServer(){
        StringRequest stringRequest;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url = "http://54.87.197.206:8080/SparkServer/api/v1/account?id="+userID;
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("OBTAINED USER ACCOUNTS");
                Toast.makeText(getApplicationContext(),response.toString(),Toast.LENGTH_LONG).show();


                try {
                    JSONArray obj = new JSONArray(response);
                    userAccounts = getAccountsDisplays(obj);
                    goToHomepage();

                }catch(JSONException e){
                    e.printStackTrace();
                    Log.d(TAG,response);
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        System.out.println("REQUESTING USER ACCOUNTS");
        queue.add(stringRequest);
    }

    public void goToHomepage(){
        //Initiate homepage Fragment when app opens
        transaction = fm.beginTransaction();
        transaction.replace(R.id.contentFragment, home_fragment);//, "Home_FRAGMENT");
        transaction.addToBackStack(null);
        transaction.commit();
    }


    //Read user's accounts from the server responce array
    public ArrayList<AccountDisplay> getAccountsDisplays(JSONArray response){
        ArrayList<AccountDisplay> myDataset = new ArrayList<AccountDisplay>();
        try {
            JSONArray accounts = (JSONArray) response.get(1);
            for(int i=0; i<accounts.length();i++){
                try {
                    JSONObject rec = accounts.getJSONObject(i);
                    System.out.println("Got an account "+rec.toString());
                    String balance = rec.getString("balance");
                    String type = rec.getString("accountType");
                    String accountnum = rec.getString("accountNumber");
                    myDataset.add(new AccountDisplay(type,accountnum,balance));
                }catch(JSONException e){
                    e.printStackTrace();
                    Log.d(TAG,response.toString());
                }

            }
        }catch(JSONException e){
            e.printStackTrace();
            Log.d(TAG,response.toString());
        }
        return myDataset;
    }
}


/*
  Helper class to organize attributes that will be dispalyed
*/
class AccountDisplay {
    private String dType;
    private String dAccountnum;
    private String dBalance;

    public AccountDisplay(String type, int num, int balance){
        this.dType = type;
        this.dAccountnum = Integer.toString(num);
        this.dBalance = Integer.toString(balance);
    }

    public AccountDisplay(String type, String num, String balance){
        this.dType = type;
        this.dAccountnum = num;
        this.dBalance = balance;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

    public String getdAccountnum() {
        return dAccountnum;
    }

    public void setdAccountnum(String dAccountnum) {
        this.dAccountnum = dAccountnum;
    }

    public String getdBalance() {
        return dBalance;
    }

    public void setdBalance(String dBalance) {
        this.dBalance = dBalance;
    }
}

