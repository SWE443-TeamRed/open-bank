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
import org.sdmlib.openbank.Account;
import de.uniks.networkparser.interfaces.Condition;

import java.math.BigInteger;
import java.util.Collection;
import org.sdmlib.openbank.Transaction;
import de.uniks.networkparser.list.NumberList;
import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.list.BooleanList;
import org.sdmlib.openbank.util.UserSet;
import org.sdmlib.openbank.User;
import java.util.Collections;
import java.util.Date;

import org.sdmlib.openbank.util.TransactionSet;
import org.sdmlib.openbank.AccountTypeEnum;
import org.sdmlib.openbank.util.BankSet;
import org.sdmlib.openbank.Bank;
import org.sdmlib.openbank.TransactionTypeEnum;

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

   
   //==========================================================================
   
   public AccountSet Account(double initialAmount)
   {
      return AccountSet.EMPTY_SET;
   }

   
   //==========================================================================


   public de.uniks.networkparser.list.BooleanList transferToUser(BigInteger amount, Account destinationAccount, String note)
   {

      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();

      for (Account obj : this)
      {
         result.add( obj.transferToAccount(amount, destinationAccount, note) );
      }
      return result;
   }

   
//   //==========================================================================
//
//   public de.uniks.networkparser.list.BooleanList myBankTransaction(double amount, Account destinationAccount)
//   {
//
//      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
//
//      for (Account obj : this)
//      {
//         result.add( obj.myBankTransaction(amount, destinationAccount) );
//      }
//      return result;
//   }
//
//
//   //==========================================================================
//
//   public de.uniks.networkparser.list.BooleanList receiveFound(double amount, Account sourceAccount)
//   {
//
//      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
//
//      for (Account obj : this)
//      {
//         result.add( obj.receiveFound(amount, sourceAccount) );
//      }
//      return result;
//   }

   
   //==========================================================================
   
//   public de.uniks.networkparser.list.BooleanList sendTransactionInfo(Transaction transaction, double amount, Date p0, Date p1, String note)
//   {
//
//      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
//
//      for (Account obj : this)
//      {
//         result.add( obj.sendTransactionInfo(transaction, amount, p0, p1, note) );
//      }
//      return result;
//   }

   
   //==========================================================================
   
   public AccountSet withdraw(double amount)
   {
      return AccountSet.EMPTY_SET;
   }

   
   //==========================================================================
   
   public AccountSet deposit(double amount)
   {
      return AccountSet.EMPTY_SET;
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
   public AccountSet filterBalance(BigInteger value)
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
   public AccountSet filterBalance(BigInteger lower, BigInteger upper)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         int resLowerGrtBlance= lower.compareTo(obj.getBalance());
         int resBlncGrtUppr= obj.getBalance().compareTo(upper);

         //if (lower <= obj.getBalance() && obj.getBalance() <= upper)
         if (resLowerGrtBlance==-1 && resBlncGrtUppr==-1)
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
   public AccountSet withBalance(BigInteger value)
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
//   public AccountSet filterCreationdate(Date value)
//   {
//      AccountSet result = new AccountSet();
//
//      for (Account obj : this)
//      {
//         if (value == obj.getCreationdate())
//         {
//            result.add(obj);
//         }
//      }
//
//      return result;
//   }


   /**
    * Loop through the current set of Account objects and assign value to the creationdate attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
//   public AccountSet withCreationdate(Date value)
//   {
//      for (Account obj : this)
//      {
//         obj.setCreationdate(value);
//      }
//
//      return this;
//   }


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

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList receiveFunds(Account giver, BigInteger amount, String note)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.receiveFunds(amount, note) );
      }
      return result;
   }

   
   //==========================================================================


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




   
   //==========================================================================



   /**
    * Loop through the current set of Account objects and collect a list of the type attribute values. 
    * 
    * @return List of org.sdmlib.openbank.AccountTypeEnum objects reachable via type attribute
    */
   public AccountTypeEnumSet getType()
   {
      AccountTypeEnumSet result = new AccountTypeEnumSet();
      
      for (Account obj : this)
      {
         result.add(obj.getType());
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and collect those Account objects where the type attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of Account objects that match the parameter
    */
   public AccountSet filterType(AccountTypeEnum value)
   {
      AccountSet result = new AccountSet();
      
      for (Account obj : this)
      {
         if (value == obj.getType())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of Account objects and assign value to the type attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of Account objects now with new attribute values.
    */
   public AccountSet withType(AccountTypeEnum value)
   {
      for (Account obj : this)
      {
         obj.setType(value);
      }
      
      return this;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList transferToAccount(BigInteger amount, Account destinationAccount, String note)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.transferToAccount(amount, destinationAccount, note) );
      }
      return result;
   }

   
   //==========================================================================
   

   /**
    * Loop through the current set of Account objects and collect a set of the Bank objects reached via bank. 
    * 
    * @return Set of Bank objects reachable via bank
    */
   public BankSet getBank()
   {
      BankSet result = new BankSet();
      
      for (Account obj : this)
      {
         result.with(obj.getBank());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference bank pointing to the object passed as parameter. 
    * 
    * @param value The object required as bank neighbor of the collected results. 
    * 
    * @return Set of Bank objects referring to value via bank
    */
   public AccountSet filterBank(Object value)
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
         if (neighbors.contains(obj.getBank()) || (neighbors.isEmpty() && obj.getBank() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the Bank attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Bank attributes.
    */
   public AccountSet withBank(Bank value)
   {
      for (Account obj : this)
      {
         obj.withBank(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Account objects and collect a set of the Bank objects reached via employingBank. 
    * 
    * @return Set of Bank objects reachable via employingBank
    */
   public BankSet getEmployingBank()
   {
      BankSet result = new BankSet();
      
      for (Account obj : this)
      {
         result.with(obj.getEmployingBank());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference employingBank pointing to the object passed as parameter. 
    * 
    * @param value The object required as employingBank neighbor of the collected results. 
    * 
    * @return Set of Bank objects referring to value via employingBank
    */
   public AccountSet filterEmployingBank(Object value)
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
         if (neighbors.contains(obj.getEmployingBank()) || (neighbors.isEmpty() && obj.getEmployingBank() == null))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the EmployingBank attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their EmployingBank attributes.
    */
   public AccountSet withEmployingBank(Bank value)
   {
      for (Account obj : this)
      {
         obj.withEmployingBank(value);
      }
      
      return this;
   }



   
   //==========================================================================

   /**
    * Loop through the current set of Account objects and collect a set of the Transaction objects reached via ToTransaction. 
    * 
    * @return Set of Transaction objects reachable via ToTransaction
    */
   public TransactionSet getToTransaction()
   {
      TransactionSet result = new TransactionSet();
      
      for (Account obj : this)
      {
         result.with(obj.getToTransaction());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference ToTransaction pointing to the object passed as parameter. 
    * 
    * @param value The object required as ToTransaction neighbor of the collected results. 
    * 
    * @return Set of Transaction objects referring to value via ToTransaction
    */
   public AccountSet filterToTransaction(Object value)
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
         if ( ! Collections.disjoint(neighbors, obj.getToTransaction()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the ToTransaction attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their ToTransaction attributes.
    */
   public AccountSet withToTransaction(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withToTransaction(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Account object passed as parameter from the ToTransaction attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public AccountSet withoutToTransaction(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withoutToTransaction(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of Account objects and collect a set of the Transaction objects reached via FromTransaction. 
    * 
    * @return Set of Transaction objects reachable via FromTransaction
    */
   public TransactionSet getFromTransaction()
   {
      TransactionSet result = new TransactionSet();
      
      for (Account obj : this)
      {
         result.with(obj.getFromTransaction());
      }
      
      return result;
   }

   /**
    * Loop through the current set of Account objects and collect all contained objects with reference FromTransaction pointing to the object passed as parameter. 
    * 
    * @param value The object required as FromTransaction neighbor of the collected results. 
    * 
    * @return Set of Transaction objects referring to value via FromTransaction
    */
   public AccountSet filterFromTransaction(Object value)
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
         if ( ! Collections.disjoint(neighbors, obj.getFromTransaction()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the Account object passed as parameter to the FromTransaction attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their FromTransaction attributes.
    */
   public AccountSet withFromTransaction(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withFromTransaction(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the Account object passed as parameter from the FromTransaction attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public AccountSet withoutFromTransaction(Transaction value)
   {
      for (Account obj : this)
      {
         obj.withoutFromTransaction(value);
      }
      
      return this;
   }

   
   //==========================================================================
   
   public TransactionSet recordTransaction(Account sender, Account reciever, TransactionTypeEnum type, BigInteger amount, String note)
   {
      
      TransactionSet result = new TransactionSet();
      
      for (Account obj : this)
      {
         result.add( obj.recordTransaction(sender, reciever, type, amount, note) );
      }
      return result;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList withdraw(BigInteger amount)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.withdraw(amount) );
      }
      return result;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList deposit(BigInteger amount)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.deposit(amount) );
      }
      return result;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList transferToAccount(double amount, Account destinationAccount, String note)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Account obj : this)
      {
         result.add( obj.transferToAccount(amount, destinationAccount, note) );
      }
      return result;
   }

}
