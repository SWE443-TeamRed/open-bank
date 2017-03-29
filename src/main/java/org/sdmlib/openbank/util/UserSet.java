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
import org.sdmlib.openbank.User;
import de.uniks.networkparser.interfaces.Condition;
import java.util.Collection;
import de.uniks.networkparser.list.ObjectSet;
import de.uniks.networkparser.list.BooleanList;
import java.util.Collections;
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.Account;

public class UserSet extends SimpleSet<User>
{
	protected Class<?> getTypClass() {
		return User.class;
	}

   public UserSet()
   {
      // empty
   }

   public UserSet(User... objects)
   {
      for (User obj : objects)
      {
         this.add(obj);
      }
   }

   public UserSet(Collection<User> objects)
   {
      this.addAll(objects);
   }

   public static final UserSet EMPTY_SET = new UserSet().withFlag(UserSet.READONLY);


   public UserPO createUserPO()
   {
      return new UserPO(this.toArray(new User[this.size()]));
   }


   public String getEntryType()
   {
      return "org.sdmlib.openbank.User";
   }


   @Override
   public UserSet getNewList(boolean keyValue)
   {
      return new UserSet();
   }


   public UserSet filter(Condition<User> condition) {
      UserSet filterList = new UserSet();
      filterItems(filterList, condition);
      return filterList;
   }

   @SuppressWarnings("unchecked")
   public UserSet with(Object value)
   {
      if (value == null)
      {
         return this;
      }
      else if (value instanceof java.util.Collection)
      {
         this.addAll((Collection<User>)value);
      }
      else if (value != null)
      {
         this.add((User) value);
      }
      
      return this;
   }
   
   public UserSet without(User value)
   {
      this.remove(value);
      return this;
   }

   
   //==========================================================================
   
   public de.uniks.networkparser.list.BooleanList openAccount(User p0)
   {
      
      de.uniks.networkparser.list.BooleanList result = new de.uniks.networkparser.list.BooleanList();
      
      for (User obj : this)
      {
         result.add( obj.openAccount(p0) );
      }
      return result;
   }


   /**
    * Loop through the current set of User objects and collect a list of the name attribute values. 
    * 
    * @return List of String objects reachable via name attribute
    */
   public ObjectSet getName()
   {
      ObjectSet result = new ObjectSet();
      
      for (User obj : this)
      {
         result.add(obj.getName());
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and collect those User objects where the name attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of User objects that match the parameter
    */
   public UserSet filterName(String value)
   {
      UserSet result = new UserSet();
      
      for (User obj : this)
      {
         if (value.equals(obj.getName()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and collect those User objects where the name attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of User objects that match the parameter
    */
   public UserSet filterName(String lower, String upper)
   {
      UserSet result = new UserSet();
      
      for (User obj : this)
      {
         if (lower.compareTo(obj.getName()) <= 0 && obj.getName().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and assign value to the name attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of User objects now with new attribute values.
    */
   public UserSet withName(String value)
   {
      for (User obj : this)
      {
         obj.setName(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of User objects and collect a list of the userID attribute values. 
    * 
    * @return List of String objects reachable via userID attribute
    */
   public ObjectSet getUserID()
   {
      ObjectSet result = new ObjectSet();
      
      for (User obj : this)
      {
         result.add(obj.getUserID());
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and collect those User objects where the userID attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of User objects that match the parameter
    */
   public UserSet filterUserID(String value)
   {
      UserSet result = new UserSet();
      
      for (User obj : this)
      {
         if (value.equals(obj.getUserID()))
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and collect those User objects where the userID attribute is between lower and upper. 
    * 
    * @param lower Lower bound 
    * @param upper Upper bound 
    * 
    * @return Subset of User objects that match the parameter
    */
   public UserSet filterUserID(String lower, String upper)
   {
      UserSet result = new UserSet();
      
      for (User obj : this)
      {
         if (lower.compareTo(obj.getUserID()) <= 0 && obj.getUserID().compareTo(upper) <= 0)
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and assign value to the userID attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of User objects now with new attribute values.
    */
   public UserSet withUserID(String value)
   {
      for (User obj : this)
      {
         obj.setUserID(value);
      }
      
      return this;
   }


   /**
    * Loop through the current set of User objects and collect a list of the isAdmin attribute values. 
    * 
    * @return List of boolean objects reachable via isAdmin attribute
    */
   public BooleanList getIsAdmin()
   {
      BooleanList result = new BooleanList();
      
      for (User obj : this)
      {
         result.add(obj.isIsAdmin());
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and collect those User objects where the isAdmin attribute matches the parameter value. 
    * 
    * @param value Search value
    * 
    * @return Subset of User objects that match the parameter
    */
   public UserSet filterIsAdmin(boolean value)
   {
      UserSet result = new UserSet();
      
      for (User obj : this)
      {
         if (value == obj.isIsAdmin())
         {
            result.add(obj);
         }
      }
      
      return result;
   }


   /**
    * Loop through the current set of User objects and assign value to the isAdmin attribute of each of it. 
    * 
    * @param value New attribute value
    * 
    * @return Current set of User objects now with new attribute values.
    */
   public UserSet withIsAdmin(boolean value)
   {
      for (User obj : this)
      {
         obj.setIsAdmin(value);
      }
      
      return this;
   }

   /**
    * Loop through the current set of User objects and collect a set of the Account objects reached via account. 
    * 
    * @return Set of Account objects reachable via account
    */
   public AccountSet getAccount()
   {
      AccountSet result = new AccountSet();
      
      for (User obj : this)
      {
         result.with(obj.getAccount());
      }
      
      return result;
   }

   /**
    * Loop through the current set of User objects and collect all contained objects with reference account pointing to the object passed as parameter. 
    * 
    * @param value The object required as account neighbor of the collected results. 
    * 
    * @return Set of Account objects referring to value via account
    */
   public UserSet filterAccount(Object value)
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
      
      UserSet answer = new UserSet();
      
      for (User obj : this)
      {
         if ( ! Collections.disjoint(neighbors, obj.getAccount()))
         {
            answer.add(obj);
         }
      }
      
      return answer;
   }

   /**
    * Loop through current set of ModelType objects and attach the User object passed as parameter to the Account attribute of each of it. 
    * 
    * @return The original set of ModelType objects now with the new neighbor attached to their Account attributes.
    */
   public UserSet withAccount(Account value)
   {
      for (User obj : this)
      {
         obj.withAccount(value);
      }
      
      return this;
   }

   /**
    * Loop through current set of ModelType objects and remove the User object passed as parameter from the Account attribute of each of it. 
    * 
    * @return The original set of ModelType objects now without the old neighbor.
    */
   public UserSet withoutAccount(Account value)
   {
      for (User obj : this)
      {
         obj.withoutAccount(value);
      }
      
      return this;
   }

}
