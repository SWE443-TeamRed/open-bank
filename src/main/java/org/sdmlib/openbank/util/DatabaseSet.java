/*
   Copyright (c) 2017 daniel
   
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
import org.sdmlib.openbank.Database;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import org.sdmlib.openbank.Account;
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.util.TransactionSet;

public class DatabaseSet extends SimpleSet<Database>
{
	protected Class<?> getTypClass() {
		return Database.class;
	}

   public DatabaseSet()
   {
      // empty
   }

   public DatabaseSet(Database... objects)
   {
      for (Database obj : objects)
      {
         this.add(obj);
      }
   }

   public DatabaseSet(Collection<Database> objects)
   {
      this.addAll(objects);
   }

   public static final DatabaseSet EMPTY_SET = new DatabaseSet().withFlag(DatabaseSet.READONLY);


   public DatabasePO createDatabasePO()
   {
      return new DatabasePO(this.toArray(new Database[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.openbank.Database";
   }


   @Override
   public DatabaseSet getNewList(boolean keyValue)
   {
      return new DatabaseSet();
   }


   public DatabaseSet filter(Condition<Database> condition) {
      DatabaseSet filterList = new DatabaseSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public DatabaseSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<Database>)value);
      }
      else if (value != null)
      {
         this.add((Database) value);
      }
      
      return this;
   }
   
   public DatabaseSet without(Database value)
   {
      this.remove(value);
      return this;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList addAccounts(Account account)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (Database obj : this)
      {
         result.add( obj.addAccounts(account) );
      }
      return result;
   }

   
   //==========================================================================
   
   public AccountSet searchForAccount(String userId)
   {
      
      AccountSet result = new AccountSet();
      
      for (Database obj : this)
      {
         result.add( obj.searchForAccount(userId) );
      }
      return result;
   }

   
   //==========================================================================
   
   public TransactionSet searchForTransaction(String userId)
   {
      
      TransactionSet result = new TransactionSet();
      
      for (Database obj : this)
      {
         result.add( obj.searchForTransaction(userId) );
      }
      return result;
   }

}
