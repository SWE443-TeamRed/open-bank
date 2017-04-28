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

package org.sdmlib.openbank;

import de.uniks.networkparser.interfaces.SendableEntity;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyChangeListener;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.uniks.networkparser.EntityUtil;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.AccountTypeEnum;
import org.sdmlib.openbank.Bank;
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
      setBank(null);
      setEmployingBank(null);
      withoutToTransaction(this.getToTransaction().toArray(new Transaction[this.getToTransaction().size()]));
      withoutFromTransaction(this.getFromTransaction().toArray(new Transaction[this.getFromTransaction().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
   }


   //==========================================================================

   public static final String PROPERTY_BALANCE = "balance";

   private BigInteger balance;

   public BigInteger getBalance()
   {
      /*
         If the user is not logged in, they should not be able to get balance
       */
         return this.balance;
   }

   public void setBalance(BigInteger value)
   {
      if (value.compareTo(BigInteger.ONE) > 0) {

         BigInteger oldValue = this.balance;
         this.balance = value;
         this.firePropertyChange(PROPERTY_BALANCE, oldValue, value);
      }else{
         throw new IllegalArgumentException("New balance falls below 0, cannot update existing balance to "+value);
      }
   }

   public Account withBalance(BigInteger value)
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
      if (value <0) {
         throw new IllegalArgumentException("Account number is not valid!");
      }

      if (this.accountnum!= value) {
/*
         if(this.getBank().getCustomerAccounts().filterAccountnum(value).size() == 0 &&
                 this.getBank().getAdminAccounts().filterAccountnum(value).size() == 0) {
            int oldValue = this.accountnum;
            this.accountnum = value;
            this.firePropertyChange(PROPERTY_ACCOUNTNUM, oldValue, value);
         }
         else {
         */
            value = this.getBank().getNextID();
            int oldValue = this.accountnum;
            this.accountnum = value;
            this.firePropertyChange(PROPERTY_ACCOUNTNUM, oldValue, value);
         //}
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
   public void Account( BigInteger initialAmount )
   {
      this.setBalance(initialAmount);
   }


    //User transfer founds to another user,
    // needs to connect and verify destinationAccount connection.
    public boolean transferToAccount(BigInteger amount, Account receiver, String note)
    {
       //Requested transfer funds cannot be negative value or undefined
        if(amount.compareTo(BigInteger.ONE) < 0)
            throw new IllegalArgumentException("Can't have an amount less than 0 or an undefined Account");
        else if (receiver==null)
           throw new IllegalArgumentException("Passed in a null for an account to recieve the funds");

        if (amount.compareTo(this.getBalance()) <= 0) {
            this.setBalance(this.getBalance().subtract(amount));
            receiver.receiveFunds(amount,note);
            recordTransaction(this,receiver,TransactionTypeEnum.TRANSFER,amount, note);
            //recordTransaction(this, false,amount, note);
            return true;
        }
        return false;//transferToUser did not work.
    }


    //User wants to give money to this, recieve the funds if this is able to
   public boolean receiveFunds(BigInteger amount, String note)
   {
       if(amount.compareTo(BigInteger.ONE) <=0)
           throw new IllegalArgumentException("Can't have negative or zero amount. You gave: "+amount);

       //Verify the user is logged in and is connected to the other user
       this.setIsConnected(true);
       if(this.isIsConnected() && this.getOwner().isLoggedIn())
       {
           this.setBalance(this.getBalance().add(amount));
           return  true;

       }
       return false;//Cannot complete transaction.
   }
    //To withdraw money from this account.
    public boolean withdraw(BigInteger amount)
    {
        //if(amount <= this.getBalance() && amount > 0) {
       int resAmntgrtZero= amount.compareTo(BigInteger.ZERO);
       int resBlncGrtAmnt= amount.compareTo(this.getBalance());

       if(resBlncGrtAmnt==-1 && resAmntgrtZero==1) {
            recordTransaction(this,null, TransactionTypeEnum.WITHDRAW,amount, "Withdrawing ");
            this.setBalance(this.getBalance().subtract(amount));
            return true;
        }
        else
            throw new IllegalArgumentException("Amount to withdraw should be less or equal to your current balance" +
                    "and greater than 0.");

    }

   //=========================================================================
   public boolean deposit( BigInteger amount ){

      int res= amount.compareTo(BigInteger.ZERO);

       //if(amount > 0) {
      if(res==1){//
           recordTransaction(null,this, TransactionTypeEnum.DEPOSIT,amount, "Depositing ");
           this.setBalance(this.getBalance().add(amount));
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

   


   /********************************************************************
    * <pre>
    *              many                       one
    * Account ----------------------------------- Bank
    *              customerAccounts                   bank
    * </pre>
    */
   
   public static final String PROPERTY_BANK = "bank";

   private Bank bank = null;

   public Bank getBank()
   {
      return this.bank;
   }

   public boolean setBank(Bank value)
   {
      boolean changed = false;
      
      if (this.bank != value)
      {
         Bank oldValue = this.bank;
         
         if (this.bank != null)
         {
            this.bank = null;
            oldValue.withoutCustomerAccounts(this);
         }
         
         this.bank = value;
         
         if (value != null)
         {
            value.withCustomerAccounts(this);
         }
         
         firePropertyChange(PROPERTY_BANK, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Account withBank(Bank value)
   {
      setBank(value);
      return this;
   } 

   public Bank createBank()
   {
      Bank value = new Bank();
      withBank(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Account ----------------------------------- Bank
    *              adminAccounts                   employingBank
    * </pre>
    */
   
   public static final String PROPERTY_EMPLOYINGBANK = "employingBank";

   private Bank employingBank = null;

   public Bank getEmployingBank()
   {
      return this.employingBank;
   }

   public boolean setEmployingBank(Bank value)
   {
      boolean changed = false;
      
      if (this.employingBank != value)
      {
         Bank oldValue = this.employingBank;
         
         if (this.employingBank != null)
         {
            this.employingBank = null;
            oldValue.withoutAdminAccounts(this);
         }
         
         this.employingBank = value;
         
         if (value != null)
         {
            value.withAdminAccounts(this);
         }
         
         firePropertyChange(PROPERTY_EMPLOYINGBANK, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Account withEmployingBank(Bank value)
   {
      setEmployingBank(value);
      return this;
   } 

   public Bank createEmployingBank()
   {
      Bank value = new Bank();
      withEmployingBank(value);
      return value;
   } 



   
   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              ToAccount                   ToTransaction
    * </pre>
    */
   
   public static final String PROPERTY_TOTRANSACTION = "ToTransaction";

   private TransactionSet ToTransaction = null;
   
   public TransactionSet getToTransaction()
   {
      if (this.ToTransaction == null)
      {
         return TransactionSet.EMPTY_SET;
      }
   
      return this.ToTransaction;
   }

   public Account withToTransaction(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.ToTransaction == null)
            {
               this.ToTransaction = new TransactionSet();
            }
            
            boolean changed = this.ToTransaction.add (item);

            if (changed)
            {
               item.withToAccount(this);
               firePropertyChange(PROPERTY_TOTRANSACTION, null, item);
            }
         }
      }
      return this;
   } 

   public Account withoutToTransaction(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.ToTransaction != null) && (item != null))
         {
            if (this.ToTransaction.remove(item))
            {
               item.setToAccount(null);
               firePropertyChange(PROPERTY_TOTRANSACTION, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createToTransaction()
   {
      Transaction value = new Transaction();
      withToTransaction(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Account ----------------------------------- Transaction
    *              FromAccount                   FromTransaction
    * </pre>
    */
   
   public static final String PROPERTY_FROMTRANSACTION = "FromTransaction";

   private TransactionSet FromTransaction = null;
   
   public TransactionSet getFromTransaction()
   {
      if (this.FromTransaction == null)
      {
         return TransactionSet.EMPTY_SET;
      }
   
      return this.FromTransaction;
   }

   public Account withFromTransaction(Transaction... value)
   {
      if(value==null){
         return this;
      }
      for (Transaction item : value)
      {
         if (item != null)
         {
            if (this.FromTransaction == null)
            {
               this.FromTransaction = new TransactionSet();
            }
            
            boolean changed = this.FromTransaction.add (item);

            if (changed)
            {
               item.withFromAccount(this);
               firePropertyChange(PROPERTY_FROMTRANSACTION, null, item);
            }
         }
      }
      return this;
   } 

   public Account withoutFromTransaction(Transaction... value)
   {
      for (Transaction item : value)
      {
         if ((this.FromTransaction != null) && (item != null))
         {
            if (this.FromTransaction.remove(item))
            {
               item.setFromAccount(null);
               firePropertyChange(PROPERTY_FROMTRANSACTION, item, null);
            }
         }
      }
      return this;
   }

   public Transaction createFromTransaction()
   {
      Transaction value = new Transaction();
      withFromTransaction(value);
      return value;
   } 

   
   //==========================================================================
   public Transaction recordTransaction( Account sender, Account receiver, TransactionTypeEnum type, BigInteger amount, String note )
   {
      Transaction trans = new Transaction();
      trans.setDate(new Date());
      trans.setAmount(amount);
      trans.setNote(note);
      trans.setTransType(type);
      trans.setToAccount(receiver);
      trans.setFromAccount(sender);
      trans.setNext(bank.getTransaction());
      bank.setTransaction(trans);
      return trans;
   }
}
