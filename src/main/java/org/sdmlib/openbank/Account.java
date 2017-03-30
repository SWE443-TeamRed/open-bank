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
import java.util.Date;

import de.uniks.networkparser.EntityUtil;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.openbank.Transaction;
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
      if(this.isLoggedIn()==true)
         return this.balance;
      else
         return 0.0;
   }

   public void setBalance(double value)
   {
      if (this.balance != value) {

         double oldValue = this.balance;
         this.balance = value;
         this.firePropertyChange(PROPERTY_BALANCE, oldValue, value);
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
      result.append(" ").append(this.getUsername());
      result.append(" ").append(this.getPassword());
      result.append(" ").append(this.getName());
      result.append(" ").append(this.getEmail());
      result.append(" ").append(this.getPhone());
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

   private String creationdate;

   public String getCreationdate()
   {
      return this.creationdate;
   }

   public void setCreationdate(String value)
   {
      if ( ! EntityUtil.stringEquals(this.creationdate, value)) {

         String oldValue = this.creationdate;
         this.creationdate = value;
         this.firePropertyChange(PROPERTY_CREATIONDATE, oldValue, value);
      }
   }

   public Account withCreationdate(String value)
   {
      setCreationdate(value);
      return this;
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

   public static final String PROPERTY_USERNAME = "username";

   private String username;

   public String getUsername()
   {
      return this.username;
   }

   public void setUsername(String value)
   {
      if ( ! EntityUtil.stringEquals(this.username, value)) {

         String oldValue = this.username;
         this.username = value;
         this.firePropertyChange(PROPERTY_USERNAME, oldValue, value);
      }
   }

   public Account withUsername(String value)
   {
      setUsername(value);
      return this;
   }


   //==========================================================================

   public static final String PROPERTY_PASSWORD = "password";

   private String password;

   public String getPassword()
   {
      return this.password;
   }

   public void setPassword(String value)
   {
      if ( ! EntityUtil.stringEquals(this.password, value)) {

         String oldValue = this.password;
         this.password = value;
         this.firePropertyChange(PROPERTY_PASSWORD, oldValue, value);
      }
   }

   public Account withPassword(String value)
   {
      setPassword(value);
      return this;
   }


   //==========================================================================

   public static final String PROPERTY_NAME = "name";

   private String name;

   public String getName()
   {
      return this.name;
   }

   public void setName(String value)
   {
      if ( ! EntityUtil.stringEquals(this.name, value)) {

         String oldValue = this.name;
         this.name = value;
         this.firePropertyChange(PROPERTY_NAME, oldValue, value);
      }
   }

   public Account withName(String value)
   {
      setName(value);
      return this;
   }


   //==========================================================================

   public static final String PROPERTY_EMAIL = "email";

   private String email;

   public String getEmail()
   {
      return this.email;
   }

   public void setEmail(String value)
   {
      if ( ! EntityUtil.stringEquals(this.email, value)) {

         String oldValue = this.email;
         this.email = value;
         this.firePropertyChange(PROPERTY_EMAIL, oldValue, value);
      }
   }

   public Account withEmail(String value)
   {
      setEmail(value);
      return this;
   }


   //==========================================================================

   public static final String PROPERTY_PHONE = "phone";

   private int phone;

   public int getPhone()
   {
      return this.phone;
   }

   public void setPhone(int value)
   {
      if (this.phone != value) {

         int oldValue = this.phone;
         this.phone = value;
         this.firePropertyChange(PROPERTY_PHONE, oldValue, value);
      }
   }

   public Account withPhone(int value)
   {
      setPhone(value);
      return this;
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
   *
   * /

   /*
      Constructor setting the initial amount
   */
   public void Account( double initialAmount )
   {
      this.setBalance(initialAmount);
   }


   //User transfer founds to another user,
   // needs to connect and verify destinationAccount connection.
   public boolean transferToUser( double amount, Account destinationAccount )
   {
      if(amount<=0 || destinationAccount==null)
         throw new IllegalArgumentException("Can't have an amount less than 0 or an undefined Account");

      this.setIsConnected(true);
      if(destinationAccount.IsConnected) {
         this.setBalance(this.getBalance() - amount);
         destinationAccount.setBalance(destinationAccount.getBalance() + amount);
         return true;
      }

      return false;//transferToUser did not work.
   }

   //Simple transaction between same user bank accounts.
   public boolean myBankTransaction( double amount, Account destinationAccount )
   {
      if(amount<=0 || destinationAccount==null)
         throw new IllegalArgumentException("Can't have an amount less than 0 or an undefined Account");

      this.setBalance(this.getBalance()-amount);
      destinationAccount.setBalance(destinationAccount.getBalance()+amount);

      return true;
   }


   //This how the second user connects to get found from another user.
   public boolean receiveFound( double amount, Account sourceAccount )
   {
      if(amount<=0 || sourceAccount==null)
         throw new IllegalArgumentException("Can't have an amount less than 0 or an undefined Account");

      //Only connects if sourceAccount is connected.
      if(sourceAccount.IsConnected)
      {
         this.setIsConnected(true);
         return true;
      }
      return false;//receiveFound did not work.
   }

   //This sets the information of the transaction.
   public boolean sendTransactionInfo(Transaction transaction, double amount, Date date, Date time, String note )
   {
      if(transaction==null || date==null || time==null || amount==0)
         throw new IllegalArgumentException("Need an amount, a date, a time and a defined Transaction");

      transaction.setAmount(amount);
      transaction.setDate(date);
      transaction.setTime(time);
      transaction.setNote(note);
      return false;
   }


   public boolean login( String username, String password )
   {
      if(this.username.equals(username) && this.password.equals(password)) {
          this.setLoggedIn(true);
          return true;
      }
      else{
         if(this.username.equals(username))
            System.out.println("Username is incorrect");
         if(this.password.equals(password))
            System.out.println("Username is incorrect");
         return false;
      }
   }

   //To withdraw money from this account.
   public void withdraw( double amount )
   {
      if(amount<=this.getBalance() || amount>0)
         this.setBalance(this.getBalance()-balance);
      else
         throw new IllegalArgumentException("Amount to withdraw should be less or equal to your current balance" +
                 "and greater than 0.");
   }

   //=========================================================================
   public void deposit( double amount )
   {
      
   }

   
   //==========================================================================
   public boolean sendTransactionInfo( Transaction transaction, double amount, String date, String time, String note )
   {
      return false;
   }

   
   //==========================================================================
   
   public static final String PROPERTY_LOGGEDIN = "LoggedIn";
   
   private boolean LoggedIn;

   public boolean isLoggedIn()
   {
      return this.LoggedIn;
   }
   
   public void setLoggedIn(boolean value)
   {
      if (this.LoggedIn != value) {
      
         boolean oldValue = this.LoggedIn;
         this.LoggedIn = value;
         this.firePropertyChange(PROPERTY_LOGGEDIN, oldValue, value);
      }
   }
   
   public Account withLoggedIn(boolean value)
   {
      setLoggedIn(value);
      return this;
   } 
}
