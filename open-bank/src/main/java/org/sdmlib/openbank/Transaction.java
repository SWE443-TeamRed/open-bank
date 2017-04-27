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
import org.sdmlib.openbank.TransactionTypeEnum;
import org.sdmlib.openbank.Bank;
import org.sdmlib.openbank.util.AccountSet;
import java.math.BigInteger;
import org.sdmlib.openbank.util.TransactionSet;
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
      setBank(null);
      withoutAccounts(this.getAccounts().toArray(new Account[this.getAccounts().size()]));
      setNext(null);
      setPrevious(null);
      firePropertyChange("REMOVE_YOU", this, null);
   }


   //==========================================================================

   public static final String PROPERTY_AMOUNT = "amount";

   private BigInteger amount;

   public BigInteger getAmount()
   {
      return this.amount;
   }

   /*
   public void setAmount(double value)
   {

      // check for negative, if less then 0 throw IllegalArgumentException
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
*/

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
      // check for negative, if less then 0 throw IllegalArgumentException
      if (value ==null) {
         throw new IllegalArgumentException("Date is null. Invalid Date.");
      }else {
         if (this.date != value) {

            Date oldValue = this.date;
            this.date = value;
            this.firePropertyChange(PROPERTY_DATE, oldValue, value);
         }
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
      if (value ==null) {
         throw new IllegalArgumentException("Date is null. Invalid Date/Time.");
      }else {
         if (this.time != value) {

            Date oldValue = this.time;
            this.time = value;
            this.firePropertyChange(PROPERTY_TIME, oldValue, value);
         }
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


   //==========================================================================
   
   public static final String PROPERTY_TRANSTYPE = "transType";
   
   private TransactionTypeEnum transType;

   public TransactionTypeEnum getTransType()
   {
      return this.transType;
   }
   
   public void setTransType(TransactionTypeEnum value)
   {
      if (value ==null) {
         throw new IllegalArgumentException("Transaction type is not valid!");
      }

      if (this.transType != value) {
      
         TransactionTypeEnum oldValue = this.transType;
         this.transType = value;
         this.firePropertyChange(PROPERTY_TRANSTYPE, oldValue, value);
      }
   }
   
   public Transaction withTransType(TransactionTypeEnum value)
   {
      setTransType(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_CREATIONDATE = "creationdate";
   
   private Date creationdate;

   public Date getCreationdate()
   {
      return this.creationdate;
   }
   
   public void setCreationdate(Date value)
   {
      if (value ==null) {
         throw new IllegalArgumentException("Creationdate is not valid!");
      }

      if (this.creationdate != value) {
      
         Date oldValue = this.creationdate;
         this.creationdate = value;
         this.firePropertyChange(PROPERTY_CREATIONDATE, oldValue, value);
      }
   }
   
   public Transaction withCreationdate(Date value)
   {
      setCreationdate(value);
      return this;
   }

   //*************** Custom Methods ****************//
   public Account getTransactions(String userID){
      JsonPersistency json = new JsonPersistency();

      Account accnt = json.fromJson(userID);

      return accnt;
   }

   
   /********************************************************************
    * <pre>
    *              one                       one
    * Transaction ----------------------------------- Bank
    *              transaction                   bank
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
            oldValue.setTransaction(null);
         }
         
         this.bank = value;
         
         if (value != null)
         {
            value.withTransaction(this);
         }
         
         firePropertyChange(PROPERTY_BANK, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Transaction withBank(Bank value)
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
    *              many                       many
    * Transaction ----------------------------------- Account
    *              transactions                   accounts
    * </pre>
    */
   
   public static final String PROPERTY_ACCOUNTS = "accounts";

   private AccountSet accounts = null;
   
   public AccountSet getAccounts()
   {
      if (this.accounts == null)
      {
         return AccountSet.EMPTY_SET;
      }
   
      return this.accounts;
   }

   public Transaction withAccounts(Account... value)
   {
      if(value==null){
         return this;
      }
      for (Account item : value)
      {
         if (item != null)
         {
            if (this.accounts == null)
            {
               this.accounts = new AccountSet();
            }
            
            boolean changed = this.accounts.add (item);

            if (changed)
            {
               item.withTransactions(this);
               firePropertyChange(PROPERTY_ACCOUNTS, null, item);
            }
         }
      }
      return this;
   } 

   public Transaction withoutAccounts(Account... value)
   {
      for (Account item : value)
      {
         if ((this.accounts != null) && (item != null))
         {
            if (this.accounts.remove(item))
            {
               item.withoutTransactions(this);
               firePropertyChange(PROPERTY_ACCOUNTS, item, null);
            }
         }
      }
      return this;
   }

   public Account createAccounts()
   {
      Account value = new Account();
      withAccounts(value);
      return value;
   } 

   
   //==========================================================================
   
   public void setAmount(BigInteger value)
   {
      if (this.amount != value) {
      
         BigInteger oldValue = this.amount;
         this.amount = value;
         this.firePropertyChange(PROPERTY_AMOUNT, oldValue, value);
      }
   }
   
   public Transaction withAmount(BigInteger value)
   {
      setAmount(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       one
    * Transaction ----------------------------------- Transaction
    *              previous                   next
    * </pre>
    */
   
   public static final String PROPERTY_NEXT = "next";

   private Transaction next = null;

   public Transaction getNext()
   {
      return this.next;
   }
   public TransactionSet getNextTransitive()
   {
      TransactionSet result = new TransactionSet().with(this);
      return result.getNextTransitive();
   }


   public boolean setNext(Transaction value)
   {
      boolean changed = false;
      
      if (this.next != value)
      {
         Transaction oldValue = this.next;
         
         if (this.next != null)
         {
            this.next = null;
            oldValue.setPrevious(null);
         }
         
         this.next = value;
         
         if (value != null)
         {
            value.withPrevious(this);
         }
         
         firePropertyChange(PROPERTY_NEXT, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Transaction withNext(Transaction value)
   {
      setNext(value);
      return this;
   } 

   public Transaction createNext()
   {
      Transaction value = new Transaction();
      withNext(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       one
    * Transaction ----------------------------------- Transaction
    *              next                   previous
    * </pre>
    */
   
   public static final String PROPERTY_PREVIOUS = "previous";

   private Transaction previous = null;

   public Transaction getPrevious()
   {
      return this.previous;
   }
   public TransactionSet getPreviousTransitive()
   {
      TransactionSet result = new TransactionSet().with(this);
      return result.getPreviousTransitive();
   }


   public boolean setPrevious(Transaction value)
   {
      boolean changed = false;
      
      if (this.previous != value)
      {
         Transaction oldValue = this.previous;
         
         if (this.previous != null)
         {
            this.previous = null;
            oldValue.setNext(null);
         }
         
         this.previous = value;
         
         if (value != null)
         {
            value.withNext(this);
         }
         
         firePropertyChange(PROPERTY_PREVIOUS, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Transaction withPrevious(Transaction value)
   {
      setPrevious(value);
      return this;
   } 

   public Transaction createPrevious()
   {
      Transaction value = new Transaction();
      withPrevious(value);
      return value;
   } 
}
