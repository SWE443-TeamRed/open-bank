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
   
package org.sdmlib.openbank.util;

import de.uniks.networkparser.list.SimpleSet;
import org.sdmlib.openbank.Transaction;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import java.util.Date;

import de.uniks.networkparser.list.NumberList;
import de.uniks.networkparser.list.ObjectSet;
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.TransactionTypeEnum;
import org.sdmlib.openbank.util.BankSet;
import org.sdmlib.openbank.Bank;
import java.util.Collections;
import org.sdmlib.openbank.util.TransactionSet;

public class TransactionSet extends SimpleSet<Transaction>
{
	protected Class<?> getTypClass() {
		return Transaction.class;
	}

   public TransactionSet()
   {
      // empty
   }

   public TransactionSet(Transaction... objects)
   {
      for (Transaction obj : objects)
      {
         this.add(obj);
      }
   }

   public TransactionSet(Collection<Transaction> objects)
   {
      this.addAll(objects);
   }

   public static final TransactionSet EMPTY_SET = new TransactionSet().withFlag(TransactionSet.READONLY);


   public TransactionPO createTransactionPO()
   {
      return new TransactionPO(this.toArray(new Transaction[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.openbank.Transaction";
   }


   @Override
   public TransactionSet getNewList(boolean keyValue)
   {
      return new TransactionSet();
   }


   public TransactionSet filter(Condition<Transaction> condition) {
      TransactionSet filterList = new TransactionSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public TransactionSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Transaction>)value);
      }
      else if (value != null)
      {
         this.add((Transaction) value);
      }
      
      return this;
   }
   
   public TransactionSet without(Transaction value)
   {
      this.remove(value);
      return this;
   }


   /**
    * Loop through the current set of Transaction objects and collect a list of the amount attribute values. 
    * 
    * @return List of double objects reachable via amount attribute
    */
   public NumberList getAmount()
   {
      NumberList result = new NumberList();
      
      for (Transaction obj : this)
      {
         result.add(obj.getAmount());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the amount attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterAmount(double value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value == obj.getAmount())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the amount attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterAmount(double lower, double upper)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (lower <= obj.getAmount() && obj.getAmount() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and assign value to the amount attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Transaction objects now with new attribute values.
    */
   public TransactionSet withAmount(double value)
   {
      for (Transaction obj : this)
      {
         obj.setAmount(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Transaction objects and collect a list of the date attribute values. 
    * 
    * @return List of java.util.Date objects reachable via date attribute
    */
   public ObjectSet getDate()
   {
      ObjectSet result = new ObjectSet();
      
      for (Transaction obj : this)
      {
         result.add(obj.getDate());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the date attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterDate(Date value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value == obj.getDate())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and assign value to the date attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Transaction objects now with new attribute values.
    */
   public TransactionSet withDate(Date value)
   {
      for (Transaction obj : this)
      {
         obj.setDate(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Transaction objects and collect a list of the time attribute values. 
    * 
    * @return List of java.util.Date objects reachable via time attribute
    */
   public ObjectSet getTime()
   {
      ObjectSet result = new ObjectSet();
      
      for (Transaction obj : this)
      {
         result.add(obj.getTime());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the time attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterTime(Date value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value == obj.getTime())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and assign value to the time attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Transaction objects now with new attribute values.
    */
   public TransactionSet withTime(Date value)
   {
      for (Transaction obj : this)
      {
         obj.setTime(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Transaction objects and collect a list of the note attribute values. 
    * 
    * @return List of String objects reachable via note attribute
    */
   public ObjectSet getNote()
   {
      ObjectSet result = new ObjectSet();
      
      for (Transaction obj : this)
      {
         result.add(obj.getNote());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the note attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterNote(String value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value.equals(obj.getNote()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the note attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterNote(String lower, String upper)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (lower.compareTo(obj.getNote()) <= 0 && obj.getNote().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and assign value to the note attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Transaction objects now with new attribute values.
    */
   public TransactionSet withNote(String value)
   {
      for (Transaction obj : this)
      {
         obj.setNote(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Transaction objects and collect a list of the transType attribute values. 
    * 
    * @return List of org.sdmlib.openbank.TransactionTypeEnum objects reachable via transType attribute
    */
   public TransactionTypeEnumSet getTransType()
   {
      TransactionTypeEnumSet result = new TransactionTypeEnumSet();
      
      for (Transaction obj : this)
      {
         result.add(obj.getTransType());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the transType attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterTransType(TransactionTypeEnum value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value == obj.getTransType())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and assign value to the transType attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Transaction objects now with new attribute values.
    */
   public TransactionSet withTransType(TransactionTypeEnum value)
   {
      for (Transaction obj : this)
      {
         obj.setTransType(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Transaction objects and collect a list of the creationdate attribute values. 
    * 
    * @return List of java.util.Date objects reachable via creationdate attribute
    */
   public ObjectSet getCreationdate()
   {
      ObjectSet result = new ObjectSet();
      
      for (Transaction obj : this)
      {
         result.add(obj.getCreationdate());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the creationdate attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterCreationdate(Date value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value == obj.getCreationdate())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and assign value to the creationdate attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Transaction objects now with new attribute values.
    */
   public TransactionSet withCreationdate(Date value)
   {
      for (Transaction obj : this)
      {
         obj.setCreationdate(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Transaction objects and collect a set of the Bank objects reached via bank. 
    * 
    * @return Set of Bank objects reachable via bank
    */
   public BankSet getBank()
   {
      BankSet result = new BankSet();
      
      for (Transaction obj : this)
      {
         result.with(obj.getBank());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Transaction objects and collect all contained objects with reference bank pointing to the object passed as parameter. 
    * 
    * @param value The object required as bank neighbor of the collected results. 
    * 
    * @return Set of Bank objects referring to value via bank
    */
   public TransactionSet filterBank(Object value)
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
      
      TransactionSet answer = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (neighbors.contains(obj.getBank()) || (neighbors.isEmpty() && obj.getBank() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Transaction object passed as parameter to the Bank attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Bank attributes.
    */
   public TransactionSet withBank(Bank value)
   {
      for (Transaction obj : this)
      {
         obj.withBank(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Transaction objects and collect a set of the Account objects reached via accounts. 
    * 
    * @return Set of Account objects reachable via accounts
    */
   public AccountSet getAccounts()
   {
      AccountSet result = new AccountSet();
      
      for (Transaction obj : this)
      {
         result.with(obj.getAccounts());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Transaction objects and collect all contained objects with reference accounts pointing to the object passed as parameter. 
    * 
    * @param value The object required as accounts neighbor of the collected results. 
    * 
    * @return Set of Account objects referring to value via accounts
    */
   public TransactionSet filterAccounts(Object value)
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
      
      TransactionSet answer = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getAccounts()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Transaction object passed as parameter to the Accounts attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Accounts attributes.
    */
   public TransactionSet withAccounts(Account value)
   {
      for (Transaction obj : this)
      {
         obj.withAccounts(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Transaction object passed as parameter from the Accounts attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public TransactionSet withoutAccounts(Account value)
   {
      for (Transaction obj : this)
      {
         obj.withoutAccounts(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Transaction objects and collect a set of the Transaction objects reached via owner. 
    * 
    * @return Set of Transaction objects reachable via owner
    */
   public TransactionSet getOwner()
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         result.with(obj.getOwner());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Transaction objects and collect all contained objects with reference owner pointing to the object passed as parameter. 
    * 
    * @param value The object required as owner neighbor of the collected results. 
    * 
    * @return Set of Transaction objects referring to value via owner
    */
   public TransactionSet filterOwner(Object value)
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
      
      TransactionSet answer = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (neighbors.contains(obj.getOwner()) || (neighbors.isEmpty() && obj.getOwner() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Follow owner reference zero or more times and collect all reachable objects. Detect cycles and deal with them. 
    * 
    * @return Set of Transaction objects reachable via owner transitively (including the start set)
    */
   public TransactionSet getOwnerTransitive()
   {
      TransactionSet todo = new TransactionSet().with(this);
      
      TransactionSet result = new TransactionSet();
      
      while ( ! todo.isEmpty())
      {
         Transaction current = todo.first();
         
         todo.remove(current);
         
         if ( ! result.contains(current))
         {
            result.add(current);
            
            if ( ! result.contains(current.getOwner()))
            {
               todo.with(current.getOwner());
            }
         }
      }
      
      return result;
   }

   /**
    * Loop through current set of ModelType objects and attach the Transaction object passed as parameter to the Owner attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Owner attributes.
    */
   public TransactionSet withOwner(Transaction value)
   {
      for (Transaction obj : this)
      {
         obj.withOwner(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Transaction objects and collect a set of the Transaction objects reached via fee. 
    * 
    * @return Set of Transaction objects reachable via fee
    */
   public TransactionSet getFee()
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         result.with(obj.getFee());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Transaction objects and collect all contained objects with reference fee pointing to the object passed as parameter. 
    * 
    * @param value The object required as fee neighbor of the collected results. 
    * 
    * @return Set of Transaction objects referring to value via fee
    */
   public TransactionSet filterFee(Object value)
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
      
      TransactionSet answer = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (neighbors.contains(obj.getFee()) || (neighbors.isEmpty() && obj.getFee() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Follow fee reference zero or more times and collect all reachable objects. Detect cycles and deal with them. 
    * 
    * @return Set of Transaction objects reachable via fee transitively (including the start set)
    */
   public TransactionSet getFeeTransitive()
   {
      TransactionSet todo = new TransactionSet().with(this);
      
      TransactionSet result = new TransactionSet();
      
      while ( ! todo.isEmpty())
      {
         Transaction current = todo.first();
         
         todo.remove(current);
         
         if ( ! result.contains(current))
         {
            result.add(current);
            
            if ( ! result.contains(current.getFee()))
            {
               todo.with(current.getFee());
            }
         }
      }
      
      return result;
   }

   /**
    * Loop through current set of ModelType objects and attach the Transaction object passed as parameter to the Fee attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Fee attributes.
    */
   public TransactionSet withFee(Transaction value)
   {
      for (Transaction obj : this)
      {
         obj.withFee(value);
      }
      
      return this;
   }

}
