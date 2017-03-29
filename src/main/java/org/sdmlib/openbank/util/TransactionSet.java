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
import de.uniks.networkparser.list.NumberList;
import de.uniks.networkparser.list.ObjectSet;
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.Account;

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
    * @return List of String objects reachable via date attribute
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
   public TransactionSet filterDate(String value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value.equals(obj.getDate()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the date attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterDate(String lower, String upper)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (lower.compareTo(obj.getDate()) <= 0 && obj.getDate().compareTo(upper) <= 0)
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
   public TransactionSet withDate(String value)
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
    * @return List of String objects reachable via time attribute
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
   public TransactionSet filterTime(String value)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (value.equals(obj.getTime()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Transaction objects and collect those Transaction objects where the time attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Transaction objects that match the parameter
    */
   public TransactionSet filterTime(String lower, String upper)
   {
      TransactionSet result = new TransactionSet();
      
      for (Transaction obj : this)
      {
         if (lower.compareTo(obj.getTime()) <= 0 && obj.getTime().compareTo(upper) <= 0)
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
   public TransactionSet withTime(String value)
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
    * Loop through the current set of Transaction objects and collect a set of the Account objects reached via fromAccount. 
    * 
    * @return Set of Account objects reachable via fromAccount
    */
   public AccountSet getFromAccount()
   {
      AccountSet result = new AccountSet();
      
      for (Transaction obj : this)
      {
         result.with(obj.getFromAccount());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Transaction objects and collect all contained objects with reference fromAccount pointing to the object passed as parameter. 
    * 
    * @param value The object required as fromAccount neighbor of the collected results. 
    * 
    * @return Set of Account objects referring to value via fromAccount
    */
   public TransactionSet filterFromAccount(Object value)
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
         if (neighbors.contains(obj.getFromAccount()) || (neighbors.isEmpty() && obj.getFromAccount() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Transaction object passed as parameter to the FromAccount attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their FromAccount attributes.
    */
   public TransactionSet withFromAccount(Account value)
   {
      for (Transaction obj : this)
      {
         obj.withFromAccount(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Transaction objects and collect a set of the Account objects reached via toAccount. 
    * 
    * @return Set of Account objects reachable via toAccount
    */
   public AccountSet getToAccount()
   {
      AccountSet result = new AccountSet();
      
      for (Transaction obj : this)
      {
         result.with(obj.getToAccount());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Transaction objects and collect all contained objects with reference toAccount pointing to the object passed as parameter. 
    * 
    * @param value The object required as toAccount neighbor of the collected results. 
    * 
    * @return Set of Account objects referring to value via toAccount
    */
   public TransactionSet filterToAccount(Object value)
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
         if (neighbors.contains(obj.getToAccount()) || (neighbors.isEmpty() && obj.getToAccount() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Transaction object passed as parameter to the ToAccount attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their ToAccount attributes.
    */
   public TransactionSet withToAccount(Account value)
   {
      for (Transaction obj : this)
      {
         obj.withToAccount(value);
      }
      
      return this;
   }

}
