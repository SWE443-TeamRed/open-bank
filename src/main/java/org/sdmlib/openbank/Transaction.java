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
import org.sdmlib.openbank.Account;
   /**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class Transaction implements SendableEntity
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
      setFromAccount(null);
      setToAccount(null);
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_AMOUNT = "amount";
   
   private double amount;

   public double getAmount()
   {
      return this.amount;
   }
   
   public void setAmount(double value)
   {
      // check for negative if less then 0
      if (value < 0) {
         throw new IllegalArgumentException("Amount is not valid!");
      }else{
         if (this.amount != value) {

            double oldValue = this.amount;
            this.amount = value;
            this.firePropertyChange(PROPERTY_AMOUNT, oldValue, value);
         }
      }
   }
   
   public Transaction withAmount(double value)
   {
      setAmount(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getAmount());
      result.append(" ").append(this.getNote());
      return result.substring(1);
   }


   
   //==========================================================================
   
   public static final String PROPERTY_DATE = "date";
   
   private Date date;

   public Date getDate()
   {
      return this.date;
   }
   
   public void setDate(Date value)
   {
      if (this.date != value) {
      
         Date oldValue = this.date;
         this.date = value;
         this.firePropertyChange(PROPERTY_DATE, oldValue, value);
      }
   }
   
   public Transaction withDate(Date value)
   {
      setDate(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_TIME = "time";
   
   private Date time;

   public Date getTime()
   {
      return this.time;
   }
   
   public void setTime(Date value)
   {
      if (this.time != value) {
      
         Date oldValue = this.time;
         this.time = value;
         this.firePropertyChange(PROPERTY_TIME, oldValue, value);
      }
   }
   
   public Transaction withTime(Date value)
   {
      setTime(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_NOTE = "note";
   
   private String note;

   public String getNote()
   {
      return this.note;
   }
   
   public void setNote(String value)
   {
      if ( ! EntityUtil.stringEquals(this.note, value)) {
      
         String oldValue = this.note;
         this.note = value;
         this.firePropertyChange(PROPERTY_NOTE, oldValue, value);
      }
   }
   
   public Transaction withNote(String value)
   {
      setNote(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Transaction ----------------------------------- Account
    *              credit                   fromAccount
    * </pre>
    */
   
   public static final String PROPERTY_FROMACCOUNT = "fromAccount";

   private Account fromAccount = null;

   public Account getFromAccount()
   {
      return this.fromAccount;
   }

   public boolean setFromAccount(Account value)
   {
      boolean changed = false;
      
      if (this.fromAccount != value)
      {
         Account oldValue = this.fromAccount;
         
         if (this.fromAccount != null)
         {
            this.fromAccount = null;
            oldValue.withoutCredit(this);
         }
         
         this.fromAccount = value;
         
         if (value != null)
         {
            value.withCredit(this);
         }
         
         firePropertyChange(PROPERTY_FROMACCOUNT, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Transaction withFromAccount(Account value)
   {
      setFromAccount(value);
      return this;
   } 

   public Account createFromAccount()
   {
      Account value = new Account();
      withFromAccount(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              many                       one
    * Transaction ----------------------------------- Account
    *              debit                   toAccount
    * </pre>
    */
   
   public static final String PROPERTY_TOACCOUNT = "toAccount";

   private Account toAccount = null;

   public Account getToAccount()
   {
      return this.toAccount;
   }

   public boolean setToAccount(Account value)
   {
      boolean changed = false;
      
      if (this.toAccount != value)
      {
         Account oldValue = this.toAccount;
         
         if (this.toAccount != null)
         {
            this.toAccount = null;
            oldValue.withoutDebit(this);
         }
         
         this.toAccount = value;
         
         if (value != null)
         {
            value.withDebit(this);
         }
         
         firePropertyChange(PROPERTY_TOACCOUNT, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Transaction withToAccount(Account value)
   {
      setToAccount(value);
      return this;
   } 

   public Account createToAccount()
   {
      Account value = new Account();
      withToAccount(value);
      return value;
   } 
}
