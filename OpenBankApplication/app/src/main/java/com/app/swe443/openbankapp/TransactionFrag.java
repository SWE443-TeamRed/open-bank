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
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class TransactionFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private TextView transactionHeaderName;
    private RecyclerView.Adapter rViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Transaction> mDataset;
    //private TransactionFrag.OnTransactionSelectedListener mCallback;
    private int accountID;
    private ArrayList<Transaction> transactions;

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

        MainActivity activity = (MainActivity) getActivity();
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            accountID = bundle.getInt("id",-1);
            transactions = activity.getTransactions(accountID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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


            public ViewHolder(View v) {
                super(v);
                fromText = (TextView) v.findViewById(R.id.fromText);
                noteText = (TextView) v.findViewById(R.id.noteText);
                balanceText = (TextView) v.findViewById(R.id.balanceText);
                amountText = (TextView) v.findViewById(R.id.amountText);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<Transaction> myDataset, Context context) {

            mDataset = myDataset;
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
            if(mDataset.get(position).getTransType().equals(TransactionTypeEnum.TRANSFER))
                holder.fromText.setText(String.valueOf(String.valueOf(mDataset.get(position).getFromAccount().getOwner().getName())));
            else
                holder.fromText.setText(String.valueOf("Self"));
            holder.noteText.setText(String.valueOf(mDataset.get(position).getNote()));
            holder.balanceText.setText(String.valueOf(mDataset.get(position).getToAccount().getBalance()));
            holder.amountText.setText(String.valueOf(mDataset.get(position).getAmount()));


        }


        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
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
