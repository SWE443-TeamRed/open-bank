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
import de.uniks.networkparser.EntityUtil;
import org.sdmlib.openbank.util.UserSet;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.Account;
   /**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class Bank implements SendableEntity
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
      withoutCustomerUser(this.getCustomerUser().toArray(new User[this.getCustomerUser().size()]));
      setTransaction(null);
      withoutCustomerAccounts(this.getCustomerAccounts().toArray(new Account[this.getCustomerAccounts().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_FEE = "fee";
   
   private double fee;

   public double getFee()
   {
      return this.fee;
   }
   
   public void setFee(double value)
   {
      if (this.fee != value) {
      
         double oldValue = this.fee;
         this.fee = value;
         this.firePropertyChange(PROPERTY_FEE, oldValue, value);
      }
   }
   
   public Bank withFee(double value)
   {
      setFee(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getFee());
      result.append(" ").append(this.getBankName());
      return result.substring(1);
   }


   
   //==========================================================================
   
   public static final String PROPERTY_BANKNAME = "bankName";
   
   private String bankName;

   public String getBankName()
   {
      return this.bankName;
   }
   
   public void setBankName(String value)
   {
      if ( ! EntityUtil.stringEquals(this.bankName, value)) {
      
         String oldValue = this.bankName;
         this.bankName = value;
         this.firePropertyChange(PROPERTY_BANKNAME, oldValue, value);
      }
   }
   
   public Bank withBankName(String value)
   {
      setBankName(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- User
    *              bank                   customerUser
    * </pre>
    */
   
   public static final String PROPERTY_CUSTOMERUSER = "customerUser";

   private UserSet customerUser = null;
   
   public UserSet getCustomerUser()
   {
      if (this.customerUser == null)
      {
         return UserSet.EMPTY_SET;
      }
   
      return this.customerUser;
   }

   public Bank withCustomerUser(User... value)
   {
      if(value==null){
         return this;
      }
      for (User item : value)
      {
         if (item != null)
         {
            if (this.customerUser == null)
            {
               this.customerUser = new UserSet();
            }
            
            boolean changed = this.customerUser.add (item);

            if (changed)
            {
               item.withBank(this);
               firePropertyChange(PROPERTY_CUSTOMERUSER, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutCustomerUser(User... value)
   {
      for (User item : value)
      {
         if ((this.customerUser != null) && (item != null))
         {
            if (this.customerUser.remove(item))
            {
               item.setBank(null);
               firePropertyChange(PROPERTY_CUSTOMERUSER, item, null);
            }
         }
      }
      return this;
   }

   public User createCustomerUser()
   {
      User value = new User();
      withCustomerUser(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       one
    * Bank ----------------------------------- Transaction
    *              bank                   transaction
    * </pre>
    */
   
   public static final String PROPERTY_TRANSACTION = "transaction";

   private Transaction transaction = null;

   public Transaction getTransaction()
   {
      return this.transaction;
   }

   public boolean setTransaction(Transaction value)
   {
      boolean changed = false;
      
      if (this.transaction != value)
      {
         Transaction oldValue = this.transaction;
         
         if (this.transaction != null)
         {
            this.transaction = null;
            oldValue.setBank(null);
         }
         
         this.transaction = value;
         
         if (value != null)
         {
            value.withBank(this);
         }
         
         firePropertyChange(PROPERTY_TRANSACTION, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Bank withTransaction(Transaction value)
   {
      setTransaction(value);
      return this;
   } 

   public Transaction createTransaction()
   {
      Transaction value = new Transaction();
      withTransaction(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- Account
    *              bank                   customerAccounts
    * </pre>
    */
   
   public static final String PROPERTY_CUSTOMERACCOUNTS = "customerAccounts";

   private AccountSet customerAccounts = null;
   
   public AccountSet getCustomerAccounts()
   {
      if (this.customerAccounts == null)
      {
         return AccountSet.EMPTY_SET;
      }
   
      return this.customerAccounts;
   }

   public Bank withCustomerAccounts(Account... value)
   {
      if(value==null){
         return this;
      }
      for (Account item : value)
      {
         if (item != null)
         {
            if (this.customerAccounts == null)
            {
               this.customerAccounts = new AccountSet();
            }
            
            boolean changed = this.customerAccounts.add (item);

            if (changed)
            {
               item.withBank(this);
               firePropertyChange(PROPERTY_CUSTOMERACCOUNTS, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutCustomerAccounts(Account... value)
   {
      for (Account item : value)
      {
         if ((this.customerAccounts != null) && (item != null))
         {
            if (this.customerAccounts.remove(item))
            {
               item.setBank(null);
               firePropertyChange(PROPERTY_CUSTOMERACCOUNTS, item, null);
            }
         }
      }
      return this;
   }

   public Account createCustomerAccounts()
   {
      Account value = new Account();
      withCustomerAccounts(value);
      return value;
   } 

   
   //==========================================================================
   public boolean validateLogin( int accountID, String username, String password ) {
       if (username == null || password == null || accountID < 0)
           throw new IllegalArgumentException("Invalid parameter(s)");
       Account pulledAccount = this.findAccountByID(accountID);
       User pulledUser = this.findUserByID(username);
       if (pulledAccount != null && pulledUser != null){
           if (pulledUser.getPassword().equals(password)){
               //pulledUser.setLoggedIn(true);
               return true;
           }
       }
       return false;
   }
   //==========================================================================
   public Account findAccountByID( int accountID )
   {
      return null;
   }

   
   //==========================================================================
   public User findUserByID(String userID) {
       /*
       for (User i: ){
           if (i.getUserID().equals(userID))
               return i;
       }
       */
       return null;
   }

   
   //==========================================================================
   public boolean confirmTransaction( int toAcctID, int fromAcctID, Integer dollarValue, Integer decimalValue )
   {
      return false;
   }
}
