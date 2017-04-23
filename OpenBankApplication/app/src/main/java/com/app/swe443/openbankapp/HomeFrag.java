package com.app.swe443.openbankapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.app.swe443.openbankapp.Support.Account;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class HomeFrag extends Fragment {

    private RecyclerView mRecyclerView;
    private TextView homepageHeaderName;
    private RecyclerView.Adapter rViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Account> mDataset;
    private OnAccountSelectedListener  mCallback;
    private MockServerSingleton mockserver;

    // Main Activity must implement this interface in order to communicate with HomeFrag
    public interface OnAccountSelectedListener {
        public void onAccountSelected(int accountID);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnAccountSelectedListener ) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnAccountSelectedListener ");
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_home, container, false);

        /*
            TODO ESTABLISH CONTACT WITHT EH SERVER
         */
        mockserver = MockServerSingleton.getInstance();

        //Get RecyclerView instance from the layout
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        homepageHeaderName = (TextView) v.findViewById(R.id.welcomeText);
        homepageHeaderName.setText("Welcome " + mockserver.getLoggedInUser().getName());
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        //jsonp.toJson(tinac);
        //Account tinaa = jsonp.fromJson(tina.getUserID());

        //Set data and send it to the Adapter
        /*
           TODO NEED ARRAYLIST OF THE USER'S ACCOUNT (SERVER?)
         */
        rViewAdapter = new MyAdapter(mockserver.getAccounts(), getContext());
        mRecyclerView.setAdapter(rViewAdapter);

        return v;
    }

    public void goToAccount(int id) {
        mCallback.onAccountSelected(id);



    }


//The adapter provides access to the items in your data set, creates views for items, and replaces the content of some of the views with new data items when the original item is no longer visible. The following code example shows a simple implementation for a data set that consists of an array of strings displayed using TextView widgets:

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        private AdapterView.OnItemClickListener mItemClickListener;
        private Context mContext;
        private FragmentManager fm;
        private FragmentTransaction transaction;
        private Fragment home_fragment;



        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView accountnameText;
            public TextView accountnumText;
            public TextView balanceText;


            public ViewHolder(View v) {
                super(v);
                accountnameText = (TextView) v.findViewById(R.id.accountnameText);
                accountnumText = (TextView) v.findViewById(R.id.accountnumText);
                balanceText = (TextView) v.findViewById(R.id.balanceText);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Account> myDataset, Context context) {

            mDataset = myDataset;
            this.mContext = context;

        }


        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent,
                                             int viewType) {
            // Get accountnameText from layout
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.account_card_view, parent, false);
            // set the view's size, margins, paddings and layout parameters

            ViewHolder vh = new ViewHolder(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("CLICKED ON ITEM " + vh.getAdapterPosition());
                    goToAccount(vh.getAdapterPosition());



                }


            });
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            holder.accountnameText.setText(String.valueOf(String.valueOf(mDataset.get(position).getType())));
            holder.accountnumText.setText("Account: "+  String.valueOf(mDataset.get(position).getAccountnum()));
            DecimalFormat precision = new DecimalFormat("0.00");
            holder.balanceText.setText("$ " +String.valueOf(precision.format(mDataset.get(position).getBalance())));
        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }

//        public void initFragments() {
//
//            /********Home Fragment********/
//            home_fragment = new HomeFrag();
//            transaction = fm.beginTransaction();
//            transaction.replace(R.id.contentFragment, home_fragment, "Home_FRAGMENT");
//            transaction.commit();
//        }
    }

}