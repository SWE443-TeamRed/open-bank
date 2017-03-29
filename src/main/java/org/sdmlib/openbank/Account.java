/*
   Copyright (c) 2017 hlope
   
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
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.openbank.Transaction;
   /**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class Account implements SendableEntity
{

   public Account(){
   }
   //==========================================================================
   public boolean login( String username, String password )
   {
      if(username.equals(this.getUsername())&& password.equals(this.getPassword())){
         this.setIsLoggedIn(true);
         return true;
      }else{
         return false;
      }
   }

   
   //==========================================================================
   public double withdraw(  )
   {
      return 0;
   }

   
   //==========================================================================
   public double deposit(  )
   {
      return 0;
   }

   
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


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getUsername());
      result.append(" ").append(this.getPassword());
      result.append(" ").append(this.getName());
      result.append(" ").append(this.getEmail());
      result.append(" ").append(this.getPhone());
      result.append(" ").append(this.getBalance());
      return result.substring(1);
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
   
   public static final String PROPERTY_BALANCE = "balance";
   
   private double balance;

   public double getBalance()
   {
      if(this.isIsLoggedIn())
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

   
   //==========================================================================
   
   public static final String PROPERTY_ISLOGGEDIN = "isLoggedIn";
   
   private boolean isLoggedIn;

   public boolean isIsLoggedIn()
   {
      return this.isLoggedIn;
   }
   
   public void setIsLoggedIn(boolean value)
   {
      if (this.isLoggedIn != value) {
      
         boolean oldValue = this.isLoggedIn;
         this.isLoggedIn = value;
         this.firePropertyChange(PROPERTY_ISLOGGEDIN, oldValue, value);
      }
   }
   
   public Account withIsLoggedIn(boolean value)
   {
      setIsLoggedIn(value);
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
}
