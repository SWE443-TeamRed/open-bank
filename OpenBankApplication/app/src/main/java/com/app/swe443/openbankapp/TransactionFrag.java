package com.app.swe443.openbankapp;

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

import org.sdmlib.models.classes.util.ArrayListCreator;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class TransactionFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView transactionHeaderName;
    private RecyclerView.Adapter rViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    //private TransactionFrag.OnTransactionSelectedListener mCallback;
    private int accountID;
    MainActivity activity;
    private ArrayList<Transaction> transactions = new ArrayList<Transaction>();
    private ArrayList<Double> transactionBalances = new ArrayList<Double>();

    //Total money changed due to all transactinos in the current accout. Used to calc dec or inc balance in transaction list
    private  double transCost = 0;

    // Container Activity must implement this interface
    /*
    TODO interface will only be used if clicking a transaction requires us to go to another fragment and pass data
     */
    public interface OnTransactionSelectedListener {
        public void onTransactionSelected(int accountID);
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//
//        // This makes sure that the container activity has implemented
//        // the callback interface. If not, it throws an exception
//        try {
//            mCallback = (TransactionFrag.OnTransactionSelectedListener) activity;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(activity.toString()
//                    + " must implement OnTransactionSelectedListener ");
//        }
//    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("INSIDE ONCREATE IN TRANSACTION");

        //Clear local transactions after every fragemnt load
        if(transactions.size()!=0){
            transactions.clear();
        }
        //Main Activity reference, used to get account and transaction data through Main's methods
        activity = (MainActivity) getActivity();
        //Grab Account ID from the passed in argument
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            //Account ID
            accountID = bundle.getInt("id",-1);
            //Call to Main Activity to get transactions of specific account
            transactions.addAll(activity.getTransactions(accountID));
            //Tracker, calculates the updated balance after each transaction occurs
            transCost = activity.getAccount(accountID).getBalance();
            System.out.println("Inital balance "+ transCost);
            //For every transaction in the account, calculate the account balance as a result of the trasaction, store for display
            for(int i=0;i<transactions.size();i++){
                if(i==0)
                    //Most recent transaction should be at the top of list, its balance is the current accounts balance
                    transactionBalances.add(i,transCost);
                else {
                    //Every transactions 'current balance' should be value before the previous transction took place
                    if (transactions.get(i-1).getTransType().equals(TransactionTypeEnum.DEPOSIT)) {
                        //i-1 = the transaction infront of i, subtracting when a deposit occurs shows balance before Deposit at i-1 took place
                        transCost -= transactions.get(i-1).getAmount();
                        transactionBalances.add(i, transCost);
                        System.out.println("Deposit  " + transactions.get(i).getAmount() + "  transcost is now " + transCost);

                    } else {
                        //i-1 = the transaction infront of i, Adding when a when occurs shows balance before Withdraw at i-1 took place
                        transCost += transactions.get(i-1).getAmount();
                        transactionBalances.add(i, transCost);
                        System.out.println("Withdrew  " + transactions.get(i).getAmount() + "  transcost is now " + transCost);


                    }
                }
            }
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("INSIDE ONCREATEVIEW IN TRANSACTION");
        final View v = inflater.inflate(R.layout.fragment_transaction, container, false);
//Get RecyclerView instance from the layout
        mRecyclerView = (RecyclerView) v.findViewById(R.id.trans_recycler_view);
        transactionHeaderName = (TextView) v.findViewById(R.id.welcomeText);
        transactionHeaderName.setText("Transactions");
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)

        //jsonp.toJson(tinac);
        //Account tinaa = jsonp.fromJson(tina.getUserID());

        //Set data and send it to the Adapter
        rViewAdapter = new TransactionFrag.MyAdapter(transactions, getContext());
        mRecyclerView.setAdapter(rViewAdapter);

        return v;
    }

//    public void goToTransaction(int id) {
//        mCallback.onTransactionSelected(id);
//
//
//    }


//The adapter provides access to the items in your data set, creates views for items, and replaces the content of some of the views with new data items when the original item is no longer visible. The following code example shows a simple implementation for a data set that consists of an array of strings displayed using TextView widgets:

    class MyAdapter extends RecyclerView.Adapter<TransactionFrag.MyAdapter.ViewHolder> {

        private AdapterView.OnItemClickListener mItemClickListener;
        private Context mContext;
        private FragmentManager fm;
        private FragmentTransaction transaction;
        private Fragment transaction_fragment;



        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView fromText;
            public TextView noteText;
            public TextView balanceText;
            public TextView amountText;
            public TextView dateText;
            public TextView typeText;


            public ViewHolder(View v) {
                super(v);
                fromText = (TextView) v.findViewById(R.id.balanceText);
                noteText = (TextView) v.findViewById(R.id.noteText);
                balanceText = (TextView) v.findViewById(R.id.balanceText);
                amountText = (TextView) v.findViewById(R.id.amountText);
                dateText = (TextView) v.findViewById(R.id.dateText);
                typeText = (TextView) v.findViewById(R.id.typeText);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Transaction> myDataset, Context context) {

            transactions = myDataset;
            this.mContext = context;

        }


        // Create new views (invoked by the layout manager)
        @Override
        public TransactionFrag.MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                int viewType) {
            // Get fromText from layout
            View v = (View) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transaction_card_view, parent, false);
            // set the view's size, margins, paddings and layout parameters

            TransactionFrag.MyAdapter.ViewHolder vh = new TransactionFrag.MyAdapter.ViewHolder(v);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("CLICKED ON Transaction" + vh.getAdapterPosition());
                    //goToTransaction(vh.getAdapterPosition());

                }


            });
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(TransactionFrag.MyAdapter.ViewHolder holder, int position) {
            // - get element from your dataset at this position
            // - replace the contents of the view with that element
            if(transactions.get(position).getTransType().equals(TransactionTypeEnum.TRANSFER))
                holder.fromText.setText(String.valueOf(String.valueOf(transactions.get(position).getFromAccount().getOwner().getName())));
            else
                holder.fromText.setText(String.valueOf("Self"));

            holder.noteText.setText(String.valueOf(transactions.get(position).getNote()));
            String newDateFormat = new SimpleDateFormat("MM/dd/yyyy 'at' HH:mm").format(transactions.get(position).getDate());
            holder.dateText.setText(String.valueOf(newDateFormat));
            holder.typeText.setText(String.valueOf(transactions.get(position).getTransType()));

            if(transactions.get(position).getTransType().equals(TransactionTypeEnum.DEPOSIT)) {
                 holder.amountText.setText(String.valueOf(transactions.get(position).getAmount()));
                 holder.balanceText.setText(String.valueOf(transactionBalances.get(position)));

            }else{
                //amount is the amount of the transaction (-) displays if it is a withdraw
                holder.amountText.setText(String.valueOf("- " + transactions.get(position).getAmount()));
                //Balance at this transactions state is stored in the transactionBalances array at index position (corresponds to transaction index)
                holder.balanceText.setText(String.valueOf(transactionBalances.get(position)));

            }


        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return transactions.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        public void initFragments() {

            /********Home Fragment********/
            transaction_fragment = new TransactionFrag();
            transaction = fm.beginTransaction();
            transaction.replace(R.id.contentFragment, transaction_fragment, "TRANSACTION_FRAGMENT");
            transaction.commit();
        }
    }
}
