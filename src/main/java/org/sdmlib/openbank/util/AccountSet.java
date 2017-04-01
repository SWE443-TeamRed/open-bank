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
   
package org.sdmlib.openbank.util;

import de.uniks.networkparser.list.SimpleSet;
import org.sdmlib.openbank.Account;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import de.uniks.networkparser.list.NumberList;
import de.uniks.networkparser.list.ObjectSet;
import org.sdmlib.openbank.util.UserSet;
import org.sdmlib.openbank.User;
import java.util.Collections;
import java.util.Date;

import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.openbank.Transaction;
import de.uniks.networkparser.list.BooleanList;

public class AccountSet extends SimpleSet<Account>
{
	protected Class<?> getTypClass() {
		return Account.class;
	}

   public AccountSet()
   {
      // empty
   }

   public AccountSet(Account... objects)
   {
      for (Account obj : objects)
      {
         this.add(obj);
      }
   }

   public AccountSet(Collection<Account> objects)
   {
      this.addAll(objects);
   }

   public static final AccountSet EMPTY_SET = new AccountSet().withFlag(AccountSet.READONLY);


   public AccountPO createAccountPO()
   {
      return new AccountPO(this.toArray(new Account[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.openbank.Account";
   }


   @Override
   public AccountSet getNewList(boolean keyValue)
   {
      return new AccountSet();
   }


   public AccountSet filter(Condition<Account> condition) {
      AccountSet filterList = new AccountSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public AccountSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Account>)value);
      }
      else if (value != null)
      {
         this.add((Account) value);
      }
      
      return this;
   }
   
   public AccountSet without(Account value)
   {
      this.remove(value);
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the balance attribute values. 
    * 
    * @return List of double objects reachable via balance attribute
    */
   public NumberList getBalance()
   {
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add(obj.getBalance());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the balance attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterBalance(double value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getBalance())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the balance attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterBalance(double lower, double upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getBalance() && obj.getBalance() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the balance attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withBalance(double value)
   {
      for (Account obj : this)
      {
         obj.setBalance(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the accountnum attribute values. 
    * 
    * @return List of int objects reachable via accountnum attribute
    */
   public NumberList getAccountnum()
   {
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add(obj.getAccountnum());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the accountnum attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterAccountnum(int value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getAccountnum())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the accountnum attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterAccountnum(int lower, int upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getAccountnum() && obj.getAccountnum() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the accountnum attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withAccountnum(int value)
   {
      for (Account obj : this)
      {
         obj.setAccountnum(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the creationdate attribute values. 
    * 
    * @return List of java.util.Date objects reachable via creationdate attribute
    */
   public ObjectSet getCreationdate()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getCreationdate());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the creationdate attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterCreationdate(Date value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getCreationdate())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the creationdate attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withCreationdate(Date value)
   {
      for (Account obj : this)
      {
         obj.setCreationdate(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Account objects and collect a set of the User objects reached via owner. 
    * 
    * @return Set of User objects reachable via owner
    */
   public UserSet getOwner()
   {
      UserSet result = new UserSet();
      
      for (Account obj : this)
      {
         result.with(obj.getOwner());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference owner pointing to the object passed as parameter. 
    * 
    * @param value The object required as owner neighbor of the collected results. 
    * 
    * @return Set of User objects referring to value via owner
    */
   public AccountSet filterOwner(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      AccountSet answer = new AccountSet();
      
      for (Account obj : this)
      {
         if (neighbors.contains(obj.getOwner()) || (neighbors.isEmpty() && obj.getOwner() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the Owner attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Owner attributes.
    */
   public AccountSet withOwner(User value)
   {
      for (Account obj : this)
      {
         obj.withOwner(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Account objects and collect a set of the Transaction objects reached via credit. 
    * 
    * @return Set of Transaction objects reachable via credit
    */
   public TransactionSet getCredit()
   {
      TransactionSet result = new TransactionSet();
      
      for (Account obj : this)
      {
         result.with(obj.getCredit());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference credit pointing to the object passed as parameter. 
    * 
    * @param value The object required as credit neighbor of the collected results. 
    * 
    * @return Set of Transaction objects referring to value via credit
    */
   public AccountSet filterCredit(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      AccountSet answer = new AccountSet();
      
      for (Account obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getCredit()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the Credit attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Credit attributes.
    */
   public AccountSet withCredit(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withCredit(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Account object passed as parameter from the Credit attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public AccountSet withoutCredit(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withoutCredit(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Account objects and collect a set of the Transaction objects reached via debit. 
    * 
    * @return Set of Transaction objects reachable via debit
    */
   public TransactionSet getDebit()
   {
      TransactionSet result = new TransactionSet();
      
      for (Account obj : this)
      {
         result.with(obj.getDebit());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference debit pointing to the object passed as parameter. 
    * 
    * @param value The object required as debit neighbor of the collected results. 
    * 
    * @return Set of Transaction objects referring to value via debit
    */
   public AccountSet filterDebit(Object value)
   {
      ObjectSet neighbors = new ObjectSet();

      if (value instanceof Collection)
      {
         neighbors.addAll((Collection<?>) value);
      }
      else
      {
         neighbors.add(value);
      }
      
      AccountSet answer = new AccountSet();
      
      for (Account obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getDebit()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the Debit attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Debit attributes.
    */
   public AccountSet withDebit(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withDebit(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Account object passed as parameter from the Debit attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public AccountSet withoutDebit(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withoutDebit(value);
      }
      
      return this;
   }

   
   //==========================================================================
   
   public AccountSet Account(double initialAmount)
   {
      return AccountSet.EMPTY_SET;
   }


   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList transferToUser(double amount, Account destinationAccount)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.transferToUser(amount, destinationAccount) );
      }
      return result;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList myBankTransaction(double amount, Account destinationAccount)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.myBankTransaction(amount, destinationAccount) );
      }
      return result;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList receiveFound(double amount, Account sourceAccount)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.receiveFound(amount, sourceAccount) );
      }
      return result;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList login(String username, String password)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.login(username, password) );
      }
      return result;
   }

   
   //==========================================================================
   
   public AccountSet withdraw(double amount)
   {
      return AccountSet.EMPTY_SET;
   }

   
   //==========================================================================
   
   public AccountSet deposit(Account ToAccount, double amount)
   {
      return AccountSet.EMPTY_SET;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the username attribute values. 
    * 
    * @return List of String objects reachable via username attribute
    */
   public ObjectSet getUsername()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getUsername());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the username attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterUsername(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getUsername()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the username attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterUsername(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getUsername()) <= 0 && obj.getUsername().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the username attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withUsername(String value)
   {
      for (Account obj : this)
      {
         obj.setUsername(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the password attribute values. 
    * 
    * @return List of String objects reachable via password attribute
    */
   public ObjectSet getPassword()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getPassword());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the password attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPassword(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getPassword()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the password attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPassword(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getPassword()) <= 0 && obj.getPassword().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the password attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withPassword(String value)
   {
      for (Account obj : this)
      {
         obj.setPassword(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the name attribute values. 
    * 
    * @return List of String objects reachable via name attribute
    */
   public ObjectSet getName()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getName());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the name attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterName(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the name attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterName(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getName()) <= 0 && obj.getName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the name attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withName(String value)
   {
      for (Account obj : this)
      {
         obj.setName(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the email attribute values. 
    * 
    * @return List of String objects reachable via email attribute
    */
   public ObjectSet getEmail()
   {
      ObjectSet result = new ObjectSet();
      
      for (Account obj : this)
      {
         result.add(obj.getEmail());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the email attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterEmail(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getEmail()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the email attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterEmail(String lower, String upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower.compareTo(obj.getEmail()) <= 0 && obj.getEmail().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the email attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withEmail(String value)
   {
      for (Account obj : this)
      {
         obj.setEmail(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the phone attribute values. 
    * 
    * @return List of int objects reachable via phone attribute
    */
   public NumberList getPhone()
   {
      NumberList result = new NumberList();
      
      for (Account obj : this)
      {
         result.add(obj.getPhone());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the phone attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPhone(int value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getPhone())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the phone attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPhone(int lower, int upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getPhone() && obj.getPhone() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the phone attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withPhone(int value)
   {
      for (Account obj : this)
      {
         obj.setPhone(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the isLoggedIn attribute values. 
    * 
    * @return List of boolean objects reachable via isLoggedIn attribute
    */
   public BooleanList getIsLoggedIn()
   {
      BooleanList result = new BooleanList();
      
      for (Account obj : this)
      {
         result.add(obj.isIsLoggedIn());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the isLoggedIn attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterIsLoggedIn(boolean value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.isIsLoggedIn())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the isLoggedIn attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withIsLoggedIn(boolean value)
   {
      for (Account obj : this)
      {
         obj.setIsLoggedIn(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect a list of the IsConnected attribute values. 
    * 
    * @return List of boolean objects reachable via IsConnected attribute
    */
   public BooleanList getIsConnected()
   {
      BooleanList result = new BooleanList();
      
      for (Account obj : this)
      {
         result.add(obj.isIsConnected());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the IsConnected attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterIsConnected(boolean value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.isIsConnected())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the IsConnected attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withIsConnected(boolean value)
   {
      for (Account obj : this)
      {
         obj.setIsConnected(value);
      }
      
      return this;
   }

   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList sendTransactionInfo(Transaction transaction, double amount, Date date, Date time, String note)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.sendTransactionInfo(transaction, amount, date, time, note) );
      }
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the phone attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPhone(long value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getPhone())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the phone attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterPhone(long lower, long upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (lower <= obj.getPhone() && obj.getPhone() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the phone attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withPhone(long value)
   {
      for (Account obj : this)
      {
         obj.setPhone(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the creationdate attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterCreationdate(String value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value.equals(obj.getCreationdate()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the creationdate attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterCreationdate(String lower, String upper)
   {
      AccountSet result = new AccountSet();
//
//      for (Account obj : this)
//      {
//         if (lower.compareTo(obj.getCreationdate()) <= 0 && obj.getCreationdate().compareTo(upper) <= 0)
//         {
//            result.add(obj);
//         }
//      }
//
      return result;
   }



}
