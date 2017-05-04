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

import de.uniks.networkparser.EntityUtil;
import de.uniks.networkparser.interfaces.SendableEntity;
import org.sdmlib.openbank.util.AccountSet;
import org.sdmlib.openbank.util.FeeValueSet;
import org.sdmlib.openbank.util.UserSet;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.math.BigInteger;
//import java.time.LocalDate;
import java.util.Date;
import java.util.Random;
import org.sdmlib.openbank.User;
import org.sdmlib.openbank.Transaction;
import org.sdmlib.openbank.FeeValue;
import org.sdmlib.openbank.Account;
   /**
    * 
    * @see <a href='../../../../../../src/main/java/Model.java'>Model.java</a>
 */
   public  class Bank implements SendableEntity
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
      withoutCustomerUser(this.getCustomerUser().toArray(new User[this.getCustomerUser().size()]));
      setTransaction(null);
      withoutCustomerAccounts(this.getCustomerAccounts().toArray(new Account[this.getCustomerAccounts().size()]));
      withoutAdminUsers(this.getAdminUsers().toArray(new User[this.getAdminUsers().size()]));
      withoutAdminAccounts(this.getAdminAccounts().toArray(new Account[this.getAdminAccounts().size()]));
      withoutFeeValue(this.getFeeValue().toArray(new FeeValue[this.getFeeValue().size()]));
      firePropertyChange("REMOVE_YOU", this, null);
   }

   
   //==========================================================================
   
   public static final String PROPERTY_FEE = "fee";
   
   private double fee;

   public double getFee()
   {
      return this.fee;
   }
   
   public void setFee(double value)
   {
      if (this.fee != value) {
      
         double oldValue = this.fee;
         this.fee = value;
         this.firePropertyChange(PROPERTY_FEE, oldValue, value);
      }
   }
   
   public Bank withFee(double value)
   {
      setFee(value);
      return this;
   } 


   @Override
   public String toString()
   {
      StringBuilder result = new StringBuilder();
      
      result.append(" ").append(this.getFee());
      result.append(" ").append(this.getBankName());
      return result.substring(1);
   }


   
   //==========================================================================
   
   public static final String PROPERTY_BANKNAME = "bankName";
   
   private String bankName;

   public String getBankName()
   {
      return this.bankName;
   }
   
   public void setBankName(String value)
   {
      if ( ! EntityUtil.stringEquals(this.bankName, value)) {
      
         String oldValue = this.bankName;
         this.bankName = value;
         this.firePropertyChange(PROPERTY_BANKNAME, oldValue, value);
      }
   }
   
   public Bank withBankName(String value)
   {
      setBankName(value);
      return this;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- User
    *              bank                   customerUser
    * </pre>
    */
   
   public static final String PROPERTY_CUSTOMERUSER = "customerUser";

   private UserSet customerUser = null;
   
   public UserSet getCustomerUser()
   {
      if (this.customerUser == null)
      {
         return UserSet.EMPTY_SET;
      }
   
      return this.customerUser;
   }

   public Bank withCustomerUser(User... value)
   {
      if(value==null){
         return this;
      }
      for (User item : value)
      {
         if (item != null)
         {
            if (this.customerUser == null)
            {
               this.customerUser = new UserSet();
            }
            
            boolean changed = this.customerUser.add (item);

            if (changed)
            {
               item.withBank(this);
               firePropertyChange(PROPERTY_CUSTOMERUSER, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutCustomerUser(User... value)
   {
      for (User item : value)
      {
         if ((this.customerUser != null) && (item != null))
         {
            if (this.customerUser.remove(item))
            {
               item.setBank(null);
               firePropertyChange(PROPERTY_CUSTOMERUSER, item, null);
            }
         }
      }
      return this;
   }

   public User createCustomerUser()
   {
      User value = new User();
      withCustomerUser(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       one
    * Bank ----------------------------------- Transaction
    *              bank                   transaction
    * </pre>
    */
   
   public static final String PROPERTY_TRANSACTION = "transaction";

   private Transaction transaction = null;

   public Transaction getTransaction()
   {
      return this.transaction;
   }

   public boolean setTransaction(Transaction value)
   {
      boolean changed = false;
      
      if (this.transaction != value)
      {
         Transaction oldValue = this.transaction;
         
         if (this.transaction != null)
         {
            this.transaction = null;
            oldValue.setBank(null);
         }
         
         this.transaction = value;
         
         if (value != null)
         {
            value.withBank(this);
         }
         
         firePropertyChange(PROPERTY_TRANSACTION, oldValue, value);
         changed = true;
      }
      
      return changed;
   }

   public Bank withTransaction(Transaction value)
   {
      setTransaction(value);
      return this;
   } 

   public Transaction createTransaction()
   {
      Transaction value = new Transaction();
      withTransaction(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- Account
    *              bank                   customerAccounts
    * </pre>
    */
   
   public static final String PROPERTY_CUSTOMERACCOUNTS = "customerAccounts";

   private AccountSet customerAccounts = null;
   
   public AccountSet getCustomerAccounts()
   {
      if (this.customerAccounts == null)
      {
         return AccountSet.EMPTY_SET;
      }
   
      return this.customerAccounts;
   }

   public Bank withCustomerAccounts(Account... value)
   {
      if(value==null){
         return this;
      }
      for (Account item : value)
      {
         if (item != null)
         {
            if (this.customerAccounts == null)
            {
               this.customerAccounts = new AccountSet();
            }
            
            boolean changed = this.customerAccounts.add (item);

            if (changed)
            {
               item.withBank(this);
               firePropertyChange(PROPERTY_CUSTOMERACCOUNTS, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutCustomerAccounts(Account... value)
   {
      for (Account item : value)
      {
         if ((this.customerAccounts != null) && (item != null))
         {
            if (this.customerAccounts.remove(item))
            {
               item.setBank(null);
               firePropertyChange(PROPERTY_CUSTOMERACCOUNTS, item, null);
            }
         }
      }
      return this;
   }

   public Account createCustomerAccounts()
   {
      Account value = new Account();
      withCustomerAccounts(value);
      return value;
   } 

   
   //==========================================================================
   public boolean validateLogin( int accountID, String username, String password ) {
       if (username == null || password == null || accountID < 0)
           throw new IllegalArgumentException("Invalid parameter(s)");
       Account pulledAccount = this.findAccountByID(accountID);
       User pulledUser = this.findUserByID(username);
       if (pulledAccount != null && pulledUser != null){
           if (pulledUser.getPassword().equals(password)){
               return true;
           }
       }
       return false;
   }
   //==========================================================================
   public Account findAccountByID( int accountID )
   {
      if (accountID<=0) {
         throw new IllegalArgumentException("Invalid accountID.");
      }

      AccountSet accountSets = this.getCustomerAccounts();

      for (Account acnt : accountSets) {
         if(acnt.getAccountnum()==accountID){
            return acnt;
         }
      }

      AccountSet adminAccnts = this.getAdminAccounts();
      for (Account AdminAccnt : adminAccnts) {
         if (AdminAccnt.getAccountnum()==accountID) {
            return AdminAccnt;
         }
      }

      return null;
   }


   //==========================================================================
   public User findUserByID(String userID) {
       UserSet pulledUsers = this.getCustomerUser();
       for (User i : pulledUsers) {
           if (i.getUserID() != null && i.getUserID().equals(userID)) {
               return i;
           }
       }
      pulledUsers = this.getAdminUsers();
      for (User i : pulledUsers) {
         if (i.getUserID() != null && i.getUserID().equals(userID)) {
            return i;
         }
      }
       return null;
   }

   
   //==========================================================================
   public boolean confirmTransaction(int toAcctID, int fromAcctID, BigInteger dollarValue, BigInteger decimalValue )
   {
       Account toAcct = findAccountByID(toAcctID);
       Account fromAcct = findAccountByID((fromAcctID));
       if(toAcct == null){
           return false;
       }
       if(fromAcct == null){
           return false;
       }

       //if(fromAcct.getBalance() < dollarValue.add(decimalValue) ){
       int res = fromAcct.getBalance().compareTo(dollarValue.add(decimalValue));

       if(res==-1){
           return false;
       }
       Transaction transferTransation = new Transaction().withBank(this)
               .withAmount(dollarValue.add(decimalValue))
               .withToAccount(toAcct)
               .withFromAccount(fromAcct)
               .withCreationdate(new Date())
               .withTransType(TransactionTypeEnum.TRANSFER);
       this.withTransaction(transferTransation); //one to one relation, so should update to the most current transaction
       return true;
   }

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- User
    *              bank                   adminUsers
    * </pre>
    */
   
   public static final String PROPERTY_ADMINUSERS = "adminUsers";

   private UserSet adminUsers = null;
   
   public UserSet getAdminUsers()
   {
      if (this.adminUsers == null)
      {
         return UserSet.EMPTY_SET;
      }
   
      return this.adminUsers;
   }

   public Bank withAdminUsers(User... value)
   {
      if(value==null){
         return this;
      }
      for (User item : value)
      {
         if (item != null)
         {
            if (this.adminUsers == null)
            {
               this.adminUsers = new UserSet();
            }
            
            boolean changed = this.adminUsers.add (item);

            if (changed)
            {
               item.withBank(this);
               firePropertyChange(PROPERTY_ADMINUSERS, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutAdminUsers(User... value)
   {
      for (User item : value)
      {
         if ((this.adminUsers != null) && (item != null))
         {
            if (this.adminUsers.remove(item))
            {
               item.setBank(null);
               firePropertyChange(PROPERTY_ADMINUSERS, item, null);
            }
         }
      }
      return this;
   }

   public User createAdminUsers()
   {
      User value = new User();
      withAdminUsers(value);
      return value;
   } 

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- Account
    *              bank                   adminAccounts
    * </pre>
    */
   
   public static final String PROPERTY_ADMINACCOUNTS = "adminAccounts";

   private AccountSet adminAccounts = null;
   
   public AccountSet getAdminAccounts()
   {
      if (this.adminAccounts == null)
      {
         return AccountSet.EMPTY_SET;
      }
   
      return this.adminAccounts;
   }

   public Bank withAdminAccounts(Account... value)
   {
      if(value==null){
         return this;
      }
      for (Account item : value)
      {
         if (item != null)
         {
            if (this.adminAccounts == null)
            {
               this.adminAccounts = new AccountSet();
            }
            
            boolean changed = this.adminAccounts.add (item);

            if (changed)
            {
               item.withBank(this);
               firePropertyChange(PROPERTY_ADMINACCOUNTS, null, item);
            }
         }
      }
      return this;
   } 

   public Bank withoutAdminAccounts(Account... value)
   {
      for (Account item : value)
      {
         if ((this.adminAccounts != null) && (item != null))
         {
            if (this.adminAccounts.remove(item))
            {
               item.setBank(null);
               firePropertyChange(PROPERTY_ADMINACCOUNTS, item, null);
            }
         }
      }
      return this;
   }

   public Account createAdminAccounts()
   {
      Account value = new Account();
      withAdminAccounts(value);
      return value;
   }

   public String Login(String username, String password ) {
      if (username == null || password == null) {
         return null;
      }

      UserSet custUserSet = this.getCustomerUser();
      for (User custUsr : custUserSet) {
         if (custUsr.getUsername() != null && custUsr.getUsername().equals(username) && custUsr.getPassword().equals(password)) {
            //custUsr.setLoggedIn(true);
            return custUsr.getUserID();
         }
      }

      UserSet admnUserSet = this.getAdminUsers();
      for (User admUsr : admnUserSet) {
         if (admUsr.getName() != null && admUsr.getName().equals(username) && admUsr.getPassword().equals(password)) {
            //admUsr.setLoggedIn(true);
            return admUsr.getUserID();
         }
      }

      return null;
   }

   //==========================================================================
   public boolean confirmTransaction( int toAcctID, int fromAcctID, Integer dollarValue, Integer decimalValue )
   {
      return false;
   }

   // withDrawFunds from given account
   public BigInteger withDrawFunds(int accountNum,BigInteger amount, StringBuilder msg){
      BigInteger balance=BigInteger.ZERO;

      Account withDrawAccnt = this.findAccountByID(accountNum);

      if (withDrawAccnt==null){
         msg.append("Account number " + accountNum + " not found.");
         return balance;
      }

      if (withDrawAccnt.getBalance().compareTo(amount)!=1){
         msg.append("Not enough funds exists.");
         return withDrawAccnt.getBalance();
      }

      withDrawAccnt.withdraw(amount);
      balance=  withDrawAccnt.getBalance();

      //create a transaction
      Transaction trans = new Transaction();
      Date dt = new Date(System.currentTimeMillis());

      trans.setAmount(amount);
      trans.setCreationdate(dt);
      trans.setNote("Withdraw. Amount " + amount);
      trans.setTransType(TransactionTypeEnum.WITHDRAW);
      this.withTransaction(trans);

      // set the message
      msg.append("successful");

      return balance;
   }

   // depositFunds to given account
   public BigInteger depositFunds(int accountNum,BigInteger amount, StringBuilder msg){
      BigInteger balance=BigInteger.ZERO;

      Account depositAccnt = findAccountByID(accountNum);

      if (depositAccnt==null){
         msg.append("Account number " + accountNum + " not found.");
         return balance;
      }

      depositAccnt.deposit(amount);
      balance=  depositAccnt.getBalance();

      //create a transaction
      Transaction trans = new Transaction();
      Date dt = new Date(System.currentTimeMillis());

      trans.setAmount(amount);
      trans.setCreationdate(dt);
      trans.setNote("Deposit. Amount " + amount);
      trans.setTransType(TransactionTypeEnum.DEPOSIT);
      this.withTransaction(trans);

      // set the message
      msg.append("successful");

      return balance;
   }

   // update given user's info
   public String updateUserInfo(String userID, String fieldName, String fieldValue){

      UserSet usr= this.getCustomerUser().filterUserID(userID);

      if(usr.size()==0){
         return "UserID " + userID  + " is not valid.";
      }

      //this.findUserByID(userID).setName(fieldValue);
      //this.getCustomerUser().withUserID(userID).filterUserID(userID).getName();
      //return "successful";

      //usr.setIsAdmin(Boolean.valueOf(fieldValue));

      /*
      user.withAttribute("name", DataType.STRING);
      user.withAttribute("userID",DataType.STRING); NO
      user.withAttribute("isAdmin", DataType.BOOLEAN);
      user.withAttribute("password", DataType.STRING);
      user.withAttribute("email", DataType.STRING);
      user.withAttribute("LoggedIn", DataType.BOOLEAN);
      user.withAttribute("phone", DataType.STRING); // FA 4-12-2017 Changed to String from int, adjustments made to the user related classes
      user.withAttribute("username", DataType.STRING); // FA 4-12-2017 new field
      */

      //System.out.println("fieldName.toUpperCase():" + fieldName.toUpperCase());

      switch (fieldName.toUpperCase()) {
         case "NAME":
            usr.withName(fieldValue);
            break;
         case "PASSWORD":
            usr.withPassword(fieldValue);
            break;
         case "EMAIL":
            usr.withEmail(fieldValue);
            break;
         case "PHONE":
            usr.withPhone(fieldValue);
            break;
         default:
            return "Field " + fieldName + " is not valid.";
      }

      //System.out.println("updateUserInfo:" + usr.getPhone());
      return "successful";

   }

   public String createUser(String username, String password,String name, String phoneNumber,String email,boolean isAdmin, StringBuilder msg)
   {

      // get the next userID, check to make sure it is not used
      boolean loop=true;
      String valID=null;
      while(loop) {
         valID=String.valueOf(this.getNextID());

         if(this.getCustomerUser().filterUserID(valID).size() == 0 &&
                 this.getAdminUsers().filterUserID(valID).size() == 0) {
            loop=false;
         }
      }

      if(valID==null){
         // set the message
         msg.append("unsuccessful. UserID is null");

         return "-1";
      }

      // check if username is already used
      if(this.getCustomerUser().filterUsername(username).size() != 0 ||
              this.getAdminUsers().filterUsername(username).size() != 0) {
         throw new IllegalArgumentException("Username " + username + " has already been used");
      }

      //set user attributes
      User usr = new User();
      usr.setUserID(valID);
      usr.setUsername(username);
      usr.setPassword(password);
      usr.setPhone(phoneNumber);
      usr.setEmail(email);
      usr.setIsAdmin(isAdmin);

      // check which user will be created
      if(isAdmin){
         this.withAdminUsers(usr);
      }else{
         this.withCustomerUser(usr);
      }

      // set the message
      msg.append("successful");

      return valID;
   }

   // create user Account
   public String createAccount(String userID,boolean isAdminAccount,BigInteger initialBalance, AccountTypeEnum accountType, StringBuilder msg) {

      // get the next accountnumber, check to make sure it is not used
      boolean loop=true;
      int valID=0;
      while(loop) {
         valID=this.getNextID();

         if(this.getCustomerAccounts().filterAccountnum(valID).size() == 0 &&
                 this.getAdminAccounts().filterAccountnum(valID).size() == 0) {
            loop=false;
         }
      }

      if(valID==0) {
         msg.append("failure. Account Number is null.");
         return "-1";
      }

      User usr = this.findUserByID(userID);

      if (usr==null) {
         msg.append("failure. UserID " + userID + " not found.");
         return "-1";
      }

      Account accnt = new Account()
              .withAccountnum(valID)
              .withOwner(usr)
              .withType(accountType)
              .withBalance(initialBalance);


      // check which user will be created
      if(isAdminAccount){
         this.withAdminAccounts(accnt);
      }else{
         this.withCustomerAccounts(accnt);
      }

      msg.append("successful");

      return String.valueOf(valID);
   }

   // get 10 digit ID
   public static int getNextID() {
      Random r = new Random(System.currentTimeMillis());
      return Math.abs(1000000000 + r.nextInt(2000000000));
   }

   
   /********************************************************************
    * <pre>
    *              one                       many
    * Bank ----------------------------------- FeeValue
    *              bank                   feeValue
    * </pre>
    */
   
   public static final String PROPERTY_FEEVALUE = "feeValue";

   private FeeValueSet feeValue = null;
   
   public FeeValueSet getFeeValue()
   {
      if (this.feeValue == null)
      {
         return FeeValueSet.EMPTY_SET;
      }
   
      return this.feeValue;
   }

   //WILL COME BACK TO THIS
   public Bank withFeeValue(FeeValue... value) {
      if(value==null){
         return this;
      }
      for (FeeValue item : value) {
         boolean skip = (this.getFeeValue().size() >= 5); //If the FeeValue set size is less than 5, skip = false
         if (item != null) {
            if (this.feeValue == null) {
               this.feeValue = new FeeValueSet();
            }
            else {
               // Search for duplicate FeeValues
               FeeValueSet pulledFeeValues = this.getFeeValue();
               for (FeeValue i : pulledFeeValues) {
                  if (item.getTransType() != null && item.getTransType() == i.getTransType())
                     skip = true;
               }
            }
            if (!skip) {
               boolean changed = this.feeValue.add(item);
               if (changed) {
                  item.withBank(this);
                  firePropertyChange(PROPERTY_FEEVALUE, null, item);
               }
            }
         }
      }
      return this;
   } 

   public Bank withoutFeeValue(FeeValue... value)
   {
      for (FeeValue item : value)
      {
         if ((this.feeValue != null) && (item != null))
         {
            if (this.feeValue.remove(item))
            {
               item.setBank(null);
               firePropertyChange(PROPERTY_FEEVALUE, item, null);
            }
         }
      }
      return this;
   }

   public FeeValue createFeeValue()
   {
      FeeValue value = new FeeValue();
      withFeeValue(value);
      return value;
   } 
}
