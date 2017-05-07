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

import de.uniks.networkparser.EntityUtil;
import de.uniks.networkparser.interfaces.SendableEntity;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.security.SecureRandom;
import java.util.Random;

/**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class User implements SendableEntity {


       /*
            User login verification

        */

       /*
       public String login(String userName, String password) {
           if (getName().equals(userName) && getPassword().equals(password)) {
               File jsonfile = new File(getUserID()+".json");
               if(jsonfile.exists()) {
                   JsonPersistency jsonPersist = new JsonPersistency();
                   Account userAccount = jsonPersist.fromJson(getUserID());
                   this.withAccount(userAccount);
               }
               this.setLoggedIn(true);
               return getUserID();
           } else {
               if (getName().equals(userName))
                   System.out.println("Username is incorrect");
               if (getPassword().equals(password))
                   System.out.println("Password is incorrect");
               return null;
           }
       }

       */

       public boolean login(String userId, String password) {
           if (getUserID().equals(userId) && getPassword().equals(password)) {
               File jsonfile = new File(userId+".json");
               if(jsonfile.exists()) {
                   JsonPersistency jsonPersist = new JsonPersistency();
                   Account userAccount = jsonPersist.fromJson(userId);
                   this.withAccount(userAccount);
               }
               this.setLoggedIn(true);
               return true;
           } else {
               if (getUserID().equals(userId))
                   System.out.println("Password is incorrect");
               if (getPassword().equals(password))
                   System.out.println("Username is incorrect");
               return false;
           }
       }

       //==========================================================================

       protected PropertyChangeSupport listeners = null;

       public boolean firePropertyChange(String propertyName, Object oldValue, Object newValue) {
           if (listeners != null) {
               listeners.firePropertyChange(propertyName, oldValue, newValue);
               return true;
           }
           return false;
       }

       public boolean addPropertyChangeListener(PropertyChangeListener listener) {
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

       public boolean removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
           if (listeners != null) {
               listeners.removePropertyChangeListener(propertyName, listener);
           }
           return true;
       }


       //==========================================================================


       public void removeYou() {
           withoutAccount(this.getAccount().toArray(new Account[this.getAccount().size()]));
           setBank(null);
      setEmployingBank(null);
      firePropertyChange("REMOVE_YOU", this, null);
       }


       //==========================================================================

       public static final String PROPERTY_NAME = "name";

       private String name;

       public String getName() {
           return this.name;
       }

       public void setName(String value) {
           if (!EntityUtil.stringEquals(this.name, value)) {

               String oldValue = this.name;
               this.name = value;
               this.firePropertyChange(PROPERTY_NAME, oldValue, value);
           }
       }

       public User withName(String value) {
           setName(value);
           return this;
       }

       public static final String PROPERTY_SESSIONID = "sessionId";

       private String sessionid;

       public void setSessionId()  {
           byte[] salt;
           if(this.isLoggedIn())
           {
               StringBuilder value = new StringBuilder();
               String usrID = this.getUserID() ;
               value.append(usrID);
               String time = Long.toString(System.currentTimeMillis());
               value.append(time);
               final Random sr = new SecureRandom();
               salt = new byte[32];
               sr.nextBytes(salt);
               System.out.println(value.toString());
               System.out.println(salt.toString());
               String oldValue = this.sessionid;
               Bank bank=new Bank();
               this.sessionid = bank.getSecureID(value.toString(), salt);

               this.firePropertyChange(PROPERTY_SESSIONID, oldValue, sessionid);
           }
       }

       public User withSessionId()
       {
           this.setSessionId();
           return this;
       }

       public String getSessionId()
       {
           return (this.sessionid);
       }

       @Override
       public String toString() {
           StringBuilder result = new StringBuilder();

           result.append(" ").append("Name: "+this.getName());
           result.append(" ").append("userID: "+this.getUserID());
           result.append(" ").append("Password: " + this.getPassword());
           result.append(" ").append("Admin: " + this.isIsAdmin());
           result.append(" ").append(this.getEmail());
           result.append(" ").append(this.getPhone());
           result.append(" ").append(this.getUsername());
           return result.substring(1);
       }


       //==========================================================================

       public static final String PROPERTY_USERID = "UserID";

       private String UserID;

       public String getUserID() {
           return this.UserID;
       }

       public void setUserID(String value) {
                    String oldValue = this.UserID;
                   this.UserID = value;
                   this.firePropertyChange(PROPERTY_USERID, oldValue, value);
       }

       public User withUserID(String value) {
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

       public AccountSet getAccount() {
           if (this.account == null) {
               return AccountSet.EMPTY_SET;
           }

           return this.account;
       }

       public User withAccount(Account... value) {
           if (value == null) {
               return this;
           }
           for (Account item : value) {
               if (item != null) {
                   if (this.account == null) {
                       this.account = new AccountSet();
                   }

                   boolean changed = this.account.add(item);

                   if (changed) {
                       item.withOwner(this);
                       firePropertyChange(PROPERTY_ACCOUNT, null, item);
                   }
               }
           }
           return this;
       }

       public User withoutAccount(Account... value) {
           for (Account item : value) {
               if ((this.account != null) && (item != null)) {
                   if (this.account.remove(item)) {
                       item.setOwner(null);
                       firePropertyChange(PROPERTY_ACCOUNT, item, null);
                   }
               }
           }
           return this;
       }




       //==========================================================================
       public boolean openAccount(User p0) {
           return false;
       }


       //==========================================================================

       private String userID;


       //==========================================================================

       public static final String PROPERTY_ISADMIN = "isAdmin";

       private boolean isAdmin;

       public boolean isIsAdmin() {
           return this.isAdmin;
       }

       public void setIsAdmin(boolean value) {
           if (this.isAdmin != value) {

               boolean oldValue = this.isAdmin;
               this.isAdmin = value;
               this.firePropertyChange(PROPERTY_ISADMIN, oldValue, value);
           }
       }

       public User withIsAdmin(boolean value) {
           setIsAdmin(value);
           return this;
       }


       //==========================================================================

       public static final String PROPERTY_PASSWORD = "password";

       private String password;

       public String getPassword() {
           return this.password;
       }

       public void setPassword(String value) {
           if (!EntityUtil.stringEquals(this.password, value)) {

               String oldValue = this.password;
               this.password = value;
               this.firePropertyChange(PROPERTY_PASSWORD, oldValue, value);
           }
       }

       public User withPassword(String value) {
           setPassword(value);
           return this;
       }


       //==========================================================================

       public static final String PROPERTY_EMAIL = "email";

       private String email;

       public String getEmail() {
           return this.email;
       }

       public void setEmail(String value) {
           if (!EntityUtil.stringEquals(this.email, value)) {

               String oldValue = this.email;
               this.email = value;
               this.firePropertyChange(PROPERTY_EMAIL, oldValue, value);
           }
       }

       public User withEmail(String value) {
           setEmail(value);
           return this;
       }


       //==========================================================================

       public static final String PROPERTY_LOGGEDIN = "LoggedIn";

       private boolean LoggedIn;

       public boolean isLoggedIn() {
           return this.LoggedIn;
       }

       public void setLoggedIn(boolean value) {
           if (this.LoggedIn != value) {

               boolean oldValue = this.LoggedIn;
               this.LoggedIn = value;
               setSessionId();
               this.firePropertyChange(PROPERTY_LOGGEDIN, oldValue, value);
           }
       }

       public User withLoggedIn(boolean value) {
           setLoggedIn(value);
           return this;
       }


       //==========================================================================
/*
       public static final String PROPERTY_PHONE = "phone";

       private String phone;

       public int getPhone() {
           return this.phone;
       }

       public void setPhone(int value) {
           if (this.phone != value) {

               int oldValue = this.phone;
               this.phone = value;
               this.firePropertyChange(PROPERTY_PHONE, oldValue, value);
           }
       }

       public User withPhone(int value) {
           setPhone(value);
           return this;
       }

*/

   
   
   //==========================================================================
   public boolean logout(  )
   {

       Account usersAccount = this.getAccount().get(0);
       JsonPersistency writeToJson = new JsonPersistency();
//       writeToJson.toJson(usersAccount);
       this.LoggedIn = false;
       this.sessionid=null;
       //this.getSessionId();
       return true;
   }

   public Account createAccount()
   {
      Account value = new Account();
      withAccount(value);
      return value;
   } 


   
   //==========================================================================
   public static final String PROPERTY_PHONE = "phone";

   private String phone;

   public void setPhone(String value)
   {
      if ( ! EntityUtil.stringEquals(this.phone, value)) {
      
         String oldValue = this.phone;
         this.phone = value;
         this.firePropertyChange(PROPERTY_PHONE, oldValue, value);
      }
   }

       public String getPhone() {
           return this.phone;
       }

   public User withPhone(String value)
   {
      setPhone(value);
      return this;
   } 

   
   //==========================================================================
   
   public static final String PROPERTY_USERNAME = "username";
   
   private String username;

   public String getUsername()
   {
      return this.username;
   }
   
   public void setUsername(String value)
   {
       /*
      if ( ! EntityUtil.stringEquals(this.username, value)) {
          if(this.getBank().getCustomerUser().filterUsername(value).size() == 0 &&
                  this.getBank().getAdminUsers().filterUsername(value).size() == 0) {
                  */
              String oldValue = this.username;
              this.username = value;
              this.firePropertyChange(PROPERTY_USERNAME, oldValue, value);
          //}
          //else
         //     throw new IllegalArgumentException("Username "+value+" has already been used");
      //}
   }
   
   public User withUsername(String value)
   {
      setUsername(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              many                       one
    * User ----------------------------------- Bank
    *              customerUser                   bank
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
            oldValue.withoutCustomerUser(this);
         }
         
         this.bank = value;
         
         if (value != null)
         {
            value.withCustomerUser(this);
         }
         
         firePropertyChange(PROPERTY_BANK, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public User withBank(Bank value)
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
    *              many                       one
    * User ----------------------------------- Bank
    *              adminUsers                   employingBank
    * </pre>
    */
   
   public static final String PROPERTY_EMPLOYINGBANK = "employingBank";

   private Bank employingBank = null;

   public Bank getEmployingBank()
   {
      return this.employingBank;
   }

   public boolean setEmployingBank(Bank value)
   {
      boolean changed = false;
      
      if (this.employingBank != value)
      {
         Bank oldValue = this.employingBank;
         
         if (this.employingBank != null)
         {
            this.employingBank = null;
            oldValue.withoutAdminUsers(this);
         }
         
         this.employingBank = value;
         
         if (value != null)
         {
            value.withAdminUsers(this);
         }
         
         firePropertyChange(PROPERTY_EMPLOYINGBANK, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public User withEmployingBank(Bank value)
   {
      setEmployingBank(value);
      return this;
   } 

   public Bank createEmployingBank()
   {
      Bank value = new Bank();
      withEmployingBank(value);
      return value;
   } 
}
