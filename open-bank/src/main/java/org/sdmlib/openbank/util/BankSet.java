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
import org.sdmlib.openbank.Bank;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import de.uniks.networkparser.list.NumberList;
import de.uniks.networkparser.list.ObjectSet;
import java.util.Collections;
import org.sdmlib.openbank.util.UserSet;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.Account;

public class BankSet extends SimpleSet<Bank>
{
	protected Class<?> getTypClass() {
		return Bank.class;
	}

   public BankSet()
   {
      // empty
   }

   public BankSet(Bank... objects)
   {
      for (Bank obj : objects)
      {
         this.add(obj);
      }
   }

   public BankSet(Collection<Bank> objects)
   {
      this.addAll(objects);
   }

   public static final BankSet EMPTY_SET = new BankSet().withFlag(BankSet.READONLY);


   public BankPO createBankPO()
   {
      return new BankPO(this.toArray(new Bank[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.openbank.Bank";
   }


   @Override
   public BankSet getNewList(boolean keyValue)
   {
      return new BankSet();
   }


   public BankSet filter(Condition<Bank> condition) {
      BankSet filterList = new BankSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public BankSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Bank>)value);
      }
      else if (value != null)
      {
         this.add((Bank) value);
      }
      
      return this;
   }
   
   public BankSet without(Bank value)
   {
      this.remove(value);
      return this;
   }


   /**
    * Loop through the current set of Bank objects and collect a list of the fee attribute values. 
    * 
    * @return List of double objects reachable via fee attribute
    */
   public NumberList getFee()
   {
      NumberList result = new NumberList();
      
      for (Bank obj : this)
      {
         result.add(obj.getFee());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Bank objects and collect those Bank objects where the fee attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Bank objects that match the parameter
    */
   public BankSet filterFee(double value)
   {
      BankSet result = new BankSet();
      
      for (Bank obj : this)
      {
         if (value == obj.getFee())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Bank objects and collect those Bank objects where the fee attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Bank objects that match the parameter
    */
   public BankSet filterFee(double lower, double upper)
   {
      BankSet result = new BankSet();
      
      for (Bank obj : this)
      {
         if (lower <= obj.getFee() && obj.getFee() <= upper)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Bank objects and assign value to the fee attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Bank objects now with new attribute values.
    */
   public BankSet withFee(double value)
   {
      for (Bank obj : this)
      {
         obj.setFee(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of Bank objects and collect a list of the bankName attribute values. 
    * 
    * @return List of String objects reachable via bankName attribute
    */
   public ObjectSet getBankName()
   {
      ObjectSet result = new ObjectSet();
      
      for (Bank obj : this)
      {
         result.add(obj.getBankName());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Bank objects and collect those Bank objects where the bankName attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Bank objects that match the parameter
    */
   public BankSet filterBankName(String value)
   {
      BankSet result = new BankSet();
      
      for (Bank obj : this)
      {
         if (value.equals(obj.getBankName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Bank objects and collect those Bank objects where the bankName attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of Bank objects that match the parameter
    */
   public BankSet filterBankName(String lower, String upper)
   {
      BankSet result = new BankSet();
      
      for (Bank obj : this)
      {
         if (lower.compareTo(obj.getBankName()) <= 0 && obj.getBankName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Bank objects and assign value to the bankName attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Bank objects now with new attribute values.
    */
   public BankSet withBankName(String value)
   {
      for (Bank obj : this)
      {
         obj.setBankName(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Bank objects and collect a set of the User objects reached via customerUser. 
    * 
    * @return Set of User objects reachable via customerUser
    */
   public UserSet getCustomerUser()
   {
      UserSet result = new UserSet();
      
      for (Bank obj : this)
      {
         result.with(obj.getCustomerUser());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Bank objects and collect all contained objects with reference customerUser pointing to the object passed as parameter. 
    * 
    * @param value The object required as customerUser neighbor of the collected results. 
    * 
    * @return Set of User objects referring to value via customerUser
    */
   public BankSet filterCustomerUser(Object value)
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
      
      BankSet answer = new BankSet();
      
      for (Bank obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getCustomerUser()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Bank object passed as parameter to the CustomerUser attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their CustomerUser attributes.
    */
   public BankSet withCustomerUser(User value)
   {
      for (Bank obj : this)
      {
         obj.withCustomerUser(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Bank object passed as parameter from the CustomerUser attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public BankSet withoutCustomerUser(User value)
   {
      for (Bank obj : this)
      {
         obj.withoutCustomerUser(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Bank objects and collect a set of the Transaction objects reached via transaction. 
    * 
    * @return Set of Transaction objects reachable via transaction
    */
   public TransactionSet getTransaction()
   {
      TransactionSet result = new TransactionSet();
      
      for (Bank obj : this)
      {
         result.with(obj.getTransaction());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Bank objects and collect all contained objects with reference transaction pointing to the object passed as parameter. 
    * 
    * @param value The object required as transaction neighbor of the collected results. 
    * 
    * @return Set of Transaction objects referring to value via transaction
    */
   public BankSet filterTransaction(Object value)
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
      
      BankSet answer = new BankSet();
      
      for (Bank obj : this)
      {
         if (neighbors.contains(obj.getTransaction()) || (neighbors.isEmpty() && obj.getTransaction() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Bank object passed as parameter to the Transaction attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Transaction attributes.
    */
   public BankSet withTransaction(Transaction value)
   {
      for (Bank obj : this)
      {
         obj.withTransaction(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Bank objects and collect a set of the Account objects reached via customerAccounts. 
    * 
    * @return Set of Account objects reachable via customerAccounts
    */
   public AccountSet getCustomerAccounts()
   {
      AccountSet result = new AccountSet();
      
      for (Bank obj : this)
      {
         result.with(obj.getCustomerAccounts());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Bank objects and collect all contained objects with reference customerAccounts pointing to the object passed as parameter. 
    * 
    * @param value The object required as customerAccounts neighbor of the collected results. 
    * 
    * @return Set of Account objects referring to value via customerAccounts
    */
   public BankSet filterCustomerAccounts(Object value)
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
      
      BankSet answer = new BankSet();
      
      for (Bank obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getCustomerAccounts()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Bank object passed as parameter to the CustomerAccounts attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their CustomerAccounts attributes.
    */
   public BankSet withCustomerAccounts(Account value)
   {
      for (Bank obj : this)
      {
         obj.withCustomerAccounts(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Bank object passed as parameter from the CustomerAccounts attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public BankSet withoutCustomerAccounts(Account value)
   {
      for (Bank obj : this)
      {
         obj.withoutCustomerAccounts(value);
      }
      
      return this;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList validateLogin(int accountID, String username, String password)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Bank obj : this)
      {
         result.add( obj.validateLogin(accountID, username, password) );
      }
      return result;
   }

   
   //==========================================================================
   
   public AccountSet findAccountByID(int accountID)
   {
      
      AccountSet result = new AccountSet();
      
      for (Bank obj : this)
      {
         result.add( obj.findAccountByID(accountID) );
      }
      return result;
   }

   
   //==========================================================================
   
   public UserSet findUserByID(String userID)
   {
      
      UserSet result = new UserSet();
      
      for (Bank obj : this)
      {
         result.add( obj.findUserByID(userID) );
      }
      return result;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList confirmTransaction(int toAcctID, int fromAcctID, Integer dollarValue, Integer decimalValue)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Bank obj : this)
      {
         result.add( obj.confirmTransaction(toAcctID, fromAcctID, dollarValue, decimalValue) );
      }
      return result;
   }

   /**
    * Loop through the current set of Bank objects and collect a set of the User objects reached via adminUsers. 
    * 
    * @return Set of User objects reachable via adminUsers
    */
   public UserSet getAdminUsers()
   {
      UserSet result = new UserSet();
      
      for (Bank obj : this)
      {
         result.with(obj.getAdminUsers());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Bank objects and collect all contained objects with reference adminUsers pointing to the object passed as parameter. 
    * 
    * @param value The object required as adminUsers neighbor of the collected results. 
    * 
    * @return Set of User objects referring to value via adminUsers
    */
   public BankSet filterAdminUsers(Object value)
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
      
      BankSet answer = new BankSet();
      
      for (Bank obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getAdminUsers()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Bank object passed as parameter to the AdminUsers attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their AdminUsers attributes.
    */
   public BankSet withAdminUsers(User value)
   {
      for (Bank obj : this)
      {
         obj.withAdminUsers(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Bank object passed as parameter from the AdminUsers attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public BankSet withoutAdminUsers(User value)
   {
      for (Bank obj : this)
      {
         obj.withoutAdminUsers(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Bank objects and collect a set of the Account objects reached via adminAccounts. 
    * 
    * @return Set of Account objects reachable via adminAccounts
    */
   public AccountSet getAdminAccounts()
   {
      AccountSet result = new AccountSet();
      
      for (Bank obj : this)
      {
         result.with(obj.getAdminAccounts());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Bank objects and collect all contained objects with reference adminAccounts pointing to the object passed as parameter. 
    * 
    * @param value The object required as adminAccounts neighbor of the collected results. 
    * 
    * @return Set of Account objects referring to value via adminAccounts
    */
   public BankSet filterAdminAccounts(Object value)
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
      
      BankSet answer = new BankSet();
      
      for (Bank obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getAdminAccounts()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Bank object passed as parameter to the AdminAccounts attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their AdminAccounts attributes.
    */
   public BankSet withAdminAccounts(Account value)
   {
      for (Bank obj : this)
      {
         obj.withAdminAccounts(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Bank object passed as parameter from the AdminAccounts attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public BankSet withoutAdminAccounts(Account value)
   {
      for (Bank obj : this)
      {
         obj.withoutAdminAccounts(value);
      }
      
      return this;
   }

}
