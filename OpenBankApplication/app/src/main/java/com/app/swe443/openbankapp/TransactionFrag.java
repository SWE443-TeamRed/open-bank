package com.app.swe443.openbankapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by kimberly_93pc on 4/9/17.
 */

public class TransactionFrag extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter rViewAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private HashMap<String,String> params;
    private double currentbalance;
    private String type;
    private int accountnum;


    //UI fields
    private TextView transactionHeaderName;


    //Parent activity which stores data
    AccountDetails activity;
    //Total money changed due to all transactinos in the current accout. Used to calc dec or inc balance in transaction list
    double transCost = 0;
    //Calculate each change in balance as a result of each transaction. Display this with each transaction
    ArrayList<TransactionDisplay> transDataset;

    private TransactionFrag.OnTransactionFragCallbackListener mCallback;


    // Container Activity must implement this interface

    public interface OnTransactionFragCallbackListener {
        public String[] getAccountInfo();
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("Creating transaction fragment");

         mCallback = (TransactionFrag.OnTransactionFragCallbackListener) getActivity();
         String[] accountinfo = mCallback.getAccountInfo();
        currentbalance = Double.valueOf(accountinfo[1]);
        type = accountinfo[2];
        accountnum = Integer.valueOf(accountinfo[0]);


        transDataset = new ArrayList<TransactionDisplay>();
        //Call to AccountDetails Activity to get transactions of specific account
         System.out.println("Getting the user's transactions");



        getTransactionsFromServer();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("CreateView for Transaction");
        final View v = inflater.inflate(R.layout.fragment_transaction, container, false);
        //Get RecyclerView instance from the layout
        mRecyclerView = (RecyclerView) v.findViewById(R.id.trans_recycler_view);
        transactionHeaderName = (TextView) v.findViewById(R.id.welcomeText);
        transactionHeaderName.setText("Transactions");
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //jsonp.toJson(tinac);

        //Set data and send it to the Adapter
        rViewAdapter = new TransactionFrag.MyAdapter(transDataset, getContext());
        mRecyclerView.setAdapter(rViewAdapter);

        return v;
    }


//The adapter provides access to the items in your data set, creates views for items, and replaces the content of some of the views with new data items when the original item is no longer visible. The following code example shows a simple implementation for a data set that consists of an array of strings displayed using TextView widgets:

    class MyAdapter extends RecyclerView.Adapter<TransactionFrag.MyAdapter.ViewHolder> {

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public TextView fromText;
            public TextView toUserText;
            public TextView balanceText;
            public TextView amountText;
            public TextView dateText;
            public TextView typeText;


            public ViewHolder(View v) {
                super(v);
               // fromText = (TextView) v.findViewById(R.id.balanceText);
                toUserText = (TextView) v.findViewById(R.id.toUserText);
                balanceText = (TextView) v.findViewById(R.id.balanceText);
                amountText = (TextView) v.findViewById(R.id.amountText);
                dateText = (TextView) v.findViewById(R.id.dateText);
                typeText = (TextView) v.findViewById(R.id.typeText);

            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public MyAdapter(ArrayList<TransactionDisplay> myDataset, Context context) {

            transDataset = myDataset;

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
                    System.out.println("Clicked Transaction" + vh.getAdapterPosition());
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
            if(transDataset.get(position).getdType().equals("TRANSFER"))
                holder.toUserText.setText("To: "+transDataset.get(position).getdToUserName());
            else
                holder.toUserText.setText(String.valueOf("Self"));

            //holder.noteText.setText(String.valueOf(transDataset.get(position).getdNote()));
            holder.dateText.setText(String.valueOf(transDataset.get(position).getdCreationDate()));
            holder.typeText.setText("Type: "+String.valueOf(transDataset.get(position).getdType()));

            //String balance = precision.format(transDataset.get(position).getdBalance());
            holder.balanceText.setText("$ "+transDataset.get(position).getdBalance());
            if(transDataset.get(position).getdType().equals("DEPOSIT") ||
                    transDataset.get(position).getdType().equals("SEED")) {
                 holder.amountText.setText("$ "+transDataset.get(position).getdAmount());

            }else{
                //amount is the amount of the transaction (-) displays if it is a withdraw
                holder.amountText.setText(String.valueOf("- $" + transDataset.get(position).getdAmount()));
                //Balance at this transDataset state is stored in the transactionBalances array at index position (corresponds to transaction index)
                holder.balanceText.setText("$ "+transDataset.get(position).getdBalance());


            }
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return transDataset.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

    }

    public void calculateBalances(ArrayList<TransactionDisplay> transactions){
        /*
                Calculate the updated balance after each transaction occurs
             */

        transCost = currentbalance;
        //For every transaction in the account, calculate the account balance as a result of the trasaction, store for display
        for(int i=0;i<transactions.size();i++){
            if(i==0) {
                //Most recent transaction should be at the top of list, its balance is the current accounts balance
                transactions.get(i).setdBalance(String.valueOf(transCost));
            }
            else {
                //Every transactions 'current balance' should be value before the previous transction took place
                if (transactions.get(i-1).getdType().equals("DEPOSIT") ||
                        transactions.get(i-1).getdType().equals("SEED")) {
                    //i-1 = the transaction infront of i, subtracting when a deposit occurs shows balance before Deposit at i-1 took place
                    transCost -= Double.valueOf(transactions.get(i-1).getdAmount());
                    transactions.get(i).setdBalance(String.valueOf(transCost));
                    System.out.println("Deposit  " + transactions.get(i).getdAmount() + "  transcost is now " + transCost);

                } else if (transactions.get(i-1).getdType().equals("WITHDRAW")) {
                    //i-1 = the transaction infront of i, Adding when a when occurs shows balance before Withdraw at i-1 took place
                    transCost += Double.valueOf(transactions.get(i-1).getdAmount());
                    transactions.get(i).setdBalance(String.valueOf(transCost));
                    System.out.println("Withdrew  " + transactions.get(i).getdAmount() + "  transcost is now " + transCost);


                }else if (transactions.get(i-1).getdType().equals("TRANSFER")) {
                    //i-1 = the transaction infront of i, Adding when a when occurs shows balance before Withdraw at i-1 took place
                    transCost += Double.valueOf(transactions.get(i-1).getdAmount());
                    transactions.get(i).setdBalance(String.valueOf(transCost));
                    System.out.println("Transfer  " + transactions.get(i).getdAmount() + "  transcost is now " + transCost);

                }
            }
        }
    }

    public void getTransactionsFromServer(){
        StringRequest stringRequest;
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "http://54.87.197.206:8080/SparkServer/api/v1/transaction?accountID="+accountnum;
        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                System.out.println("OBTAINED RESPONSE FROM GET USER TRANSACTION REQUEST "+response);
                //Toast.makeText(getContext(),response.toString(),Toast.LENGTH_LONG).show();
                JSONObject obj = null;
                JSONArray transactions = null;
                try {
                    obj = new JSONObject(response);
                    transactions = (JSONArray) obj.get("transactions");
                    for(int i=0; i<transactions.length();i++) {

//                        {"transAmount":"123000000000","fromUserName":"","toAccountType":"CHECKING","transType":"SEED",
//                                "toUserName":"henry lopez","balanceAfter":"123000000000","creationDate":"Wed May 10 01:42:23 UTC 2017"}
                        JSONObject rec = transactions.getJSONObject(i);
                        System.out.println("Got an account " + rec.toString());

                        String type = rec.getString("transType");
                        String amount = formatServerBalance(rec.getString("transAmount"));
                        Date newDateFormat = new Date();
                        try {
                            newDateFormat = new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy").parse(rec.getString("creationDate"));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String numericDate = new SimpleDateFormat("yyyy-MM-dd").format(newDateFormat);
                        String creationDate = new SimpleDateFormat("EEE MMM d, yyyy").format(newDateFormat);
                        String[] parseCreationDate = newDateFormat.toString().split(" ");
                        String[] parseTime = parseCreationDate[3].split(":");
                        Date numericHour = null;
                        String hour = "";
                        try {
                            numericHour = new SimpleDateFormat("k").parse(parseTime[0]);
                            System.out.println("NUMERIC HOUR IS "+numericHour);
                            String[] parseH = numericHour.toString().split(" ");
                            hour = parseH[3];
                            String[] hours = hour.split(":");
                            hour = hours[0];
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String[] parseDate = numericDate.split("-");
                        String hourNumValue = parseDate[0]+parseDate[1]+parseDate[2]+hour+parseTime[1]+parseTime[2];
                        System.out.println("*****************************************NUMERIC TIME VALUE IS "+parseDate[0]+" "+parseDate[1]+" "+parseDate[2]+" "+hour+" "+parseTime[1]+" "+parseTime[2]);
                        //String note = rec.getString("note");
                        String toUserName = rec.getString("toUserName");
                        String toAccountType = rec.getString("toAccountType");
                        transDataset.add(new TransactionDisplay(type, amount, creationDate, toUserName, toAccountType, hourNumValue));
                    }
                    Collections.sort(transDataset, Collections.reverseOrder());
                    calculateBalances(transDataset);
                    rViewAdapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                return params;
            }
        };
        System.out.println("REQUESTING TRANSFER TRANSACTION");
        queue.add(stringRequest);
    }


    public String formatServerBalance(String amount){
//        String temp2 = amount.substring(0,amount.length()-7);
//        String decimal = temp2.substring(temp2.length()-2,temp2.length());
//        String whole = temp2.substring(0,temp2.length()-2);
//        return whole+"."+decimal;
        BigDecimal bigDecimal = new BigDecimal(amount);
        DecimalFormat df2 = new DecimalFormat("###.##");
        return String.valueOf(Double.valueOf(df2.format(bigDecimal.doubleValue())));
    }

    public String formatUserAmountInput(String amount){
        String[] binput = {" "," "};
        //Balance contains cents
        if(amount.contains(".")) {
            binput = amount.split("\\.");
            System.out.println("SIZE IS "+binput.length);
            //Toast.makeText(getContext(),binput.length,Toast.LENGTH_LONG).show();
            //Format decimal values
            if(binput[1].length()==1)
                binput[1] = binput[1]+"0";
            else if(binput[1].length()==0)
                binput[1] = "00";
            else{
                StringBuilder s = new StringBuilder();
                s.append(binput[1].charAt(0)).append(binput[1].charAt(1));
                binput[1] = s.toString();
            }
        }
        //Balance is a whole number
        else {
            binput[0] = amount;
            binput[0] = binput[0].replaceAll("[$,.]", "");
            binput[1] = "00";
        }
        StringBuilder finalinputbalance = new StringBuilder();
        finalinputbalance.append(binput[0]).append(binput[1]).append("0000000");
        BigInteger initialBalance = new BigInteger(finalinputbalance.toString());
        System.out.println("USER INITIAL BALANCE IS "+ initialBalance.toString());
        return initialBalance.toString();
    }


   }


/*
  Helper class to organize attributes that will be dispalyed
*/
class TransactionDisplay implements Comparable {
    private String dType;
    private String dAmount;
    private String dCreationDate;
    //private String dNote;
    private String dToUserName;
    private String dToAccountType;
    private String dBalance;
    private String numericDate;


    public TransactionDisplay(String type, String amount, String creationDate, String toUsername, String toAccountType, String numericDate){
        this.dType = type;
        DecimalFormat precision = new DecimalFormat("0.00");
        this.dAmount = precision.format(Double.valueOf(amount));
        this.dCreationDate = creationDate;
        this.dToUserName = toUsername;
        this.dToAccountType = toAccountType;
        this.numericDate = numericDate;
    }



    public String getdAmount() {
        return dAmount;
    }

    public void setdAmount(String dAmount) {
        this.dAmount = dAmount;
    }

    public String getdCreationDate() {
        return dCreationDate;
    }

    public void setdCreationDate(String dCreationDate) {
        this.dCreationDate = dCreationDate;
    }

    public String getdType() {
        return dType;
    }

    public void setdType(String dType) {
        this.dType = dType;
    }

//    public String getdNote() {
//        return dNote;
//    }
//
//    public void setdNote(String dNote) {
//        this.dNote = dNote;
//    }
    public String getNumericDate() {
        return numericDate;
    }

    public String getdToUserName() {
        return dToUserName;
    }

    public void setdToUserName(String dToUserName) {
        this.dToUserName = dToUserName;
    }

    public String getdToAccountType() {
        return dToAccountType;
    }

    public void setdToAccountType(String dToAccountType) {
        this.dToAccountType = dToAccountType;
    }

    public String getdBalance() {
        return dBalance;
    }

    public void setdBalance(String dBalance) {
        this.dBalance = dBalance;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        TransactionDisplay other = (TransactionDisplay) o;
        BigInteger thisValue = new BigInteger(this.getNumericDate());
        BigInteger otherValue = new BigInteger(other.getNumericDate());
        if(thisValue.compareTo(otherValue) ==1)
            return 1;
        else if(thisValue.compareTo(otherValue) ==-1)
            return -1;
        else
            return 0;
    }
}
