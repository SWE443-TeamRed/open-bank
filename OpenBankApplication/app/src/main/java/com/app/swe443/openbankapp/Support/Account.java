/*
   Copyright (c) 2017 FA
   Permission is hereby granted, free of charge, to any person obtaining a copy of this software
   and associated documentation files (the "Software"), to deal in the Software without restriction,
   including without limitation the rights to use, copy, modify, merge, publish, distribute,
   sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
   furnished to do so, subject to the following conditions:
   The above copyright notice and this permission notice shall be included in all copies or
   substantial portions of the Software.
   The Software shall be used for Good, not Evil.
   THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
   BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
   NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
   DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
   OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */


package com.app.swe443.openbankapp.Support;

import de.uniks.networkparser.interfaces.SendableEntity;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.LinkedList;

/**
 *
 * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
public  class Account implements SendableEntity
{



   //==========================================================================

   protected PropertyChangeSupport listeners = null;

   public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue)
   {
      if (listeners != null) {
         listeners.firePropertyChange(propertyName, oldValue, newValue);
         return true;
      }
      return false;
   }

   public boolean addPropertyChangeListener(PropertyChangeListener listener)
   {
      if (listeners == null) {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(listener);
      return true;
   }

   public boolean addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
      if (listeners == null) {
         listeners = new PropertyChangeSupport(this);
      }
      listeners.addPropertyChangeListener(propertyName, listener);
      return true;
   }

   public boolean removePropertyChangeListener(PropertyChangeListener listener) {
      if (listeners == null) {
         listeners.removePropertyChangeListener(listener);
      }
      listeners.removePropertyChangeListener(listener);
      return true;
   }

   public boolean removePropertyChangeListener(String propertyName,PropertyChangeListener listener) {
      if (listeners != null) {
         listeners.removePropertyChangeListener(propertyName, listener);
      }
      return true;
   }


   //==========================================================================


   public void removeYou()
   {
      setOwner(null);
      withoutCredit(this.getCredit().toArray(new Transaction[this.getCredit().size()]));
      withoutDebit(this.getDebit().toArray(new Transaction[this.getDebit().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
   }


   //==========================================================================

   public static final String PROPERTY_BALANCE = "balance";

   private double balance;

   public double getBalance()
   {
      /*
         If the user is not logged in, they should not be able to get balance
       */
      return this.balance;
   }

   public void setBalance(double value)
   {
      if (value >0) {

         double oldValue = this.balance;
         this.balance = value;
         this.firePropertyChange(PROPERTY_BALANCE, oldValue, value);
      }else{
         throw new IllegalArgumentException("New balance falls below 0, cannot update existing balance to "+value);
      }
   }

   public Account withBalance(double value)
   {
      setBalance(value);
      return this;
   }


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      result.append(" ").append(this.getBalance());
      result.append(" ").append(this.getAccountnum());
      result.append(" ").append(this.getCreationdate());
      return result.substring(1);
   }



   //==========================================================================

   public static final String PROPERTY_ACCOUNTNUM = "accountnum";

   private int accountnum;

   public int getAccountnum()
   {
      return this.accountnum;
   }

   public void setAccountnum(int value)
   {
      if (this.accountnum != value) {

         int oldValue = this.accountnum;
         this.accountnum = value;
         this.firePropertyChange(PROPERTY_ACCOUNTNUM, oldValue, value);
      }
   }

   public Account withAccountnum(int value)
   {
      setAccountnum(value);
      return this;
   }


   //==========================================================================

   public static final String PROPERTY_CREATIONDATE = "creationdate";

   private Date creationdate;

   public Date getCreationdate()
   {
      return this.creationdate;
   }




   /********************************************************************
    * <pre>
    *              many                       one
    * Account ----------------------------------- User
    *              account                   owner
    * </pre>
    */

   public static final String PROPERTY_OWNER = "owner";

   private User owner = null;

   public User getOwner()
   {
      return this.owner;
   }

   public boolean setOwner(User value)
   {
      boolean changed = false;

      if (this.owner != value)
      {
         User oldValue = this.owner;

         if (this.owner != null)
         {
            this.owner = null;
            oldValue.withoutAccount(this);
         }

         this.owner = value;

         if (value != null)
         {
            value.withAccount(this);
         }

         firePropertyChange(PROPERTY_OWNER, oldValue, value);
         changed = true;
      }

      return changed;
   }

   public Account withOwner(User value)
   {
      setOwner(value);
      return this;
   }

   public User createOwner()
   {
      User value = new User();
      withOwner(value);
      return value;
   }


   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              fromAccount                   credit
    * </pre>
    */

   public static final String PROPERTY_CREDIT = "credit";

   private TransactionSet credit = null;

   public TransactionSet getCredit()
   {
      if (this.credit == null)
      {
         return TransactionSet.EMPTY_SET;
      }

      return this.credit;
   }

   public Account withCredit(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.credit == null)
            {
               this.credit = new TransactionSet();
            }

            boolean changed = this.credit.add (item);

            if (changed)
            {
               item.withFromAccount(this);
               firePropertyChange(PROPERTY_CREDIT, null, item);
            }
         }
      }
      return this;
   }

   public Account withoutCredit(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.credit != null) && (item != null))
         {
            if (this.credit.remove(item))
            {
               item.setFromAccount(null);
               firePropertyChange(PROPERTY_CREDIT, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createCredit()
   {
      Transaction value = new Transaction();
      withCredit(value);
      return value;
   }


   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              toAccount                   debit
    * </pre>
    */

   public static final String PROPERTY_DEBIT = "debit";

   private TransactionSet debit = null;

   public TransactionSet getDebit()
   {
      if (this.debit == null)
      {
         return TransactionSet.EMPTY_SET;
      }

      return this.debit;
   }

   public Account withDebit(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.debit == null)
            {
               this.debit = new TransactionSet();
            }

            boolean changed = this.debit.add (item);

            if (changed)
            {
               item.withToAccount(this);
               firePropertyChange(PROPERTY_DEBIT, null, item);
            }
         }
      }
      return this;
   }

   public Account withoutDebit(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.debit != null) && (item != null))
         {
            if (this.debit.remove(item))
            {
               item.setToAccount(null);
               firePropertyChange(PROPERTY_DEBIT, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createDebit()
   {
      Transaction value = new Transaction();
      withDebit(value);
      return value;
   }










   //==========================================================================

   public static final String PROPERTY_ISCONNECTED = "IsConnected";

   private boolean IsConnected;

   public boolean isIsConnected()
   {
      return this.IsConnected;
   }

   public void setIsConnected(boolean value)
   {
      if (this.IsConnected != value) {

         boolean oldValue = this.IsConnected;
         this.IsConnected = value;
         this.firePropertyChange(PROPERTY_ISCONNECTED, oldValue, value);
      }
   }

   public Account withIsConnected(boolean value)
   {
      setIsConnected(value);
      return this;
   }


   /*
   *
   * Log:
   *     Kimberly 03/29/17
   *     4/3 - Henry -> refactored and incorporated transaction serialization
   * /
   /*
      Constructor setting the initial amount
   */
   public void Account( double initialAmount )
   {
      this.setBalance(initialAmount);
   }

    private LinkedList<Transaction> accountTransactions = new LinkedList<Transaction>();

    public LinkedList<Transaction> getAccountTransactions()
    {
        if (this.accountTransactions == null)
        {
            return null;
        }

        return this.accountTransactions;
    }

   //User transfer founds to another user,
   // needs to connect and verify destinationAccount connection.
   public boolean transferToAccount(double amount, Account reciever, String note)
   {
      //Requested transfer funds cannot be negative value or undefined
      if(amount < 0)
         throw new IllegalArgumentException("Can't have an amount less than 0 or an undefined Account");
      else if (reciever==null)
         throw new IllegalArgumentException("Passed in a null for an account to recieve the funds");

      if (amount <= this.getBalance()) {
         //Check this account is connected to other account
            /*TODO: Discuss with creater or isConneccted what it refers to, Accounts must be connected or Users?*/
         if (reciever.getOwner().isLoggedIn() && this.getOwner().isLoggedIn()) {
            //Update this balance to new balance
            this.setBalance(this.getBalance() - amount);
            //Request to receiver for a credit of amount
            //reciever.receiveFunds(amount,note);
            reciever.setBalance(reciever.getBalance()+amount);
            recordTransaction(reciever,true,amount, note);
            recordTransaction(this, false,amount, note);
            return true;

         }
      }
      return false;//transferToUser did not work.
   }


   //User wants to give money to this, recieve the funds if this is able to
   public boolean receiveFunds(double amount, String note)
   {
      Transaction transaction;
      if(amount<=0)
         throw new IllegalArgumentException("Can't have negative or zero amount. You gave: "+amount);

      //Verify the user is logged in and is connected to the other user
      this.setIsConnected(true);
      if(this.isIsConnected() && this.getOwner().isLoggedIn())
      {
         this.setBalance(this.getBalance()+amount);
         return  true;

      }
      return false;//Cannot complete transaction.
   }


   //This sets the information of the transaction.
   public Transaction recordTransaction(Account recordforAccount, Boolean credit, double amount, String note )
   {
      Transaction trans;

      //Create transaction object
      trans = new Transaction();
      trans.setDate(new Date());
      trans.setAmount(amount);

      trans.setNote(note);
       if(recordforAccount != this)
           trans.setTransType(TransactionTypeEnum.TRANSFER);
       else if (credit == true)
           trans.setTransType(TransactionTypeEnum.DEPOSIT);
       else
           trans.setTransType(TransactionTypeEnum.WITHDRAW);
      if(credit) {
         //Credit transaction, Set who is getting amount
         trans.setFromAccount(recordforAccount);
      }else{
         //Debit transaction, set who is recieving amount
         trans.setToAccount(recordforAccount);

      }
      accountTransactions.addFirst(trans);
      return trans;
   }




   //To withdraw money from this account.
   public boolean withdraw(double amount)
   {
      if(amount <= this.getBalance() && amount > 0) {
         recordTransaction(this, false,amount, "Withdrawing ");
         this.setBalance(this.getBalance() - amount);
         return true;
      }
      else
         throw new IllegalArgumentException("Amount to withdraw should be less or equal to your current balance" +
                 "and greater than 0.");

   }

   //=========================================================================
   public boolean deposit( double amount ){
      if(amount > 0) {
         recordTransaction(this, true,amount, "Depositing ");
         this.setBalance(this.getBalance() + amount);
         return true;
      }
      else
         throw new IllegalArgumentException("Amount to deposit should be greater than 0. You entered " + amount);


   }






   //==========================================================================

   public void setCreationdate(Date value)
   {
      if (this.creationdate != value) {

         Date oldValue = this.creationdate;
         this.creationdate = value;
         this.firePropertyChange(PROPERTY_CREATIONDATE, oldValue, value);
      }
   }

   public Account withCreationdate(Date value)
   {
      setCreationdate(value);
      return this;
   }





   //==========================================================================

   public static final String PROPERTY_TYPE = "type";

   private AccountTypeEnum type;

   public AccountTypeEnum getType()
   {
      return this.type;
   }

   public void setType(AccountTypeEnum value)
   {
      if (this.type != value) {

         AccountTypeEnum oldValue = this.type;
         this.type = value;
         this.firePropertyChange(PROPERTY_TYPE, oldValue, value);
      }
   }

   public Account withType(AccountTypeEnum value)
   {
      setType(value);
      return this;
   }


}