package com.app.swe443.openbankapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.swe443.openbankapp.Support.Account;
import com.app.swe443.openbankapp.Support.AccountTypeEnum;
import com.app.swe443.openbankapp.Support.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import static android.content.ContentValues.TAG;


public class HomeFrag extends Fragment {

    private RecyclerView mRecyclerView;
    private TextView homepageHeaderName;
    private RecyclerView.Adapter rViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private OnHomeFragMethodSelectedListener mCallback;
    private ArrayList<AccountDisplay> userAccounts;
    private ArrayList<AccountDisplay> mDataset;

    // Main Activity must implement this interface in order to communicate with HomeFrag
    public interface OnHomeFragMethodSelectedListener {
        public void onAccountSelected(int accountID);

        public ArrayList<AccountDisplay> getAccounts();

        public String getUsername();
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

        //Initialize callback to communicate with parent activity
        mCallback = (OnHomeFragMethodSelectedListener) getActivity();
        //Grab the user's accounts obtained in the parent activity
        userAccounts = mCallback.getAccounts();

        //Get RecyclerView instance from the layout
        mRecyclerView = (RecyclerView) v.findViewById(R.id.my_recycler_view);
        homepageHeaderName = (TextView) v.findViewById(R.id.welcomeText);

        //Display the username that has logged in
        homepageHeaderName.setText("Welcome " + mCallback.getUsername());
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        //Set data and send it to the UserAccountsAdapter
        rViewAdapter = new UserAccountsAdapter(userAccounts, getContext());
        mRecyclerView.setAdapter(rViewAdapter);

        return v;
    }

    //User clicks on an account in the view
    public void goToAccount(int id) {
        mCallback.onAccountSelected(id);
    }


    //The adapter provides access to the items in your data set,
    // creates views for items,
    // and replaces the content of some of the views with new data
    // items when the original item is no longer visible.
    class UserAccountsAdapter extends RecyclerView.Adapter<UserAccountsAdapter.ViewHolder> {

        private Context mContext;

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
        public UserAccountsAdapter(ArrayList<AccountDisplay> myDataset, Context context) {
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
            holder.accountnameText.setText(mDataset.get(position).getdType());
            holder.accountnumText.setText("Account: " + mDataset.get(position).getdAccountnum());
            DecimalFormat precision = new DecimalFormat("0.00");
            holder.balanceText.setText("$ " + String.valueOf(precision.format(Integer.valueOf(mDataset.get(position).getdBalance()))));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }
}


