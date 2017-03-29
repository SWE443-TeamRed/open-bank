/*
   Copyright (c) 2017 CShultz
   
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
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.Account;
   /**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class User implements SendableEntity
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
      withoutAccount(this.getAccount().toArray(new Account[this.getAccount().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
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
   
   public User withName(String value)
   {
      setName(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getName());
      result.append(" ").append(this.getUserID());
      return result.substring(1);
   }


   
   //==========================================================================
   
   public static final String PROPERTY_USERID = "UserID";
   
   private String UserID;

   public String getUserID()
   {
      return this.UserID;
   }
   
   public void setUserID(String value)
   {
      if ( ! EntityUtil.stringEquals(this.UserID, value)) {
      
         String oldValue = this.UserID;
         this.UserID = value;
         this.firePropertyChange(PROPERTY_USERID, oldValue, value);
      }
   }
   
   public User withUserID(String value)
   {
      setUserID(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * User ----------------------------------- Account
    *              owner                   account
    * </pre>
    */
   
   public static final String PROPERTY_ACCOUNT = "account";

   private AccountSet account = null;
   
   public AccountSet getAccount()
   {
      if (this.account == null)
      {
         return AccountSet.EMPTY_SET;
      }
   
      return this.account;
   }

   public User withAccount(Account... value)
   {
      if(value==null){
         return this;
      }
      for (Account item : value)
      {
         if (item != null)
         {
            if (this.account == null)
            {
               this.account = new AccountSet();
            }
            
            boolean changed = this.account.add (item);

            if (changed)
            {
               item.withOwner(this);
               firePropertyChange(PROPERTY_ACCOUNT, null, item);
            }
         }
      }
      return this;
   } 

   public User withoutAccount(Account... value)
   {
      for (Account item : value)
      {
         if ((this.account != null) && (item != null))
         {
            if (this.account.remove(item))
            {
               item.setOwner(null);
               firePropertyChange(PROPERTY_ACCOUNT, item, null);
            }
         }
      }
      return this;
   }

   public Account createAccount()
   {
      Account value = new Account();
      withAccount(value);
      return value;
   } 

   
   //==========================================================================
   public boolean login(  )
   {
      return false;
   }

   
   //==========================================================================
   
   private String userID;

   
   //==========================================================================
   
   public static final String PROPERTY_ISADMIN = "isAdmin";
   
   private boolean isAdmin;

   public boolean isIsAdmin()
   {
      return this.isAdmin;
   }
   
   public void setIsAdmin(boolean value)
   {
      if (this.isAdmin != value) {
      
         boolean oldValue = this.isAdmin;
         this.isAdmin = value;
         this.firePropertyChange(PROPERTY_ISADMIN, oldValue, value);
      }
   }
   
   public User withIsAdmin(boolean value)
   {
      setIsAdmin(value);
      return this;
   } 
}
