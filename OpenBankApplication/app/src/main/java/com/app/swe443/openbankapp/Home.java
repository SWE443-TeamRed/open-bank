package com.app.swe443.openbankapp;

import android.os.Bundle;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class Home extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private TextView homepageHeaderName;
    private RecyclerView.Adapter rViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Get RecyclerView instance from the layout
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        homepageHeaderName = (TextView) findViewById(R.id.welcomeText);
        homepageHeaderName.setText("Welcome "+"Nick");
                // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        //Create User's and Accounts as dummy data
        JsonPersistency jsonp = new JsonPersistency();
        User tina = new User().withUserID("tina1").withPassword("tinapass");
        Account tinac = new Account().withBalance(100).withOwner(tina).withAccountnum(1).withType(AccountTypeEnum.SAVINGS);
        Account tinas = new Account().withBalance(100).withOwner(tina).withAccountnum(2).withType(AccountTypeEnum.SAVINGS);
        Account tinap = new Account().withBalance(100).withOwner(tina).withAccountnum(3).withType(AccountTypeEnum.CHECKING);
        System.out.println("TYPE OF ACCOUNT IS "+ tinac.getType());
        tinac.withdraw(10);
        tinac.withdraw(20);
        tinac.withdraw(30);
        //jsonp.toJson(tinac);
        //Account tinaa = jsonp.fromJson(tina.getUserID());

        //Set data and send it to the Adapter
        ArrayList<Account> data = new ArrayList<Account>();
        data.addAll(tina.getAccount());
//        rViewAdapter = new HomeFrag.MyAdapter(data);
//        mRecyclerView.setAdapter(rViewAdapter);
    }



}
