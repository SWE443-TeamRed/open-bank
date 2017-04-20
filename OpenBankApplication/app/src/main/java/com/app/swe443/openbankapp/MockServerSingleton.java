package com.app.swe443.openbankapp;

import com.app.swe443.openbankapp.Support.Account;
import com.app.swe443.openbankapp.Support.AccountSet;
import com.app.swe443.openbankapp.Support.AccountTypeEnum;
import com.app.swe443.openbankapp.Support.Bank;
import com.app.swe443.openbankapp.Support.Transaction;
import com.app.swe443.openbankapp.Support.TransactionTypeEnum;
import com.app.swe443.openbankapp.Support.User;
import com.app.swe443.openbankapp.Support.UserSet;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by hlope on 4/18/2017.
 */

public class MockServerSingleton {


    private static MockServerSingleton singleton= new MockServerSingleton();
    private static Bank mainBank;
    private static User loggedInUser;
    private static int accountindex;

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private MockServerSingleton() {
        mainBank = new Bank();
        //==================================================================
        User user = new User()
                .withName("Admin")
                .withPassword("a")
                .withPhone("Whatever")
                .withEmail("dontcare")
                .withUserID("a")
                .withIsAdmin(false)
                .withBank(mainBank)
                .withUsername("a");
                /*
                    TODO WANT TO GET A UNIQUE ACCOUTNNUM TO CREATE AN ACCOUNT, TELL SERVER TO GIVE ACCOUNTNUM
                    TODO AS THE SIZE OF ALL THE ACCOUNTS+1
                 */
        int newAccountNum= this.getUniqueAccountNum();
        Account newAccount = new Account()
                .withAccountnum(newAccountNum)
                .withType(AccountTypeEnum.SAVINGS)
                .withOwner(user)
                .withCreationdate(new Date())
                .withBalance(Double.valueOf("10.0"));
        user.withAccount(newAccount);
        Transaction t = new Transaction()
                .withAmount(Double.valueOf("10.0"))
                .withBank(mainBank)
                .withDate(new Date())
                .withToAccount(newAccount)
                .withNote("Initial Seeding")
                .withTransType(TransactionTypeEnum.Create);
        newAccount.getAccountTransactions().addFirst(t);

        mainBank.withCustomerUser(user);
        user = new User()
                .withName("Admin2")
                .withPassword("b")
                .withPhone("Whatever")
                .withEmail("docare")
                .withUserID("b")
                .withIsAdmin(false)
                .withBank(mainBank)
                .withUsername("b");
                /*
                    TODO WANT TO GET A UNIQUE ACCOUTNNUM TO CREATE AN ACCOUNT, TELL SERVER TO GIVE ACCOUNTNUM
                    TODO AS THE SIZE OF ALL THE ACCOUNTS+1
                 */
        newAccountNum= this.getUniqueAccountNum();
        newAccount = new Account()
                .withAccountnum(newAccountNum)
                .withType(AccountTypeEnum.SAVINGS)
                .withOwner(user)
                .withCreationdate(new Date())
                .withBalance(Double.valueOf("500.0"));
        user.withAccount(newAccount);
         t = new Transaction()
                .withAmount(Double.valueOf("500.0"))
                 .withBank(mainBank)
                 .withDate(new Date())
                 .withToAccount(newAccount)
                 .withNote("Initial Seeding")
                 .withTransType(TransactionTypeEnum.Create);
        newAccount.getAccountTransactions().addFirst(t);

        mainBank.withCustomerUser(user);
        //=======================================================
    }

    /* Static 'singelton' method */
    public static MockServerSingleton getInstance( ) {
        if(singleton == null) {
            singleton = new MockServerSingleton();

        }
        return singleton;
    }

    /* Other methods protected by singleton-ness */
    public Bank getBank( ) {
        return mainBank;
    }


    public User getLoggedInUser(){
        return loggedInUser;
    }
    public void setLoggedInUser(String email){
        loggedInUser = mainBank.getCustomerUser().filterEmail(email).get(0);

    }

    //Does the account with the specified account number exists? If so, return the user's name and account type
    /*
        TODO MAY NOT WANT TO RETURN THE TOHER USER'S ACCOUNT, SECURITY. MAYBE JUST NAME
     */
    public boolean doesAccountExists(int accountnum){
        AccountSet accounts = mainBank.getCustomerAccounts();
        for(Account acc: accounts){
            if(acc.getAccountnum()==accountnum)
                return true;
        }
        return false;
    }

    /*
           TODO BANK CLASS, WANT TO BE ABLE TO GET THE USER WHO HAS ACCOUNTNUM X
     */
    public String getRecieverInfo(int accountnum){
        UserSet users = mainBank.getCustomerUser();
        User reciever;
        for(User user : users){
            if(user.getAccount().filterAccountnum(accountnum).get(0) != null){
                reciever = user;
                Account acc = reciever.getAccount().filterAccountnum(accountnum).get(0);
                return acc.getOwner().getName()+"'s "+acc.getType().toString() + " account";
            }
        }
        return "USER DOES NOT EXISTS";
    }

    //The account index being accessed by the user in the UI
    public void setAccountIndex(int index){
        accountindex = index;
    }

    public int getAccountIndex(){
        return accountindex;
    }
    public void transfer(int accountnum, double amount){
        UserSet users = mainBank.getCustomerUser();
        System.out.println("THERE ARE USERS AMOUNT OF "+users.size());
        System.out.println("TRYING TO GIVE IT TO USER WITH ACCOUNTNUM OF "+accountnum);

        User reciever;
        for(User user : users){
            System.out.println("This USERS ACCOUNTS SIZE ARE  "+user.getAccount().size());
            if(user.getAccount().filterAccountnum(accountnum).get(0) != null){
                reciever = user;
                System.out.println("RECIEVER HAS "+reciever.getAccount().size() + " accounts");
                for(Account ac: reciever.getAccount())
                    System.out.println("ACCOUNT NUM OF "+ac.getAccountnum());
                Account acc = reciever.getAccount().filterAccountnum(accountnum).get(0);
                System.out.println("RECIEVERS ACCOUNT IS  "+acc.getAccountnum());
                loggedInUser.getAccount().get(accountindex).transferToAccount(amount,acc,"Transfer Note");
                System.out.println("TRANSFER HAS OCCURED\n" +
                        "loggedinuser has debit count of "+loggedInUser.getAccount().get(getAccountIndex()).getDebit().size()+"\n"+
                " and credit of "+loggedInUser.getAccount().get(getAccountIndex()).getCredit().size()+
                        "  and the person that recieved that money has credit size of "+
                reciever.getAccount().filterAccountnum(accountnum).get(0).getCredit().size() + " debit of"+
                        reciever.getAccount().filterAccountnum(accountnum).get(0).getDebit().size() );
            }
        }

    }



    public ArrayList<Account> getAccounts(){
        ArrayList<Account> trans = new ArrayList<Account>();
        trans.addAll(loggedInUser.getAccount());
        return trans;
    }

    /*
        TODO DOES BACK CLASS HAVE A SET CONTAINING ALL ACCOUNTS ACROSS ALL USERS? WANT TO MAKE ACCOUNTNUM UNIQUE

     */
    public int getUniqueAccountNum(){
        UserSet users = mainBank.getCustomerUser();
        int total = 0;
        for(User user : users){
            total += user.getAccount().size();
        }

        return total+1;
    }

    public ArrayList<Transaction> getTransactions(){
        ArrayList<Transaction> trans = new ArrayList<Transaction>();
        trans.addAll(loggedInUser.getAccount().get(accountindex).getAccountTransactions());
        return trans;

    }

    public Account getAccount(){
        return loggedInUser.getAccount().get(accountindex);
    }


}